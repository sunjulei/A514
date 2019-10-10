package com.appname.weare.app514.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.appname.weare.app514.app.MyAppliction;
import com.appname.weare.app514.app.bean.GoodsBean;
import com.appname.weare.app514.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车数据存储类
 */
public class CartStorage {

    public static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private final Context mContext;
    //优化过的HashMap集合
    private SparseArray<GoodsBean> sparseArray;

    public CartStorage(Context mContext) {
        this.mContext = mContext;
        sparseArray = new SparseArray<>(100);
        listToSparseArray();
    }

    /**
     * 从本地读取的数据加入到SparseArray里
     */
    private void listToSparseArray() {
        List<GoodsBean> carts = getAllData();
        //把List数据转换成SparseArray
        if (carts != null && carts.size() > 0) {
            for (int i = 0; i < carts.size(); i++) {
                GoodsBean goodsBean = carts.get(i);
                sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }
    }

    /**
     * 获取本地所有数据
     *
     * @return
     */
    public List<GoodsBean> getAllData() {

        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //从本地获取
        String json = CacheUtils.getString(mContext, JSON_CART);
        //使用Gson转换成列表
        if (!TextUtils.isEmpty(json)) {
            //把string转换成list
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return goodsBeanList;
    }

    /**
     * 得到购物车实例
     *
     * @return
     */
    public static CartStorage getInstance() {
        if (instance == null) {
            instance = new CartStorage(MyAppliction.getContext());
        }
        return instance;
    }

    /**
     * 添加数据
     *
     * @param goodsBean
     */
    public void addDate(GoodsBean goodsBean) {
        //添加到内存中
        //如果数据存在，就改成数量增加
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData != null) {
            tempData.setNumber(tempData.getNumber() + 1);
        } else {
            tempData = goodsBean;
            tempData.setNumber(1);
        }
        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()), tempData);
        //同步到本地
        saveLocal();
    }

    /**
     * 保存数据到本地
     */
    private void saveLocal() {
        //sparseArray转换成List
        List<GoodsBean> goodsBeanList = sparseToList();
        //用Gson把列表转换成string类型
        String json = new Gson().toJson(goodsBeanList);
        //把string类型保存
        CacheUtils.saveString(mContext, JSON_CART, json);
    }

    private List<GoodsBean> sparseToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;

    }

    /**
     * 删除数据
     *
     * @param goodsBean
     */
    public void deleteData(GoodsBean goodsBean) {
        //从内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //把内存保存到本地
        saveLocal();
    }

    /**
     * 更新数据
     *
     * @param goodsBean
     */
    public void updateData(GoodsBean goodsBean) {
        //内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        //保存到本地
        saveLocal();
    }
}
