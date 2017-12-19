package com.piccjm.piccdemo.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by mangowangwang on 2017/11/24.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface OrderMealUrl {
}
