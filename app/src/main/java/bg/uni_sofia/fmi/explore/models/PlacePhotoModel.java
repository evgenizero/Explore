package bg.uni_sofia.fmi.explore.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eyanev on 5/18/15.
 */
public class PlacePhotoModel {

    @SerializedName("photo_reference")
    private String photoReference;

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
