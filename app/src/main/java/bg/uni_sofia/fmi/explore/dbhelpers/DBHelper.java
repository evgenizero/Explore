package bg.uni_sofia.fmi.explore.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tjeannin.provigen.ProviGenOpenHelper;

/**
 * Created by eyanev on 5/15/15.
 */
public class DBHelper extends ProviGenOpenHelper {
    private static final String DB_NAME = "exploreDB";
    private static final int DB_VERSION = 2;

    public DBHelper(Context context, Class[] contractClasses) {
        super(context, DB_NAME, null, DB_VERSION, contractClasses);
    }
}
