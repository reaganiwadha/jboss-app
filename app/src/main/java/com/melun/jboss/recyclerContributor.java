package com.melun.jboss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


class recyclerContributor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MENU_ITEM_VIEW_TYPE = 0;

    private final Context mContext;

    private final List<Object> mRecyclerViewItems;

    public recyclerContributor(Context context, List<Object> recyclerViewItems) {
        this.mContext = context;
        this.mRecyclerViewItems = recyclerViewItems;
    }

    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private LinearLayout lineo;

        MenuItemViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            lineo = view.findViewById(R.id.lineor);
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
                        R.layout.repolayout, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final MenuItemViewHolder mih = (MenuItemViewHolder) holder;
        final getterContributor menuItem = (getterContributor) mRecyclerViewItems.get(position);

        mih.lineo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/"+mih.name.getText();
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        int viewType = getItemViewType(position);
        switch (viewType) {
            default:
                MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
                menuItemHolder.name.setText(menuItem.getUser());
        }

    }
}