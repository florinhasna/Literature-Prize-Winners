package task1.DAO;

import task1.Domain.LiteraturePrize;
import java.util.ArrayList;

/* Class used to process the ArrayList of years created by a FileReader object.
The data is parsed to an ArrayList holding LiteraturePrize objects */
public class DataProcessor {

    private final ArrayList<LiteraturePrize> literatureAwards = new ArrayList<>();

    public DataProcessor() {
    }

    public ArrayList<LiteraturePrize> getLiteratureAwards() {
        return this.literatureAwards;
    }

    // process data of nested ArrayLists and add to literatureAwards
    public void processData(ArrayList<ArrayList<String>> data) {
        final int YEAR_INDEX = 0;
        
        for (ArrayList<String> aYear : data) {
            // initialise a new LiteraturePrize object using the year
            LiteraturePrize anAward = new LiteraturePrize(Integer.parseInt((String) aYear.get(YEAR_INDEX)));
            
            // case if there was no prize awarded
            if (aYear.size() == 2) {
                getLiteratureAwards().add(anAward); // add the award object to the array
                continue; // stop execution of the method
            }
            
            // remove the year element from the list
            aYear.remove(YEAR_INDEX);
            
            // iterate for every array until aYear ArrayList is empty
            while (!aYear.isEmpty()) {
                String firstLine = (String) aYear.get(0); // get first line
                String citation = (String) aYear.get(1); // get citation
                String genres = (String) aYear.get(2); // get genres

                // remove them from aYear ArrayList after retrieval
                aYear.remove(firstLine);
                aYear.remove(citation);
                aYear.remove(genres);

                // process first line, split the string
                String[] splitFirstLine = firstLine.split("\\|");
                
                // split the name at first bracket
                String[] splitName = splitFirstLine[0].split("\\(");
                
                // save the name final format
                String name = splitName[0].trim();
                
                // save the birth and death final format
                String birthDeath = splitName[1].replace(')', ' ').trim();
                
                // save nations
                String nations = splitFirstLine[1];
                
                // save languages
                String languages = splitFirstLine[2];

                // add the winner to the award object
                anAward.addWinner(name, birthDeath, nations, languages, genres, citation);
            }

            // add the award to the awards ArrayList
            getLiteratureAwards().add(anAward);
        }
    }
}
