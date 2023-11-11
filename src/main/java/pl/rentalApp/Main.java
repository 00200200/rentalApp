package pl.rentalApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.rentalApp.controllers.MainAppController;
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Wybierz użytkownika");
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/fxml/MainApp.fxml"));
        VBox mainPane = loader.load();

        MainAppController mainAppController = loader.getController();
        if(mainAppController != null){
            mainAppController.setPrimaryStage(stage);

        }else{
            throw new Error("mainAppController is Null");
        }
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();
        stage.toFront();

    }
}