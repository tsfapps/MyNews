package com.mynews.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mynews.R;
import com.mynews.model.catBlog.CatBlogResponse;
import com.mynews.utils.Constant;

import org.apache.http.util.EncodingUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragSingleBlog extends Fragment {
    @BindView(R.id.iv_singleBlog)
    protected ImageView ivSingleBlog;
    @BindView(R.id.tv_singleBlog_Date)
    protected TextView tvSingleDate;
    @BindView(R.id.tv_singleBlog_category)
    protected TextView tvSingleCategory;
    @BindView(R.id.tv_singleBlog_Title)
    protected TextView tvSingleBlogTitle;
    @BindView(R.id.wv_singleBLog_body)
    protected WebView wvSingleBlogBody;
    private CatBlogResponse tModel;


    public static FragSingleBlog newInstance(CatBlogResponse tModel) {

        FragSingleBlog fragment = new FragSingleBlog();
        fragment.tModel = tModel;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_single, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getContext()).load(tModel.getImageUrl()).into(ivSingleBlog);
        tvSingleCategory.setText(tModel.getSubCategoryName());
        tvSingleDate.setText(tModel.getCreatedAt());
        tvSingleBlogTitle.setText(tModel.getTitle());
        String strId = String.valueOf(tModel.getId());
        postUrl(Constant.BODY_URL+strId, strId);
        return view;
    }
    private void postUrl(String url, String strId ) {
        wvSingleBlogBody.postUrl(url, EncodingUtils.getBytes(strId, "BASE64"));
    }

}
