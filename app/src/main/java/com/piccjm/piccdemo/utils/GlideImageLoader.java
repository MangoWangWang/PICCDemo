package com.piccjm.piccdemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.piccjm.piccdemo.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by mangowangwang on 2017/10/23.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .placeholder(R.mipmap.img_two_bi_one)
                .error(R.mipmap.img_two_bi_one)
                .crossFade(1000) // 淡入淡出
                .into(imageView);
    }
}
