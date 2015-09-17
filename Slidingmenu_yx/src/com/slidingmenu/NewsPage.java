package com.slidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsPage extends Activity{
	private WebView newspage;
	private String url;
	private String name;
	private TextView title_bar;
	private ImageView back;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newspage);
		Bundle bundle = this.getIntent().getExtras();
		url = bundle.getString("url");
		name = bundle.getString("name");
		
		title_bar = (TextView) findViewById(R.id.title_bar_name);
		if(name.equalsIgnoreCase("news")){
			title_bar.setText("校园资讯");
		}
		else if(name.equalsIgnoreCase("boya")){
			title_bar.setText("博雅课堂");
		}
		else if(name.equalsIgnoreCase("amuse")){
			title_bar.setText("文娱北航");
		}
		else if(name.equalsIgnoreCase("award")){
			title_bar.setText("奖学金管理");
		}
		else if(name.equalsIgnoreCase("stipend")){
			title_bar.setText("助学金管理");
		}
		else{
			title_bar.setText("校园资讯");
		}
		
		back = (ImageView)findViewById(R.id.iv_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NewsPage.this.finish();
			}
		});
		
		
		newspage = (WebView) findViewById(R.id.newspage);
		newspage.loadUrl(url);
		newspage.getSettings().setAppCacheEnabled(true);
		newspage.getSettings().setJavaScriptEnabled(true); 
		newspage.getSettings().setSupportZoom(true); 
		newspage.getSettings().setBuiltInZoomControls(false);
		newspage.getSettings().setUseWideViewPort(true);
		newspage.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		newspage.getSettings().setLoadWithOverviewMode(true);
		
		newspage.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	             view.loadUrl(url);
	            return true;
	        }
	       });
	    }
}
