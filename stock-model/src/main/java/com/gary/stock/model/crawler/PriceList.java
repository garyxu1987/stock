package com.gary.stock.model.crawler;

import java.util.ArrayList;
import java.util.List;

public class PriceList {

	private List<Chartlist> chartlist = new ArrayList<Chartlist>();
	private String success;
	private Stock stock;

	public List<Chartlist> getChartlist() {
		return chartlist;
	}

	public void setChartlist(List<Chartlist> chartlist) {
		this.chartlist = chartlist;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}
