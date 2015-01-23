package com.jia.voa.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.gionee.download.utils.LogUtils;
import com.jia.voa.db.bean.ArticleBean;

public class ArticleDao {

	private static final String TAG = "DetailsDao";
	
	public static final int STATE_INITIAL = 0;//初始
	public static final int STATE_INFO_READY = 1;//信息准备就绪
	public static final int STATE_COMPLETE = 2;//完成(下载完成)

	public static void insert(int subject, String title, String url,
			String date) {
		DBHelper.getWritableDB().execSQL(
				"INSERT INTO " + DBHelper.TABLE_ARTICLE + " ("
						+ DBHelper.DETA_SUBJECT + ", " 
						+ DBHelper.DETA_TITLE+ ", " 
						+ DBHelper.DETA_URL + ", " 
						+ DBHelper.DETA_STATE + ", " 
						+ DBHelper.DETA_DATE + ") VALUES (?, ?, ?, ?, ?)",
				new Object[] { subject, title, url, 0, date});
		LogUtils.logi(TAG, "insert title = " + title);
	}

public static List<ArticleBean> queryListBySubject(int subjectId) {
		
		Cursor cursor = DBHelper.getReadableDB().rawQuery("SELECT * FROM " 
				+ DBHelper.TABLE_ARTICLE + " WHERE "
				+ DBHelper.DETA_SUBJECT + " = " + subjectId, null);

		return cursor2List(cursor);
	}
	
	public static List<ArticleBean> queryListBySubjectAndState(int subjectId, int state) {
		Cursor cursor = DBHelper.getReadableDB().rawQuery(
				"SELECT * FROM " + DBHelper.TABLE_ARTICLE + " WHERE "
						+ DBHelper.DETA_SUBJECT + " = " + subjectId + " AND "
						+ DBHelper.DETA_STATE + " = " + state, null);
		return cursor2List(cursor);
	}

	private static List<ArticleBean> cursor2List(Cursor cursor) {
		List<ArticleBean> list = new ArrayList<ArticleBean>();
		if (cursor != null && cursor.getCount() > 0) {
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				ArticleBean bean = createDetailBean(cursor);
				list.add(bean);
			}
		}
		cursor.close();
		return list;
	}

	private static ArticleBean createDetailBean(Cursor cursor) {
		ArticleBean bean = new ArticleBean();
		bean.setBrowseTimes(cursor.getInt(cursor.getColumnIndex(DBHelper.DETA_BROWSE_TIMES)));
		bean.setContent(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_CONTENT)));
		bean.setContentZh(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_CONTENT_ZH)));
		bean.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_DATE)));
		bean.setUrl(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_URL)));
		bean.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.DETA_ID)));
		bean.setCollected(cursor.getInt(cursor.getColumnIndex(DBHelper.DETA_IS_COLLECTED)) == 1);
		bean.setLrcLocal(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_LRC_LOCAL)));
		bean.setLrcUrl(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_LRC_URL)));
		bean.setMp3Local(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_MP3_LOCAL)));
		bean.setMp3Url(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_MP3_URL)));
		bean.setState(cursor.getInt(cursor.getColumnIndex(DBHelper.DETA_STATE)));
		bean.setSubjectId(cursor.getInt(cursor.getColumnIndex(DBHelper.DETA_SUBJECT)));
		bean.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_TITLE)));
		bean.setVideoLocal(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_VIDEO_LOCAL)));
		bean.setVideoUrl(cursor.getString(cursor.getColumnIndex(DBHelper.DETA_VIDEO_URL)));
		return bean;
	}

	public static void updateOnDownloadSuccee(int articleId, String filePath) {
		DBHelper.getWritableDB().execSQL(
				"UPDATE " + DBHelper.TABLE_ARTICLE + " SET "
						+ DBHelper.DETA_MP3_LOCAL + " = ?, "
						+ DBHelper.DETA_STATE + " = ? WHERE " + DBHelper.DETA_ID + " = ?",
				new Object[] { filePath
						, STATE_COMPLETE
						, articleId});
	}
	
	public static void update4InfoReady(ArticleBean bean) {
		DBHelper.getWritableDB().execSQL(
				"UPDATE " + DBHelper.TABLE_ARTICLE + " SET "
						+ DBHelper.DETA_CONTENT + " = ?, " 
						+ DBHelper.DETA_CONTENT_ZH + " = ?, "
						+ DBHelper.DETA_LRC_URL + " = ?, "
						+ DBHelper.DETA_MP3_URL + " = ?, "
						+ DBHelper.DETA_STATE + " = ?, "
						+ DBHelper.DETA_VIDEO_URL + " = ? WHERE " + DBHelper.DETA_ID + " = ?",
				new Object[] { bean.getContent()
						, bean.getContentZh()
						, bean.getLrcUrl()
						, bean.getMp3Url()
						, bean.getState()
						, bean.getVideoUrl()
						, bean.getId()});
	}

	public static boolean queryIsExist(String url) {
		Cursor cursor = DBHelper.getReadableDB().rawQuery(
				"SELECT * FROM " + DBHelper.TABLE_ARTICLE + " WHERE "
						+ DBHelper.DETA_URL + " = ?", new String[]{url} );
		boolean isHas =  cursor.getCount() > 0;
		cursor.close();
		return isHas;
	}

}
