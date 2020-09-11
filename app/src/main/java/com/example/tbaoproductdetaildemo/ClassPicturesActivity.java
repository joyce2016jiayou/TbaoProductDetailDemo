package com.example.tbaoproductdetaildemo;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ClassPicturesActivity extends MyBaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_class_pictures;
    }

    @Override
    public void initView(Bundle var1) {
        setTitle("课程图库");

        setView();


    }

    private void setView() {
        List<String> strings = new ArrayList<>();
        strings.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2723795492,1562596155&fm=15&gp=0.jpg");
        strings.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2955714677,144559322&fm=26&gp=0.jpg");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ClassPicturesAdapter classPicturesAdapter = new ClassPicturesAdapter(R.layout.image_item_layout,strings);
        recyclerView.setAdapter(classPicturesAdapter);
        View nullView = View.inflate(this, R.layout.fragment_null, null);
        classPicturesAdapter.setEmptyView(nullView);
    }


}