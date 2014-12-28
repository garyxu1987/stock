package com.gary.stock.crawler.requestbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.utils.HttpConstant;

import com.gary.stock.crawler.AbstractRequestBuilder;

/**
 * 
 * 通过分析日志补全股价信息
 * 
 * @author gary
 * 
 */
@Component("lackStockPriceRequestBuilder")
public class LackStockPriceRequestBuilder extends AbstractRequestBuilder {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String LOG_FILE_FOLDER = "E:\\logs\\old";
	private static final String DONE_LOG_FILE_FOLDER = "E:\\logs\\done";
	private static final Pattern urlPattern = Pattern.compile("http://[^ ']+");

	@Override
	public List<Request> getRequests() {
		List<Request> result = new ArrayList<Request>();

		File file = new File(LOG_FILE_FOLDER);
		File doneFolder = new File(DONE_LOG_FILE_FOLDER);
		if (!doneFolder.exists()) {
			doneFolder.mkdir();
		}

		File[] files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(".log");
			}
		});

		if (ArrayUtils.isNotEmpty(files)) {
			for (File f : files) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(f));
					String line = null;
					while ((line = br.readLine()) != null) {
						String url = getUrlFromErrorLog(line);
						if (url != null) {
							Request request = new Request();
							request.setMethod(HttpConstant.Method.GET);
							request.setUrl(url);
							result.add(request);
						}
					}
					br.close();
					FileUtils.copyFileToDirectory(f, doneFolder);
					f.delete();
				} catch (Exception e) {
					logger.error("", e);
					try {
						br.close();
					} catch (IOException e1) {
						logger.error("", e1);
					}
				}
			}
		}
		return result;
	}

	private String getUrlFromErrorLog(String line) {
		String url = null;
		if (line.contains("error") && !line.contains("code error 50")) {
			Matcher m = urlPattern.matcher(line);
			if (m.find()) {
				url = m.group();
			}
		}
		return url;
	}

}
