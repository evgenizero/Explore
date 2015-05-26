package bg.uni_sofia.fmi.explore.fragments;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bg.uni_sofia.fmi.explore.R;
import bg.uni_sofia.fmi.explore.adapters.PlaceAdapter;
import bg.uni_sofia.fmi.explore.filters.PlaceCriteria;
import bg.uni_sofia.fmi.explore.filters.PlaceDistanceCriteria;
import bg.uni_sofia.fmi.explore.models.PlaceModel;
import bg.uni_sofia.fmi.explore.services.LocationService;
import bg.uni_sofia.fmi.explore.services.PlaceService;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by eyanev on 5/14/15.
 */
public class NearbyPlacesFragment extends PlacesFragment implements GoogleApiClient.OnConnectionFailedListener {
    private static final int LOCATION_TIMEOUT_SECONDS = 20;
    private static final String TAG = "NearbyPlacesFragment";

    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private GoogleApiClient mGoogleApiClient;

    @InjectView(R.id.cardList)
    RecyclerView mCardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nearby_places, container, false);
        ButterKnife.inject(this, v);

        mCardList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mCardList.setLayoutManager(llm);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        final PlaceService placeService = new PlaceService();

        LocationService locationService = new LocationService(lm);
        Observable<List<PlaceModel>> stringObservable = locationService.getLocation().timeout(20, TimeUnit.SECONDS).
                flatMap(new Func1<Location, Observable<List<PlaceModel>>>() {
                    @Override
                    public Observable<List<PlaceModel>> call(Location location) {
                        return placeService.getPlacesForLocation(location);
                    }
                });

        mCompositeSubscription.add(stringObservable.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.newThread())
        .subscribe(new Action1<List<PlaceModel>>() {
            @Override
            public void call(List<PlaceModel> placeModels) {
                mCardList.setAdapter(new PlaceAdapter(getActivity(), placeModels));
            }
        }));


        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), String.valueOf(connectionResult), Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(PlaceCriteria placeCriteria) {
        Observable<List<PlaceModel>> listObservable = placeCriteria.meetCriteria(((PlaceAdapter) mCardList.getAdapter()).getData());

        mCompositeSubscription.add(listObservable.subscribe(new Action1<List<PlaceModel>>() {
            @Override
            public void call(List<PlaceModel> placeModels) {
                mCardList.setAdapter(new PlaceAdapter(getActivity(), placeModels));
            }
        }));
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
