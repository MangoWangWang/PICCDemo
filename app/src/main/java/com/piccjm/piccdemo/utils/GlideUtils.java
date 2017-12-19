package com.piccjm.piccdemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.piccjm.piccdemo.R;

/**
 * Created by mangowangwang on 2017/10/23.
 */

public class GlideUtils {

    /**
     * 首页zhihu item读取图片
     *
     * @param imgNumber 图片大小1最大 2中等 3最小正方形的
     * @param url
     * @param image
     */
    public static void loadImage(int imgNumber, String url, ImageView image) {
        Glide.with(Utils.getContext())
                .load(url)  // 加载图片地址
                .crossFade(1500)   // 淡入淡出时间
                .placeholder(getDefaultPic(imgNumber))  // 加载过程中显示的图片
                .error(getDefaultPic(imgNumber))   // 设置加载错误时的图片
                .into(image);  // imageView对象
    }

    private static int getDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.mipmap.img_two_bi_one;
            case 2:
                return R.mipmap.img_four_bi_three;
            case 3:
                return R.mipmap.img_one_bi_one;
        }
        return R.mipmap.img_four_bi_three;
    }

    public static void load(Context mContext, String url, ImageView imageView) {
        //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(mContext).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    public static void loadBenDi(Context mContext,Integer resouceId, ImageView imageView) {
        //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(mContext).load(resouceId).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

}
