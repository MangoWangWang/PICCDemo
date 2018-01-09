package com.piccjm.piccdemo.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.piccjm.piccdemo.R;

/**
 * Created by mangowangwang on 2018/1/2.
 */

public class BirthdayDialog extends Dialog {
    //   private Button no;//取消按钮
    private Button yes;//确定按钮
    private TextView titleTv;//消息标题文本
    private String titleStr;//从外界设置的title文本

    //确定文本和取消文本的显示内容
    private String yesStr, noStr;

    //    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private BirthdayDialog.onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public String dateString; // 默认的日期字符串字符串

    private DatePicker birthdayPicker; // 日期选择picker

    public BirthdayDialog(@NonNull Context context,String birthday) {
        super(context);
        this.dateString = birthday;
    }

    public void setYesOnclickListener(String str, BirthdayDialog.onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public interface onYesOnclickListener {
        public void onYesClick();
    }


    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (noOnclickListener != null) {
//                    noOnclickListener.onNoClick();
//                }
//            }
//        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
//        if (messageStr != null) {
//            messageTv.setText(messageStr);
//        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
//        if (noStr != null) {
//            no.setText(noStr);
//        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.dialog_birthday_yes);
//        no = (Button) findViewById(R.id.no);
        titleTv = (TextView) findViewById(R.id.dialog_birthday_title);
//        messageTv = (TextView) findViewById(R.id.message);
        birthdayPicker = (DatePicker) findViewById(R.id.dialog_birthday_dataPicker);
    }

    private void defaultSelected()
    {
        String birthdayString[] = dateString.split("-");
        String year = birthdayString[0];
        String month = birthdayString[1];
        String day = birthdayString[2];

        birthdayPicker.init(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        dateString = year + "-" + month + "-" + day;
                        System.out.println(dateString);
                    }
                });
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
        //初始化选择中控件
        defaultSelected();
    }
}
