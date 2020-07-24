package com.luongthuan.lab4.server;

import com.luongthuan.lab4.model.Example;
import com.luongthuan.lab4.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {

    @GET("services/rest/")
    Call<Example> getListFavorite(@Query("extras") String extras,
                                      @Query("nojsoncallback") String nojsoncallback,
                                      @Query("user_id") String user_id,
                                      @Query("format") String format,
                                      @Query("api_key") String api_key,
                                      @Query("method") String method,
                                      @Query("page") int page,
                                      @Query("per_page") int per_page);
}
