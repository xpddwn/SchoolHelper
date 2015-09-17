package com.slidingmenu.Util;

public class News {

	private String title;
	private String newsurl;
	private String time;
	
	
	public void settitle(String title){
		this.title = title;
	}
	
	public String gettitle()
	{
		return title;
	}
	
	public void setnewsurl(String url){
		this.newsurl = url;
	}
	
	public String getnewsurl()
	{
		return newsurl;
	}
	
	public void settime(String time)
	{
		this.time = time;
	}
	
	public String gettime(){
		return time;
	}

}
