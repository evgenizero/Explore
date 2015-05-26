package bg.uni_sofia.fmi.explore.contracts;

import android.net.Uri;

import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

/**
 * Created by eyanev on 5/14/15.
 */
public class UserContract implements ProviGenBaseContract {
    @Column(Column.Type.TEXT)
    public static final String USER_NAME = "username";

    @Column(Column.Type.TEXT)
    public static final String PASSWORD = "password";

    @Column(Column.Type.BLOB)
    public static final String PROFILE_IMAGE = "profileImage";

    @Column(Column.Type.INTEGER)
    public static final String IS_CURRENT = "isCurrent";

    @ContentUri
    public static final Uri CONTENT_URI = Uri.parse("content://com.explorer/users");
}
