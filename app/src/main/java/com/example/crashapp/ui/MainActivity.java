package com.example.crashapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crashapp.service.View.BaseView;
import com.example.crashapp.service.entity.UserInformation;
import com.example.crashapp.service.entity.listBean;
import com.example.crashapp.ui.activity.BaseActivity;
import com.example.crashapp.service.Presenter.BasePresenter;
import com.example.crashapp.R;
import com.example.crashapp.service.Presenter.MvpPresenter;
import com.example.crashapp.service.View.MvpView;
import com.example.crashapp.service.entity.BookBean;
import com.example.crashapp.service.entity.ReadBean;
import com.example.crashapp.ui.activity.LoginActivity;
import com.example.crashapp.util.DaoSessionUtils;
import com.example.crashapp.util.NewImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MvpView {
    @BindView(R.id.tv_1)
    TextView mt;
    @BindView(R.id.tv_2)
    TextView mt2;
    @BindView(R.id.iv_1)
    ImageView iv_1;
    @BindView(R.id.tv_3)
    TextView mt3;
    @BindView(R.id.tv_4)
    TextView mt4;

    String imurl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579090643083&di=e9e97c2f57c06f42c849f60fa1dfeeff&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20150818%2Fmp28084935_1439889383180_5.jpeg";
    ProgressDialog progressDialog;
    MvpPresenter presenter;
    UserInformation userInformation;

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        // 初始化进度条
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载数据");
//        NewImageLoader.loadCenterCrop(imurl,iv_1);
        NewImageLoader.loadCArcCrop(imurl, iv_1);
        userInformation = new UserInformation();
    }

    @OnClick(R.id.tv_1)
    public void trytest() {
        getData();
    }

    @OnClick(R.id.tv_2)
    public void t2() {
        getData2();
    }

    @OnClick(R.id.tv_3)
    public void t3() {

        List<UserInformation> baseBeans = (List<UserInformation>) DaoSessionUtils.queryAll(userInformation);
        for (int i = 0; i < baseBeans.size(); i++) {
            Log.e("Ddddd", "t3: " + baseBeans.get(i).getUserId() + "=========" + baseBeans.get(i).getUserName());
            Log.e("Ddddd", "t4: " + baseBeans.get(i).getUserIsExist());
        }
    }

    @OnClick(R.id.tv_4)
    public void t4() {
        userInformation.setUserId(9);
        userInformation.setUserName("asdadas");
        userInformation.setUserIsExist(true);
        DaoSessionUtils.insertOrReplaceDbBean(userInformation);
        List<UserInformation> baseBeans = (List<UserInformation>) DaoSessionUtils.queryAll(userInformation);
        for (int i = 0; i < baseBeans.size(); i++) {
            Log.e("Ddddd", "t3: " + baseBeans.get(i).getUserId() + "=========" + baseBeans.get(i).getUserName());
            Log.e("Ddddd", "t4: " + baseBeans.get(i).getUserIsExist());
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void initPresenter() {
        //初始化Presenter
        presenter = new MvpPresenter();
    }

    // button 点击事件调用方法
    public void getData() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    // button 点击事件调用方法
    public void getData2() {
        presenter.getSearchList(0);
    }


    @Override
    public void onSuccess(Object bean) {
        if (bean instanceof BookBean) {
            BookBean book = (BookBean) bean;
            mt.setText(book.getMsg());
        } else if (bean instanceof ReadBean) {
            ReadBean rbook = (ReadBean) bean;
            mt2.setText(rbook.getMsg());
        } else if (bean instanceof listBean) {
            mt2.setText(((listBean) bean).getData().getCurPage() + "");
        }
    }


    @Override
    public void onError(String result) {
        Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
    }
}
