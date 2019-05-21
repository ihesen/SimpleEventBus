package com.ihesen.eventbus;

import java.lang.reflect.Method;

/**
 * Created by ihesen on 2019-05-21
 */
public class SubscribeMethod {

    private Method method;

    private Class paramType;

    private ThreadModle threadModle;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getParamType() {
        return paramType;
    }

    public void setParamType(Class paramType) {
        this.paramType = paramType;
    }

    public ThreadModle getThreadModle() {
        return threadModle;
    }

    public void setThreadModle(ThreadModle threadModle) {
        this.threadModle = threadModle;
    }
}
