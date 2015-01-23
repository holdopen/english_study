package com.jia.voa.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jia.voa.R;
import com.jia.voa.db.SubjectDao;
import com.jia.voa.db.bean.SubjectBean;
import com.jia.voa.exception.SpiderException;
import com.jia.voa.spider.CheckUpdatesSpider;
import com.jia.voa.spider.MenuSpider;

public class MainActivity extends Activity {
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			init();
		};
	};
	
	private ListView mListView;
	private SubjectAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setOnItemClickListener(listener);
		
		new Thread(){
			public void run() {
				try {
					new MenuSpider().start();
					
					handler.sendEmptyMessage(1);
					
					new CheckUpdatesSpider().start();
					
					handler.sendEmptyMessage(2);
				} catch (SpiderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			};
		}.start();
	}
	
	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			int clickItemId = mAdapter.getSubjectItem(arg2).getId();
			Intent in = new Intent();
			in.putExtra("id", clickItemId);
			in.setClass(MainActivity.this, ArticleListActivity.class);
			MainActivity.this.startActivity(in);
		}
		
	};

	private void init() {
		mAdapter = new SubjectAdapter();
		mListView.setAdapter(mAdapter);
	}

	private class SubjectAdapter extends BaseAdapter{

		private List<SubjectBean> list;
		
		public SubjectAdapter() {
			list = SubjectDao.queryList();
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
		
		public SubjectBean getSubjectItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return list.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(MainActivity.this);
			SubjectBean bean = list.get(position);
			textView.setText(bean.getTitle() + "   " + bean.getEntryCount() + "/" +bean.getTotal());
			return textView;
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
