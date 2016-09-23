package kg.taptap.android;

import android.app.Application;

import roboguice.RoboGuice;
import kg.taptap.android.modules.ApiModule;

public class AndroidApplication extends Application {

    private static final String TAG = AndroidApplication.class.getSimpleName();

    private static AndroidApplication instance = null;

    public static AndroidApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RoboGuice.overrideApplicationInjector(this,
                RoboGuice.newDefaultRoboModule(this), new ApiModule());
    }
}
