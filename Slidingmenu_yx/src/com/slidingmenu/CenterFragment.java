package com.slidingmenu;

import java.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CenterFragment extends Fragment implements OnClickListener {
	private TextView calendar;
	private TextView weath;
	private TextView meseum;
	private TextView news;
	private TextView boya;

	private LinearLayout metro;

	private String number;
	private int day_in_year;
	private TextView car;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mainpage, null);
		findview(view);
		adjustScreen();
		setNowDate();
		setListener();

		return view;
	}

	private void setListener() {
		weath.setOnClickListener(this);
		car.setOnClickListener(this);
		calendar.setOnClickListener(this);
		meseum.setOnClickListener(this);
		news.setOnClickListener(this);
		boya.setOnClickListener(this);
	}

	private void adjustScreen() {
		WindowManager vm = this.getActivity().getWindowManager();
		int width = vm.getDefaultDisplay().getWidth();
		int height = vm.getDefaultDisplay().getHeight();

		if (width == 480) {
			metro.setPadding(35, 35, 35, 35);
		}
	}

	private void findview(View view) {
		weath = (TextView) view.findViewById(R.id.weath);
		calendar = (TextView) view.findViewById(R.id.calendar);
		car = (TextView) view.findViewById(R.id.car);
		meseum = (TextView) view.findViewById(R.id.meseum);
		news = (TextView) view.findViewById(R.id.news);
		boya = (TextView) view.findViewById(R.id.boya);
		metro = (LinearLayout) view.findViewById(R.id.metro);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void setNowDate() {
		Calendar buaa = Calendar.getInstance();

		switch ((int) buaa.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			number = "��";
			break;
		case 2:
			number = "һ";
			break;
		case 3:
			number = "��";
			break;
		case 4:
			number = "��";
			break;
		case 5:
			number = "��";
			break;
		case 6:
			number = "��";
			break;
		case 7:
			number = "��";
			break;

		default:
			break;
		}

		int ttt = ((int) buaa.get(Calendar.DAY_OF_YEAR) - 67) % 7;
		if (ttt == 0) {
			day_in_year = ((int) buaa.get(Calendar.DAY_OF_YEAR) - 67) / 7;
		} else {
			day_in_year = ((int) buaa.get(Calendar.DAY_OF_YEAR) - 67 - ttt) / 7 + 1;
		}
		calendar.setText("��" + day_in_year + "��\n����" + number);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weath:
			((MainActivity) getActivity()).setFragment(15);
			return;
		case R.id.calendar:
			((MainActivity) getActivity()).setFragment(10);
			return;
		case R.id.car:
			((MainActivity) getActivity()).setFragment(11);
			return;
		case R.id.meseum:
			((MainActivity) getActivity()).setFragment(12);
			return;
		case R.id.news:
			((MainActivity) getActivity()).setFragment(13);
			return;
		case R.id.boya:
			((MainActivity) getActivity()).setFragment(14);
			return;

		default:
			break;
		}
	}

}
