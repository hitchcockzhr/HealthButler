package com.housekeeper.ar.healthhousekeeper.followup.view;

/**
 * Created by Lenovo on 2016/11/29.
 */
import java.io.Serializable;

public class Information implements Serializable
{
    public String weidu; //名称
    public String dulian1; //数据
    public String bfb; //百分比
    public String dw; //单位


    public Information(String weidu, String dulian1, String dw)
    {
        super();
        this.weidu = weidu;
        this.dulian1 = dulian1;
        this.dw = dw;
    }


    public Information ()
    {
    }
}
