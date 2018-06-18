package me.aflak.thecodinglove.ui.main.presenter;

import android.content.Context;
import android.os.Bundle;

public interface MainPresenter {
    void onCreate(Bundle bundle);
    void onSaveInstance(Bundle bundle);
    void previousPost();
    void nextPost();
    void menuGithubRepoClicked(Context context);
    void onImageLongClick(Context context);
}
