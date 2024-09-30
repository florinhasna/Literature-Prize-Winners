package task1.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/* Class designed to read the data of the file given in its constructor, all data
is parsed into nested ArrayList of String class. Each ArrayList inside dataArrays
holds the Laureate winners of a year. */
public class FileReader {
    private final File DATA_FILE;
    private final ArrayList<ArrayList<String>> dataArrays = new ArrayList<>();
    
    public FileReader(File aFile){
        this.DATA_FILE = aFile;
    }
    
    public File getDataFile() {
        return DATA_FILE;
    }

    public ArrayList<ArrayList<String>> getDataArrays() {
        return dataArrays;
    }
    
    // method to load data
    public void loadData() throws Exception {
        try {
            if (checkFile()) { // check if the file can be read
                startReading(); // read the file
            } else { // case where file was not found
                throw new FileNotFoundException("Unable to locate the file");
            }
        } catch (SecurityException e) { // catch security exception
            throw new SecurityException("Unable to read");
        }
    }

    // check if the file can be read
    private boolean checkFile() throws SecurityException {
        try {
            return getDataFile().canRead();
        } catch (SecurityException e) { // catch exception raised by no read rights
            throw new SecurityException("Unable to read"); // throw the exception
        }
    }

    // start reading the file
    private void startReading() throws FileNotFoundException {
        try {
            try (Scanner reader = new Scanner(getDataFile())) {
                // initialise an arraylist to store data for a year
                ArrayList<String> data = new ArrayList<>();
                while (reader.hasNext()) {
                    String aLine = reader.nextLine().trim(); // read data from file by line
                    
                    if (isDataSeparator(aLine)) { // check if the line is a separator between years
                        // add the list of data for a year to DataArrays
                        this.getDataArrays().add(new ArrayList<>(data));
                        // empty the data list for next iteration
                        data.clear();
                    } else {
                        // add the line to the data ArrayList
                        data.add(aLine); 
                    }
                }                
            }
        } catch (FileNotFoundException ex) { // if file not found
            throw new FileNotFoundException("Unable to locate the file");
        }
    }

    // method to check if a line is separator between years
    private boolean isDataSeparator(String aLineOfData) {
        final String LINE_SEPARATOR = "-----";
        return aLineOfData.equals(LINE_SEPARATOR);
    }
}
