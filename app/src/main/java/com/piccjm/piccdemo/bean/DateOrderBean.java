package com.piccjm.piccdemo.bean;

import java.util.List;

/**
 * Created by mangowangwang on 2017/11/29.
 */

public class DateOrderBean {

    /**
     * card_number : 12345678
     * meal_order : [{"time":"2017-11-27","breakfast":true,"lunch":false,"dinner":false},{"time":"2017-11-28","breakfast":false,"lunch":true,"dinner":false},{"time":"2017-11-29","breakfast":false,"lunch":false,"dinner":true},{"time":"2017-11-30","breakfast":true,"lunch":false,"dinner":false},{"time":"2017-12-1","breakfast":false,"lunch":true,"dinner":false},{"time":"2017-12-2","breakfast":false,"lunch":false,"dinner":true},{"time":"2017-12-3","breakfast":true,"lunch":false,"dinner":false}]
     */

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    private String card_number;
    private List<MealOrderBean> meal_order;


    public List<MealOrderBean> getMeal_order() {
        return meal_order;
    }

    public void setMeal_order(List<MealOrderBean> meal_order) {
        this.meal_order = meal_order;
    }

    public static class MealOrderBean {
        /**
         * time : 2017-11-27
         * breakfast : 1
         * lunch : 1
         * dinner : 1
         */

        private String date;
        private String orderTime;
        private int breakfast;
        private int lunch;
        private int dinner;

        public int getBreakfast() {
            return breakfast;
        }

        public void setBreakfast(int breakfast) {
            this.breakfast = breakfast;
        }

        public int getLunch() {
            return lunch;
        }

        public void setLunch(int lunch) {
            this.lunch = lunch;
        }

        public int getDinner() {
            return dinner;
        }

        public void setDinner(int dinner) {
            this.dinner = dinner;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }



    }
}
