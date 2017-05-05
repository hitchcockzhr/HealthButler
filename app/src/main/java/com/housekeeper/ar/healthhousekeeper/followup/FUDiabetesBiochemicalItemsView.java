package com.housekeeper.ar.healthhousekeeper.followup;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.housekeeper.ar.healthhousekeeper.R;

/**
 * 生化全项
 * Created by Lenovo on 2016/11/15.
 */
public class FUDiabetesBiochemicalItemsView extends ViewGroup {
    private Context mContext;
    private LinearLayout parentView;
    String TAG="FUDiabetesBiochemicalItemsView";
    View biochemicalView;



    public FUDiabetesBiochemicalItemsView(Context context) {
        super(context);
        mContext = context;
//        setContentView(R.layout.activity_follow_up_table_diabetes_aux_urine_routine);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public FUDiabetesBiochemicalItemsView(Context context,LinearLayout parent){
        this(context);
        Log.i(TAG, "parent " + parent);
        parentView = parent;
        initView();
    }
    ImageButton deleteBtn;

    EditText gluET;
    TextView gluLevelTV,gluHighValueTV,gluLowValueTV;
    String gluHighValue,gluLowValue;


    EditText gptET;
    TextView gptLevelTV,gptHighValueTV,gptLowValueTV;
    String gptHighValue,gptLowValue;

    EditText gotET;
    TextView gotLevelTV,gotHighValueTV,gotLowValueTV;
    String gotHighValue,gotLowValue;

    EditText tpET;
    TextView tpLevelTV,tpHighValueTV,tpLowValueTV;
    String tpHighValue,tpLowValue;

    EditText albET;
    TextView albLevelTV,albHighValueTV,albLowValueTV;
    String albHighValue,albLowValue;
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
    }


    private String getALB(){
        return albET.getText().toString();
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
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
