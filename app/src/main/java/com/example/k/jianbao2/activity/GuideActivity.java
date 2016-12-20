package com.example.k.jianbao2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.k.jianbao2.R;
import com.example.k.jianbao2.utils.ShareUtils;

/*
* 引导页面
* */
public class GuideActivity extends AppCompatActivity {
    private ViewPager vp;
    private int imgs[] = {R.mipmap.phono2, R.mipmap.phono3, R.mipmap.phono1};
    private ImageView dots[] = new ImageView[imgs.length];
    private View iView[] = new View[imgs.length];
    private String tag = "tag";
    private LinearLayout layout;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //进入登录页面，存放tag值
        ShareUtils.setData(GuideActivity.this, "tag", tag);
        vp = (ViewPager) findViewById(R.id.vp);
        layout = (LinearLayout) findViewById(R.id.layout);

        initView();
        initDots();
        initListener();

        vp.setAdapter(new MyAdapter());

    }
    private void initView() {
        for (int i = 0; i < imgs.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.guide_item,null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            btn = (Button) view.findViewById(R.id.bt_tiaozhuan);
            iv.setImageResource(imgs[i]);

            iView[i] = view;
        }
    }

    private void initDots() {

        for (int n = 0; n < dots.length; n++) {
            ImageView dotsView = new ImageView(this);
            dotsView.setLayoutParams(new ViewGroup.LayoutParams(40, 40));
            dotsView.setImageResource(R.mipmap.community_ad_banner_point_nor);
            //把控件给数组
            dots[n] = dotsView;
            //设置第一张图片对应的圆点为白点
            dots[0].setImageResource(R.mipmap.community_ad_banner_point_sel);
            layout.addView(dotsView);
        }

    }

    private void initListener() {
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int j = 0; j < dots.length; j++) {
                    if (j == position%dots.length) {
                        dots[j].setImageResource(R.mipmap.community_ad_banner_point_sel);
                    } else {
                        dots[j].setImageResource(R.mipmap.community_ad_banner_point_nor);
                    }
                }
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GuideActivity.this,LoginActivity.class));
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return iView.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(iView[position]);
            return iView[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(iView[position]);
        }
    }
}

