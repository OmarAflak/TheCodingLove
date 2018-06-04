package me.aflak.thecodinglove.ui.main.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.aflak.thecodinglove.ui.main.interactor.MainInteractor;
import me.aflak.thecodinglove.ui.main.interactor.MainInteractorImpl;
import me.aflak.thecodinglove.ui.main.presenter.MainPresenter;
import me.aflak.thecodinglove.ui.main.presenter.MainPresenterImpl;
import me.aflak.thecodinglove.ui.main.view.MainView;

@Module
public class MainModule {
    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides @Singleton
    public MainView provideMainView(){
        return view;
    }

    @Provides @Singleton
    public MainInteractor provideMainInteractot(){
        return new MainInteractorImpl();
    }

    @Provides @Singleton
    public MainPresenter provideMainPresenter(MainView view, MainInteractor interactor){
        return new MainPresenterImpl(view, interactor);
    }
}
