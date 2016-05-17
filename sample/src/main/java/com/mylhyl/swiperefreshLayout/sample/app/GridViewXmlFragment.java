package com.mylhyl.swiperefreshLayout.sample.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mylhyl.rslayout.OnListLoadListener;
import com.mylhyl.rslayout.SwipeRefreshGridView;
import com.mylhyl.rslayout.SwipeRefreshListView;
import com.mylhyl.swiperefreshLayout.sample.R;

import java.util.ArrayList;
import java.util.List;


public class GridViewXmlFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnListLoadListener {
    private SwipeRefreshGridView swipeRefreshGridView;
    private ArrayAdapter<String> adapter;
    private List<String> objects = new ArrayList<>();
    private int index;
    private int footerIndex = 25;

    public GridViewXmlFragment() {
    }

    public static GridViewXmlFragment newInstance() {
        GridViewXmlFragment fragment = new GridViewXmlFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_view_xml, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshGridView = (SwipeRefreshGridView) view.findViewById(R.id.swipeRefresh);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshGridView.setOnListLoadListener(this);
        swipeRefreshGridView.setOnRefreshListener(this);

        for (int i = 0; i < footerIndex; i++) {
            objects.add("Grid数据 = " + i);
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, objects);
        swipeRefreshGridView.setAdapter(adapter);
    }


    @Override
    public void onRefresh() {
        swipeRefreshGridView.postDelayed(new Runnable() {
            @Override
            public void run() {
                objects.add(0, "下拉 = " + (--index));
                adapter.notifyDataSetChanged();
                swipeRefreshGridView.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onListLoad() {
        swipeRefreshGridView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = footerIndex + 5;
                for (int i = footerIndex; i < count; i++) {
                    objects.add("上拉 = " + i);
                }
                footerIndex = count;
                adapter.notifyDataSetChanged();
                swipeRefreshGridView.setLoading(false);
            }
        }, 2000);
    }
}