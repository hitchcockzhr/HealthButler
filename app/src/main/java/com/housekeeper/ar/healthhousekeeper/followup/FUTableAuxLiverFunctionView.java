package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.followup.view.MyTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * 肝功能
 * Created by Lenovo on 2016/12/26.
 */
public class FUTableAuxLiverFunctionView extends ViewGroup {
    private String TAG = "FUTableLiverFunctionView";
    private LinearLayout parentView;
    private TextView dateTV, timeTV;
    int year, month, day;
    int hour, min;
    View view;
    Context mContext;
    String name = "肝功能";

    private String from;
    public FUTableAuxLiverFunctionView(Context context) {
        super(context);
    }

    public FUTableAuxLiverFunctionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FUTableAuxLiverFunctionView(Context context, LinearLayout parent,String from) {
        this(context);
        Log.i(TAG, "parent " + parent);
        parentView = parent;
        mContext = context;
        this.from = from;
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private ImageButton deleteBtn;
    private MyTextView altTV;
    private EditText altET;
    private TextView altLevelTV, altHighTV, altLowTV;

    private String altHighString, altLowString;

    private MyTextView astTV;
    private EditText astET;
    private TextView astLevelTV, astHighTV, astLowTV;

    private String astHighString, astLowString;

    private MyTextView astaltTV;
    private TextView astaltRateTV;
    private TextView astaltLevelTV, astaltHighTV, astaltLowTV;

    private String astaltHighString, astaltLowString;

    private MyTextView ggpTV;
    private EditText ggpET;
    private TextView ggpLevelTV, ggpHighTV, ggpLowTV;

    private String ggpHighString, ggpLowString;


    private MyTextView alpTV;
    private EditText alpET;
    private TextView alpLevelTV, alpHighTV, alpLowTV;

    private String alpHighString, alpLowString;

    private MyTextView tbiliTV;
    private EditText tbiliET;
    private TextView tbiliLevelTV, tbiliHighTV, tbiliLowTV;

    private String tbiliHighString, tbiliLowString;

    private MyTextView dbiliTV;
    private EditText dbiliET;
    private TextView dbiliLevelTV, dbiliHighTV, dbiliLowTV;

    private String dbiliHighString, dbiliLowString;

    private MyTextView ibiliTV;
    private EditText ibiliET;
    private TextView ibiliLevelTV, ibiliHighTV, ibiliLowTV;

    private String ibiliHighString, ibiliLowString;

    private MyTextView tpTV;
    private EditText tpET;
    private TextView tpLevelTV, tpHighTV, tpLowTV;

    private String tpHighString, tpLowString;

    private MyTextView albTV;
    private EditText albET;
    private TextView albLevelTV, albHighTV, albLowTV;

    private String albHighString, albLowString;

    private MyTextView glbTV;
    private EditText glbET;
    private TextView glbLevelTV, glbHighTV, glbLowTV;

    private String glbHighString, glbLowString;


    private MyTextView albglbTV;
    private TextView albglbRateTV;
    private TextView albglbLevelTV, albglbHighTV, albglbLowTV;

    private String albglbHighString, albglbLowString;
    private Button personalAuxBtn,healthyKeeperAuxBtn,hospitalAuxBtn;

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_aux_liver_function, null);
        parentView.addView(view);
//        this.addView(biochemicalView);

        personalAuxBtn = (Button)view.findViewById(R.id.personal_aux);
        healthyKeeperAuxBtn = (Button)view.findViewById(R.id.healthy_keeper_aux);
        hospitalAuxBtn = (Button)view.findViewById(R.id.hospital_aux);

        personalAuxBtn.setOnClickListener(clickListener);
        healthyKeeperAuxBtn.setOnClickListener(clickListener);
        hospitalAuxBtn.setOnClickListener(clickListener);

        deleteBtn = (ImageButton) view.findViewById(R.id.liver_delete_image);
        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parentView.removeView(view);
                if(from.equals("糖尿病")){
                    FUTableDiabetesActivity activity = (FUTableDiabetesActivity)mContext;
                    activity.removeAuxMap(name);
                }else if(from.equals("高血压")){
                    FUTAbleHypertensionActivity activity = (FUTAbleHypertensionActivity)mContext;
                    activity.removeAuxMap(name);
                }else if(from.equals("脑卒中")){
                    FUTableStrokeActivity activity = (FUTableStrokeActivity)mContext;
                    activity.removeAuxMap(name);
                }else if(from.equals("血脂异常")){
                    FUTAbleDyslipidemiaActivity activity = (FUTAbleDyslipidemiaActivity)mContext;
                    activity.removeAuxMap(name);
                }else if(from.equals("")){
                    FUTableDiagnosisActivity activity = (FUTableDiagnosisActivity)mContext;
                    activity.removeAuxMap(name);
                }
//                FUTableDiagnosisActivity activity = (FUTableDiagnosisActivity) mContext;
//                activity.removeAuxMap(name);


            }
        });


        altTV = (MyTextView) view.findViewById(R.id.alt_tv);
        altET = (EditText) view.findViewById(R.id.alt_et);
        altLevelTV = (TextView) view.findViewById(R.id.alt_level_tv);
        altHighTV = (TextView) view.findViewById(R.id.alt_high_normal_tv);
        altLowTV = (TextView) view.findViewById(R.id.alt_low_normal_tv);

        altHighString = altHighTV.getText().toString();
        altLowString = altLowTV.getText().toString();

        astTV = (MyTextView) view.findViewById(R.id.ast_tv);
        astET = (EditText) view.findViewById(R.id.ast_et);
        astLevelTV = (TextView) view.findViewById(R.id.ast_level_tv);
        astHighTV = (TextView) view.findViewById(R.id.ast_high_normal_tv);
        astLowTV = (TextView) view.findViewById(R.id.ast_low_normal_tv);

        astHighString = astHighTV.getText().toString();
        astLowString = astLowTV.getText().toString();


        astaltTV = (MyTextView) view.findViewById(R.id.ast_alt_tv);
        astaltRateTV = (TextView) view.findViewById(R.id.ast_alt_et);
        astaltLevelTV = (TextView) view.findViewById(R.id.ast_alt_level_tv);
        astaltHighTV = (TextView) view.findViewById(R.id.ast_alt_high_normal_tv);
        astaltLowTV = (TextView) view.findViewById(R.id.ast_alt_low_normal_tv);

        astaltHighString = astaltHighTV.getText().toString();
        astaltLowString = astaltLowTV.getText().toString();




        ggpTV = (MyTextView) view.findViewById(R.id.ggp_tv);
        ggpET = (EditText) view.findViewById(R.id.ggp_et);
        ggpLevelTV = (TextView) view.findViewById(R.id.ggp_level_tv);
        ggpHighTV = (TextView) view.findViewById(R.id.ggp_high_normal_tv);
        ggpLowTV = (TextView) view.findViewById(R.id.ggp_low_normal_tv);

        ggpHighString = ggpHighTV.getText().toString();
        ggpLowString = ggpLowTV.getText().toString();


        alpTV = (MyTextView) view.findViewById(R.id.alp_tv);
        alpET = (EditText) view.findViewById(R.id.alp_et);
        alpLevelTV = (TextView) view.findViewById(R.id.alp_level_tv);
        alpHighTV = (TextView) view.findViewById(R.id.alp_high_normal_tv);
        alpLowTV = (TextView) view.findViewById(R.id.alp_low_normal_tv);

        alpHighString = alpHighTV.getText().toString();
        alpLowString = alpLowTV.getText().toString();

        tbiliTV = (MyTextView) view.findViewById(R.id.tbili_tv);
        tbiliET = (EditText) view.findViewById(R.id.tbili_et);
        tbiliLevelTV = (TextView) view.findViewById(R.id.tbili_level_tv);
        tbiliHighTV = (TextView) view.findViewById(R.id.tbili_high_normal_tv);
        tbiliLowTV = (TextView) view.findViewById(R.id.tbili_low_normal_tv);

        tbiliHighString = tbiliHighTV.getText().toString();
        tbiliLowString = tbiliLowTV.getText().toString();

        dbiliTV = (MyTextView) view.findViewById(R.id.dbili_tv);
        dbiliET = (EditText) view.findViewById(R.id.dbili_et);
        dbiliLevelTV = (TextView) view.findViewById(R.id.dbili_level_tv);
        dbiliHighTV = (TextView) view.findViewById(R.id.dbili_high_normal_tv);
        dbiliLowTV = (TextView) view.findViewById(R.id.dbili_low_normal_tv);

        dbiliHighString = dbiliHighTV.getText().toString();
        dbiliLowString = dbiliLowTV.getText().toString();


        ibiliTV = (MyTextView) view.findViewById(R.id.ibili_tv);
        ibiliET = (EditText) view.findViewById(R.id.ibili_et);
        ibiliLevelTV = (TextView) view.findViewById(R.id.ibili_level_tv);
        ibiliHighTV = (TextView) view.findViewById(R.id.ibili_high_normal_tv);
        ibiliLowTV = (TextView) view.findViewById(R.id.ibili_low_normal_tv);

        ibiliHighString = ibiliHighTV.getText().toString();
        ibiliLowString = ibiliLowTV.getText().toString();

        tpTV = (MyTextView) view.findViewById(R.id.tp_tv);
        tpET = (EditText) view.findViewById(R.id.tp_et);
        tpLevelTV = (TextView) view.findViewById(R.id.tp_level_tv);
        tpHighTV = (TextView) view.findViewById(R.id.tp_high_normal_tv);
        tpLowTV = (TextView) view.findViewById(R.id.tp_low_normal_tv);

        tpHighString = tpHighTV.getText().toString();
        tpLowString = tpLowTV.getText().toString();

        albTV = (MyTextView) view.findViewById(R.id.alb_tv);
        albET = (EditText) view.findViewById(R.id.alb_et);
        albLevelTV = (TextView) view.findViewById(R.id.alb_level_tv);
        albHighTV = (TextView) view.findViewById(R.id.alb_high_normal_tv);
        albLowTV = (TextView) view.findViewById(R.id.alb_low_normal_tv);

        albHighString = albHighTV.getText().toString();
        albLowString = albLowTV.getText().toString();

        glbTV = (MyTextView) view.findViewById(R.id.glb_tv);
        glbET = (EditText) view.findViewById(R.id.glb_et);
        glbLevelTV = (TextView) view.findViewById(R.id.glb_level_tv);
        glbHighTV = (TextView) view.findViewById(R.id.glb_high_normal_tv);
        glbLowTV = (TextView) view.findViewById(R.id.glb_low_normal_tv);

        glbHighString = glbHighTV.getText().toString();
        glbLowString = glbLowTV.getText().toString();

        albglbTV = (MyTextView) view.findViewById(R.id.alb_glb_tv);
        albglbRateTV = (TextView) view.findViewById(R.id.alb_glb_et);
        albglbLevelTV = (TextView) view.findViewById(R.id.alb_glb_level_tv);
        albglbHighTV = (TextView) view.findViewById(R.id.alb_glb_high_normal_tv);
        albglbLowTV = (TextView) view.findViewById(R.id.alb_glb_low_normal_tv);

        albglbHighString = albglbHighTV.getText().toString();
        albglbLowString = albglbLowTV.getText().toString();



        altET.addTextChangedListener(altTextWatcher);
        astET.addTextChangedListener(astTextWatcher);
        astaltRateTV.addTextChangedListener(astaltRateTextWatcher);
        ggpET.addTextChangedListener(ggpTextWatcher);
        alpET.addTextChangedListener(alpTextWatcher);
        tbiliET.addTextChangedListener(tbiliTextWatcher);
        dbiliET.addTextChangedListener(dbiliTextWatcher);
        ibiliET.addTextChangedListener(ibiliTextWatcher);
        tpET.addTextChangedListener(tpTextWatcher);
        albET.addTextChangedListener(albTextWatcher);
        glbET.addTextChangedListener(glbTextWatcher);
        albglbRateTV.addTextChangedListener(albglbRateTextWatcher);

        altLevelTV.setOnClickListener(clickListener);
        astLevelTV.setOnClickListener(clickListener);
        alpLevelTV.setOnClickListener(clickListener);
        ibiliLevelTV.setOnClickListener(clickListener);
        dbiliLevelTV.setOnClickListener(clickListener);
        tbiliLevelTV.setOnClickListener(clickListener);
        tpLevelTV.setOnClickListener(clickListener);
        albLevelTV.setOnClickListener(clickListener);
        glbLevelTV.setOnClickListener(clickListener);
        ggpLevelTV.setOnClickListener(clickListener);

        dateTV = (TextView) view.findViewById(R.id.date_tv);
        timeTV = (TextView) view.findViewById(R.id.time_tv);

        //初始化记录时间，以后可能从数据库读取
        //初始化Calendar日历对象
        Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

        year = mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month = mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day = mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        hour = mycalendar.get(Calendar.HOUR_OF_DAY);
        min = mycalendar.get(Calendar.MINUTE);
        dateTV.setText(+year + "-" + (month + 1) + "-" + day); //显示当前的年月日

        timeTV.setText(hour + ":" + min);

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
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, TimePickerDialog.THEME_HOLO_LIGHT, timeListener, hour, min, true);
                timePickerDialog.show();
            }
        });
        initData();
        initChartData();
    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {


            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year = myyear;
            month = monthOfYear;
            day = dayOfMonth;
            //更新日期
            updateDate();

        }

        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate() {
            //在TextView上显示日期
            dateTV.setText(year + "-" + (month + 1) + "-" + day);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            //更新时间
            updateTime();
        }

        private void updateTime() {
            //在TextView上显示日期
            timeTV.setText(hour + ":" + min);
        }
    };
    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.personal_aux:
                    personalAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    hospitalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;

                case R.id.healthy_keeper_aux:

                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    personalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    hospitalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;

                case R.id.hospital_aux:
                    hospitalAuxBtn.setBackgroundResource(R.drawable.tab_btn_pressed_shape);
                    hospitalAuxBtn.setTextColor(getResources().getColor(R.color.white));
                    personalAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    personalAuxBtn.setTextColor(getResources().getColor(R.color.black));
                    healthyKeeperAuxBtn.setBackgroundResource(R.drawable.btn_green_stroke_shape);
                    healthyKeeperAuxBtn.setTextColor(getResources().getColor(R.color.black));

                    break;
                case R.id.alt_level_tv:
                    if(!altLevelTV.getText().toString().equals("正常") && !altLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","谷丙转氨");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.ast_level_tv:
                    if(!astLevelTV.getText().toString().equals("正常") && !astLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","谷草转氨酶");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.alp_level_tv:
                    if(!alpLevelTV.getText().toString().equals("正常") && !alpLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","碱性磷酸酶");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.dbili_level_tv:
                    if(!dbiliLevelTV.getText().toString().equals("正常") && !dbiliLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","直接胆红素");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.ibili_level_tv:
                    if(!ibiliLevelTV.getText().toString().equals("正常") && !ibiliLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","间接胆红素");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.tbili_level_tv:
                    if(!tbiliLevelTV.getText().toString().equals("正常") && !tbiliLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","总胆红素");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.glb_level_tv:
                    if(!glbLevelTV.getText().toString().equals("正常") && !glbLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","血清球蛋白");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.alb_level_tv:
                    if(!albLevelTV.getText().toString().equals("正常") && !albLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","血清白蛋白");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.tp_level_tv:
                    if(!tpLevelTV.getText().toString().equals("正常") && !tpLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","血清总蛋白");
                        mContext.startActivity(intent);
                    }
                    break;
                case R.id.ggp_level_tv:
                    if(!ggpLevelTV.getText().toString().equals("正常") && !ggpLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","谷氨酰转移酶");
                        mContext.startActivity(intent);
                    }
                    break;

                case R.id.alb_glb_level_tv:
                    if(!albglbLevelTV.getText().toString().equals("正常") && !albglbLevelTV.getText().toString().equals("")){
                        Intent intent = new Intent(mContext,FUTableAuxLiverFunctionUnderstandingActivity.class);
                        intent.putExtra("name","白球比");
                        mContext.startActivity(intent);
                    }
                    break;

            }
        }
    };
    private void initData(){

        altET.setText("5");
        astET.setText("10");

        calculateAstAltRate();

        ggpET.setText("10");
        alpET.setText("8");
        tbiliET.setText("2");
        dbiliET.setText("6");
        ibiliET.setText("12");

        tpET.setText("30");
        albET.setText("30");
        glbET.setText("20");
        calculateAlbGlbRate();

    }
    private void initChartData(){


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

        dataTextList.add("测试");

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
        altTV.setRadarViewData(radarViewData);
        altTV.setRadarViewMaxValue(90);
        altTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        altTV.setLineData(altTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        astTV.setRadarViewData(radarViewData);
        astTV.setRadarViewMaxValue(90);
        astTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        astTV.setLineData(astTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        astaltTV.setRadarViewData(radarViewData);
        astaltTV.setRadarViewMaxValue(90);
        astaltTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        astaltTV.setLineData(astaltTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        ggpTV.setRadarViewData(radarViewData);
        ggpTV.setRadarViewMaxValue(90);
        ggpTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ggpTV.setLineData(ggpTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        alpTV.setRadarViewData(radarViewData);
        alpTV.setRadarViewMaxValue(90);
        alpTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        alpTV.setLineData(alpTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        tbiliTV.setRadarViewData(radarViewData);
        tbiliTV.setRadarViewMaxValue(90);
        tbiliTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        tbiliTV.setLineData(tbiliTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        dbiliTV.setRadarViewData(radarViewData);
        dbiliTV.setRadarViewMaxValue(90);
        dbiliTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        dbiliTV.setLineData(dbiliTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        ibiliTV.setRadarViewData(radarViewData);
        ibiliTV.setRadarViewMaxValue(90);
        ibiliTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        ibiliTV.setLineData(ibiliTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

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
        glbTV.setRadarViewData(radarViewData);
        glbTV.setRadarViewMaxValue(90);
        glbTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        glbTV.setLineData(glbTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        albglbTV.setRadarViewData(radarViewData);
        albglbTV.setRadarViewMaxValue(90);
        albglbTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        albglbTV.setLineData(albglbTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);
    }
    private TextWatcher altTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(altLowString)) {
                    altLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(altHighString)) {
                    altLevelTV.setText("↑");
                } else {
                    altLevelTV.setText("正常");
                }
                calculateAstAltRate();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher astTextWatcher = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(astLowString)) {
                    astLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(astHighString)) {
                    astLevelTV.setText("↑");
                } else {
                    astLevelTV.setText("正常");
                }
                calculateAstAltRate();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher ggpTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(ggpLowString)) {
                    ggpLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(ggpHighString)) {
                    ggpLevelTV.setText("↑");
                } else {
                    ggpLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher alpTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(alpLowString)) {
                    alpLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(alpHighString)) {
                    alpLevelTV.setText("↑");
                } else {
                    alpLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher tbiliTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(tbiliLowString)) {
                    tbiliLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(tbiliHighString)) {
                    tbiliLevelTV.setText("↑");
                } else {
                    tbiliLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher dbiliTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(dbiliLowString)) {
                    dbiliLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(dbiliHighString)) {
                    dbiliLevelTV.setText("↑");
                } else {
                    dbiliLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher ibiliTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(ibiliLowString)) {
                    ibiliLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(ibiliHighString)) {
                    ibiliLevelTV.setText("↑");
                } else {
                    ibiliLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher tpTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(tpLowString)) {
                    tpLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(tpHighString)) {
                    tpLevelTV.setText("↑");
                } else {
                    tpLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher albTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(albLowString)) {
                    albLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(albHighString)) {
                    albLevelTV.setText("↑");
                } else {
                    albLevelTV.setText("正常");
                }
                calculateAlbGlbRate();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher glbTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(glbLowString)) {
                    glbLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(glbHighString)) {
                    glbLevelTV.setText("↑");
                } else {
                    glbLevelTV.setText("正常");
                }
                calculateAlbGlbRate();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s!=null&&!s.toString().equals("")) {

                Activity activity = (Activity) mContext;
                if(activity.getCurrentFocus() == null){
                    return;
                }
                if (activity.getCurrentFocus().getId() == altET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(altLowString)) {
                        altLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(altHighString)) {
                        altLevelTV.setText("↑");
                    } else {
                        altLevelTV.setText("正常");
                    }
                    calculateAstAltRate();
                } else if (activity.getCurrentFocus().getId() == astET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(astLowString)) {
                        astLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(astHighString)) {
                        astLevelTV.setText("↑");
                    } else {
                        astLevelTV.setText("正常");
                    }
                    calculateAstAltRate();
                } else if (activity.getCurrentFocus().getId() == ggpET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ggpLowString)) {
                        ggpLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ggpHighString)) {
                        ggpLevelTV.setText("↑");
                    } else {
                        ggpLevelTV.setText("正常");
                    }
                } else if (activity.getCurrentFocus().getId() == alpET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(alpLowString)) {
                        alpLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(alpHighString)) {
                        alpLevelTV.setText("↑");
                    } else {
                        alpLevelTV.setText("正常");
                    }
                }else if (activity.getCurrentFocus().getId() == tbiliET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(tbiliLowString)) {
                        tbiliLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(tbiliHighString)) {
                        tbiliLevelTV.setText("↑");
                    } else {
                        tbiliLevelTV.setText("正常");
                    }
                }else if (activity.getCurrentFocus().getId() == dbiliET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(dbiliLowString)) {
                        dbiliLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(dbiliHighString)) {
                        dbiliLevelTV.setText("↑");
                    } else {
                        dbiliLevelTV.setText("正常");
                    }
                }else if (activity.getCurrentFocus().getId() == ibiliET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(ibiliLowString)) {
                        ibiliLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(ibiliHighString)) {
                        ibiliLevelTV.setText("↑");
                    } else {
                        ibiliLevelTV.setText("正常");
                    }
                }else if (activity.getCurrentFocus().getId() == tpET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(tpLowString)) {
                        tpLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(tpHighString)) {
                        tpLevelTV.setText("↑");
                    } else {
                        tpLevelTV.setText("正常");
                    }
                }else if (activity.getCurrentFocus().getId() == albET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(albLowString)) {
                        albLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(albHighString)) {
                        albLevelTV.setText("↑");
                    } else {
                        albLevelTV.setText("正常");
                    }
                    calculateAlbGlbRate();
                }else if (activity.getCurrentFocus().getId() == glbET.getId()) {
                    if (Float.valueOf(s.toString()) < Float.valueOf(glbLowString)) {
                        glbLevelTV.setText("↓");
                    } else if (Float.valueOf(s.toString()) > Float.valueOf(glbHighString)) {
                        glbLevelTV.setText("↑");
                    } else {
                        glbLevelTV.setText("正常");
                    }
                    calculateAlbGlbRate();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //通过ast alt计算比值
    private void calculateAstAltRate(){
        if(astET != null && altET != null && !astET.getText().toString().equals("") && !altET.getText().toString().equals("")){
            float rate = Float.valueOf(astET.getText().toString()) / Float.valueOf(altET.getText().toString());
            DecimalFormat df=new DecimalFormat(".##");
            String st=df.format(rate);
            astaltRateTV.setText(st);
        }else{
            astaltRateTV.setText("0");
        }
    }
    private void calculateAlbGlbRate(){
        if(albET != null && glbET != null && !albET.getText().toString().equals("") && !glbET.getText().toString().equals("")){
            float rate = Float.valueOf(albET.getText().toString()) / Float.valueOf(glbET.getText().toString());
            DecimalFormat df=new DecimalFormat(".##");
            String st=df.format(rate);


            albglbRateTV.setText(st);
        }else{
            albglbRateTV.setText("0");
        }
    }
    private TextWatcher astaltRateTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s != null && !s.toString().equals("")){
                if (Float.valueOf(s.toString()) < Float.valueOf(astaltLowString)) {
                    astaltLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(astaltHighString)) {
                    astaltLevelTV.setText("↑");
                } else {
                    astaltLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };
    private TextWatcher albglbRateTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s != null && !s.toString().equals("")){
                if (Float.valueOf(s.toString()) < Float.valueOf(albglbLowString)) {
                    albglbLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(albglbHighString)) {
                    albglbLevelTV.setText("↑");
                } else {
                    albglbLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };
}
