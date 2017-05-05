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
 * 血常规检查解读
 */
public class FUTableAuxBloodRoutineUnderstandingActivity extends BaseActivity {
    private ToastCommom toastCommom;
    private String TAG = "FUTableAuxBloodRoutineUnderstandingActivity";
    private String name;
    private TextView nameTV,normalConditionTV,highConditionTV,lowConditionTV;
    private LinearLayout understandingLayout;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_aux_blood_routine_understanding);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableAuxBloodRoutineUnderstandingActivity.this);

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
            if(name.equals("血红蛋白")){
//                understandingLayout.removeAllViews();
//                View view = getLayoutInflater().inflate(R.layout.activity_follow_up_table_aux_liver_function_alt_understanding,null);
//                understandingLayout.addView(view);
                nameTV.setText("血红蛋白（Hb）");
                normalConditionTV.setText("男性120-160g/L，女性110-150g/L");
                highConditionTV.setText("真性红细胞增多症，严重脱水，肺源性心脏病，先天性心脏病，严重烧伤，休克等。");
                lowConditionTV.setText("贫血，出血等。");
            }else if(name.equals("红细胞")){
                nameTV.setText("红细胞（RBC）");
                normalConditionTV.setText("（4.09-5.74）×10×12/L");
                highConditionTV.setText("真性红细胞增多症，严重脱水，肺源性心脏病，先天性心脏病，严重烧伤，休克等。");
                lowConditionTV.setText("贫血，出血等。");
            }else if(name.equals("白细胞")){
                nameTV.setText("白细胞（WBC）");
                normalConditionTV.setText("成人（4.0-10.0）×10×9/L");
                highConditionTV.setText("各种细菌感染，炎症，严重烧伤，也可能是白血病。");
                lowConditionTV.setText("白细胞减少症，脾功能亢进，造血功能障碍，放射线，药物，化学毒素等引起骨髓抑制，疟疾，伤寒，病毒感染，副伤寒。");
            }else if(name.equals("血小板")){
                nameTV.setText("血小板");
                normalConditionTV.setText("（100-300）×10×9/L");
                highConditionTV.setText("原发性血小板增多症，真性红细胞增多症，慢性白血病，骨髓纤维化，症状性血小板增多症，感染，炎症，恶性肿瘤，外伤，手术，出血，脾切除后的脾静脉血栓形成，运动后。");
                lowConditionTV.setText("原发性血小板减少性紫癜，播散性红斑狼疮，药物过敏性血小板减少症，弥漫性血管内凝血，血小板破坏增多，血小板生成减少，再生障碍性贫血，骨髓造血机能障碍，药物引起的骨髓抑制，脾功能亢进等。");
            }else if(name.equals("网织红细胞计数")){
                nameTV.setText("网织红细胞计数");
                normalConditionTV.setText("0.5%-1.5%");
                highConditionTV.setText("溶血性贫血，大量出血，缺铁性贫血，恶性贫血应用时。");
                lowConditionTV.setText("骨髓造血功能低下，再生障碍性贫血，白血病等。");
            }else if(name.equals("中性杆状核粒细胞")){
                nameTV.setText("中性杆状核粒细胞");
                normalConditionTV.setText("0.01-0.05（1%-5%）");
                highConditionTV.setText("细菌感染，炎症。");
                lowConditionTV.setText("病毒性感染等。");
            }else if(name.equals("中性分叶核粒细胞")){
                nameTV.setText("中性分叶核粒细胞");
                normalConditionTV.setText("0.50-0.70（50%-70%）");
                highConditionTV.setText("细菌感染，炎症等。");
                lowConditionTV.setText("病毒性感染等");
            }else if(name.equals("嗜酸性粒细胞")){
                nameTV.setText("嗜酸性粒细胞");
                normalConditionTV.setText("0.005-0.05（0.5%-5%）");
                highConditionTV.setText("慢性粒细胞性白血病及慢性溶血性贫血等。");
                lowConditionTV.setText("-");
            }else if(name.equals("淋巴细胞")){
                nameTV.setText("淋巴细胞");
                normalConditionTV.setText("0.20-0.40（20%-40%）");
                highConditionTV.setText("百日咳为传染性单核细胞增多症，病毒感染，急性传染性淋巴细胞增多症，淋巴细胞性白血病等。");
                lowConditionTV.setText("免疫缺陷等。");
            }else if(name.equals("单核细胞")){
                nameTV.setText("单核细胞");
                normalConditionTV.setText("0.03-0.08（3%-8%）");
                highConditionTV.setText("结核，伤寒，疟疾，单核细胞性白血病等。");
                lowConditionTV.setText("-");
            }else if(name.equals("中性粒细胞数")){
                nameTV.setText("中性粒细胞数");
                normalConditionTV.setText("（3.0-5.8）×10E9/L");
                highConditionTV.setText("急性和化脓性感染（疖痈、脓肿、肺炎、阑尾炎、丹毒、败血症、内脏穿孔、猩红热等），各种中毒（酸中毒、尿毒症、铅中毒、汞中毒等），组织损伤、恶性肿瘤、急性大出血、急性溶血等。");
                lowConditionTV.setText("见于伤寒、副伤寒、麻疹、流感等传染病、化疗、放疗。某些血液病（再生障碍性贫血、粒细胞缺乏症、骨髓增生异常综合征）、脾功能亢进、自身免疫性疾病等。");
            }else if(name.equals("中性粒细胞")){
                nameTV.setText("中性粒细胞");
                normalConditionTV.setText("55%-75%");
                highConditionTV.setText("急性和化脓性感染（疖痈、脓肿、肺炎、阑尾炎、丹毒、败血症、内脏穿孔、猩红热等），各种中毒（酸中毒、尿毒症、铅中毒、汞中毒等），组织损伤、恶性肿瘤、急性大出血、急性溶血等。");
                lowConditionTV.setText("见于伤寒、副伤寒、麻疹、流感等传染病、化疗、放疗。某些血液病（再生障碍性贫血、粒细胞缺乏症、骨髓增生异常综合征）、脾功能亢进、自身免疫性疾病等。");
            }else if(name.equals("单核细胞数")){
                nameTV.setText("单核细胞数");
                normalConditionTV.setText("(0.3-0.8)×10E9/L");
                highConditionTV.setText("结核，伤寒，疟疾，单核细胞性白血病等。");
                lowConditionTV.setText("-");
            }else if(name.equals("嗜碱性粒细胞数")){
                nameTV.setText("嗜碱性粒细胞数");
                normalConditionTV.setText("（0～0.1）×10E9/L");
                highConditionTV.setText("过敏反应，延迟过敏反应，慢性粒细胞白血病、骨髓纤维化症、恶性肿瘤骨髓转移、真性红细胞增多症、黏液水肿、溃疡性结肠炎、水痘、食物或药物等过敏、异种蛋白质过敏、肾病综合征、慢性溶血性贫血、霍奇金病、脾切除术后等。");
                lowConditionTV.setText("甲状腺功能亢进、妊娠、急性感染症、库欣综合征等。");
            }else if(name.equals("平均红细胞体积")){
                nameTV.setText("平均红细胞体积");
                normalConditionTV.setText("82-94fl");
                highConditionTV.setText("见于营养不良性巨幼红细胞性贫血（营养不良；吸收不良；胃切除术后、肠病、裂头绦虫等寄生虫病；及恶性贫血、混合缺乏、叶酸、B12、癌；遗传原因）。酒精性肝硬化、胰外功能不全、获得性溶血性贫血、出血性贫血再生之后和甲状腺功能低下。");
                lowConditionTV.setText("见于小细胞低色素贫血（由癌或感染引起的继发性贫血；高铁血症见于铁粒幼红细胞贫血和铅中毒及CO中毒），全身性溶血性贫血（地中海贫血、遗传性球形红细胞增多症、先天性丙酮酸激酶缺乏症）。");
            }else if(name.equals("RBC分布宽度")){
                nameTV.setText("红细胞分布宽度");
                normalConditionTV.setText("11%-14.5%");
                highConditionTV.setText("超过正常值多提示各种贫血、造血异常或者先天性红细胞异常；");
                lowConditionTV.setText("分布宽度小，说明样本血液红细胞形态大小一致，很整齐。");
            }else if(name.equals("平均血小板体积")){
                nameTV.setText("平均血小板体积");
                normalConditionTV.setText("6.5-13fl");
                highConditionTV.setText("骨髓纤维化、原发性血小板减少性紫癜、血栓性疾病及血栓前状态。脾切除、慢粒、巨大血小板综合症、镰刀细胞性贫血等。");
                lowConditionTV.setText("脾亢、化疗后、再障、巨幼细胞性贫血等。");
            }else if(name.equals("嗜碱性粒细胞")){
                nameTV.setText("嗜碱性粒细胞");
                normalConditionTV.setText("0%-1%");
                highConditionTV.setText("嗜碱性粒细胞白血病（罕见）、慢性粒细胞性白血病常伴有嗜碱性粒细胞增高，骨髓纤维化症、慢性溶血及脾切除后也可见嗜碱性粒细胞增高。");
                lowConditionTV.setText("Ⅰ型超敏反应（速发型变态反应），如荨麻疹、过敏性休克等；促肾上腺皮质激素及糖皮质激素过量；甲亢；库欣症等；应激反应，如心梗、严重感染、出血等。");
            }else if(name.equals("淋巴细胞数")){
                nameTV.setText("淋巴细胞数");
                normalConditionTV.setText("（1.5-3.0）×10E9/L");
                highConditionTV.setText("见于病毒感染、结核病、百日咳、传染性单核细胞增多症、传染性淋巴细胞增多症、淋巴细胞白血病、淋巴肉瘤。");
                lowConditionTV.setText("见于细胞免疫缺陷病、某些传染病的急性期、放射病、应用肾上腺皮质激素、抗淋巴细胞球蛋白治疗、淋巴细胞减少症、免疫缺陷病、丙种球蛋白缺乏症等等。");
            }else if(name.equals("嗜酸性粒细胞数")){
                nameTV.setText("嗜酸性粒细胞数");
                normalConditionTV.setText("（0.05-0.50）×10E9/L");
                highConditionTV.setText("过敏性疾患、皮肤病、寄生虫病、感染症、血液病、其他如放射线照射后、卵巢肿瘤、肉样瘤病、肾上腺皮质功能减退症等。");
                lowConditionTV.setText("常见于伤寒、副伤寒初期，大手术、烧伤等应激状态，或长期应用肾上腺皮质激素后");
            }else if(name.equals("红细胞压积")){
                nameTV.setText("红细胞压积");
                normalConditionTV.setText("0.40～0.50L/L");
                highConditionTV.setText("各种原因所致血液浓缩如大量呕吐、腹泻、大面积烧伤后有大量创面渗出液等，测定红细胞压积以了解血液浓缩程度，可作为补液量的依据。真性红细胞增多症有时可高达80%左右。继发性红细胞增多症系体内氧供应不足引起的代偿反应如新生儿、高原地区居住者及慢性心肺疾患等。");
                lowConditionTV.setText("见于各种贫血或血液稀释。由于贫血类型不同，红细胞计数与红细胞比积的降低不一定成比例，故可以根据红细胞比积和红细胞计数血红蛋白的量计算红细胞三种平均值，以有助于贫血的鉴别和分类。");
            }else if(name.equals("平均血红蛋白量")){
                nameTV.setText("平均血红蛋白量");
                normalConditionTV.setText("26-32pg");
                highConditionTV.setText("提示体内铁质丰富，无需再补铁。若体内含铁量高，会引起冠状动脉粥样硬化等心血管疾病。");
                lowConditionTV.setText("表示体内缺铁，进而影响血红蛋白的合成和携氧能力，造成贫血，阻碍身体各项生理活动的正常进行。");
            }else if(name.equals("平均血红蛋白浓度")){
                nameTV.setText("平均血红蛋白浓度");
                normalConditionTV.setText("320-360g/L");
                highConditionTV.setText("严重呕吐，腹泻，大量出汗，大面积烧伤病人，尿崩症，甲状腺功能亢进危象，糖尿病酸中毒等;某些肿瘤，如肾癌，肝细胞癌，子宫肌瘤，卵巢癌，肾胚胎癌等也可使促红细胞生成素呈非代偿性增加，导致上述的结果;真性红细胞增多症;");
                lowConditionTV.setText("-");
            }else if(name.equals("血小板分布宽度")){
                nameTV.setText("血小板分布宽度");
                normalConditionTV.setText("15.5%-18.1%");
                highConditionTV.setText("表明血小板大小悬殊，见于急性髓系白血病、巨幼细胞贫血、慢性粒细胞白血病、脾切除、巨大血小板综合征、血栓性疾病等；");
                lowConditionTV.setText("表明血小板的均一性高");
            }else if(name.equals("RBC分布绝对值")){
                nameTV.setText("红细胞分布绝对值");
                normalConditionTV.setText("37.1-49.2fl");
                highConditionTV.setText("样本血液中红细胞形状大小各部一样,超过正常值多提示各种贫血,造血异常或者先天性红细胞异常");
                lowConditionTV.setText("红细胞比正常人的红细胞更整齐,临床意义不大");
            }
        }
    }
}
