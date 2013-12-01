package storage;

import java.util.List;

import lt.suiniad.etymologies.EtymologiesApp;
import storage.database.EtymologyController;

/**
 * Implementation of storage interface.
 * Created by mo on 12/1/13.
 */
public class Storage implements IStorage {

    private EtymologyController etymologyController =
            new EtymologyController(EtymologiesApp.getAppContext());
    /**
     * Given a list of strings stores them in some form of persistent
     * storage.
     *
     * @param data - everything that needs to be stored.
     */
    @Override
    public void storeData(List<String> data) {
        etymologyController.open();
        etymologyController.storeEtymology(data);
        etymologyController.close();
    }

    /**
     * Given a string key, returns a string response.
     *
     * @param key - search key
     * @return result of a search using a search key
     */
    @Override
    public String getDataByKey(String key) {
        etymologyController.open();
        String data = etymologyController.getEtymology(key);
        etymologyController.close();
        return data;
    }

    /**
     * Singleton class
     */
    private static Storage INSTANCE = new Storage();
    private Storage() {}
    public static Storage getInstance() {
        return INSTANCE;
    }
}
