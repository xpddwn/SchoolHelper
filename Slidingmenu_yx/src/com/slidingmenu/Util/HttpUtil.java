package com.slidingmenu.Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtil {
    private static     AsyncHttpClient client =new AsyncHttpClient();   
    public static String baseurl = "https://www.kimonolabs.com/api/cy0aq10w?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6";
    public static String baseurl1 = "https://www.kimonolabs.com/api/ajg4os9a?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6";
    public static String baseurl2 = "https://www.kimonolabs.com/api/amjiqg6s?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6";
    public static String baseurl3 = "https://www.kimonolabs.com/api/cz12oq9q?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6";
    
    /* ??·å??å¥?å­???????è¡?api */
    public static String baseurl4 = "http://tmp.xsc32.longdol.com//api/generic/match/list/";
    
    /* ??¡å??æ¶????ç¬?2é¡?~ç¬?né¡? */
    public static String baseurl6 ="https://www.kimonolabs.com/api/3dhx1eyu?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6";
    public static String baseurl7 = "https://www.kimonolabs.com/api/6e7ga8ay?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6";
    public static String baseurl8 ="https://www.kimonolabs.com/api/89uajhk2?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6";
    
    /* ???å¨±ç??2é¡?~ç¬?né¡? */
    public static String baseurl9 ="https://www.kimonolabs.com/api/2cujtce2?apikey=hzHqbO5pYeybMcCCpQh1jQAGiMQhRDw6";
    
    /* ??»å??api */
    public static String baseurl5 = "http://tmp.xsc32.longdol.com/api/login/";
    public static final int TIMEOUT = 200000;
    
    static
    {
        client.setTimeout(10000);  
    }
    public static void get(String tag,RequestParams params,AsyncHttpResponseHandler res)  
    {
    	if(tag.equalsIgnoreCase("0")){
    		client.get(baseurl, params,res);
    	}
    	else if(tag.equalsIgnoreCase("1")){
    		client.get(baseurl1, params,res);
    	}
    	else if(tag.equalsIgnoreCase("2")){
    		client.get(baseurl2, params,res);
    	}else if(tag.equalsIgnoreCase("5")){
    		client.get(baseurl5, res);
    	}
    }
    public static void get(String urlString,RequestParams params,JsonHttpResponseHandler res)   
    {
        client.get(urlString, params,res);
    }
    public static void get(String uString, BinaryHttpResponseHandler bHandler)   
    {
        client.get(uString, bHandler);
    }
    public static AsyncHttpClient getClient()
    {
        return client;
    }
    public static void post(RequestParams params,JsonHttpResponseHandler res) 
    {
    	client.post(baseurl5, params, res);
    }
	public static void post(RequestParams params,
			AsyncHttpResponseHandler res) {
		// TODO Auto-generated method stub
		client.post(baseurl, params, res);
	}
}