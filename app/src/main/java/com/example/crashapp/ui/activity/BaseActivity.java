package com.example.crashapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.crashapp.MyApplication;
import com.example.crashapp.R;
import com.example.crashapp.service.Presenter.BasePresenter;
import com.example.crashapp.service.Presenter.MvpPresenter;
import com.example.crashapp.service.View.BaseView;
import com.example.crashapp.service.View.MvpView;
import com.example.crashapp.ui.fragment.BaseFragment;
import com.example.crashapp.util.ActivityLifeProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

//RxAppCompatActivity
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private ProgressDialog mProgressDialog;
    FragmentManager fragmentManager;
    protected ActivityLifeProvider lifeProvider = new ActivityLifeProvider();

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    /**
     * 初始化布局
     */
    public abstract int setContentView();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 获取Presenter实例，子类实现
     */
    public abstract BasePresenter getPresenter();

    /**
     * 初始化Presenter的实例，子类实现
     */
    public abstract void initPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        hideNavigationBar(1);
        setContentView(setContentView());
        initPresenter();
        if (getPresenter() != null) {
            getPresenter().attachView(this, getContext());
        }
        lifeProvider.onNext(ActivityEvent.CREATE);
        initView();
        initData();
    }


    @Override
    public  <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event) {
        return lifeProvider.bindUntilEvent(event);
    }

    private void initData() {
        // MyApplication.NavigationBarStatusBar(this, true);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifeProvider.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifeProvider.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifeProvider.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifeProvider.onNext(ActivityEvent.STOP);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifeProvider.onNext(ActivityEvent.DESTROY);
        if (getPresenter() != null) {
            Log.e("tag", "onDestroy: +解除当前绑定");
            getPresenter().detachView();
//            MvpPresenter.onStop();
        }
    }


    /**
     * -------------other--------------------------
     */
//    public static <T> ObservableTransformer<T, T> transform(final BaseView view) {
//        return observable -> observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(view.bindToLifecycle());
//    }
    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr() {
        showToast("error");
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }








    /*----------------FrgmentManager--------/

     */

    /**
     * 获取Fragment管理器
     */
    public FragmentManager getBaseFragmentManager() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        return fragmentManager;
    }

    /**
     * 获取Fragment事物管理
     */
    public FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }

    /**
     * 替换一个Fragment
     */
    public void replaceFragment(int res, BaseFragment fragment) {
        replaceFragment(res, fragment, false);
    }

    /**
     * 替换一个Fragment并设置是否加入回退栈
     */
    public void replaceFragment(int res, BaseFragment fragment, boolean isAddToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.replace(res, fragment);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

    }

    /**
     * 添加一个Fragment
     */
    public void addFragment(int res, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 移除一个Fragment
     */
    public void removeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 显示一个Fragment
     */
    public void showFragment(Fragment fragment) {
        if (fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * 隐藏一个Fragment
     */
    public void hideFragment(Fragment fragment) {
        if (!fragment.isHidden()) {
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
        }
    }

    /*-----------------over---------------*/
    //废弃（此方法需要威尔提供系统签名，获取root权限，）---隐藏StatusBar---（后期通过systemUI更改）
    private void hideNavigationBar() {

        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏Navigation Bar

                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;//防止Navigation Bar在覆盖view的情况下上弹

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_main, null);

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override

            public boolean onTouch(View v, MotionEvent event) {

                Log.e("rocky", "Hi....");

                return false;

            }

        });

        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        int width = windowManager.getDefaultDisplay().getWidth();

        int height = windowManager.getDefaultDisplay().getHeight();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(width, height,

                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,

                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, //让window占满整个手机屏幕，不留任何边界（border）

                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;

        params.y = 0;

        params.x = 0;

        windowManager.addView(view, params);

        view.setSystemUiVisibility(flags);

    }

}
