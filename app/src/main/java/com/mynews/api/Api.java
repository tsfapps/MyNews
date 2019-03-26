package com.mynews.api;

import com.mynews.model.blog.ModelBlog;
import com.mynews.model.catBlog.ModelCatBlog;
import com.mynews.model.category.CatResponse;
import com.mynews.model.category.ModelCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @POST("api/all-category")
    Call<ModelCategory> getCategoryList();

    @FormUrlEncoded
    @POST("api/blogs")
    Call<ModelBlog> getBlogMain(
            @Field("skip") String strSkip,
            @Field("take") String strTake
    );

    @FormUrlEncoded
    @POST("api/category-blog")
    Call<ModelCatBlog> getCatBlog(
            @Field("id") String strId,
            @Field("skip") String strSkip,
            @Field("take") String strTake
    );
 @FormUrlEncoded
    @POST("api/single-blog")
    Call<ModelCatBlog> getSingleBlog(
            @Field("id") String strId
    );

}
