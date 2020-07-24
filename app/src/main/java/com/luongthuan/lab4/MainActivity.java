package com.luongthuan.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.luongthuan.lab4.model.Example;
import com.luongthuan.lab4.model.Photo;
import com.luongthuan.lab4.server.FlickrService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout srlRefesh;
    RecyclerView rvPhoto;
    List<Photo> photoList;
    MyAdapter myAdapter;
    int number = 0;
    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
    private static final String USER_ID = "187015156@N07";
    private static final String KEY_TOKEN = "9d788c3ae7173a1cda830edcc1be5792";
    private static final String GET_FAVORITE = "flickr.favorites.getList";
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        srlRefesh = findViewById(R.id.srlRefesh);
        rvPhoto = findViewById(R.id.rvPhoto);
        getRetrofit();
        srlRefesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRetrofit();
                srlRefesh.setRefreshing(false);
            }
        });
    }

    private void getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FlickrService flickrService = retrofit.create(FlickrService.class);

        flickrService.getListFavorite(FULL_EXTRAS, "1", USER_ID, "json", KEY_TOKEN, GET_FAVORITE, page, 40).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                List<Photo> photoList = response.body().getPhotos().getPhoto();
                rvPhoto.setHasFixedSize(true);
                myAdapter = new MyAdapter(photoList, MainActivity.this);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

                rvPhoto.setLayoutManager(staggeredGridLayoutManager);
                rvPhoto.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    private void getVoley() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.flickr.com/services/rest/?method=flickr.favorites.getList&api_key=9d788c3ae7173a1cda830edcc1be5792&user_id=187015156%40N07&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&per_page=20&page=1&format=json&nojsoncallback=1";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("photos");
                            JSONArray array = jsonObject1.getJSONArray("photo");
                            photoList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                Photo photo = new Photo();
                                JSONObject jsonObject2 = array.getJSONObject(i);
                                String url = jsonObject2.getString("url_l");
                                String title = jsonObject2.getString("title");
                                String pathalias = jsonObject2.getString("pathalias");
                                photo.setTitle(title);
                                photo.setUrlL(url);
                                photo.setPathalias(pathalias);
                                Log.e("Title", title);
                                photoList.add(photo);
                            }
                            rvPhoto.setHasFixedSize(true);
                            myAdapter = new MyAdapter(photoList, MainActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            rvPhoto.setLayoutManager(linearLayoutManager);
                            rvPhoto.setAdapter(myAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Lỗi: ", error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}