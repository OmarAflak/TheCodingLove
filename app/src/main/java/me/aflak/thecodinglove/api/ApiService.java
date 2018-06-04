package me.aflak.thecodinglove.api;

import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import me.aflak.thecodinglove.entitiy.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService {
    private Api api;

    public ApiService(Api api) {
        this.api = api;
    }

    public void getPosts(int page, int perPage, final ApiServiceCallback callback){
        if(callback!=null){
            api.getPosts(page, perPage).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) {
                        String body = response.body();
                        try {
                            Log.d(getClass().getSimpleName(), body);

                            JSONArray array = new JSONArray(body);
                            List<Post> posts = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String description = Jsoup.parse(object.getJSONObject("title").getString("rendered")).body().text();
                                String url = object.getJSONObject("content").getString("rendered");
                                url = Html.fromHtml(Jsoup.parse(url).getElementsByTag("img").first().absUrl("src")).toString();
                                posts.add(new Post(description, url));
                            }
                            callback.onPosts(posts);
                        } catch (JSONException e) {
                            callback.onError("Could not parse json : " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    else{
                        callback.onError("Could not request");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    callback.onError("Could not request : "+call.request().url().toString());
                }
            });
        }
    }

    public interface ApiServiceCallback{
        void onPosts(List<Post> posts);
        void onError(String error);
    }
}
