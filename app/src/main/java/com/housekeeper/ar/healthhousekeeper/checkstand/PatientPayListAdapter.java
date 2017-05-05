package com.housekeeper.ar.healthhousekeeper.checkstand;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientPayListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;
	public static  List<HashMap<String,String>> selectedData = new ArrayList<>();

	String TAG = "PatientPayListAdapter";

	private ToastCommom toastCommom;

	public PatientPayListAdapter(Context context, List<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		toastCommom = ToastCommom.createToastConfig();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_patient_pay_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.idTextView = (TextView) convertView.findViewById(R.id.dingdan_id_tv);
			viewHolder.typeTextView = (TextView)convertView.findViewById(R.id.dingdan_type_tv);
			viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.pay_cb);
			viewHolder.jiageTextView = (TextView)convertView.findViewById(R.id.jiage_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.idTextView.setText(data.get(position).get("id"));
		viewHolder.typeTextView.setText(data.get(position).get("type"));
		viewHolder.jiageTextView.setText(data.get(position).get("jiage"));

		viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.i(TAG,"isChecked "+isChecked);
				Log.i(TAG,"data.get(position) "+data.get(position));
				if(isChecked){
					selectedData.add(data.get(position));
				}else{
					selectedData.remove(data.get(position));
				}
				Log.i(TAG,"selectedData "+selectedData.toString());
			}
		});

		return convertView;
	}


	public List<HashMap<String,String>> getSelectedData(){
		return  selectedData;
	}

	class ViewHolder{
		TextView idTextView,typeTextView,jiageTextView;
		CheckBox checkBox;


	}

}
