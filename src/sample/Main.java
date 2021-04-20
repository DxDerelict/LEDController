package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Main extends Application {
    boolean lightSwitchState = false;
    int[] RGB = new int[4];
    String[] portNames;

    Label redVal = new Label(String.valueOf(RGB[1]));
    Label blueVal = new Label(String.valueOf(RGB[2]));
    Label greenVal =  new Label(String.valueOf(RGB[3]));

    Slider redSlider   = new Slider(0,255,0);
    Slider blueSlider  = new Slider(0, 255, 0);
    Slider greenSlider = new Slider(0,255,0);

    Button lightSwitch = new Button("On/Off");
    Button connectButton = new Button("Connect");

    private void updateSerialData(int[] RGB){



    }


    private void detectPort() {
        ObservableList<String> portList;
        portList = FXCollections.observableArrayList();
        portNames = SerialPortList.getPortNames();
//        for (String name : portNames) {
//            System.out.println(name);
//            portList.add(name);
//        }
    }

    private void sliderChanged(){
        RGB[1] = (int) redSlider.getValue();
        redVal.setText(String.valueOf(RGB[1]));

        RGB[2] = (int) blueSlider.getValue();
        blueVal.setText(String.valueOf(RGB[2]));

        RGB[3] = (int) greenSlider.getValue();
        greenVal.setText(String.valueOf(RGB[3]));

    }

    private void setupGUI(){
        redSlider.setShowTickLabels(true);
        redSlider.setLayoutY(10);
        redSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sliderChanged();
            }
        });

        blueSlider.setShowTickLabels(true);
        blueSlider.setLayoutY(40);
        blueSlider.setShowTickLabels(true);
        blueSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sliderChanged();
            }
        });

        greenSlider.setShowTickLabels(true);
        greenSlider.setLayoutY(70);
        greenSlider.setShowTickLabels(true);
        greenSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sliderChanged();
            }
        });


    }

    public void start(Stage stage) {
        detectPort();
        setupGUI();


        ComboBox serialComboList = new ComboBox();
        serialComboList.getItems().addAll(portNames);
        serialComboList.setPromptText("Choose COM Port");
        serialComboList.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {

            }
        });

        connectButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(serialComboList.getValue() != null){
                    try {
                        SerialPort serialPort =
                                new SerialPort(serialComboList.getValue().toString());

                        serialPort.openPort();
                        serialPort.setParams(
                                SerialPort.BAUDRATE_9600,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                       // serialPort.writeBytes(RGB[1].toByte);


                    } catch (SerialPortException ex) {
                       System.out.println("Cant connect to Serial port");
                    }

                }else{
                    System.out.println("No SerialPort selected!");
                }

                }
        });


        lightSwitch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("lightSwitch clicked");
                lightSwitchState = !lightSwitchState;
                if(lightSwitchState) lightSwitch.setText("ON");
                    else lightSwitch.setText("OFF");

            }
        });

        {
            GridPane gridPane = new GridPane();
            gridPane.setMinSize(400, 200);
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.add(serialComboList, 0, 0);
            gridPane.add(connectButton, 0, 1);
            gridPane.add(redSlider, 1, 0);
            gridPane.add(blueSlider, 1, 1);
            gridPane.add(greenSlider, 1, 2);
            gridPane.add(lightSwitch, 1, 3);
            gridPane.add(redVal, 2, 0);
            gridPane.add(blueVal, 2, 1);
            gridPane.add(greenVal, 2, 2);

            Scene scene = new Scene(gridPane, 600, 300);
            scene.setFill(Color.SNOW);

            stage.setTitle("Arduino RGB Controller");
            stage.setScene(scene);
            stage.show();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}


