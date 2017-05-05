package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.followup.view.MyTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * 生化全项
 * Created by Lenovo on 2016/11/15.
 */
public class FUTableDiabetesBiochemicalItemsView extends ViewGroup {
    private Context mContext;
    private LinearLayout parentView;
    String TAG="FUTableDiabetesBiochemicalItemsView";
    View biochemicalView;

    String from;

    String name="生化全项";

    public FUTableDiabetesBiochemicalItemsView(Context context) {
        super(context);
        mContext = context;
//        setContentView(R.layout.activity_follow_up_table_diabetes_aux_urine_routine);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public FUTableDiabetesBiochemicalItemsView(Context context, LinearLayout parent,String from){
        this(context);
        Log.i(TAG, "parent " + parent);
        parentView = parent;
        this.from = from;
        initView();
    }
    ImageButton deleteBtn;

    MyTextView gluTV;
    EditText gluET;
    TextView gluLevelTV,gluHighValueTV,gluLowValueTV;
    String gluHighValue,gluLowValue;

    MyTextView gptTV;
    EditText gptET;
    TextView gptLevelTV,gptHighValueTV,gptLowValueTV;
    String gptHighValue,gptLowValue;

    MyTextView gotTV;
    EditText gotET;
    TextView gotLevelTV,gotHighValueTV,gotLowValueTV;
    String gotHighValue,gotLowValue;

    MyTextView tpTV;
    EditText tpET;
    TextView tpLevelTV,tpHighValueTV,tpLowValueTV;
    String tpHighValue,tpLowValue;

    MyTextView gloTV;
    EditText gloET;
    TextView gloLevelTV,gloHighValueTV,gloLowValueTV;
    String gloHighValue,gloLowValue;

    MyTextView albTV;
    EditText albET;
    TextView albLevelTV,albHighValueTV,albLowValueTV;
    String albHighValue,albLowValue;

    MyTextView agTV;
    EditText agET;
    TextView agLevelTV,agHighValueTV,agLowValueTV;
    String agHighValue,agLowValue;

    MyTextView tbiTV;
    EditText tbiET;
    TextView tbiLevelTV,tbiHighValueTV,tbiLowValueTV;
    String tbiHighValue,tbiLowValue;

    MyTextView dbiTV;
    EditText dbiET;
    TextView dbiLevelTV,dbiHighValueTV,dbiLowValueTV;
    String dbiHighValue,dbiLowValue;

    MyTextView ibilTV;
    EditText ibilET;
    TextView ibilLevelTV,ibilHighValueTV,ibilLowValueTV;
    String ibilHighValue,ibilLowValue;

    MyTextView ggtTV;
    EditText ggtET;
    TextView ggtLevelTV,ggtHighValueTV,ggtLowValueTV;
    String ggtHighValue,ggtLowValue;

    MyTextView alpTV;
    EditText alpET;
    TextView alpLevelTV,alpHighValueTV,alpLowValueTV;
    String alpHighValue,alpLowValue;

    MyTextView creTV;
    EditText creET;
    TextView creLevelTV,creHighValueTV,creLowValueTV;
    String creHighValue,creLowValue;

    MyTextView bunTV;
    EditText bunET;
    TextView bunLevelTV,bunHighValueTV,bunLowValueTV;
    String bunHighValue,bunLowValue;

    MyTextView uaTV;
    EditText uaET;
    TextView uaLevelTV,uaHighValueTV,uaLowValueTV;
    String uaHighValue,uaLowValue;

    MyTextView choTV;
    EditText choET;
    TextView choLevelTV,choHighValueTV,choLowValueTV;
    String choHighValue,choLowValue;

    MyTextView tgTV;
    EditText tgET;
    TextView tgLevelTV,tgHighValueTV,tgLowValueTV;
    String tgHighValue,tgLowValue;

    MyTextView idlTV;
    EditText idlET;
    TextView idlLevelTV,idlHighValueTV,idlLowValueTV;
    String idlHighValue,idlLowValue;

    MyTextView ldlTV;
    EditText ldlET;
    TextView ldlLevelTV,ldlHighValueTV,ldlLowValueTV;
    String ldlHighValue,ldlLowValue;

    MyTextView vldlTV;
    EditText vldlET;
    TextView vldlLevelTV,vldlHighValueTV,vldlLowValueTV;
    String vldlHighValue,vldlLowValue;

    MyTextView apoaTV;
    EditText apoaET;
    TextView apoaLevelTV,apoaHighValueTV,apoaLowValueTV;
    String apoaHighValue,apoaLowValue;

    MyTextView apobTV;
    EditText apobET;
    TextView apobLevelTV,apobHighValueTV,apobLowValueTV;
    String apobHighValue,apobLowValue;

    MyTextView ckTV;
    EditText ckET;
    TextView ckLevelTV,ckHighValueTV,ckLowValueTV;
    String ckHighValue,ckLowValue;

    MyTextView ckmbTV;
    EditText ckmbET;
    TextView ckmbLevelTV,ckmbHighValueTV,ckmbLowValueTV;
    String ckmbHighValue,ckmbLowValue;

    MyTextView ldhTV;
    EditText ldhET;
    TextView ldhLevelTV,ldhHighValueTV,ldhLowValueTV;
    String ldhHighValue,ldhLowValue;

    MyTextView amyTV;
    EditText amyET;
    TextView amyLevelTV,amyHighValueTV,amyLowValueTV;
    String amyHighValue,amyLowValue;

    MyTextView lpaTV;
    EditText lpaET;
    TextView lpaLevelTV,lpaHighValueTV,lpaLowValueTV;
    String lpaHighValue,lpaLowValue;

    MyTextView co2TV;
    EditText co2ET;
    TextView co2LevelTV,co2HighValueTV,co2LowValueTV;
    String co2HighValue,co2LowValue;

    MyTextView tbaTV;
    EditText tbaET;
    TextView tbaLevelTV,tbaHighValueTV,tbaLowValueTV;
    String tbaHighValue,tbaLowValue;

    MyTextView cyscTV;
    EditText cyscET;
    TextView cyscLevelTV,cyscHighValueTV,cyscLowValueTV;
    String cyscHighValue,cyscLowValue;

    MyTextView hcyTV;
    EditText hcyET;
    TextView hcyLevelTV,hcyHighValueTV,hcyLowValueTV;
    String hcyHighValue,hcyLowValue;

    private TextView dateTV,timeTV;
    int year,month,day;
    int hour,min;
    private void initView(){
        LayoutInflater inflater =LayoutInflater.from(mContext);
        biochemicalView = inflater.inflate(R.layout.activity_follow_up_table_diabetes_aux_biochemical_items, null);
        Log.i(TAG, "biochemicalView " + biochemicalView);
        Log.i(TAG, "parentView " + parentView);
        parentView.addView(biochemicalView);
//        this.addView(biochemicalView);

        deleteBtn = (ImageButton)biochemicalView.findViewById(R.id.biochemical_delete_image);
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parentView.removeView(biochemicalView);
                if(from.equals("糖尿病")){
                    FUTableDiabetesActivity activity = (FUTableDiabetesActivity)mContext;
                    activity.removeAuxMap(name);
                }

            }
        });


        gluET = (EditText)biochemicalView.findViewById(R.id.glu_et);
        gluLevelTV = (TextView)biochemicalView.findViewById(R.id.glu_level_tv);
        gluHighValueTV = (TextView)biochemicalView.findViewById(R.id.glu_high_normal_tv);
        gluLowValueTV = (TextView)biochemicalView.findViewById(R.id.glu_low_normal_tv);

        gluHighValue = gluHighValueTV.getText().toString();
        gluLowValue = gluLowValueTV.getText().toString();
        gluET.addTextChangedListener(biochemicalTextWatcher);


        gptET = (EditText)biochemicalView.findViewById(R.id.gpt_et);
        gptLevelTV = (TextView)biochemicalView.findViewById(R.id.gpt_level_tv);
        gptHighValueTV = (TextView)biochemicalView.findViewById(R.id.gpt_high_normal_tv);
        gptLowValueTV = (TextView)biochemicalView.findViewById(R.id.gpt_low_normal_tv);

        gptHighValue = gptHighValueTV.getText().toString();
        gptLowValue = gptLowValueTV.getText().toString();
        gptET.addTextChangedListener(biochemicalTextWatcher);

        gotET = (EditText)biochemicalView.findViewById(R.id.got_et);
        gotLevelTV = (TextView)biochemicalView.findViewById(R.id.got_level_tv);
        gotHighValueTV = (TextView)biochemicalView.findViewById(R.id.got_high_normal_tv);
        gotLowValueTV = (TextView)biochemicalView.findViewById(R.id.got_low_normal_tv);

        gotHighValue = gotHighValueTV.getText().toString();
        gotLowValue = gotLowValueTV.getText().toString();
        gotET.addTextChangedListener(biochemicalTextWatcher);

        tpET = (EditText)biochemicalView.findViewById(R.id.tp_et);
        tpLevelTV = (TextView)biochemicalView.findViewById(R.id.tp_level_tv);
        tpHighValueTV = (TextView)biochemicalView.findViewById(R.id.tp_high_normal_tv);
        tpLowValueTV = (TextView)biochemicalView.findViewById(R.id.tp_low_normal_tv);

        tpHighValue = tpHighValueTV.getText().toString();
        tpLowValue = tpLowValueTV.getText().toString();
        tpET.addTextChangedListener(biochemicalTextWatcher);

        albET = (EditText)biochemicalView.findViewById(R.id.alb_et);
        albLevelTV = (TextView)biochemicalView.findViewById(R.id.alb_level_tv);
        albHighValueTV = (TextView)biochemicalView.findViewById(R.id.alb_high_normal_tv);
        albLowValueTV = (TextView)biochemicalView.findViewById(R.id.alb_low_normal_tv);

        albHighValue = albHighValueTV.getText().toString();
        albLowValue = albLowValueTV.getText().toString();
        albET.addTextChangedListener(biochemicalTextWatcher);

        gloET = (EditText)biochemicalView.findViewById(R.id.glo_et);
        gloLevelTV = (TextView)biochemicalView.findViewById(R.id.glo_level_tv);
        gloHighValueTV = (TextView)biochemicalView.findViewById(R.id.glo_high_normal_tv);
        gloLowValueTV = (TextView)biochemicalView.findViewById(R.id.glo_low_normal_tv);

        gloHighValue = gloHighValueTV.getText().toString();
        gloLowValue = gloLowValueTV.getText().toString();
        gloET.addTextChangedListener(biochemicalTextWatcher);

        agET = (EditText)biochemicalView.findViewById(R.id.ag_et);
        agLevelTV = (TextView)biochemicalView.findViewById(R.id.ag_level_tv);
        agHighValueTV = (TextView)biochemicalView.findViewById(R.id.ag_high_normal_tv);
        agLowValueTV = (TextView)biochemicalView.findViewById(R.id.ag_low_normal_tv);

        agHighValue = agHighValueTV.getText().toString();
        agLowValue = agLowValueTV.getText().toString();
        agET.addTextChangedListener(biochemicalTextWatcher);

        tbiET = (EditText)biochemicalView.findViewById(R.id.tbi_et);
        tbiLevelTV = (TextView)biochemicalView.findViewById(R.id.tbi_level_tv);
        tbiHighValueTV = (TextView)biochemicalView.findViewById(R.id.tbi_high_normal_tv);
        tbiLowValueTV = (TextView)biochemicalView.findViewById(R.id.tbi_low_normal_tv);

        tbiHighValue = tbiHighValueTV.getText().toString();
        tbiLowValue = tbiLowValueTV.getText().toString();
        tbiET.addTextChangedListener(biochemicalTextWatcher);

        dbiET = (EditText)biochemicalView.findViewById(R.id.dbi_et);
        dbiLevelTV = (TextView)biochemicalView.findViewById(R.id.dbi_level_tv);
        dbiHighValueTV = (TextView)biochemicalView.findViewById(R.id.dbi_high_normal_tv);
        dbiLowValueTV = (TextView)biochemicalView.findViewById(R.id.dbi_low_normal_tv);

        dbiHighValue = dbiHighValueTV.getText().toString();
        dbiLowValue = dbiLowValueTV.getText().toString();
        dbiET.addTextChangedListener(biochemicalTextWatcher);

        ibilET = (EditText)biochemicalView.findViewById(R.id.ibil_et);
        ibilLevelTV = (TextView)biochemicalView.findViewById(R.id.ibil_level_tv);
        ibilHighValueTV = (TextView)biochemicalView.findViewById(R.id.ibil_high_normal_tv);
        ibilLowValueTV = (TextView)biochemicalView.findViewById(R.id.ibil_low_normal_tv);

        ibilHighValue = ibilHighValueTV.getText().toString();
        ibilLowValue = ibilLowValueTV.getText().toString();
        ibilET.addTextChangedListener(biochemicalTextWatcher);

        ggtET = (EditText)biochemicalView.findViewById(R.id.ggt_et);
        ggtLevelTV = (TextView)biochemicalView.findViewById(R.id.ggt_level_tv);
        ggtHighValueTV = (TextView)biochemicalView.findViewById(R.id.ggt_high_normal_tv);
        ggtLowValueTV = (TextView)biochemicalView.findViewById(R.id.ggt_low_normal_tv);

        ggtHighValue = ggtHighValueTV.getText().toString();
        ggtLowValue = ggtLowValueTV.getText().toString();
        ggtET.addTextChangedListener(biochemicalTextWatcher);

        alpET = (EditText)biochemicalView.findViewById(R.id.alp_et);
        alpLevelTV = (TextView)biochemicalView.findViewById(R.id.alp_level_tv);
        alpHighValueTV = (TextView)biochemicalView.findViewById(R.id.alp_high_normal_tv);
        alpLowValueTV = (TextView)biochemicalView.findViewById(R.id.alp_low_normal_tv);

        alpHighValue = alpHighValueTV.getText().toString();
        alpLowValue = alpLowValueTV.getText().toString();
        alpET.addTextChangedListener(biochemicalTextWatcher);

        creET = (EditText)biochemicalView.findViewById(R.id.cre_et);
        creLevelTV = (TextView)biochemicalView.findViewById(R.id.cre_level_tv);
        creHighValueTV = (TextView)biochemicalView.findViewById(R.id.cre_high_normal_tv);
        creLowValueTV = (TextView)biochemicalView.findViewById(R.id.cre_low_normal_tv);

        creHighValue = creHighValueTV.getText().toString();
        creLowValue = creLowValueTV.getText().toString();
        creET.addTextChangedListener(biochemicalTextWatcher);

        bunET = (EditText)biochemicalView.findViewById(R.id.bun_et);
        bunLevelTV = (TextView)biochemicalView.findViewById(R.id.bun_level_tv);
        bunHighValueTV = (TextView)biochemicalView.findViewById(R.id.bun_high_normal_tv);
        bunLowValueTV = (TextView)biochemicalView.findViewById(R.id.bun_low_normal_tv);

        bunHighValue = bunHighValueTV.getText().toString();
        bunLowValue = bunLowValueTV.getText().toString();
        bunET.addTextChangedListener(biochemicalTextWatcher);

        uaET = (EditText)biochemicalView.findViewById(R.id.ua_et);
        uaLevelTV = (TextView)biochemicalView.findViewById(R.id.ua_level_tv);
        uaHighValueTV = (TextView)biochemicalView.findViewById(R.id.ua_high_normal_tv);
        uaLowValueTV = (TextView)biochemicalView.findViewById(R.id.ua_low_normal_tv);

        uaHighValue = uaHighValueTV.getText().toString();
        uaLowValue = uaLowValueTV.getText().toString();
        uaET.addTextChangedListener(biochemicalTextWatcher);


        choET = (EditText)biochemicalView.findViewById(R.id.cho_et);
        choLevelTV = (TextView)biochemicalView.findViewById(R.id.cho_level_tv);
        choHighValueTV = (TextView)biochemicalView.findViewById(R.id.cho_high_normal_tv);
        choLowValueTV = (TextView)biochemicalView.findViewById(R.id.cho_low_normal_tv);

        choHighValue = choHighValueTV.getText().toString();
        choLowValue = choLowValueTV.getText().toString();
        choET.addTextChangedListener(biochemicalTextWatcher);

        tgET = (EditText)biochemicalView.findViewById(R.id.tg_et);
        tgLevelTV = (TextView)biochemicalView.findViewById(R.id.tg_level_tv);
        tgHighValueTV = (TextView)biochemicalView.findViewById(R.id.tg_high_normal_tv);
        tgLowValueTV = (TextView)biochemicalView.findViewById(R.id.tg_low_normal_tv);

        tgHighValue = tgHighValueTV.getText().toString();
        tgLowValue = tgLowValueTV.getText().toString();
        tgET.addTextChangedListener(biochemicalTextWatcher);


        idlET = (EditText)biochemicalView.findViewById(R.id.idl_et);
        idlLevelTV = (TextView)biochemicalView.findViewById(R.id.idl_level_tv);
        idlHighValueTV = (TextView)biochemicalView.findViewById(R.id.idl_high_normal_tv);
        idlLowValueTV = (TextView)biochemicalView.findViewById(R.id.idl_low_normal_tv);

        idlHighValue = idlHighValueTV.getText().toString();
        idlLowValue = idlLowValueTV.getText().toString();
        idlET.addTextChangedListener(biochemicalTextWatcher);

        ldlET = (EditText)biochemicalView.findViewById(R.id.ldl_et);
        ldlLevelTV = (TextView)biochemicalView.findViewById(R.id.ldl_level_tv);
        ldlHighValueTV = (TextView)biochemicalView.findViewById(R.id.ldl_high_normal_tv);
        ldlLowValueTV = (TextView)biochemicalView.findViewById(R.id.ldl_low_normal_tv);

        ldlHighValue = ldlHighValueTV.getText().toString();
        ldlLowValue = ldlLowValueTV.getText().toString();
        ldlET.addTextChangedListener(biochemicalTextWatcher);

        vldlET = (EditText)biochemicalView.findViewById(R.id.vldl_et);
        vldlLevelTV = (TextView)biochemicalView.findViewById(R.id.vldl_level_tv);
        vldlHighValueTV = (TextView)biochemicalView.findViewById(R.id.vldl_high_normal_tv);
        vldlLowValueTV = (TextView)biochemicalView.findViewById(R.id.vldl_low_normal_tv);

        vldlHighValue = vldlHighValueTV.getText().toString();
        vldlLowValue = vldlLowValueTV.getText().toString();
        vldlET.addTextChangedListener(biochemicalTextWatcher);

        apoaET = (EditText)biochemicalView.findViewById(R.id.apoa_et);
        apoaLevelTV = (TextView)biochemicalView.findViewById(R.id.apoa_level_tv);
        apoaHighValueTV = (TextView)biochemicalView.findViewById(R.id.apoa_high_normal_tv);
        apoaLowValueTV = (TextView)biochemicalView.findViewById(R.id.apoa_low_normal_tv);

        apoaHighValue = apoaHighValueTV.getText().toString();
        apoaLowValue = apoaLowValueTV.getText().toString();
        apoaET.addTextChangedListener(biochemicalTextWatcher);

        apobET = (EditText)biochemicalView.findViewById(R.id.apob_et);
        apobLevelTV = (TextView)biochemicalView.findViewById(R.id.apob_level_tv);
        apobHighValueTV = (TextView)biochemicalView.findViewById(R.id.apob_high_normal_tv);
        apobLowValueTV = (TextView)biochemicalView.findViewById(R.id.apob_low_normal_tv);

        apobHighValue = apobHighValueTV.getText().toString();
        apobLowValue = apobLowValueTV.getText().toString();
        apobET.addTextChangedListener(biochemicalTextWatcher);

        ckET = (EditText)biochemicalView.findViewById(R.id.ck_et);
        ckLevelTV = (TextView)biochemicalView.findViewById(R.id.ck_level_tv);
        ckHighValueTV = (TextView)biochemicalView.findViewById(R.id.ck_high_normal_tv);
        ckLowValueTV = (TextView)biochemicalView.findViewById(R.id.ck_low_normal_tv);

        ckHighValue = ckHighValueTV.getText().toString();
        ckLowValue = ckLowValueTV.getText().toString();
        ckET.addTextChangedListener(biochemicalTextWatcher);

        ckmbET = (EditText)biochemicalView.findViewById(R.id.ckmb_et);
        ckmbLevelTV = (TextView)biochemicalView.findViewById(R.id.ckmb_level_tv);
        ckmbHighValueTV = (TextView)biochemicalView.findViewById(R.id.ckmb_high_normal_tv);
        ckmbLowValueTV = (TextView)biochemicalView.findViewById(R.id.ckmb_low_normal_tv);

        ckmbHighValue = ckmbHighValueTV.getText().toString();
        ckmbLowValue = ckmbLowValueTV.getText().toString();
        ckmbET.addTextChangedListener(biochemicalTextWatcher);

        ldhET = (EditText)biochemicalView.findViewById(R.id.ldh_et);
        ldhLevelTV = (TextView)biochemicalView.findViewById(R.id.ldh_level_tv);
        ldhHighValueTV = (TextView)biochemicalView.findViewById(R.id.ldh_high_normal_tv);
        ldhLowValueTV = (TextView)biochemicalView.findViewById(R.id.ldh_low_normal_tv);

        ldhHighValue = ldhHighValueTV.getText().toString();
        ldhLowValue = ldhLowValueTV.getText().toString();
        ldhET.addTextChangedListener(biochemicalTextWatcher);

        amyET = (EditText)biochemicalView.findViewById(R.id.amy_et);
        amyLevelTV = (TextView)biochemicalView.findViewById(R.id.amy_level_tv);
        amyHighValueTV = (TextView)biochemicalView.findViewById(R.id.amy_high_normal_tv);
        amyLowValueTV = (TextView)biochemicalView.findViewById(R.id.amy_low_normal_tv);

        amyHighValue = amyHighValueTV.getText().toString();
        amyLowValue = amyLowValueTV.getText().toString();
        amyET.addTextChangedListener(biochemicalTextWatcher);

        lpaET = (EditText)biochemicalView.findViewById(R.id.lpa_et);
        lpaLevelTV = (TextView)biochemicalView.findViewById(R.id.lpa_level_tv);
        lpaHighValueTV = (TextView)biochemicalView.findViewById(R.id.lpa_high_normal_tv);
        lpaLowValueTV = (TextView)biochemicalView.findViewById(R.id.lpa_low_normal_tv);

        lpaHighValue = lpaHighValueTV.getText().toString();
        lpaLowValue = lpaLowValueTV.getText().toString();
        lpaET.addTextChangedListener(biochemicalTextWatcher);

        co2ET = (EditText)biochemicalView.findViewById(R.id.co2_et);
        co2LevelTV = (TextView)biochemicalView.findViewById(R.id.co2_level_tv);
        co2HighValueTV = (TextView)biochemicalView.findViewById(R.id.co2_high_normal_tv);
        co2LowValueTV = (TextView)biochemicalView.findViewById(R.id.co2_low_normal_tv);

        co2HighValue = co2HighValueTV.getText().toString();
        co2LowValue = co2LowValueTV.getText().toString();
        co2ET.addTextChangedListener(biochemicalTextWatcher);

        tbaET = (EditText)biochemicalView.findViewById(R.id.tba_et);
        tbaLevelTV = (TextView)biochemicalView.findViewById(R.id.tba_level_tv);
        tbaHighValueTV = (TextView)biochemicalView.findViewById(R.id.tba_high_normal_tv);
        tbaLowValueTV = (TextView)biochemicalView.findViewById(R.id.tba_low_normal_tv);

        tbaHighValue = tbaHighValueTV.getText().toString();
        tbaLowValue = tbaLowValueTV.getText().toString();
        tbaET.addTextChangedListener(biochemicalTextWatcher);

        cyscET = (EditText)biochemicalView.findViewById(R.id.cysc_et);
        cyscLevelTV = (TextView)biochemicalView.findViewById(R.id.cysc_level_tv);
        cyscHighValueTV = (TextView)biochemicalView.findViewById(R.id.cysc_high_normal_tv);
        cyscLowValueTV = (TextView)biochemicalView.findViewById(R.id.cysc_low_normal_tv);

        cyscHighValue = cyscHighValueTV.getText().toString();
        cyscLowValue = cyscLowValueTV.getText().toString();
        cyscET.addTextChangedListener(biochemicalTextWatcher);

        hcyET = (EditText)biochemicalView.findViewById(R.id.hcy_et);
        hcyLevelTV = (TextView)biochemicalView.findViewById(R.id.hcy_level_tv);
        hcyHighValueTV = (TextView)biochemicalView.findViewById(R.id.hcy_high_normal_tv);
        hcyLowValueTV = (TextView)biochemicalView.findViewById(R.id.hcy_low_normal_tv);

        hcyHighValue = hcyHighValueTV.getText().toString();
        hcyLowValue = hcyLowValueTV.getText().toString();
        hcyET.addTextChangedListener(biochemicalTextWatcher);

        dateTV = (TextView)biochemicalView.findViewById(R.id.date_tv);
        timeTV = (TextView)biochemicalView.findViewById(R.id.time_tv);

        //初始化记录时间，以后可能从数据库读取
        //初始化Calendar日历对象
        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
        Date mydate=new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        hour = mycalendar.get(Calendar.HOUR_OF_DAY);
        min = mycalendar.get(Calendar.MINUTE);
        dateTV.setText(+year+"-"+(month+1)+"-"+day); //显示当前的年月日

        timeTV.setText(hour+":"+min);

        dateTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建DatePickerDialog对象
                DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, Datelistener, year, month, day);
                dpd.show();//显示DatePickerDialog组件


            }
        });
        timeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,TimePickerDialog.THEME_HOLO_LIGHT,timeListener,hour,min,true);
                timePickerDialog.show();
            }
        });

        initChartData(biochemicalView);

    }

    private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {


            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();

        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            //在TextView上显示日期
            dateTV.setText(year+"-"+(month+1)+"-"+day);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour=hourOfDay;
            min=minute;
            //更新时间
            updateTime();
        }
        private void updateTime()
        {
            //在TextView上显示日期
            timeTV.setText(hour+":"+min);
        }
    };
    private void initChartData(View view){

        gluTV = (MyTextView)view.findViewById(R.id.glu_tv);
        gptTV = (MyTextView)view.findViewById(R.id.gpt_tv);
        gotTV = (MyTextView)view.findViewById(R.id.got_tv);
        tpTV = (MyTextView)view.findViewById(R.id.tp_tv);
        albTV = (MyTextView)view.findViewById(R.id.alb_tv);
        gloTV = (MyTextView)view.findViewById(R.id.glo_tv);
        agTV = (MyTextView)view.findViewById(R.id.ag_tv);
        tbiTV = (MyTextView)view.findViewById(R.id.tbi_tv);
        dbiTV = (MyTextView)view.findViewById(R.id.dbi_tv);
        ibilTV = (MyTextView)view.findViewById(R.id.ibil_tv);
        ggtTV = (MyTextView)view.findViewById(R.id.ggt_tv);
        alpTV = (MyTextView)view.findViewById(R.id.alp_tv);
        creTV = (MyTextView)view.findViewById(R.id.cre_tv);
        bunTV = (MyTextView)view.findViewById(R.id.bun_tv);
        uaTV = (MyTextView)view.findViewById(R.id.ua_tv);
        choTV = (MyTextView)view.findViewById(R.id.cho_tv);
        tgTV = (MyTextView)view.findViewById(R.id.tg_tv);
        idlTV = (MyTextView)view.findViewById(R.id.idl_tv);
        ldlTV = (MyTextView)view.findViewById(R.id.ldl_tv);
        vldlTV = (MyTextView)view.findViewById(R.id.vldl_tv);

        apoaTV = (MyTextView)view.findViewById(R.id.apoa_tv);
        apobTV = (MyTextView)view.findViewById(R.id.apob_tv);
        ckTV = (MyTextView)view.findViewById(R.id.ck_tv);
        ckmbTV = (MyTextView)view.findViewById(R.id.ckmb_tv);
        ldhTV = (MyTextView)view.findViewById(R.id.ldh_tv);
        amyTV = (MyTextView)view.findViewById(R.id.amy_tv);
        lpaTV = (MyTextView)view.findViewById(R.id.lpa_tv);
        co2TV = (MyTextView)view.findViewById(R.id.co2_tv);
        tbaTV = (MyTextView)view.findViewById(R.id.tba_tv);
        cyscTV = (MyTextView)view.findViewById(R.id.cysc_tv);
        hcyTV = (MyTextView)view.findViewById(R.id.hcy_tv);


        double[] radarViewData = {30,60,60,60,80,50,10,20}; //各维度分值
        String[] radarViewTitles = {"测试1","测试2","测试3","测试4","测试5","测试6"};//各维度名称

        int yMax = 100; //y轴最大值
        int ySteps = 10;//Y轴间隔值
        int yDetailSteps = 1;//Y轴文字显示间隔数，Y轴每隔ySteps*yDetailSteps显示一次文字，如10*1=10，在0，10，20...处显示文字
        //X轴数值
        LinkedList<String> xLabels = new LinkedList<>();

        //线段文字集合
        List<String> dataTextList = new ArrayList<>();
        //线段颜色集合，若为null，则默认绿色；若成员HashMap<String,Integer>是-1，-1，-1，则设置成默认值绿色58,171,119
        List<HashMap<String,Integer>>dataColorList = new ArrayList<>();
        //线段值集合
        List<LinkedList<Double>>dataList = new ArrayList<>();

        for(int i=0;i<8;i++){
            xLabels.add(i+"");
        }

        dataTextList.add("低");

        HashMap<String,Integer> map = new HashMap<>();
        map.put("r", 245);
        map.put("g", 30);
        map.put("b", 150);
        dataColorList.add(map);

        LinkedList<Double> dataSeries1= new LinkedList<Double>();
        dataSeries1.add(40d);
        dataSeries1.add(48d);
        dataSeries1.add(50d);
        dataSeries1.add(56d);

        dataList.add(dataSeries1);

        //设置六边形数据
        gluTV.setRadarViewData(radarViewData);
        gluTV.setRadarViewMaxValue(90);
        gluTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        gluTV.setLineData(gluTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        gptTV.setRadarViewData(radarViewData);
        gptTV.setRadarViewMaxValue(90);
        gptTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        gptTV.setLineData(gptTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        gotTV.setRadarViewData(radarViewData);
        gotTV.setRadarViewMaxValue(90);
        gotTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        gotTV.setLineData(gotTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        tpTV.setRadarViewData(radarViewData);
        tpTV.setRadarViewMaxValue(90);
        tpTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        tpTV.setLineData(tpTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        albTV.setRadarViewData(radarViewData);
        albTV.setRadarViewMaxValue(90);
        albTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        albTV.setLineData(albTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        gloTV.setRadarViewData(radarViewData);
        gloTV.setRadarViewMaxValue(90);
        gloTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        gloTV.setLineData(gloTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        agTV.setRadarViewData(radarViewData);
        agTV.setRadarViewMaxValue(90);
        agTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        agTV.setLineData(agTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        tbiTV.setRadarViewData(radarViewData);
        tbiTV.setRadarViewMaxValue(90);
        tbiTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        tbiTV.setLineData(tbiTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        dbiTV.setRadarViewData(radarViewData);
        dbiTV.setRadarViewMaxValue(90);
        dbiTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        dbiTV.setLineData(dbiTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        ibilTV.setRadarViewData(radarViewData);
        ibilTV.setRadarViewMaxValue(90);
        ibilTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ibilTV.setLineData(ibilTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        ggtTV.setRadarViewData(radarViewData);
        ggtTV.setRadarViewMaxValue(90);
        ggtTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ggtTV.setLineData(ggtTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        alpTV.setRadarViewData(radarViewData);
        alpTV.setRadarViewMaxValue(90);
        alpTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        alpTV.setLineData(alpTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        creTV.setRadarViewData(radarViewData);
        creTV.setRadarViewMaxValue(90);
        creTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        creTV.setLineData(creTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        bunTV.setRadarViewData(radarViewData);
        bunTV.setRadarViewMaxValue(90);
        bunTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        bunTV.setLineData(bunTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        uaTV.setRadarViewData(radarViewData);
        uaTV.setRadarViewMaxValue(90);
        uaTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        uaTV.setLineData(uaTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        choTV.setRadarViewData(radarViewData);
        choTV.setRadarViewMaxValue(90);
        choTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        choTV.setLineData(choTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);



        //设置六边形数据
        tgTV.setRadarViewData(radarViewData);
        tgTV.setRadarViewMaxValue(90);
        tgTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        tgTV.setLineData(tgTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);



        //设置六边形数据
        idlTV.setRadarViewData(radarViewData);
        idlTV.setRadarViewMaxValue(90);
        idlTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        idlTV.setLineData(idlTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        ldlTV.setRadarViewData(radarViewData);
        ldlTV.setRadarViewMaxValue(90);
        ldlTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ldlTV.setLineData(ldlTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        vldlTV.setRadarViewData(radarViewData);
        vldlTV.setRadarViewMaxValue(90);
        vldlTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        vldlTV.setLineData(vldlTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        apoaTV.setRadarViewData(radarViewData);
        apoaTV.setRadarViewMaxValue(90);
        apoaTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        apoaTV.setLineData(apoaTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        apobTV.setRadarViewData(radarViewData);
        apobTV.setRadarViewMaxValue(90);
        apobTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        apobTV.setLineData(apobTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        ckTV.setRadarViewData(radarViewData);
        ckTV.setRadarViewMaxValue(90);
        ckTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ckTV.setLineData(ckTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        ckmbTV.setRadarViewData(radarViewData);
        ckmbTV.setRadarViewMaxValue(90);
        ckmbTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ckmbTV.setLineData(ckmbTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        ldhTV.setRadarViewData(radarViewData);
        ldhTV.setRadarViewMaxValue(90);
        ldhTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ldhTV.setLineData(ldhTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        amyTV.setRadarViewData(radarViewData);
        amyTV.setRadarViewMaxValue(90);
        amyTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        amyTV.setLineData(amyTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        lpaTV.setRadarViewData(radarViewData);
        lpaTV.setRadarViewMaxValue(90);
        lpaTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        lpaTV.setLineData(lpaTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);


        //设置六边形数据
        co2TV.setRadarViewData(radarViewData);
        co2TV.setRadarViewMaxValue(90);
        co2TV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        co2TV.setLineData(co2TV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        tbaTV.setRadarViewData(radarViewData);
        tbaTV.setRadarViewMaxValue(90);
        tbaTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        tbaTV.setLineData(tbaTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        cyscTV.setRadarViewData(radarViewData);
        cyscTV.setRadarViewMaxValue(90);
        cyscTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        cyscTV.setLineData(cyscTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        hcyTV.setRadarViewData(radarViewData);
        hcyTV.setRadarViewMaxValue(90);
        hcyTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        hcyTV.setLineData(hcyTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

    }
    private String getALB(){
        return albET.getText().toString();
    }

    private String getGLU(){
        return gluET.getText().toString();
    }

    private String getGPT(){
        return gptET.getText().toString();
    }
    private String getGOT(){
        return gotET.getText().toString();
    }
    private String getTP(){
        return tpET.getText().toString();
    }
    private String getGLO(){
        return gloET.getText().toString();
    }
    private String getAG(){
        return agET.getText().toString();
    }
    private String getTBI(){
        return tbiET.getText().toString();
    }
    private String getDBI(){
        return dbiET.getText().toString();
    }
    private String getIBIL(){
        return ibilET.getText().toString();
    }
    private String getGGT(){
        return ggtET.getText().toString();
    }
    private String getALP(){
        return alpET.getText().toString();
    }
    private String getCRE(){
        return creET.getText().toString();
    }
    private String getBUN(){
        return bunET.getText().toString();
    }
    private String getUA(){
        return uaET.getText().toString();
    }
    private String getCHO(){
        return choET.getText().toString();
    }
    private String getTG(){
        return tgET.getText().toString();
    }
    private String getIDL(){
        return idlET.getText().toString();
    }
    private String getLDL(){
        return ldlET.getText().toString();
    }
    private String getVLDL(){
        return vldlET.getText().toString();
    }
    private String getAPOA(){
        return apoaET.getText().toString();
    }
    private String getAPOB(){
        return apobET.getText().toString();
    }
    private String getCK(){
        return ckET.getText().toString();
    }
    private String getCKMB(){
        return ckmbET.getText().toString();
    }
    private String getLDH(){
        return ldhET.getText().toString();
    }
    private String getAMY(){
        return amyET.getText().toString();
    }
    private String getLPA(){
        return lpaET.getText().toString();
    }
    private String getCO2(){
        return co2ET.getText().toString();
    }
    private String getTBA(){
        return tbaET.getText().toString();
    }
    private String getCYSC(){
        return cyscET.getText().toString();
    }
    private String getHCY(){
        return hcyET.getText().toString();
    }
    private TextWatcher biochemicalTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG,"biochemicalTextWatcher onTextChanged s "+s);
            if (!s.toString().equals("")) {
                FUTableDiabetesActivity activity = (FUTableDiabetesActivity)mContext;
                if (activity.getCurrentFocus().getId() == gluET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(gluLowValue)) {
                        gluLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(gluHighValue)) {
                        gluLevelTV.setText("↑");
                    } else {
                        gluLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == gptET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(gptLowValue)) {
                        gptLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(gptHighValue)) {
                        gptLevelTV.setText("↑");
                    } else {
                        gptLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == gotET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(gotLowValue)) {
                        gotLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(gotHighValue)) {
                        gotLevelTV.setText("↑");
                    } else {
                        gotLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == tpET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(tpLowValue)) {
                        tpLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(tpHighValue)) {
                        tpLevelTV.setText("↑");
                    } else {
                        tpLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == albET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(albLowValue)) {
                        albLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(albHighValue)) {
                        albLevelTV.setText("↑");
                    } else {
                        albLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == gloET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(gloLowValue)) {
                        gloLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(gloHighValue)) {
                        gloLevelTV.setText("↑");
                    } else {
                        gloLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == agET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(agLowValue)) {
                        agLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(agHighValue)) {
                        agLevelTV.setText("↑");
                    } else {
                        agLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == tbiET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(tbiLowValue)) {
                        tbiLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(tbiHighValue)) {
                        tbiLevelTV.setText("↑");
                    } else {
                        tbiLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == dbiET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(dbiLowValue)) {
                        dbiLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(dbiHighValue)) {
                        dbiLevelTV.setText("↑");
                    } else {
                        dbiLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == ibilET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ibilLowValue)) {
                        ibilLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ibilHighValue)) {
                        ibilLevelTV.setText("↑");
                    } else {
                        ibilLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == ggtET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ggtLowValue)) {
                        ggtLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ggtHighValue)) {
                        ggtLevelTV.setText("↑");
                    } else {
                        ggtLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == alpET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(alpLowValue)) {
                        alpLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(alpHighValue)) {
                        alpLevelTV.setText("↑");
                    } else {
                        alpLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == creET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(creLowValue)) {
                        creLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(creHighValue)) {
                        creLevelTV.setText("↑");
                    } else {
                        creLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == bunET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(bunLowValue)) {
                        bunLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(bunHighValue)) {
                        bunLevelTV.setText("↑");
                    } else {
                        bunLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == uaET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(uaLowValue)) {
                        uaLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(uaHighValue)) {
                        uaLevelTV.setText("↑");
                    } else {
                        uaLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == choET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(choLowValue)) {
                        choLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(choHighValue)) {
                        choLevelTV.setText("↑");
                    } else {
                        choLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == tgET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(tgLowValue)) {
                        tgLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(tgHighValue)) {
                        tgLevelTV.setText("↑");
                    } else {
                        tgLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == idlET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(idlLowValue)) {
                        idlLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(idlHighValue)) {
                        idlLevelTV.setText("↑");
                    } else {
                        idlLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == ldlET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ldlLowValue)) {
                        ldlLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ldlHighValue)) {
                        ldlLevelTV.setText("↑");
                    } else {
                        ldlLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == vldlET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(vldlLowValue)) {
                        vldlLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(vldlHighValue)) {
                        vldlLevelTV.setText("↑");
                    } else {
                        vldlLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == apoaET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(apoaLowValue)) {
                        apoaLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(apoaHighValue)) {
                        apoaLevelTV.setText("↑");
                    } else {
                        apoaLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == apobET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(apobLowValue)) {
                        apobLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(apobHighValue)) {
                        apobLevelTV.setText("↑");
                    } else {
                        apobLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == ckET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ckLowValue)) {
                        ckLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ckHighValue)) {
                        ckLevelTV.setText("↑");
                    } else {
                        ckLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == ckmbET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ckmbLowValue)) {
                        ckmbLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ckmbHighValue)) {
                        ckmbLevelTV.setText("↑");
                    } else {
                        ckmbLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == ldhET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ldhLowValue)) {
                        ldhLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ldhHighValue)) {
                        ldhLevelTV.setText("↑");
                    } else {
                        ldhLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == amyET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(amyLowValue)) {
                        amyLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(amyHighValue)) {
                        amyLevelTV.setText("↑");
                    } else {
                        amyLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == lpaET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(lpaLowValue)) {
                        lpaLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(lpaHighValue)) {
                        lpaLevelTV.setText("↑");
                    } else {
                        lpaLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == co2ET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(co2LowValue)) {
                        co2LevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(co2HighValue)) {
                        co2LevelTV.setText("↑");
                    } else {
                        co2LevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == tbaET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(tbaLowValue)) {
                        tbaLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(tbaHighValue)) {
                        tbaLevelTV.setText("↑");
                    } else {
                        tbaLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == cyscET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(cyscLowValue)) {
                        cyscLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(cyscHighValue)) {
                        cyscLevelTV.setText("↑");
                    } else {
                        cyscLevelTV.setText("正常");
                    }
                }else  if (activity.getCurrentFocus().getId() == hcyET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(hcyLowValue)) {
                        hcyLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(hcyHighValue)) {
                        hcyLevelTV.setText("↑");
                    } else {
                        hcyLevelTV.setText("正常");
                    }
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
