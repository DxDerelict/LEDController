package sample;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialComms {
    SerialPort serialPort;
    boolean init;


    public void serialInitialize() {
        try {
            System.out.println("Open Ports " + serialPort.openPort());
            System.out.println("Set parameters " + serialPort.setParams(9600, 8, 1, 0));
            Thread.sleep(3000);
            init = true;
        }
        catch (SerialPortException | InterruptedException ex){
            System.out.println(ex);
        }
    }

    public void serialWrite(int[] rgbValue) {
        try {
            byte[] data = new byte[4];
            data[0] = (byte)'X';
            data[1] = (byte)rgbValue[0];
            data[2] = (byte)rgbValue[1];
            data[3] = (byte)rgbValue[2];
            System.out.println("\"Send RGB: " + rgbValue[0] + "," + rgbValue[1] + "," + rgbValue[2] + " " + serialPort.writeBytes(data));
            //System.out.println("Port closed: " + serialPort.closePort());
        }
        catch (SerialPortException ex){
            System.out.println(ex);
        }
    }





}
