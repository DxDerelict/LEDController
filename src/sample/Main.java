package sample;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


public class Main extends Application {
    boolean lightSwitchState = false;
    volatile int[] RGB = {0, 155, 145, 125};
    String[] portNames;
    String chosenPortName;
    public static SerialPort pickedPort;
    int selectedIndex;
    boolean isComConnected = false;


    Label redVal = new Label(String.valueOf(RGB[1]));
    Label blueVal = new Label(String.valueOf(RGB[2]));
    Label greenVal =  new Label(String.valueOf(RGB[3]));

    Slider redSlider   = new Slider(0,255,0);
    Slider blueSlider  = new Slider(0, 255, 0);
    Slider greenSlider = new Slider(0,255,0);

    Button lightSwitch = new Button("On/Off");
    Button connectButton = new Button("Connect");
    Button sendButton = new Button("Send");

    private byte[] sendToSerial(int[] RGB){
        String RGBString = " ";
        for(int i =0; i< RGB.length; i++) {
            RGBString += String.valueOf(RGB[i]) + '/';
        }
        byte[] msg = RGBString.getBytes();
        return msg;
    }


    private void detectPorts() {
        SerialPort[] AvailableComPorts= SerialPort.getCommPorts();
        portNames = new String[AvailableComPorts.length];
        for (int i = 0; i < AvailableComPorts.length; i++) {
            //System.out.println(AvailableComPorts[i].getDescriptivePortName());
            portNames[i] = AvailableComPorts[i].getDescriptivePortName();
        }
    }

    private void sliderChanged(){
        RGB[1] = (int) redSlider.getValue();
        redVal.setText(String.valueOf(RGB[1]));

        RGB[2] = (int) blueSlider.getValue();
        blueVal.setText(String.valueOf(RGB[2]));

        RGB[3] = (int) greenSlider.getValue();
        greenVal.setText(String.valueOf(RGB[3]));
        pickedPort.writeBytes(sendToSerial(RGB), 15);
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
        detectPorts();
        setupGUI();


        ComboBox serialComboList = new ComboBox();
        serialComboList.getItems().addAll(portNames);
        serialComboList.setPromptText("Choose COM Port");
        serialComboList.setOnAction((event) -> {
            selectedIndex = serialComboList.getSelectionModel().getSelectedIndex();
            Object selectedItem = serialComboList.getSelectionModel().getSelectedItem();
            System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            chosenPortName = (String) serialComboList.getValue();
        });


        connectButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                    SerialPort[] AvailableComPorts = SerialPort.getCommPorts();
                    if (serialComboList.getValue() != null) {
                        pickedPort = AvailableComPorts[selectedIndex];
                        System.out.println(pickedPort.getDescriptivePortName());

                        if (pickedPort.openPort() ) {
                            isComConnected = true;
                            pickedPort.setBaudRate(115200);
                            // pickedPort.setComPortTimeouts(pickedPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
                        } else {
                            System.out.println("Cant connect to Serial port");
                        }

                    } else {
                        System.out.println("No SerialPort selected!");
                    }

                if(isComConnected){
                    connectButton.setText("Disconnect");

                }else{
                    pickedPort.closePort();
                    isComConnected = false;
                    connectButton.setText("Connect");
                }

            }
        });

        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Sent" + RGB[1]);
                pickedPort.writeBytes(sendToSerial(RGB), 15);

            }
        });


        lightSwitch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("lightSwitch clicked");
                lightSwitchState = !lightSwitchState;
                    if (lightSwitchState) {
                        lightSwitch.setText("ON");
                        RGB[0] = 1;
                    } else {
                        lightSwitch.setText("OFF");
                        RGB[0] = 0;
                    }
                pickedPort.writeBytes(sendToSerial(RGB), 15);
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
            //gridPane.add(sendButton, 0,2);
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


