package com.piccjm.piccdemo.bean;

import java.util.List;

/**
 * Created by mangowangwang on 2017/11/24.
 */

public class DataListBean {

    public List<GirlBean> getGirlList() {
        return girlList;
    }

    public void setGirlList(List<GirlBean> girlList) {
        this.girlList = girlList;
    }

    private List<GirlBean> girlList;


    public static class GirlBean
    {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;
        private String image;
    }

}


