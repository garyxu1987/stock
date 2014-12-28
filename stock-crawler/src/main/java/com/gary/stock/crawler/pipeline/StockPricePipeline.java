package com.gary.stock.crawler.pipeline;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.gary.stock.common.IStockPriceService;
import com.gary.stock.crawler.CrawlerConst;
import com.gary.stock.model.StockPrice;
import com.gary.stock.model.crawler.Chartlist;
import com.gary.stock.model.crawler.PriceList;

@Component("stockPricePipeline")
public class StockPricePipeline implements Pipeline {

	@Autowired
	private IStockPriceService stockPriceService;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	// private static final String[] DAY_WEEK = new String[] { "Sun", "Mon",
	// "Tue", "Wed", "Thu.", "Fri", "Sat" };
	private static final String[] MONTH = new String[] { "Jan", "Feb", "Mar",
			"Apr", "may", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	public void process(ResultItems resultItems, Task task) {

		PriceList stockPriceList = resultItems
				.get(CrawlerConst.RESULT_KEY_STOCKPRICELIST);

		if (stockPriceList != null) {
			for (Chartlist chartlistItem : stockPriceList.getChartlist()) {
				StockPrice stockPrice = new StockPrice();

				stockPrice.setVolume(chartlistItem.getVolume());
				stockPrice.setOpen(chartlistItem.getOpen());
				stockPrice.setHigh(chartlistItem.getHigh());
				stockPrice.setClose(chartlistItem.getClose());
				stockPrice.setLow(chartlistItem.getLow());
				stockPrice.setChg(chartlistItem.getChg());
				stockPrice.setPercent(chartlistItem.getPercent());
				stockPrice.setTurnrate(chartlistItem.getTurnrate());
				stockPrice.setMa5(chartlistItem.getMa5());
				stockPrice.setMa10(chartlistItem.getMa10());
				stockPrice.setMa20(chartlistItem.getMa20());
				stockPrice.setMa30(chartlistItem.getMa30());
				stockPrice.setDif(chartlistItem.getDif());
				stockPrice.setDea(chartlistItem.getDea());
				stockPrice.setMacd(chartlistItem.getMacd());

				Date time = parseDateFromString(chartlistItem.getTime());
				stockPrice.setTime(time);
				stockPrice.setDateId(Integer.parseInt(sdf.format(time)));

				stockPriceService.saveStockPrice(stockPrice,
						(String) resultItems
								.get(CrawlerConst.RESULT_KEY_MARKET),
						(String) resultItems.get(CrawlerConst.RESULT_KEY_CODE),
						(int) resultItems.get(CrawlerConst.RESULT_KEY_PERIOD),
						(int) resultItems.get(CrawlerConst.RESULT_KEY_TYPE));
			}
		}
	}

	private Date parseDateFromString(String time) {
		Calendar calendar = Calendar.getInstance();
		String[] timeArr = time.split(" ");
		int month = 0;

		for (int i = 0; i < MONTH.length; i++) {
			if (MONTH[i].endsWith(timeArr[1])) {
				month = i;
			}
		}

		calendar.set(Integer.parseInt(timeArr[5]), month,
				Integer.parseInt(timeArr[2]));
		return calendar.getTime();
	}
}