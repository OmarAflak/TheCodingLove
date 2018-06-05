package me.aflak.thecodinglove.ui.main.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import me.aflak.thecodinglove.R;
import me.aflak.thecodinglove.entitiy.Post;
import me.aflak.thecodinglove.ui.main.interactor.MainInteractor;
import me.aflak.thecodinglove.ui.main.view.MainView;

public class MainPresenterImpl implements MainPresenter {
    private MainView view;
    private MainInteractor interactor;

    public MainPresenterImpl(MainView view, MainInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate(Bundle bundle) {
        view.showProgress();

        if(bundle==null) {
            nextPost();
        }
        else{
            interactor.restoreInstanceState(bundle);
            interactor.currentPost(new MainInteractor.PostCallback() {
                @Override
                public void onPost(Post post) {
                    view.showPost(post, requestListener);
                }

                @Override
                public void onError(String error) {
                    view.toast(error);
                }
            });
        }
    }

    @Override
    public void onSaveInstance(Bundle bundle) {
        interactor.saveInstanceState(bundle);
    }

    @Override
    public void previousPost() {
        view.showProgress();
        interactor.previousPost(new MainInteractor.PostCallback() {
            @Override
            public void onPost(Post post) {
                view.showPost(post, requestListener);
            }

            @Override
            public void onError(String error) {
                view.hideProgress();
                view.toast(error);
            }
        });
    }

    @Override
    public void nextPost() {
        view.showProgress();
        interactor.nextPost(new MainInteractor.PostCallback() {
            @Override
            public void onPost(Post post) {
                view.showPost(post, requestListener);
            }

            @Override
            public void onError(String error) {
                view.hideProgress();
                view.toast(error);
            }
        });
    }

    @Override
    public void menuGithubRepoClicked(Context context) {
        String url = context.getResources().getString(R.string.github_repo_link);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    private RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            view.hideProgress();
            view.toast("Could not load gif...");
            nextPost();
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            view.hideProgress();
            return false;
        }
    };
}
