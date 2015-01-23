package com.jia.voa.spider;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gionee.download.utils.LogUtils;
import com.jia.voa.db.ArticleDao;
import com.jia.voa.db.bean.ArticleBean;
import com.jia.voa.exception.SpiderException;

public class ArticleSpider extends Spider {

	private static final String TAG = "DetailsSpider";
	private int subjectId;

	public ArticleSpider(int subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public void start() throws SpiderException {
		List<ArticleBean> list = ArticleDao.queryListBySubjectAndState(
				subjectId, ArticleDao.STATE_INITIAL);
		for (ArticleBean bean : list) {
			LogUtils.logi(TAG, "title = " + bean.getTitle());
			parseDetail(bean);
		}
	}

	private void parseDetail(ArticleBean bean) throws SpiderException {
		Document dom = getDom(bean.getUrl());

		parseMp3Url(bean, dom);

		LogUtils.logi(TAG, "title = " + bean.getMp3Url());
		
		parseLrcUrl(bean, dom);

		parseContent(bean, dom);

		bean.setState(ArticleDao.STATE_INFO_READY);
		
		ArticleDao.update4InfoReady(bean);
		
		LogUtils.logw(TAG, "completed ; ");
	}

	private void parseMp3Url(ArticleBean bean, Element menubarEle) {
		Element mp3 = menubarEle.getElementById("mp3");
		if (null == mp3) {
			return;
		}
		bean.setMp3Url(mp3.absUrl("href"));
	}

	private void parseLrcUrl(ArticleBean bean, Element menubarEle) {
		Element lrcEle = menubarEle.getElementById("lrc");
		if (null == lrcEle) {
			return;
		}
		bean.setLrcUrl(lrcEle.absUrl("href"));
	}

	private void parseContent(ArticleBean bean, Element menubarEle)
			throws SpiderException {
		Element contentEle = menubarEle.getElementById("content");
		if (null == contentEle) {
			return;
		}
		bean.setContent(contentEle.html());

		// 解析译文
		Element contentZhUrlEle = menubarEle.getElementById("EnPage");
		if (null == contentZhUrlEle) {
			return;
		}
		String contentZhUrl = contentZhUrlEle.absUrl("href");
		Document domZh = getDom(contentZhUrl);
		Element contentZhEle = domZh.getElementById("content");
		bean.setContent(contentZhEle.html());
	}

}
