package com.sastroman.angga.androidlearn.view;

/**
 * Created by User on 12/13/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sastroman.angga.androidlearn.R;
import com.sastroman.angga.androidlearn.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
public class FragmentProduct3 extends Fragment {

    private ProgressBar pb;
    private RecyclerView recyclerView;

    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";

    public static FragmentProduct3 createInstance(int itemsCount) {
        FragmentProduct3 fragmentProduct3 = new FragmentProduct3();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        fragmentProduct3.setArguments(bundle);
        return fragmentProduct3;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_product3, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        pb = (ProgressBar)rootView.findViewById(R.id.progressBar);

        setupRecyclerView(recyclerView);

        return rootView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        //pb.setVisibility(View.VISIBLE);
        //recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(createItemList());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle!=null) {
            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);
            for (int i = 0; i < itemsCount; i++) {
                itemList.add("Item " + i);
            }
        }
        return itemList;
    }
}