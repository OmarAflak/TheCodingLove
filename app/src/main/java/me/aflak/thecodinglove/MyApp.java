package me.aflak.thecodinglove;

import android.app.Application;

import me.aflak.thecodinglove.api.ApiComponent;
import me.aflak.thecodinglove.api.ApiModule;
import me.aflak.thecodinglove.api.DaggerApiComponent;
import me.aflak.thecodinglove.app.AppModule;

public class MyApp extends Application {
    public static MyApp app;
    private ApiComponent apiComponent;
    private AppModule appModule;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        apiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule()).build();
        appModule = new AppModule(this);
    }

    public ApiComponent apiComponent() {
        return apiComponent;
    }

    public AppModule appModule() {
        return appModule;
    }
}
