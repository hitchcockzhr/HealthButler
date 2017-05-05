package com.housekeeper.ar.healthhousekeeper;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * Created by ZhangRui0113 on 2016/6/1 0001.
 */
public class MyActivityManager {
    private static MyActivityManager instance;
    private static Stack<Activity> activityStack;//activity栈
    private MyActivityManager() {
    }
    //单例模式
    public static MyActivityManager getInstance() {
        if (instance == null) {
            instance = new MyActivityManager();
        }
        return instance;
    }
    //把一个activity压入栈中
    public static  void pushOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
        Log.d("MyActivityManager ", "size = " + activityStack.size());
    }
    //获取栈顶的activity，先进后出原则
    public static  Activity getLastActivity() {
        return activityStack.lastElement();
    }
    //移除一个activity
    public static void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }
    //退出所有activity
    public static void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null) break;
                popOneActivity(activity);
            }
        }
    }}
