package com.appname.weare.app514.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     */
    @Override
    public int getItemCount() {
        //以后做完后改成6，现在只实现横幅广告，暂时写1
        return 4;
    }

    /**
     * 横幅广播的适配器
     * 初始化ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null), mContext, resultBean);
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null), mContext);
        } else if (viewType == ACT) {
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.act_item, null), mContext);
        }else if (viewType==SECKILL){
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item, null), mContext);
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
            //设置Banner的数据
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            //设置Channel的数据
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            //设置Act的数据
            actViewHolder.setData(resultBean.getAct_info());
        }else if (getItemViewType(position)==SECKILL){
            SeckillViewHolder seckillViewHolder= (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
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

    /**
     * 设置活动适配器
     */
    class ActViewHolder extends RecyclerView.ViewHolder {
        public ViewPager actViewPager;
        public Context mContext;

        public ActViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.actViewPager = itemView.findViewById(R.id.act_viewpager);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBean.ActInfoBean> data) {
            //设置每个页面的间距
            actViewPager.setPageMargin(20);
            //设置缓存的页面数量
            actViewPager.setOffscreenPageLimit(3);
            //设置动画
            actViewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

            //设置适配器
            actViewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return data.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object o) {
                    return view == o;
                }


                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    ImageView view = new ImageView(mContext);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mContext).load(Constants.Base_URl_IMAGE + data.get(position).getIcon_url()).into(view);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

            actViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                    Toast.makeText(mContext, "首页act的position:" + i, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

        }
    }

    /**
     * 设置秒杀适配器
     */
    class SeckillViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView tvMore;
        private RecyclerView recyclerView;

        public SeckillViewHolder(View itemView, Context mContext) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            this.mContext = mContext;

        }



        public void setData(final ResultBean.SeckillInfoBean data) {
            //设置时间
            if (isFirst) {
                dt = Integer.parseInt(data.getEnd_time()) - Integer.parseInt(data.getStart_time());
                isFirst = false;
            }
            //倒计时开始
            handler.sendEmptyMessageDelayed(0, 1000);

            //设置RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            SeckillRecyclerViewAdapter adapter = new SeckillRecyclerViewAdapter(mContext, data);
            recyclerView.setAdapter(adapter);

            //点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isFirst = true;
    private TextView tvTime;
    private long dt;
    //用handler刷新秒杀的倒计时
    @SuppressLint({"HandlerLeak","SimpleDateFormat"})
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                dt=dt-1000;
                 SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tvTime.setText(sd.format(new Date(dt)));
                System.out.println();
                handler.removeMessages(0,1000);
                handler.sendEmptyMessageDelayed(0,1000);
                if (dt<=0){
                    handler.removeMessages(0);
                }
            }
        }
    };
}

