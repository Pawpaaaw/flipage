package flipage.cpu.com.cpuflipage.utils;

import android.app.Application;
import android.content.Context;


public class FlipageApp extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        appContext = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext() {
        return appContext;
    }
}
