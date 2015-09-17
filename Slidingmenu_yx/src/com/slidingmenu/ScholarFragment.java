package com.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.slidingmenu.Util.HttpUtil;
import com.slidingmenu.Util.News;
import com.slidingmenu.net.BuaaFragmentTask;
import com.slidingmenu.net.JSONHelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ScholarFragment extends Fragment{

	private ListView lv_news;
	private ProgressDialog circleDialog;
	private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_scholar_frame, null);
        lv_news = (ListView) view.findViewById(R.id.lv_news);
        circleDialog = new ProgressDialog(view.getContext());
        circleDialog.setMessage("loading");
        circleDialog.show();
        newHandler();
        
        BuaaFragmentTask task = new BuaaFragmentTask(mHandler);
		task.execute(HttpUtil.baseurl3);
        
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
	
	/**
	 * 异步获取数据并在主UI进程中绑定
	 * 
	 * @author yoyo
	 */
	private void newHandler(){
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				dealNewsList(msg.obj);
			}
		};
	}
	
	private void dealNewsList(Object obj) {
		List<News> result = new ArrayList<News>();
		result = (List<News>) obj;
		NewsFragmentAdapter adapter = new NewsFragmentAdapter(result);
		lv_news.setAdapter(adapter);
		mHandler.sendEmptyMessage(0);
	}

	/**
	 * 自定义Adapter显示数据
	 * 
	 * @author yoyo
	 * 
	 */
	class NewsFragmentAdapter extends BaseAdapter {
		private List<News> data;
		private ViewHolder holder;

		private boolean mBusy = false;
		public void setFlagBusy(boolean busy) {
			this.mBusy = busy;
		}
		
		public NewsFragmentAdapter(List<News> data) {
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.wawrdlist_items, null);
				
				holder = new ViewHolder();
				holder.txTitle = (TextView) convertView.findViewById(R.id.newstitle);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			final News news = data.get(position);
			holder.txTitle.setText(news.gettitle());
			
			holder.txTitle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent newsIntent = new Intent((MainActivity)getActivity(),NewsPage.class);
					Bundle bundle = new Bundle();
					bundle.putString("url", news.getnewsurl());
					bundle.putString("name", "award");
					newsIntent.putExtras(bundle);
					startActivity(newsIntent);
				}
			});
			
			return convertView;
		}

		class ViewHolder {
			public TextView txTitle;
		}
	}

}
