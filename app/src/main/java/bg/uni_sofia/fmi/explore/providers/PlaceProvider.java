package bg.uni_sofia.fmi.explore.providers;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.tjeannin.provigen.ProviGenProvider;

import bg.uni_sofia.fmi.explore.contracts.PlaceContract;
import bg.uni_sofia.fmi.explore.dbhelpers.DBHelper;

/**
 * Created by eyanev on 5/25/15.
 */
public class PlaceProvider extends ProviGenProvider {
    private static Class[] contracts = new Class[]{PlaceContract.class};

    @Override
    public SQLiteOpenHelper openHelper(Context context) {
        return new DBHelper(context, contracts);
    }

    @Override
    public Class[] contractClasses() {
        return contracts;
    }
}
