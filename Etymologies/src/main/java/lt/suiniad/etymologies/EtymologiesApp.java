package lt.suiniad.etymologies;

import android.app.Application;
import android.content.Context;

/**
 * This hack class is to have a context always available.
 * Created by mo on 12/1/13.
 */
public class EtymologiesApp extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        EtymologiesApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return EtymologiesApp.context;
    }

}
