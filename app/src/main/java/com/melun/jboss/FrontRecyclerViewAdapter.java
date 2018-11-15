package com.melun.jboss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MENU_ITEM_VIEW_TYPE = 0;

    private final Context mContext;

    private final List<Object> mRecyclerViewItems;

    public RecyclerViewAdapter(Context context, List<Object> recyclerViewItems) {
        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView language;
        private TextView desc;
        private TextView stargazer;
        private TextView watcher;
        private RelativeLayout color;

        MenuItemViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            language = view.findViewById(R.id.language);
            desc = view.findViewById(R.id.desc);
            stargazer = view.findViewById(R.id.stargazer);
            watcher = view.findViewById(R.id.watcher);
            color = view.findViewById(R.id.color);
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case MENU_ITEM_VIEW_TYPE:
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.cardview_repo, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}