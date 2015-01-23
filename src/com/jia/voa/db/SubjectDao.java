package com.jia.voa.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.gionee.download.utils.LogUtils;
import com.jia.voa.db.bean.SubjectBean;

public class SubjectDao {

	private static final String TAG = "SubjectDao";

	public static int count() {
		Cursor cursor = DBHelper.getReadableDB().rawQuery(
				"SELECT COUNT(*) FROM " + DBHelper.TABLE_SUBJECT, null);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		cursor.close();
		return count;
	}

	public static void insert(String category, String url, String title) {
		DBHelper.getWritableDB().execSQL(
				"INSERT INTO " + DBHelper.TABLE_SUBJECT + " ("
						+ DBHelper.SUBJ_CATEGORY + ", " 
						+ DBHelper.SUBJ_URL + ", " 
						+ DBHelper.SUBJ_TITLE + ") VALUES (?, ?, ?)",
				new String[] { category, url, title });
		LogUtils.logi(TAG, "insert title = " + title);
	}
	
	public static List<SubjectBean> queryList() {
		List<SubjectBean> list = new ArrayList<SubjectBean>();
		Cursor cursor = DBHelper.getReadableDB().rawQuery(
				"SELECT * FROM " + DBHelper.TABLE_SUBJECT, null);
		if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                SubjectBean bean = createSubjectBean(cursor);
                list.add(bean);
            }
        }
		cursor.close();
		return list;
	}

	private static SubjectBean createSubjectBean(Cursor cursor) {
		SubjectBean bean = new SubjectBean();
		bean.setCategory(cursor.getString(cursor.getColumnIndex(DBHelper.SUBJ_CATEGORY)));
		bean.setEntryCount(cursor.getInt(cursor.getColumnIndex(DBHelper.SUBJ_ENTRY_COUNT)));
		bean.setUrl(cursor.getString(cursor.getColumnIndex(DBHelper.SUBJ_URL)));
		bean.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.SUBJ_ID)));
		bean.setLearnedCount(cursor.getInt(cursor.getColumnIndex(DBHelper.SUBJ_LEARNED)));
		bean.setNewCount(cursor.getInt(cursor.getColumnIndex(DBHelper.SUBJ_NEW)));
		bean.setReadTitle(cursor.getString(cursor.getColumnIndex(DBHelper.SUBJ_READ_TITLE)));
		bean.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.SUBJ_TITLE)));
		bean.setTotal(cursor.getInt(cursor.getColumnIndex(DBHelper.SUBJ_TOTAL)));
		return bean;
	}

	public static void update(int id, int total, int newCount) {
		DBHelper.getWritableDB().execSQL(
				"UPDATE " + DBHelper.TABLE_SUBJECT + " SET "
						+ DBHelper.SUBJ_TOTAL + " = ?, " 
						+ DBHelper.SUBJ_NEW + " = ? WHERE " + DBHelper.SUBJ_ID + " = ?",
				new Object[] { total, newCount, id});
		LogUtils.logi(TAG, "updata id = " + id);
	}
}
