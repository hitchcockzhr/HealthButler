package com.housekeeper.ar.healthhousekeeper.appointmentlist;

/**
 * Created by lenovo on 2016/8/13.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.HashMap;
import java.util.List;

/**
 * 子listview适配器
 * @author mmsx
 *
 */
public class ChildAdapt extends BaseAdapter {

    private List<HashMap<String,String>> data;
    private Context context;
    private  String TAG = "ChildAdapt";
    public ChildAdapt(Context context, List<HashMap<String,String>> datas) {
        this.context = context;
        this.data = datas;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_appointment_childlist_item, null);
            vHolder.numTextView = (TextView)convertView.findViewById(R.id.num_tv);
            vHolder.nameTextView= (TextView)convertView.findViewById(R.id.name_tv);
            vHolder.sexTextView = (TextView)convertView.findViewById(R.id.sex_tv);
            vHolder.ageTextView= (TextView)convertView.findViewById(R.id.age_tv);
            vHolder.idTextView = (TextView)convertView.findViewById(R.id.id_tv);
            vHolder.headerImageView= (RoundImageView)convertView.findViewById(R.id.head_imageview);
            convertView.setTag(vHolder);
        }else {
            vHolder = (ViewHolder) convertView.getTag();
        }

        Log.i(TAG, "data " + data.toString());
        int p = position+1;
        vHolder.numTextView.setText(""+p);
        vHolder.nameTextView.setText(data.get(position).get("name"));
        vHolder.sexTextView.setText(data.get(position).get("sex"));
        vHolder.ageTextView.setText(data.get(position).get("age")+"岁");
        vHolder.idTextView.setText(data.get(position).get("id"));

//        vHolder.headerImageView.setImageDrawable();
        return convertView;
    }

    class ViewHolder{
        TextView nameTextView,numTextView,ageTextView,sexTextView,idTextView;
        ImageView headerImageView;
    }
}

