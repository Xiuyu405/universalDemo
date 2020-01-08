package com.example.crashapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crashapp.ui.activity.BaseActivity;
import com.example.crashapp.service.View.BaseView;

public abstract class BaseFragment extends Fragment implements BaseView {
    private BaseActivity mActivity;
    private View mLayoutView;

    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLayoutView != null) {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null) {
                parent.removeView(mLayoutView);
            }
        } else {
            mLayoutView = getCreateView(inflater, container);
            initView(mLayoutView);     //初始化布局
        }

        return mLayoutView;
    }

    /**
     * 获取Fragment布局文件的View
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化view
     */
    public abstract void initView(View mLayoutView);

    /**
     * 初始化数据
     */
    public void initData() {
    }

    /**
     * 获取当前Fragment状态
     *
     * @return true为正常 false为未加载或正在删除
     */
    private boolean getStatus() {
        return (isAdded() && !isRemoving());
    }

    /**
     * 获取Activity
     */
    public BaseActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (BaseActivity) getActivity();
        }
        return mActivity;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
