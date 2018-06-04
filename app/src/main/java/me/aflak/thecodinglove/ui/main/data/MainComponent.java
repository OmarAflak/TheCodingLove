package me.aflak.thecodinglove.ui.main.data;

import javax.inject.Singleton;

import dagger.Component;
import me.aflak.thecodinglove.app.AppModule;
import me.aflak.thecodinglove.ui.main.view.MainActivity;

@Singleton
@Component(modules = {AppModule.class, MainModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
