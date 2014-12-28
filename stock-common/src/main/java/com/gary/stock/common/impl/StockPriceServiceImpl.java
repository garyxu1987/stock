package com.gary.stock.common.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gary.stock.common.CommonConstant;
import com.gary.stock.common.CommonUtil;
import com.gary.stock.common.IStockPriceService;
import com.gary.stock.model.StockPrice;

@Service
public class StockPriceServiceImpl implements IStockPriceService {

	@Autowired
	MongoTemplate beforeExTypeStockmongoTemplate;
	@Autowired
	MongoTemplate normalExTypeStockmongoTemplate;
	@Autowired
	MongoTemplate afterExTypeStockmongoTemplate;

	@Override
	public Serializable saveStockPrice(StockPrice stockPrice, String market,
			String code, int period, int type) {
		StockPrice oldObject = findOne(
				Criteria.where(CommonConstant.DATEID_STOCKPRICE_COLUMN_NAME)
						.is(stockPrice.getDateId()), market, code, period, type);
		if (oldObject != null) {
			stockPrice.setId(oldObject.getId());
		}
		getStockPriceMongoTemplate(type).save(
				stockPrice,
				CommonUtil.getStockPriceCollectionName(market, code, period,
						type));
		return stockPrice.getId();
	}

	@Override
	public StockPrice findOne(Criteria criteria, String market, String code,
			int period, int type) {
		List<StockPrice> result = find(criteria, market, code, period, type);
		return (CollectionUtils.isNotEmpty(result) ? result.get(0) : null);
	}

	@Override
	public List<StockPrice> find(Criteria criteria, String market, String code,
			int period, int type) {
		Query query;
		if (criteria == null) {
			query = new Query();
		} else {
			query = new Query(criteria);
		}
		return getStockPriceMongoTemplate(type).find(
				query,
				StockPrice.class,
				CommonUtil.getStockPriceCollectionName(market, code, period,
						type));
	}

	private MongoTemplate getStockPriceMongoTemplate(int type) {
		switch (type) {
		case CommonConstant.BEFORE_EX_TYPE: {
			return beforeExTypeStockmongoTemplate;
		}
		case CommonConstant.NORMAL_EX_TYPE: {
			return normalExTypeStockmongoTemplate;
		}
		default: {
			return afterExTypeStockmongoTemplate;
		}
		}
	}
}
