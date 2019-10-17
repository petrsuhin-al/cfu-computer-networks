package com.company;
import java.io.*;
import java.applet.Applet;
import java.awt.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.TextField;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client extends Applet implements ActionListener  {

    private Button MainButton;
    private TextField textBoxAddress,textBoxPort;
    private TextArea Output;

    public void init(){
        String serverAddress = "127.0.0.1";
        int serverPort = 9090;

        setLayout(new FlowLayout());

        textBoxAddress = new TextField(20);
        textBoxPort = new TextField(20);

        textBoxAddress.setText(serverAddress);
        textBoxPort.setText(String.valueOf(serverPort));

        MainButton = new Button("Подключение");
        MainButton.addActionListener(this);

        Output = new TextArea("Ожидание...",20,80);

        add(Output);
        add(textBoxAddress);
        add(textBoxPort);
        add(MainButton);
    }

    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == MainButton) {
            try (Socket socket = new Socket(textBoxAddress.getText(), Integer.parseInt(textBoxPort.getText()))) {
                String timeNow = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                String addr = InetAddress.getByName(null).toString(),
                       PORT = Integer.toString(socket.getPort()),
                       localPORT = Integer.toString(socket.getLocalPort());

                out.writeUTF(addr);
                out.writeUTF(PORT);
                out.writeUTF(localPORT);

                int name = in.read();
                long start = in.readLong();
                long end = in.readLong();


                Output.append("\nКлиент " + name + " получил интервал: " + start + " - " + end + " в " + timeNow);

                int result = 0;
                long startTime = System.currentTimeMillis();
                for (long i = start; i <= end; i++) {
                    if (i % 11 == 0 && i % 13 == 0 && i % 17 == 0) {
                        result++;
                    }
                }
                long timeConsumedMillis = System.currentTimeMillis() - startTime;

                out.write(name);
                out.writeLong(timeConsumedMillis);

                String strResult;
                if (result > 0) {
                    Output.append("\nКлиент " + name + " на интервале " + start + "-" + end + ", нашел " + result + " чисел " + "за " + timeConsumedMillis + " мс.");
                    strResult = Integer.toString(result);
                } else {
                    Output.append("\nКлиент " + name + " на интервале" + start + "-" + end + " не нашел подходящие числа.");
                    strResult = "0";
                }
                out.writeUTF(strResult);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Output.append("\nКлиент отключился.");
                Output.append("\n============================================");
                Output.append("\nОжидание...");
            }
        }

    }
    public static void main(String[] args){
        try {
            Client applet = new Client();
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Клиент");
            frame.getContentPane().add(applet, BorderLayout.CENTER);
            applet.init();
            applet.start();
            frame.setSize(680, 410);
            frame.setVisible(true);
        } catch (Exception ignored){

        }
    }
}