package com.mynews.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mynews.R;
import com.mynews.model.blog.BlogResponse;
import com.mynews.utils.Constant;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragSingleMain extends Fragment {
    @BindView(R.id.iv_singleMain)
    protected ImageView ivSingleMain;
    @BindView(R.id.tv_singleMain_Date)
    protected TextView tvSingleMainDate;
    @BindView(R.id.tv_singleMain_category)
    protected TextView tvSingleMainCategory;
    @BindView(R.id.tv_singleMain_Title)
    protected TextView tvSingleMainTitle;
    @BindView(R.id.wv_singleMain_body)
    protected WebView wvSingleMainBody;
    private BlogResponse tModel;

    public static FragSingleMain newInstance(BlogResponse tModel) {

        FragSingleMain fragment = new FragSingleMain();
        fragment.tModel = tModel;
        return fragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_single_main, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getContext()).load(tModel.getImageUrl()).into(ivSingleMain);
        tvSingleMainCategory.setText(tModel.getSubCategoryName());
        tvSingleMainDate.setText(tModel.getCreatedAt());
        tvSingleMainTitle.setText(tModel.getTitle());
        String strId = String.valueOf(tModel.getId());
        postUrl(Constant.BODY_URL+strId, strId);
        return view;
    }

    private void postUrl(String url, String strId ) {

        wvSingleMainBody.postUrl(url, EncodingUtils.getBytes(strId, "BASE64"));

    }
}