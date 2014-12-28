package com.gary.stock.crawler.pageprocessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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

@Component("szStockInfoPageProcessor")
public class SZStockInfoPageProcessor extends AbstractSpiderPageProcessor {

	@Override
	public void process(Page page) {
		List<StockInfo> stockInfo = new ArrayList<StockInfo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<Selectable> trs = page.getHtml().$("tr:gt(0)").nodes();

		if (CollectionUtils.isNotEmpty(trs)) {
			for (Selectable tr : trs) {
				String stockCode = tr.$("td:eq(0)", "text").get();
				String stockName = tr.$("td:eq(1)", "text").get();
				String stockOpenDate = tr.$("td:eq(7)", "text").get();
				String profession = tr.$("td:eq(18)", "text").get();

				StockInfo s = new StockInfo();
				s.setCode(stockCode);
				s.setName(stockName);
				s.setMarket(CommonConstant.MARKET_SHENZHEN);

				if (StringUtils.isNotEmpty(stockOpenDate)) {
					try {
						s.setOpenDate(sdf.parse(stockOpenDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (StringUtils.isNotEmpty(profession)) {
					s.setProfession(profession.substring(0, 1));
				}

				stockInfo.add(s);
			}
		}

		page.putField(CrawlerConst.RESULT_KEY_SZ_STOCKINFO, stockInfo);
	}

}
