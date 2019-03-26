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
import com.mynews.fragment.FragSingleMain;
import com.mynews.model.blog.BlogResponse;
import com.mynews.model.blog.ModelBlog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterBlog extends RecyclerView.Adapter<AdapterBlog.BloggViewHolder> {
    private Context tContext;
    private ModelBlog tModels;
    private FragmentManager fragmentManager;

    public AdapterBlog(Context tContext, ModelBlog tModels, FragmentManager fragmentManager) {
        this.tContext = tContext;
        this.tModels = tModels;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public BloggViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_blog_item, viewGroup, false);
        return new BloggViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloggViewHolder bloggViewHolder, int i) {

        final BlogResponse tModel = tModels.getResponse().get(i);
        bloggViewHolder.tvBlogTitle.setText(tModel.getTitle());
        bloggViewHolder.tvBlogDate.setText(tModel.getCreatedAt());
        bloggViewHolder.tvBlogViews.setText(tModel.getMainCategoryName());
        Glide.with(tContext).load(tModel.getImageUrl()).placeholder(R.drawable.logo).into(bloggViewHolder.ivBlog);
        bloggViewHolder.llBlogItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.container_main, FragSingleMain.newInstance(tModel)).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tModels.getResponse().size();
    }

    public class BloggViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_blog)
        protected ImageView ivBlog;
        @BindView(R.id.tv_blog_title)
        protected TextView tvBlogTitle;
        @BindView(R.id.tv_blog_date)
        protected TextView tvBlogDate;
        @BindView(R.id.tv_blog_views)
        protected TextView tvBlogViews;
        @BindView(R.id.ll_blog_item)
        protected LinearLayout llBlogItem;
        public BloggViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
