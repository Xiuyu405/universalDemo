package com.example.crashapp.ui.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class DBaseFragment extends BaseFragment {
    public abstract int setView();

    public abstract void init();

    FragmentManager fragmentManager;
    private Unbinder mUnbinder;

    @Override
    public int getLayoutRes() {
        return setView();
    }

    @Override
    public void initView(View mLayoutView) {
        mUnbinder = ButterKnife.bind(this, mLayoutView);
        init();
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

    /**
     * 获取Fragment事物管理
     */
    public FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }

    /**
     * 获取Fragment管理器
     */
    public FragmentManager getBaseFragmentManager() {
        if (fragmentManager == null) {
            fragmentManager = getActivity().getSupportFragmentManager();
        }
        return fragmentManager;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
