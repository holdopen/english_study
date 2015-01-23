package com.jia.voa;

import com.gionee.download.manager.DownloadMgr;

import android.app.Application;

public class VoaApplication extends Application {

	private static VoaApplication instance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		DownloadMgr.getInstance().init(this);
		DownloadMgr.Setting.setMaxDownloadTask(4);
	}

	public static VoaApplication getInstance() {
		return instance;
	}
	
}
