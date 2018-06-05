package me.aflak.thecodinglove.ui.main.interactor;

import android.os.Bundle;

import me.aflak.thecodinglove.entitiy.Post;

public interface MainInteractor {
    void saveInstanceState(Bundle bundle);
    void restoreInstanceState(Bundle bundle);
    void currentPost(PostCallback callback);
    void nextPost(PostCallback callback);
    void previousPost(PostCallback callback);

    interface PostCallback{
        void onPost(Post post);
        void onError(String error);
    }
}
