
package finalproject;

/**
 *
 * @author dtorallo
 */

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;


public class CustomerCostScreen extends Application {
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void start(Stage primaryStage) {
        
        String status;
        if(LoginScreen.pointsIn >= 1000) {
            status = "Gold";
        } else {
            status = "Silver";
        }

        Label totalCost = new Label("Total Cost: $" + df.format(CustomerScreen.totalCost));
        Label using = new Label("Points: " + LoginScreen.pointsIn + ", Status: " + status);


        Button btLo = new Button();
        btLo.setText("Logout");
        btLo.setOnAction(event -> {
            Platform.runLater(() -> {
            new LoginScreen().start(primaryStage);
            });
        });

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(totalCost, using, btLo);
        root.setSpacing(15);

        
        Scene scene = new Scene(root, 500, 350);
        primaryStage.setTitle("Bookstore App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}