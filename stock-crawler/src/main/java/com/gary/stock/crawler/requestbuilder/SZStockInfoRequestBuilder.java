package com.gary.stock.crawler.requestbuilder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Request;

import com.gary.stock.crawler.AbstractRequestBuilder;

@Component("szStockInfoRequestBuilder")
public class SZStockInfoRequestBuilder extends AbstractRequestBuilder {

	@Override
	public List<Request> getRequests() {
		List<Request> result = new ArrayList<Request>();

		Request request = new Request();
		// request.setMethod("post");
		request.setUrl("http://www.szse.cn/szseWeb/FrontController.szse?ACTIONID=8&CATALOGID=1110&TABKEY=tab1&ENCODE=1");

		result.add(request);
		return result;
	}
}
