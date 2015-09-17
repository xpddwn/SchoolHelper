package com.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.slidingmenu.ScholarFragment.NewsFragmentAdapter.ViewHolder;
import com.slidingmenu.Util.News;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AwardDetail extends Activity{
	private ImageView back;
	private ListView lv_news;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.award_detail);
		
		lv_news = (ListView)findViewById(R.id.lv_news);
		AwardListAdapter mAdapter = new AwardListAdapter(this);
		lv_news.setAdapter(mAdapter);
		
		back = (ImageView)findViewById(R.id.iv_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AwardDetail.this.finish();
			}
		});
	}
	
	 private ArrayList<HashMap<String, Object>> getData(){
		    
		    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();
		    /*为动态数组添加数据*/     
		    for(int i=0;i<3;i++)  
		         {  
		    		 if(i==1){
		    			 HashMap<String, Object> map = new HashMap<String, Object>();  
			             map.put("scholar_name", "国家奖学金");  
			             listItem.add(map); 
		    		 }
		    		 else if(i==2){
		    			 HashMap<String, Object> map = new HashMap<String, Object>();  
			             map.put("scholar_name", "科技实践奖");  
			             listItem.add(map);
		    		 }
		    		 else{
		    			 HashMap<String, Object> map = new HashMap<String, Object>();  
			             map.put("scholar_name", "学习优秀奖");  
			             listItem.add(map);
		    		 }
		         } 
		        return listItem;
		    
		    }
	
	/**
	 * 自定义Adapter显示数据
	 * 
	 * @author yoyo
	 * 
	 */
	class AwardListAdapter extends BaseAdapter {
		private ViewHolder holder;
		private LayoutInflater mInflater;
		
		public AwardListAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return getData().size();
		}

		@Override
		public Object getItem(int position) {
			return getData().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.awardlist_items, null);
				
				holder = new ViewHolder();
				holder.txTitle = (TextView) convertView.findViewById(R.id.scholar_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.txTitle.setText(getData().get(position).get("scholar_name").toString());
			
			return convertView;
		}

		class ViewHolder {
			public TextView txTitle;
		}
	}
}
