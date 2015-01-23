package com.jia.voa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.jia.voa.VoaApplication;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String DB_NAME = "voa_info_db";
	
	//主题表
	public static final String TABLE_SUBJECT = "voa_subject";
	
	public static final String SUBJ_ID = "_id";
	public static final String SUBJ_TITLE = "title";
	public static final String SUBJ_URL = "url";//列表页面url
	public static final String SUBJ_READ_TITLE = "readTitle";//读到的id
	public static final String SUBJ_ENTRY_COUNT = "entryCount";//录入的个数
	public static final String SUBJ_TOTAL = "total";//总数
	public static final String SUBJ_LEARNED = "learnedCount";//学过的个数
	public static final String SUBJ_NEW = "newCount";//新增个数
	public static final String SUBJ_CATEGORY = "category";//分类名字
	public static final String SUBJ_RESERVED = "xxx";//预留字段
	
	//详情表
	public static final String TABLE_ARTICLE= "voa_article";
	
	public static final String DETA_ID = "_id";
	public static final String DETA_SUBJECT = "subjectId";
	public static final String DETA_TITLE = "title";
	public static final String DETA_DATE = "date_";
	public static final String DETA_URL = "url";//详情页面url
	public static final String DETA_CONTENT = "content_";//内容
	public static final String DETA_CONTENT_ZH = "content_zh";//译文内容
	public static final String DETA_MP3_URL = "mp3Url";//mp3 下载地址
	public static final String DETA_MP3_LOCAL = "mp3Local";//mp3 文件路径
	public static final String DETA_LRC_URL = "lrcUrl";//lrc文件下载地址
	public static final String DETA_LRC_LOCAL = "lrcLocal";//lrc文件路径
	public static final String DETA_VIDEO_URL = "videoUrl";//视频文件下载地址
	public static final String DETA_VIDEO_LOCAL = "videoLocal";//视频文件路径
	public static final String DETA_BROWSE_TIMES = "browseTimes";//浏览次数
	public static final String DETA_IS_COLLECTED = "isCollected";//收藏
	public static final String DETA_STATE = "state_";//0初始状态, 1完成数据, 2完成下载
	public static final String DETA_RESERVED = "xxx";//预留字段

	private static DBHelper instance;
	
	public static DBHelper getInstance() {
		if (null == instance) {
			instance = new DBHelper(VoaApplication.getInstance(), DB_NAME, null, 2);
		}
		return instance;
	}
	
	public static SQLiteDatabase getReadableDB(){
		return getInstance().getReadableDatabase();
	}
	
	public static SQLiteDatabase getWritableDB(){
		return getInstance().getWritableDatabase();
	}
	
	private DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context.getApplicationContext(), name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder categorySql = new StringBuilder();
		categorySql.append("create table if not exists ");
		categorySql.append(TABLE_SUBJECT);
		categorySql.append("(").append(SUBJ_ID).append(" integer primary key,");
		categorySql.append(SUBJ_TITLE).append(" varchar,");
		categorySql.append(SUBJ_URL).append(" varchar,");
		categorySql.append(SUBJ_READ_TITLE).append(" varchar,");
		categorySql.append(SUBJ_ENTRY_COUNT).append(" integer,");
		categorySql.append(SUBJ_TOTAL).append(" integer,");
		categorySql.append(SUBJ_LEARNED).append(" integer,");
		categorySql.append(SUBJ_NEW).append(" integer,");
		categorySql.append(SUBJ_CATEGORY).append(" varchar,");
		categorySql.append(SUBJ_RESERVED).append(" TEXT)");
		db.execSQL(categorySql.toString());
		
		StringBuilder detailsSql = new StringBuilder();
		detailsSql.append("create table if not exists ");
		detailsSql.append(TABLE_ARTICLE);
		detailsSql.append("(").append(DETA_ID).append(" integer primary key,");
		detailsSql.append(DETA_SUBJECT).append(" integer,");
		detailsSql.append(DETA_TITLE).append(" varchar,");
		detailsSql.append(DETA_DATE).append(" varchar,");
		detailsSql.append(DETA_URL).append(" varchar,");
		detailsSql.append(DETA_CONTENT).append(" TEXT,");
		detailsSql.append(DETA_CONTENT_ZH).append(" TEXT,");
		detailsSql.append(DETA_MP3_URL).append(" varchar,");
		detailsSql.append(DETA_MP3_LOCAL).append(" varchar,");
		detailsSql.append(DETA_LRC_URL).append(" varchar,");
		detailsSql.append(DETA_LRC_LOCAL).append(" varchar,");
		detailsSql.append(DETA_VIDEO_URL).append(" varchar,");
		detailsSql.append(DETA_VIDEO_LOCAL).append(" varchar,");
		detailsSql.append(DETA_BROWSE_TIMES).append(" integer,");
		detailsSql.append(DETA_IS_COLLECTED).append(" integer,");
		detailsSql.append(DETA_STATE).append(" integer,");
		detailsSql.append(DETA_RESERVED).append(" TEXT)");
		db.execSQL(detailsSql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
}
