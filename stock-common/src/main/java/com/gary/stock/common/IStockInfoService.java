package com.gary.stock.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.gary.stock.model.StockInfo;

public interface IStockInfoService {
	public Serializable saveOrUpdate(StockInfo stockInfo);

	public StockInfo findOne(Criteria criteria);

	public List<StockInfo> find(Criteria criteria);
}
