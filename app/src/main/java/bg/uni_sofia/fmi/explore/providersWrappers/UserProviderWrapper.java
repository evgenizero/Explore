package bg.uni_sofia.fmi.explore.providersWrappers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import bg.uni_sofia.fmi.explore.ExploreApplication;
import bg.uni_sofia.fmi.explore.LoginActivity;
import bg.uni_sofia.fmi.explore.contracts.UserContract;
import bg.uni_sofia.fmi.explore.models.UserModel;

/**
 * Created by eyanev on 5/15/15.
 */
public class UserProviderWrapper {
    public static boolean insertSingleUser(Context context, UserModel user) {
        ContentValues values = new ContentValues();
        values.put(UserContract.USER_NAME, user.getUsername());
        values.put(UserContract.PASSWORD, user.getPassword());
        return context.getContentResolver().insert(UserContract.CONTENT_URI, values) != null;
    }

    public static UserModel getUserByCredentials(Context context, String username, String password) {
        Cursor cursor = context.getContentResolver().query(UserContract.CONTENT_URI,
                new String[]{UserContract.USER_NAME},
                UserContract.USER_NAME + " = ? AND " + UserContract.PASSWORD + " = ?",
                new String[]{username, password}, null);

        if(cursor != null && cursor.moveToFirst()) {
            return new UserModel(username, password);
        }

        return null;
    }

    public static void setCurrentUser(Context context, UserModel userModel) {
        ContentValues values = new ContentValues();
        values.put(UserContract.IS_CURRENT, 1);
        context.getContentResolver().update(UserContract.CONTENT_URI, values,
                UserContract.USER_NAME + " = ? AND " + UserContract.PASSWORD + " = ?",
                new String[]{userModel.getUsername(), userModel.getPassword()});
    }

    public static UserModel getCurrentUser(Context context) {
        Cursor cursor = context.getContentResolver().query(UserContract.CONTENT_URI,
                new String[]{UserContract.USER_NAME},
                UserContract.IS_CURRENT + " = ? ",
                new String[]{"1"}, null);

        if(cursor != null && cursor.moveToFirst()) {
            return new UserModel(cursor.getString(0));
        }

        return null;
    }
}
