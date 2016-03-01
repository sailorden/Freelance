package xyz.umng.freelance;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Umang on 2/29/2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // initializing parse library
        Parse.initialize( this ,"", "");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
