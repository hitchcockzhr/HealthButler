package com.housekeeper.ar.healthhousekeeper.appointmentlist;

/**
 * Created by lenovo on 2016/8/13.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.RoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 父listview适配器
 * @author mmsx
 *
 */
public class ParentAdapt extends BaseAdapter {

    private List<HashMap<String,String>> mChildData;
    private List<HashMap<String,String>> mParentData;
    public static int mParentItem = -1;
    public static boolean mbShowChild = false;
    private Context context;

    private String TAG = "ParentAdapt";
    public ParentAdapt(Context context, List<HashMap<String,String>> datas) {
        this.context = context;
        mParentData = datas;
        initChildData();
    }

    //初始化第二级listview的数据,获取医生下的所有预约病人
    private void initChildData() {
        mChildData  = new ArrayList<>();
        mChildData.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("name","李晓红");
        map.put("sex", "女");
        map.put("age", "56");
        map.put("id", "1832285515");

        mChildData.add(map);

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","王娅艳");
        map2.put("sex","女");
        map2.put("age", "66");
        map2.put("id", "1832285515");

        mChildData.add(map2);

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name","张继科");
        map3.put("sex","男");
        map3.put("age","30");
        map3.put("id", "1832285515");

        mChildData.add(map3);

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name","周天");
        map4.put("sex","男");
        map4.put("age","36");
        map4.put("id", "1832285515");

        mChildData.add(map4);

        HashMap<String,String> map5 = new HashMap<>();
        map5.put("name","马龙");
        map5.put("sex","男");
        map5.put("age","35");
        map5.put("id","1832285515");

        mChildData.add(map5);

    }

    @Override
    public int getCount() {
        return mParentData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_appointment_parentlist_item, null);
            vHolder.nameTextView = (TextView)convertView.findViewById(R.id.name_tv);
            vHolder.hospitalTextView= (TextView)convertView.findViewById(R.id.hospital_tv);
            vHolder.keshiTextView = (TextView)convertView.findViewById(R.id.keshi_tv);
            vHolder.zhichengTextView= (TextView)convertView.findViewById(R.id.zhicheng_tv);

            vHolder.listViewItem = (ChildListView)convertView.findViewById(R.id.patient_listview);
            vHolder.buttonStake = (Button)convertView.findViewById(R.id.button_1);
            vHolder.headderImageView = (RoundImageView)convertView.findViewById(R.id.head_imageview);
            convertView.setTag(vHolder);
        }else {
            vHolder = (ViewHolder) convertView.getTag();
        }

        vHolder.nameTextView.setText(mParentData.get(position).get("name"));
        vHolder.hospitalTextView.setText(mParentData.get(position).get("hospital"));
        vHolder.keshiTextView.setText(mParentData.get(position).get("keshi"));
        vHolder.zhichengTextView.setText(mParentData.get(position).get("zhicheng"));

        //点击那个弹出那个，如果已经弹出就收回子listview
        Log.i(TAG, "医生列表mParentItem " + mParentItem);
        Log.i(TAG, "医生列表position " + position);
        Log.i(TAG, "医生列表mbShowChild " + mbShowChild);

//        if (mParentItem == position && mbShowChild) {

//            //子listview实在这里加载数据的
            ChildAdapt tempAdapt = new ChildAdapt(context, mChildData);
            vHolder.listViewItem.setAdapter(tempAdapt);
            vHolder.listViewItem.setVisibility(View.VISIBLE);

            //子listview的点击监听
            vHolder.listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context, "ChildListview" + String.valueOf(position), Toast.LENGTH_SHORT).show();
                }
            });

//        }else {
//            vHolder.listViewItem.setVisibility(View.GONE);
//        }

//        if (mParentItem == position && mbShowChild) {
//
//            vHolder.listViewItem.setVisibility(View.GONE);
//
//        }else  if (mParentItem == position && !mbShowChild){
//
//            //子listview实在这里加载数据的
//            ChildAdapt tempAdapt = new ChildAdapt(context, mChildData);
//            vHolder.listViewItem.setAdapter(tempAdapt);
//            vHolder.listViewItem.setVisibility(View.VISIBLE);
//
//            //子listview的点击监听
//            vHolder.listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(context, "ChildListview" + String.valueOf(position), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

        vHolder.buttonStake.setOnClickListener(new ParentButtonLisener());
        //记住是那个button事件
        vHolder.buttonStake.setTag(position);

        return convertView;
    }

    class ViewHolder{
        TextView nameTextView,hospitalTextView,keshiTextView,zhichengTextView;
        ImageView headderImageView;
        ListView listViewItem;
        Button buttonStake;
    }

    //父listview的button的监听
    private class ParentButtonLisener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Integer nPosition = (Integer) (v.getTag());
            Toast.makeText(context, "提示" + String.valueOf(nPosition.intValue()), Toast.LENGTH_SHORT).show();
        }
    }
}




