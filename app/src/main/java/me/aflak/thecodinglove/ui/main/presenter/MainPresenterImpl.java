package me.aflak.thecodinglove.ui.main.presenter;

import android.util.Log;

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
    public void onCreate() {
        view.showProgress();
        nextPost();
    }

    @Override
    public void previousPost() {
        view.showProgress();
        interactor.previousPost(new MainInteractor.PostCallback() {
            @Override
            public void onPost(Post post) {
                view.showPost(post);
                view.hideProgress();
            }

            @Override
            public void onError(String error) {
                view.hideProgress();
                view.toast(error);
                Log.d(getClass().getSimpleName(), error);
            }
        });
    }

    @Override
    public void nextPost() {
        view.showProgress();
        interactor.nextPost(new MainInteractor.PostCallback() {
            @Override
            public void onPost(Post post) {
                view.showPost(post);
                view.hideProgress();
            }

            @Override
            public void onError(String error) {
                view.hideProgress();
                view.toast(error);
                Log.d(getClass().getSimpleName(), error);
            }
        });
    }
}
