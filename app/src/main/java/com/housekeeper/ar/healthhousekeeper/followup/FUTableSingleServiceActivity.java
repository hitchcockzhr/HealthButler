package com.housekeeper.ar.healthhousekeeper.followup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lenovo on 2017/4/6.
 */
public class FUTableSingleServiceActivity extends BaseActivity {
    String TAG="FUTableSingleServiceActivity";
    private ToastCommom toastCommom;
    private Button backBtn;

    private Button okBtn,cancelBtn;
    private ListView listView;
    private FUTableSingleServiceListAdapter singleServiceListAdapter;
    private List<HashMap<String,String>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_single_service);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableSingleServiceActivity.this);

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okBtn = (Button)findViewById(R.id.ok_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        singleServiceListAdapter = new FUTableSingleServiceListAdapter(FUTableSingleServiceActivity.this,data);
        listView = (ListView)findViewById(R.id.service_listview);
        listView.setAdapter(singleServiceListAdapter);


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        //TODO  现实实现中，该数据从数据库中获取，所以点击加号添加了新服务后不用通过页面将新数据添加到serviceData
        data.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("name", "心电图");
        map.put("price", "8");
        map.put("count", "0");

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","胃管护理");
        map2.put("price", "10");
        map2.put("count", "0");

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name","鼻饲");
        map3.put("price", "2--20");
        map3.put("count", "0");

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name","口腔护理");
        map4.put("price", "3");
        map4.put("count", "0");

        HashMap<String,String> map5 = new HashMap<>();
        map5.put("name","插管护理/气管切开护理");
        map5.put("price", "20");
        map5.put("count", "0");

        HashMap<String,String> map6 = new HashMap<>();
        map6.put("name","吸痰护理");
        map6.put("price", "2--20");
        map6.put("count", "0");

        HashMap<String,String> map7 = new HashMap<>();
        map7.put("name","尿管护理");
        map7.put("price", "10");
        map7.put("count", "0");

        HashMap<String,String> map8 = new HashMap<>();
        map8.put("name","导尿");
        map8.put("price", "一次性10元/次，留置2元/日");
        map8.put("count", "0");

        HashMap<String,String> map9 = new HashMap<>();
        map9.put("name","灌肠");
        map9.put("price", "8元/次");
        map9.put("count", "0");

        HashMap<String,String> map10 = new HashMap<>();
        map10.put("name","清洁灌肠");
        map10.put("price", "15元/次");
        map10.put("count", "0");

        HashMap<String,String> map11 = new HashMap<>();
        map11.put("name","少量灌肠");
        map11.put("price", "8元/次");
        map11.put("count", "0");

        HashMap<String,String> map12 = new HashMap<>();
        map12.put("name","造口护理");
        map12.put("price", "119");
        map12.put("count", "0");

        HashMap<String,String> map13 = new HashMap<>();
        map13.put("name","压疮护理");
        map13.put("price", "119");
        map13.put("count", "0");

        HashMap<String,String> map14 = new HashMap<>();
        map14.put("name","足部护理");
        map14.put("price", "10");
        map14.put("count", "0");

        HashMap<String,String> map15 = new HashMap<>();
        map15.put("name","受压教育");
        map15.put("price", "10");
        map15.put("count", "0");

        HashMap<String,String> map16 = new HashMap<>();
        map16.put("name","剪指甲");
        map16.put("price", "10");
        map16.put("count", "0");

        HashMap<String,String> map17 = new HashMap<>();
        map17.put("name","氧气吸入");
        map17.put("price", "2元/小时");
        map17.put("count", "0");

        HashMap<String,String> map18 = new HashMap<>();
        map18.put("name","肌肉注射");
        map18.put("price", "1元/次");
        map18.put("count", "0");

        HashMap<String,String> map19 = new HashMap<>();
        map19.put("name","静脉注射");
        map19.put("price", "3元/次，小儿+1元");
        map19.put("count", "0");

        HashMap<String,String> map20 = new HashMap<>();
        map20.put("name","心内注射");
        map20.put("price", "10元/次");
        map20.put("count", "0");

        HashMap<String,String> map21 = new HashMap<>();
        map21.put("name","皮下输液");
        map21.put("price", "4元/组，自第2瓶起每瓶+1元");
        map21.put("count", "0");

        HashMap<String,String> map22 = new HashMap<>();
        map22.put("name","静脉输液（静脉留置针）");
        map22.put("price", "5元/组，泵+1元/小时，自第2瓶起每瓶+1元");
        map22.put("count", "0");

        HashMap<String,String> map23 = new HashMap<>();
        map23.put("name","小儿头皮静脉输液（静脉留置针）");
        map23.put("price", "6元/组，泵+1元/小时，自第2瓶起每瓶+1元");
        map23.put("count", "0");

        HashMap<String,String> map24 = new HashMap<>();
        map24.put("name","特大换药（创面100cm2以上，伤口25cm以上）");
        map24.put("price", "40元/次");
        map24.put("count", "0");

        HashMap<String,String> map25 = new HashMap<>();
        map25.put("name","大换药（创面50cm2以上，伤口15cm以上）");
        map25.put("price", "25元/次");
        map25.put("count", "0");

        HashMap<String,String> map26 = new HashMap<>();
        map26.put("name","中换药（创面30~50cm2以上，伤口6~15cm）");
        map26.put("price", "15元/次");
        map26.put("count", "0");

        HashMap<String,String> map27 = new HashMap<>();
        map27.put("name","小换药（创面30cm2以上，伤口小于5cm）");
        map27.put("price", "6元/次");
        map27.put("count", "0");

        HashMap<String,String> map28 = new HashMap<>();
        map28.put("name","静脉踩血");
        map28.put("price", "3元/次，小儿+1元");
        map28.put("count", "0");

        HashMap<String,String> map29 = new HashMap<>();
        map29.put("name","PICC换药");
        map29.put("price", "109");
        map29.put("count", "0");

        HashMap<String,String> map30 = new HashMap<>();
        map30.put("name","血常规");
        map30.put("price", "25");
        map30.put("count", "0");

        HashMap<String,String> map31 = new HashMap<>();
        map31.put("name","尿常规");
        map31.put("price", "20");
        map31.put("count", "0");

        HashMap<String,String> map32 = new HashMap<>();
        map32.put("name","24小时尿蛋白定量");
        map32.put("price", "20");
        map32.put("count", "0");

        HashMap<String,String> map33 = new HashMap<>();
        map33.put("name","肾功能3项");
        map33.put("price", "120");
        map33.put("count", "0");

        HashMap<String,String> map34 = new HashMap<>();
        map34.put("name","乙肝五项");
        map34.put("price", "普通25元，定量100元");
        map34.put("count", "0");


        HashMap<String,String> map35 = new HashMap<>();
        map35.put("name","肝功能11项");
        map35.put("price", "");
        map35.put("count", "0");

        HashMap<String,String> map36 = new HashMap<>();
        map36.put("name","空腹血糖");
        map36.put("price", "5");
        map36.put("count", "0");

        HashMap<String,String> map37 = new HashMap<>();
        map37.put("name","餐后血糖（2小时）");
        map37.put("price", "5");
        map37.put("count", "0");

        HashMap<String,String> map38 = new HashMap<>();
        map38.put("name","血脂（2/4/6/7）");
        map38.put("price", "");
        map38.put("count", "0");

        HashMap<String,String> map39 = new HashMap<>();
        map39.put("name","糖化血红蛋白");
        map39.put("price", "");
        map39.put("count", "0");

        data.add(map);
        data.add(map2);

        data.add(map3);
        data.add(map4);

        data.add(map5);
        data.add(map6);

        data.add(map7);
        data.add(map8);
        data.add(map9);
        data.add(map10);
        data.add(map11);
        data.add(map12);
        data.add(map13);
        data.add(map14);
        data.add(map15);
        data.add(map16);
        data.add(map17);
        data.add(map18);
        data.add(map19);
        data.add(map20);
        data.add(map21);
        data.add(map22);
        data.add(map23);
        data.add(map24);
        data.add(map25);
        data.add(map26);
        data.add(map27);
        data.add(map28);
        data.add(map29);
        data.add(map30);
        data.add(map31);
        data.add(map32);
        data.add(map33);
        data.add(map34);
        data.add(map35);
        data.add(map36);
        data.add(map37);
        data.add(map38);
        data.add(map39);
    }
}
