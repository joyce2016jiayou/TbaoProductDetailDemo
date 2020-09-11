package com.example.tbaoproductdetaildemo;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClassPicturesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ClassPicturesAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);


    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        ImageView image_view = baseViewHolder.itemView.findViewById(R.id.image_view);
        Picasso.get().load(s).into(image_view);

    }
}
