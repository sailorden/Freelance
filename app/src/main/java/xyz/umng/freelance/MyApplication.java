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
        Parse.initialize( this ,"Om2bIcLgd3hbHJ08BPwb6wPKVCZ3XCnPa3IZPfVO", "TqvqazLpeAPS4qTEmawAa27mEAjTqAlgKkAsYEZn");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
