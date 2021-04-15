package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    boolean lightSwitchState = false;

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage stage) throws Exception {

        Button lightSwitch = new Button("On/Off");

        Label redVal = new Label("red Val");
        Label blueVal = new Label("blue Val");
        Label greenVal =  new Label("green Val");

        Slider redSlider   = new Slider(0,255,0);
        redSlider.setShowTickLabels(true);
        redSlider.setLayoutY(10);

        Slider blueSlider  = new Slider(0, 255, 0);
        blueSlider.setShowTickLabels(true);
        blueSlider.setLayoutY(40);
        blueSlider.setShowTickLabels(true);

        Slider greenSlider = new Slider(0,255,0);
        greenSlider.setShowTickLabels(true);
        greenSlider.setLayoutY(70);
        greenSlider.setShowTickLabels(true);

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

//        Group root = new Group();
//
//        root.getChildren().addAll(redSlider, blueSlider, greenSlider, lightSwitch);

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(redSlider, 0,0);
        gridPane.add(blueSlider, 0 , 1);
        gridPane.add(greenSlider, 0 ,2);
        gridPane.add(redVal, 1,0);
        gridPane.add(blueVal, 1,1);
        gridPane.add(greenVal,1, 2);
        gridPane.add(lightSwitch, 1,3);

        Scene scene = new Scene(gridPane,600, 300);
        scene.setFill(Color.SNOW);

        stage.setTitle("Arduino RGB Controller");
        stage.setScene(scene);
        stage.show();


    }
}


