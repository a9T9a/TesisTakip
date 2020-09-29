package pomex.garnet;

import android.app.Application;

import com.parse.Parse;

public class ParseAyar extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("gUcsnX4jER2vV3Ch6rStdF5801Mha8HJLGD3HSfX")
                .clientKey("PRX7AxCoBdT6oihJuBs4HSA6ZEC9EwVheFb7YutS")
                .server("https://parseapi.back4app.com/")
                .build());
    }
}
