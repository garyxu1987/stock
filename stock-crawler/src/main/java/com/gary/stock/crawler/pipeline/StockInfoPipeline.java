package com.gary.stock.crawler.pipeline;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.gary.stock.common.IStockInfoService;
import com.gary.stock.model.StockInfo;

public abstract class StockInfoPipeline implements Pipeline {

	@Autowired
	private IStockInfoService stockInfoService;

	@Override
	public abstract void process(ResultItems resultItems, Task task);

	protected void saveOrUpdateStockInfo(List<StockInfo> stockInfoList) {
		if (CollectionUtils.isNotEmpty(stockInfoList)) {
			for (StockInfo stockInfo : stockInfoList) {
				stockInfoService.saveOrUpdate(stockInfo);
			}
		}
	}
}
