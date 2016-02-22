package award.sfparks.di.components;

import javax.inject.Singleton;

import award.sfparks.MainActivity;
import award.sfparks.di.modules.NetworkModule;
import award.sfparks.di.modules.PresenterModule;
import dagger.Component;

@Singleton
@Component (
        modules = { NetworkModule.class, PresenterModule.class}
)
public interface AppComponent {
    void inject(MainActivity activity);
}
