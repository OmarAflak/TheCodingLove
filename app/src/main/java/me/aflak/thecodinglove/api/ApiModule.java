package me.aflak.thecodinglove.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class ApiModule {
    @Provides @Singleton
    public ScalarsConverterFactory provideConverter(){
        return ScalarsConverterFactory.create();
    }

    @Provides @Singleton
    public Api provideApi(ScalarsConverterFactory converterFactory){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://thecodinglove.com")
                .addConverterFactory(converterFactory)
                .build();

        return retrofit.create(Api.class);
    }

    @Provides @Singleton
    public ApiService provideApiService(Api api){
        return new ApiService(api);
    }
}
