package com.gary.stock.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.gary.stock.model.StockPrice;

public interface IStockPriceService {
	/**
	 * 
	 * @param stockPrice
	 * @param market
	 * @param code
	 * @param period
	 *            间隔期
	 * @param type
	 *            复权情况
	 * @return
	 */
	public Serializable saveStockPrice(StockPrice stockPrice, String market,
			String code, int period, int type);

	public StockPrice findOne(Criteria criteria, String market, String code,
			int period, int type);

	public List<StockPrice> find(Criteria criteria, String market, String code,
			int period, int type);

}
