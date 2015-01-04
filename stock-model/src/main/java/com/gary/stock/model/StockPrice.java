package com.gary.stock.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class StockPrice {

	@Id
	private String id;

	@Indexed(unique = true)
	private Integer dateId;
	// 成交量
	private Double volume;
	private Double open;
	private Double high;
	private Double close;
	private Double low;
	// 涨跌额
	private Double chg;
	// 涨跌幅
	private Double percent;
	// 换手率
	private Double turnrate;
	private Double ma5;
	private Double ma10;
	private Double ma20;
	private Double ma30;
	private Double dif;
	private Double dea;
	private Double macd;
	private Date time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDateId() {
		return dateId;
	}

	public void setDateId(Integer dateId) {
		this.dateId = dateId;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getChg() {
		return chg;
	}

	public void setChg(Double chg) {
		this.chg = chg;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getTurnrate() {
		return turnrate;
	}

	public void setTurnrate(Double turnrate) {
		this.turnrate = turnrate;
	}

	public Double getMa5() {
		return ma5;
	}

	public void setMa5(Double ma5) {
		this.ma5 = ma5;
	}

	public Double getMa10() {
		return ma10;
	}

	public void setMa10(Double ma10) {
		this.ma10 = ma10;
	}

	public Double getMa20() {
		return ma20;
	}

	public void setMa20(Double ma20) {
		this.ma20 = ma20;
	}

	public Double getMa30() {
		return ma30;
	}

	public void setMa30(Double ma30) {
		this.ma30 = ma30;
	}

	public Double getDif() {
		return dif;
	}

	public void setDif(Double dif) {
		this.dif = dif;
	}

	public Double getDea() {
		return dea;
	}

	public void setDea(Double dea) {
		this.dea = dea;
	}

	public Double getMacd() {
		return macd;
	}

	public void setMacd(Double macd) {
		this.macd = macd;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
