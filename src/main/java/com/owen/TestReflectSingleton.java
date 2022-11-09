package com.owen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflectSingleton {
    public static void main(String[] args) throws InvocationTargetException {
        // 反射获取的实例
        try {
            Class<?> _clazz = Class.forName("com.owen.Singleton");
            if (_clazz != null) {
                Method _getInstance = _clazz.getMethod("getInstance", String.class);
                Object _handler = _getInstance.invoke(_clazz, "yx");
                // 反射后实例已经产生，比较instanceId 说明反射生成的单例 在正常使用单例获取的实例是一个
                System.out.println("instanceId:" + _handler);
                Method _method = _clazz.getMethod("getName");
                if (_method != null) {
                    _method.invoke(_clazz.cast(_handler));
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 非反射获取的实例
        Singleton singleton = Singleton.getInstance("yx");
        // 反射后实例已经产生，比较instanceId 说明反射生成的单例 在正常使用单例获取的实例是一个
        System.out.println("instanceId:" + singleton);
        System.out.println(singleton.getName());
        // 再反射获取的实例
        try {
            Class<?> _clazz = Class.forName("com.owen.Singleton");
            if (_clazz != null) {
                Method _getInstance = _clazz.getMethod("getInstance", String.class);
                Object _handler = _getInstance.invoke(_clazz, "yx");
                // 反射后实例已经产生，比较instanceId 说明反射生成的单例 在正常使用单例获取的实例是一个
                System.out.println("instanceId:" + _handler);
                Method _method = _clazz.getMethod("getName");
                if (_method != null) {
                    _method.invoke(_clazz.cast(_handler));
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
 
class Singleton {
    private static Singleton sInstance = null;
    private String mName = "Singleton";
 
    /**
     * 获取单例
     *
     * @param name 带String型的参数
     * @return
     */
    public static Singleton getInstance(String name) {
        if (sInstance == null) {
            synchronized (Singleton.class) {
                if (sInstance == null) {
                    sInstance = new Singleton(name);
                }
            }
        }
        return sInstance;
    }
 
    /**
     * 带String型的参数私有构造方法
     *
     * @param mame
     */
    private Singleton(String mame) {
        mName = mame;
    }
 
    /**
     * invoke method
     *
     * @return
     */
    public String getName() {
        System.out.println("getName：" + mName);
        return mName;
    }
}