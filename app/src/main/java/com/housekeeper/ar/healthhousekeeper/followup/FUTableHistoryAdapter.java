package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.List;
//随访历史适配器
public class FUTableHistoryAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;
	String TAG = "FUTableHistoryAdapter";
	ViewHolder viewHolder;

	public FUTableHistoryAdapter(Context context, List<HashMap<String, String>> data) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_up_table_history_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.time_tv);
			viewHolder.templateTextView = (TextView)convertView.findViewById(R.id.template_tv);
			viewHolder.detailBtn = (Button)convertView.findViewById(R.id.detail_btn);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		Log.i(TAG, "data " + data.toString());
		viewHolder.timeTextView.setText(data.get(position).get("time"));
		viewHolder.templateTextView.setText(data.get(position).get("template"));


		viewHolder.detailBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String template = data.get(position).get("template");
				if(template.equals("糖尿病")){
					Intent intent = new Intent(context,FUTableHistoryDiabetesActivity.class);
					intent.putExtra("time",data.get(position).get("time"));
					intent.putExtra("id",data.get(position).get("id"));
					context.startActivity(intent);

				}else if(template.equals("高血压")){
					Intent intent = new Intent(context,FUTAbleHistoryHypertensionActivity.class);
					intent.putExtra("time",data.get(position).get("time"));
					intent.putExtra("id",data.get(position).get("id"));
					context.startActivity(intent);
				}else if(template.equals("冠心病")){
					Intent intent = new Intent(context,FUTableHistoryCHDActivity.class);
					intent.putExtra("time",data.get(position).get("time"));
					intent.putExtra("id",data.get(position).get("id"));
					context.startActivity(intent);
				}else if(template.equals("脑卒中")){
					Intent intent = new Intent(context,FUTableHistoryStrokeActivity.class);
					intent.putExtra("time",data.get(position).get("time"));
					intent.putExtra("id",data.get(position).get("id"));
					context.startActivity(intent);
				}else if(template.equals("血脂异常")){
					Intent intent = new Intent(context,FUTableHistoryDyslipidemiaActivity.class);
					intent.putExtra("time",data.get(position).get("time"));
					intent.putExtra("id",data.get(position).get("id"));
					context.startActivity(intent);
				}

			}
		});
		return convertView;
	}

	class ViewHolder{

		TextView timeTextView,templateTextView;
		Button detailBtn;


	}


}
