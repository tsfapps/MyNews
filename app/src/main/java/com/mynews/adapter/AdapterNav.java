package com.mynews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mynews.R;
import com.mynews.activity.MainActivity;
import com.mynews.fragment.FragCatBlog;
import com.mynews.model.category.CatResponse;
import com.mynews.model.category.ModelCategory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterNav extends RecyclerView.Adapter<AdapterNav.NavViewHolder> {
    private MainActivity tActivity;
    private Context tContext;
    private ModelCategory tModels;
    private FragmentManager tFagmentManager;

    public AdapterNav(Context tContext, ModelCategory tModels, FragmentManager tFagmentManager, MainActivity tActivity) {
        this.tContext = tContext;
        this.tModels = tModels;
        this.tFagmentManager = tFagmentManager;
        this.tActivity = tActivity;
    }
    @NonNull
    @Override
    public NavViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nav_menu, viewGroup, false);

        return new NavViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NavViewHolder navViewHolder, int i) {
        final CatResponse tModel = tModels.getResponse().get(i);
        navViewHolder.tvNavMenu.setText(tModel.getName());
        final String strId = String.valueOf(tModel.getId());
        navViewHolder.tvNavMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tFagmentManager.beginTransaction().replace(R.id.container_main, FragCatBlog.newInstance(tContext, strId )).addToBackStack(null).commit();
                tActivity.closeDrawer();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tModels.getResponse().size();
    }

    public class NavViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_nav_menu)
        protected TextView tvNavMenu;
        public NavViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
