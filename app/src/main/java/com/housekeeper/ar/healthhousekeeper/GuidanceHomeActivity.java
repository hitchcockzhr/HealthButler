package com.housekeeper.ar.healthhousekeeper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.appointmentlist.AppointmentListActivity;
import com.housekeeper.ar.healthhousekeeper.autoregpatientlist.AutoRegPatientListActivity;
import com.housekeeper.ar.healthhousekeeper.checkstand.CheckStandTab;
import com.housekeeper.ar.healthhousekeeper.community.AddCommmunityTab;
import com.housekeeper.ar.healthhousekeeper.guidance.GuidanceActivity;
import com.housekeeper.ar.healthhousekeeper.packages.PackagesActivity;
import com.housekeeper.ar.healthhousekeeper.personalinfo.PersonalInfoActivity;
import com.housekeeper.ar.healthhousekeeper.sign.SignHomeActivity;
import com.housekeeper.ar.healthhousekeeper.teamlist.TeamListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2016/3/5.
 */
public class GuidanceHomeActivity extends Activity {


    String TAG = "GuidanceHomeActivity";
    private TextView moreBtn;
    private ListView communityListView;
    private ListView hospitalListView;
    private TextView communityMoreBtn;
    private CommunityListAdapter communityListAdapter;
    private List<HashMap<String,String>> communityData = new ArrayList<HashMap<String,String>>();

    private List<HashMap<String,String>> hospitalData = new ArrayList<HashMap<String,String>>();
    private CommunityListAdapter hospitalListAdapter;

    private PopupWindow mPopupWindow;

    private LinearLayout guidanceLayout;
    private LinearLayout registerLayout;
    private ImageView headerImage;
    private ToastCommom toastCommom;
    private TextView qianyueTextView;

    private int ADD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance_home);


        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(GuidanceHomeActivity.this);

        moreBtn = (TextView)findViewById(R.id.more_btn);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPopupWindowInstance();

                mPopupWindow.showAsDropDown(view, view.getWidth()
                        - mPopupWindow.getWidth() - 10, 10);
            }
        });
        communityMoreBtn = (TextView)findViewById(R.id.community_more_btn);
        communityMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GuidanceHomeActivity.this, AddCommmunityTab.class);

                startActivityForResult(intent, ADD);
            }
        });

        communityListView = (ListView)findViewById(R.id.community_listview);
        communityListAdapter = new CommunityListAdapter(GuidanceHomeActivity.this,communityData);
        communityListView.setAdapter(communityListAdapter);



        hospitalListView = (ListView)findViewById(R.id.hospital_listview);
        hospitalListAdapter = new CommunityListAdapter(GuidanceHomeActivity.this,hospitalData);
        hospitalListView.setAdapter(hospitalListAdapter);

        registerLayout = (LinearLayout)findViewById(R.id.register_layout);
        guidanceLayout = (LinearLayout)findViewById(R.id.guidance_layout);

        qianyueTextView = (TextView)findViewById(R.id.qianyue_btn);
        qianyueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuidanceHomeActivity.this,SignHomeActivity.class);
                startActivity(intent);
            }
        });

        guidanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuidanceHomeActivity.this,GuidanceActivity.class);
                startActivity(intent);
            }
        });

        registerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuidanceHomeActivity.this,RegisterPatientActivity.class);
                startActivity(intent);
            }
        });

        headerImage = (ImageView)findViewById(R.id.personal_photo);

        initData();

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onresume");
//        initData();
    }

    private void initData(){
        communityData.clear();
        hospitalData.clear();
        //测试
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name","阳光小区");
        map.put("address", "XX省");
        communityData.add(map);


        HashMap<String,String> map1 = new HashMap<String,String>();
        map1.put("name","阳光医院");
        hospitalData.add(map1);
    }
    private void getPopupWindowInstance() {
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /*
     * 创建PopupWindow
     */
    private void initPopuptWindow() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View popupWindowView = layoutInflater.inflate(
                R.layout.main_popupwindown, null);
        TextView packageTV = (TextView) popupWindowView
                .findViewById(R.id.taocan_tv);
        TextView infotv = (TextView) popupWindowView
                .findViewById(R.id.personal_info_tv);

        TextView checktv = (TextView) popupWindowView
                .findViewById(R.id.check_stand_tv);

        TextView yuyuelisttv = (TextView) popupWindowView
                .findViewById(R.id.yuyue_list_tv);


        TextView teamlisttv = (TextView) popupWindowView
                .findViewById(R.id.team_list_tv);

        TextView newReglisttv = (TextView) popupWindowView
                .findViewById(R.id.new_reg_list_tv);

        packageTV.setOnClickListener(popupWindowClickListener);
        infotv.setOnClickListener(popupWindowClickListener);
        checktv.setOnClickListener(popupWindowClickListener);
        yuyuelisttv.setOnClickListener(popupWindowClickListener);

        teamlisttv.setOnClickListener(popupWindowClickListener);
        newReglisttv.setOnClickListener(popupWindowClickListener);



        // 创建一个PopupWindow
        // 参数1：contentView 指定PopupWindow的内容
        // 参数2：width 指定PopupWindow的width
        // 参数3：height 指定PopupWindow的height
//		mPopupWindow = new PopupWindow(popupWindowView, getWindowManager()
//				.getDefaultDisplay().getWidth() /3, getWindowManager()
//				.getDefaultDisplay().getHeight() / 5);

        mPopupWindow = new PopupWindow(popupWindowView, getWindowManager()
                .getDefaultDisplay().getWidth() /2, getWindowManager()
                .getDefaultDisplay().getHeight() / 2);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

    }


    private View.OnClickListener popupWindowClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.taocan_tv:
//					Toast.makeText(GuidanceHomeActivity.this, "套餐服务", Toast.LENGTH_LONG).show();
//					toastCommom.ToastShow(GuidanceHomeActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "套餐服务");
                    Intent intent1 = new Intent(GuidanceHomeActivity.this, PackagesActivity.class);
                    startActivity(intent1);

                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                    break;

                case R.id.personal_info_tv:

                    Intent intent2 = new Intent(GuidanceHomeActivity.this, PersonalInfoActivity.class);
                    startActivity(intent2);
                    mPopupWindow.dismiss();
                    mPopupWindow = null;

                    break;

                case R.id.check_stand_tv:
                    Intent intent3 = new Intent(GuidanceHomeActivity.this, CheckStandTab.class);
                    startActivity(intent3);
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                    break;
                case R.id.yuyue_list_tv:
                    Intent intent4 = new Intent(GuidanceHomeActivity.this, AppointmentListActivity.class);
                    startActivity(intent4);
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                    break;
                case R.id.team_list_tv:
                    Intent intent5 = new Intent(GuidanceHomeActivity.this, TeamListActivity.class);
                    startActivity(intent5);
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                    break;
                case R.id.new_reg_list_tv:
                    Intent intent6 = new Intent(GuidanceHomeActivity.this, AutoRegPatientListActivity.class);
                    startActivity(intent6);
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                    break;
                default:
                    break;
            }

        }
    };

    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     *
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
        Log.i(TAG, "onActivityResult data " + data);
        Log.i(TAG, "onActivityResult requestCode "+requestCode);
        Log.i(TAG, "onActivityResult resultCode "+resultCode);

        if(requestCode == ADD && resultCode == RESULT_OK){
            ///测试activity间传递list
            Bundle bundle=data.getExtras();

            ArrayList list2 = bundle.getParcelableArrayList("list");

            String flag = bundle.getString("type");
            if(flag.equals("0")){
                //如果是社区
                if(list2 != null && !list2.isEmpty()) {

                    Log.i("GXF", "onActivityResult测试activity间传递list list2 " + list2);
                    for (int i = 0; i < list2.size(); i++) {
                        HashMap<String, String> map = (HashMap<String, String>) list2.get(i);
                        communityData.add(map);
                    }
                    communityListAdapter.notifyDataSetChanged();
                }
            }else{
                if(list2 != null && !list2.isEmpty()) {

                    Log.i("GXF", "onActivityResult测试activity间传递list list2 " + list2);
                    for (int i = 0; i < list2.size(); i++) {
                        HashMap<String, String> map = (HashMap<String, String>) list2.get(i);
                        hospitalData.add(map);
                    }
                    hospitalListAdapter.notifyDataSetChanged();
                }
            }


//        if(resultCode == RESULT_OK) {
//            String name = data.getStringExtra("name");
//            String address = data.getStringExtra("address");
//            String flag = data.getStringExtra("type");
//            Log.i(TAG,"onActivityResult name "+name);
//            Log.i(TAG,"onActivityResult address "+address);
//            Log.i(TAG,"onActivityResult type "+flag);
//            if (flag.equals("0")) {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("name", name);
//                map.put("address", address);
//
//                communityData.add(map);
//                Log.i(TAG, "onActivityResult communityData " + communityData.toString());
//                communityListAdapter.notifyDataSetChanged();
//
//            } else {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("name", name);
//                map.put("address", address);
//
//                hospitalData.add(map);
//                hospitalListAdapter.notifyDataSetChanged();
//            }
//
        }

    }



}
