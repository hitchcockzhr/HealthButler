package com.housekeeper.ar.healthhousekeeper.membershipcard;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

/**
 * Created by lenovo on 2016/3/5.
 */
//添加会员卡类型
public class MCHoapitalAddMCTypeActivity extends BaseActivity {


    String TAG = "MCHoapitalAddMCTypeActivity";



    String http, httpUrl;

    private ToastCommom toastCommom;
    private TextView hospitalTextView;
    private TextView moreTextView;
    private Button backBtn;
    private String hospital = "";
    private LinearLayout rulesLayout;
    private Button okBtn;
    private String rules="";
    private EditText nameEditText;
    //折扣率
    private EditText discountRateEditText;
    //赠送额
    private EditText givenAmountEditText;
    //充值额
    private EditText rechargeAmountEditText;
    private TextView rechargeAmountTextView;
    private String discountString="",givenAmountString="",rechargeAmountString="";

    private MyApp myApp;
    private String flag="";
    private EditText firstRulesEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershipcard_hospital_add_mctype2);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(MCHoapitalAddMCTypeActivity.this);
        myApp = (MyApp)getApplication();

        hospitalTextView = (TextView)findViewById(R.id.hospital_tv);
        moreTextView = (TextView)findViewById(R.id.add_tv);
        backBtn = (Button)findViewById(R.id.back_btn);
        rulesLayout = (LinearLayout)findViewById(R.id.rules_layout);
        okBtn = (Button)findViewById(R.id.ok_btn);
        nameEditText = (EditText)findViewById(R.id.mc_name_et);
        firstRulesEditText = (EditText)findViewById(R.id.mc_rule_et);

        discountRateEditText=(EditText)findViewById(R.id.zhekoulv_tv);
        givenAmountEditText=(EditText)findViewById(R.id.zengsonge_tv);

        rechargeAmountEditText = (EditText)findViewById(R.id.chongzhie_tv);
        rechargeAmountTextView = (TextView)findViewById(R.id.chongzhie_tv2);

        discountRateEditText.addTextChangedListener(discountTextWatcher);
        givenAmountEditText.addTextChangedListener(givenAmountTextWatcher);
        rechargeAmountEditText.addTextChangedListener(rechargeAmountTextWatcher);


//        discountRateEditText.setFilters(new InputFilter[]{lengthfilter});
//        givenAmountEditText.setFilters(new InputFilter[]{lengthfilter});
//        rechargeAmountEditText.setFilters(new InputFilter[]{ lengthfilter });

        discountString = discountRateEditText.getText().toString();
        givenAmountString = givenAmountEditText.getText().toString();
        rechargeAmountString = rechargeAmountEditText.getText().toString();

        Intent intent = getIntent();
//        if(intent != null){
            hospital = myApp.getYiyuanString();
            hospitalTextView.setText(hospital);
//        }

        if(intent != null){
            String name = intent.getStringExtra("type");
            String rules = intent.getStringExtra("rules");

            String discountString = intent.getStringExtra("discount");
//            String rechargeString = intent.getStringExtra("recharge");
//            String givenString = intent.getStringExtra("given");
            Log.i(TAG,"name "+name);
            Log.i(TAG,"discountString "+discountString);
            if(name != null){
                nameEditText.setText(name);
                //编辑界面
                flag = "edit";
            }
            if (rules!=null){

                String[] rulesArray = rules.split(";");
//                rulesLayout.removeAllViews();
                for(int i = 0;i<rulesArray.length;i++){
                    if(i == 0){
                        //第一条是放到已经存在的EditText中
                        firstRulesEditText.setText(rulesArray[i]);
                    }else{
                        EditText editText = new EditText(MCHoapitalAddMCTypeActivity.this);
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 130));
                        editText.setBackground(MCHoapitalAddMCTypeActivity.this.getResources().getDrawable(R.drawable.simple_gray_stroke));
                        editText.setTextSize(13);
//                    editText.setTop(100);
                        editText.setPadding(3, 3, 3, 3);

                        editText.setText( rulesArray[i]);
                        rulesLayout.addView(editText);
                        rulesLayout.setBottom(20);
                        rulesLayout.setTop(20);
                    }

                }
                Log.i(TAG, "nameEditText.getVisibility() " + nameEditText.getVisibility());

            }
            if (discountString!=null){
                discountRateEditText.setText(discountString);
            }
//            if (givenString!=null){
//                givenAmountEditText.setText(givenString);
//            }
//            if (rechargeString!=null){
//
//                rechargeAmountEditText.setText(rechargeString);
//                rechargeAmountTextView.setText(rechargeString);
//            }
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = new EditText(MCHoapitalAddMCTypeActivity.this);
                editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 130));
                editText.setBackground(getResources().getDrawable(R.drawable.simple_gray_stroke));
                editText.setTextSize(13);
                editText.setTop(100);
                editText.setPadding(3,3,3,3);

                rulesLayout.addView(editText);

            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEditText.getText().toString().equals("")){
                    toastCommom.ToastShow(MCHoapitalAddMCTypeActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"卡名称不能为空");
                    return;
                }
                rules="";

                for(int i = 0;i< rulesLayout.getChildCount();i++){
                   EditText editText = (EditText)rulesLayout.getChildAt(i);
                    if(editText.getText().toString().equals("")){
                       continue;
                    }else{
                        rules = rules+";"+editText.getText().toString();
                    }

                }
                if(rules.equals("")){
                    toastCommom.ToastShow(MCHoapitalAddMCTypeActivity.this,(ViewGroup)findViewById(R.id.toast_layout_root),"规则不能为空");
                    return;
                }
                //折扣率要写进规则中吗？单独写一个extra传到MCHospitalType
//                if(!discountRateEditText.getText().toString().equals("")){
//
//                    rules = rules+";"+"折扣率"+discountRateEditText.getText().toString()+"%";
//                }
                //去掉rules里第一个空;数据
                rules = rules.substring(1);
                Intent intent1 = getIntent();
                intent1.putExtra("name",nameEditText.getText().toString());
                intent1.putExtra("rules",rules);
                intent1.putExtra("discount",discountRateEditText.getText().toString());
                setResult(RESULT_OK, intent1);
                finish();

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
//        init();
    }

    private TextWatcher discountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG,"折扣率 CharSequence "+s);

            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    discountRateEditText.setText(s);
                    discountRateEditText.setSelection(s.length());
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                discountRateEditText.setText(s);
                discountRateEditText.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    discountRateEditText.setText(s.subSequence(0, 1));
                    discountRateEditText.setSelection(1);
                    return;
                }
            }
            String discount = discountRateEditText.getText().toString();
            String given = givenAmountEditText.getText().toString();
            String recharge = rechargeAmountEditText.getText().toString();
            Log.i(TAG,"折扣率 "+discount);
            Log.i(TAG,"赠送额 "+given);
            Log.i(TAG,"充值额 "+recharge);
            if(discount.equals("")){
                Log.i(TAG,"折扣率为空");
                return;
            }

            EditText editText = (EditText)getCurrentFocus();
            Log.i(TAG,"焦点在 editText "+editText);
            if(editText == null){
                return;
            }
            Log.i(TAG,"焦点在 "+editText.getId());
            Log.i(TAG,"折扣率ID "+discountRateEditText.getId());
            Log.i(TAG, "赠送额ID " + givenAmountEditText.getId());
            if(discountRateEditText.getId() != editText.getId()){
                Log.i(TAG,"焦点没在折扣率上");
                return;
            }
            if(!given.equals("") && !recharge.equals("")){
                //如赠送额和充值额已经有内容了，折扣率变化，则改变赠送额
               Double tmp = Double.valueOf(recharge)/(Double.valueOf(discount)/100)-Double.valueOf(recharge);
                Log.i(TAG,"折扣率变化，充值额不变，赠送额= "+tmp);
                givenAmountEditText.setText(""+tmp);
            }else if(given.equals("")&&!recharge.equals("")){
                Double tmp = Double.valueOf(recharge)/(Double.valueOf(discount)/100)-Double.valueOf(recharge);
                Log.i(TAG,"折扣率变化，充值额不变，赠送额= "+tmp);
                givenAmountEditText.setText(""+tmp);
            }else if(!given.equals("")&&recharge.equals("")){
                Double discountRate = Double.valueOf(discount)/100;
                Double givenAmount = Double.valueOf(given);
                Double rechargeAmount = discountRate*givenAmount/(1-discountRate);

                rechargeAmountEditText.setText(""+rechargeAmount);
            }


        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };
    private TextWatcher givenAmountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.i(TAG,"赠送额 CharSequence "+s);

            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    givenAmountEditText.setText(s);
                    givenAmountEditText.setSelection(s.length());
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                givenAmountEditText.setText(s);
                givenAmountEditText.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    givenAmountEditText.setText(s.subSequence(0, 1));
                    givenAmountEditText.setSelection(1);
                    return;
                }
            }
            String discount = discountRateEditText.getText().toString();
            String given = givenAmountEditText.getText().toString();
            String recharge = rechargeAmountEditText.getText().toString();
            Log.i(TAG,"折扣率 "+discount);
            Log.i(TAG,"赠送额 "+given);
            Log.i(TAG,"充值额 "+recharge);

            if(given.equals("")){

                return;
            }
            EditText editText = (EditText)getCurrentFocus();
            if(editText == null){
                return;
            }
            if(givenAmountEditText.getId() != editText.getId()){
                Log.i(TAG,"焦点没在赠送额上");
                return;
            }
            if(!discount.equals("") && !recharge.equals("")){

                //如果赠送额开始变化，充值额和折扣率已经有数值时，改变折扣率
                Double rechargeAmount = Double.valueOf(recharge);
                Double givenAmount = Double.valueOf(given);
                Double discountD = rechargeAmount/(givenAmount+rechargeAmount)*100;
                Log.i(TAG,"赠送额变化，充值额不变，折扣率= "+discountD);
                discountRateEditText.setText("" + discountD);
            }else if(discount.equals("") && !recharge.equals("")){
                Double rechargeAmount = Double.valueOf(recharge);
                Double givenAmount = Double.valueOf(given);
                Double discountD = rechargeAmount/(givenAmount+rechargeAmount)*100;
                Log.i(TAG,"赠送额变化，充值额不变，折扣率= "+discountD);
                discountRateEditText.setText("" + discountD);
            }else if(!discount.equals("") && recharge.equals("")){
                //计算充值额
                Double discountRate = Double.valueOf(discount)/100;
                Double givenAmount = Double.valueOf(given);
                Double rechargeAmount = discountRate*givenAmount/(1-discountRate);

                rechargeAmountEditText.setText(""+rechargeAmount);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher rechargeAmountTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Log.i(TAG,"充值额 CharSequence "+s);


            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    rechargeAmountEditText.setText(s);
                    rechargeAmountEditText.setSelection(s.length());
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                rechargeAmountEditText.setText(s);
                rechargeAmountEditText.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    rechargeAmountEditText.setText(s.subSequence(0, 1));
                    rechargeAmountEditText.setSelection(1);
                    return;
                }
            }
            String discount = discountRateEditText.getText().toString();
            String given = givenAmountEditText.getText().toString();
            String recharge = rechargeAmountEditText.getText().toString();
            Log.i(TAG,"折扣率 "+discount);
            Log.i(TAG,"赠送额 "+given);
            Log.i(TAG, "充值额 " + recharge);
            if(recharge.equals("")){
                    return;
            }
            EditText editText = (EditText)getCurrentFocus();
            if(editText == null){
                return;
            }
            if(rechargeAmountEditText.getId() != editText.getId()){
                Log.i(TAG,"焦点没在充值额上");
                return;
            }
            rechargeAmountTextView.setText(recharge);
            if(!discount.equals("")&&!given.equals("")){
                //如果折扣率和赠送额已经有内容了，充值额变化，改变赠送额
                Double tmp = Double.valueOf(recharge)/(Double.valueOf(discount)/100)-Double.valueOf(recharge);
                Log.i(TAG,"折扣率变化，充值额不变，赠送额= "+tmp);
                givenAmountEditText.setText(""+tmp);

            }else if(!discount.equals("")&& given.equals("")){

                Double tmp = Double.valueOf(recharge)/(Double.valueOf(discount)/100)-Double.valueOf(recharge);
                Log.i(TAG,"折扣率变化，充值额不变，赠送额= "+tmp);
                givenAmountEditText.setText(""+tmp);
            }else if(discount.equals("")&& !given.equals("")){
                //计算折扣率
                Double rechargeAmount = Double.valueOf(recharge);
                Double givenAmount = Double.valueOf(given);
                Double discountD = rechargeAmount/(givenAmount+rechargeAmount)*100;
                Log.i(TAG,"赠送额变化，充值额不变，折扣率= "+discountD);
                discountRateEditText.setText("" + discountD);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    /** 输入框小数的位数*/
    private static final int DECIMAL_DIGITS = 1;                /**
    /**	 *  设置小数位数控制   	 */
    InputFilter lengthfilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                  // 删除等特殊字符，直接返回
                  if ("".equals(source.toString())) {
                       return null;
                  }
                  String dValue = dest.toString();
                  String[] splitArray = dValue.split("//.");
                  if (splitArray.length > 1) {
                      String dotValue = splitArray[1];
                      int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                      if (diff > 0) {
                          return source.subSequence(start, end - diff);
                      }
                  }
            return null;
        }
    };
}
