package com.housekeeper.ar.healthhousekeeper.guidance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.FlowLayout;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyDialog;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
//新处方
public class NewPrescriptionActivity extends Activity {


    String TAG = "NewPrescriptionActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private ImageView searchImageView;
    private EditText searchEditView;
    private FlowLayout newMedicineFlowLayout;
    private Button okBtn;
    private Button cancelBtn;
    private ListView searchMedicineListView;

    private List<HashMap<String,String>> newMedicineLists = new ArrayList<>();
    //被选中新处方列表，用于添加到新处方列表//标记是否被选择，初始化false
    private ArrayList<HashMap<String,String>> selectedMedicineLists =  new ArrayList<HashMap<String, String>>();

    //搜索的处方列表，用于添加到新处方列表//标记是否被选择，初始化false
    private ArrayList<HashMap<String,String>> searchMedicineLists =  new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(NewPrescriptionActivity.this);

        searchEditView = (EditText)findViewById(R.id.search_edittext);
        searchImageView = (ImageView)findViewById(R.id.search_image_view);

        newMedicineFlowLayout = (FlowLayout)findViewById(R.id.new_medicine_flowlayout);
        okBtn = (Button)findViewById(R.id.ok_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);


        searchImageView.setOnClickListener(clickListener);
        okBtn.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);


    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search_image_view:

                    search();
                    break;
                case R.id.ok_btn:

                    if(newMedicineLists != null && !newMedicineLists.isEmpty()){

                        for(HashMap<String,String>map:newMedicineLists){
                            map.put("selected","false");
                        }
//					selectedHistoryMedicineLists.addAll(oneSelectedHistoryMedicineLists);

                        Bundle bundle=new Bundle();

                        bundle.putParcelableArrayList("list", (ArrayList)newMedicineLists);
                        PrescriptionTab  mTabMainActivity = (PrescriptionTab) getParent();
                        Intent mIntent = getIntent();
                        mIntent.putExtras(bundle);
                        mTabMainActivity.setResult(RESULT_OK, mIntent);

                        finish();
                    }else{
//					Toast.makeText(HistoryMedicineActivity.this,"请选择处方",Toast.LENGTH_LONG).show();
                        toastCommom.ToastShow(NewPrescriptionActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择处方");
                    }

                    break;

                case R.id.cancel_btn:

                    finish();
                    break;


            }
        }
    };

    private void search(){
        if(searchEditView.getText().toString().equals("")){
            toastCommom.ToastShow(NewPrescriptionActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "搜索内容不能为空");
            return;
        }
        searchMedicineDialog();


    }


    //打开搜索对话框
    private void searchMedicineDialog(){

        LayoutInflater inflater =getLayoutInflater();
        final View functionListView = inflater.inflate(R.layout.search_medicine_dialog, null);
//		dialog = new AlertDialog.Builder(DiagnosisActivity.this, R.style.load_dialog).setView(functionListView).show();

        final MyDialog dialog = new MyDialog(NewPrescriptionActivity.this, functionListView,R.style.load_dialog);
        int width = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
//		int height = getResources().getDimensionPixelSize(R.dimen.schedule_set_width);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();


        Button dialogCancelBtn = (Button)dialog.findViewById(R.id.cancel_btn);
        Button dialogOkBtn = (Button)functionListView.findViewById(R.id.ok_btn);
        searchMedicineListView = (ListView)functionListView.findViewById(R.id.new_medicine_listview);

        showMedicineList(searchEditView.getText().toString());

        dialogOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedMedicineLists == null ||selectedMedicineLists.isEmpty()){
                    toastCommom.ToastShow(NewPrescriptionActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请选择");
                    return;
                }

                for (HashMap<String,String> map:selectedMedicineLists){

                    map.put("name",map.get("name"));
                    map.put("dj", map.get("dj"));
                    map.put("drugId",map.get("drugId"));
                    map.put("product", map.get("product"));
                    map.put("guige",map.get("guige"));
                    map.put("selected","false");

                    newMedicineLists.add(map);

                }
                initNewMedicineChildViews();
                dialog.dismiss();

            }
        });
        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

    }

    //初始化新增处方的流式布局
    private void initNewMedicineChildViews() {
        // TODO Auto-generated method stub

        if(newMedicineFlowLayout.getChildCount() > 0){
            newMedicineFlowLayout.removeAllViews();
        }

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        if(newMedicineLists == null || newMedicineLists.isEmpty()){
            return;
        }
//		Log.i(TAG, "initHistoryDateChildViews selectedDate " + selectedDate);
        for(int i = 0; i < newMedicineLists.size(); i ++){
            final TextView view = new TextView(this);
            final HashMap<String,String> result = newMedicineLists.get(i);
            final int ii = i;
            view.setText(result.get("name"));
            Log.i(TAG, "initHistoryDateChildViews result" + result);
            view.setTextSize(12);
            view.setTextColor(Color.BLACK);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_flow_shape_unpressed));


            newMedicineFlowLayout.addView(view, lp);
        }
    }

    SearchMedicinesItemBaseAdapter searchMedicinesItemBaseAdapter;
    //根据药品获取内容
    private void showMedicineList(String yaopin){

        searchMedicinesItemBaseAdapter = new SearchMedicinesItemBaseAdapter(NewPrescriptionActivity.this,
                searchMedicineLists, R.layout.search_medicine_dialog_item,
                new String[]{"name","product","guige","layout"},
                new int[]{R.id.medicine_name_item, R.id.medicine_product_name_item,R.id.medicine_guige_item,R.id.search_medicine_dialog_item_layout});
        searchMedicineListView.setAdapter(searchMedicinesItemBaseAdapter);

        ////////测试///////////////////////////////////////////////////////////////////////////////
        Toast.makeText(NewPrescriptionActivity.this,"测试数据",Toast.LENGTH_LONG).show();
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("dj", "15");
//        map.put("drugId","1");
//        map.put("name","测试");
//        map.put("guige","3ml/袋");
//        map.put("product","商品名测试");
//        map.put("selected", "false");
//        searchMedicineLists.add(map);
//
//
//        HashMap<String, String> map2 = new HashMap<String, String>();
//        map2.put("dj", "5");
//        map2.put("drugId","2");
//        map2.put("name","药品测试2");
//        map2.put("guige","5ml/袋");
//        map2.put("product","商品名测试2");
//        map2.put("selected", "false");
//        searchMedicineLists.add(map2);

        HashMap<String, String> map3 = new HashMap<String, String>();
        map3.put("dj", "5");
        map3.put("drugId","3");
        map3.put("name","头孢");
        map3.put("guige","5ml/袋");
        map3.put("product","商品名测试3");
        map3.put("selected", "false");
        searchMedicineLists.add(map3);

        /////////////////////////////////////////////////////////////////////////////////////////

        searchMedicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final HashMap<String, String> map = (HashMap<String, String>) adapterView.getItemAtPosition(i);

                if (map.get("selected").equals("false")) {
                    map.put("selected", "true");
                    selectedMedicineLists.add(map);
                } else {
                    selectedMedicineLists.remove(map);
                    map.put("selected", "false");
                }


                searchMedicinesItemBaseAdapter.notifyDataSetChanged();
            }
        });

    }

    class SearchMedicinesItemBaseAdapter extends BaseAdapter {
        private class ButtonViewHolder{
            TextView drugName;
            TextView productName;
            TextView guigeTV;
            LinearLayout layout;
        }

        private ArrayList<HashMap<String, String>> mAppList;
        private LayoutInflater mInflater;
        private Context mContext;
        private String[] keyString;
        private int[] valueViewID;
        private ButtonViewHolder holder;
        //private OnHDListener mCallBack;
        public SearchMedicinesItemBaseAdapter(Context c,
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
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView != null) {
                holder = (ButtonViewHolder) convertView.getTag();
            } else {
                convertView = mInflater.inflate(R.layout.search_medicine_dialog_item, null);
                holder = new ButtonViewHolder();
                holder.drugName = (TextView)convertView.findViewById(valueViewID[0]);
                holder.productName = (TextView)convertView.findViewById(valueViewID[1]);
                holder.guigeTV = (TextView)convertView.findViewById(valueViewID[2]);
                holder.layout = (LinearLayout)convertView.findViewById(valueViewID[3]);
                convertView.setTag(holder);
            }

            HashMap<String, String> appInfo = mAppList.get(position);
            if (appInfo != null) {
//				String aname = (String) appInfo.get(keyString[0]);
//				holder.drugName.setText(aname);
//                String drugJO = mAppList.get(position).get("drugJO");
//                try {
//                    JSONObject obj = new JSONObject(drugJO);
                    holder.drugName.setText(appInfo.get("name"));
                    //TODO 商品名目前没有字段，是bxlx吗？
                    holder.productName.setText("商品名测试");
//					holder.productName.setText(obj.getString("bxlx"));
                    holder.guigeTV.setText(appInfo.get("guige"));

                    if(appInfo.get("selected").equals("true")){

                        //如果选中则将字体变成白色，背景变成绿色
                        holder.layout.setBackgroundColor(getResources().getColor(R.color.background_green));
                        holder.productName.setTextColor(getResources().getColor(R.color.white));
                        holder.drugName.setTextColor(getResources().getColor(R.color.white));
                        holder.guigeTV.setTextColor(getResources().getColor(R.color.white));

                    }else{
                        holder.layout.setBackgroundColor(getResources().getColor(R.color.white));
                        holder.productName.setTextColor(getResources().getColor(R.color.black));
                        holder.drugName.setTextColor(getResources().getColor(R.color.black));
                        holder.guigeTV.setTextColor(getResources().getColor(R.color.black));
                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


            }
            return convertView;
        }


    }


}
