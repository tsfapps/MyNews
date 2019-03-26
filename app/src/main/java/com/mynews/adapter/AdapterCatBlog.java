package com.mynews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mynews.R;
import com.mynews.fragment.FragSingleBlog;
import com.mynews.model.catBlog.CatBlogResponse;
import com.mynews.model.catBlog.ModelCatBlog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCatBlog extends RecyclerView.Adapter<AdapterCatBlog.BlogViewHolder> {
    private Context tContext;
    private ModelCatBlog tModels;
    private FragmentManager tFragmentManager;

    public AdapterCatBlog(Context tContext, ModelCatBlog tModels, FragmentManager tFragmentManager) {
        this.tContext = tContext;
        this.tModels = tModels;
        this.tFragmentManager = tFragmentManager;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_cat_blog_item, viewGroup, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder blogViewHolder, int i) {

        final CatBlogResponse tModel = tModels.getResponse().get(i);
        blogViewHolder.tvCatBlogTitle.setText(tModel.getTitle());
        blogViewHolder.tvCatBlogDate.setText(tModel.getCreatedAt());
        blogViewHolder.tvCatBlogViews.setText(tModel.getSubCategoryName());
        Glide.with(tContext).load(tModel.getImageUrl()).placeholder(R.drawable.logo).into(blogViewHolder.ivCatBlog);
        blogViewHolder.llCatBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tFragmentManager.beginTransaction().replace(R.id.container_main, FragSingleBlog.newInstance(tModel)).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tModels.getResponse().size();
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_cat_blog)
        protected ImageView ivCatBlog;
        @BindView(R.id.tv_cat_blog_title)
        protected TextView tvCatBlogTitle;
        @BindView(R.id.tv_cat_blog_date)
        protected TextView tvCatBlogDate;
        @BindView(R.id.tv_cat_blog_views)
        protected TextView tvCatBlogViews;
        @BindView(R.id.ll_cat_blog_item)
        protected LinearLayout llCatBlog;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
