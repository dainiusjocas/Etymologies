package storage.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Controls the interaction with the etymologies table.
 * Created by mo on 12/1/13.
 */
public class EtymologyController {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    private String[] allColumns = {
            SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_SEARCH_URL,
            SQLiteHelper.COLUMN_TERM_URL,
            SQLiteHelper.COLUMN_DEFINITIONS
    };

    private String[] definitions = {SQLiteHelper.COLUMN_DEFINITIONS};

    public EtymologyController(Context context) {
        dbHelper = new SQLiteHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * List will always be a pair: url and some html.
     *
     * @param entry url and some html
     * @return long which is ID of insertion to say that everything is ok or not so ok
     */
    public long storeEtymology(List<String> entry) {
        ContentValues values = new ContentValues();
        String url = entry.get(0);
        String definitions = entry.get(1);

        String searchUrl = makeSearchUrl(url);
        String termUrl = makeTermUrl(url);

        values.put(SQLiteHelper.COLUMN_SEARCH_URL, searchUrl);
        values.put(SQLiteHelper.COLUMN_TERM_URL, termUrl);
        values.put(SQLiteHelper.COLUMN_DEFINITIONS, definitions);

        return database.insert(SQLiteHelper.TABLE_ETYMOLOGIES, null, values);
    }

    /**
     * Given an url checks if it is a term url and returns it.
     * if not, then transforms it to a term url.
     *
     * @param url
     * @return
     */
    private String makeTermUrl(String url) {
        if (url.contains("?term=")) {
            return url;
        } else {
            String head = "http://www.etymonline.com/index.php?term=";
            String tail = "allowed_in_frame=0";
            String term = extractTerm(url);
            return head.concat(term).concat(tail);
        }
    }

    /**
     * Given an url checks if it is a search url and returns it.
     * if not, then transforms it to a search url.
     *
     * @param url
     * @return
     */
    private String makeSearchUrl(String url) {
        if (url.contains("&search=")) {
            return url;
        } else {
            String head = "http://www.etymonline.com/index.php?allowed_in_frame=0&search=";
            String tail = "";
            String term = extractTerm(url);
            return head.concat(term).concat(tail);
        }
    }

    /**
     * Given an url, extracts the term for which this url was constructed.
     *
     * @param url url of etymonline.com
     * @return term
     */
    private String extractTerm(String url) {
        // extract from search url
        if (url.contains("&search=")) {
            String temp = url.split("&search=")[1];
            temp = temp.split("&")[0];
            return temp;
        }
        // if not search
        if (url.contains("?term=")) {
            String temp = url.split("\\?term=")[1];
            temp = temp.split("&")[0];
            return temp;
        }
        return "";
    }

    /**
     * Queries the database using a given url. If there is no DB entry with
     * such a url, then return null;
     *
     * @param url
     * @return
     */
    public String getEtymology(String url) {
        String whereClause = SQLiteHelper.COLUMN_SEARCH_URL + " = ? OR " + SQLiteHelper.COLUMN_TERM_URL + " = ?";
        String[] whereArgs = {url, url};
        Cursor cursor = database.query(
                SQLiteHelper.TABLE_ETYMOLOGIES,
                definitions,
                whereClause,
                whereArgs,
                null, null, null);
        if (cursor.moveToFirst()) {
            String data = cursor.getString(0); // i'm interested only in the fist string
            cursor.close();
            return data;
        }
        cursor.close();
        return null;
    }

    // http://www.etymonline.com/index.php?allowed_in_frame=0&search=google
    // http://www.etymonline.com/index.php?term=google&allowed_in_frame=0
}
