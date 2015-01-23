package com.jia.voa.spider;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gionee.download.utils.LogUtils;
import com.jia.voa.db.SubjectDao;
import com.jia.voa.exception.SpiderException;

public class MenuSpider extends Spider {

	private static final String TAG = "MenuSpider";
	
	public final static String ROOT_URL = "http://www.51voa.com/";

	public void start() throws SpiderException {
		if (SubjectDao.count() > 0) {
			return;
		}
		
		Document dom = getDom(ROOT_URL);

		Element menuAll = dom.getElementById("left_nav");

		parseMenu(menuAll);
	}

	private void parseMenu(Element menuAll) {
		int index = 0;
		while (true) {
			Element div = menuAll.child(index++);
			Element ul = menuAll.child(index++);
			if(isBreak(div, ul)){
				break;
			}
			
//			LogUtils.logw(TAG, "div = " + div.html());
			String category = div.select("a").first().text().trim();
			
			if (isDropCategory(category)) {
				break;
			}
			
			if (false == "VOA英语教学".equals(category)) {
				continue;
			}
			
			parseSubject(category, ul);
		}
	}

	private void parseSubject(String category, Element ul) {
		Elements subjects = ul.select("a");
		for (Element link : subjects) {
			String url = link.absUrl("href");
			String title = link.text();
			LogUtils.logi(TAG, "title = " + title);
			SubjectDao.insert(category, url, title);
		}
	}

	private boolean isBreak(Element div, Element ul) {
		return div == null || ul == null
				|| !"div".equals(div.nodeName().trim())
				|| !"ul".equals(ul.nodeName().trim());
	}

	private boolean isDropCategory(String category) {
		return "VOA节目介绍".equals(category) || "友情链接".equals(category);
	}
}
