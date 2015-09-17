package com.slidingmenu.Util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Xml;
import android.widget.Toast;


public class WeatherService {

	private Context context;

	public WeatherService(Context context) {
		this.context = context;
	}

	public List<Weather> getCity() {
		List<Weather> list = null;
		Weather city = null;
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			
			url = new URL("http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0");
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				list = new ArrayList<Weather>();
				in = conn.getInputStream();
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(in, "utf-8");
				int type = parser.getEventType();

				while (type != parser.END_DOCUMENT) {

					if (type == parser.START_TAG) {
						if ("Weather".equals(parser.getName())) {
							city = new Weather();
							String status1 = parser.getAttributeValue(1);
							city.setstatedetail(status1);
							String lowTem = parser.getAttributeValue(10);
							city.setlowTem(lowTem);
							
							String highTem = parser.getAttributeValue(9);
							city.sethighTem(highTem);
						}
					}

					if (type == parser.END_TAG) {
						if ("Weather".equals(parser.getName())) {
							list.add(city);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "获取网络连接超时",1).show();
		}
		return list;
	}
}
