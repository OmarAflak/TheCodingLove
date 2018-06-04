package me.aflak.thecodinglove.ui.main.view;

import me.aflak.thecodinglove.entitiy.Post;

public interface MainView {
    void showProgress();
    void hideProgress();
    void showPost(Post post);
    void toast(String message);
}
