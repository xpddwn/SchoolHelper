package com.slidingmenu.view;

import com.slidingmenu.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


public class ButtonLayout extends RelativeLayout {
	 
	
	private TextView title;
	private ToggleButton content;

	public ButtonLayout(Context context) {  
        this(context, null);  
    } 
	
	public ButtonLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.settingbutton, this, true); 
		title = (TextView)findViewById(R.id.buttontext1);
		content = (ToggleButton)findViewById(R.id.buttontext2);
		
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
}
