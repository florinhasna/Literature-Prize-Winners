package task1.DAO;

import java.io.File;
import java.util.ArrayList;
import task1.Domain.LiteraturePrize;

public class DataLoaderHandler {

    private final FileReader reader;
    private final DataProcessor dataProcessor = new DataProcessor();

    // default constructor
    public DataLoaderHandler() {
        String fileLocation = System.getProperty("user.dir");
        String dataPath = fileLocation + File.separator + "literature-prizes.txt";

        reader = new FileReader(new File(dataPath));
    }

    // constructor with a given file name
    public DataLoaderHandler(String fileName) {
        String fileLocation = System.getProperty("user.dir");
        String dataPath = fileLocation + File.separator + fileName;

        reader = new FileReader(new File(dataPath));
    }

    public FileReader getReader() {
        return reader;
    }

    public DataProcessor getDataProcessor() {
        return dataProcessor;
    }

    // initialise reading the file
    public void readFileInArray() throws Exception {
        this.getReader().loadData();
    }
    
    // process the data that was read from file
    public void startProcessingData() {
        this.getDataProcessor().processData(this.getReader().getDataArrays());
    }
    
    // get the data that was processed
    public ArrayList<LiteraturePrize> getProcessedData() {
        return this.getDataProcessor().getLiteratureAwards();
    }
}
