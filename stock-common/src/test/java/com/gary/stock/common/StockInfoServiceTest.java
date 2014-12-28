package com.gary.stock.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gary.stock.model.StockInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class StockInfoServiceTest {
	@Autowired
	IStockInfoService stockInfoService;

	@Test
	public void testFind() {
		for (StockInfo st : stockInfoService.find(null)) {
			System.out.println(st.getId());
		}
	}

	@Test
	public void testFindOne() {
		StockInfo st = stockInfoService.findOne(Criteria.where(
				CommonConstant.CODE_STOCKINFO_COLUMN_NAME).is("000001"));
		Assert.assertEquals(st.getName(), "平安银行");
	}
}
