package com.housekeeper.ar.healthhousekeeper.followup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
 * 尿常规
 * Created by Lenovo on 2016/11/17.
 */
public class FUTableDiabetesUrineRoutineView extends ViewGroup {
    private String TAG = "FUTableDiabetesBloodSugarView";
    private LinearLayout parentView;
    private TextView urineDateTV,urineTimeTV;
    int urineYear,urineMonth,urineDay;
    int urineHour,urineMin;
    View view;
    Context mContext;
    String name="尿常规";
    String from;

    public FUTableDiabetesUrineRoutineView(Context context) {
        super(context);

    }

    public FUTableDiabetesUrineRoutineView(Context context, LinearLayout parent,String from){
        this(context);
        Log.i(TAG, "parent " + parent);
        parentView = parent;
        mContext = context;
        this.from = from;
        initView();
    }

    private TextView phLevelTextView,phHighValueTV,phLowValueTV;
    private TextView sgLevelTextView,sgHighValueTV,sgLowValueTV;
    private TextView uroLevelTextView,uroHighValueTV;
    private EditText phValueET,sgValueET,uroValueET;
    private String phHighValueString,phLowValueString,sgHighValueString,sgLowValueString,uroHighValueString;
    private ImageButton deleteUrineView;

    private MyTextView phTV,sgTV,uroTV;

    private Spinner bloSpinner,wbcSpinner,proSpinner,gluSpinner,bilSpinner,ketSpinner,rbcSpinner,colSpinner;
    private String bloString,wbcString,proString,gluString,bilString,ketString,rbcString,colString;

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_follow_up_table_diabetes_aux_urine_routine, null);
        parentView.addView(view);
//        this.addView(biochemicalView);



        phValueET = (EditText)view.findViewById(R.id.ph_value_et);
        phLevelTextView = (TextView)view.findViewById(R.id.ph_value_level_tv);
        phHighValueTV = (TextView)view.findViewById(R.id.ph_value_high_tv);
        phLowValueTV = (TextView)view.findViewById(R.id.ph_value_low_tv);

        phHighValueString = phHighValueTV.getText().toString();
        phLowValueString = phLowValueTV.getText().toString();

        sgValueET = (EditText)view.findViewById(R.id.urine_specific_gravity_et);
        sgLevelTextView = (TextView)view.findViewById(R.id.urine_specific_gravity_level_tv);
        sgHighValueTV = (TextView)view.findViewById(R.id.urine_specific_gravity_high_normal_tv);
        sgLowValueTV = (TextView)view.findViewById(R.id.urine_specific_gravity_low_normal_tv);

        sgHighValueString = sgHighValueTV.getText().toString();
        sgLowValueString = sgLowValueTV.getText().toString();

        uroValueET = (EditText)view.findViewById(R.id.uro_et);
        uroLevelTextView = (TextView)view.findViewById(R.id.uro_level_tv);
        uroHighValueTV = (TextView)view.findViewById(R.id.uro_normal_tv);



        urineDateTV = (TextView)view.findViewById(R.id.date_tv);
        urineTimeTV = (TextView)view.findViewById(R.id.time_tv);
        //初始化记录时间，以后可能从数据库读取
        //初始化Calendar日历对象
        Calendar mycalendar=Calendar.getInstance(Locale.CHINA);
        Date mydate=new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期

        urineYear=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        urineMonth=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        urineDay=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        urineHour = mycalendar.get(Calendar.HOUR_OF_DAY);
        urineMin = mycalendar.get(Calendar.MINUTE);
        urineDateTV.setText(+urineYear + "-" + (urineMonth + 1) + "-" + urineDay); //显示当前的年月日

        urineTimeTV.setText(urineHour + ":" + urineMin);

        urineDateTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建DatePickerDialog对象
                DatePickerDialog dpd = new DatePickerDialog(mContext, DatePickerDialog.THEME_HOLO_LIGHT, UrineDatelistener, urineYear, urineMonth, urineDay);
                dpd.show();//显示DatePickerDialog组件


            }
        });
        urineTimeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, TimePickerDialog.THEME_HOLO_LIGHT, UrineTimeListener, urineHour, urineMin, true);
                timePickerDialog.show();
            }
        });

        deleteUrineView = (ImageButton)view.findViewById(R.id.urine_delete_image);
        Log.i(TAG,"deleteUrineView "+deleteUrineView);
        deleteUrineView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                parentView.removeView(view);
                if(from.equals("糖尿病")){
                    FUTableDiabetesActivity activity = (FUTableDiabetesActivity)mContext;
                    activity.removeAuxMap(name);
                }else if(from.equals("高血压")){
                    FUTAbleHypertensionActivity activity = (FUTAbleHypertensionActivity)mContext;
                    activity.removeAuxMap(name);
                }

            }
        });

        uroHighValueString = uroHighValueTV.getText().toString();

        bloSpinner = (Spinner)view.findViewById(R.id.blo_spinner);
        wbcSpinner = (Spinner)view.findViewById(R.id.wbc_spinner);
        proSpinner = (Spinner)view.findViewById(R.id.pro_spinner);
        gluSpinner = (Spinner)view.findViewById(R.id.glu_spinner);
        bilSpinner = (Spinner)view.findViewById(R.id.bil_spinner);
        ketSpinner = (Spinner)view.findViewById(R.id.ket_spinner);
        rbcSpinner = (Spinner)view.findViewById(R.id.rbc_spinner);
        colSpinner = (Spinner)view.findViewById(R.id.col_spinner);


        phValueET.addTextChangedListener(phTextWatcher);
        sgValueET.addTextChangedListener(sgTextWatcher);
        uroValueET.addTextChangedListener(uroTextWatcher);


        initUrineSpinner();
        initUrineData();
        initChartData();

    }

    private void initChartData(){
        phTV = (MyTextView)view.findViewById(R.id.ph_value_tv);
        sgTV = (MyTextView)view.findViewById(R.id.urine_specific_gravity_tv);
        uroTV = (MyTextView)view.findViewById(R.id.uro_tv);


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
        phTV.setRadarViewData(radarViewData);
        phTV.setRadarViewMaxValue(90);
        phTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        phTV.setLineData(phTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        sgTV.setRadarViewData(radarViewData);
        sgTV.setRadarViewMaxValue(90);
        sgTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        sgTV.setLineData(sgTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);

        //设置六边形数据
        uroTV.setRadarViewData(radarViewData);
        uroTV.setRadarViewMaxValue(90);
        uroTV.setRadarViewTitles(radarViewTitles);
        //设置曲线数据
        uroTV.setLineData(uroTV.getText().toString(), yMax, ySteps, yDetailSteps, xLabels, dataTextList, dataColorList, dataList);
    }
    private DatePickerDialog.OnDateSetListener UrineDatelistener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {


            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            urineYear=myyear;
            urineMonth=monthOfYear;
            urineDay=dayOfMonth;
            //更新日期
            updateDate();

        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            //在TextView上显示日期
            urineDateTV.setText(urineYear+"-"+(urineMonth+1)+"-"+urineDay);
        }
    };

    private TimePickerDialog.OnTimeSetListener UrineTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            urineHour=hourOfDay;
            urineMin=minute;
            //更新时间
            updateTime();
        }
        private void updateTime()
        {
            //在TextView上显示日期
            urineTimeTV.setText(urineHour+":"+urineMin);
        }
    };

    private TextWatcher phTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG,"phTextWatcher onTextChanged s "+s);
            if (!s.toString().equals("")){
                if(Float.valueOf(s.toString())<Float.valueOf(phLowValueString)){
                    phLevelTextView.setText("↓");
                }else if(Float.valueOf(s.toString())>Float.valueOf(phHighValueString)){
                    phLevelTextView.setText("↑");
                }else{
                    phLevelTextView.setText("正常");
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher sgTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG, "sgTextWatcher onTextChanged s " + s);
            if (!s.toString().equals("")){
                if(Float.valueOf(s.toString())<Float.valueOf(sgLowValueString)){
                    sgLevelTextView.setText("↓");
                }else if(Float.valueOf(s.toString())>Float.valueOf(sgHighValueString)){
                    sgLevelTextView.setText("↑");
                }else{
                    sgLevelTextView.setText("正常");
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher uroTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG, "uroTextWatcher onTextChanged s " + s);
            if (!s.toString().equals("")){
                if(Float.valueOf(s.toString())>=Float.valueOf(uroHighValueString)){
                    uroLevelTextView.setText("↑");
                }else{
                    uroLevelTextView.setText("正常");
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //尿常规
    private void initUrineSpinner(){
        final List<String> bloList = getFeminineData();
        ArrayAdapter<String> bloAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getFeminineData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fu_spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(bloList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        bloAdapter.setDropDownViewResource(R.layout.fu_spinner_item_layout);
        bloSpinner.setAdapter(bloAdapter);

        bloSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "隐血 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        bloSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                bloString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });




        final List<String> wbcList = getFeminineData();
        ArrayAdapter<String> wbcAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getFeminineData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fu_spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(wbcList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        wbcAdapter.setDropDownViewResource(R.layout.fu_spinner_item_layout);
        wbcSpinner.setAdapter(wbcAdapter);

        wbcSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "白细胞 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        wbcSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                wbcString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });




        final List<String> proList = getFeminineData();
        ArrayAdapter<String> proAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getFeminineData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fu_spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(proList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        proAdapter.setDropDownViewResource(R.layout.fu_spinner_item_layout);
        proSpinner.setAdapter(proAdapter);

        proSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "尿蛋白 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        proSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                proString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });




        final List<String> gluList = getFeminineData();
        ArrayAdapter<String> gluAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getFeminineData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fu_spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(gluList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        gluAdapter.setDropDownViewResource(R.layout.fu_spinner_item_layout);
        gluSpinner.setAdapter(gluAdapter);

        gluSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "尿糖 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        gluSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                gluString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        final List<String> bilList = getFeminineData();
        ArrayAdapter<String> bilAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getFeminineData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fu_spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(bilList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        bilAdapter.setDropDownViewResource(R.layout.fu_spinner_item_layout);
        bilSpinner.setAdapter(bilAdapter);

        bilSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "胆红素 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        bilSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                bilString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        final List<String> ketList = getFeminineData();
        ArrayAdapter<String> ketAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getFeminineData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fu_spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(ketList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        ketAdapter.setDropDownViewResource(R.layout.fu_spinner_item_layout);
        ketSpinner.setAdapter(ketAdapter);

        ketSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "酮体 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        ketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                ketString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        final List<String> rbcList = getFeminineData();
        ArrayAdapter<String> rbcAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getFeminineData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.fu_spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(rbcList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        rbcAdapter.setDropDownViewResource(R.layout.fu_spinner_item_layout);
        rbcSpinner.setAdapter(rbcAdapter);

        rbcSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "尿红细胞 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        rbcSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                rbcString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        final List<String> cloList = getCOLData();
        ArrayAdapter<String> colAdapter = new ArrayAdapter<String>
                (mContext, R.layout.fu_spinner_item,getCOLData()){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.spinner_item_layout,
                        null);
                TextView label = (TextView) view
                        .findViewById(R.id.spinner_item_label);

                label.setText(cloList.get(position));

                return view;
                //return super.getDropDownView(position, convertView, parent);
            }
        };

        //设置样式
        colAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
        colSpinner.setAdapter(colAdapter);

        colSpinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.i(TAG, "尿液颜色 touch ");
                closeSoftKeyboard();
                return false;
            }
        });

        colSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                colString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void initUrineData(){
        bloSpinner.setSelection(1);
        wbcSpinner.setSelection(0);
        proSpinner.setSelection(0);
        gluSpinner.setSelection(1);
        bilSpinner.setSelection(0);
        ketSpinner.setSelection(0);
        rbcSpinner.setSelection(1);
        colSpinner.setSelection(0);
    }


    //获取阴性、阳性数据
    private List<String> getFeminineData(){
        List<String> dataList = new ArrayList<String>();

//        dataList.add("阴性（-）");
//        dataList.add("阳性（+）");
        dataList.add("-");
        dataList.add("+");
        dataList.add("+-");
        dataList.add("++");
        dataList.add("+++");
        dataList.add("++++");

        return dataList;
    }

    private List<String> getCOLData(){
        List<String> dataList = new ArrayList<String>();

        dataList.add("浅黄色至深黄色");
        dataList.add("黄绿色");
        dataList.add("血红色");
        return dataList;
    }
    private void closeSoftKeyboard(){
        Activity activity = (Activity)mContext;
        /**隐藏软键盘**/
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
//    private String getFBG(){
//        return fbgET.getText().toString();
//    }
//    private String getGH(){
//        return ghET.getText().toString();
//    }
//    private String getPBG(){
//        return pbgET.getText().toString();
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
