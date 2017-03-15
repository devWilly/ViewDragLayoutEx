package com.devwilly.tutorial.viewdraghelperex.adapter;

import com.devwilly.tutorial.viewdraghelperex.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Willy on 2017/3/15.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mList;

    public CustomRecyclerViewAdapter(ArrayList<String> list) {
        this.mList = list;
    }

    @Override
    public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.vh_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mInfoText.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mInfoText;

        ViewHolder(View itemView) {
            super(itemView);
            mInfoText = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
