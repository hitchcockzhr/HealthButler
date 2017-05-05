package com.housekeeper.ar.healthhousekeeper.community;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExistingCommunityAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "ExistingCommunityAdapter";
	private List<HashMap<String,String>> selectedData = new ArrayList<>();



	private int flag = 0;
	private int index = -1;


	public ExistingCommunityAdapter(Context context, List<HashMap<String, String>> data, int flag) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;

		this.flag = flag;


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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_exist_community_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_tv);
			viewHolder.addressTextView = (TextView)convertView.findViewById(R.id.address_tv);

			viewHolder.selectRadioBtn = (RadioButton)convertView.findViewById(R.id.select_radio);
//			viewHolder.okBtn = (Button)convertView.findViewById(R.id.ok_btn);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.addressTextView.setText(data.get(position).get("address"));
		viewHolder.selectRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.i("existCommunity","selectRadioBtn isChecked "+isChecked);
				if (isChecked) {
//					Toast.makeText(context, "您选择的作家是：" + data.get(position).toString(),
//							Toast.LENGTH_LONG).show();
					index = position;
					notifyDataSetChanged();
				}

				if (isChecked) {
					selectedData.add( data.get(position));
				}else {
					selectedData.remove(data.get(position));
				}
			}
		});

		if (index == position) {// 选中的条目和当前的条目是否相等
			viewHolder.selectRadioBtn.setChecked(true);
		} else {
			viewHolder.selectRadioBtn.setChecked(false);
		}



		return convertView;
	}

	public List<HashMap<String,String>> getSelectedData(){
		return  selectedData;
	}

	class ViewHolder{
		RoundImageView headerImage;

		TextView nameTextView,addressTextView;
		Button okBtn;
		RadioButton selectRadioBtn;


	}

}
