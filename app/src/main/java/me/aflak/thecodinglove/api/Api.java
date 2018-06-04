package me.aflak.thecodinglove.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("/wp-json/wp/v2/posts")
    Call<String> getPosts(@Query("page") int page, @Query("per_page") int perPage);
}
