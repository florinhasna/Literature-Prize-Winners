package task1.Domain;

import java.util.ArrayList;
import task1.UI.UserInterface;

public class LiteraturePrize {

    private final int YEAR;
    private final ArrayList<Laureate> WINNERS = new ArrayList<>();

    public LiteraturePrize(int Y) {
        this.YEAR = Y;
    }

    // getter methods
    public int getYEAR() {
        return YEAR;
    }

    public ArrayList<Laureate> getWinners() {
        return WINNERS;
    }
    
    // add a laureate
    public void addWinner(String name, String birthDeath, String nations,
            String languages, String genres, String citation) {
        getWinners().add(new Laureate(name, birthDeath, nations, languages, genres, citation));
    }

    // toString method to print a table format holding the winners
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // print header of the table
        sb.append(UserInterface.getLineSeparator(123));
        sb.append(String.format("| %-39s | %-4s | %-4s | %-30s | %-30s |\n",
                "Winner(s)", "Born", "Died", "Language(s)", "Genre(s)"));
        sb.append(UserInterface.getLineSeparator(123));

        if (getWinners().isEmpty()) { // if not awarded
            sb.append(String.format("| %-39s | %-4s | %-4s | %-30s | %-30s |\n",
                    "NOT AWARDED", "----", "----", "-", "-"));
            sb.append(UserInterface.getLineSeparator(123));
        } else {
            // print each winner row
            getWinners().forEach(l -> {
                sb.append(l.toString());
            });
        }

        return sb.toString();
    }
}
