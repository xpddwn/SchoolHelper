package com.slidingmenu;

import java.util.Random;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RightFragment extends Fragment
{
    private TextView list;
    private TextView title;
    private TextView writer;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.right, null);
        list = (TextView) view.findViewById(R.id.lv_right);
        writer = (TextView)view.findViewById(R.id.lv_writer);
        title = (TextView) view.findViewById(R.id.lv_right_title);
        
        title.setText("每日一句");
        
        int num = (int)(Math.random()*60+1);
        Resources res = getResources();
        String [] saying = res.getStringArray(R.array.saying);
        String [] writer2 = res.getStringArray(R.array.writer);
              
        list.setText(saying[num]);
        writer.setText("－－－" + writer2[num]);
        
        return view;
    }

}
