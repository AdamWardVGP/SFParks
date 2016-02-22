package award.sfparks.di.components;

import javax.inject.Singleton;

import award.sfparks.MainActivity;
import award.sfparks.di.modules.NetworkModule;
import dagger.Component;

@Singleton
@Component (
        modules = { NetworkModule.class}
)
public interface AppComponent {
    void inject(MainActivity activity);
}
