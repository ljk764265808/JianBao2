package com.example.k.jianbao2.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.k.jianbao2.R;
import com.example.k.jianbao2.app.MInterface;
import com.example.k.jianbao2.been.Login;
import com.example.k.jianbao2.utils.OkHttpUtils;
import com.example.k.jianbao2.utils.ShareUtils;
import com.example.k.jianbao2.utils.ShowUtils;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.k.jianbao2.utils.ShareUtils.getData;


/**
 * Created by yst on 2016/11/24.
 */
/*
* 登陆页面
* */
public class LoginActivity extends Activity {
    @InjectView(R.id.ed_id)
    EditText edId;
    @InjectView(R.id.ed_password)
    EditText edPassword;
    @InjectView(R.id.box)
    CheckBox box;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.tv_noid)
    TextView tvNoid;

    private String username;
    private String password;
    private String status;
    private HashMap<String, String> map;
    private Login login;
    private ProgressDialog progressDialog;
    private String regPhone;
    private String regPassword;
    private Boolean judgeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initView();

        initLister();
    }

    private void initLister() {
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                initView();
                if (isChecked == true) {
                    ShareUtils.setData(LoginActivity.this, "judgeText", true);
                    ShareUtils.setData(LoginActivity.this, "username", username);
                    ShareUtils.setData(LoginActivity.this, "password", password);
                } else {
                    ShareUtils.setData(LoginActivity.this, "judgeText", false);
                    ShareUtils.setData(LoginActivity.this, "username", "");
                    ShareUtils.setData(LoginActivity.this, "password", "                   ");

                }
            }
        });
    }

    private void isKeepPassword() {
        //从配置文件中取用户名密码的键值对
        //若第一运行，则取出的键值对为所设置的默认值
        // 选中状态
        judgeText = (Boolean) getData(LoginActivity.this, "judgeText", false);
        String strUserName = (String) getData(LoginActivity.this, "username", "0");// 用户名
        String strPassword = (String) getData(LoginActivity.this, "password", "0");// 密码*/

        if (judgeText == true) {
            box.setChecked(true);
            edId.setText(strUserName);
            edPassword.setText(strPassword);
        } else {
            box.setChecked(false);
            edId.setText("");
            edPassword.setText("");
        }
    }

    private void postData() {

        String path = MInterface.zhuji + MInterface.denglu;
        map = new HashMap<>();
        //把用户输入的文本携带到工具类进行联网操作，通过联网中判断是否为正确的token值
        map.put("username", username);
        map.put("password", password);
        OkHttpUtils.post(LoginActivity.this, path, map, Login.class);
        OkHttpUtils.setGetEntityCallBack(new OkHttpUtils.GetEntityCallBack() {
            @Override
            public void getEntity(Object obj) {
                login = (Login) obj;
                if (login != null) {
                    status = login.getStatus();
                     /*通过用户输入的用户名密码获取token值* */

                    String token = login.getToken();
                    //存放token值
                    if (!TextUtils.isEmpty(token)) {
                        ShareUtils.setData(LoginActivity.this, "token", token);
                    }
                    juegeStatus();
                }
            }
        });
    }

    private void juegeStatus() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        //再次判断请求回来的code码是否为200，正确的话跳转，并清空这个集合，这样的话避免重复以前的数据
        if (status.equals("200")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isKeepPassword();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    ShowUtils.show(LoginActivity.this, "欢迎使用");
                }
            });
        } else if (status.equals("303")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowUtils.show(LoginActivity.this, "用户不存在，请重新输入");
                }
            });
        } else if (status.equals("304")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowUtils.show(LoginActivity.this, "您输入的密码有误，请重新输入密码");
                }
            });
        } else if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ShowUtils.show(LoginActivity.this, "账号或密码不能为空");
                }
            });
        }
    }


    @OnClick({R.id.btn_login, R.id.tv_noid})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                initView();
                //显示ProgressDialog
                progressDialog = ProgressDialog.show(LoginActivity.this, "正在加载...", "请稍等...", true, false);
                postData();
                break;
            case R.id.tv_noid:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }

    private void initView() {
        username = edId.getText().toString().trim();
        password = edPassword.getText().toString().trim();
        //得到注册成功的用户名和密码
        regPhone = getIntent().getStringExtra("phone");
        regPassword = getIntent().getStringExtra("password");

        if (!TextUtils.isEmpty(regPhone) && !TextUtils.isEmpty(regPassword)){

            box.setChecked(true);
            edId.setText(regPhone);
            edPassword.setText(regPassword);
        }
    }
}