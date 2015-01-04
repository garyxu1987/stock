package com.gary.stock.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gary.stock.model.StockInfo;
import com.gary.stock.model.StockPrice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class StockPriceServiceTest {
	@Autowired
	MongoTemplate stockMongoTemplate;
	// @Autowired
	// MongoTemplate stock1MongoTemplate;
	@Autowired
	MongoTemplate beforeExTypeStockmongoTemplate;
	@Autowired
	MongoTemplate normalExTypeStockmongoTemplate;
	@Autowired
	MongoTemplate afterExTypeStockmongoTemplate;

	static ExecutorService pool = Executors.newFixedThreadPool(50);

	@Test
	public void mergeDB() {
		// doMerge(stockMongoTemplate);
		// doMerge(stock1MongoTemplate);
	}

	@Test
	public void beautyPriceData() {
		List<StockInfo> list = stockMongoTemplate.find(
				new Query(Criteria.where(
						CommonConstant.MARKET_STOCKINFO_COLUMN_NAME).is(
						CommonConstant.MARKET_SHANGHAI)), StockInfo.class,
				CommonConstant.STOCKINFO_COLLECTION_NAME);

		if (CollectionUtils.isNotEmpty(list)) {
			List<Future<?>> fl = new ArrayList<Future<?>>();
			for (StockInfo info : list) {
				fl.add(keepUnique(afterExTypeStockmongoTemplate, info,
						CommonConstant.DAILY_PERIOD,
						CommonConstant.AFTER_EX_TYPE));
				fl.add(keepUnique(afterExTypeStockmongoTemplate, info,
						CommonConstant.WEEKLY_PERIOD,
						CommonConstant.AFTER_EX_TYPE));
				fl.add(keepUnique(afterExTypeStockmongoTemplate, info,
						CommonConstant.MONTHLY_PERIOD,
						CommonConstant.AFTER_EX_TYPE));

				fl.add(keepUnique(beforeExTypeStockmongoTemplate, info,
						CommonConstant.DAILY_PERIOD,
						CommonConstant.BEFORE_EX_TYPE));
				fl.add(keepUnique(beforeExTypeStockmongoTemplate, info,
						CommonConstant.WEEKLY_PERIOD,
						CommonConstant.BEFORE_EX_TYPE));
				fl.add(keepUnique(beforeExTypeStockmongoTemplate, info,
						CommonConstant.MONTHLY_PERIOD,
						CommonConstant.BEFORE_EX_TYPE));

				fl.add(keepUnique(normalExTypeStockmongoTemplate, info,
						CommonConstant.DAILY_PERIOD,
						CommonConstant.NORMAL_EX_TYPE));
				fl.add(keepUnique(normalExTypeStockmongoTemplate, info,
						CommonConstant.WEEKLY_PERIOD,
						CommonConstant.NORMAL_EX_TYPE));
				fl.add(keepUnique(normalExTypeStockmongoTemplate, info,
						CommonConstant.MONTHLY_PERIOD,
						CommonConstant.NORMAL_EX_TYPE));

			}
			for (Future<?> f : fl) {
				try {
					f.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Future<?> keepUnique(MongoTemplate template, StockInfo info,
			int period, int type) {
		TidyThread t = new TidyThread(template, info, period, type);
		pool.submit(t);
		return pool.submit(t);
	}

	private void doMerge(MongoTemplate template) {
		Iterator<String> cns = template.getCollectionNames().iterator();
		List<Future<?>> fl = new ArrayList<Future<?>>();
		while (cns.hasNext()) {
			String name = cns.next();
			if (name.endsWith(String.valueOf(CommonConstant.NORMAL_EX_TYPE))) {
				CloneThread t = new CloneThread(template,
						normalExTypeStockmongoTemplate, name);
				Future<?> f = pool.submit(t);
				fl.add(f);
			} else if (name.endsWith(String
					.valueOf(CommonConstant.BEFORE_EX_TYPE))) {
				CloneThread t = new CloneThread(template,
						beforeExTypeStockmongoTemplate, name);
				pool.submit(t);
				Future<?> f = pool.submit(t);
			} else if (name.endsWith(String
					.valueOf(CommonConstant.AFTER_EX_TYPE))) {
				CloneThread t = new CloneThread(template,
						afterExTypeStockmongoTemplate, name);
				pool.submit(t);
				Future<?> f = pool.submit(t);
			}
		}

		for (Future<?> f : fl) {
			try {
				f.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class CloneThread implements Runnable {
	MongoTemplate source;
	MongoTemplate dest;
	String collectionName;

	public CloneThread(MongoTemplate source, MongoTemplate dest,
			String collectionName) {
		super();
		this.source = source;
		this.dest = dest;
		this.collectionName = collectionName;
	}

	@Override
	public void run() {
		System.out.println("handling " + collectionName
				+ "======================");
		List<StockPrice> list = source.find(new Query(), StockPrice.class,
				collectionName);

		if (CollectionUtils.isNotEmpty(list)) {
			for (StockPrice price : list) {
				price.setId(null);
				List<StockPrice> temp = dest.find(
						new Query(Criteria.where(
								CommonConstant.DATEID_STOCKPRICE_COLUMN_NAME)
								.is(price.getDateId())), StockPrice.class,
						collectionName);
				if (CollectionUtils.isEmpty(temp)) {
					dest.insert(price, collectionName);
					System.out.println(price.getDateId());
				} else {
					System.out
							.println(price.getDateId() + "@" + collectionName);
				}
			}
		}
		System.out.println("handled " + collectionName
				+ "*******************************");
	}
}

class TidyThread implements Runnable {

	MongoTemplate template;
	StockInfo info;
	int period;
	int type;

	public MongoTemplate getTemplate() {
		return template;
	}

	public void setTemplate(MongoTemplate template) {
		this.template = template;
	}

	public StockInfo getInfo() {
		return info;
	}

	public void setInfo(StockInfo info) {
		this.info = info;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public TidyThread(MongoTemplate template, StockInfo info, int period,
			int type) {
		super();
		this.template = template;
		this.info = info;
		this.period = period;
		this.type = type;
	}

	@Override
	public void run() {
		GroupBy gb = GroupBy
				.key(new String[] { CommonConstant.DATEID_STOCKPRICE_COLUMN_NAME })
				.initialDocument("{ count: 0 }")
				.reduceFunction("function(obj, result) { result.count += 1; }");
		String cn = CommonUtil.getStockPriceCollectionName(info.getMarket(),
				info.getCode(), period, type);

		GroupByResults<Temp> result = template.group(cn, gb, Temp.class);
		Iterator<Temp> iterator = result.iterator();

		while (iterator.hasNext()) {
			Temp t = iterator.next();
			if (t.getCount() > 1) {
				System.out.println(cn + "**************" + t.getDateId());
				List<StockPrice> rpl = template.find(
						new Query(Criteria.where(
								CommonConstant.DATEID_STOCKPRICE_COLUMN_NAME)
								.is(t.getDateId())), StockPrice.class, cn);
				for (int i = 0; i < rpl.size() - 1; i++) {
					template.remove(rpl.get(i), cn);
				}
				rpl = template.find(
						new Query(Criteria.where(
								CommonConstant.DATEID_STOCKPRICE_COLUMN_NAME)
								.is(t.getDateId())), StockPrice.class, cn);

				if (rpl.size() == 0) {
					System.out.println(cn + "==================="
							+ t.getDateId());
					System.exit(0);
				}
			}
		}
	}
}

class Temp {
	private Integer dateId;
	private Integer count;

	public Integer getDateId() {
		return dateId;
	}

	public void setDateId(Integer dateId) {
		this.dateId = dateId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}