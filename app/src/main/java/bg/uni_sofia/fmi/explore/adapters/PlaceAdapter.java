package bg.uni_sofia.fmi.explore.adapters;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bg.uni_sofia.fmi.explore.R;
import bg.uni_sofia.fmi.explore.models.PlaceModel;
import bg.uni_sofia.fmi.explore.models.PlacePhotoModel;
import bg.uni_sofia.fmi.explore.services.PlaceService;
import de.greenrobot.event.EventBus;

/**
 * Created by eyanev on 5/15/15.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {
    private FragmentActivity mActivity;
    private List<PlaceModel> placesList;
    private final PlaceService mPlaceService;

    public PlaceAdapter(FragmentActivity activity, List<PlaceModel> places) {
        this.mActivity = activity;
        this.placesList = places;
        this.mPlaceService = new PlaceService();
    }

    public List<PlaceModel> getData() {
        return placesList;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.place_row_layout, parent, false);

        final PlaceViewHolder holder = new PlaceViewHolder(itemView, new PlaceViewHolder.PlaceItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPlaceClicked(View caller, PlaceModel placeModel) {
                EventBus.getDefault().postSticky(placeModel);
                Intent intent = new Intent(mActivity, bg.uni_sofia.fmi.explore.PlaceActivity.class);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity,
                        Pair.create(itemView.findViewById(R.id.card_title), "title"),
                        Pair.create(itemView.findViewById(R.id.card_image), "image"));

                mActivity.startActivity(intent, options.toBundle());
            }

        });

        return holder;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        PlaceModel pm = placesList.get(position);
        holder.vPlaceTitle.setText(pm.getPlaceName());
        holder.vPlaceDesc.setText(String.valueOf(pm.getDistance()) + " m");
        holder.mPlaceModel = pm;
        Picasso.with(mActivity).load(R.drawable.no_place_image).fit().centerCrop().into(holder.vPlaceImageView);

        if(pm.getPhotos() != null) {
            for(PlacePhotoModel photo : pm.getPhotos()) {
                mPlaceService.loadPlacePhoto(mActivity, holder.vPlaceImageView, photo.getPhotoReference());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vPlaceTitle;
        TextView vPlaceDesc;
        ImageView vPlaceImageView;
        PlaceModel mPlaceModel;
        PlaceItemClickListener mListener;

        public PlaceViewHolder(View itemView, PlaceItemClickListener listener) {
            super(itemView);
            mListener = listener;
            vPlaceTitle = (TextView) itemView.findViewById(R.id.card_title);
            vPlaceDesc = (TextView) itemView.findViewById(R.id.card_description);
            vPlaceImageView = (ImageView) itemView.findViewById(R.id.card_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onPlaceClicked(v, mPlaceModel);
        }

        public static interface PlaceItemClickListener {
            public void onPlaceClicked(View caller, PlaceModel placeModel);
        }
    }
}
