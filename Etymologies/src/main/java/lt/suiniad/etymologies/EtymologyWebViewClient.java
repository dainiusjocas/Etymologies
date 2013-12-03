package lt.suiniad.etymologies;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This class deals with process flow of the web view.
 * Also it implements this hacky process of intercepting the
 * page request.
 * Created by mo on 11/30/13.
 */
public class EtymologyWebViewClient extends WebViewClient {

    /**
     * Helper objects
     */
    private SiteSourceGetter mSiteSourceGetter = SiteSourceGetter.getInstance();
    private ResponsePreparator mResponsePreparator = ResponsePreparator.getInstance();

    @Override
    /**
     * this method handles events when a user initiated url loading
     * is requested.
     * I want every request in the same domain to be handled by the
     * webview, while requests to other domains must be dealt with
     * a default android webrowser.
     *
     */
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // check if the request is for the same host
        if(Uri.parse(url).getHost().endsWith("etymonline.com")) {
            return false;
        }

        // if not intiate the start of the browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }

    /**
     * This method intercepts requests made by webview.
     * What I want to do here is get a website's html, replace certain parts,
     * put modified html into the InputStream,
     * put that input stream into webresource response,
     * and return for the webview.
     * @param view a current WebView
     * @param url web request url
     * @return either null of prepared WebResourceResponse object
     */
    public WebResourceResponse shouldInterceptRequest (WebView view, String url){
        // if not a html resource was requested then return null
        if (!url.contains("index.php?")) {
            return null;
        }
        String html = getHtml(url);

        // do modifications to the html.
        String preparedHtml = prepareResponse(html);
        MainPage.toScreen = url;

        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(preparedHtml.getBytes());

        // create WebResourceResponse object
        return new WebResourceResponse("text/html", "UTF-8", is);
    }


    /**
     * Gets the custom header and the footer of the webpage.
     * Extracts the definitions from the original response website.
     * concatenates everything and returns.
     *
     * @param html html source code
     * @return string that represents HTML for the webview
     */
    private String prepareResponse(String html) {
        // forward call
        return mResponsePreparator.prepareResponse(html);
    }

    /**MyApplication
     * Get html from the provided url.
     * @param url web url
     * @return string that is html source code
     */
    private String getHtml(String url) {
        return mSiteSourceGetter.getWebsiteSource(url);
    }


}
