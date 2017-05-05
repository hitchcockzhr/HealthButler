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
 * 肝功能检查解读
 */
public class FUTableAuxLiverFunctionUnderstandingActivity extends BaseActivity {
    private ToastCommom toastCommom;
    private String TAG = "FUTableAuxLiverFunctionUnderstandingActivity";
    private String name;
    private TextView nameTV,normalConditionTV,highConditionTV,lowConditionTV;
    private LinearLayout understandingLayout;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_aux_liver_function_understanding);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableAuxLiverFunctionUnderstandingActivity.this);

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
            if(name.equals("谷丙转氨")){
//                understandingLayout.removeAllViews();
//                View view = getLayoutInflater().inflate(R.layout.activity_follow_up_table_aux_liver_function_alt_understanding,null);
//                understandingLayout.addView(view);
                nameTV.setText("谷丙转氨（SGPT）");
                normalConditionTV.setText("（0-40）U/L");
                highConditionTV.setText("肝炎，脂肪肝，肝脏肿瘤，肝硬化，溶血性疾病，心肌梗塞，肌肉病变。");
                lowConditionTV.setText("—");
            }else if(name.equals("r-谷氨酰转肽酶")){
                nameTV.setText("r-谷氨酰转肽酶（r-GT）");
                normalConditionTV.setText("低于50IU/ml");
                highConditionTV.setText("肝内和肝外梗阻性黄疸，肝炎，肝硬化，酒精或药物性肝损害。");
                lowConditionTV.setText("—");
            }else if(name.equals("谷草转氨酶")){
                nameTV.setText("谷草转氨酶（SGOT）");
                normalConditionTV.setText("（0-40）U/L");
                highConditionTV.setText("心肌梗塞急性期，肝炎，心肌炎，胸膜炎，肾炎，肺炎，肌炎。");
                lowConditionTV.setText("—");
            }else if(name.equals("总胆红素")){
                nameTV.setText("总胆红素");
                normalConditionTV.setText("（5.1-17.1）umol/L");
                highConditionTV.setText("急、慢性肝炎，梗阻性黄疸，血色素沉着症，肝癌，胆结石，胆管炎，肝硬化，溶血性疾病。");
                lowConditionTV.setText("—");
            }else if(name.equals("间接胆红素")){
                nameTV.setText("间接胆红素");
                normalConditionTV.setText("（5.0-12.0）umol/L");
                highConditionTV.setText("溶血性疾病，葡萄糖醛酸转移酶缺乏症。");
                lowConditionTV.setText("—");
            }else if(name.equals("直接胆红素")){
                nameTV.setText("直接胆红素");
                normalConditionTV.setText("（0-3.4）ummol/L");
                highConditionTV.setText("肝炎，肝硬化，药物性肝损害，肝癌，肝内结石，胆道阻塞。");
                lowConditionTV.setText("—");
            }else if(name.equals("碱性磷酸酶")){
                nameTV.setText("碱性磷酸酶（AKP）");
                normalConditionTV.setText("（53-128）U/L");
                highConditionTV.setText("阻塞性黄疸，肝炎，肝癌，畸形性骨炎，佝偻病，软骨病，骨转移癌，骨折修复期。");
                lowConditionTV.setText("—");
            }else if(name.equals("血清总蛋白")){
                nameTV.setText("血清总蛋白");
                normalConditionTV.setText("（60-80）g/L");
                highConditionTV.setText("总蛋白增高，脱水；");
                lowConditionTV.setText("血液稀释，饥饿，营养不良，消化吸收不良综合征，严重甲状腺机能亢进，重症糖尿病，烧伤，蛋白质吸收功能障碍的胃肠道疾患，出血。");
            }else if(name.equals("血清白蛋白")){
                nameTV.setText("血清白蛋白");
                normalConditionTV.setText("（35-50）g/L");
                highConditionTV.setText("脱水；");
                lowConditionTV.setText("营养摄入不足，肝硬化，烧伤，低蛋白血症，肾病综合征。");
            }else if(name.equals("血清球蛋白")){
                nameTV.setText("血清球蛋白");
                normalConditionTV.setText("（20-30）g/L");
                highConditionTV.setText("感染性疾病，多发性骨髓瘤。结缔组织病，肝硬化，疟疾，丝虫病等；");
                lowConditionTV.setText("肾上腺皮质机能亢进，营养不良。");
            }else if(name.equals("谷氨酰转移酶")){
                nameTV.setText("谷氨酰转移酶");
                normalConditionTV.setText("（7-32）U/L");
                highConditionTV.setText("胆道阻塞性疾病；急、慢性病毒性肝炎、肝硬化；急、慢性酒精性肝炎、药物性肝炎；脂肪肝、胰腺炎、胰腺肿瘤、前列腺肿瘤等GGT亦可轻度增高");
                lowConditionTV.setText("-");
            }else if(name.equals("白球比")){
                nameTV.setText("白球比");
                normalConditionTV.setText("1.0-2.0");
                highConditionTV.setText("-");
                lowConditionTV.setText("在慢性肝病，A/G比值降低是诊断肝硬化的良好指标，但在早期病例检出不够灵敏。");
            }
        }
    }
}
