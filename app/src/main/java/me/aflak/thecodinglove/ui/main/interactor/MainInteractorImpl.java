package me.aflak.thecodinglove.ui.main.interactor;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import me.aflak.thecodinglove.MyApp;
import me.aflak.thecodinglove.api.ApiService;
import me.aflak.thecodinglove.entitiy.Post;

public class MainInteractorImpl implements MainInteractor {
    private int page;
    private int perPage;
    private int currentIndex;
    private List<Post> postList;

    @Inject ApiService apiService;

    public MainInteractorImpl() {
        page = 1;
        perPage = 50;
        currentIndex = -1;
        postList = new ArrayList<>();

        MyApp.app.apiComponent().inject(this);
    }

    @Override
    public void saveInstanceState(Bundle bundle) {
        bundle.putInt("page", page);
        bundle.putInt("index", currentIndex);
        bundle.putParcelableArray("posts", postList.toArray(new Post[postList.size()]));
    }

    @Override
    public void restoreInstanceState(Bundle bundle) {
        page = bundle.getInt("page");
        currentIndex = bundle.getInt("index");
        Post[] posts = (Post[]) bundle.getParcelableArray("posts");
        if(posts!=null) {
            postList = Arrays.asList(posts);
        }
        else{
            postList.clear();
        }
    }

    @Override
    public void currentPost(PostCallback callback) {
        getPost(currentIndex, callback);
    }

    @Override
    public void nextPost(PostCallback callback) {
        getPost(++currentIndex, callback);
    }

    @Override
    public void previousPost(PostCallback callback) {
        getPost(--currentIndex, callback);
    }

    @Override
    public Post getCurrentPost() {
        return postList.get(currentIndex);
    }

    private void getPost(int index, final PostCallback callback){
        if(callback != null){
            if(!postList.isEmpty()){
                if(index>=0 && index<postList.size()-1){
                    callback.onPost(postList.get(index));
                }
                else{
                    page++;
                    currentIndex = 0;
                    apiService.getPosts(page, perPage, new ApiService.ApiServiceCallback() {
                        @Override
                        public void onPosts(List<Post> posts) {
                            postList = posts;
                            callback.onPost(postList.get(0));
                        }

                        @Override
                        public void onError(String error) {
                            callback.onError(error);
                        }
                    });
                }
            }
            else{
                apiService.getPosts(page, perPage, new ApiService.ApiServiceCallback() {
                    @Override
                    public void onPosts(List<Post> posts) {
                        postList = posts;
                        callback.onPost(postList.get(0));
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });
            }
        }
    }
}
