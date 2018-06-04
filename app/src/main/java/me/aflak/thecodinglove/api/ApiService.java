package me.aflak.thecodinglove.api;

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
                    String body = response.body();
                    try {
                        JSONArray array = new JSONArray(body);
                        List<Post> posts = new ArrayList<>();
                        for(int i=0 ; i<array.length() ; i++){
                            JSONObject object = array.getJSONObject(i);
                            String description = object.getJSONObject("title").getString("rendered");
                            String html = object.getJSONObject("content").getString("rendered");
                            Document document = Jsoup.parse(html);
                            String url = document.getElementsByTag("img").first().absUrl("src");
                            posts.add(new Post(description, url));
                        }
                        callback.onPosts(posts);
                    } catch (JSONException e) {
                        callback.onError("Could not parse json : "+e.getMessage());
                        e.printStackTrace();
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
