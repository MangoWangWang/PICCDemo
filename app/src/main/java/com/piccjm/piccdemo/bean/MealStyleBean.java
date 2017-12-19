package com.piccjm.piccdemo.bean;

import java.util.List;

/**
 * Created by mangowangwang on 2017/11/27.
 */

public class MealStyleBean {

    private List<WeekBean> week;

    public List<WeekBean> getWeek() {
        return week;
    }

    public void setWeek(List<WeekBean> week) {
        this.week = week;
    }

    public static class WeekBean {
        /**
         * day : monday
         * meal : [{"image":"https://pic1.zhimg.com/v2-5974b7251c67747e6d2422164a6b2624.jpg","type":"breakfast","isOrder":false},{"image":"https://pic1.zhimg.com/v2-5974b7251c67747e6d2422164a6b2624.jpg","type":"lunch","isOrder":true},{"image":"https://pic1.zhimg.com/v2-5974b7251c67747e6d2422164a6b2624.jpg","type":"dinner","isOrder":true}]
         */

        private String day;
        private List<MealBean> meal;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public List<MealBean> getMeal() {
            return meal;
        }

        public void setMeal(List<MealBean> meal) {
            this.meal = meal;
        }

        public static class MealBean {
            /**
             * image : https://pic1.zhimg.com/v2-5974b7251c67747e6d2422164a6b2624.jpg
             * type : breakfast
             * isOrder : false
             */

            private String image;
            private String type;
            private boolean isOrder;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isIsOrder() {
                return isOrder;
            }

            public void setIsOrder(boolean isOrder) {
                this.isOrder = isOrder;
            }
        }
    }
}
