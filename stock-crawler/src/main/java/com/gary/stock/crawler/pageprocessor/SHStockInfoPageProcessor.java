package com.gary.stock.crawler.pageprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import com.gary.stock.common.CommonConstant;
import com.gary.stock.crawler.AbstractSpiderPageProcessor;
import com.gary.stock.crawler.CrawlerConst;
import com.gary.stock.model.StockInfo;

/**
 * 
 * 获取股票信息
 * 
 * @author Gary
 * 
 */

@Component("shStockInfoPageProcessor")
public class SHStockInfoPageProcessor extends AbstractSpiderPageProcessor {
	static Pattern stockInfoPattern = Pattern
			.compile("[^\"]*\"([^,]+),([^\"]*)\";");

	@Override
	public void process(Page page) {

		List<Selectable> tds = page.getHtml().$(".tablestyle td").nodes();
		List<StockInfo> stockInfo = new ArrayList<StockInfo>();

		for (Selectable td : tds) {
			String text = td.$("a", "text").get();
			if (StringUtils.isNotEmpty(text)) {
				String stockName = text.split("\\(")[0].trim();
				String stockCode = text.split("\\(")[1].replaceAll("\\)", "")
						.trim();

				StockInfo st = new StockInfo();
				st.setCode(stockCode);
				st.setName(stockName);
				st.setMarket(CommonConstant.MARKET_SHANGHAI);

				stockInfo.add(st);
			}
		}

		page.putField(CrawlerConst.RESULT_KEY_SZ_STOCKINFO, stockInfo);
	}

}
