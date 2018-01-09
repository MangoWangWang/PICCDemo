package com.piccjm.piccdemo.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.piccjm.piccdemo.R;

/**
 * Created by mangowangwang on 2018/1/5.
 */

public class HeadDialog extends Dialog {
    // 取消按钮
    private Button no;
    // 消息标题文本
    private TextView titleTv;
    // 从外界设置的title文本
    private String titleStr;

    // 拍照按钮
    private ImageButton takePhotoBt;

    // 图库按钮
    private ImageButton photoGalleryBt;

    //按钮被点击了的监听器
    private OnHeadButtonOnclickListener onHeadButtonClickListener;

    public HeadDialog(@NonNull Context context) {
        super(context);
    }

    public void setHeadButtonOnclickListener( HeadDialog.OnHeadButtonOnclickListener clickListener) {
       this.onHeadButtonClickListener = clickListener;

    }

    public interface OnHeadButtonOnclickListener {
        public void onNoClick();
        public void onTakePhotoClick();
        public void onPhotoGalleryClick();
    }


    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHeadButtonClickListener!= null) {
                    onHeadButtonClickListener.onNoClick();
                }
            }
        });
        takePhotoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHeadButtonClickListener!= null) {
                    onHeadButtonClickListener.onTakePhotoClick();
                }
            }
        });
        photoGalleryBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHeadButtonClickListener!= null) {
                    onHeadButtonClickListener.onPhotoGalleryClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        titleTv = (TextView) findViewById(R.id.dialog_head_title);
        no = (Button) findViewById(R.id.dialog_head_no);
        takePhotoBt = (ImageButton) findViewById(R.id.dialog_head_take_photo);
        photoGalleryBt = (ImageButton) findViewById(R.id.dialog_head_photo_gallery);
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
        setContentView(R.layout.head_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }
}
