package com.example.tbaoproductdetaildemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;

import java.lang.reflect.Field;

/**
 * Created by Forever
 * on 2020/5/27 0027.
 */
public class CustomBehavior extends AppBarLayout.Behavior {
    private OverScroller mScroller;

    /**解决抖动问题*/
    private static final int TYPE_FLING = 1;
    private boolean isFlinging;
    private boolean shouldBlockNestedScroll;
    /**解决抖动问题*/


    public CustomBehavior() {

    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        getParentScroller(context);
    }
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
      //  LogUtil.d(TAG, "onInterceptTouchEvent:" + child.getTotalScrollRange());
        shouldBlockNestedScroll = isFlinging;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //手指触摸屏幕的时候停止fling事件
                stopAppbarLayoutFling(child);
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void stopAppbarLayoutFling(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Runnable flingRunnable;
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
                flingRunnable = (Runnable) flingRunnableField.get(this);
                if (flingRunnable != null) {
                    //LogUtil.d(TAG, "存在flingRunnable");
                    appBarLayout.removeCallbacks(flingRunnable);
                    flingRunnableField.set(this, null);
                }
            }

            Field scrollerField = getScrollerField();
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
                OverScroller overScroller = (OverScroller) scrollerField.get(this);
                if (overScroller != null && !overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child,
                                       View directTargetChild, View target,
                                       int nestedScrollAxes, int type) {
        //LogUtil.d(TAG, "onStartNestedScroll");
        stopAppbarLayoutFling(child);
        return super.onStartNestedScroll(parent, child, directTargetChild, target,
                nestedScrollAxes, type);
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     */
    private Field getScrollerField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     */
    private Field getFlingRunnableField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }



    /**
     * 反射获得滑动属性。
     *
     * @param context
     */
    private void getParentScroller(Context context) {
        if (mScroller != null) {
            return;
        }
        mScroller = new OverScroller(context);
        try {
            Class<?> reflex_class = getClass().getSuperclass().getSuperclass();//父类AppBarLayout.Behavior  父类的父类   HeaderBehavior
            Field fieldScroller = reflex_class.getDeclaredField("mScroller");
            fieldScroller.setAccessible(true);
            fieldScroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }



    //fling上滑appbar然后迅速fling下滑recycler时, HeaderBehavior的mScroller并未停止, 会导致上下来回晃动
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {

        if (mScroller != null) { //当recyclerView 做好滑动准备的时候 直接干掉Appbar的滑动
            Log.e("滑动时间",""+mScroller.getCurrY());

            if (mScroller.computeScrollOffset()) {
                mScroller.abortAnimation();
            }
        }
        if (type == ViewCompat.TYPE_NON_TOUCH && getTopAndBottomOffset() == 0) { //recyclerview 鸡儿的 惯性比较大 会顶在头部一会儿  到头直接干掉它的滑动
            ViewCompat.stopNestedScroll(target, type);
        }
        /**解决抖动问题*/
//        LogUtil.d(TAG, "onNestedPreScroll:" + child.getTotalScrollRange()
//                + " ,dx:" + dx + " ,dy:" + dy + " ,type:" + type);
        //type返回1时，表示当前target处于非touch的滑动，
        //该bug的引起是因为appbar在滑动时，CoordinatorLayout内的实现NestedScrollingChild2接口的滑动
        //子类还未结束其自身的fling
        //所以这里监听子类的非touch时的滑动，然后block掉滑动事件传递给AppBarLayout
        if (type == TYPE_FLING) {
            isFlinging = true;
        }
        if (!shouldBlockNestedScroll) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
        /**解决抖动问题*/


    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, int[] consumed) {
        //        LogUtil.d(TAG, "onNestedScroll: target:" + target.getClass() + " ,"
//                + child.getTotalScrollRange() + " ,dxConsumed:"
//                + dxConsumed + " ,dyConsumed:" + dyConsumed + " " + ",type:" + type);

        if (!shouldBlockNestedScroll) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);

        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl,
                                   View target, int type) {
       // LogUtil.d(TAG, "onStopNestedScroll");
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        isFlinging = false;
        shouldBlockNestedScroll = false;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent e) {
        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
        }

        return super.onTouchEvent(parent, child, e);
    }
}

