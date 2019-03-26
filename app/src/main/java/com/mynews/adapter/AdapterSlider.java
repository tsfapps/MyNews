package com.mynews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mynews.R;
import com.mynews.model.blog.BlogResponse;
import com.mynews.model.blog.ModelBlog;

import java.util.List;

public class AdapterSlider extends PagerAdapter {

    private Context context;
    private List<Integer> color;
    private List<String> colorName;
    private ModelBlog tBannerModels;


    public AdapterSlider(Context context, ModelBlog tBannerModels) {
        this.context = context;
        this.tBannerModels = tBannerModels;
        this.colorName = colorName;
    }

    @Override
    public int getCount() {
        return tBannerModels.getResponse().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);

        BlogResponse tModel = tBannerModels.getResponse().get(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
       // LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        ImageView imageView = view.findViewById(R.id.iv_slider_main);
        Glide.with(context).load(tModel.getImageUrl()).into(imageView);

        textView.setText(tModel.getTitle());
       // textView.setText(colorName.get(position));
       // linearLayout.setBackgroundColor(color.get(position));

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }}
