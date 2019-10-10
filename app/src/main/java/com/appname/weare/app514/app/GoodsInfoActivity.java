package com.appname.weare.app514.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appname.weare.app514.R;
import com.appname.weare.app514.app.bean.GoodsBean;
import com.appname.weare.app514.shoppingcart.utils.CartStorage;
import com.appname.weare.app514.utils.Constants;
import com.bumptech.glide.Glide;


public class GoodsInfoActivity extends Activity implements View.OnClickListener {
    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tvMoreShare;
    private TextView tvMoreSearch;
    private TextView tvMoreHome;
    private LinearLayout ll_root;
    private Button btn_more;

    private GoodsBean goods_bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);

        findViews();

        //取出intent
        Intent intent = getIntent();
        goods_bean = (GoodsBean) intent.getSerializableExtra("goods_bean");
        if (goods_bean != null) {
            setDataFormView(goods_bean);
        }
    }

    private void setWebView(String product_id) {
        if (product_id != null) {

            WebSettings settings = wbGoodInfoMore.getSettings();
            settings.setUseWideViewPort(true);//支持双击变大变小
            settings.setJavaScriptEnabled(true);//启用支持javascript
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存


            //http://192.168.51.104:8080/atguigu/json/GOODSINFO_URL.json2691 //
//            wbGoodInfoMore.loadUrl(Constants.GOODSINFO_URL + product_id);
            wbGoodInfoMore.loadUrl("https://www.jd.com");
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            wbGoodInfoMore.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });
        }
    }

    public void setDataFormView(GoodsBean goodsBean) {
        String name = goodsBean.getName();
        String cover_price = goodsBean.getCover_price();
        String figure = goodsBean.getFigure();
        String product_id = goodsBean.getProduct_id();
        Glide.with(this).load(Constants.BASE_URl_IMAGE + figure).into(ivGoodInfoImage);
        if (name != null) {
            tvGoodInfoName.setText(name);
        }
        if (cover_price != null) {
            tvGoodInfoPrice.setText("￥" + cover_price);
        }
        setWebView(product_id);
    }


    private void findViews() {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);//左上角返回
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);//右上角更多

        ll_root = (LinearLayout) findViewById(R.id.ll_root);//右上角下拉列表
        tvMoreShare = (TextView) findViewById(R.id.tv_more_share);//右上角下拉分享
        tvMoreSearch = (TextView) findViewById(R.id.tv_more_search);//右上角下拉搜索
        tvMoreHome = (TextView) findViewById(R.id.tv_more_home); //右上角下拉首页

        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);//商品图片
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);//商品名称
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);//商品描述
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);//商品价格
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);//商品的店名
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);//商品选择款式

        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);//web页面

        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);//联系客服
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);//收藏
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);//购物车
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);//加入购物车

        btn_more = (Button) findViewById(R.id.btn_more);

        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);

        tvMoreShare.setOnClickListener(this);
        tvMoreSearch.setOnClickListener(this);
        tvMoreHome.setOnClickListener(this);
        btn_more.setOnClickListener(this);

        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            finish();
        } else if (v == ibGoodInfoMore) {
            if (ll_root.getVisibility() == View.VISIBLE) {
                ll_root.setVisibility(View.GONE);
            } else {
                ll_root.setVisibility(View.VISIBLE);
            }
        } else if (v == btn_more) {
            ll_root.setVisibility(View.GONE);
        } else if (v == tvMoreShare) {
            Toast.makeText(GoodsInfoActivity.this, "分享", Toast.LENGTH_SHORT).show();
//            showShare();
        } else if (v == tvMoreSearch) {
            Toast.makeText(GoodsInfoActivity.this, "搜索", Toast.LENGTH_SHORT).show();
        } else if (v == tvMoreHome) {
            Constants.isBackHome = true;
            finish();
        } else if (v == tvGoodInfoCallcenter) {
            Toast.makeText(GoodsInfoActivity.this, "客服", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, CallCenterActivity.class);
//            startActivity(intent);
        } else if (v == tvGoodInfoCollection) {
            Toast.makeText(GoodsInfoActivity.this, "收藏", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCart) {

            Toast.makeText(GoodsInfoActivity.this, "购物车",
                    Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, ShoppingCartActivity.class);
//            startActivity(intent);
        } else if (v == btnGoodInfoAddcart) {
            CartStorage.getInstance().addDate(goods_bean);
            Toast.makeText(GoodsInfoActivity.this, "添加到购物车成功",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
