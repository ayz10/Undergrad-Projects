package bankgui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;


/**
 * This class runs the project and is used as a runner class
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class BankTellerMain extends Application {
    /**
     * This method creates the GUI by loading the fxml "MainView.fxml"
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("Project 3 - Vineel Reddy, Alexander Zhao");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method acts as a runner
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}