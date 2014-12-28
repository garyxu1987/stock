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
import com.gary.stock.common.IStockInfoService;
import com.gary.stock.model.StockInfo;

@Service
public class StockInfoServiceImpl implements IStockInfoService {

	@Autowired
	MongoTemplate stockMongoTemplate;

	@Override
	public Serializable saveOrUpdate(StockInfo stockInfo) {
		StockInfo oldObject = findOne(Criteria
				.where(CommonConstant.CODE_STOCKINFO_COLUMN_NAME)
				.is(stockInfo.getCode())
				.and(CommonConstant.MARKET_STOCKINFO_COLUMN_NAME)
				.is(stockInfo.getMarket()));
		if (oldObject != null) {
			stockInfo.setId(oldObject.getId());
		}
		stockMongoTemplate.save(stockInfo, CommonConstant.STOCKINFO_COLLECTION_NAME);
		return stockInfo.getId();
	}

	@Override
	public StockInfo findOne(Criteria criteria) {
		List<StockInfo> result = find(criteria);
		return (CollectionUtils.isNotEmpty(result) ? result.get(0) : null);
	}

	@Override
	public List<StockInfo> find(Criteria criteria) {
		Query query;
		if (criteria == null) {
			query = new Query();
		} else {
			query = new Query(criteria);
		}
		return stockMongoTemplate.find(query, StockInfo.class);
	}

}
