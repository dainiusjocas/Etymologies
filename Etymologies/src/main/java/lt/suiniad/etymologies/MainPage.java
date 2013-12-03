package lt.suiniad.etymologies;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.ShareActionProvider;

/**
 * Main activity of the app.
 */
public class MainPage extends Activity {
    /*
     * My web view
     */
    private WebView mWebView;
    private FrameLayout webViewPlaceholder;

    private ShareActionProvider mShareActionProvider;

    // home page url, yeah it is a file
    private final static String home = "file:///android_asset/www/index.html";
    private final static String about = "file:///android_asset/www/about.html";
    // url which is currently being displayed, by default it points to home
    public static String toScreen = home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleIntent(getIntent());
        initUI();
    }

    /**
     * Handling of the search
     * @param intent
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            toScreen = constructSearchUrl(query);
        }
    }

    /**
     * Given a query string return a url to search in etymonline website.
     * @param query raw input
     * @return url for search
     */
    private String constructSearchUrl(String query) {
        String head = "http://www.etymonline.com/index.php?allowed_in_frame=0&search=";
        String modifiedQuery = query.trim().toLowerCase().replace(" ", "+");
        return head.concat(modifiedQuery);
    }

    /**
     * Does the job of putting webview onto the screen.
     */
    protected void initUI() {
        // Retrieve UI elements
        webViewPlaceholder = ((FrameLayout)findViewById(R.id.webViewPlaceholder));

        // Initialize the WebView if necessary
        if (mWebView == null)
        {
            // Create the webview
            mWebView = new WebView(this);
            mWebView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mWebView.setScrollbarFadingEnabled(true);
            mWebView.getSettings().setLoadsImagesAutomatically(true);

            // Load the URLs inside the WebView, not in the external web browser
            mWebView.setWebViewClient(new EtymologyWebViewClient());

            // Load a page
            mWebView.loadUrl(toScreen);
        }

        // Attach the WebView to its placeholder
        webViewPlaceholder.addView(mWebView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        if (mWebView != null)
        {
            // Remove the WebView from the old placeholder
            webViewPlaceholder.removeView(mWebView);
        }

        super.onConfigurationChanged(newConfig);

        // Load the layout resource for the new configuration
        setContentView(R.layout.activity_main);

        // Reinitialize the UI
        initUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of the WebView, for navigation when orientation of the device changes
//        mWebView.saveState(outState); // doesn't work for webview
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore the state of the WebView
        mWebView.restoreState(savedInstanceState);
    }

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        // Locate MenuItem with ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        if (mShareActionProvider != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, getCurrentDefinition());
            shareIntent.setType("text/plain");
            mShareActionProvider.setShareIntent(shareIntent);
        }

        return true;
    }

    /**
     * Provides current definition url for sharing
     * @return
     */
    private String getCurrentDefinition() {
        if (toScreen.equals(home)) {
            return "www.etymonline.com\nThis is a fantastic service!";
        }
        return toScreen;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                // do something with settings
                // TODO
                return true;
            case R.id.go_home:
                // show the home page
                mWebView.loadUrl(home);
                break;
            case R.id.history:
                // show search history
                // TODO
                break;
            case R.id.about:
                // show information about this application
                mWebView.loadUrl(about);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
