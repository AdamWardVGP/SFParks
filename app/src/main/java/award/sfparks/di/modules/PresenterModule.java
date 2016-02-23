package award.sfparks.di.modules;

import javax.inject.Singleton;

import award.sfparks.data.ParksService;
import award.sfparks.presenter.ParkPresenterImpl;
import award.sfparks.presenter.interfaces.ParkPresenter;
import award.sfparks.util.LocationService;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {
    @Provides
    @Singleton
    ParkPresenter getParkPresenter(ParksService parksService, LocationService locationService) {
        return new ParkPresenterImpl(parksService, locationService);
    }
}
