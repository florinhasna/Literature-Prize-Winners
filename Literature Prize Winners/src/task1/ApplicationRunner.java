package task1;

import task1.Application.Manager;
import task1.DAO.DataLoaderHandler;
import task1.UI.UserInterface;

public class ApplicationRunner {

    public static void main(String[] args) {
        DataLoaderHandler dataLoader = new DataLoaderHandler();
        UserInterface UI = new UserInterface();
        Manager controller = new Manager(dataLoader, UI);

        // read the file in and process data
        controller.startDAO();
        
        // run the program
        controller.runProgram();
    }
}
