package com.mylhyl.swiperefreshLayout.sample.app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mylhyl.rslayout.app.SwipeRefreshListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hupei on 2016/5/13.
 */
public class DemoSwipeRefreshListFragment extends SwipeRefreshListFragment {
    private ArrayAdapter<String> adapter;
    private List<String> objects = new ArrayList<>();
    private int index;
    private int footerIndex = 20;

    public static DemoSwipeRefreshListFragment newInstance() {
        return new DemoSwipeRefreshListFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSwipeRefreshLayout().showRefreshHeader();
        setEnabledLoad(true);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, objects);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onListItemClick(parent, view, position, id);
        Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        getSwipeRefreshLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.isEmpty()) {
                    for (int i = 0; i < footerIndex; i++) {
                        objects.add("数据 = " + i);
                    }
                } else {
                    objects.add(0, "下拉 = " + (--index));
                }
                adapter.notifyDataSetChanged();
                setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onListLoad() {
        getSwipeRefreshLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = footerIndex + 5;
                for (int i = footerIndex; i < count; i++) {
                    objects.add("上拉 = " + i);
                }
                footerIndex = count;
                adapter.notifyDataSetChanged();
                setLoading(false);
            }
        }, 2000);
    }
}
