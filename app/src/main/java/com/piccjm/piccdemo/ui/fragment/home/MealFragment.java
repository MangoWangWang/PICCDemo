package com.piccjm.piccdemo.ui.fragment.home;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.bean.MealStyleBean;
import com.piccjm.piccdemo.presenter.ordermeal.MealPresenter;
import com.piccjm.piccdemo.presenter.ordermeal.MealPresenterImpl;
import com.piccjm.piccdemo.ui.fragment.base.BaseFragment;
import com.piccjm.piccdemo.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by mangowangwang on 2017/11/22.
 */

public class MealFragment extends BaseFragment<MealPresenterImpl> implements MealPresenter.View {

    public static final int MONDAY = 0;
    public static final int TUESDAY= 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY= 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY= 5;
    public static final int SUNDAY = 6;
    public static final int BREAKFAST = 0;
    public static final int LUNCH = 1;
    public static final int DINNER = 2;



    @BindView(R.id.rg_menu_week_main)
    RadioGroup week_radioGroup;

    @BindView(R.id.rg_meal_day_main)
    RadioGroup day_radioGroup;

    @BindView(R.id.iv_meal_breakfast)
    ImageView iv_mealBreakfast;

    @BindView(R.id.iv_meal_lunch)
    ImageView iv_mealLunch;

    @BindView(R.id.iv_meal_dinner)
    ImageView iv_mealDinner;


    public List<MealStyleBean.WeekBean> getWeekBeanList() {
        return weekBeanList;
    }

    private List<MealStyleBean.WeekBean> weekBeanList; // 主页listBean
    private MealStyleBean.WeekBean dayOnWeek;  // 一周中的一天
    private List<MealStyleBean.WeekBean.MealBean> mealOnDayList; // 一天中的三餐

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

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void refresh() {
        weekBeanList = mPresenter.getWeekList();

        week_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                selectedDayFromWeek(radioGroup,i);
            }
        });

        day_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                selectedMealFromDay(radioGroup,i);
            }
        });



    }

    protected void selectedDayFromWeek(RadioGroup radioGroup, @IdRes int i) {
        int position = 0;
        switch (i)
        {
            case R.id.rb_monday_menu:
                dayOnWeek = weekBeanList.get(MONDAY);
                mealOnDayList = dayOnWeek.getMeal();
                for (MealStyleBean.WeekBean.MealBean mealOnDay: mealOnDayList)
            {
                if (!mealOnDay.isIsOrder())
                {
                    day_radioGroup.getChildAt(position).setEnabled(false);
                }
                position ++;
            }
                break;
            case R.id.rb_tuesday_menu:
                dayOnWeek = weekBeanList.get(TUESDAY);
                mealOnDayList = dayOnWeek.getMeal();
                for (MealStyleBean.WeekBean.MealBean mealOnDay: mealOnDayList)
                {
                    if (!mealOnDay.isIsOrder())
                    {
                        day_radioGroup.getChildAt(position).setEnabled(false);
                    }
                    position ++;
                }
                break;
            case R.id.rb_wednesday_menu:
                dayOnWeek = weekBeanList.get(WEDNESDAY);
                mealOnDayList = dayOnWeek.getMeal();
                for (MealStyleBean.WeekBean.MealBean mealOnDay: mealOnDayList)
                {
                    if (!mealOnDay.isIsOrder())
                    {
                        day_radioGroup.getChildAt(position).setEnabled(false);
                    }
                    position ++;
                }
                break;
            case R.id.rb_thursday_menu:
                dayOnWeek = weekBeanList.get(THURSDAY);
                mealOnDayList = dayOnWeek.getMeal();
                for (MealStyleBean.WeekBean.MealBean mealOnDay: mealOnDayList)
                {
                    if (!mealOnDay.isIsOrder())
                    {
                        day_radioGroup.getChildAt(position).setEnabled(false);
                    }
                    position ++;
                }
                break;
            case R.id.rb_friday_menu:
                dayOnWeek = weekBeanList.get(FRIDAY);
                mealOnDayList = dayOnWeek.getMeal();
                for (MealStyleBean.WeekBean.MealBean mealOnDay: mealOnDayList)
                {
                    if (!mealOnDay.isIsOrder())
                    {
                        day_radioGroup.getChildAt(position).setEnabled(false);
                    }
                    position ++;
                }
                break;
            case R.id.rb_saturday_menu:
                dayOnWeek = weekBeanList.get(SATURDAY);
                mealOnDayList = dayOnWeek.getMeal();
                for (MealStyleBean.WeekBean.MealBean mealOnDay: mealOnDayList)
                {
                    if (!mealOnDay.isIsOrder())
                    {
                        day_radioGroup.getChildAt(position).setEnabled(false);
                    }
                    position ++;
                }
                break;
            case R.id.rb_sunday_menu:
                dayOnWeek = weekBeanList.get(SUNDAY);
                mealOnDayList = dayOnWeek.getMeal();
                for (MealStyleBean.WeekBean.MealBean mealOnDay: mealOnDayList)
                {
                    if (!mealOnDay.isIsOrder())
                    {
                        day_radioGroup.getChildAt(position).setEnabled(false);
                    }
                    position ++;
                }
                break;
            default:
        }
    }
    protected void selectedMealFromDay(RadioGroup radioGroup, @IdRes int i)
    {
        switch (i)
        {
            case R.id.rb_meal_breakfast:
                if (iv_mealLunch.getVisibility() == View.VISIBLE)
                {
                    iv_mealLunch.setVisibility(View.GONE);
                }
                if (iv_mealDinner.getVisibility() == View.VISIBLE)
                {
                    iv_mealDinner.setVisibility(View.GONE);
                }
                if(iv_mealBreakfast.getVisibility() == View.GONE)
                {
                    GlideUtils.load(getContext(),mealOnDayList.get(BREAKFAST).getImage(),iv_mealBreakfast);
                    iv_mealBreakfast.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.rb_meal_lunch:
                if (iv_mealBreakfast.getVisibility() == View.VISIBLE)
                {
                    iv_mealBreakfast.setVisibility(View.GONE);
                }
                if (iv_mealDinner.getVisibility() == View.VISIBLE)
                {
                    iv_mealDinner.setVisibility(View.GONE);
                }
                if(iv_mealLunch.getVisibility() == View.GONE)
                {
                    GlideUtils.loadImage(3,mealOnDayList.get(LUNCH).getImage(),iv_mealLunch);
                    iv_mealLunch.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.rb_meal_dinner:
                if (iv_mealLunch.getVisibility() == View.VISIBLE)
                {
                    iv_mealLunch.setVisibility(View.GONE);
                }
                if (iv_mealBreakfast.getVisibility() == View.VISIBLE)
                {
                    iv_mealBreakfast.setVisibility(View.GONE);
                }
                if(iv_mealDinner.getVisibility() == View.GONE)
                {
                    GlideUtils.loadImage(3,mealOnDayList.get(DINNER).getImage(),iv_mealDinner);
                    iv_mealDinner.setVisibility(View.VISIBLE);

                }
                break;
            default:
        }
    }
}
