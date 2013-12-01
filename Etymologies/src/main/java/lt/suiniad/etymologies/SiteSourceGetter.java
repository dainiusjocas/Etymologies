package lt.suiniad.etymologies;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import storage.IStorage;
import storage.Storage;

/**
 * This class gets the html behind a url.
 * Created by mo on 11/30/13.
 */
public class SiteSourceGetter {

    IStorage storage = Storage.getInstance();
    /**
     * Given a url returns the html of the website.
     * If some problems occurs during the reading, empty string is returned.
     *
     * @param url website address
     * @return html source of the website
     */
    public String getWebsiteSource(String url) {
        String resString = "";
        // get url from storage
        String html = storage.getDataByKey(url);
        // check if we already have this url in the db, null means not
        if (null == html) {
            HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
            HttpGet httpget = new HttpGet(url); // Set the action you want to do

            try {
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent(); // Create an InputStream with the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");  // Read line by line
                }

                resString = sb.toString(); // Result is here

                is.close(); // Close the stream

                List<String> data = new ArrayList<String>();
                data.add(url);
                data.add(resString);
                storage.storeData(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return html;
        }

        return resString;
    }

    /*
     * Singleton class
     */
    private static SiteSourceGetter INSTANCE = new SiteSourceGetter();
    private SiteSourceGetter() {}
    public static SiteSourceGetter getInstance() {
        return INSTANCE;
    }
}
