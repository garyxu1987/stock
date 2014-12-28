package com.gary.stock.crawler.requestbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class LackStockPriceRequestBuilderTest {

	private static final String LOG_FILE_FOLDER = "E:\\logs\\old";

	@Test
	public void test() {

		File file = new File(LOG_FILE_FOLDER);

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
						// System.out.println(url);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private String getUrlFromErrorLog(String line) {
		String url = null;
		Pattern urlPattern = Pattern.compile("http://[^ ']+");
		if (line.contains("error")) {
			Matcher m = urlPattern.matcher(line);
			if (m.find()) {
				url = m.group();
			}
		}
		return url;
	}

}
