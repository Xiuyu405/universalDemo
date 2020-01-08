package com.example.crashapp.ui;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crashapp.MyApplication;
import com.example.crashapp.gen.UserInformationDao;
import com.example.crashapp.service.entity.BaseBean;
import com.example.crashapp.service.entity.UserInformation;
import com.example.crashapp.ui.activity.BaseActivity;
import com.example.crashapp.service.Presenter.BasePresenter;
import com.example.crashapp.R;
import com.example.crashapp.service.Presenter.MvpPresenter;
import com.example.crashapp.service.View.MvpView;
import com.example.crashapp.service.entity.BookBean;
import com.example.crashapp.service.entity.ReadBean;
import com.example.crashapp.util.DaoSessionUtils;
import com.example.crashapp.util.NewImageLoader;

import org.greenrobot.greendao.AbstractDao;

import java.util.ArrayList;
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

    String imurl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578285746496&di=d5b5abf8118cfa655933dcfb19013c11&imgtype=0&src=http%3A%2F%2Fk.zol-img.com.cn%2Fwallpaper%2F7056%2F7055706_0540.jpg";
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
    public void t4(){
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
        presenter.getSearchBooks("金瓶梅", null, 0, 1);
    }

    // button 点击事件调用方法
    public void getData2() {
        presenter.getReadBooks("金瓶梅", null, 0, 1);
    }

    @Override
    public void onSuccess(Object mBook) {
        if (mBook instanceof BookBean) {
            BookBean book = (BookBean) mBook;
            mt.setText(book.getMsg());
        } else if (mBook instanceof ReadBean) {
            ReadBean rbook = (ReadBean) mBook;
            mt2.setText(rbook.getMsg());
        }
    }

    @Override
    public void onError(String result) {
        Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
    }
}
