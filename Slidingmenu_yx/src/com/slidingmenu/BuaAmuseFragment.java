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
import com.slidingmenu.view.PullToRefreshLayout;
import com.slidingmenu.view.PullToRefreshLayout.OnRefreshListener;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BuaAmuseFragment extends Fragment implements OnRefreshListener,OnItemClickListener{
	private ListView lv_news;
	private ProgressDialog circleDialog;
	private PullToRefreshLayout perl;
	private int status;
	private int loadmore =0;
	private List<News> loadnews = new ArrayList<News>();
	private Handler freshHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_amuse_frame, null);
		findview(view);
		newHandler();
		setListener();
		perl.autoRefresh();
		return view;
	}

	private void setListener() {
		lv_news.setOnItemClickListener(this);
	}

	private void newHandler() {
		freshHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// 千万别忘了告诉控件刷新完毕了哦！
				dealNewsList(msg.obj);
				perl.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		};
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private void findview(View view){
		perl = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
		perl.setOnRefreshListener(this);
		lv_news = (ListView) view.findViewById(R.id.content_view);
		circleDialog = new ProgressDialog(view.getContext());
		circleDialog.setMessage("loading");
		circleDialog.show();
	}
	
	private void dealNewsList(Object obj) {
		circleDialog.dismiss();
		MyAdapter adapter = null;
		List<News> result = new ArrayList<News>();
		result = (List<News>) obj;
		if (status == 1) {
			loadmore = 0;
			loadnews = result;
			adapter = new MyAdapter((MainActivity) getActivity(), loadnews);
			
		} else if (status == 2) {
			loadmore++;
			loadnews.addAll(result);
			adapter = new MyAdapter((MainActivity) getActivity(), loadnews);
		}

		lv_news.setAdapter(adapter);
		if (status == 2) {
			lv_news.setSelection(adapter.getCount() - 1);
		}
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		status = 1;
		BuaaFragmentTask task = new BuaaFragmentTask(freshHandler);
		task.execute(HttpUtil.baseurl1);
	}

	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		status = 2;
		if(loadmore == 0){
			BuaaFragmentTask task = new BuaaFragmentTask(freshHandler);
			task.execute(HttpUtil.baseurl9);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent webpageIntent = new Intent(
				(MainActivity) getActivity(), NewsPage.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", loadnews.get(position).getnewsurl());
		bundle.putString("name", "amuse");
		webpageIntent.putExtras(bundle);
		startActivity(webpageIntent);
	}
}
