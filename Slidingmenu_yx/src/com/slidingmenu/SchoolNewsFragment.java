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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SchoolNewsFragment extends Fragment implements OnRefreshListener,
		OnItemClickListener {
	private ListView lv_news;
	private ProgressDialog circleDialog;
	private PullToRefreshLayout perl;
	private int status = 1;
	private int loadmore = 0;
	private List<News> loadnews = new ArrayList<News>();
	private Handler mHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_1frame, null);
		findview(view);
		setListener();
		newHandler();
		perl.autoRefresh();

		return view;
	}

	private void setListener() {
		lv_news.setOnItemClickListener(this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void findview(View view) {
		perl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
		perl.setOnRefreshListener(this);
		lv_news = (ListView) view.findViewById(R.id.content_view);
		circleDialog = new ProgressDialog(view.getContext());
		circleDialog.setMessage("loading");
		circleDialog.show();
	}

	private void newHandler() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 千万别忘了告诉控件刷新完毕了哦！
				perl.refreshFinish(PullToRefreshLayout.SUCCEED);
				dealNewsList(msg.obj);
			}
		};
	}

	private void dealNewsList(Object obj) {
		circleDialog.dismiss();
		List<News> result = new ArrayList<News>();
		result = (List<News>) obj;
		MyAdapter adapter = null;
		if (status == 1) {
			loadmore = 0;
			adapter = new MyAdapter((MainActivity) getActivity(), result);
			loadnews = result;
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

	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		status = 1;
		BuaaFragmentTask task = new BuaaFragmentTask(mHandler);
		task.execute("https://www.kimonolabs.com/api/cy0aq10w?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6");
	}

	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		status = 2;
		BuaaFragmentTask task = new BuaaFragmentTask(mHandler);
		switch (loadmore) {
		case 0:
			task.execute(HttpUtil.baseurl6);
			break;
		case 1:
			task.execute(HttpUtil.baseurl7);
			break;
		case 2:
			task.execute(HttpUtil.baseurl8);
			Toast.makeText((MainActivity) getActivity(), "加载完成，后面没有了", 2000)
					.show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent webpageIntent = new Intent((MainActivity) getActivity(),
				NewsPage.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", loadnews.get(position).getnewsurl());
		bundle.putString("name", "news");
		webpageIntent.putExtras(bundle);
		startActivity(webpageIntent);
	}
}
