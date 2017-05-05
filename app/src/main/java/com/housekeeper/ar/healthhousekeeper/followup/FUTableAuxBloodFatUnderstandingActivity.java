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
 * 血脂检查解读
 */
public class FUTableAuxBloodFatUnderstandingActivity extends BaseActivity {
    private ToastCommom toastCommom;
    private String TAG = "FUTableAuxBloodFatUnderstandingActivity";
    private String name;
    private TextView nameTV,normalConditionTV,highConditionTV,lowConditionTV;
    private LinearLayout understandingLayout;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_aux_blood_fat_understanding);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableAuxBloodFatUnderstandingActivity.this);

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

        Intent intent = getIntent();
        if(intent != null){
            name = intent.getStringExtra("name");
            if(name.equals("总胆固醇")){
//                understandingLayout.removeAllViews();
//                View view = getLayoutInflater().inflate(R.layout.activity_follow_up_table_aux_liver_function_alt_understanding,null);
//                understandingLayout.addView(view);
                nameTV.setText("总胆固醇（TC）");
                normalConditionTV.setText("2.8-5.69mmol/L");
                highConditionTV.setText("肥胖，糖尿病，妊娠，甲状腺技能低下，肾病，脂代谢异常");
                lowConditionTV.setText("甲状腺技能亢进，阿狄森病，肝硬化，长期营养不良");
            }else if(name.equals("甘油三脂")){
                nameTV.setText("甘油三脂（血清）");
                normalConditionTV.setText("（0.56-1.7）mmol/L");
                highConditionTV.setText("高脂蛋白血症，肥胖症，动脉硬化症，痛风，甲状腺技能低下，柯兴综合征，糖尿病，妊娠。");
                lowConditionTV.setText("甲状腺技能亢进，慢性肾上腺功能不全，脑垂体功能低下，肝硬化。");
            }else if(name.equals("高密度脂蛋白胆固醇")){
                nameTV.setText("高密度脂蛋白胆固醇（HDL-C）");
                normalConditionTV.setText("大于1.00mmol/L");
                highConditionTV.setText("-");
                lowConditionTV.setText("—");
            }else if(name.equals("低密度脂蛋白胆固醇")){
                nameTV.setText("低密度脂蛋白胆固醇（LDL-C）");
                normalConditionTV.setText("低于3.12mmol/L");
                highConditionTV.setText("常见于家族性高胆固醇血症、Ⅱa型高脂蛋白血症等。");
                lowConditionTV.setText("—");
            }else if(name.equals("脂蛋白a")){
                nameTV.setText("脂蛋白（a）[Lp（a）]");
                normalConditionTV.setText("健康成人血清中浓度小于300mg/L。");
                highConditionTV.setText("增高可见于缺血性心脑血管疾病、心肌梗死、外科手术、急性创伤和炎症、肾病综合征和尿毒症、除肝癌外的恶性肿瘤等。");
                lowConditionTV.setText("降低可见于肝脏疾病，因为脂蛋白在肝脏合成。");
            }else if(name.equals("软脂蛋白A")){
                nameTV.setText("软脂蛋白A");
                normalConditionTV.setText("男性：0.92-2.36g/L；女性：0.8-2.10g/L");
                highConditionTV.setText("抗癫痫药物、长时间过量饮酒、妊娠期间、肝脏出现异常（慢性肝炎）。");
                lowConditionTV.setText("肝脏功能受损，常见于患有动脉粥样硬化、糖尿病、高脂蛋白血症等疾病的患者。");
            }else if(name.equals("软脂蛋白B")){
                nameTV.setText("软脂蛋白B");
                normalConditionTV.setText("男性：0.42-1.14g/L；女性0.42-1.26g/L");
                highConditionTV.setText("高脂蛋白血症、糖尿病、动脉粥样硬化、心肌梗塞。");
                lowConditionTV.setText("见于心肌局部缺血和肝功能不全。");
            }
        }
    }
}
