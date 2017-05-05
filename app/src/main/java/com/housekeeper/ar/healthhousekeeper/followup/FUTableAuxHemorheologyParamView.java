package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * 血流变参数
 * Created by Lenovo on 2016/12/26.
 */
public class FUTableAuxHemorheologyParamView extends ViewGroup {
    private String TAG = "FUTableAuxHemorheologyParamView";
    private LinearLayout parentView;
    private TextView dateTV, timeTV;
    int year, month, day;
    int hour, min;
    View view;
    Context mContext;
    String name = "血流变参数";
    private String sex;

    private String from;
    public FUTableAuxHemorheologyParamView(Context context) {
        super(context);
    }

    public FUTableAuxHemorheologyParamView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FUTableAuxHemorheologyParamView(Context context, LinearLayout parent,String from) {
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
    private MyTextView bsvlTV;
    private EditText bsvlET;
    private TextView bsvlLevelTV, bsvlHighTV, bsvlLowTV;

    private String bsvlHighString,bsvlLowString;

    private MyTextView bsvhTV;
    private EditText bsvhET;
    private TextView bsvhLevelTV, bsvhHighTV, bsvhLowTV;

    private String bsvhHighString, bsvhLowString;

    private MyTextView psvTV;
    private EditText psvET;
    private TextView psvLevelTV, psvHighTV, psvLowTV;

    private String psvHighString, psvLowString;

    private MyTextView eetTV;
    private EditText eetET;
    private TextView eetLevelTV, eetHighTV, eetLowTV;

    private String eetHighString, eetLowString;


    private MyTextView petTV;
    private EditText petET;
    private TextView petLevelTV, petHighTV, petLowTV;

    private String petHighString, petLowString;

    private MyTextView fbTV;
    private EditText fbET;
    private TextView fbLevelTV, fbHighTV, fbLowTV;

    private String fbHighString, fbLowString;

    private MyTextView brvlTV;
    private EditText brvlET;
    private TextView brvlLevelTV, brvlHighTV, brvlLowTV;

    private String brvlHighString, brvlLowString;

    private MyTextView brvhTV;
    private EditText brvhET;
    private TextView brvhLevelTV, brvhHighTV, brvhLowTV;

    private String brvhHighString, brvhLowString;

    private MyTextView esrTV;
    private EditText esrET;
    private TextView esrLevelTV, esrHighTV, esrLowTV;

    private String esrHighString, esrLowString;

    private MyTextView hctTV;
    private EditText hctET;
    private TextView hctLevelTV, hctHighTV, hctLowTV;

    private String hctHighString, hctLowString;

    private MyTextView rbcdTV;
    private EditText rbcdET;
    private TextView rbcdLevelTV, rbcdHighTV, rbcdLowTV;

    private String rbcdHighString,rbcdLowString;


    private MyTextView rbcrTV;
    private EditText rbcrET;
    private TextView rbcrLevelTV, rbcrNormalTV;

    private String rbcrNormalString;
    private Button personalAuxBtn,healthyKeeperAuxBtn,hospitalAuxBtn;
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_aux_hemorheology_parameters, null);
        parentView.addView(view);
//        this.addView(biochemicalView);

        deleteBtn = (ImageButton) view.findViewById(R.id.hemorheology_delete_image);
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

        personalAuxBtn = (Button)view.findViewById(R.id.personal_aux);
        healthyKeeperAuxBtn = (Button)view.findViewById(R.id.healthy_keeper_aux);
        hospitalAuxBtn = (Button)view.findViewById(R.id.hospital_aux);

        personalAuxBtn.setOnClickListener(clickListener);
        healthyKeeperAuxBtn.setOnClickListener(clickListener);
        hospitalAuxBtn.setOnClickListener(clickListener);

        bsvlTV = (MyTextView) view.findViewById(R.id.bsvl_tv);
        bsvlET = (EditText) view.findViewById(R.id.bsvl_et);
        bsvlLevelTV = (TextView) view.findViewById(R.id.bsvl_level_tv);
        bsvlHighTV = (TextView) view.findViewById(R.id.bsvl_high_normal_tv);
        bsvlLowTV = (TextView) view.findViewById(R.id.bsvl_low_normal_tv);
        bsvlHighString = bsvlHighTV.getText().toString();
        bsvlLowString = bsvlLowTV.getText().toString();


        bsvhTV = (MyTextView) view.findViewById(R.id.bsvh_tv);
        bsvhET = (EditText) view.findViewById(R.id.bsvh_et);
        bsvhLevelTV = (TextView) view.findViewById(R.id.bsvh_level_tv);
        bsvhHighTV = (TextView) view.findViewById(R.id.bsvh_high_normal_tv);
        bsvhLowTV = (TextView) view.findViewById(R.id.bsvh_low_normal_tv);

        bsvhHighString = bsvhHighTV.getText().toString();
        bsvhLowString = bsvhLowTV.getText().toString();


        psvTV = (MyTextView) view.findViewById(R.id.psv_tv);
        psvET = (EditText) view.findViewById(R.id.psv_et);
        psvLevelTV = (TextView) view.findViewById(R.id.psv_level_tv);
        psvHighTV = (TextView) view.findViewById(R.id.psv_high_normal_tv);
        psvLowTV = (TextView) view.findViewById(R.id.psv_low_normal_tv);

        psvHighString = psvHighTV.getText().toString();
        psvLowString = psvLowTV.getText().toString();




        eetTV = (MyTextView) view.findViewById(R.id.eet_tv);
        eetET = (EditText) view.findViewById(R.id.eet_et);
        eetLevelTV = (TextView) view.findViewById(R.id.eet_level_tv);
        eetHighTV = (TextView) view.findViewById(R.id.eet_high_normal_tv);
        eetLowTV = (TextView) view.findViewById(R.id.eet_low_normal_tv);




        eetHighString = eetHighTV.getText().toString();
        eetLowString = eetLowTV.getText().toString();


        petTV = (MyTextView) view.findViewById(R.id.pet_tv);
        petET = (EditText) view.findViewById(R.id.pet_et);
        petLevelTV = (TextView) view.findViewById(R.id.pet_level_tv);
        petHighTV = (TextView) view.findViewById(R.id.pet_high_normal_tv);
        petLowTV = (TextView) view.findViewById(R.id.pet_low_normal_tv);

        petHighString = petHighTV.getText().toString();
        petLowString = petLowTV.getText().toString();

        fbTV = (MyTextView) view.findViewById(R.id.fb_tv);
        fbET = (EditText) view.findViewById(R.id.fb_et);
        fbLevelTV = (TextView) view.findViewById(R.id.fb_level_tv);
        fbHighTV = (TextView) view.findViewById(R.id.fb_high_normal_tv);
        fbLowTV = (TextView) view.findViewById(R.id.fb_low_normal_tv);

        fbHighString = fbHighTV.getText().toString();
        fbLowString = fbLowTV.getText().toString();

        brvlTV = (MyTextView) view.findViewById(R.id.brvl_tv);
        brvlET = (EditText) view.findViewById(R.id.brvl_et);
        brvlLevelTV = (TextView) view.findViewById(R.id.brvl_level_tv);
        brvlHighTV = (TextView) view.findViewById(R.id.brvl_high_normal_tv);
        brvlLowTV = (TextView) view.findViewById(R.id.brvl_low_normal_tv);

        brvlHighString = brvlHighTV.getText().toString();
        brvlLowString = brvlLowTV.getText().toString();


        brvhTV = (MyTextView) view.findViewById(R.id.brvh_tv);
        brvhET = (EditText) view.findViewById(R.id.brvh_et);
        brvhLevelTV = (TextView) view.findViewById(R.id.brvh_level_tv);
        brvhHighTV = (TextView) view.findViewById(R.id.brvh_high_normal_tv);
        brvhLowTV = (TextView) view.findViewById(R.id.brvh_low_normal_tv);

        brvhHighString = brvhHighTV.getText().toString();
        brvhLowString = brvhLowTV.getText().toString();

        esrTV = (MyTextView) view.findViewById(R.id.esr_tv);
        esrET = (EditText) view.findViewById(R.id.esr_et);
        esrLevelTV = (TextView) view.findViewById(R.id.esr_level_tv);
        esrHighTV = (TextView) view.findViewById(R.id.esr_high_normal_tv);
        esrLowTV = (TextView) view.findViewById(R.id.esr_low_normal_tv);

        esrHighString = esrHighTV.getText().toString();
        esrLowString = esrLowTV.getText().toString();

        hctTV = (MyTextView) view.findViewById(R.id.hct_tv);
        hctET = (EditText) view.findViewById(R.id.hct_et);
        hctLevelTV = (TextView) view.findViewById(R.id.hct_level_tv);
        hctHighTV = (TextView) view.findViewById(R.id.hct_high_normal_tv);
        hctLowTV = (TextView) view.findViewById(R.id.hct_low_normal_tv);

        hctHighString = hctHighTV.getText().toString();
        hctLowString = hctLowTV.getText().toString();

        rbcdTV = (MyTextView) view.findViewById(R.id.rbcd_tv);
        rbcdET = (EditText) view.findViewById(R.id.rbcd_et);
        rbcdLevelTV = (TextView) view.findViewById(R.id.rbcd_level_tv);
        rbcdHighTV = (TextView) view.findViewById(R.id.rbcd_high_normal_tv);
        rbcdLowTV = (TextView) view.findViewById(R.id.rbcd_low_normal_tv);

        rbcdHighString = rbcdHighTV.getText().toString();
        rbcdLowString = rbcdLowTV.getText().toString();

        rbcrTV = (MyTextView) view.findViewById(R.id.rbcr_tv);
        rbcrET = (EditText) view.findViewById(R.id.rbcr_et);
        rbcrLevelTV = (TextView) view.findViewById(R.id.rbcr_level_tv);
        rbcrNormalTV = (TextView) view.findViewById(R.id.rbcr_normal_tv);


        rbcrNormalString = rbcrNormalTV.getText().toString();



        bsvlET.addTextChangedListener(bsvlTextWatcher);
        bsvhET.addTextChangedListener(bsvhTextWatcher);
        psvET.addTextChangedListener(psvTextWatcher);
        eetET.addTextChangedListener(eetTextWatcher);
        petET.addTextChangedListener(petTextWatcher);
        fbET.addTextChangedListener(fbTextWatcher);
        brvlET.addTextChangedListener(brvlTextWatcher);
        brvhET.addTextChangedListener(brvhTextWatcher);
        esrET.addTextChangedListener(esrTextWatcher);
        hctET.addTextChangedListener(hctTextWatcher);
        rbcdET.addTextChangedListener(rbcdTextWatcher);
        rbcrNormalTV.addTextChangedListener(rbcrTextWatcher);

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
            }
        }
    };
    private void initData(){

        sex = "女";

        bsvlET.setText("1");
        bsvhET.setText("4");

        psvET.setText("2");
        eetET.setText("16");
        petET.setText("20");
        fbET.setText("3.0");

        brvlET.setText("20");
        brvhET.setText("8");

        esrET.setText("20");
        hctET.setText("0.5");

        rbcdET.setText("3");

        rbcrET.setText("5");

        if(sex.equals("男")){
            bsvlHighTV.setText("10.0");
            bsvlLowTV.setText("7.5");

            bsvhHighTV.setText("6.7");
            bsvhLowTV.setText("5.6");

            brvlHighTV.setText("20");
            brvlLowTV.setText("14");

            brvhHighTV.setText("13");
            brvhLowTV.setText("10");

            esrHighTV.setText("21");
            esrLowTV.setText("0");

            hctHighTV.setText("0.47");
            hctLowTV.setText("0.42");

            rbcdHighTV.setText("5.0");
            rbcdLowTV.setText("3.9");

            rbcrNormalTV.setText("7.16");

        }else{
            bsvlHighTV.setText("8.1");
            bsvlLowTV.setText("5.8");

            bsvhHighTV.setText("6.01");
            bsvhLowTV.setText("4.7");

            brvlHighTV.setText("21");
            brvlLowTV.setText("12");


            brvhHighTV.setText("13");
            brvhLowTV.setText("9");

            esrHighTV.setText("38");
            esrLowTV.setText("0");

            hctHighTV.setText("0.40");
            hctLowTV.setText("0.39");

            rbcdHighTV.setText("4.2");
            rbcdLowTV.setText("3.0");

            rbcrNormalTV.setText("7.14");
        }


        bsvlHighString = bsvlHighTV.getText().toString();
        bsvlLowString = bsvlLowTV.getText().toString();

        bsvhHighString = bsvhHighTV.getText().toString();
        bsvhLowString = bsvhLowTV.getText().toString();

        brvlHighString = brvlHighTV.getText().toString();
        brvlLowString = brvlLowTV.getText().toString();


        esrHighString = esrHighTV.getText().toString();
        esrLowString =  esrLowTV.getText().toString();

        hctHighString = hctHighTV.getText().toString();
        hctLowString =  hctLowTV.getText().toString();

        rbcdHighString = rbcdHighTV.getText().toString();
        rbcdLowString =  rbcdLowTV.getText().toString();

        rbcrNormalString =  rbcrNormalTV.getText().toString();

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
        bsvlTV.setRadarViewData(radarViewData);
        bsvlTV.setRadarViewMaxValue(90);
        bsvlTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        bsvlTV.setLineData(bsvlTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        bsvhTV.setRadarViewData(radarViewData);
        bsvhTV.setRadarViewMaxValue(90);
        bsvhTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        bsvhTV.setLineData(bsvhTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        psvTV.setRadarViewData(radarViewData);
        psvTV.setRadarViewMaxValue(90);
        psvTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        psvTV.setLineData(psvTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        eetTV.setRadarViewData(radarViewData);
        eetTV.setRadarViewMaxValue(90);
        eetTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        eetTV.setLineData(eetTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        petTV.setRadarViewData(radarViewData);
        petTV.setRadarViewMaxValue(90);
        petTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        petTV.setLineData(petTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        fbTV.setRadarViewData(radarViewData);
        fbTV.setRadarViewMaxValue(90);
        fbTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        fbTV.setLineData(fbTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        brvlTV.setRadarViewData(radarViewData);
        brvlTV.setRadarViewMaxValue(90);
        brvlTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        brvlTV.setLineData(brvlTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        brvhTV.setRadarViewData(radarViewData);
        brvhTV.setRadarViewMaxValue(90);
        brvhTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        brvhTV.setLineData(brvhTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        esrTV.setRadarViewData(radarViewData);
        esrTV.setRadarViewMaxValue(90);
        esrTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        esrTV.setLineData(esrTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        hctTV.setRadarViewData(radarViewData);
        hctTV.setRadarViewMaxValue(90);
        hctTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        hctTV.setLineData(hctTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        rbcdTV.setRadarViewData(radarViewData);
        rbcdTV.setRadarViewMaxValue(90);
        rbcdTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        rbcdTV.setLineData(rbcdTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        rbcrTV.setRadarViewData(radarViewData);
        rbcrTV.setRadarViewMaxValue(90);
        rbcrTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        rbcrTV.setLineData(rbcrTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);
    }
    private TextWatcher bsvlTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(bsvlLowString)) {
                    bsvlLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(bsvlHighString)) {
                    bsvlLevelTV.setText("↑");
                } else {
                    bsvlLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher bsvhTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(bsvhLowString)) {
                    bsvhLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(bsvhHighString)) {
                    bsvhLevelTV.setText("↑");
                } else {
                    bsvhLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher psvTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(psvLowString)) {
                    psvLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(psvHighString)) {
                    psvLevelTV.setText("↑");
                } else {
                    psvLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher eetTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(eetLowString)) {
                    eetLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(eetHighString)) {
                    eetLevelTV.setText("↑");
                } else {
                    eetLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher petTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(petLowString)) {
                    petLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(petHighString)) {
                    petLevelTV.setText("↑");
                } else {
                    petLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher fbTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(fbLowString)) {
                    fbLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(fbHighString)) {
                    fbLevelTV.setText("↑");
                } else {
                    fbLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher brvlTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(brvlLowString)) {
                    brvlLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(brvlHighString)) {
                    brvlLevelTV.setText("↑");
                } else {
                    brvlLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher brvhTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(brvhLowString)) {
                    brvhLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(brvhHighString)) {
                    brvhLevelTV.setText("↑");
                } else {
                    brvhLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher esrTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(esrLowString)) {
                    esrLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(esrHighString)) {
                    esrLevelTV.setText("↑");
                } else {
                    esrLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher hctTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(hctLowString)) {
                    hctLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(hctHighString)) {
                    hctLevelTV.setText("↑");
                } else {
                    hctLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher rbcdTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s!=null&&!s.toString().equals("")) {
                if (Float.valueOf(s.toString()) < Float.valueOf(rbcdLowString)) {
                    rbcdLevelTV.setText("↓");
                } else if (Float.valueOf(s.toString()) > Float.valueOf(rbcdHighString)) {
                    rbcdLevelTV.setText("↑");
                } else {
                    rbcdLevelTV.setText("正常");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private TextWatcher rbcrTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s != null && !s.toString().equals("")){
                if (Float.valueOf(s.toString()) != Float.valueOf(rbcrNormalString)) {
                    rbcrLevelTV.setText("不正常");
                }  else {
                    rbcrLevelTV.setText("正常");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };

}
