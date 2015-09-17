package com.slidingmenu.view;

import com.slidingmenu.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SocialButtonLayout extends RelativeLayout {
	
	
	private TextView title;
	private TextView content;
	private ImageView icon;

	public SocialButtonLayout(Context context) {  
        this(context, null);  
    } 
	
	public SocialButtonLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate( R.layout.socialbutton, this, true); 
		title = (TextView)findViewById(R.id.buttontext1);
		content = (TextView)findViewById(R.id.buttontext2);
		icon = (ImageView)findViewById(R.id.imageView2);
	}
  
	
	public ImageView getIcon() {
		return icon;
	}

	public void setIcon(int weiboicon) {
		
		this.icon.setImageResource(weiboicon);
	}

	public void setTitleText(String mytitle)
	{
		title.setText(mytitle);
	}
	
	public void setContentText(String mycontent)
	{
		
		content.setText(mycontent);
	}

	public TextView getTitle() {
		return title;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

	public String getContent() {
		return content.getText().toString();
	}

	public void setContent(TextView content) {
		this.content = content;
	}
	
}
