package bg.uni_sofia.fmi.explore.contracts;

import android.net.Uri;

import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

/**
 * Created by eyanev on 5/25/15.
 */
public class PlaceContract implements ProviGenBaseContract {

    @ContentUri
    public static final Uri CONTENT_URI = Uri.parse("content://com.explorer/users");

    @Column(Column.Type.TEXT)
    public static final String PLACE_ID = "extPlaceId";

    @Column(Column.Type.TEXT)
    public static final String PLACE_NAME = "name";
}
