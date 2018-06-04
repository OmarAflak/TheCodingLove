package me.aflak.thecodinglove;

import android.app.Application;

import me.aflak.thecodinglove.api.ApiComponent;
import me.aflak.thecodinglove.api.ApiModule;
import me.aflak.thecodinglove.api.DaggerApiComponent;

public class MyApp extends Application {
    public static MyApp app;
    private ApiComponent apiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        apiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule()).build();
    }

    public ApiComponent apiComponent() {
        return apiComponent;
    }
}
