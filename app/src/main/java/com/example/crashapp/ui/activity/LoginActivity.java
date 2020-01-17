package com.example.crashapp.ui.activity;

import android.widget.TextView;

import com.example.crashapp.R;
import com.example.crashapp.service.Presenter.BasePresenter;
import com.example.crashapp.service.Presenter.MvpPresenter;
import com.example.crashapp.service.View.BaseView;
import com.example.crashapp.service.View.MvpView;
import com.example.crashapp.service.entity.listBean;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements MvpView {
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_ms)
    TextView tv_ms;

    MvpPresenter presenter;
    @Override
    public int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
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

    @Override
    public void onSuccess(Object bean) {
        if (bean instanceof listBean){
            tv_ms.setText(((listBean) bean).getData().getCurPage()+"");
        }

    }

    @Override
    public void onError(String result) {

    }
    @OnClick(R.id.tv_back)
    public void back() {
        finish();
    }
    @OnClick(R.id.tv_ms)
    public void message(){
        presenter.getSearchList(0);
    }
}
