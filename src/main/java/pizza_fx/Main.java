package pizza_fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //FXMLLoader -> załadowanie pliku .fxml i zwrcenie jako obiektu
        Parent root = FXMLLoader.load(getClass().getResource("/view/pizzaView.fxml"));
        //Utworzenie obiektu Scene wypełnionego widokiem aplikacji
        Scene scene = new Scene(root);
        // Ustawienie obiektu Scene w Stage (oknie aplikacji)
        primaryStage.setTitle("Pizza");
        primaryStage.setScene(scene);
        // Zatrzymanie okna aplikacji
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
