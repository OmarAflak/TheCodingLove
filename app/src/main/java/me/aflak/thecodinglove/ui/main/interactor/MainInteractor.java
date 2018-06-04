package me.aflak.thecodinglove.ui.main.interactor;

import me.aflak.thecodinglove.entitiy.Post;

public interface MainInteractor {
    void nextPost(PostCallback callback);
    void previousPost(PostCallback callback);

    interface PostCallback{
        void onPost(Post post);
        void onError(String error);
    }
}
