package lt.suiniad.etymologies.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class is responsible for management of the database instance
 * of Etymologies app.
 *
 * Created by mo on 12/1/13.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    /**
     * General database parameters.
     */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "etymologiesdb";


    /**
     * table parameters.
     */
    public static final String TABLE_ETYMOLOGIES = "etymologies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SEARCH_URL = "searchurl";
    public static final String COLUMN_TERM_URL = "termurl";
    public static final String COLUMN_DEFINITIONS = "data";

    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_ETYMOLOGIES + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_SEARCH_URL + " text not null, "
                    + COLUMN_TERM_URL + " text not null, "
                    + COLUMN_DEFINITIONS + ");";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // do application logging
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        // do actual upgrade
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ETYMOLOGIES);
        onCreate(sqLiteDatabase);
    }
}
