package com.example.tbaoproductdetaildemo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import com.cdbhe.plib.base.BaseActivity;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public abstract class MyBaseActivity extends BaseActivity {
    public SwipeBackLayout mSwipeBackLayout;
    public abstract int getLayoutResId();
    public Activity activity;
    public abstract void initView(Bundle var1);

    @Override
    public int getContentViewResId() {
        return getLayoutResId();
    }

    @Override
    public void init(Bundle savedInstanceState) {
        // ButterKnife注入
        ButterKnife.bind(this);
        //设置返回动画
        setBackEnable(false);
        //设置标题属性
        setIsFullScreen(false);
        setStatusBarColor(Color.parseColor("#ffffff"));
        setTitleBarBg(Color.parseColor("#ffffff"));
        setTitleTextColor(Color.parseColor("#000000"));
        setEscIcon(R.mipmap.return_img);
        setTitleTextSize(18);
        // 初始化视图
        initView(savedInstanceState);

        activity = this;

    }



    /**
     * @param isBack 是否允许滑动退出，默认为true
     */
    public void setBackEnable(boolean isBack) {
        // 可以调用该方法，设置是否允许滑动退出
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setSwipeBackEnable(isBack);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        mSwipeBackLayout.setEdgeSize(200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
