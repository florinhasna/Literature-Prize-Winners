package task1.UI;

import java.util.InputMismatchException;
import task1.Domain.Laureate;
import task1.Domain.LiteraturePrize;
import task1.Application.LocalUtilities;

public class UserInterface {

    public void loadMenu() {
        System.out.println("----------------------");
        System.out.println("Literature prize menu");
        System.out.println("----------------------");
        System.out.println("List ................1");
        System.out.println("Select ..............2");
        System.out.println("Search...............3");
        System.out.println("Exit.................0");
        System.out.println("----------------------");
    }
    
    
    public void checkChoice(int choice) {
        if (choice == 0) {
            System.out.println("Exiting program...");
        } else {
            System.out.println("Enter the right label number, (e.g. enter 3 to search)...\n");
        }
    }

    public int getMenuChoice() {
        System.out.print("\nEnter choice");
        
        return LocalUtilities.promptSelect(Integer.class);
    }

    public int getStartYearInput() {
        System.out.print("Enter start year, or \"0\" to go back");

        return LocalUtilities.promptSelect(Integer.class);
    }

    public int getEndYearInput() {
        System.out.print("Enter end year, or \"0\" to go back");

        return LocalUtilities.promptSelect(Integer.class);
    }

    public int getYearInput() {
        System.out.print("Enter year, or \"0\" to go back");

        return LocalUtilities.promptSelect(Integer.class);
    }

    public String getGenreInput() {
        try {
            System.out.print("Enter search term for writing genre, or \"0\" to go back");
            return LocalUtilities.promptSelect(String.class);
        } catch (InputMismatchException e) {
            throw new InputMismatchException("Error");
        }
    }

    public void printHeaderListTable(int rowLength) {
        System.out.print(getLineSeparator(rowLength));
        System.out.printf("|%6s|%-75s |\n", " YEAR ", " Prize winners (and associated nations)");
        System.out.print(getLineSeparator(rowLength));
    }

    public void printRowInList(LiteraturePrize lp) {
        if (lp.getWinners().isEmpty()) {
            System.out.printf("| %4d |%-75s |\n", lp.getYEAR(), " NOT AWARDED");
        } else {
            StringBuilder names = new StringBuilder();
            lp.getWinners().stream().peek(laureate -> {
                names.append(laureate.getName()).append(" [").append(laureate.getNations()).append("]");
                // if is not last, add a coma
            }).filter(l -> (!(lp.getWinners().indexOf(l) == lp.getWinners().size() - 1))).forEachOrdered(_item -> names.append(", "));
            System.out.printf("| %4d | %-75s|\n", lp.getYEAR(), names);
        }
    }

    public void printSearchHeader() {
        System.out.print(getLineSeparator(115));
        System.out.printf("| %-30s | %-71s | %-4s |\n", "Name", "Genres", "Year");
        System.out.print(getLineSeparator(115));
    }

    public void printSearchLine(Laureate l, int year, String genre) {
        System.out.printf("| %-30s | %-71s | %-4d |\n", l.getName(), l.getGenres().replace(genre, genre.toUpperCase()), year);
        System.out.print(getLineSeparator(115));
    }

    public String getNewFileNamePrompt() {
        System.out.println("The file was not found, name might have been altered...");
        System.out.println("Must be a text file (adding the extension in the name is not necessary)...");
        System.out.println("Please enter a new file name");

        try {
            return LocalUtilities.promptSelect(String.class) + ".txt";
        } catch (InputMismatchException e) {
            System.out.println("Wrong input...");
            return null;
        }
    }

    public void noReadRightsMessage() {
        System.out.println("The user does not have read rights to the file");
        System.out.println("Exiting...");
    }

    public void errorMessage() {
        System.out.println("An error has occured while processing the given input, try again...");
    }
    
    public void noMatchForSearch(String input) {
        System.out.println("\nNothing came up for " + "\"" + input + "\"\n");
    }

    // print a line separator
    public static StringBuilder getLineSeparator(int rowLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowLength; i++) {
            sb.append("-");
        }
        sb.append("\n");
        return sb;
    }
}
