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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gary.stock.model.StockPrice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class StockPriceServiceTest {
	@Autowired
	MongoTemplate stockMongoTemplate;
	@Autowired
	MongoTemplate stock1MongoTemplate;
	@Autowired
	MongoTemplate beforeExTypeStockmongoTemplate;
	@Autowired
	MongoTemplate normalExTypeStockmongoTemplate;
	@Autowired
	MongoTemplate afterExTypeStockmongoTemplate;

	static ExecutorService pool = Executors.newFixedThreadPool(50);

	@Test
	public void mergeDB() {
		// handleTemplate(stockMongoTemplate);
		handleTemplate(stock1MongoTemplate);
	}

	private void handleTemplate(MongoTemplate template) {
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

	private void doMerge(MongoTemplate source, MongoTemplate dest,
			String collectionName) {
		// List<StockPrice> list = source.find(new Query(), StockPrice.class,
		// collectionName);
		//
		// if (CollectionUtils.isNotEmpty(list)) {
		// for (StockPrice price : list) {
		// price.setId(null);
		// List<StockPrice> temp = dest.find(
		// new Query(Criteria.where(
		// CommonConstant.DATEID_STOCKPRICE_COLUMN_NAME)
		// .is(price.getDateId())), StockPrice.class,
		// collectionName);
		// if (CollectionUtils.isEmpty(temp)) {
		// dest.insert(price, collectionName);
		// } else {
		// System.out
		// .println(price.getDateId() + "@" + collectionName);
		// }
		// }
		// }
	}
	// @Test
	// public void testFind() {
	// for (StockInfo st : stockInfoService.find(null)) {
	// System.out.println(st.getId());
	// }
	// }
	//
	// @Test
	// public void testFindOne() {
	// StockInfo st = stockInfoService.findOne(Criteria.where(
	// CommonConstant.CODE_STOCKINFO_COLUMN_NAME).is("000001"));
	// Assert.assertEquals(st.getName(), "平安银行");
	// }
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
