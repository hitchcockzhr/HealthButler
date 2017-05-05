package com.housekeeper.ar.healthhousekeeper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RememberPwdActivity extends BaseActivity {

    private String TAG = "RememberPwdActivity";

    private Button modifyBtn;

    //	private TextView oldPwdTextView;
    private TextView newPwdTextView;
    private TextView newAgainPwdTextView;

    private Button backBtn;

    private EditText etRegisterName;
    private EditText etCode;
    private Button btCode;
    private Button okBtn;

    private LinearLayout codeLayout;
    private LinearLayout modifyLayout;

    private String userName;

    /**
     * 客户端输入的验证码
     */
    private String valicationCode;

    /**
     * 服务器端获取的验证码
     */
    private static String serverValicationCode;


    public boolean isChange = false;
    private boolean tag = true;
    private int i = 30;
    Thread thread = null;

    Thread codeThread;
    private static ToastCommom toastCommom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyActivityManager.pushOneActivity(RememberPwdActivity.this);
//        SysApplication.getInstance().addActivity(this);

        setContentView(R.layout.activity_remember_psd);

        SysApplication.getInstance().addActivity(this);
        toastCommom = ToastCommom.createToastConfig();
        MyActivityManager.pushOneActivity(RememberPwdActivity.this);

        newPwdTextView = (TextView) findViewById(R.id.new_psd_tv);
        newAgainPwdTextView = (TextView) findViewById(R.id.new_psd_again_tv);
        modifyBtn = (Button) findViewById(R.id.modify_psw_btn);
        backBtn = (Button) findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        okBtn = (Button) findViewById(R.id.ok_btn);
        etRegisterName = (EditText) findViewById(R.id.et_register_username_id);
        etCode = (EditText) findViewById(R.id.et_register_code_id);

        btCode = (Button) findViewById(R.id.bt_getcode_id);

        codeLayout = (LinearLayout) findViewById(R.id.code_layout);
        modifyLayout = (LinearLayout) findViewById(R.id.find_pwd_layout);

        btCode.setOnClickListener(clickListener);
        okBtn.setOnClickListener(clickListener);


        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 获取数据库中的密码
                final String pwdDBString = "ming";

//				final String oldPwdString = oldPwdTextView.getText().toString();
                final String newPwdString = newPwdTextView.getText().toString();
                final String newAgainString = newAgainPwdTextView.getText().toString();

                Log.i(TAG, " newPwdString " + newPwdString + " newAgainString " + newAgainString);

                if (newPwdString.equals("") || newAgainString.equals("")) {
//                    Toast.makeText(RememberPwdActivity.this, "数据不能为空", Toast.LENGTH_LONG).show();
                    toastCommom.ToastShow(RememberPwdActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "数据不能为空");
                    return;
                }

                if (!newPwdString.equals(newAgainString)) {
//                    Toast.makeText(RememberPwdActivity.this, "确认密码与新密码不相同", Toast.LENGTH_LONG).show();
                    toastCommom.ToastShow(RememberPwdActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "确认密码与新密码不相同");
                    return;
                }
                //其他条件

                //TODO 密码修改
            }
        });

    }


    private boolean isvalidate() {
        // TODO Auto-generated method stub
        // 获取控件输入的值
        userName = etRegisterName.getText().toString().trim();

        if (userName == null || userName.equals("")) {
//            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            toastCommom.ToastShow(RememberPwdActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "手机号不能为空");
            return false;
        }
        if (!isPhoneNumberValid(userName)) {
//            Toast.makeText(this, "手机号有误", Toast.LENGTH_SHORT).show();
            toastCommom.ToastShow(RememberPwdActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "手机号有误");
            return false;
        }
        return true;

    }

    /* 说明：移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
2.      * 联通：130、131、132、152、155、156、185、186
3.      * 电信：133、153、180、189
4.      * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
5.      * 验证号码 手机号 固话均可
6.      * 作者：丁国华
7.      * 2015年9月20日 13:52:35
8.      */
    public static boolean isPhoneNumberValid(String phoneNumber) {

        boolean isValid = false;

        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.bt_getcode_id:
                    if (!isvalidate())
                        break;

                    btCode.setText("获取验证码");
                    btCode.setClickable(true);
                    isChange = true;
                    changeBtnGetCode();
                    getValidateCode();
                    break;

                case R.id.ok_btn:

                    if(isValid()){
                        codeLayout.setVisibility(View.GONE);
                        modifyLayout.setVisibility(View.VISIBLE);
                    }


                    break;


                default:
                    break;
            }
        }
    };

    private void changeBtnGetCode() {
        thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (RememberPwdActivity.this == null) {
                            break;
                        }

                        RememberPwdActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btCode.setText("获取验证码(" + i + ")");
                                btCode.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    tag = false;
                }
                i = 30;
                tag = true;
                if (RememberPwdActivity.this != null) {
                    RememberPwdActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btCode.setText("获取验证码");
                            btCode.setClickable(true);
                        }
                    });
                }
            }

            ;
        };
        thread.start();
    }

    /**
     * 说明：获取验证码
     * <p/>
     * 作者： 吴利昌
     * 时间： 2015-9-3 下午3:26:55
     */
    public boolean getValidateCode() {

        String name = etRegisterName.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        if (name.equals("")) {
//            Toast.makeText(this, "请输入用户名或手机号!", Toast.LENGTH_SHORT).show();
            toastCommom.ToastShow(RememberPwdActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "请输入用户名或手机号");
            return false;
        } else {
            userName = name;
            valicationCode = code;
             codeThread= new Thread(codeRunnable);
            codeThread.start();
        }
        return true;
    }

    /**
     * 自定义一个静态的具有弱引用的Handler，解决内存泄漏的问题,本handler用来获取验证码
     */
    private static class CodeHandler extends Handler {
        // 持有对本外部类的弱引用
        private final WeakReference<RememberPwdActivity> mActivity;

        public CodeHandler(RememberPwdActivity activity) {
            mActivity = new WeakReference<RememberPwdActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            // 获取上下文对象
            RememberPwdActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        serverValicationCode = (String) msg.obj;
                        //activity.etCode.setText(serverValicationCode);
                        break;
                    case -1:
//                        Toast.makeText(activity, "获取验证码失败!", Toast.LENGTH_SHORT).show();
                        toastCommom.ToastShow(activity, (ViewGroup) activity.findViewById(R.id.toast_layout_root), "获取验证码失败");
                        break;
                    case 0:
//                        Toast.makeText(activity, "哎呀,出错啦..", Toast.LENGTH_SHORT).show();
                        toastCommom.ToastShow(activity, (ViewGroup) activity.findViewById(R.id.toast_layout_root), "哎呀,出错啦..");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 实例化自定义的handler
     */
    private final CodeHandler codeHandler = new CodeHandler(this);


    /**定义获取验证码的子线程*/
    //TODO 后期需要实现
    private Runnable codeRunnable = new Runnable() {
        @Override
        public void run() {
            Message msg = new Message();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("jbPhone", userName);

            //测试////////////////////////////////////////////////
            msg.what = 1;
            msg.obj = "123456";

            ///////////////////////////////////////////////////////


//            // 获取全局对象Application
//            AppContext appContext = (AppContext) getApplication();
//
//            try {
//                // 获取服务器数据
//                serverValicationCode = appContext.getCode(map);
//
//                // 返回true则将消息的what值为1，为false则what为-1，异常为0
//                if (serverValicationCode.equals("")) {
//                    msg.what = -1;
//                } else {
//                    msg.what = 1;
//                    msg.obj = serverValicationCode;
//                }
//
//            } catch (AppException e) {
//                msg.what = 0;
//                e.printStackTrace();
//            }
           codeHandler.sendMessage(msg);
        }
    };


    /**
     * 说明：确认之前判断数据是否为空
     *
     *
     *
     */
    public boolean isValid() {

        userName = etRegisterName.getText().toString().trim();
        valicationCode = etCode.getText().toString().trim();

        if (userName.equals("")) {
//            Toast.makeText(this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
            toastCommom.ToastShow(RememberPwdActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "用户名不能为空");
            return false;
        }

        if (valicationCode.equals("")) {
//            Toast.makeText(this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
            toastCommom.ToastShow(RememberPwdActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "验证码不能为空");
            return false;
        }

        Log.i(TAG,"serverValicationCode "+serverValicationCode);
        if (serverValicationCode == null||!serverValicationCode.equals(valicationCode)) {
//            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            toastCommom.ToastShow(RememberPwdActivity.this, (ViewGroup) findViewById(R.id.toast_layout_root), "验证码错误");
            return false;
        }


        return true;
    }




}
