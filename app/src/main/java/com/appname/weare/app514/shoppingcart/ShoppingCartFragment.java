package com.appname.weare.app514.shoppingcart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appname.weare.app514.R;
import com.appname.weare.app514.app.MainActivity;
import com.appname.weare.app514.app.bean.GoodsBean;
import com.appname.weare.app514.base.BaseFragment;
import com.appname.weare.app514.shoppingcart.adapter.ShoppingCartAdapter;
import com.appname.weare.app514.shoppingcart.utils.CartStorage;

import java.util.List;

public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;

    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;

    private LinearLayout llEmptyShopcart;
    private ShoppingCartAdapter adapter;

    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;

    @Override
    public View initView() {
        Log.e(TAG, "购物车视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_shoppingcart, null);
        tvShopcartEdit = (TextView) view.findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        llCheckAll = (LinearLayout) view.findViewById(R.id.ll_check_all);
        checkboxAll = (CheckBox) view.findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) view.findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) view.findViewById(R.id.btn_check_out);
        llDelete = (LinearLayout) view.findViewById(R.id.ll_delete);
        cbAll = (CheckBox) view.findViewById(R.id.cb_all);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnCollection = (Button) view.findViewById(R.id.btn_collection);
        ivEmpty = (ImageView) view.findViewById(R.id.iv_empty);
        tvEmptyCartTobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);
        llEmptyShopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);

        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection.setOnClickListener(this);
        tvEmptyCartTobuy.setOnClickListener(this);

        initListener();
        return view;
    }

    private void initListener() {
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = (int) tvShopcartEdit.getTag();
                if (state == ACTION_EDIT) {
                    //切换为完成状态
                    showDelete();
                } else {
                    //切换为编辑状态
                    hideDelete();
                }
            }
        });
    }

    /**
     * 编辑完成状态
     */
    private void hideDelete() {
        //1.设置状态和文本-编辑
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        //2.变成非勾选
        if (adapter != null) {
            adapter.checkAll_none(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }
        //3.删除视图显示
        llDelete.setVisibility(View.GONE);
        //4.结算视图隐藏
        llCheckAll.setVisibility(View.VISIBLE);
    }

    /**
     * 进入编辑状态
     */
    private void showDelete() {
        //1.设置状态和文本-完成
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        //2.变成非勾选
        if (adapter != null) {
            adapter.checkAll_none(false);
            adapter.checkAll();
        }
        //3.删除视图显示
        llDelete.setVisibility(View.VISIBLE);
        //4.结算视图隐藏
        llCheckAll.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        if (v == btnCheckOut) {

        } else if (v == btnDelete) {
            //删除选中的产品
            adapter.deleteData();
            adapter.showTotalPrice();
            if (adapter.getItemCount() == 0) {
                emtyShoppingCart();
            }
        } else if (v == btnCollection) {

        } else if (v == tvEmptyCartTobuy) {
            startActivity(new Intent(mContext, MainActivity.class));
        }
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "购物车数据被初始化了");
        showData();

    }

    private void showData() {
        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();

        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.VISIBLE);
            //有数据
            //把当没有数据显示的布局-隐藏
            llEmptyShopcart.setVisibility(View.GONE);

            //设置适配器
            adapter = new ShoppingCartAdapter(mContext, goodsBeanList, tvShopcartTotal, checkboxAll, cbAll, llEmptyShopcart);
            recyclerview.setAdapter(adapter);

            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));


        } else {
            //没有数据
            //显示数据为空的布局
            emtyShoppingCart();
        }
    }

    private void emtyShoppingCart() {
        llEmptyShopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }

}