package info.androidhive.simdocomo.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by web3 on 5/22/2016.
 */
public class Database extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DBName = "database_fdo";
    public static final String TABLE_USER_ID = "tbl_userid";
    public Database(Context context)
    {super(context, DBName, null, VERSION);}

    String create_userid_table = "CREATE TABLE IF NOT EXISTS "
            + TABLE_USER_ID
            + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, USER_NAME TEXT, AGENCY_CODE TEXT )";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_userid_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public long insertUserID(LoginDetails_model loginDetails) {
        long count = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("USER_ID", loginDetails.getUserId());
            values.put("USER_NAME", loginDetails.getUserName());
            values.put("AGENCY_CODE", loginDetails.getAgency_code());
            count = db.insert(TABLE_USER_ID, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<LoginDetails_model> getLoginDetails() {
        ArrayList<LoginDetails_model> received_data = new ArrayList<LoginDetails_model>();
        SQLiteDatabase sql = this.getWritableDatabase();
        String query = "select * from " + TABLE_USER_ID;
        Log.i("Database get query", query);
        Cursor cursor = sql.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                LoginDetails_model details = new LoginDetails_model();
                details.setUserId(cursor.getInt(1));
                details.setUserName(cursor.getString(2));
                details.setAgency_code(cursor.getString(3));
                received_data.add(details);
            } while (cursor.moveToNext());
        }
        return received_data;
    }

    // This method retrive userID from table
    public int getUserID() {
        int user_id = 0;
        SQLiteDatabase sql = getWritableDatabase();
        String qry = "SELECT USER_ID FROM " + TABLE_USER_ID;
        Cursor cursor = sql.rawQuery(qry, null);
        if (cursor.moveToFirst()) {
            do {
                user_id = cursor.getInt(0);
                break;
            } while (cursor.moveToNext());
        }
        return user_id;
    }

    //.....updated by vaibhav pote on 05/03/2016
    public void truncateUserIdTable() {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.execSQL(" delete from " + TABLE_USER_ID);
            database.execSQL("VACUUM");
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
