package com.piccjm.piccdemo.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.piccjm.piccdemo.R;


/**
 * Created by MangoWang on 2017/9/23.
 */

public class PicAndTextBtn extends LinearLayout {
    private Context context;

    /**
     * 布局控件
     */
    private ImageView imageView;
    private TextView textView;

    /**
     * 布局属性
     */
    private Drawable image;
    private Drawable imageBackground;
    private String text;
    private float textSize;
    private int textColor;
    private Drawable textBackground;


    public void setImageView(int id) {
        imageView.setImageResource(id);
    }

    /**
     * 自定义回调借口
     */

    interface picAndTextBtnClickListener {
        void onClick(View view);
    }
    private picAndTextBtnClickListener listener;

    public void setOnClickListener(picAndTextBtnClickListener listener) {
        this.listener = listener;
    }


    /**
     *
     * @param context 上下文
     * @param attrs 自定义attr文件
     */
    public PicAndTextBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        findAttrs(attrs);
        initViews();
        setPicAndTextBtnLayoutParams();
        setClickListener();
    }

    private void findAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PicAndTextBtn);
        image = typedArray.getDrawable(R.styleable.PicAndTextBtn_pic);
        imageBackground = typedArray.getDrawable(R.styleable.PicAndTextBtn_picBackground);
        text = typedArray.getString(R.styleable.PicAndTextBtn_text);
        textSize = typedArray.getDimension(R.styleable.PicAndTextBtn_textSize, 0);
        textColor = typedArray.getColor(R.styleable.PicAndTextBtn_textColor, 0);
        textBackground = typedArray.getDrawable(R.styleable.PicAndTextBtn_textBackground);
        typedArray.recycle();
    }

    private void initViews() {
        imageView = new ImageView(context);
        textView = new TextView(context);

        imageView.setImageDrawable(image);
        imageView.setBackground(imageBackground);

        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setBackground(textBackground);

        textView.setGravity(Gravity.RIGHT);
    }

    private void setPicAndTextBtnLayoutParams() {

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(28, 0, 0, 0);


        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
        addView(textView, textViewLayoutParams);

        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addView(imageView, imageLayoutParams);


    }

    private void setClickListener() {
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
    }
}
