package com.gary.stock.crawler.pageprocessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gary.stock.crawler.SpiderConfig;
import com.gary.stock.crawler.service.WebMagicCrawler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class StockInfoPageProcessorTest {
	@Autowired
	WebMagicCrawler webMagicCrawler;
	@Autowired
	SpiderConfig szStockInfoConfig;
	@Autowired
	SpiderConfig shStockInfoConfig;

	@Autowired
	SpiderConfig stockPriceConfig;
	@Autowired
	SpiderConfig lackStockPriceConfig;
	// @Autowired
	// SpiderConfig sinaTodayLackStockPriceConfig;
	// @Autowired
	// SpiderConfig yahooStockHistoryPriceConfig;
	// @Autowired
	// SpiderConfig yahooStockLackHistoryPriceConfig;
	private static final Pattern MARKET_CODE_PATTERN = Pattern
			.compile("symbol=([^&]+)");
	private static final Pattern TYPE_PATTERN = Pattern.compile("type=([^&]+)");
	private static final Pattern PERIOD_PATTERN = Pattern
			.compile("period=([^&]+)");

	private static final String[] MONTH = new String[] { "Jan", "Feb", "Mar",
			"Apr", "may", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

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

	@Test
	public void testSZProcess() {
		// // Date d = new Date(1419678065260l);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// // System.out.println((new Date()).getDay());
		// // // System.out.println(sdf.format(d));
		// String time = "Fri Oct 25 00:00:00 +0800 2002";
		// System.out.println(sdf.format(parseDateFromString(time)));
		// System.out.println(parseDateFromString(time).getTime());
		// // String url =
		// //
		// "http://xueqiu.com/stock/forchartk/stocklist.json?symbol=SH600036&period=1day&type=normal&begin=0&end=1419680039477&_=1419680039479";
		// //
		// // System.out.println(getRequestVal(url,
		// // MARKET_CODE_PATTERN).substring(0,
		// // 2));
		// // System.out
		// // .println(getRequestVal(url, MARKET_CODE_PATTERN).substring(2));
		// // System.out.println(getRequestVal(url, TYPE_PATTERN));
		// // System.out.println(getRequestVal(url, PERIOD_PATTERN));
		 webMagicCrawler.crawl(szStockInfoConfig);
	}

	private String getRequestVal(String url, Pattern p) {
		Matcher m = p.matcher(url);
		String result = null;
		if (m.find()) {
			result = m.group(1);
		}
		return result;
	}

	@Test
	public void testSHProcess() {
		webMagicCrawler.crawl(shStockInfoConfig);
	}

	@Test
	public void testStockPriceProcess() {
		webMagicCrawler.crawl(stockPriceConfig);
	}

	@Test
	public void testLackStockPriceProcess() {
		webMagicCrawler.crawl(lackStockPriceConfig);
	}

	// @Test
	// public void testSinaTodayLackStockPriceProcess() throws Exception {
	// webMagicCrawler.crawl(sinaTodayLackStockPriceConfig);
	// }
	//
	// @Test
	// public void testYahooStockHistoryProcess() {
	// webMagicCrawler.crawl(yahooStockHistoryPriceConfig);
	// }
	//
	// @Test
	// public void testYahooStockLackHistoryProcess() {
	// webMagicCrawler.crawl(yahooStockLackHistoryPriceConfig);
	// }

}
