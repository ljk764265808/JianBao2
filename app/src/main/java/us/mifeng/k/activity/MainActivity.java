package us.mifeng.k.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import us.mifeng.k.R;
import us.mifeng.k.fragment.Fragment_Goods;
import us.mifeng.k.fragment.Fragment_Mine;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private LinearLayout mFrag_grup,mLine_home,mLine_fabu,mLine_mine;
    private ImageView mImg_home,mImg_fabu,mImg_mine;
    private TextView mTv_home,mTv_fabu,mTv_mine;
    private Fragment_Goods mFrag_home;
    private Fragment_Mine mFrag_mine;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=getSupportFragmentManager();
        initView();
        //提交
    }
    private void initView() {
        mFrag_grup= (LinearLayout) findViewById(R.id.mFrag_group);
        mLine_home= (LinearLayout) findViewById(R.id.mLine_home);
        mLine_fabu= (LinearLayout) findViewById(R.id.mLine_fabu);
        mLine_mine= (LinearLayout) findViewById(R.id.mLine_mine);
        mImg_home= (ImageView) findViewById(R.id.mImg_home);
        mImg_fabu= (ImageView) findViewById(R.id.mImg_fabu);
        mImg_mine= (ImageView) findViewById(R.id.mImg_mine);
        mTv_home= (TextView) findViewById(R.id.mTv_home);
        mTv_fabu= (TextView) findViewById(R.id.mTv_fabu);
        mTv_mine= (TextView) findViewById(R.id.mTv_mine);

        mLine_home.setOnClickListener(this);
        mLine_fabu.setOnClickListener(this);
        mLine_mine.setOnClickListener(this);

        FragmentTransaction ft=manager.beginTransaction();
        mFrag_home=new Fragment_Goods();
        ft.add(R.id.mFrag_group, mFrag_home);
        ft.commit();
    }
    @Override
    public void onClick(View v) {
        clearBottom();
        hintFragment();
        FragmentTransaction ft=manager.beginTransaction();
        switch(v.getId()){
            case R.id.mLine_home:
                mImg_home.setImageResource(R.mipmap.shangpin1);
                mTv_home.setTextColor(Color.RED);
                ft.show(mFrag_home);
                break;
            case R.id.mLine_fabu:
                mImg_fabu.setImageResource(R.mipmap.fabu2);
                mTv_fabu.setTextColor(Color.RED);
                startActivity(new Intent(this,FaBuActivity.class));
                break;
            case R.id.mLine_mine:
                mImg_mine.setImageResource(R.mipmap.gerenzhongxin1);
                mTv_mine.setTextColor(Color.RED);
                if(mFrag_mine==null){
                    mFrag_mine=new Fragment_Mine();
                    ft.add(R.id.mFrag_group,mFrag_mine);
                }else{
                    ft.show(mFrag_mine);
                }
                break;
        }
        ft.commit();
    }
    //将底部颜色清空
    private void clearBottom(){
        mImg_home.setImageResource(R.mipmap.shangpin);
        mImg_mine.setImageResource(R.mipmap.gerenzhongxin);

        mTv_home.setTextColor(Color.BLACK);
        mTv_mine.setTextColor(Color.BLACK);
    }
    //隐藏所有的fragment的方法
    private void hintFragment(){
        FragmentTransaction ft=manager.beginTransaction();
        if(mFrag_home!=null){
            ft.hide(mFrag_home);
        }
        if(mFrag_mine!=null){
            ft.hide(mFrag_mine);
        }
        ft.commit();
    }
}
