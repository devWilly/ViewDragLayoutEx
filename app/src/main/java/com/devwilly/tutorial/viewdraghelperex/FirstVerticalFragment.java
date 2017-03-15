package com.devwilly.tutorial.viewdraghelperex;

import com.devwilly.tutorial.viewdraghelperex.adapter.CustomRecyclerViewAdapter;
import com.devwilly.tutorial.viewdraghelperex.ui.CustomRecyclerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * Created by Willy on 2017/3/12.
 */

public class FirstVerticalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_layout, container, false);
        CustomRecyclerView recyclerView = (CustomRecyclerView) view.findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }

        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
