package com.mynews.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mynews.R;
import com.mynews.adapter.AdapterNav;
import com.mynews.api.Api;
import com.mynews.api.ApiClients;
import com.mynews.fragment.FragBlog;
import com.mynews.model.category.CatResponse;
import com.mynews.model.category.ModelCategory;
import com.mynews.utils.Constant;
import com.mynews.utils.CustomLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ModelCategory tModels;
    private FragmentManager tFragmentManager;

    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.rv_inside_nav)
    protected RecyclerView rvNav;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;
    private ProgressDialog mDialog;
    public UIThreadHandler uiThreadHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        uiThreadHandler = new UIThreadHandler();
        tFragmentManager = getSupportFragmentManager();
        callApi();
        initRvNav();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        initFrag();
    }

    private void initFrag(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, FragBlog.newInstance(this)).commit();
    }

    public void setToolTitle(String strTitle){
        tvToolbar.setText(strTitle);
    }

    public void closeDrawer(){
        drawer.closeDrawer(GravityCompat.START);
    }


    private void callApi(){
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelCategory> call = api.getCategoryList();
        call.enqueue(new Callback<ModelCategory>() {
            @Override
            public void onResponse(Call<ModelCategory> call, Response<ModelCategory> response) {
                tModels = response.body();
                AdapterNav tAdapter = new AdapterNav(getApplicationContext(), tModels, tFragmentManager, MainActivity.this);
                rvNav.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<ModelCategory> call, Throwable t) {
                CustomLog.d(Constant.TAG, "Responding : "+ t);
            }
        });
    }

    private void initRvNav(){
        RecyclerView.LayoutManager tManager = new LinearLayoutManager(this);
        rvNav.setLayoutManager(tManager);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class UIThreadHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SHOW_PROGRESS_DIALOG:
                    showProgressDialog();
                    break;
                case Constant.HIDE_PROGRESS_DIALOG:
                    hideProgressDialog();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private void hideProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Loading....");
        mDialog.show();
    }

}
