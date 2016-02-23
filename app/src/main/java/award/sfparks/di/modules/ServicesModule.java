package award.sfparks.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import award.sfparks.util.LocationService;
import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

    private final Context context;

    public ServicesModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    LocationService getLocationServices() {
        return new LocationService(context);
    }
}
