package com.example.k.jianbao2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.k.jianbao2.R;
import com.example.k.jianbao2.utils.ShareUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by admin on 2016/12/12.
 */
/*
* 欢迎页面
* */
public class WelcomeActivity extends Activity {
    @InjectView(R.id.tv_skip)
    TextView tvSkip;
    private int num = 4;
    private Timer timer = new Timer();
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.inject(this);
        timer.schedule(task,1000, 1000);
    }

    //设置线程
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            if (flag){
                // 更新UI控件
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        num--;
                        //设置文本内容
                        tvSkip.setText("跳过 " + num + " 秒");
                        //判断num小于0，停止计时
                        if (num <= 0) {
                            timer.cancel();
                            initIntent();
                        }
                    }
                });
            }
        }
    };

    private void initIntent() {
        String tag = (String) ShareUtils.getData(WelcomeActivity.this, "tag", "");
        if (tag.equals("")) {
            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }
    }

    @OnClick(R.id.tv_skip)
    public void onClick() {
        flag = false;
        initIntent();
    }
}
