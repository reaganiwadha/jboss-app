package com.melun.jboss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        private LinearLayout llinside;

        MenuItemViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            language = view.findViewById(R.id.language);
            desc = view.findViewById(R.id.desc);
            stargazer = view.findViewById(R.id.stargazer);
            watcher = view.findViewById(R.id.watcher);
            llinside = view.findViewById(R.id.llinside);
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

        final MenuItemViewHolder mih = (MenuItemViewHolder) holder;
        final getterHome menuItem = (getterHome) mRecyclerViewItems.get(position);

        mih.llinside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.github.com/repos/JBossOutreach/" + menuItem.getName();
                SharedPreferences sharedPref = mContext.getSharedPreferences("openrepo",Context.MODE_PRIVATE);
                sharedPref.edit().putString("link",url).apply();
                mContext.startActivity(new Intent(mContext, aboutRepo.class));
            }
        });

        int viewType = getItemViewType(position);
        switch (viewType) {
            default:
                MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;

                menuItemHolder.name.setText(menuItem.getName());
                menuItemHolder.desc.setText(menuItem.getDesc());
                menuItemHolder.language.setText(menuItem.getLanguage());
                menuItemHolder.stargazer.setText(menuItem.getStargazer());
                menuItemHolder.watcher.setText(menuItem.getWatcher());
                // menuItemHolder.color
        }

        }
}