package bg.uni_sofia.fmi.explore;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bg.uni_sofia.fmi.explore.models.PlaceModel;
import bg.uni_sofia.fmi.explore.models.PlacePhotoModel;
import bg.uni_sofia.fmi.explore.services.PlaceService;
import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by eyanev on 5/22/15.
 */
public class PlaceActivity extends FragmentActivity {

    @InjectView(R.id.card_title)
    TextView mTitle;
    @InjectView(R.id.card_image)
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity);
        ButterKnife.inject(this);
    }

    public void onEventMainThread(PlaceModel placeModel) {
        PlaceService placeService = new PlaceService();

        mTitle.setText(placeModel.getPlaceName());
        if(placeModel.getPhotos() != null) {
            for(PlacePhotoModel photo : placeModel.getPhotos()) {
                placeService.loadPlacePhoto(this, mImage, photo.getPhotoReference());
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        EventBus.getDefault().registerSticky(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
