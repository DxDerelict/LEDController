package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    boolean lightSwitchState = false;

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {

        Button lightSwitch = new Button("On/Off");

        lightSwitch.setLayoutX(250);
        lightSwitch.setLayoutY(250);
        lightSwitch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("lightSwitch clicked");
                lightSwitchState = !lightSwitchState;

                if(lightSwitchState) lightSwitch.setText("ON");
                    else lightSwitch.setText("OFF");


            }
        });

        Group root = new Group(lightSwitch);
        Scene scene = new Scene(root,600, 300);
        scene.setFill(Color.SNOW);

        primaryStage.setTitle("Arduino RGB Controller");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}


