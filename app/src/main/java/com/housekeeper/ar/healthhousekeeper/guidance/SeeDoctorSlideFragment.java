package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;

public class SeeDoctorSlideFragment extends Fragment {
	String TAG = "SlideFragment";
	private View view;
	public TextView diquTextView, yiyuanTextView, keshiTextView, yishengTextView;
	public Button yuyueButton, jysmButton, kanhuayanButton;
	String diquString, yiyuanString, keshiString, yishengString;
	MyApp myApp;
	ProgressDialog pDialog;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_see_doctor_fragment, null);
		myApp = (MyApp)getActivity().getApplication();
		initView();
		return view;
	}

	public void initView(){
		diquTextView = (TextView)view.findViewById(R.id.diqu_tv);
		yiyuanTextView = (TextView)view.findViewById(R.id.yiyuan_tv);
		keshiTextView = (TextView)view.findViewById(R.id.keshi_tv);
		yishengTextView = (TextView)view.findViewById(R.id.yisheng_tv);
		yuyueButton = (Button)view.findViewById(R.id.yuyue_btn);
//		jysmButton = (Button)view.findViewById(R.id.jysm_btn);
//		kanhuayanButton = (Button)view.findViewById(R.id.kanhuayan_btn);
		diquTextView.setClickable(true);
		yiyuanTextView.setClickable(true);
		keshiTextView.setClickable(true);
		yishengTextView.setClickable(true);
		diquString = myApp.getDiquString();
		yiyuanString = myApp.getYiyuanString();
		keshiString = myApp.getKeshiString();
		yishengString = myApp.getYishengString();
		Log.v(TAG, "diquString:" + myApp.getDiquString());
		if(diquString != null){
			diquTextView.setText(diquString);
		}
		if(yiyuanString!= null){
			yiyuanTextView.setText(yiyuanString);
		}
		if(keshiString!= null){
			keshiTextView.setText(keshiString);
		}
		if(yishengString!= null){
			yishengTextView.setText(yishengString);
		}

		diquTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((SeeDoctorActivity)getActivity()).openRightLayout("地区");
				//diquTextView.setText(myApp.getDiquString());
			}
		});
		yiyuanTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((SeeDoctorActivity)getActivity()).openRightLayout("Yiyuan");
			}
		});
		keshiTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((SeeDoctorActivity)getActivity()).openRightLayout("KeShi");
			}
		});
		yishengTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((SeeDoctorActivity)getActivity()).openRightLayout("YiSheng");
			}
		});
		yuyueButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				((SeeDoctorActivity)getActivity()).yuyue();
			}
		});
//		jysmButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				((MainActivity)getActivity()).myJysm();
//			}
//		});
//		kanhuayanButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				((MainActivity)getActivity()).myKanHuaYan();
//			}
//		});
	}
}
