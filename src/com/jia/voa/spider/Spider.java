package com.jia.voa.spider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.jia.voa.exception.SpiderException;

public abstract class Spider {

	public abstract void start() throws SpiderException;
	
	protected Document getDom(String url) throws SpiderException {
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new SpiderException("MenuSpider:获取document异常!", e);
		}
	}

}
