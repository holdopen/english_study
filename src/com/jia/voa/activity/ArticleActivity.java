package com.jia.voa.activity;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gionee.download.core.DownloadInfo;
import com.gionee.download.manager.DownloadMgr;
import com.gionee.download.manager.DownloadRequest;
import com.gionee.download.manager.IDownloadObserver;
import com.gionee.download.utils.LogUtils;
import com.jia.voa.R;
import com.jia.voa.db.ArticleDao;
import com.jia.voa.db.bean.ArticleBean;
import com.jia.voa.download.Mp3DownloadMgr;

public class ArticleActivity extends Activity implements OnClickListener {

	private static final int STOPPED = 0;
	private static final int STARTTING = 1;
	private static final int PAUSE = 2;

	private static final String TAG = "ArticleActivity";

	private TextView titleText, contentText;

	private ArticleBean bean;

	private MediaPlayer mMediaPlayer;
	
	private int state = STOPPED;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		titleText = (TextView) findViewById(R.id.text_title);
		contentText = (TextView) findViewById(R.id.text_context);
		bean = (ArticleBean) getIntent().getSerializableExtra("bean");

		titleText.setText(bean.getTitle());
		contentText.setText(Html.fromHtml(bean.getContent()));

		if (false == TextUtils.isEmpty(bean.getMp3Local())) {
			((Button) findViewById(R.id.btn_download)).setEnabled(false);
		}

		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		mMediaPlayer = new MediaPlayer();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_play:
			play();
			break;
		case R.id.btn_pause:
			pause();
			break;
		case R.id.btn_download:
			downlaod();
			break;
		default:
			break;
		}
	}

	private void downlaod() {
		if (TextUtils.isEmpty(bean.getMp3Url())) {
			return;
		}
		Mp3DownloadMgr.getInstance().download(bean);
	}

	private void pause() {
		if (STARTTING == state) {
			mMediaPlayer.pause();
			state = PAUSE;
		} else if (PAUSE == state) {
			mMediaPlayer.start();
			state = STARTTING;
		}
	}

	private void play() {
		if (STARTTING == state) {
			return;
		}
		state = STARTTING;
		try {
			/* 重置多媒体 */
			mMediaPlayer.reset();
			/* 读取mp3文件 */
			LogUtils.logi(TAG, "bean.getState() = " + bean.getState());
			if (bean.getState() == ArticleDao.STATE_COMPLETE) {
				LogUtils.logi(TAG, "bean.getMp3Local() = " + bean.getMp3Local());
				mMediaPlayer.setDataSource(bean.getMp3Local());
			} else {
				LogUtils.logi(TAG, "bean.getMp3Url() = " + bean.getMp3Url());
				mMediaPlayer.setDataSource(bean.getMp3Url());
			}
			/* 准备播放 */
			mMediaPlayer.prepare();
			/* 开始播放 */
			mMediaPlayer.start();
		} catch (IOException e) {
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
