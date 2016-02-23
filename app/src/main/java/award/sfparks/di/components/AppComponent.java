package award.sfparks.di.components;

import javax.inject.Singleton;

import award.sfparks.MainActivity;
import award.sfparks.di.modules.NetworkModule;
import award.sfparks.di.modules.PresenterModule;
import award.sfparks.di.modules.ServicesModule;
import dagger.Component;

@Singleton
@Component (modules = { NetworkModule.class, PresenterModule.class, ServicesModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
}
