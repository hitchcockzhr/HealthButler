package com.housekeeper.ar.healthhousekeeper.packages;

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
 * Created by Lenovo on 2017/3/1.
 * 套餐服务
 */
public class PackagesActivity extends BaseActivity {
    private ToastCommom toastCommom;
    private String TAG = "PackagesActivity";
    private Button backBtn;
    PackagesListAdapter packagesListAdapter;
    ListView servicesListView;
    public List<HashMap<String,String>> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_service);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(PackagesActivity.this);

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        servicesListView = (ListView)findViewById(R.id.service_list);
        packagesListAdapter = new PackagesListAdapter(PackagesActivity.this,data);
        servicesListView.setAdapter(packagesListAdapter);
        initData();
    }

    private void initData(){
        data.clear();

        HashMap<String,String> map = new HashMap<>();
        map.put("name","免费套餐");
        map.put("item","BMI、身高、体重、基础代谢率、体脂含量、肌肉含量、内脏脂肪指数、心率、脉搏、血氧饱和度、血压、血糖（1次/季度）、口腔检查（1次/季度）、指标解读");
        map.put("charge_standard", "0");
        map.put("price", "0");

        HashMap<String,String> map2 = new HashMap<>();
        map2.put("name","高血压人群套餐（季度）");
        map2.put("item","血糖3次、血脂1次、尿常规6次、心电图3次、肾功能1次、24小时尿1次、指标解读、专业医疗方案指导、生活健康指导");
        map2.put("charge_standard","198元/季度、66元/月");
        map2.put("price", "198");

        HashMap<String,String> map3 = new HashMap<>();
        map3.put("name","血脂异常人群套餐（季度）");
        map3.put("item","血糖3次、血脂3次、尿常规6次、肝功能1次、心电图3次、指标解读、专业医疗方案指导、生活健康指导");
        map3.put("charge_standard","228元/季度、76元/月");
        map3.put("price", "228");

        HashMap<String,String> map4 = new HashMap<>();
        map4.put("name","糖尿病人群套餐（季度）");
        map4.put("item","血糖6次、血脂1次、尿常规6次、肝功能1次、肾功能1次、糖化血红蛋白1次、血常规1次、糖尿病指标解读、专业医疗方案指导、生活健康指导");
        map4.put("charge_standard","258元/季度、86元/月");
        map4.put("price", "258");

        HashMap<String,String> map5 = new HashMap<>();
        map5.put("name","儿童套餐（季度）");
        map5.put("item","微量元素1次、骨密度1次、小儿发热感冒（血常规、支原体、C反应蛋白）1次、口腔检查1次、指标解读、专业医疗方案指导、生活健康指导");
        map5.put("charge_standard","238元/季度");
        map5.put("price","238");

        data.add(map);
        data.add(map2);
        data.add(map3);
        data.add(map4);
        data.add(map5);

    }
}
