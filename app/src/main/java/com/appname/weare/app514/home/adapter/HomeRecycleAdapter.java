package com.appname.weare.app514.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.appname.weare.app514.R;
import com.appname.weare.app514.home.bean.ResultBeanData.ResultBean;
import com.appname.weare.app514.utils.Constants;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import java.util.ArrayList;
import java.util.List;

public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /** *五种类型*/

    /**
     * 横幅广告
     */
    public static final int BANNER = 0;

    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 秒杀
     */
    public static final int SECKILL = 3;

    /**
     * 推荐
     * 17
     */
    public static final int RECOMMEND = 4;

    /**
     * 热卖
     */
    public static final int HOT = 5;

    /**
     * 当前类型
     */
    public int currentType = BANNER;

    /**
     * 数据对象
     */
    private ResultBean resultBean;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public HomeRecycleAdapter(Context mContext, ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    /**
     * 根据位置得到类型-系统调用
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
            default:
                break;
        }
        return currentType;
    }


    /**
     * 返回总条数，共六种类型
     *
     * @return
     */
    @Override
    public int getItemCount() {
        //以后做完后改成6，现在只实现横幅广告，暂时写1
        return 2;
    }

    /**
     * 横幅广播的适配器
     * 初始化BannerViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null), mContext, resultBean);
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null), mContext);
        }
        return null;
    }

    /**
     * 绑定数据
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //设置数据Banner的数据
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            //设置数据Channel的数据
            channelViewHolder.setData(resultBean.getChannel_info());
        }
    }


    /**
     * 设置广告轮播图适配器
     */
    class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        public Context mContext;
        public ResultBean resultBean;

        /**
         * 构造器赋值
         */
        public BannerViewHolder(View view, Context mContext, ResultBean resultBean) {
            super(view);
            banner = (Banner) view.findViewById(R.id.banner);
            this.mContext = mContext;
            this.resultBean = resultBean;
        }

        /**
         * 填充数据
         */
        public void setData(final List<ResultBean.BannerInfoBean> banner_info) {
            setBannerData(banner_info);
        }


        /**
         * d定义轮播图的属性及监听
         */
        private void setBannerData(final List<ResultBean.BannerInfoBean> banner_info) {
            //设置循环指标点
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置类似手风琴动画
            banner.setBannerAnimation(Transformer.Accordion);
            //如果你想用自己项目的图片加载,那么----->自定义图片加载框架
            List<String> imageUris = new ArrayList<>();
            for (int i = 0; i < resultBean.getBanner_info().size(); i++) {
                imageUris.add(resultBean.getBanner_info().get(i).getImage());
            }

            //设置加载图片
            banner.setImages(imageUris, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    //使用Glide加载图片
                    Glide.with(mContext).load(Constants.Base_URl_IMAGE + url).into(view);

                }
            });

            //设置点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    int realPosition = position - 1;
                    if (realPosition < banner_info.size()) {
                        String product_id = "";
                        String name = "";
                        String cover_price = "";
                        if (realPosition == 0) {
                            product_id = "627";
                            cover_price = "32.00";
                            name = "剑三T恤批发";
                        } else if (realPosition == 1) {
                            product_id = "21";
                            cover_price = "8.00";
                            name = "同人原创】剑网3 剑侠情缘叁Q版成男口袋袋胸针";
                        } else {
                            product_id = "1341";
                            cover_price = "50.00";
                            name = "【蓝诺】《天下吾双》剑网3同人本";
                        }
                        String image = banner_info.get(realPosition).getImage();
//                        GoodsBean goodsBean = new GoodsBean(name, cover_price, image, product_id);
//                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
//                        intent.putExtra("goods_bean", goodsBean);
//                        mContext.startActivity(intent);
                        Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 设置频道适配器
     */
    class ChannelViewHolder extends RecyclerView.ViewHolder {
        public GridView gvChannel;
        public Context mContext;

        public ChannelViewHolder(View view, Context mContext) {
            super(view);
            gvChannel = (GridView) view.findViewById(R.id.gv_channel);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBean.ChannelInfoBean> channel_info) {
            gvChannel.setAdapter(new ChannelAdapter(mContext, channel_info));
            //点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position <= 8) {
//                        Intent intent = new Intent(mContext, GoodsListActivity.class);
//                        intent.putExtra("position", position);
//                        mContext.startActivity(intent);
                    }
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
