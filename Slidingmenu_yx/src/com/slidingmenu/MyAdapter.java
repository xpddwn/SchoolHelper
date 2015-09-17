package com.slidingmenu;

import java.util.List;

import com.slidingmenu.Util.News;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	List<News> items;
	Context context;
	private ViewHolder holder;
	private int index = 0;

	public MyAdapter(Context context, List<News> items) {
		this.context = context;
		this.items = items;
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_items, null);
			if(position == 0&&index==0)
			{
				convertView.setBackgroundResource(R.drawable.front_index);
				index++;
			}
			holder = new ViewHolder();
			holder.txTitle = (TextView) convertView
					.findViewById(R.id.newstitle);
			holder.txDesc = (TextView) convertView
					.findViewById(R.id.newstime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final News news = items.get(position);
		holder.txTitle.setText(news.gettitle());
		holder.txDesc.setText(news.gettime());
		return convertView;
	}
	class ViewHolder {
		public TextView txTitle;
		public TextView txDesc;
	}

}
