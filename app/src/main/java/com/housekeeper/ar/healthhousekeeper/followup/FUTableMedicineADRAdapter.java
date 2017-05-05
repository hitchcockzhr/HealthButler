package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;

import java.util.HashMap;
import java.util.List;

//不良反应适配器
public class FUTableMedicineADRAdapter extends BaseAdapter {
	private Context context;
	public List<HashMap<String,String>> data;

	String TAG = "FUTableMedicineADRAdapter";
	EditText et1,et2,et3;
	TextView unitTV;
	ViewHolder viewHolder;
	int index=0;
	public FUTableMedicineADRAdapter(Context context, List<HashMap<String, String>> data) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_follow_up_table_medicine_adr_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.medicine_name);
			viewHolder.deleteBtn = (Button)convertView.findViewById(R.id.delete_btn);

			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		index = position;
		Log.i(TAG, "data " + data.toString());
		viewHolder.nameTextView.setText(data.get(position).get("drugName"));
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<String,String> map = data.get(position);
				data.remove(map);
				notifyDataSetChanged();
			}
		});



		return convertView;
	}




	class ViewHolder{

		TextView nameTextView;

		Button deleteBtn;


	}

}
