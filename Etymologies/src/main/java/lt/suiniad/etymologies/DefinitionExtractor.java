package lt.suiniad.etymologies;

/**
 * This class knows how to get definitions from the
 * etymonline website html source.
 *
 * Created by mo on 11/30/13.
 */
public class DefinitionExtractor {
    /**
     * Extracts text that is between start and end markers.
     * @param etymOnlineResultPage String that is html
     * @return html snippets with definitions
     */
    public String extractDefinitions(String etymOnlineResultPage) {
        // known strings that surround definitions
        String startMarker = "<div id=\"dictionary\">";
        String endMarker = "<!-- DICTIONARY -->";

        int start = etymOnlineResultPage.indexOf(startMarker);
        int end = etymOnlineResultPage.indexOf(endMarker);

        if ((-1 != start) && (-1 != end)) {
            return etymOnlineResultPage.substring(start, end);
        }
        return etymOnlineResultPage;
    }

    /**
     * Singleton class
     */
    private static DefinitionExtractor INSTANCE =
            new DefinitionExtractor();
    private DefinitionExtractor() {}
    public static DefinitionExtractor getInstance() {
        return INSTANCE;
    }
}
