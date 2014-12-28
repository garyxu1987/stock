package com.gary.stock.crawler.requestbuilder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Request;

import com.gary.stock.crawler.AbstractRequestBuilder;

@Component("shStockInfoRequestBuilder")
public class SHStockInfoRequestBuilder extends AbstractRequestBuilder {

	@Override
	public List<Request> getRequests() {
		List<Request> result = new ArrayList<Request>();

		Request request = new Request();
		request.setUrl("http://www.sse.com.cn/market/sseindex/indexlist/s/i000002/const_list.shtml");

		result.add(request);
		return result;
	}
}
