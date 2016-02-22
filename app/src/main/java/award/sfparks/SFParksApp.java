package award.sfparks;

import android.app.Application;
import android.content.Context;

import award.sfparks.di.components.AppComponent;
import award.sfparks.di.components.DaggerAppComponent;
import award.sfparks.di.modules.NetworkModule;

/**
 * Custom application object
 *
 * This sets up the applications object graph
 */
public class SFParksApp extends Application {

    private AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }

    public static SFParksApp from(Context context) {
        return (SFParksApp) context.getApplicationContext();
    }

    public AppComponent getComponent() {
        return applicationComponent;
    }
}
