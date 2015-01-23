package com.jia.voa.download;

import java.util.List;

import com.gionee.download.core.DownloadInfo;
import com.gionee.download.manager.DownloadMgr;
import com.gionee.download.manager.DownloadRequest;
import com.gionee.download.manager.IDownloadObserver;
import com.jia.voa.db.ArticleDao;
import com.jia.voa.db.bean.ArticleBean;

import android.os.Environment;
import android.text.TextUtils;

public class Mp3DownloadMgr {

	public static final String SD_PATH =  Environment.getExternalStorageDirectory().getPath();
	public static final String MP3_FOLDER = SD_PATH + "/voa/mp3/";
	private static final String ARTICLE_ID = "articleId";
	
	private static Mp3DownloadMgr instance;

	private Mp3DownloadMgr(){
		DownloadMgr.getInstance().registerObserver(observer);
	}
	
	public static Mp3DownloadMgr getInstance() {
		if (null == instance) {
			instance = new Mp3DownloadMgr();
		}
		return instance;
	}
	
	public void download(ArticleBean articleBean) {
		String filePath = MP3_FOLDER + articleBean.getTitle() + ".mp3";
		DownloadRequest request = new DownloadRequest(articleBean.getMp3Url(),
				articleBean.getTitle(), filePath);
		request.putExtra(ARTICLE_ID, articleBean.getId() + "");
		
		DownloadMgr.getInstance().enqueue(request);
	}

	
	private IDownloadObserver observer = new IDownloadObserver() {

		@Override
		public void onDelete(List<DownloadInfo> arg0) {

		}

		@Override
		public void onProgressChange(List<DownloadInfo> arg0) {

		}

		@Override
		public void onStatusChange(List<DownloadInfo> arg0) {
			for (DownloadInfo info : arg0) {
				if (info.getStatus() == DownloadMgr.STATUS_SUCCESSFUL) {
					String articleIdStr = info.getStringExtra(ARTICLE_ID);
					if (TextUtils.isEmpty(articleIdStr)) {
						continue;
					}
					int articleId = Integer.valueOf(articleIdStr);
					ArticleDao.updateOnDownloadSuccee(articleId, info.getLocalPath());
					DownloadMgr.getInstance().deleteTask(info.getDownId(), false);
				}
			}
		}
	};
}
