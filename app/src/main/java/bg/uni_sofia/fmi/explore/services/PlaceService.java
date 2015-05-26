package bg.uni_sofia.fmi.explore.services;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.location.places.Place;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpException;

import java.util.ArrayList;
import java.util.List;

import bg.uni_sofia.fmi.explore.R;
import bg.uni_sofia.fmi.explore.models.Geometry;
import bg.uni_sofia.fmi.explore.models.PlaceModel;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by eyanev on 5/18/15.
 */
public class PlaceService {

    static final String API_KEY = "AIzaSyBgP9y3zvmow8TbYCJZX3lp8ci1cMlT-UU";
    static final String BASE_URL = "https://maps.googleapis.com/maps/api/place";

    private final GooglePlaceApiService mWebService;

    public PlaceService() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Accept", "application/json");
                request.addQueryParam("key", API_KEY);
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mWebService = restAdapter.create(GooglePlaceApiService.class);
    }

    public Observable<List<PlaceModel>> getPlacesForLocation(final android.location.Location location) {
        return mWebService.getPlacesForLocation(String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()))
                .flatMap(new Func1<PlaceListEnvelope, Observable<PlaceModel>>() {
                    @Override
                    public Observable<PlaceModel> call(PlaceListEnvelope placeListEnvelope) {
                        return Observable.from(placeListEnvelope.getResults());
                    }
                })
                .map(new Func1<PlaceModel, PlaceModel>() {
                    @Override
                    public PlaceModel call(PlaceModel placeModel) {
                        Geometry.Location mockPlaceLocation = placeModel.getGeometry().getLocation();
                        Location placeLocation = new Location(placeModel.getPlaceName());
                        placeLocation.setLatitude(mockPlaceLocation.getLat());
                        placeLocation.setLongitude(mockPlaceLocation.getLng());

                        placeModel.setDistance((int)location.distanceTo(placeLocation));

                        return placeModel;
                    }
                })
                .toList();
    }

    public Observable<PlaceModel> getPlaceDetails(String placeId) {
        return mWebService.getPlaceDetails(placeId);
    }

    public void loadPlacePhoto(Context context, ImageView placeholder, String photoReference) {
        Picasso.with(context).load(BASE_URL + "/photo?maxwidth=300&photoreference=" + photoReference + "&key=" + API_KEY).fit()
                .error(R.drawable.no_place_image)
                .placeholder(R.drawable.no_place_image)
                .centerCrop().into(placeholder);
    }

    private interface GooglePlaceApiService {

        @GET("/nearbysearch/json?radius=1000")
        public Observable<PlaceListEnvelope> getPlacesForLocation(@Query("location") String location);

        @GET("/details/json")
        public Observable<PlaceModel> getPlaceDetails(@Query("placeid") String placeId);
    }

    public class PlaceListEnvelope {

        private List<PlaceModel> results = new ArrayList<PlaceModel>();
        private String status;

        public List<PlaceModel> getResults() {
            return results;
        }

        public void setResults(List<PlaceModel> results) {
            this.results = results;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
