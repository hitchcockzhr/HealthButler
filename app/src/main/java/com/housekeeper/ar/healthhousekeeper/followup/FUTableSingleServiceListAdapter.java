package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.List;

//单项服务列表适配器
public class FUTableSingleServiceListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;
	String TAG = "FUTableSingleServiceListAdapter";
	ViewHolder viewHolder;
	private int index = 0;

	public FUTableSingleServiceListAdapter(Context context, List<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;




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

		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_up_table_single_service_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.service_name_tv);
			viewHolder.priceTextView = (TextView)convertView.findViewById(R.id.service_price_tv);
			viewHolder.addTV = (TextView)convertView.findViewById(R.id.service_add_count_tv);
			viewHolder.subTV = (TextView) convertView.findViewById(R.id.service_sub_count_tv);
			viewHolder.countET = (EditText)convertView.findViewById(R.id.service_count_et);


			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.priceTextView.setText(data.get(position).get("price"));

		viewHolder.countET.setText(data.get(position).get("count"));

		changeCountBtnStatus(data.get(position).get("count"), position);

		index = position;
		viewHolder.addTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				int count = Integer.valueOf(data.get(position).get("count"));
				count++;
				index = position;
				Log.i(TAG,"addTV position "+position);
				changeCountBtnStatus(count+"",position);
			}
		});

		viewHolder.subTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int count = Integer.valueOf(data.get(position).get("count"));
				count--;
				index = position;
				Log.i(TAG,"subTV position "+position);
				changeCountBtnStatus(count+"",position);
			}
		});

//		viewHolder.countET.addTextChangedListener(new TextWatcher() {
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//				Log.i(TAG,"addTextChangedListener position "+position);
//				Log.i(TAG,"addTextChangedListener s "+s);
//				Log.i(TAG,"addTextChangedListener index "+index);
//				if(s != null && !s.toString().equals("")){
//					changeCountBtnStatus(s.toString(),index);
//				}else{
//					Toast.makeText(context, "请填写次数", Toast.LENGTH_SHORT).show();
//				}
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//
//			}
//		});

		return convertView;
	}

	private void changeCountBtnStatus(String etCount,int pos){
		Log.i(TAG, "changeCountBtnStatus index " + index);
		Log.i(TAG,"changeCountBtnStatus pos "+pos);
		Log.i(TAG,"addTV changeCountBtnStatus etCount "+etCount);

		int count = Integer.valueOf(etCount);

		if(count <= 0){
			count = 0;
			viewHolder.subTV.setClickable(false);
			viewHolder.subTV.setTextColor(context.getResources().getColor(R.color.gray));

			viewHolder.addTV.setClickable(true);
			viewHolder.addTV.setTextColor(context.getResources().getColor(R.color.black));

		}else{
			viewHolder.subTV.setClickable(true);
			viewHolder.subTV.setClickable(true);
			viewHolder.subTV.setTextColor(context.getResources().getColor(R.color.black));
		}

		data.get(pos).put("count", "" + count);
		notifyDataSetChanged();
//		viewHolder.countET.setText(""+count);

	}
	class ViewHolder{

		TextView nameTextView,priceTextView,addTV,subTV;
		EditText countET;


	}


}
