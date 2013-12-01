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
    private DefinitionExtractor mDefinitionExtractor = DefinitionExtractor.getInstance();

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
     * put that input stream into webresource responce,
     * and return for the webview.
     * @param view a current WebView
     * @param url web request url
     * @return either null of prepared WebResourceResponce object
     */
    public WebResourceResponse shouldInterceptRequest (WebView view, String url){
        // if not a html resource was requested then return null
        if (!url.contains("index.php?")) {
            return null;
        }
        String html = getHtml(url);

        // do modifications to the html.
        String modifiedHtml = prepareHtml(html);

        // convert String into InputStream
        InputStream is = new ByteArrayInputStream(modifiedHtml.getBytes());

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
    private String prepareHtml(String html) {
        String head =
                "<html>" +
                        "<head>" +
                        "<style>" +
                        "body {\n" +
                        "   background-color: #700020;\n" +
                        "   color: #000;\n" +
                        "   font-family: Georgia, Garamond, Times New Roman, Times, serif;\n" +
                        "   font-size: 12pt;\n" +
                        "   margin: 0;\n" +
                        "}\n" +
                        "    #dictionary {\n" +
                        "   background-color: #fffbec;\n" +
                        "   padding-top: 0.5em;\n" +
                        "   border: 1px solid #000;\n" +
                        "   margin-bottom: 1em;\n" +
                        "}\n" +
                        "#dictionary dd, #dictionary dt {\n" +
                        "   background-color: #fffbec;\n" +
                        "   margin-left: 0;\n" +
                        "   padding: 0 0.5em 0;\n" +
                        "}\n" +
                        "#dictionary dd { padding-bottom: 0.5em; }\n" +
                        "#dictionary .highlight { background-color: #ddd9ca; }" +
                        "</style>" +
                "</head>" +
                "<body><br/>";

        String search = "<form action=\"http://www.etymonline.com/index.php\" method=\"get\">\n" +
                "            <input type=\"hidden\" name=\"allowed_in_frame\" value=\"0\">\n" +
                "            <div id=\"search\" style=\"text-align:center;\">\n" +
                "                <input type=\"text\" name=\"search\" value=\"\" maxlength=\"255\" class=\"initial_focus\" style=\"height:40px; width:200px\">\n" +
                "                <br/><br/>\n" +
                "                <input type=\"submit\" value=\"SEARCH\" style=\"height:50px; width:200px\">\n" +
                "            </div> <!-- SEARCH -->\n" +
                "        </form>";
        String footer = "</body></html>";

        // extract definitions
        String definitions = mDefinitionExtractor.extractDefinitions(html);

        return head.concat(search).concat(definitions).concat(footer);
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
