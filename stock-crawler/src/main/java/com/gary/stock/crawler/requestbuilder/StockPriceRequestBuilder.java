package com.gary.stock.crawler.requestbuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.utils.HttpConstant;

import com.gary.stock.common.CommonConstant;
import com.gary.stock.common.IStockInfoService;
import com.gary.stock.crawler.AbstractRequestBuilder;
import com.gary.stock.crawler.CrawlerConst;
import com.gary.stock.model.StockInfo;

@Component("stockPriceRequestBuilder")
public class StockPriceRequestBuilder extends AbstractRequestBuilder {

	@Autowired
	private IStockInfoService stockInfoService;

	@Override
	public List<Request> getRequests() {
		List<Request> result = new ArrayList<Request>();

		List<StockInfo> list = stockInfoService.find(Criteria.where(
				CommonConstant.MARKET_STOCKINFO_COLUMN_NAME).is(
				CommonConstant.MARKET_SHANGHAI));
		// List<StockInfo> list = stockInfoService.find(null);
		if (CollectionUtils.isNotEmpty(list)) {
			for (StockInfo stockInfo : list) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						Request request = new Request();
						long now = (new Date()).getTime();
						request.setUrl("http://xueqiu.com/stock/forchartk/stocklist.json?symbol="
								+ stockInfo.getMarket()
								+ stockInfo.getCode()
								+ "&period="
								+ CrawlerConst.PERIOD_REQUEST_PARAM[i]
								+ "&type="
								+ CrawlerConst.EX_TYPE_REQUEST_PARAM[j]
								+ "&begin=0&end=" + now + "&_=" + now);
						request.setMethod(HttpConstant.Method.GET);
						result.add(request);
					}
				}
			}
		}
		return result;
	}
}
