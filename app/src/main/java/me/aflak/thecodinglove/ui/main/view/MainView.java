package me.aflak.thecodinglove.ui.main.view;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.RequestListener;

import me.aflak.thecodinglove.entitiy.Post;

public interface MainView {
    void showProgress();
    void hideProgress();
    void showPost(Post post, RequestListener<Drawable> listener);
    void toast(String message);
}
