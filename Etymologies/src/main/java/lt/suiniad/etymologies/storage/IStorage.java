package lt.suiniad.etymologies.storage;

import java.util.List;

/**
 * Defines what lt.suiniad.etymologies.storage should provide for the app.
 * Created by mo on 12/1/13.
 */
public interface IStorage {

    /**
     * Given a list of strings stores them in some form of persistent
     * lt.suiniad.etymologies.storage.
     *
     * @param data - everything that needs to be stored.
     */
    void storeData(List<String> data);

    /**
     * Given a string key, returns a string response.
     *
     * @param key - search key
     * @return result of a search using a search key
     */
    String getDataByKey(String key);
}
