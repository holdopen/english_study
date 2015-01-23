package com.jia.voa.spider;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gionee.download.utils.LogUtils;
import com.jia.voa.db.ArticleDao;
import com.jia.voa.db.SubjectDao;
import com.jia.voa.db.bean.SubjectBean;
import com.jia.voa.exception.SpiderException;

public class CheckUpdatesSpider extends Spider {
	
	private static final String TAG = "Check4UpdatesSpider";

	@Override
	public void start() throws SpiderException{
		List<SubjectBean> subjectList = SubjectDao.queryList();
		for (SubjectBean bean : subjectList) {
			LogUtils.logd(TAG, "url = " + bean.getUrl());
			Document dom = getDom(bean.getUrl());
			
			int total = parseTotle(dom);
			if (total == bean.getTotal()) {
				continue;
			}
			int newCount = total - bean.getTotal();
			bean.setNewCount(newCount);
			bean.setTotal(total);
			
			parseDetailsList(bean.getId(), dom);
			
			SubjectDao.update(bean.getId(), bean.getTotal(), bean.getNewCount());
		}
	}

	private int parseTotle(Document dom) {
		Element pagelist = dom.getElementById("pagelist");
		
		int total = getNewTotal(pagelist);
		return total;
	}

	private void parseDetailsList(int subject, Document dom) {
		Element list = dom.getElementById("list");
		Elements listLi = list.select("li");
		
		for (Element li : listLi) {
			String date = li.text();
			Element link = li.select("a").first();
			String url = link.absUrl("href");
			
			if(ArticleDao.queryIsExist(url)){
				return;
			}
			
			String title = link.text();
			
			ArticleDao.insert(subject, title, url, date);
		}
	}

	private int getNewTotal(Element pagelist) {
		int newTotal = Integer.valueOf(pagelist.select("b").last().text().trim());
		return newTotal;
	}

}
