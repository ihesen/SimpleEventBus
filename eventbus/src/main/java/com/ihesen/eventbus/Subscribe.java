package com.ihesen.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ihesen.eventbus.ThreadModle.*;

/**
 * Created by ihesen on 2019-05-21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {

    ThreadModle threadModle() default MAIN;
}
