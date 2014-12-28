package com.gary.stock.common;

public class CommonConstant {

	public static final String MARKET_SHANGHAI = "SH";
	public static final String MARKET_SHENZHEN = "SZ";

	public static final String STOCKINFO_COLLECTION_NAME = "stockInfo";

	public static final String DELIMITER_STOCKPRICE_COLLECTION_NAME = "_";

	public static final int DAILY_PERIOD = 0;
	public static final int WEEKLY_PERIOD = 1;
	public static final int MONTHLY_PERIOD = 2;

	public static final int NORMAL_EX_TYPE = 0;
	public static final int BEFORE_EX_TYPE = 1;
	public static final int AFTER_EX_TYPE = 2;

	public static final String STOCK_MONGOTEMPLATE_NAME = "stockMongoTemplate";
	public static final String BEFORE_STOCK_MONGOTEMPLATE_NAME = "beforeExTypeStockmongoTemplate";
	public static final String NORMAL_STOCK_MONGOTEMPLATE_NAME = "normalExTypeStockmongoTemplate";
	public static final String AFTER_STOCK_MONGOTEMPLATE_NAME = "afterExTypeStockmongoTemplate";

	// public static final String DAILY_SUFFIX_STOCKPRICE_COLLECTION_NAME =
	// "daily";
	// public static final String WEEKLY_SUFFIX_STOCKPRICE_COLLECTION_NAME =
	// "weekly";
	// public static final String MONTHLY_SUFFIX_STOCKPRICE_COLLECTION_NAME =
	// "monthly";
	//
	// public static final String NORMAL_SUFFIX_STOCKPRICE_COLLECTION_NAME =
	// "normal";
	// public static final String BEFORE_SUFFIX_STOCKPRICE_COLLECTION_NAME =
	// "before";
	// public static final String AFTER_SUFFIX_STOCKPRICE_COLLECTION_NAME =
	// "after";

	public static final String CODE_STOCKINFO_COLUMN_NAME = "code";
	public static final String MARKET_STOCKINFO_COLUMN_NAME = "market";

	public static final String DATEID_STOCKPRICE_COLUMN_NAME = "dateId";
}
