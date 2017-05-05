package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.housekeeper.ar.healthhousekeeper.BaseActivity;
import com.housekeeper.ar.healthhousekeeper.HttpUtil;
import com.housekeeper.ar.healthhousekeeper.MyActivityManager;
import com.housekeeper.ar.healthhousekeeper.MyApp;
import com.housekeeper.ar.healthhousekeeper.R;
import com.housekeeper.ar.healthhousekeeper.SysApplication;
import com.housekeeper.ar.healthhousekeeper.ToastCommom;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Lenovo on 2017/3/29.
 */
public class FUTableVisitActivity extends BaseActivity {

    String TAG="FUTableVisitActivity";
    private ToastCommom toastCommom;
    private Button backBtn;

    private Button tuxingBtn, tuxingCheckBtn, duanxinmaBtn;
    private Button okBtn,cancelBtn;
    private EditText tuxingET, duanxinET;
    private ImageView tuxingIV;
    private TextView telTV;
    private String telStr="";

    static String http ;//="http://192.168.1.54:8080/";
    static String httpUrl;
    static String regUrl = "shlc/doctor/user";
    private MyApp myApp;
    private JSONObject params = new JSONObject();
    String token, smsCaptcha;
    boolean yanzhengFlag ;
    private TimeCount time;
    private String showStr = "";
    private String resultStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_table_visit);

        Toast.makeText(FUTableVisitActivity.this,"测试时只需要随便输入验证码",Toast.LENGTH_SHORT).show();
        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(FUTableVisitActivity.this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myApp = (MyApp)getApplication();
        http = myApp.getHttp();

        telTV = (TextView)findViewById(R.id.tel_tv);
        tuxingET = (EditText)findViewById(R.id.tuxingma_et);
        tuxingIV = (ImageView)findViewById(R.id.tuxingma_iv);
        tuxingBtn = (Button)findViewById(R.id.tuxingma_btn);
        tuxingCheckBtn = (Button)findViewById(R.id.tuxingcheck_btn);
        duanxinmaBtn = (Button)findViewById(R.id.duanxinma_btn);
        duanxinET = (EditText)findViewById(R.id.yanzhengma_et);
        okBtn = (Button)findViewById(R.id.ok_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsCaptcha= duanxinET.getText().toString();
                if(smsCaptcha == null || smsCaptcha.equals("")){
                    toastCommom.ToastShow(FUTableVisitActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请输入短信验证码");
                    return;
                }
                try {

                    //验证
                    params.put("smsCaptcha",smsCaptcha);
                    Log.d(TAG, "httpUrl:"+http+regUrl);//str
                    Log.d(TAG, "reg params:"+params.toString());
                    //TODO 以后regUrl修改
                    httpUrl = http + regUrl;
                    HttpPost post = HttpUtil.getPost(httpUrl, params);
                    JSONObject joRev = HttpUtil.getString(post, 3);
                    showStr = joRev.getString("result");
                    resultStr = joRev.getString("resultMessage");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //TODO　为了测试，后期去掉
                showStr = "200";

                if(showStr.equals("200")){
                    Log.d(TAG, "resultStr:" + resultStr);

                    toastCommom.ToastShow(FUTableVisitActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "验证成功！");
                    Intent intent = new Intent(FUTableVisitActivity.this , FUTableServiceActivity.class);
                    startActivity(intent);
                    FUTableVisitActivity.this.finish();
                }else{
                    toastCommom.ToastShow(FUTableVisitActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), resultStr);
                }
            }
        });

        tuxingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTuxingma();
            }
        });
        //取得验证码
        getTuxingma();
        tuxingCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuxingET.getText().toString() != null) {
                    JSONObject tuxingJO = new JSONObject();
                    try {
                        tuxingJO.put("token", token);
                        tuxingJO.put("captcha", tuxingET.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String yanzhengtuxingUrl = "shlc/captcha/image/check";
                    httpUrl = http + yanzhengtuxingUrl;
                    HttpPost post = HttpUtil.getPost(httpUrl, tuxingJO);
                    JSONObject jsonObject = HttpUtil.getYanzheng(post, 3);
                    Log.i(TAG, "yanzheng tuxing result:" + jsonObject.toString());
                    try {
                        if (jsonObject.getString("statusCode").equals("1")) {
                            yanzhengFlag = true;
                            toastCommom.ToastShow(FUTableVisitActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), jsonObject.getString("statusMsg"));

                        } else {
                            yanzhengFlag = false;
                            toastCommom.ToastShow(FUTableVisitActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), jsonObject.getString("statusMsg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    toastCommom.ToastShow(FUTableVisitActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请先输入图形验证码");
                }
            }
        });
        duanxinmaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.start();
                Log.i(TAG, "phone number:"+ telStr.length());
                Log.i(TAG, "yanzheng flag:"+ yanzhengFlag);
                if(yanzhengFlag == true){
                    JSONObject duanxinJO = new JSONObject();
                    try {
                        duanxinJO.put("token", token);
                        duanxinJO.put("captcha", tuxingET.getText().toString());
                        duanxinJO.put("phoneNumber", telStr);
                        String yanzhengduanxinUrl = "shlc/captcha/send";
                        httpUrl = http + yanzhengduanxinUrl;
                        HttpPost post = HttpUtil.getPost(httpUrl, duanxinJO);
                        JSONObject jsonObject = HttpUtil.getYanzheng(post, 3);
                        Log.i(TAG, "yanzheng duanxinresult:" + jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    toastCommom.ToastShow(FUTableVisitActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请先验证图形验证码，或检查您的手机号码位数是否正确");
                }
            }
        });

        Intent intent = getIntent();
        if(intent != null){
            telStr = intent.getStringExtra("tel");
            telTV.setText(telStr);
        }
    }


    //取得验证码
    String getTuxingmaUrl = "shlc/captcha/image";
    public void getTuxingma(){
        httpUrl = http+getTuxingmaUrl;
        try {
            Bitmap tuxingBitmap = HttpUtil.getImageMessageForHttpGet(httpUrl);
            token = myApp.getToken();
            tuxingIV.setImageBitmap(tuxingBitmap);
            Log.i(TAG, "token:"+token);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            duanxinmaBtn.setText("重新获取");
            duanxinmaBtn.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            duanxinmaBtn.setClickable(false);
            duanxinmaBtn.setText(millisUntilFinished /1000+"秒");
        }
    }
}
