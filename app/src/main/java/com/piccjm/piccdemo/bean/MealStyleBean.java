package com.piccjm.piccdemo.bean;

import java.util.List;

/**
 * Created by mangowangwang on 2017/11/27.
 */

public class MealStyleBean {

    private List< DayBean> week;

    public List< DayBean> getWeek() {
        return week;
    }

    public void setWeek(List<DayBean> week) {
        this.week = week;
    }

    public static class DayBean {
        /**
         * day : monday
         * meal : [{"image":"https://pic1.zhimg.com/v2-5974b7251c67747e6d2422164a6b2624.jpg","type":"breakfast","isOrder":false},{"image":"https://pic1.zhimg.com/v2-5974b7251c67747e6d2422164a6b2624.jpg","type":"lunch","isOrder":true},{"image":"https://pic1.zhimg.com/v2-5974b7251c67747e6d2422164a6b2624.jpg","type":"dinner","isOrder":true}]
         */

        private String day;
        private List<MealBean> meal;

        private boolean openMeal;

        public boolean isOpenMeal() {
            return openMeal;
        }

        public void setOpenMeal(boolean openMeal) {
            this.openMeal = openMeal;
        }


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
             * menuName : 腐竹焖猪脚
             * type : breakfast
             * isOrder : false
             */
            private String menuName;
            private String type;
            private boolean isOrder;


            public String getMenuName() {
                return menuName;
            }

            public void setMenuName(String menuName) {
                this.menuName = menuName;
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
