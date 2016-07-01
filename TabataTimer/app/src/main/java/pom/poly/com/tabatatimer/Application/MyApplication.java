package pom.poly.com.tabatatimer.Application;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

/**
 * Created by User on 30/6/2016.
 */
public class MyApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

// Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

// Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(getApplicationContext())
        );

// Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

// Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
        //TODO remeber to delete the Stetho when publish the app
    }
}