package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.List;

public class MCPatientTabPerMCListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "MCPatientTabPerMCListAdapter";

	MyApp myApp;

	public MCPatientTabPerMCListAdapter(Context context, MyApp myApp, List<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		this.myApp = myApp;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public void refresh(List<HashMap<String,String>> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_membership_patient_tab_perpatient_mctype_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.hospitalTextView = (TextView) convertView.findViewById(R.id.hospital_tv);
			viewHolder.mctypeTextView = (TextView)convertView.findViewById(R.id.mctype_name_tv);
			viewHolder.balanceTextView = (TextView)convertView.findViewById(R.id.ue_amount_tv);
			viewHolder.dateTextView = (TextView)convertView.findViewById(R.id.expiry_date_tv);


			//续订
			viewHolder.reSubBtn = (Button)convertView.findViewById(R.id.xuding_btn);
			//退订
			viewHolder.unSubBtn = (Button)convertView.findViewById(R.id.tuiding_btn);



			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG,"data "+ data.toString());
		viewHolder.hospitalTextView.setText(data.get(position).get("hospital"));
		viewHolder.mctypeTextView.setText(data.get(position).get("mctype"));
		viewHolder.balanceTextView.setText(data.get(position).get("balance"));
		viewHolder.dateTextView.setText(data.get(position).get("date"));

		viewHolder.reSubBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,MCHospitalRechargeActivity.class);
				intent.putExtra("type",data.get(position).get("mctype"));
//				intent.putExtra("rules",data.get(position).get("rules"));
//				intent.putExtra("discount",data.get(position).get("discount"));

				MCPatientTabPerMCActivity activity  = (MCPatientTabPerMCActivity)context;
				activity.startActivity(intent);




			}
		});

		viewHolder.unSubBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				Intent intent = new Intent(context,MCPatientTabUnSubActivity.class);
				Bundle bundle = new Bundle();

				bundle.putString("name", data.get(position).get("name"));
				bundle.putString("sex",data.get(position).get("sex"));
				bundle.putString("age",data.get(position).get("age"));
				bundle.putString("tel", data.get(position).get("tel"));
				bundle.putString("hospital",data.get(position).get("hospital"));
				bundle.putString("mctype",data.get(position).get("mctype"));

				intent.putExtras(bundle);
				context.startActivity(intent);


			}
		});




		return convertView;
	}

	class ViewHolder{
		TextView hospitalTextView,mctypeTextView,dateTextView, balanceTextView;
		Button reSubBtn,unSubBtn;

	}

}
