package com.example.tbaoproductdetaildemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdbhe.plib.base.MyFragmentPagerAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends MyBaseActivity {
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.swipe_layout)
    SuperSwipeRefreshLayout swipe_layout;
    @BindView(R.id.shiting_btn)
    RelativeLayout shiting_btn;
    @BindView(R.id.information_btn)
    RelativeLayout information_btn;
    @BindView(R.id.dead_price_tv)
    TextView dead_price_tv;
    @BindView(R.id.class_pictures_btn)
    RelativeLayout class_pictures_btn;
    @BindView(R.id.banner_recycler_view)
    RecyclerView banner_recycler_view;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinator_layout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private MyFragmentPagerAdapter mAdapter;
    private String[] PERMS = {Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE};
    private int PERMISSION_STORAGE_CODE = 10001;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search_detail;
    }

    @Override
    public void initView(Bundle var1) {

        setTitleView();

        init_banner();

        set_head_refresh();

        setViewPage();


        information_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicPopControl.bottom_pop(MainActivity.this, R.layout.class_information_pop, new PopViewCallBack() {
                    @Override
                    public void return_view(View view, BasePopupView popup) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        RecyclerView information_recyclerview = view.findViewById(R.id.information_recyclerview);
                        information_recyclerview.setLayoutManager(linearLayoutManager);
                        List<String> strings = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            strings.add("" + i);
                        }
                        InformationAdapter informationAdapter = new InformationAdapter(R.layout.information_adapter_item, strings);
                        information_recyclerview.setAdapter(informationAdapter);
                    }
                });
            }
        });

        //添加中划线
        dead_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

        class_pictures_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClassPicturesActivity.class);
                startActivity(intent);
            }
        });

        //解决swipelayout与Recyclerview的冲突
        banner_recycler_view.setNestedScrollingEnabled(false);
        banner_recycler_view.setFocusableInTouchMode(false);
        banner_recycler_view.requestFocus();

    }


    private void init_banner() {
        if (checkPermissions(PERMS)) {

            setBannerView();
        } else {
            //请求权限
            requestPermission(PERMS, PERMISSION_STORAGE_CODE);
        }


    }

    LinearLayoutManager layoutManager;
    int lastVisibleItem;
    int firstVisibleItem;
    GSYVideoHelper smallVideoHelper;
    GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    BannerAdapter multiAdapter;
    PagerSnapHelper snapHelper;
    private void setBannerView() {
        /**adapter设置*/
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        banner_recycler_view.setLayoutManager(layoutManager);
        List<VideoImageItem> videoImageItems = new ArrayList<>();
        VideoImageItem videoImageItem1 = new VideoImageItem("https://v-cdn.zjol.com.cn/2841.mp4", 1);
        VideoImageItem videoImageItem2 = new VideoImageItem("http://mgapi.yoyazu.com/Upload/images/20200727/15958375818296.jpg", 2);//图
        VideoImageItem videoImageItem3 = new VideoImageItem("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", 1);
        videoImageItems.add(videoImageItem1);
        videoImageItems.add(videoImageItem2);
        videoImageItems.add(videoImageItem3);
        multiAdapter = new BannerAdapter(videoImageItems);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(banner_recycler_view);
        banner_recycler_view.setAdapter(multiAdapter);
        /**adapter设置*/

        /**小窗口设置*/
        smallVideoHelper = new GSYVideoHelper(this, new NormalGSYVideoPlayer(MainActivity.this));
        gsySmallVideoHelperBuilder = new GSYVideoHelper.GSYVideoHelperBuilder();
        gsySmallVideoHelperBuilder
                .setHideActionBar(true)
                .setHideStatusBar(true)
                .setNeedLockFull(true)
                .setCacheWithPlay(true)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setLockLand(true).setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                Debuger.printfLog("Duration " + smallVideoHelper.getGsyVideoPlayer().getDuration() + " CurrentPosition " + smallVideoHelper.getGsyVideoPlayer().getCurrentPositionWhenPlaying());
            }

            @Override
            public void onQuitSmallWidget(String url, Object... objects) {
                super.onQuitSmallWidget(url, objects);
            }
        });

        smallVideoHelper.setGsyVideoOptionBuilder(gsySmallVideoHelperBuilder);
        multiAdapter.setVideoHelper(smallVideoHelper, gsySmallVideoHelperBuilder);
        /**小窗口设置*/


        /**
         * 监听滑动事件
         */
        banner_recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //停止滚动
                        autoPlay(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        //拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        //惯性滑动
                        GSYVideoManager.releaseAllVideos();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        /**
         * 点击图片或者视频
         */
        multiAdapter.setOnItemClickListener((adapter, view, position) -> {
            VideoImageItem o = (VideoImageItem) adapter.getData().get(position);
            if (o.getFlag() == 2) {
                //图片点击则放大
                ImageView imageView = (ImageView) adapter.getViewByPosition(position, R.id.image);
                //bannner 点击
                new XPopup.Builder(MainActivity.this)
                        .asImageViewer(imageView, o.getUrl(), new XPopupImageLoader() {
                            @Override
                            public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
                                Glide.with(MainActivity.this).load(uri).into(imageView);
                            }

                            @Override
                            public File getImageFile(@NonNull Context context, @NonNull Object uri) {
                                try {
                                    return Glide.with(context).downloadOnly().load(uri).submit().get();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }).show();
            }
        });


    }

    private void autoPlay(RecyclerView recyclerView) {
        View view = snapHelper.findSnapView(layoutManager);
        if (view != null) {
            if (view instanceof RelativeLayout) {
                GSYVideoManager.releaseAllVideos();
            } else {
                BaseViewHolder viewHolder = (BaseViewHolder) recyclerView.getChildViewHolder(view);
                if (viewHolder != null) {

                    /*JzvdStd myVideoPlayer = viewHolder.getView(R.id.player);
                    myVideoPlayer.startVideo();*/

                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (smallVideoHelper.backFromFull()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != smallVideoHelper.getGsyVideoPlayer()) {
            smallVideoHelper.releaseVideoPlayer();
        }
        GSYVideoManager.releaseAllVideos();
    }


    private void set_head_refresh() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return true;
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //根据偏移量判断下拉刷新和悬浮
                if (verticalOffset <= 0) {
                    if (verticalOffset > (-200) && verticalOffset < 0) {
                        swipe_layout.setEnabled(true);
                    } else if (verticalOffset < (-900)) {//视频view滑出屏幕
                        if (null != smallVideoHelper.getGsyVideoPlayer() && smallVideoHelper.getGsyVideoPlayer().isInPlayingState()) {
                            //设置悬浮
                            if (!smallVideoHelper.isSmall() && !smallVideoHelper.isFull()) {
                                Log.e("打印1", "打印1");
                                //小窗口
                                int size = CommonUtil.dip2px(MainActivity.this, 150);
                                //actionbar为true才不会掉下面去
                                smallVideoHelper.showSmallVideo(new Point(size, size), true, true);
                            }
                        }
                        swipe_layout.setEnabled(false);
                    } else if (verticalOffset > (-900)) {
                        if (null == smallVideoHelper) {
                            return;
                        }
                        if (null != smallVideoHelper.getGsyVideoPlayer() && smallVideoHelper.getGsyVideoPlayer().isInPlayingState()) {
                            if (smallVideoHelper.isSmall()) {
                                if (null != smallVideoHelper.getGsyVideoPlayer().getSmallWindowPlayer()) {
                                    Log.e("打印2", "打印2");

                                          /*  GSYVideoPlayer small_view= smallVideoHelper.getGsyVideoPlayer().getSmallWindowPlayer();
                                            small_view.hideSmallVideo();
                                            small_view.onVideoPause();*/
                                    smallVideoHelper.smallVideoToNormal();
                                    //smallVideoHelper.startPlay();
                                    //smallVideoHelper.smallVideoToNormal();
                                }
                            }
                        }


                        swipe_layout.setEnabled(false);

                    }
                } else {
                    swipe_layout.setEnabled(false);
                }
            }
        });
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe_layout.setRefreshing(false);
                    }
                }, 2000);

            }
        });


    }


    private void setViewPage() {
        List<String> strings = new ArrayList<>();
        strings.add("课程介绍");
        strings.add("课程大纲");
        strings.add("相关课程");

        mTitles = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            mTitles[i] = strings.get(i);
        }
        if (mFragments.size() > 0) {
            mFragments.clear();
        }
        mFragments.add(new ClassOutlineFragment());
        mFragments.add(new ClassOutlineFragment());
        mFragments.add(new AboutClassFragment());

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mAdapter.setItems(mFragments);
        viewPager.setAdapter(mAdapter);
        tab_layout.setViewPager(viewPager, mTitles);
    }


    private void setTitleView() {
        replaceMoreIcon(R.drawable.navbar_share_btn);
        showMore(new OnTitleBarListener() {
            @Override
            public void clickMore(View view) {

            }
        });
    }

    @Override
    public void permissionSucceed(int code) {
        super.permissionSucceed(code);
        //获取权限成功之后
        setBannerView();

    }

}