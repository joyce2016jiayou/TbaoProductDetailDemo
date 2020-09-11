package com.cdbhe.plib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 应用程序的基类Fragment，实现懒加载
 * Created by Jack on 2016/5/17 08:31.
 * Email: zhuochangjing@foxmail.com
 */
public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;

    /**
     * 在这里实现Fragment数据的懒加载
     *
     * @param isVisibleToUser Fragment UI对用户是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lazyLoad();
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {

    }

    protected abstract void lazyLoad();

    protected abstract int getContentView();

    protected abstract void initView(View view);
}