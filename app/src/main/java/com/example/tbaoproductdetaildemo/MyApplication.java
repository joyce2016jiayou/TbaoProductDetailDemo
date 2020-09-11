package com.example.tbaoproductdetaildemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;



import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class MyApplication extends Application {
    private static Context context;

    private static Map<String, Activity> destoryMap = new HashMap<>();

    public MyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getContext();
        //初始化sdk


    }
    public static Context getContext() {
        return context;
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            destoryMap.get(key).finish();
        }
    }


}
