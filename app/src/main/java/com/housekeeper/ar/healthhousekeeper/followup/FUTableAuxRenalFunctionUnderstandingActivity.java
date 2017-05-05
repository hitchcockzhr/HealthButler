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
 * 肾功能检查解读
 */
public class FUTableAuxRenalFunctionUnderstandingActivity extends BaseActivity {
    private ToastCommom toastCommom;
    private String TAG = "FUTableAuxRenalFunctionUnderstandingActivity";
    private String name;
    private TextView nameTV,normalConditionTV,highConditionTV,lowConditionTV;
    private LinearLayout understandingLayout;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_aux_renal_function_understanding);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableAuxRenalFunctionUnderstandingActivity.this);

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
            if(name.equals("血尿素氮")){
//                understandingLayout.removeAllViews();
//                View view = getLayoutInflater().inflate(R.layout.activity_follow_up_table_aux_liver_function_alt_understanding,null);
//                understandingLayout.addView(view);
                nameTV.setText("血尿素氮（BUN）");
                normalConditionTV.setText("二乙酰-肟显色法1.8～6.8mmol/L尿素酶-钠氏显色法3.2～6.1mmol/L。");
                highConditionTV.setText("急慢性肾炎、重症肾盂肾炎、各种原因所致的急慢性肾功能障碍，心衰、休克、大量内出血、烧伤、失水、肾上腺皮质功能减退症、前列腺肥大、慢性尿路梗阻等。");
                lowConditionTV.setText("—");
            }else if(name.equals("血肌酐")){
                nameTV.setText("血肌酐(Scr)");
                normalConditionTV.setText("成人男79.6～132.6μmol/L，女70.7～106.1μmol/L；小儿26.5～62.0μmol/L全血88.4～159.1μmol/L。");
                highConditionTV.setText("肾衰、尿毒症、心衰、巨人症、肢端肥大症、水杨酸盐类治疗等。");
                lowConditionTV.setText("进行性肌萎缩，白血病，贫血等。");
            }else if(name.equals("血尿素")){
                nameTV.setText("血尿素");
                normalConditionTV.setText("3.2～7.0mmol/L。");
                highConditionTV.setText("升高表示急慢性肾炎、重症肾盂肾炎、各种原因所致的急慢性肾功能障碍，心衰、休克、烧伤、失水、大量内出血、肾上腺皮质功能减退症、前列腺肥大、慢性尿路梗阻等。");
                lowConditionTV.setText("—");
            }else if(name.equals("血尿酸")){
                nameTV.setText("血尿酸");
                normalConditionTV.setText("成人男149～417μmol/L，女89～357μmol/L；>60岁男250～476μmol/L，女190～434μmol/L。");
                highConditionTV.setText("痛风、急慢性白血病、多发性骨髓瘤、恶性贫血、肾衰、肝衰、红细胞增多症、妊娠反应、剧烈活动及高脂肪餐后等。");
                lowConditionTV.setText("—");
            }else if(name.equals("尿肌酐")){
                nameTV.setText("尿肌酐(Cr)");
                normalConditionTV.setText("婴儿88～176μmmol·kg-1/d；儿童44～352μmol·kg-1/d；成人7～8mmol/d。");
                highConditionTV.setText("饥饿、发热、急慢性消耗等疾病，剧烈运动后等。");
                lowConditionTV.setText("肾衰、肌萎缩、贫血、白血病等。");
            }else if(name.equals("尿蛋白")){
                nameTV.setText("尿蛋白");
                normalConditionTV.setText("定性阴性，正常人每日自尿中排出约40～80蛋白，上限不超过150mg，其中主要为白蛋白，其次为糖蛋白和糖肽。这些蛋白的0.60(60%)左右来自血浆，其余的来源于肾、泌尿道、前列腺的分泌物和组织分解产物，包括尿酶、激素、抗体及其降解物等。");
                highConditionTV.setText("体位性蛋白尿、运动性蛋白尿、发热、情绪激动、过冷过热的气候等。");
                lowConditionTV.setText("—");
            }else if(name.equals("β2-微球蛋白清除试验")){
                nameTV.setText("β2-微球蛋白清除试验");
                normalConditionTV.setText("23～62μl/min");
                highConditionTV.setText("肾小管损害。本试验是了解肾小管损害程度的可靠指标，特别有助于发现轻型患者。");
                lowConditionTV.setText("—");
            }else if(name.equals("选择性蛋白尿指数")){
                nameTV.setText("选择性蛋白尿指数");
                normalConditionTV.setText("0.0～0.2");
                highConditionTV.setText("高选择性见于微小病变型肾病，对激素敏感，预后较好。");
                lowConditionTV.setText("膜性或膜增殖性肾炎常表现为低选择性或称之为非选择性，表示大分子蛋白大量通过了肾小球滤膜，对激素反应差，预后不良。");
            }else if(name.equals("尿素清除率")){
                nameTV.setText("尿素清除率");
                normalConditionTV.setText("0.7～1.6ml·s-1/1.73m2");
                highConditionTV.setText("心排血量增多的各种情况（如高热，甲亢，妊娠），烧伤，一氧化碳中毒，高蛋白饮食，糖尿病肾病早期。");
                lowConditionTV.setText("休克，出血，失水，充血性心衰，高血压晚期，急慢性肾功能衰竭，急慢性肾小球肾炎，肾病综合征，肾盂肾炎，肾淀粉样变性，急性肾小管病变，输尿管阻塞，多发性骨髓瘤，肾上腺皮质功能减退，肝豆状核变性，维生素D抵抗性佝偻病，慢性阻塞性肺病，肝功能衰竭等。");
            }else if(name.equals("血内生肌酐清除率")){
                nameTV.setText("血内生肌酐清除率");
                normalConditionTV.setText("0.80～1.20ml·s-1/m2");
                highConditionTV.setText("心排血量增多的各种情况（如高热，甲亢，妊娠），烧伤，一氧化碳中毒，高蛋白饮食，糖尿病肾病早期。");
                lowConditionTV.setText("休克，出血，失水，充血性心衰，高血压晚期，急慢性肾功能衰竭，急慢性肾小球肾炎，肾病综合征，肾盂肾炎，肾淀粉样变性，急性肾小管病变，输尿管阻塞，多发性骨髓瘤，肾上腺皮质功能减退，肝豆状核变性，维生素D抵抗性佝偻病，慢性阻塞性肺病，肝功能衰竭等。");
            }else if(name.equals("尿素氮肌酐比值")){
                nameTV.setText("尿素氮肌酐比值");
                normalConditionTV.setText("12～20");
                highConditionTV.setText("异化作用亢进：发热、服用类固醇和四环素等药物、应激状态等；高蛋白饮食(特别是肾功能不全时)、消化道出血；尿素的再吸收亢进；");
                lowConditionTV.setText("饥饿、低蛋白饮食；合并严重肝功能衰竭；利尿药物；透析。");
            }else if(name.equals("酚红排泄试验")){
                nameTV.setText("酚红排泄试验");
                normalConditionTV.setText("15min0.25～0.51(0.53) 30min0.13～0.24(0.17) 60min0.09～0.17(0.12) 120min0.03～0.10(0.06) 120min总量0.63～0.84(0.70)");
                highConditionTV.setText("阻塞性黄疸时肝脏排泄途径出现障碍，因此经尿液排出的酚红总量会有所增加。甲亢病人因血循环加快也会使酚红排泄量增加。");
                lowConditionTV.setText("两小时内酚红总排除出量<50%，提示肾小管分泌功能减低。见于慢性肾小球肾炎、肾盂肾炎、肾小动脉硬化症、肾淤血时，总排出量也会降低。尿毒症时排出量可能接近0。");
            }
        }
    }
}
