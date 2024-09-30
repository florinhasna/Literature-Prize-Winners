package task1.Application;

import task1.Domain.*;
import task1.DAO.DataLoaderHandler;
import task1.UI.UserInterface;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;

public class Manager {
    private final UserInterface UI;
    private DataLoaderHandler loader;
    private ArrayList<LiteraturePrize> awards;

    public Manager(DataLoaderHandler d, UserInterface ui) {
        loader = d;
        UI = ui;
    }

    public UserInterface getUI() {
        return UI;
    }

    public DataLoaderHandler getLoader() {
        return loader;
    }

    public ArrayList<LiteraturePrize> getAwards() {
        return awards;
    }

    public void setAwards(ArrayList<LiteraturePrize> a) {
        this.awards = a;
    }

    public void setData(DataLoaderHandler d) {
        loader = d;
    }

    // function is loading the loader from the file, if name is altered prompts the user to enter filename
    public void startDAO() {
        while (true) { // loop until the file was readed
            try {
                // read the file into an array
                this.getLoader().readFileInArray();
                break; // break the loop when the file reading execution was successful
            } catch (FileNotFoundException e) {
                // if file opening is unsuccessful, set a new loader object with user input filename
                this.setData(new DataLoaderHandler(this.getUI().getNewFileNamePrompt()));
            } catch (Exception e) {
                // case where there are no read rights
                this.getUI().noReadRightsMessage(); // print message
                System.exit(1); // exit
            }
        }
        
        this.getLoader().startProcessingData();
        this.setAwards(this.getLoader().getProcessedData());
    }

    public void runProgram() {
        int request;
        final int EXIT_FLAG = 0;

        do {
            this.getUI().loadMenu();

            request = this.getUserRequest();

            this.processRequest(request);
        } while (request != EXIT_FLAG);
    }

    // initialises the program by printing the menu, prompts the user to select option from the list
    public int getUserRequest() {
        int choice = -1;

        // get user's input
        try {
            choice = this.getUI().getMenuChoice();
        } catch (InputMismatchException e) {
            this.getUI().errorMessage();
        }

        return choice;
    }

    public void processRequest(int request) {
        // process the input
        switch (request) {
            case 1: // list
                list();
                break;
            case 2: // select
                select();
                break;
            case 3: // search
                search();
                break;
            default: // when the number is not one of the accepted ones
                this.getUI().checkChoice(request); // checks if exits or input is incorrect
            }
    }

    // method to list winners in a table format, in a range of years
    public void list() {
        int startYear = -1;
        int endYear = -1;

        do {
            try {
                // read input from user for startYear
                startYear = this.getUI().getStartYearInput();

                // check if chooses to cancel the action
                if (startYear == 0) {
                    return; // stop execution of the method
                }
            } catch (InputMismatchException e) {
                this.getUI().errorMessage(); // print error message
            }
            // loops until the right input is given, or enter's 0 to go back
        } while (LocalUtilities.isYearValid(startYear));

        do {
            try {
                // read input from user for endYear
                endYear = this.getUI().getEndYearInput();

                // check if chooses to cancel the action
                if (endYear == 0) {
                    return; // stop execution of the method
                }
            } catch (InputMismatchException e) {
                this.getUI().errorMessage(); // print error message
            }
            // loops until the right input is given, or enter's 0 to go back
        } while (LocalUtilities.testEndYear(startYear, endYear));

        int rowLength = 85;
        // print header of the table
        this.getUI().printHeaderListTable(rowLength);

        // print rows
        for (LiteraturePrize lp : this.getAwards()) {
            if (lp.getYEAR() >= startYear && lp.getYEAR() <= endYear) {
                this.getUI().printRowInList(lp);
            }
        }

        System.out.println(UserInterface.getLineSeparator(rowLength));
    }

    // method to print a year's winner(s)
    public void select() {
        int year = -1;
        // get a year input from user
        do {
            try {
                year = UI.getYearInput();

                if (year == 0) // check if the choice is to go back
                {
                    return; // stop execution of the method
                }
            } catch (InputMismatchException e) {
                this.getUI().errorMessage(); // print error message for wrong inputs
            }
            // check validity of the input, loop until exits or gives right input
        } while (LocalUtilities.isYearValid(year));

        for (LiteraturePrize lp : this.getAwards()) {
            if (lp.getYEAR() == year) // find the object matching the given year
            {
                System.out.println(lp.toString());
            }
        }
    }

    public void search() {
        String genreInput = null;

        try { // get user input
            genreInput = this.getUI().getGenreInput().toLowerCase().trim();

            // if chooses to go back
            if (genreInput.equals("0")) {
                return;
            }
        } catch (InputMismatchException e) {
            this.getUI().errorMessage(); // display error message 
        }

        ArrayList<Laureate> laureates = new ArrayList<>();
        // check every year
        for (LiteraturePrize lp : this.getAwards()) {
            // check every laureate
            for (Laureate l : lp.getWinners()) {
                // check if the laureate includes the genre
                if (l.hasGenre(genreInput)) {
                    laureates.add(l);
                }
            }
        }

        // sort the list by name
        Collections.sort(laureates, Comparator.comparing(Laureate::getName));

        // if the search does not match anything
        if (laureates.isEmpty()) {
            this.getUI().noMatchForSearch(genreInput);
            return;
        }

        // print the header of the table
        this.getUI().printSearchHeader();

        for (Laureate l : laureates) {
            // get the year of the laureate
            for (LiteraturePrize lp : this.getAwards()) {
                // to check if the LiteraturePrize contains the laureate
                if (lp.getWinners().contains(l)) // print line of laureate and year
                {
                    this.getUI().printSearchLine(l, lp.getYEAR(), genreInput);
                }
            }
        }

        System.out.println();
    }
}
