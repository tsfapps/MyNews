package com.mynews.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mynews.R;
import com.mynews.activity.MainActivity;
import com.mynews.adapter.AdapterBlog;
import com.mynews.adapter.AdapterSlider;
import com.mynews.api.Api;
import com.mynews.api.ApiClients;
import com.mynews.model.blog.ModelBlog;
import com.mynews.utils.Constant;
import com.mynews.utils.CustomLog;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragBlog extends Fragment {
    @BindView(R.id.rv_blog)
    protected RecyclerView rvBlog;
    @BindView(R.id.viewPager)
    protected ViewPager viewPager;
    @BindView(R.id.indicator)
    protected TabLayout indicator;
    private ModelBlog tBannerModels;

    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private Context tContext;
    private ModelBlog tModels;
    private FragmentManager tFragmentManager;

    private  boolean loading = true;
    private MainActivity tActivity;

    public static FragBlog newInstance(Context tContext) {

        FragBlog fragment = new FragBlog();
        fragment.tContext = tContext;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_blog, container, false);
        ButterKnife.bind(this, view);
        tActivity = (MainActivity)getActivity();
        initRvBlog();
        initFrag();
        return view;
    }
    private void initFrag(){
        tFragmentManager = getFragmentManager();
        apiBanner();
        indicator.setupWithViewPager(viewPager, true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 10000, 10000);
        callApi();
    }
    private void initRvBlog(){

    }


    private void apiBanner(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelBlog> call = api.getBlogMain("0", "5");
        call.enqueue(new Callback<ModelBlog>() {
            @Override
            public void onResponse(Call<ModelBlog> call, Response<ModelBlog> response) {
                tBannerModels = response.body();
                viewPager.setAdapter(new AdapterSlider(getContext(), tBannerModels));
            }

            @Override
            public void onFailure(Call<ModelBlog> call, Throwable t) {

            }
        });
    }


    private void callApi(){
        tActivity.uiThreadHandler.sendEmptyMessage(Constant.SHOW_PROGRESS_DIALOG);
        final LinearLayoutManager tManager = new LinearLayoutManager(tContext);
        rvBlog.setLayoutManager(tManager);




        rvBlog.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = tManager.getChildCount();
                    totalItemCount = tManager.getItemCount();
                    pastVisiblesItems = tManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            CustomLog.e(Constant.TAG, "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });


        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelBlog> call = api.getBlogMain("0", "20");
        call.enqueue(new Callback<ModelBlog>() {
            @Override
            public void onResponse(Call<ModelBlog> call, Response<ModelBlog> response) {
                tModels = response.body();
                AdapterBlog tAdapter = new AdapterBlog(tContext, tModels, tFragmentManager);
                rvBlog.setAdapter(tAdapter);
                tActivity.uiThreadHandler.sendMessageDelayed(tActivity.uiThreadHandler.obtainMessage(Constant.HIDE_PROGRESS_DIALOG),Constant.HIDE_PROGRESS_DIALOG_DELAY);

            }
            @Override
            public void onFailure(Call<ModelBlog> call, Throwable t) {
                tActivity.uiThreadHandler.sendMessageDelayed(tActivity.uiThreadHandler.obtainMessage(Constant.HIDE_PROGRESS_DIALOG),Constant.HIDE_PROGRESS_DIALOG_DELAY);
            }
        });
    }
    private class SliderTimer extends TimerTask {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            try {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < tBannerModels.getResponse().size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            } catch (Exception e) {
                CustomLog.d(Constant.TAG, "View Pager : "+e);
            }
        }
    }
}
