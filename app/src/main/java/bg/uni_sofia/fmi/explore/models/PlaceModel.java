package bg.uni_sofia.fmi.explore.models;

import android.location.Location;

import com.google.android.gms.location.places.Place;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eyanev on 5/15/15.
 */
public class PlaceModel {
    private String name;
    private String placeDesc;
    private String id;
    @SerializedName("place_id")
    private String placeId;

    private Geometry geometry;

    private int distance;

    private List<PlacePhotoModel> photos;

    public PlaceModel() {}
    public PlaceModel(String placeName) {
        this.name = placeName;
    }

    public String getPlaceName() {
        return name;
    }

    public void setPlaceName(String placeName) {
        this.name = placeName;
    }

    public String getPlaceDesc() {
        return placeDesc;
    }

    public void setPlaceDesc(String placeDesc) {
        this.placeDesc = placeDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<PlacePhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PlacePhotoModel> photos) {
        this.photos = photos;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
