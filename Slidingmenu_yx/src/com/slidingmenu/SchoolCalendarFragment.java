package com.slidingmenu;

import java.io.Console;
import java.util.Calendar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SchoolCalendarFragment extends Fragment{


	private TextView year;
	private TextView month;
	private TextView day;
	private TextView week;
	private TextView weekday;
	private String number;
	private int day_in_year;

	@SuppressWarnings("deprecation")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_calendar_frame, null);
        year = (TextView)view.findViewById(R.id.year);
        month = (TextView)view.findViewById(R.id.month);
        day = (TextView)view.findViewById(R.id.day);
        week = (TextView)view.findViewById(R.id.week);
        weekday = (TextView)view.findViewById(R.id.weekday);
        
        Calendar buaa = Calendar.getInstance();
        year.setText(""+buaa.get(Calendar.YEAR));
        month.setText(""+((int)buaa.get(Calendar.MONTH)+1));
        day.setText(""+buaa.get(Calendar.DAY_OF_MONTH));
        
        switch ((int)buaa.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			number = "天";
			break;
		case 2:
			number = "一";
			break;
		case 3:
			number = "二";
			break;
		case 4:
			number = "三";
			break;
		case 5:
			number = "四";
			break;
		case 6:
			number = "五";
			break;
		case 7:
			number = "六";
			break;

		default:
			break;
		}
        weekday.setText(number);
        
        int ttt = ((int)buaa.get(Calendar.DAY_OF_YEAR)-67)%7;
        if(ttt==0){
        	day_in_year = ((int)buaa.get(Calendar.DAY_OF_YEAR) - 67)/7;
        }
        else{
        	day_in_year = ((int)buaa.get(Calendar.DAY_OF_YEAR) - 67-ttt)/7+1;
        }
        
        week.setText(""+day_in_year);
        
        return view;
    }

	@Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }
}
