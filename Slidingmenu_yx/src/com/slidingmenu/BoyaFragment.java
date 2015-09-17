package com.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import com.slidingmenu.Util.HttpUtil;
import com.slidingmenu.Util.News;
import com.slidingmenu.net.BuaaFragmentTask;
import com.slidingmenu.view.PullToRefreshLayout;
import com.slidingmenu.view.PullToRefreshLayout.OnRefreshListener;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class BoyaFragment extends Fragment implements OnRefreshListener,
		OnItemClickListener {
	private ListView lv_news;
	private ProgressDialog circleDialog;
	private PullToRefreshLayout perl;
	private int status = 1;
	private List<News> loadnews = new ArrayList<News>();
	private Handler mHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lsit_frame, null);
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
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				perl.refreshFinish(PullToRefreshLayout.SUCCEED);
				dealNewsList(msg.obj);
			}
		};
	}

	private void findview(View view) {
		perl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		perl.setOnRefreshListener(this);
		lv_news = (ListView) view.findViewById(R.id.content_view);
		circleDialog = new ProgressDialog(view.getContext());
		circleDialog.setMessage("loading");
		circleDialog.show();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	private void dealNewsList(Object obj){
		circleDialog.dismiss();
		MyAdapter adapter = null;
		List<News> result = new ArrayList<News>();
		result = (List<News>) obj;
		if (status == 1) {
			adapter = new MyAdapter((MainActivity) getActivity(), result);
			loadnews = result;
		} else if (status == 2) {
			loadnews.addAll(result);
			adapter = new MyAdapter((MainActivity) getActivity(), loadnews);
		}

		lv_news.setAdapter(adapter);
		if (status == 2) {
			lv_news.setSelection(adapter.getCount() - 1);
		}
	}

	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		status = 1;
		BuaaFragmentTask task = new BuaaFragmentTask(mHandler);
		task.execute(HttpUtil.baseurl2);
	}

	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		status = 2;
		BuaaFragmentTask task = new BuaaFragmentTask(mHandler);
		task.execute(HttpUtil.baseurl2);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent webpageIntent = new Intent(
				(MainActivity) getActivity(), NewsPage.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", loadnews.get(position).getnewsurl());
		bundle.putString("name", "boya");
		webpageIntent.putExtras(bundle);
		startActivity(webpageIntent);
	}

}
