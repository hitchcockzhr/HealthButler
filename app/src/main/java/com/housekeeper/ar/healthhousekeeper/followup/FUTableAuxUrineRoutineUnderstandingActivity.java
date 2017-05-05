package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

/**
 * Created by Lenovo on 2017/2/8.
 * 尿常规检查解读
 */
public class FUTableAuxUrineRoutineUnderstandingActivity extends BaseActivity {
    private ToastCommom toastCommom;
    private String TAG = "FUTableAuxUrineRoutineUnderstandingActivity";
    private String name;
    private TextView nameTV,normalConditionTV,highConditionTV,lowConditionTV,highConditionTitleTV,lowConditionTitleTV;
    private LinearLayout understandingLayout;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_aux_urine_routine_understanding);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableAuxUrineRoutineUnderstandingActivity.this);

        backBtn = (Button)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nameTV = (TextView)findViewById(R.id.name_tv);
        normalConditionTV = (TextView)findViewById(R.id.normal_condition_tv);
        highConditionTV = (TextView)findViewById(R.id.high_condition_tv);
        lowConditionTV = (TextView)findViewById(R.id.low_condition_tv);
        understandingLayout = (LinearLayout)findViewById(R.id.understanding_layout);
        highConditionTitleTV = (TextView)findViewById(R.id.tv2);
        lowConditionTitleTV = (TextView)findViewById(R.id.tv3);

        Intent intent = getIntent();
        if(intent != null){
            name = intent.getStringExtra("name");
            if(name.equals("酸碱度")){
//                understandingLayout.removeAllViews();
//                View view = getLayoutInflater().inflate(R.layout.activity_follow_up_table_aux_liver_function_alt_understanding,null);
//                understandingLayout.addView(view);
                nameTV.setText("酸碱度（PH）");
                normalConditionTV.setText("4.6-8.0（平均值6.0）");
                highConditionTV.setText("增高常见于频繁呕吐、呼吸性碱中毒等");
                lowConditionTV.setText("降低常见于酸中毒、慢性肾小球肾炎、糖尿病等");
            }else if(name.equals("尿比重")){
                nameTV.setText("尿比重（SG）");
                normalConditionTV.setText("1.015-1.025");
                highConditionTV.setText("增高多见于高热、心功能不全、糖尿病等");
                lowConditionTV.setText("降低多见于慢性肾小球肾炎和肾孟肾炎等");
            }else if(name.equals("尿胆原")){
                nameTV.setText("尿胆原（URO）");
                normalConditionTV.setText("<16");
                highConditionTV.setText("超过此数值，说明有黄疽");
                lowConditionTV.setText("—");
            }else if(name.equals("隐血")){
                nameTV.setText("隐血（BLO）");
                normalConditionTV.setText("阴性（-）");
                highConditionTitleTV.setText("阳性");
                highConditionTV.setText("同时有蛋白者，要考虑肾脏病和出血");
                lowConditionTitleTV.setText("阴性（-）");
                lowConditionTV.setText("正常");
            }else if(name.equals("白细胞")){
                nameTV.setText("白细胞（WBC）");
                normalConditionTV.setText("阴性（-）");
                highConditionTitleTV.setText("阳性（+）");
                highConditionTV.setText("超过五个，说明尿路感染");
                lowConditionTitleTV.setText("阴性（-）");
                lowConditionTV.setText("正常");
            }else if(name.equals("尿蛋白")){
                nameTV.setText("尿蛋白（PRO）");
                normalConditionTV.setText("阴性或仅有微量");
                highConditionTitleTV.setText("阳性（+）");
                highConditionTV.setText("阳性提示可能有急性肾小球肾炎、糖尿病肾性病变");
                lowConditionTitleTV.setText("阴性（-）");
                lowConditionTV.setText("正常");
            }else if(name.equals("尿糖")){
                nameTV.setText("尿糖（GLU）");
                normalConditionTV.setText("阴性（-）");
                highConditionTitleTV.setText("阳性（+）");
                highConditionTV.setText("阳性提示可能有糖尿病、甲亢、肢端肥大症等");
                lowConditionTitleTV.setText("阴性（-）");
                lowConditionTV.setText("正常");
            }else if(name.equals("胆红素")){
                nameTV.setText("胆红素（BIL）");
                normalConditionTV.setText("阴性（-）");
                highConditionTitleTV.setText("阳性（+）");
                highConditionTV.setText("阳性提示可能肝细胞性或阻塞性黄疽");
                lowConditionTitleTV.setText("阴性（-）");
                lowConditionTV.setText("正常");
            }else if(name.equals("酮体")){
                nameTV.setText("酮体（KET）");
                normalConditionTV.setText("阴性（-）");
                highConditionTitleTV.setText("阳性（+）");
                highConditionTV.setText("阳性提示可能酸中毒、糖尿病、呕吐、腹泻");
                lowConditionTitleTV.setText("阴性（-）");
                lowConditionTV.setText("正常");
            }else if(name.equals("尿红细胞")){
                nameTV.setText("尿红细胞（RBC）");
                normalConditionTV.setText("阴性（-）");
                highConditionTitleTV.setText("阳性（+）");
                highConditionTV.setText("阳性提示可能泌尿道肿瘤、肾炎尿路感染等");
                lowConditionTitleTV.setText("阴性（-）");
                lowConditionTV.setText("正常");
            }else if(name.equals("尿液颜色")){
                nameTV.setText("尿液颜色（GOL）");
                normalConditionTV.setText("浅黄色至深黄色");
                highConditionTitleTV.setText("其他颜色");
                highConditionTV.setText("黄绿色、尿浑浊、血红色等就说明有问题");
                lowConditionTitleTV.setText("浅黄色至深黄色");
                lowConditionTV.setText("正常");
            }
        }
    }
}
