package com.housekeeper.ar.healthhousekeeper.sign;

import android.annotation.TargetApi;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.SystemBarTintManager;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

/**
 * Created by lenovo on 2016/3/5.
 */
//病人
public class SignPatientTabActivity extends TabActivity {


    String TAG = "SignPatientTabActivity";

    //设置任务栏颜色
    SystemBarTintManager tintManager;

    String http, httpUrl;

    private ToastCommom toastCommom;
    private TextView leftArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTranslucentStatus(true);
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.white);//通知栏所需颜色

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(SignPatientTabActivity.this);


        setContentView(R.layout.activity_tabhost);
        leftArrow = (TextView)findViewById(R.id.left_arrow);

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        Resources res = getResources(); // Resource object to get Drawables
        final TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec1;
        TabHost.TabSpec spec2;
        TabHost.TabSpec spec3;
        Intent intent;  // Reusable Intent for each tab

        //第一个Tab
        intent = new Intent(this,NotSignPatientListActivity.class);//新建一个Intent用作Tab1显示的内容
        spec1 = tabHost.newTabSpec("tab1")//新建一个 Tab
                .setIndicator(createTabView(R.string.tab_not_sign))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec1);//添加进tabHost

        //第二个Tab
        intent = new Intent(this,HasSignedPatientListActivity.class);//第二个Intent用作Tab1has显示的内容
        spec2 = tabHost.newTabSpec("tab2")//新建一个 Tab
                .setIndicator(createTabView(R.string.tab_has_signed))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec2);//添加进tabHost

        //第二个Tab
        intent = new Intent(this,NotFinishPatientListActivity.class);//第二个Intent用作Tab1has显示的内容
        spec3 = tabHost.newTabSpec("tab3")//新建一个 Tab
                .setIndicator(createTabView(R.string.tab_not_finish))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec3);//添加进tabHost

//        intent = new Intent(this,NotFinishPatientListActivity.class);//第二个Intent用作Tab1has显示的内容
//        spec3 = tabHost.newTabSpec("tab4")//新建一个 Tab
//                .setIndicator(createTabView(R.string.tab_not_finish))//设置名称以及图标
//                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
//        tabHost.addTab(spec3);//添加进tabHost

        View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
        View mView2 = tabHost.getTabWidget().getChildAt(1);//0是代表第二个Tab
        View mView3 = tabHost.getTabWidget().getChildAt(2);//0是代表第二个Tab


        LinearLayout layout1 = (LinearLayout)mView1.findViewById(R.id.tab_layout);

        layout1.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
        TextView tv1 = (TextView)mView1.findViewById(R.id.tv_tab);
        tv1.setTextColor(getResources().getColor(R.color.white));

        LinearLayout layout2 = (LinearLayout)mView2.findViewById(R.id.tab_layout);
        layout2.setBackgroundResource(R.drawable.btn_green_stroke_shape);
        TextView tv2 = (TextView)mView2.findViewById(R.id.tv_tab);
        tv2.setTextColor(getResources().getColor(R.color.black));

        LinearLayout layout3 = (LinearLayout)mView3.findViewById(R.id.tab_layout);
        layout3.setBackgroundResource(R.drawable.btn_green_stroke_shape);
        TextView tv3 = (TextView)mView3.findViewById(R.id.tv_tab);
        tv3.setTextColor(getResources().getColor(R.color.black));


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                Log.i("GXF", "tab daifukuan s " + s);
                if(s.equals("tab1")){

                    View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
                    View mView2 = tabHost.getTabWidget().getChildAt(1);//0是代表第二个Tab
                    View mView3 = tabHost.getTabWidget().getChildAt(2);

                    LinearLayout layout1 = (LinearLayout)mView1.findViewById(R.id.tab_layout);

                    layout1.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    TextView tv1 = (TextView)mView1.findViewById(R.id.tv_tab);
                    tv1.setTextColor(getResources().getColor(R.color.white));

                    LinearLayout layout2 = (LinearLayout)mView2.findViewById(R.id.tab_layout);
                    layout2.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    TextView tv2 = (TextView)mView2.findViewById(R.id.tv_tab);
                    tv2.setTextColor(getResources().getColor(R.color.black));

                    LinearLayout layout3 = (LinearLayout)mView3.findViewById(R.id.tab_layout);
                    layout3.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    TextView tv3 = (TextView)mView3.findViewById(R.id.tv_tab);
                    tv3.setTextColor(getResources().getColor(R.color.black));

                } else if(s.equals("tab2")){

                    View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
                    View mView2 = tabHost.getTabWidget().getChildAt(1);//0是代表第二个Tab
                    View mView3 = tabHost.getTabWidget().getChildAt(2);

                    LinearLayout layout1 = (LinearLayout)mView1.findViewById(R.id.tab_layout);

                    layout1.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    TextView tv1 = (TextView)mView1.findViewById(R.id.tv_tab);
                    tv1.setTextColor(getResources().getColor(R.color.black));

                    LinearLayout layout2 = (LinearLayout)mView2.findViewById(R.id.tab_layout);
                    layout2.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    TextView tv2 = (TextView)mView2.findViewById(R.id.tv_tab);
                    tv2.setTextColor(getResources().getColor(R.color.white));

                    LinearLayout layout3 = (LinearLayout)mView3.findViewById(R.id.tab_layout);
                    layout3.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    TextView tv3 = (TextView)mView3.findViewById(R.id.tv_tab);
                    tv3.setTextColor(getResources().getColor(R.color.black));

                }else if(s.equals("tab3")){

                    View mView1 = tabHost.getTabWidget().getChildAt(0);//0是代表第一个Tab
                    View mView2 = tabHost.getTabWidget().getChildAt(1);//0是代表第二个Tab
                    View mView3 = tabHost.getTabWidget().getChildAt(2);

                    LinearLayout layout1 = (LinearLayout)mView1.findViewById(R.id.tab_layout);

                    layout1.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    TextView tv1 = (TextView)mView1.findViewById(R.id.tv_tab);
                    tv1.setTextColor(getResources().getColor(R.color.black));

                    LinearLayout layout2 = (LinearLayout)mView2.findViewById(R.id.tab_layout);
                    layout2.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    TextView tv2 = (TextView)mView2.findViewById(R.id.tv_tab);
                    tv2.setTextColor(getResources().getColor(R.color.black));

                    LinearLayout layout3 = (LinearLayout)mView3.findViewById(R.id.tab_layout);
                    layout3.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    TextView tv3 = (TextView)mView3.findViewById(R.id.tv_tab);
                    tv3.setTextColor(getResources().getColor(R.color.white));

                }
            }
        });


    }
    private View createTabView(int text) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator1, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_tab);
        tv.setText(getResources().getString(text));


        return view;
    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
