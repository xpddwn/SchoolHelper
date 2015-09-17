package com.slidingmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SchoolWorkFragment extends Fragment{

	private TextView buaamuse;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_work_frame, null);
        buaamuse = (TextView)view.findViewById(R.id.work);
        buaamuse.setText("ÇÚ¹¤¼óÑ§");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }
}
