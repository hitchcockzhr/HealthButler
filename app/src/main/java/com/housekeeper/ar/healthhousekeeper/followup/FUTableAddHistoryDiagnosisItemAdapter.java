package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FUTableAddHistoryDiagnosisItemAdapter extends BaseAdapter{
	private class ButtonViewHolder{
		TextView diagnosisNameTextView = null;
		Button cancelBtn = null;
		FrameLayout layout;
	}

	private ArrayList<HashMap<String, String>> mAppList;
    private LayoutInflater mInflater;
    private Context mContext;
    private String[] keyString;
    private int[] valueViewID;
    private ButtonViewHolder holder;

    public FUTableAddHistoryDiagnosisItemAdapter(Context c,
												 ArrayList<HashMap<String, String>> appList,
												 int resource,
												 String[] from, int[] to) {
        mAppList = appList;
        mContext = c;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        keyString = new String[from.length];
        valueViewID = new int[to.length];
        System.arraycopy(from, 0, keyString, 0, from.length);
        System.arraycopy(to, 0, valueViewID, 0, to.length);
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAppList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public void refresh( ArrayList<HashMap<String, String>> mAppList)   {
		this.mAppList = mAppList;
		Log.v("CDIBA", mAppList.toString());
		notifyDataSetChanged();
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 if (convertView != null) {
	            holder = (ButtonViewHolder) convertView.getTag();
	        } else {
	            convertView = mInflater.inflate(R.layout.activity_follow_up_table_add_history_diagnosis_item, null);
	            holder = new ButtonViewHolder();
	            holder.diagnosisNameTextView = (TextView)convertView.findViewById(valueViewID[0]);
	            holder.cancelBtn = (Button)convertView.findViewById(valueViewID[1]);
			    holder.layout = (FrameLayout)convertView.findViewById(valueViewID[2]);
	            convertView.setTag(holder);
	        }
		//String name = mAppList.get(position).get("name");
		//String cancelStr = mAppList.get(position).get("cancelBtn");
		//HashMap<String, String> appInfo = mAppList.get(position);
        if (mAppList != null) {
            String aname = (String) mAppList.get(position).get("diagnose");
            Log.v("CDIBA", "aname:"+aname);
            if(holder.diagnosisNameTextView != null)
            	holder.diagnosisNameTextView.setText(aname);
            if(holder.cancelBtn != null)
            	holder.cancelBtn.setOnClickListener(new CancelButtonListener(position));
//			if(position%2==0){
//               holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.light_gray));
//			}else if(position%2!=0){
//				holder.layout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//			}
        }        
        return convertView;
	}
	class CancelButtonListener implements OnClickListener {
        private int position;

        CancelButtonListener(int pos) {
            position = pos;
        }
        
        @Override
        public void onClick(View v) {
        	 removeItem(position);  
            	
        }
    }
	public void removeItem(int position){  
        mAppList.remove(position);  
        this.notifyDataSetChanged();  
    } 

}
