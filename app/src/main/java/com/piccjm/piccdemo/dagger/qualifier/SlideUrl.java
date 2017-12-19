package com.piccjm.piccdemo.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by mangowangwang on 2017/12/12.
 */


@Qualifier
@Documented
@Retention(RUNTIME)
public @interface SlideUrl {
}
