package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.List;

//服务列表适配器
public class FUTableServiceListAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;
	String TAG = "FUTableServiceListAdapter";
	ViewHolder viewHolder;
	private int index = 0;

	public FUTableServiceListAdapter(Context context, List<HashMap<String, String>> data) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_up_table_visit_service_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.service_name_tv);
			viewHolder.detailTextView = (TextView)convertView.findViewById(R.id.service_detail_tv);
			viewHolder.totleCountTextView = (TextView) convertView.findViewById(R.id.service_total_count_tv);
			viewHolder.addTV = (TextView)convertView.findViewById(R.id.service_add_count_tv);
			viewHolder.subTV = (TextView) convertView.findViewById(R.id.service_sub_count_tv);
			viewHolder.countET = (EditText)convertView.findViewById(R.id.service_count_et);

			viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image_view);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("name"));
		viewHolder.detailTextView.setText(data.get(position).get("detail"));

		String totleCountStr = data.get(position).get("total_count");

		viewHolder.totleCountTextView.setText(totleCountStr+"次");
		viewHolder.countET.setText(data.get(position).get("count"));

		changeCountBtnStatus(data.get(position).get("total_count"), data.get(position).get("count"), position);

		index = position;
		viewHolder.addTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				int count = Integer.valueOf(data.get(position).get("count"));
				count++;
				index = position;
				Log.i(TAG,"addTV position "+position);
				changeCountBtnStatus(data.get(position).get("total_count"),count+"",position);
			}
		});

		viewHolder.subTV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int count = Integer.valueOf(data.get(position).get("count"));
				count--;
				index = position;
				Log.i(TAG,"subTV position "+position);
				changeCountBtnStatus(data.get(position).get("total_count"),count+"",position);
			}
		});

		viewHolder.countET.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				Log.i(TAG,"addTextChangedListener position "+position);
				Log.i(TAG,"addTextChangedListener s "+s);
				Log.i(TAG,"addTextChangedListener index "+index);
				if(s != null && !s.toString().equals("")){
					changeCountBtnStatus(data.get(index).get("total_count"),s.toString(),index);
				}else{
					Toast.makeText(context, "请填写次数", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		return convertView;
	}

	private void changeCountBtnStatus(String totleCountStr,String etCount,int pos){
		Log.i(TAG, "changeCountBtnStatus index " + index);
		Log.i(TAG,"changeCountBtnStatus pos "+pos);
		Log.i(TAG,"changeCountBtnStatus etCount "+etCount);
		Log.i(TAG,"changeCountBtnStatus totleCountStr "+totleCountStr);
		int total = Integer.valueOf(totleCountStr);
		int count = Integer.valueOf(etCount);
		if(count >= total){
			count = total;
			viewHolder.addTV.setClickable(false);
			viewHolder.addTV.setTextColor(context.getResources().getColor(R.color.gray));

			viewHolder.subTV.setClickable(true);
			viewHolder.subTV.setTextColor(context.getResources().getColor(R.color.black));
		}
		if(count <= 0){
			count = 0;
			viewHolder.subTV.setClickable(false);
			viewHolder.subTV.setTextColor(context.getResources().getColor(R.color.gray));

			viewHolder.addTV.setClickable(true);
			viewHolder.addTV.setTextColor(context.getResources().getColor(R.color.black));

		}

		data.get(pos).put("count", "" + count);
		notifyDataSetChanged();
//		viewHolder.countET.setText(""+count);

	}
	class ViewHolder{

		TextView nameTextView,detailTextView,totleCountTextView,addTV,subTV;
		EditText countET;
		ImageView imageView;


	}


}
