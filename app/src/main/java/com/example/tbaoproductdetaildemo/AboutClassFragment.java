package com.example.tbaoproductdetaildemo;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdbhe.plib.base.LazyFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutClassFragment extends LazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public AboutClassFragment() {
        // Required empty public constructor
    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_about_class;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);

        setDate();
    }


    private void setDate() {
        List<String> strings = new ArrayList<>();
        for(int i=0;i<10;i++){
            strings.add(""+i);
        }
        AboutClassAdapter aboutClassAdapter = new AboutClassAdapter(R.layout.about_class_item,strings);
        recyclerView.setAdapter(aboutClassAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        View nullView = View.inflate(getActivity(), R.layout.fragment_null, null);
        aboutClassAdapter.setEmptyView(nullView);
        aboutClassAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                Intent intent = new Intent(getActivity(), SearchDetailActivity.class);
//                startActivity(intent);
            }
        });
    }
}