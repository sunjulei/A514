package com.appname.weare.app514.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appname.weare.app514.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //两秒延迟进入主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //启动主面
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                //关闭当前页面
            }
        }, 2500);

    }
}
