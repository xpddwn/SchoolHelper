package com.slidingmenu;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SchoolCarFragemnt extends Fragment{


	private TextView time_count1;
	private TextView time_count2;
	private String time1 = "无车";
	private String time2;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_car_frame, null);
        time_count1 = (TextView)view.findViewById(R.id.time_count1);
        time_count2 = (TextView)view.findViewById(R.id.time_count2);
        
        Calendar tf = Calendar.getInstance();
        int week = (int)tf.get(Calendar.DAY_OF_WEEK);
        Time tr = new Time();
        tr.setToNow();
        
        int hour = tr.hour;
        int minute = tr.minute;
        int totalminute;
        totalminute = hour*60+minute;
        
        if(week>1&&week<7){
        	if(totalminute<435){
            	int reduce;
            	reduce = 435 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=435&&totalminute<550){
            	int reduce;
            	reduce = 550 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=550&&totalminute<750){
            	int reduce;
            	reduce = 750 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=750&&totalminute<870){
            	int reduce;
            	reduce = 870 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=750&&totalminute<870){
            	int reduce;
            	reduce = 870 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=870&&totalminute<1010){
            	int reduce;
            	reduce = 1010 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
        }
        else{
        	if(totalminute<510){
            	int reduce;
            	reduce = 510 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
        	else if(totalminute>=510&&totalminute<750){
            	int reduce;
            	reduce = 750 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
        	else if(totalminute>=750&&totalminute<990){
            	int reduce;
            	reduce = 990 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
        }
        time_count1.setText(time1);
        
        
        if(week>1&&week<7){
        	if(totalminute<620){
            	int reduce;
            	reduce = 620 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=620&&totalminute<780){
            	int reduce;
            	reduce = 780 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=780&&totalminute<940){
            	int reduce;
            	reduce = 940 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=940&&totalminute<1000){
            	int reduce;
            	reduce = 1000 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=1000&&totalminute<1060){
            	int reduce;
            	reduce = 1060 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
            else if(totalminute>=1060&&totalminute<1250){
            	int reduce;
            	reduce = 1250 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
        }
        else{
        	if(totalminute<570){
            	int reduce;
            	reduce = 570 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
        	else if(totalminute>=570&&totalminute<810){
            	int reduce;
            	reduce = 810 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
        	else if(totalminute>=810&&totalminute<1050){
            	int reduce;
            	reduce = 1050 - totalminute;
            	if(reduce<60){
            		time1 = reduce+"分";
            	}
            	else if(reduce==60){
            		time1 = "1时";
            	}
            	else{
            		time1 = (reduce/60)+"时"+(reduce%60)+"分";
            	}
            }
        }
        time_count2.setText(time1);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }

}
