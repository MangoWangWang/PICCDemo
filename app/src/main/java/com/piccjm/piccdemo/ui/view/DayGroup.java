package com.piccjm.piccdemo.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.piccjm.piccdemo.R;

/**
 * Created by mangowangwang on 2017/11/17.
 */

public class DayGroup extends LinearLayout {

    private Context context;

    /**
     * 布局控件
     */
    private TextView dayTextView; // 左边星期几
    public CheckBox cb_breakfast,cb_lunch,cb_dinner,cb_supper; // 分别代表早餐,午餐,晚餐,夜宵的选择框

    /**
     * dayTextView控件对应的自定义属性
     */
    private String dayText;;
    private float dayTextSize;
    private int dayTextColor;

    /**
     * cb_breakfast控件对应的自定义属性
     */

    private Drawable breakfastBackground;

    /**
     * cb_lunch控件对应的自定义属性
     */

    private Drawable lunchBackground;

    /**
     * cb_dinnerfast控件对应的自定义属性
     */

    private Drawable dinnerBackground;

    /**
     * cb_supper控件对应的自定义属性
     */

    private Drawable supperBackground;

    /**
     * cb_supper控件对应的自定义属性
     */

    private Drawable dayTextBackground;




    /**
     * 内部接口的定义
     */
//    public interface titleBarClickListener
//    {
//
//    }
//
//    private titleBarClickListener listener;
//
//    public void setTitleBarClickListener(titleBarClickListener listetner) {
//        this.listener = listetner;
//    }

    public DayGroup (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        findAttrs(attrs);
        initView();
        setDayGroupLayoutParams();
       // setButtonClickListener();
    }

    private void findAttrs(AttributeSet attrs) {
        //obtain 获得 参数二为自定义的styleable名称
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayGroup); // 获得type属性数组

        // 获取xml文件中的属性
        dayText = typedArray.getString(R.styleable.DayGroup_dayText);
        dayTextSize = typedArray.getDimension(R.styleable.DayGroup_dayTextSize,14); // 参数二为默认值
        dayTextColor = typedArray.getColor(R.styleable.DayGroup_dayTextColor,0);


        breakfastBackground = typedArray.getDrawable(R.styleable.DayGroup_breakfastBackground);
        lunchBackground = typedArray.getDrawable(R.styleable.DayGroup_lunchBackground);
        dinnerBackground = typedArray.getDrawable(R.styleable.DayGroup_dinnerBackground);
        supperBackground = typedArray.getDrawable(R.styleable.DayGroup_supperBackground);
        dayTextBackground = typedArray.getDrawable(R.styleable.DayGroup_dayTextBackground);


        // 回收typeArray的缓存
        typedArray.recycle();


    }

    private void initView() {
        //1.实例化控件
        dayTextView = new TextView(context);
        cb_breakfast = new CheckBox(context);
        cb_lunch = new CheckBox(context);
        cb_dinner = new CheckBox(context);
        cb_supper = new CheckBox(context);


        // 2.设置属性
        dayTextView.setText(dayText);
        dayTextView.setTextColor(dayTextColor);
        dayTextView.setTextSize(dayTextSize);
        dayTextView.setGravity(Gravity.CENTER);
        dayTextView.setBackground(dayTextBackground);

        cb_breakfast.setBackground(breakfastBackground);
        cb_breakfast.setButtonDrawable(android.R.color.transparent);
        cb_breakfast.setGravity(Gravity.CENTER);

        //cb_lunch.setButtonDrawable(lunchBackground);
        cb_lunch.setButtonDrawable(android.R.color.transparent);
        cb_lunch.setBackground(lunchBackground);
        cb_lunch.setGravity(Gravity.CENTER);
        cb_dinner.setButtonDrawable(android.R.color.transparent);
        cb_dinner.setBackground(dinnerBackground);
        cb_dinner.setGravity(Gravity.CENTER);
        cb_supper.setButtonDrawable(android.R.color.transparent);
        cb_supper.setBackground(supperBackground);
        cb_supper.setGravity(Gravity.CENTER);

        // 暂时不开放宵夜选项
        cb_supper.setEnabled(true);
        //cb_lunch.setBackground(lunchBackground);
        //cb_dinner.setBackground(dinnerBackground);
        //cb_supper.setBackground(supperBackground);


        // 设置bar的背景颜色
        //setBackgroundColor(0xFF01AAFF);

    }

    private void  setDayGroupLayoutParams() {


        // 设置dayTextView自带属性
        LinearLayout.LayoutParams dayTextViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT,1);
        // 将控件整合属性一起添加到view上
        addView(dayTextView,dayTextViewLayoutParams);

        // 设置breakfast自带属性
        LinearLayout.LayoutParams breakfastLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT,1);
        // 将控件整合属性一起添加到view上
        addView(cb_breakfast,breakfastLayoutParams);

        // 设置lunch自带属性
        LinearLayout.LayoutParams lunchLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT,1);
        // 将控件整合属性一起添加到view上
        addView(cb_lunch,lunchLayoutParams);

        // 设置dinner自带属性
        LinearLayout.LayoutParams dinnerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT,1);
        // 将控件整合属性一起添加到view上
        addView(cb_dinner,dinnerLayoutParams);

        // 设置supperLayout自带属性
        LinearLayout.LayoutParams supperLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT,1);
        // 将控件整合属性一起添加到view上
        addView(cb_supper,supperLayoutParams);

    }

//    public void setButtonClickListener( ) {
//        btnLeft.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.leftButtonClick();
//            }
//        });
//        btnRight.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.rightButtonClick();
//            }
//        });
//    }
}
