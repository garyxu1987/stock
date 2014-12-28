package com.gary.stock.crawler;

import com.gary.stock.common.CommonConstant;

public class CrawlerConst {
	public static final int PRICE_DAY_ALL = -1;

	public static final String RESULT_KEY_SZ_STOCKINFO = "stockInfo";

	public static final String RESULT_KEY_STOCKPRICELIST = "stockPriceList";
	public static final String RESULT_KEY_MARKET = "market";
	public static final String RESULT_KEY_CODE = "code";
	public static final String RESULT_KEY_PERIOD = "period";
	public static final String RESULT_KEY_TYPE = "type";

	public static final String DAILY_PERIOD_REQUEST_PARAM = "1day";
	public static final String WEEKLY_PERIOD_REQUEST_PARAM = "1week";
	public static final String MONTHLY_PERIOD_REQUEST_PARAM = "1month";

	public static final String NORMAL_EX_TYPE_REQUEST_PARAM = "normal";
	public static final String BEFORE_EX_TYPE_REQUEST_PARAM = "before";
	public static final String AFTER_EX_TYPE_REQUEST_PARAM = "after";

	public static String[] PERIOD_REQUEST_PARAM = new String[3];
	public static String[] EX_TYPE_REQUEST_PARAM = new String[3];

	static {
		PERIOD_REQUEST_PARAM[CommonConstant.DAILY_PERIOD] = CrawlerConst.DAILY_PERIOD_REQUEST_PARAM;
		PERIOD_REQUEST_PARAM[CommonConstant.WEEKLY_PERIOD] = CrawlerConst.WEEKLY_PERIOD_REQUEST_PARAM;
		PERIOD_REQUEST_PARAM[CommonConstant.MONTHLY_PERIOD] = CrawlerConst.MONTHLY_PERIOD_REQUEST_PARAM;

		EX_TYPE_REQUEST_PARAM[CommonConstant.NORMAL_EX_TYPE] = CrawlerConst.NORMAL_EX_TYPE_REQUEST_PARAM;
		EX_TYPE_REQUEST_PARAM[CommonConstant.BEFORE_EX_TYPE] = CrawlerConst.BEFORE_EX_TYPE_REQUEST_PARAM;
		EX_TYPE_REQUEST_PARAM[CommonConstant.AFTER_EX_TYPE] = CrawlerConst.AFTER_EX_TYPE_REQUEST_PARAM;
	}

}
