package task1.Domain;

import task1.Application.LocalUtilities;
import task1.UI.UserInterface;

public class Laureate {

    private final String NAME;
    private final String BIRTH_DEATH;
    private final String NATIONS;
    private final String LANGUAGES;
    private final String GENRES;
    private final String CITATION;

    // constructor 
    public Laureate(String name, String birthDeath, String nations,
            String languages, String genres, String citation) {
        this.NAME = name;
        this.BIRTH_DEATH = birthDeath;
        this.NATIONS = nations;
        this.LANGUAGES = languages;
        this.GENRES = genres;
        this.CITATION = citation;
    }

    // getter methods
    
    public String getName() {
        return NAME;
    }
    
    public String getBirthDeath() {
        return BIRTH_DEATH;
    }

    public String getNations() {
        return NATIONS;
    }
    
    public String getLanguages() {
        return LANGUAGES;
    }
    
    public String getGenres() {
        return GENRES;
    }
    
    public String getCitation() {
        return CITATION;
    }

    public String[] getSeparatedBirthDeath() {
        return getBirthDeath().split("-");
    }

    public String[] getSeparatedLanguages() {
        return getLanguages().split(",");
    }

    public String[] getSeparatedGenres() {
        return getGenres().split(",");
    }

    // to check if a given string is a genre in the string
    public boolean hasGenre(String genre) {
        return getGenres().contains(genre);
    }

    // returns object's data in a row of a table format
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendNameAndDates(sb);
        appendLanguagesAndGenres(sb);
        appendCitation(sb);
        return sb.toString();
    }

    // methods used to form the row
    private void appendNameAndDates(StringBuilder sb) {
        sb.append("| ").append(String.format("%-40s", getName()));

        String[] separatedBirthDeath = getSeparatedBirthDeath();

        // append birth
        if (separatedBirthDeath[0].contains("b.")) {
            // remove "b." string
            sb.append("| ").append(String.format("%-4s", separatedBirthDeath[0].replace("b.", "").trim())).append(" | ");
        } else {
            sb.append("| ").append(String.format("%-4s",separatedBirthDeath[0].trim())).append(" | ");
        }

        // append death
        if (separatedBirthDeath.length == 2) { // append death
            sb.append(String.format("%-4s", separatedBirthDeath[1])).append(" | ");
        } else { // if no date
            sb.append("----").append(" | ");
        }
    }

    private void appendLanguagesAndGenres(StringBuilder sb) {
        // get languages and genres into arrays
        String[] separatedLanguages = getSeparatedLanguages();
        String[] separatedGenres = getSeparatedGenres();

        // get their length
        int languageLength = separatedLanguages.length;
        int genresLength = separatedGenres.length;

        // get the highest number of the lengths. the number of rows to be created
        int higherNumberOfRows = Math.max(languageLength, genresLength);

        // append the language and genre on index 0
        sb.append(String.format("%-30s", separatedLanguages[0])).append(" | ");
        sb.append(String.format("%-30s", separatedGenres[0])).append(" |\n");

        // append the languages and genres on the rest of the elements
        for (int i = 1; i < higherNumberOfRows; i++) {
            sb.append("| ").append(String.format("%-40s", ""));
            sb.append("| ").append("    ").append(" | ");
            sb.append("    ").append(" | ");

            // if i is a index of the language array
            if (languageLength > i) {
                sb.append(String.format("%-30s", separatedLanguages[i])).append(" | ");
            } else { // empty string if not
                sb.append(String.format("%-30s", "")).append(" | ");
            }

            // if i is a index of the genre array
            if (genresLength > i) {
                sb.append(String.format("%-30s", separatedGenres[i].trim())).append(" |\n");
            } else { // empty string if not
                sb.append(String.format("%-30s", "")).append(" |\n");
            }
        }
    }

    private void appendCitation(StringBuilder sb) {
        // append line separator
        sb.append(UserInterface.getLineSeparator(123));
        // first row
        sb.append(String.format("|%-56s%9s%56s|", "", "Citation:", "")).append("\n");
        // empty row
        sb.append(String.format("|%121s|", "")).append("\n");
        // append the citation, wrap the text if needed
        sb.append(LocalUtilities.textWrapper(getCitation(), 121)).append("\n");
        sb.append(UserInterface.getLineSeparator(123));
    }

}
