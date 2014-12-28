package com.gary.stock.common;


public class CommonUtil {
	public static String getStockPriceCollectionName(String market,
			String code, int period, int type) {
		return market + code
				+ CommonConstant.DELIMITER_STOCKPRICE_COLLECTION_NAME + period
				+ CommonConstant.DELIMITER_STOCKPRICE_COLLECTION_NAME + type;
	}

}
