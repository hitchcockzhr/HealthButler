package com.housekeeper.ar.healthhousekeeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by lenovo on 2016/1/6.
 */
@SuppressLint("ResourceAsColor")
public class ToastCommom {

    private static ToastCommom toastCommom;

    private Toast toast;

    private ToastCommom(){
    }

    public static ToastCommom createToastConfig(){
        if (toastCommom==null) {
            toastCommom = new ToastCommom();
        }
        return toastCommom;
    }

    /**
     * 显示Toast
     * @param context
     * @param root
     * @param tvString
     */

    public void ToastShow(Context context,ViewGroup root,String tvString){
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_xml,root);
        TextView text = (TextView) layout.findViewById(R.id.text);
//        ImageView mImageView = (ImageView) layout.findViewById(R.id.iv);
//        mImageView.setBackgroundResource(R.drawable.ic_launcher);
        text.setText(tvString);
//        text.setTextColor(context.getResources().getColor(R.color.white));
        toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0,100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
