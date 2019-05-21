package com.ihesen.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ihesen on 2019-05-21
 */
public class EventBus {

    private static volatile EventBus eventBus;

    private Map<Object, List<SubscribeMethod>> subscribeMethodMap;
    private Handler mHandler;

    private EventBus() {
        subscribeMethodMap = new HashMap<>();
        mHandler = new Handler();
    }

    public static EventBus getDefault() {
        if (eventBus == null) {
            synchronized (EventBus.class) {
                if (eventBus == null) {
                    eventBus = new EventBus();
                }
            }
        }
        return eventBus;
    }

    //管理方法（遍历类中固定注解的方法将其加入到map中）
    public void register(Object obj) {
        if (subscribeMethodMap.get(obj) != null) {
            return;
        }
        Class<?> aClass = obj.getClass();
        while (aClass != null) {
            String name = aClass.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")) {
                break;
            }

            List<SubscribeMethod> list = new ArrayList<>();
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe != null) {
                    SubscribeMethod subscribeMethod = new SubscribeMethod();
                    subscribeMethod.setThreadModle(subscribe.threadModle());
                    subscribeMethod.setMethod(method);
                    subscribeMethod.setParamType(method.getParameterTypes()[0]);
                    list.add(subscribeMethod);
                }
            }
            subscribeMethodMap.put(obj, list);
            aClass = aClass.getSuperclass();
        }
    }

    public void post(final Object data) {
        Set<Object> keySet = subscribeMethodMap.keySet();
        Iterator iterator = keySet.iterator();
        while (iterator.hasNext()) {
            final Object next = iterator.next();
            List<SubscribeMethod> subscribeMethods = subscribeMethodMap.get(next);
            for (final SubscribeMethod subscribeMethod : subscribeMethods) {
                if (data.getClass().isAssignableFrom(subscribeMethod.getParamType())) {
                    //主线程
                    if (subscribeMethod.getThreadModle() == ThreadModle.MAIN) {
                        if (Looper.myLooper() != Looper.getMainLooper()) {
                            Log.e("ihesen", "post thread name: " + Thread.currentThread().getName());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    invoke(next, data, subscribeMethod);
                                }
                            });
                        } else {
                            Log.e("ihesen", "post thread name: " + Thread.currentThread().getName());
                            invoke(next, data, subscribeMethod);
                        }
                    }
                    //子线程
                    else {
                        //可以用线程池实现ThreadPoolExcutor实现
                        new Thread() {
                            @Override
                            public void run() {
                                invoke(next, data, subscribeMethod);
                            }
                        }.start();
                    }
                }
            }
        }
    }

    private void invoke(Object obj, Object data, SubscribeMethod subscribeMethod) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(obj, data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
