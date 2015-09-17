package com.slidingmenu.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.slidingmenu.Util.News;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * 异步任务基础类
 * @author yoyo
 *
 */
public class BuaaFragmentTask extends AsyncTask<String, Void, List<News>>{
	private Handler mHandler;
	
	public BuaaFragmentTask(Handler handler) {
		this.mHandler = handler;
	}
	
	@Override
	protected List<News> doInBackground(String... params) {
		List<News> news = new ArrayList<News>();

		try {
			String jsonData = JSONHelper.getJson(params[0]);
			System.out.println(jsonData);
			JSONObject result = new JSONObject(jsonData)
					.getJSONObject("results");
			JSONArray array = result.getJSONArray("collection1");

			if (array != null && array.length() > 0) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject json = array.getJSONObject(i);
					News obj = new News();
					obj.settitle(json.getJSONObject("property1").getString(
							"text"));
					obj.settime(json.getString("property2"));
					obj.setnewsurl(json.getJSONObject("property1")
							.getString("href"));
					news.add(obj);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		return news;
	}
	
	@Override
	protected void onPostExecute(List<News> result) {
		Message msg = new Message();
		msg.obj = result;
		mHandler.sendMessage(msg);
	}
}
