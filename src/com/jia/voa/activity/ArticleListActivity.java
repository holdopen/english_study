package com.jia.voa.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jia.voa.R;
import com.jia.voa.db.ArticleDao;
import com.jia.voa.db.bean.ArticleBean;
import com.jia.voa.exception.SpiderException;
import com.jia.voa.spider.ArticleSpider;

public class ArticleListActivity extends Activity {
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			init();
		};
	};
	
	private ListView mListView;
	private ArticleAdapter mAdapter;

	public int subjectId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setOnItemClickListener(listener);
		subjectId = getIntent().getIntExtra("id", -1);
		
		new Thread(){
			public void run() {
				try {
					new ArticleSpider(subjectId).start();
					
					handler.sendEmptyMessage(2);
				} catch (SpiderException e) {
					e.printStackTrace();
				}
				
			};
		}.start();
		
		init();
	}

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ArticleBean bean = mAdapter.getArticleBean(arg2);
			if (bean.getState() == ArticleDao.STATE_INITIAL) {
				return;
			}
			
			Intent in = new Intent(ArticleListActivity.this, ArticleActivity.class);
			in.putExtra("bean", bean);
			ArticleListActivity.this.startActivity(in);
		}
	};
	
	private void init() {
		mAdapter = new ArticleAdapter();
		mListView.setAdapter(mAdapter);
	}

	private class ArticleAdapter extends BaseAdapter{

		private List<ArticleBean> list;
		
		public ArticleAdapter() {
			list = ArticleDao.queryListBySubject(subjectId);
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		public ArticleBean getArticleBean(int position){
			return list.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(ArticleListActivity.this);
			ArticleBean bean = list.get(position);
			textView.setText(bean.getTitle()  + "  <state = " + bean.getState() + ">");
			if (bean.getState() != ArticleDao.STATE_INITIAL) {
				textView.setTextColor(Color.BLACK);
			} else {
				textView.setTextColor(Color.GRAY);
			}
			return textView;
		}
		
	}
	
}
