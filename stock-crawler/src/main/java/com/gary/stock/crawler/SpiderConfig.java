package com.gary.stock.crawler;

import java.util.Map;

import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 爬虫的配置信息
 * 
 * @author Gary
 * 
 */
public class SpiderConfig {

	// 抓取的地址
	private String baseUrl;
	// 请求参数生成器
	private AbstractRequestBuilder requestBuilder;
	// 页面处理器
	private AbstractSpiderPageProcessor pageProcessor;
	// 结果输出器
	private Pipeline pipeline;
	// 请求失败后重试次数
	private int retryTime = 3;
	// 请求间休眠时间
	private int sleepTime = 1000;
	// 线程数
	private int threadsCount = 1;
	// 超时
	private int timeout = 30000;
	// 字符集
	private String charset = "utf8";
	// 请求头信息
	private Map<String, String> requestHeader;
	// 请求附带的cookie
	private Map<String, String> requestCookies;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public AbstractRequestBuilder getRequestBuilder() {
		return requestBuilder;
	}

	public void setRequestBuilder(AbstractRequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	public AbstractSpiderPageProcessor getPageProcessor() {
		return pageProcessor;
	}

	public void setPageProcessor(AbstractSpiderPageProcessor pageProcessor) {
		this.pageProcessor = pageProcessor;
	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	public void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	public int getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(int retryTime) {
		this.retryTime = retryTime;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int getThreadsCount() {
		return threadsCount;
	}

	public void setThreadsCount(int threadsCount) {
		this.threadsCount = threadsCount;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Map<String, String> getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(Map<String, String> requestHeader) {
		this.requestHeader = requestHeader;
	}

	public Map<String, String> getRequestCookies() {
		return requestCookies;
	}

	public void setRequestCookies(Map<String, String> requestCookies) {
		this.requestCookies = requestCookies;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
