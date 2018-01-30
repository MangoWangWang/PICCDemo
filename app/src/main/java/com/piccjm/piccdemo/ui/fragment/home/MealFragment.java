package com.piccjm.piccdemo.ui.fragment.home;

import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.bean.DateOrderBean;
import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.presenter.ordermeal.MealPresenter;
import com.piccjm.piccdemo.presenter.ordermeal.MealPresenterImpl;
import com.piccjm.piccdemo.ui.fragment.base.BaseFragment;
import com.piccjm.piccdemo.utils.DateUtil;
import com.piccjm.piccdemo.utils.GlideUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mangowangwang on 2017/11/22.
 */

public class MealFragment extends BaseFragment<MealPresenterImpl> implements MealPresenter.View {

    //String imageHost = "http://10.0.10.187:8080/Breakfast/";
    String breakfastImageHost = "http://120.79.62.147:8080/PiccLife/Breakfast/";
    String lunchAndDinnerImageHost = "http://120.79.62.147:8080/PiccLife/LunchAndDinner/";

    private final static int SETTING_TIME = 9 ;
    public static final int TOTLE_DAYS = 7;

    @BindView(R.id.breakfast_image)
    LinearLayout breakfastImages;
    @BindView(R.id.breakfast_textView)
    LinearLayout breakfastTexts;
    @BindView(R.id.breakfast_checkbox)
    CheckBox breakfast_checkbox;

    @BindView(R.id.lunch_roulei_title)
    TextView lunchRouLeiTitle;
    @BindView(R.id.lunch_roulei_image)
    LinearLayout lunchRouLeiImages;
    @BindView(R.id.lunch_ruolei_radiogroup)
    RadioGroup lunchRouLeiGroup;

    @BindView(R.id.lunch_shucai_title)
    TextView lunchShuCaiTitle;
    @BindView(R.id.lunch_shucai_image)
    LinearLayout lunchShuCaiImages;
    @BindView(R.id.lunch_shucai_textView)
    LinearLayout lunchShuCaiTexts;

    @BindView(R.id.lunch_checkbox)
    CheckBox lunch_checkbox;
    @BindView(R.id.lunch_one_radioButton)
    RadioButton lunch_one_radioButton;
    @BindView(R.id.lunch_two_radioButton)
    RadioButton lunch_two_radioButton;


    @BindView(R.id.dinner_image)
    LinearLayout dinnerImages;
    @BindView(R.id.dinner_textView)
    LinearLayout dinnerTexts;
    @BindView(R.id.rg_menu_week_main)
    RadioGroup weekGroup;
    @BindView(R.id.dinner_checkbox)
    CheckBox dinner_checkbox;


    private float density;


    @OnClick(R.id.button_breakfast)
    public void openOrShutBreakfastView() {
        visibleBreakfastView();
    }


    @OnClick(R.id.button_lunch)
    public void openOrShutLunchView() {
        visibleLunchView();
    }


    @OnClick(R.id.button_dinner)
    public void openOrShutDinnerView() {
        visibleDinnerView();
    }

    @OnClick(R.id.post_orderButton)
    public void postOrder() {
        if (isSelectedDay) {
            mPresenter.PostDateOrder(selectedDateOrderBean());
        } else {
            Toast.makeText(getContext(), "请先选择星期几!", Toast.LENGTH_SHORT).show();
        }

    }

    // 获得的每日菜单
    private List<MealStyleBean.DayBean> days;

    // 点击星期的位置
    private int position;

    // 点击星期的日期
    private String selected_date;

    // 判断日期按钮是否被选中,确定按钮是否可以提交
    private boolean isSelectedDay = false;

    // 三个代表checkbox能否被选中的值 0:未选中 1:选中 2:午餐专用(类型Two)
    private int cb_breakfast_status = 0;
    private int cb_lunch_status = 0;
    private int cb_dinner_status = 0;

    // 代表午餐radioButton的选中值 0:未选中 1: 选中第一个 2:选中第二个
    private int rb_lunch_status = 0;

    private DateOrderBean dateOrderBean;


    @Override
    protected void loadData() {
        mPresenter.fetchData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meun;
    }

    @Override
    protected void initView() {
        getDensity();
        weekGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                weightOfRadioButton(radioGroup, i);

                LoadingBreakfastOfMenu(position);
                LoadingLunchOfMenu(position);
                LoadingDinnerOfMenu(position);
                mPresenter.getDateOrder(selected_date);


            }
        });
        setCheckboxChangeListener();
        setLunchRadioButtonListener();


    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void refresh() {

        // 1.获得服务器返回的数据
        days = mPresenter.getDayOfWeekList();
        todayOrTomorrow();
        dayOfOpenMeal();


    }

    /**
     * 判断时间点有没有超过每天的10点钟
     */
    private void todayOrTomorrow() {
        Date today = new Date();
        String weekOfDate = DateUtil.getWeekOfDate(today);
        for (int position = 0 ; position < TOTLE_DAYS; position++) {
            RadioButton rb = (RadioButton) weekGroup.getChildAt(position);
            boolean equals = rb.getText().equals(weekOfDate);
            if (equals) {
                if (DateUtil.getHourOfDay(today) <= SETTING_TIME) {
                    rb.setEnabled(true);
                } else {
                    rb.setEnabled(false);
                    weekGroup.getChildAt(position + 1).setEnabled(true);
                    position = position + 1;
                }
            } else {
                rb.setEnabled(false);
            }
        }


    }


    protected void visibleBreakfastView() {
        if (breakfastImages.getVisibility() == View.GONE) {
            breakfastImages.setVisibility(View.VISIBLE);
            breakfastTexts.setVisibility(View.VISIBLE);
        } else {
            breakfastImages.setVisibility(View.GONE);
            breakfastTexts.setVisibility(View.GONE);
        }
    }

    protected void visibleLunchView() {
        if (lunchRouLeiTitle.getVisibility() == View.GONE) {
            lunchRouLeiTitle.setVisibility(View.VISIBLE);
            lunchRouLeiImages.setVisibility(View.VISIBLE);
            lunchRouLeiGroup.setVisibility(View.VISIBLE);

            lunchShuCaiTitle.setVisibility(View.VISIBLE);
            lunchShuCaiImages.setVisibility(View.VISIBLE);
            lunchShuCaiTexts.setVisibility(View.VISIBLE);

        } else {
            lunchRouLeiTitle.setVisibility(View.GONE);
            lunchRouLeiImages.setVisibility(View.GONE);
            lunchRouLeiGroup.setVisibility(View.GONE);

            lunchShuCaiTitle.setVisibility(View.GONE);
            lunchShuCaiImages.setVisibility(View.GONE);
            lunchShuCaiTexts.setVisibility(View.GONE);
        }
    }

    protected void visibleDinnerView() {
        if (dinnerImages.getVisibility() == View.GONE) {
            dinnerImages.setVisibility(View.VISIBLE);
            dinnerTexts.setVisibility(View.VISIBLE);
        } else {
            dinnerImages.setVisibility(View.GONE);
            dinnerTexts.setVisibility(View.GONE);
        }
    }

    protected void weightOfRadioButton(RadioGroup radioGroup, @IdRes int i) {
        RadioGroup.LayoutParams Params;
        for (int position = 0; position < TOTLE_DAYS; position++) {
            RadioButton dayButton = (RadioButton) radioGroup.getChildAt(position);
            if (!dayButton.isChecked()) {
                Params = (RadioGroup.LayoutParams) dayButton.getLayoutParams(); //取控件textView当前的布局参数
                //Params.height = 20;// 控件的高强制设成20
                Params.width = (int) (88 * density);// 控件的宽强制设成30
                dayButton.setLayoutParams(Params);
            }
        }

        RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
        Params = (RadioGroup.LayoutParams) radioButton.getLayoutParams(); //取控件textView当前的布局参数
        //Params.height = 20;// 控件的高强制设成20
        Params.width = (int) (100 * density);// 控件的宽强制设成30
        radioButton.setLayoutParams(Params); //使设置好的布局参数应用到控件
        position = positionOfClickOnWeek(radioButton.getText().toString());

    }

    /**
     * 判读当天当天是否可以开餐
     */
    private void dayOfOpenMeal() {
        for (int position = 0; position < TOTLE_DAYS; position++) {
            // 不可以开饭的话,取反将对应的星期按钮设置为不可点击
            if (!days.get(position).isOpenMeal()) {
                weekGroup.getChildAt(position).setEnabled(false);
            }
        }
    }

    private void LoadingBreakfastOfMenu(int position) {


        // 1.先取出早餐菜单字符串,组合出图片
        MealStyleBean.DayBean selected_day = days.get(position);
        MealStyleBean.DayBean.MealBean breakfast_day = selected_day.getMeal().get(0);
        String breakfast_meal = breakfast_day.getMenuName();

        String breakfasts[] = breakfast_meal.split(",");
        int size = breakfasts.length;

        breakfastImages.removeAllViews();
        breakfastTexts.removeAllViews();


        for (int i = 0; i < size; i++) {
            String mealImageString = breakfastImageHost + breakfasts[i] + ".jpg";
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            layoutParams.setMargins(5, 5, 5, 5);
            ImageView imageView = new ImageView(getContext());
            imageView.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(3, mealImageString, imageView);
            breakfastImages.addView(imageView, layoutParams);
            TextView textView = new TextView(getContext());
            textView.setVisibility(View.VISIBLE);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.textview_background);
            textView.setText(breakfasts[i]);
            breakfastTexts.addView(textView, layoutParams);
        }
    }

    private void LoadingLunchOfMenu(int position) {
        // 1.先取出午餐菜单字符串,组合出图片
        MealStyleBean.DayBean selected_day = days.get(position);
        MealStyleBean.DayBean.MealBean lunch_day = selected_day.getMeal().get(1);
        String lunch_meal = lunch_day.getMenuName();
        String lunches[] = lunch_meal.split(",");

        // 前两个填充到肉类
        ImageView iv_firstMeat = (ImageView) lunchRouLeiImages.getChildAt(0);
        ImageView iv_secondMeat = (ImageView) lunchRouLeiImages.getChildAt(1);
        RadioButton rb_firstMeat = (RadioButton) lunchRouLeiGroup.getChildAt(0);
        RadioButton rb_secondMeat = (RadioButton) lunchRouLeiGroup.getChildAt(1);

        GlideUtils.loadImage(4, lunchAndDinnerImageHost + lunches[0] + ".jpg", iv_firstMeat);
        GlideUtils.loadImage(4, lunchAndDinnerImageHost + lunches[1] + ".jpg", iv_secondMeat);

        rb_firstMeat.setText(lunches[0]);
        rb_secondMeat.setText(lunches[1]);


        // 后两个填充到蔬菜类
        ImageView iv_firstVegetable = (ImageView) lunchShuCaiImages.getChildAt(0);
        ImageView iv_secondVegetable = (ImageView) lunchShuCaiImages.getChildAt(1);
        TextView tv_firstVegetable = (TextView) lunchShuCaiTexts.getChildAt(0);
        TextView tv_secondVegetable = (TextView) lunchShuCaiTexts.getChildAt(1);

        GlideUtils.loadImage(4, lunchAndDinnerImageHost + lunches[2] + ".jpg", iv_firstVegetable);
        GlideUtils.loadImage(4, lunchAndDinnerImageHost + lunches[3] + ".jpg", iv_secondVegetable);

        tv_firstVegetable.setText(lunches[2]);
        tv_secondVegetable.setText(lunches[3]);

    }

    private void LoadingDinnerOfMenu(int position) {

        // 1.先取出午餐菜单字符串,组合出图片
        MealStyleBean.DayBean selected_day = days.get(position);
        MealStyleBean.DayBean.MealBean lunch_day = selected_day.getMeal().get(2);
        String dinner_meal = lunch_day.getMenuName();
        String dinners[] = dinner_meal.split(",");

        // 后两个填充到蔬菜类
        ImageView iv_firstMeat = (ImageView) dinnerImages.getChildAt(0);
        ImageView iv_firstVegetable = (ImageView) dinnerImages.getChildAt(1);
        TextView tv_firstMeat = (TextView) dinnerTexts.getChildAt(0);
        TextView tv_secondVegetable = (TextView) dinnerTexts.getChildAt(1);

        GlideUtils.loadImage(4, lunchAndDinnerImageHost + dinners[0] + ".jpg", iv_firstMeat);
        GlideUtils.loadImage(4, lunchAndDinnerImageHost + dinners[1] + ".jpg", iv_firstVegetable);

        tv_firstMeat.setText(dinners[0]);
        tv_secondVegetable.setText(dinners[1]);
    }

    private int positionOfClickOnWeek(String day) {
        if (day.equals("星期一")) {
            isSelectedDay = true;
            selected_date = days.get(0).getDay();
            return 0;
        } else if (day.equals("星期二")) {
            isSelectedDay = true;
            selected_date = days.get(1).getDay();
            return 1;
        } else if (day.equals("星期三")) {
            isSelectedDay = true;
            selected_date = days.get(2).getDay();
            return 2;
        } else if (day.equals("星期四")) {
            isSelectedDay = true;
            selected_date = days.get(3).getDay();
            return 3;
        } else if (day.equals("星期五")) {
            isSelectedDay = true;
            selected_date = days.get(4).getDay();
            return 4;
        } else if (day.equals("星期六")) {
            isSelectedDay = true;
            selected_date = days.get(5).getDay();
            return 5;
        } else if (day.equals("星期日")) {
            isSelectedDay = true;
            selected_date = days.get(6).getDay();
            return 6;
        }
        return -1;
    }

    @Override
    public void PostOrderResult(String result) {
        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
    }

    private DateOrderBean selectedDateOrderBean() {
        DateOrderBean.MealOrderBean mealOrderBean = dateOrderBean.getMeal_order().get(0);
        mealOrderBean.setDate(selected_date);
        //mealOrderBean.setOrderTime("201TOTLE_DAYS-12-25 11:00:00");
        mealOrderBean.setBreakfast(cb_breakfast_status);
        mealOrderBean.setLunch(cb_lunch_status);
        mealOrderBean.setDinner(cb_dinner_status);
        return dateOrderBean;
    }

    // 用于设置radioButton和checkBox的监听事件
    private void setCheckboxChangeListener() {
        breakfast_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_breakfast_status = 1;
                } else {
                    cb_breakfast_status = 0;
                }
            }
        });
        lunch_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    cb_lunch_status = 0;
                } else if (rb_lunch_status == 0 && b) // 表示没有选择午餐的肉类菜式
                {
                    cb_lunch_status = 0;
                    lunch_checkbox.setChecked(false);
                    Toast.makeText(getContext(), "请先选择午餐菜式", Toast.LENGTH_SHORT).show();

                } else if (rb_lunch_status == 1 && b) {
                    cb_lunch_status = 1;
                } else if (rb_lunch_status == 2 && b) {
                    cb_lunch_status = 2;
                }

            }
        });
        dinner_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_dinner_status = 1;
                } else {
                    cb_dinner_status = 0;
                }

            }
        });
    }

    private void setLunchRadioButtonListener() {

        lunchRouLeiGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (lunch_checkbox.isChecked()) {
                    if (i == lunch_one_radioButton.getId()) {
                        cb_lunch_status = 1;
                    } else if (i == lunch_two_radioButton.getId()) {
                        cb_lunch_status = 2;
                    }
                } else {
                    if (i == lunch_one_radioButton.getId()) {
                        rb_lunch_status = 1;
                    } else if (i == lunch_two_radioButton.getId()) {
                        rb_lunch_status = 2;
                    }
                }


            }
        });


//        lunch_one_radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b)
//                {
//                    rb_lunch_status = 1 ;
//                }
//            }
//        });
//        lunch_two_radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b)
//                {
//                    rb_lunch_status = 2 ;
//                }
//            }
//        });
    }

    @Override
    public void setSelectedCheckBoxAndRadioButton(DateOrderBean dateOrderFromService) {
        dateOrderBean = dateOrderFromService;

        // 取出其中的mealOrder
        DateOrderBean.MealOrderBean mealOrder = dateOrderBean.getMeal_order().get(0);
        if (mealOrder.getBreakfast() == 0) {
            cb_breakfast_status = 0;
            breakfast_checkbox.setChecked(false);

        } else if (mealOrder.getBreakfast() == 1) {
            cb_breakfast_status = 1;
            breakfast_checkbox.setChecked(true);

        }


        if (mealOrder.getLunch() == 0) {
            cb_lunch_status = 0;
            rb_lunch_status = 0;
            lunchRouLeiGroup.clearCheck();
            lunch_checkbox.setChecked(false);

        } else if (mealOrder.getLunch() == 1) {
            rb_lunch_status = 1;
            cb_lunch_status = 1;
            lunchRouLeiGroup.check(lunch_one_radioButton.getId());
            lunch_checkbox.setChecked(true);
        } else if (mealOrder.getLunch() == 2) {
            rb_lunch_status = 2;
            cb_lunch_status = 2;
            lunchRouLeiGroup.check(lunch_two_radioButton.getId());
            lunch_checkbox.setChecked(true);
        }


        if (mealOrder.getDinner() == 0) {
            cb_dinner_status = 0;
            dinner_checkbox.setChecked(false);

        } else if (mealOrder.getDinner() == 1) {
            cb_dinner_status = 1;
            dinner_checkbox.setChecked(true);
        }

    }

    private void getDensity() {
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
    }

}
