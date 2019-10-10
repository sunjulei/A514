package com.appname.weare.app514.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 缓存工具类
 */
public class CacheUtils {

    /**
     * 得到保存的String类型的数据
     *
     * @param mContext
     * @param key
     * @return
     */
    public static String getString(Context mContext, String key) {
        String result = "";
        SharedPreferences sp = mContext.getSharedPreferences("Aff", Context.MODE_PRIVATE);
        result = sp.getString(key, "");


        return result;
    }

    /**
     * 保存String类型的数据
     *
     * @param mContext
     * @param key
     * @param value
     */
    public static void saveString(Context mContext, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences("Aff", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}

