package lt.suiniad.etymologies;

/**
 * Prepares html to send to a webview.
 * Created by mo on 12/2/13.
 */
public class ResponsePreparator {

    /**
     * Helper object that knows how extract definitions.
     */
    private DefinitionExtractor mDefinitionExtractor = DefinitionExtractor.getInstance();
    /**
     * Gets the custom header and the footer of the webpage.
     * Extracts the definitions from the original response website.
     * concatenates everything and returns.
     *
     * @param html html source code
     * @return string that represents HTML for the webview
     */
    public String prepareResponse(String html) {
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
                        "#dictionary .highlight { background-color: #ddd9ca; }\n" +
                        "ul li {\n" +
                        "display: inline ;\n" +
                        "padding: 0px 3px 0px 3px ;\n" +
                        "}\n" +
                        "</style>" +
                        "</head>" +
                        "<body><br/>";

        String footer = "</body></html>";

        // extract definitions
        String definitions = mDefinitionExtractor.extractDefinitions(html);

        return head.concat(definitions).concat(footer);
    }

    /**
     * Singleton class
     */
    private static ResponsePreparator INSTANCE =
            new ResponsePreparator();
    private ResponsePreparator() {}
    public static ResponsePreparator getInstance() {
        return INSTANCE;
    }
}
