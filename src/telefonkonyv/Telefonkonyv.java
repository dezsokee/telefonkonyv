package telefonkonyv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Telefonkonyv extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Telefokönyv");
        stage.setWidth(625);
        stage.setHeight(690);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
