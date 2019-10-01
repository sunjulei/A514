package com.appname.weare.app514.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appname.weare.app514.R;
import com.appname.weare.app514.home.bean.ResultBeanData;
import com.appname.weare.app514.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecommendGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ResultBeanData.ResultBean.RecommendInfoBean> data;

    public RecommendGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean> data) {
        this.mContext = mContext;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = data.get(position);
        Glide.with(mContext).load(Constants.Base_URl_IMAGE + recommendInfoBean.getFigure()).into(holder.iv_recommend);
        holder.tvName.setText(recommendInfoBean.getName());
        holder.tvPrice.setText("￥" + recommendInfoBean.getCover_price());

        return convertView;
    }

   static class ViewHolder {
        @Bind(R.id.iv_recommend)
        ImageView iv_recommend;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
