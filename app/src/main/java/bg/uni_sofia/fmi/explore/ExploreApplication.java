package bg.uni_sofia.fmi.explore;

import android.app.Application;
import android.location.Location;

import bg.uni_sofia.fmi.explore.models.UserModel;
import bg.uni_sofia.fmi.explore.providersWrappers.UserProviderWrapper;

/**
 * Created by eyanev on 5/15/15.
 */
public class ExploreApplication extends Application {
    private UserModel currentUser;
    private Location mLastKnownLocation;

    @Override
    public void onCreate() {
        super.onCreate();

        currentUser = UserProviderWrapper.getCurrentUser(this);
    }

    public void setCurrentUser(UserModel currentUser) {
        this.currentUser = currentUser;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    public Location getmLastKnownLocation() {
        return mLastKnownLocation;
    }

    public void setmLastKnownLocation(Location mLastKnownLocation) {
        this.mLastKnownLocation = mLastKnownLocation;
    }
}
