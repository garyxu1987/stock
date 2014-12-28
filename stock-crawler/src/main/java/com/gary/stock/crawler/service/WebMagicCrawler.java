package com.gary.stock.crawler.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import com.gary.stock.crawler.AbstractSpiderPageProcessor;
import com.gary.stock.crawler.SpiderConfig;

/**
 * webmagic爬虫的基类,泛化各种爬虫的实现,规范各个爬虫的实现
 * 
 * @author Gary
 * 
 */
@Component
public class WebMagicCrawler {

	public Site initSite(SpiderConfig config) {
		Site site = Site.me().setRetryTimes(config.getRetryTime())
				.setTimeOut(config.getTimeout())
				.setSleepTime(config.getSleepTime())
				.setDomain(config.getBaseUrl()).setCharset(config.getCharset());
		Map<String, String> requestHeader = config.getRequestHeader();
		Map<String, String> requestCookies = config.getRequestCookies();

		if (requestHeader != null) {
			Iterator<String> iterator = requestHeader.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				site.addHeader(key, requestHeader.get(key));
			}
		}
		if (requestCookies != null) {
			Iterator<String> iterator = requestCookies.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				site.addCookie(key, requestCookies.get(key));
			}
		}

		return site;
	}

	public void crawl(SpiderConfig config) {

		List<Request> requests = config.getRequestBuilder().getRequests();

		AbstractSpiderPageProcessor pageProcessor = config.getPageProcessor();
		pageProcessor.setSite(initSite(config));

		Spider.create(pageProcessor).thread(config.getThreadsCount())
				.addRequest(requests.toArray(new Request[requests.size()]))
				.addPipeline(config.getPipeline()).run();
	}

}
