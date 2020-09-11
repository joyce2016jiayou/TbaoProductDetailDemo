package com.example.tbaoproductdetaildemo;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BannerAdapter extends BaseMultiItemQuickAdapter<VideoImageItem, BaseViewHolder> {
    public final static String TAG = "BannerAdapter";

    private GSYVideoHelper smallVideoHelper;

    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BannerAdapter(List<VideoImageItem> data) {
        super(data);
        addItemType(1, R.layout.banner_video);
        addItemType(2, R.layout.banner_image);


    }

    public void setVideoHelper(GSYVideoHelper smallVideoHelper, GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final VideoImageItem item) {
        helper.setText(R.id.item_number_tv, helper.getLayoutPosition() + 1 + "/" + getItemCount());
        switch (item.getItemType()) {
            case 1://设置播放器
                FrameLayout player = helper.getView(R.id.player);
                ImageView listItemBtn = helper.getView(R.id.list_item_btn);

                ImageView imageView = new ImageView(getContext());//增加封面
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(R.drawable.fengmian);

                smallVideoHelper.addVideoPlayer(helper.getAdapterPosition(), imageView, TAG, player, listItemBtn);

                listItemBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        smallVideoHelper.setPlayPositionAndTag(helper.getAdapterPosition(), TAG);

                        notifyDataSetChanged();
                        //listVideoUtil.setLoop(true);
                        //listVideoUtil.setCachePath(new File(FileUtils.getPath()));

                        gsySmallVideoHelperBuilder.setUrl(item.getUrl());
                        if (null != smallVideoHelper.getGsyVideoPlayer()) {
                            smallVideoHelper.startPlay();
                            //必须在startPlay之后设置才能生效
                            //listVideoUtil.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
                          /*  //防止错位设置
                            videoPlayer.setPlayTag(TAG);
                            videoPlayer.setPlayPosition(helper.getAdapterPosition());
                            //增加title
                            videoPlayer.getTitleTextView().setVisibility(View.GONE);
                            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
                            videoPlayer.setAutoFullWithSize(false);
                            //音频焦点冲突时是否释放
                            videoPlayer.setReleaseWhenLossAudio(false);
                            //全屏动画
                            videoPlayer.setShowFullAnimation(true);
                            //小屏时不触摸滑动
                            videoPlayer.setIsTouchWiget(false);
                            //设置返回键
                            videoPlayer.getBackButton().setVisibility(View.GONE);
                            //设置全屏按键功能
                            videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    videoPlayer.startWindowFullscreen(getContext(), false, true);
                                }
                            });*/
                        }

                    }
                });

                break;
            case 2://设置图片
                ImageView img = helper.getView(R.id.image);
                Log.e("数据库浪费",""+item.getUrl());
                Glide.with(helper.itemView).load(item.getUrl()).into(img);
                //Picasso.get().load(item.getUrl()).into(img);
                break;
            default:
                break;
        }
    }
}


