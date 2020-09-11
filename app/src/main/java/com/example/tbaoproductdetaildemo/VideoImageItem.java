package com.example.tbaoproductdetaildemo;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class VideoImageItem implements MultiItemEntity {
    private String url;
    //1 是视频  2  是商品
    private int flag;
    private String coverUrl;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public VideoImageItem(String url, int flag) {
        this.url = url;
        this.flag = flag;
    }

    public String getUrl() {
        return url;
    }

    public int getFlag() {
        return flag;
    }

    @Override
    public int getItemType() {
        return flag;
    }
}

