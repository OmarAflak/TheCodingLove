package me.aflak.thecodinglove.app;

import android.content.Context;
import android.graphics.Typeface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.aflak.thecodinglove.R;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides @Singleton
    public Typeface provideTypeface(Context context){
        return context.getResources().getFont(R.font.fira_mono_regular);
    }
}
