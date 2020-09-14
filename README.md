**这是仿照淘宝的商品浏览详情页，主要实现的效果有：**
**如果效果图演示不了，请移步https://blog.csdn.net/weixin_39592302/article/details/108538327**

 - [x] 视频和图片横向Banner浏览效果
 - [x] 视频播放全屏和小窗悬浮效果
 - [x] tab吸顶效果
  
  这是效果实例图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200914180818531.gif#pic_center)

  
**具体完整代码，可以下载链接: [Demo工程](https://download.csdn.net/download/weixin_39592302/12838206).自行运行查看**
布局文件：使用的是

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tl="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#fff2f2f2"
    android:orientation="vertical"
    >
    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <com.example.tbaoproductdetaildemo.SuperSwipeRefreshLayout
        android:id="@+id/swipe_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:addStatesFromChildren="true"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">


            <androidx.coordinatorlayout.widget.CoordinatorLayout
                android:id="@+id/coordinator_layout"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="#fff2f2f2"
                android:orientation="vertical">


                <com.google.android.material.appbar.AppBarLayout
                    android:id="@+id/appBarLayout"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    app:layout_behavior=".CustomBehavior">

                    <com.google.android.material.appbar.CollapsingToolbarLayout
                        android:id="@+id/collapsing_toolbar"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        app:contentScrim="@android:color/transparent"
                        app:expandedTitleGravity="top"
                        app:layout_scrollFlags="enterAlwaysCollapsed|scroll">
                        <include layout="@layout/my_scoll_view_layout"/>
                    </com.google.android.material.appbar.CollapsingToolbarLayout>>

                    <com.flyco.tablayout.SlidingTabLayout
                        android:id="@+id/tab_layout"
                        android:layout_width="match_parent"
                        android:layout_height="50dp"
                        android:background="#ffffff"
                        app:layout_behavior="@string/appbar_scrolling_view_behavior"
                        app:tabMode="scrollable"
                        tl:tl_indicator_color="#3DC5E4"
                        tl:tl_indicator_corner_radius="1dp"
                        tl:tl_indicator_height="2dp"
                        tl:tl_indicator_margin_top="0dp"
                        tl:tl_indicator_width="64dp"
                        tl:tl_tab_padding="0dp"
                        tl:tl_tab_space_equal="true"
                        tl:tl_textSelectColor="#3DC5E4"
                        tl:tl_textUnselectColor="#333333"
                        tl:tl_textsize="16sp" />

                </com.google.android.material.appbar.AppBarLayout>
                <androidx.viewpager.widget.ViewPager
                    android:id="@+id/viewPager"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_marginBottom="49dp"
                    app:layout_behavior="@string/appbar_scrolling_view_behavior"
                    />
            </androidx.coordinatorlayout.widget.CoordinatorLayout>

    </com.example.tbaoproductdetaildemo.SuperSwipeRefreshLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="49dp"
        android:layout_alignParentBottom="true"
        android:orientation="horizontal">

        <RelativeLayout
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:background="#ffffff"
            android:orientation="horizontal">
            <View
                android:layout_width="match_parent"
                android:layout_height="0.5dp"
                android:layout_alignParentTop="true"
                android:background="#EEEEEE" />
            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:layout_centerVertical="true"
                android:layout_centerHorizontal="true"
                android:orientation="horizontal">

                <ImageView
                    android:layout_width="20dp"
                    android:layout_height="20dp"
                    android:src="@drawable/erji" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="电话客服"
                    android:textColor="#ff999999"
                    android:textSize="14sp" />
            </LinearLayout>
        </RelativeLayout>

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:background="#ff3dc5e4"
            android:gravity="center"
            android:orientation="horizontal">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="加入订单"
                android:textColor="#ffffffff"
                android:textSize="18sp" />
        </LinearLayout>

    </LinearLayout>

    </RelativeLayout>

</LinearLayout>
```
其中~~my_scoll_view_layout~~为自己定义的布局部分！

1.视频播放器我采用的是：[GSYVideoPlayer](https://github.com/CarGuo/GSYVideoPlayer).
2.图片放大效果采用的是[XPopup](https://github.com/li-xiaojun/XPopup).
3.其中商品视频和图片浏览的banner效果是用原生的RecyclerView实现








