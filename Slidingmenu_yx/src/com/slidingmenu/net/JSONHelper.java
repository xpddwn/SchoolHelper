package com.slidingmenu.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import com.slidingmenu.Util.HttpUtil;

public class JSONHelper {
	/**
	 * 获取远程JSON数据
	 * 
	 * @param url
	 * @return
	 */
	public static String getJson(String url) {
		StringBuilder sb = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
			HttpConnectionParams.setSoTimeout(httpParams, 10000);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			if (response != null) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					reader.close();
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		if (sb != null && sb.length() > 0) {
			return sb.toString();
		} else {
			return null;
		}
	}

	public static JSONArray getJsonArray(String url) {
		try {
			StringBuffer json = new StringBuffer();

			HttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HttpUtil.TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HttpUtil.TIMEOUT);
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);

			if (response != null) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
					String line = null;
					while ((line = reader.readLine()) != null) {
						json.append(line + "/n");
					}
					reader.close();
				}
			}

			return new JSONArray(json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
