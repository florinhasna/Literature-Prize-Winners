package task1.Application;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LocalUtilities {

    // generic method to get different type of inputs, the argument is the type desired
    // throws exception if type not supported
    public static <T> T promptSelect(Class<T> type) throws InputMismatchException {
        Scanner input = new Scanner(System.in);
        System.out.print(" > ");

        // user inputs an integer
        if (type.equals(Integer.class)) {
            try {
                return type.cast(input.nextInt());
            } catch (InputMismatchException e) {
                throw new InputMismatchException("Integer required");
            }
        } else {
            return type.cast(input.nextLine().trim());
        }
    }

    // check the validity of a given year
    public static boolean isYearValid(int aYear) {
        final int MIN_YEAR = 1901;
        final int MAX_YEAR = 2022;

        if (aYear < MIN_YEAR || aYear > MAX_YEAR) {
            System.out.println("The year must be in the range 1901-2022, inclusively...\n");
            return true;
        }

        return false;
    }

    // test the input of end year
    public static boolean testEndYear(int startYear, int endYear) {
        // test the year if within the accepted range
        if (isYearValid(endYear)) {
            return true;
        }

        if (endYear < startYear) {
            System.out.println("The end year must not be lower than the start year...\n");
            return true;
        }

        return false;
    }

    // method used to wrap a text, used to print citation in the table of option 2
    public static String textWrapper(String aText, int totalWidth) {
        StringBuilder wrappedText = new StringBuilder();
        int maxWidth = 75;

        String[] words = aText.split(" ");
        StringBuilder aLine = new StringBuilder();
        
        // declare to integere for padding
        int paddingLeft;
        int paddingRight;
        
        //loop the array of words
        for (String word : words) {
            // check if exceeds the maximum width
            if (aLine.length() + word.length() > maxWidth) {
                // get the padding left by deducting the length of the line, dividing by two
                paddingLeft = (totalWidth - aLine.length()) / 2;
                // give the same value to padding right
                paddingRight = paddingLeft;
                
                // check if it fill the whole row
                if (paddingLeft + paddingRight + aLine.toString().trim().length() != totalWidth) {
                    // adjust the padding right to fill it if not
                    paddingRight += totalWidth - (paddingLeft + paddingRight + aLine.toString().trim().length());
                }

                // append the line to the wrapped text
                wrappedText.append(String.format("|%-" + paddingLeft + "s%s%-" + paddingRight + "s|\n", "", aLine.toString().trim(), ""));
                // overwrite stringbuilder with the word to go on the next line
                aLine = new StringBuilder(word + " ");
            } else {
                // append the word with a space
                aLine.append(word).append(" ");
            }
        }
        
        // same with the operation in the for loop, only this is applied to last row
        paddingLeft = (totalWidth - aLine.length()) / 2;
        paddingRight = paddingLeft;

        if (paddingLeft + paddingRight + aLine.toString().trim().length() != totalWidth) {
            paddingRight += totalWidth - (paddingLeft + paddingRight + aLine.toString().trim().length());
        }
        
        // append the last row
        wrappedText.append(String.format("|%-" + paddingLeft + "s%s%-" + paddingRight + "s|", "", aLine.toString().trim(), ""));
        
        // return the wrapped text
        return wrappedText.toString();
    }
}
