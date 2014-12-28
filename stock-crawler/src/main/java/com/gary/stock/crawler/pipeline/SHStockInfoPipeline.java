package com.gary.stock.crawler.pipeline;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import com.gary.stock.crawler.CrawlerConst;
import com.gary.stock.model.StockInfo;

@Component("shStockInfoPipeline")
public class SHStockInfoPipeline extends StockInfoPipeline {

	@Transactional
	@Override
	public void process(ResultItems resultItems, Task task) {

		List<StockInfo> stockInfoList = resultItems
				.get(CrawlerConst.RESULT_KEY_SZ_STOCKINFO);

		saveOrUpdateStockInfo(stockInfoList);
	}

}
