package pomex.garnet;

import android.app.Application;

import com.parse.Parse;

public class ParseAyar extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("---------------")
                .clientKey("---------------")
                .server("https://parseapi.back4app.com/")
                .build());
    }
}
