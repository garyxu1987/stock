package com.gary.stock.crawler.pageprocessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;

import com.gary.stock.crawler.AbstractSpiderPageProcessor;
import com.gary.stock.crawler.CrawlerConst;
import com.gary.stock.model.crawler.PriceList;

/**
 * 
 * 获取股票信息
 * 
 * @author Gary
 * 
 */

@Component("stockPricePageProcessor")
public class StockPricePageProcessor extends AbstractSpiderPageProcessor {

	private static final Pattern MARKET_CODE_PATTERN = Pattern
			.compile("symbol=([^&]+)");
	private static final Pattern TYPE_PATTERN = Pattern.compile("type=([^&]+)");
	private static final Pattern PERIOD_PATTERN = Pattern
			.compile("period=([^&]+)");

	@Override
	public void process(Page page) {
		String url = page.getUrl().toString();

		String maketAndCode = getRequestVal(url, MARKET_CODE_PATTERN);
		String type = getRequestVal(url, TYPE_PATTERN);
		String period = getRequestVal(url, PERIOD_PATTERN);

		page.putField(CrawlerConst.RESULT_KEY_MARKET,
				maketAndCode.substring(0, 2));
		page.putField(CrawlerConst.RESULT_KEY_CODE, maketAndCode.substring(2));
		page.putField(CrawlerConst.RESULT_KEY_TYPE, getTypeIndex(type));
		page.putField(CrawlerConst.RESULT_KEY_PERIOD, getPeriodIndex(period));

		page.putField(CrawlerConst.RESULT_KEY_STOCKPRICELIST, page.getJson()
				.toObject(PriceList.class));
	}

	private int getTypeIndex(String type) {
		for (int i = 0; i < CrawlerConst.EX_TYPE_REQUEST_PARAM.length; i++) {
			if (type.equals(CrawlerConst.EX_TYPE_REQUEST_PARAM[i])) {
				return i;
			}
		}
		return 0;
	}

	private int getPeriodIndex(String period) {
		for (int i = 0; i < CrawlerConst.PERIOD_REQUEST_PARAM.length; i++) {
			if (period.equals(CrawlerConst.PERIOD_REQUEST_PARAM[i])) {
				return i;
			}
		}
		return 0;
	}

	private String getRequestVal(String url, Pattern p) {
		Matcher m = p.matcher(url);
		String result = null;
		if (m.find()) {
			result = m.group(1);
		}
		return result;
	}
}
