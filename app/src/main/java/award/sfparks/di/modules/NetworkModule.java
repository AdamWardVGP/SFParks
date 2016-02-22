package award.sfparks.di.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import award.sfparks.data.ParksAPI;
import award.sfparks.data.ParksService;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    GsonConverter getGSONConverter() {
        return new GsonConverter(new Gson());
    }

    @Provides
    @Singleton
    ParksAPI getParksAPI(GsonConverter converter) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ParksAPI.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(converter)
                .build();

        return restAdapter.create(ParksAPI.class);
    }

    @Provides
    @Singleton
    ParksService getParksService(ParksAPI parkAPI) {
        return new ParksService(parkAPI);
    }
}
