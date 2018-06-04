package me.aflak.thecodinglove.api;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.thecodinglove.ui.main.interactor.MainInteractorImpl;

@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(MainInteractorImpl interactor);
}
