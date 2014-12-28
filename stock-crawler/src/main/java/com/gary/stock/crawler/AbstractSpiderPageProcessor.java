package com.gary.stock.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public abstract class AbstractSpiderPageProcessor implements PageProcessor {
	Site site;

	public void setSite(Site site) {
		this.site = site;
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public abstract void process(Page arg0);

}
