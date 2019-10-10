package com.appname.weare.app514.shoppingcart.adapter;

import android.content.Context;
import android.preference.TwoStatePreference;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appname.weare.app514.R;
import com.appname.weare.app514.app.bean.GoodsBean;
import com.appname.weare.app514.shoppingcart.utils.CartStorage;
import com.appname.weare.app514.shoppingcart.view.AddSubView;
import com.appname.weare.app514.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private final List<GoodsBean> datas;
    private final Context mContext;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    private final CheckBox cbAll;
    private final LinearLayout llEmptyShopcart;


    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll, LinearLayout llEmptyShopcart) {
        this.mContext = mContext;
        this.datas = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        this.llEmptyShopcart=llEmptyShopcart;

        //首次加载数据
        showTotalPrice();
        //设置点击事件
        setListener();

    }

    private void setListener() {
        //结算时的全选点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1.得到状态
                boolean isCheck = checkboxAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);
                //3.计算总价格
                showTotalPrice();
            }
        });

        //编辑状态下的全选点击事件
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1.得到状态
                boolean isCheck = cbAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);
            }
        });

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1.根据位置找到对应的Bean对象
                GoodsBean goodsBean = datas.get(position);
                //2.设置取反状态
                goodsBean.setIsChildSelected(!goodsBean.isChildSelected());
                //3.刷新状态
                notifyItemChanged(position);
                //4.校验是否全选
                checkAll();
                //5.重新计算总价格
                showTotalPrice();
            }
        });
    }


    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isChildSelected()) {
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    //选中的
                    number++;
                }
            }

            if (number == datas.size()) {
                //全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        } else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    /**
     * 设置全选和非全选
     *
     * @param isCheck
     */
    public void checkAll_none(boolean isCheck) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setIsChildSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_shop_cart, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //1.根据位置得到对应的Bean对象
        final GoodsBean goodsBean = datas.get(position);
        //2.设置数据
        holder.cbGov.setChecked(goodsBean.isChildSelected());
        Glide.with(mContext).load(Constants.BASE_URl_IMAGE + goodsBean.getFigure()).into(holder.ivGov);
        holder.tvDescGov.setText(goodsBean.getName());
        holder.tvPriceGov.setText("￥" + goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.addSubView.setMinValue(1);
        holder.addSubView.setMaxValue(9);

        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1.当前列表内存中
                goodsBean.setNumber(value);
                //2本地要更新
                CartStorage.getInstance().updateData(goodsBean);
                //3.刷新适配器
                notifyItemChanged(position);
                //4.再次计算总价格
                showTotalPrice();
            }
        });
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("" + getTotalPrice());
    }

    private double getTotalPrice() {
        double total = 0.0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isChildSelected()) {
                    total += Double.parseDouble(goodsBean.getCover_price()) * Double.parseDouble(goodsBean.getNumber() + "");
                }
            }
        }
        return total;
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 删除数据
     */
    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //删除选中的
                if (goodsBean.isChildSelected()) {
                    //内存-把移除
                    datas.remove(goodsBean);
                    //保持到本地
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbGov;
        private ImageView ivGov;
        private TextView tvDescGov;
        private TextView tvPriceGov;
        private AddSubView addSubView;

        public ViewHolder(View itemView) {
            super(itemView);
            cbGov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            ivGov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tvDescGov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tvPriceGov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            addSubView = (AddSubView) itemView.findViewById(R.id.addSubView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
