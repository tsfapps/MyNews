package com.mynews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mynews.R;
import com.mynews.activity.MainActivity;
import com.mynews.adapter.AdapterBlog;
import com.mynews.adapter.AdapterCatBlog;
import com.mynews.api.Api;
import com.mynews.api.ApiClients;
import com.mynews.model.blog.ModelBlog;
import com.mynews.model.catBlog.ModelCatBlog;
import com.mynews.utils.Constant;
import com.mynews.utils.CustomLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragCatBlog extends Fragment {
    @BindView(R.id.rv_cat_blog)
    protected RecyclerView rvCatBlog;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private ModelCatBlog tModels;
    private  boolean loading = true;
    private Context tContext;
    private String strId;
    private FragmentManager tFragmentManager;

    private MainActivity tActivity;

    public static FragCatBlog newInstance(Context tContext, String strId) {

        FragCatBlog fragment = new FragCatBlog();
        fragment.tContext = tContext;
        fragment.strId = strId;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_cat_blog, container, false);
        ButterKnife.bind(this, view);
        tActivity = (MainActivity) getActivity();
        initRvBlog();
        initFrag();
        return view;
    }
    private void initFrag(){

        tFragmentManager = getFragmentManager();
        callApi();
    }
    private void initRvBlog(){

    }
    private void callApi(){
        tActivity.uiThreadHandler.sendEmptyMessage(Constant.SHOW_PROGRESS_DIALOG);
        final LinearLayoutManager tManager = new LinearLayoutManager(tContext);
        rvCatBlog.setLayoutManager(tManager);

        rvCatBlog.addOnScrollListener(new RecyclerView.OnScrollListener()
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
        Call<ModelCatBlog> call = api.getCatBlog(strId,"0", "20");
        call.enqueue(new Callback<ModelCatBlog>() {
            @Override
            public void onResponse(Call<ModelCatBlog> call, Response<ModelCatBlog> response) {
                tModels = response.body();
                AdapterCatBlog tAdapter = new AdapterCatBlog(tContext, tModels, tFragmentManager);
                rvCatBlog.setAdapter(tAdapter);
                tActivity.uiThreadHandler.sendMessageDelayed(tActivity.uiThreadHandler.obtainMessage(Constant.HIDE_PROGRESS_DIALOG),Constant.HIDE_PROGRESS_DIALOG_DELAY);

            }
            @Override
            public void onFailure(Call<ModelCatBlog> call, Throwable t) {
                tActivity.uiThreadHandler.sendMessageDelayed(tActivity.uiThreadHandler.obtainMessage(Constant.HIDE_PROGRESS_DIALOG),Constant.HIDE_PROGRESS_DIALOG_DELAY);
            }
        });
    }
}
