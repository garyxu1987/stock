package com.gary.stock.crawler;

import java.util.List;

import us.codecraft.webmagic.Request;

/**
 * 生成url请求的参数
 * 
 * @author Gary
 * 
 */
public abstract class AbstractRequestBuilder {
	public abstract List<Request> getRequests();
}
