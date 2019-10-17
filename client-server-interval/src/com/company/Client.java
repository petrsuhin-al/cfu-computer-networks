//package com.company;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = null;
        int serverPort = 0;

        if (args.length == 0 || args == null) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("Введите адрес сервера:");
                serverAddress = scanner.nextLine();
                System.out.println("Введите порт сервера:");
                serverPort = scanner.nextInt();
            } catch (Exception ignored){
                System.out.println("Вы не ввели или ввели неправильно адрес и порт...");
            }
            scanner.close();
        } else {
            serverAddress = args[0];
            serverPort = Integer.parseInt(args[1]);
        }

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            String timeNow = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            int name = in.read();
            long start = in.readLong();
            long end = in.readLong();

            System.out.println("Клиент " + name + ", получил интервал: " + start + " - " + end + " в " + timeNow);

            int result = 0;

            long startTime = System.currentTimeMillis();
            for (long i = start; i <= end; i++) {
                if (i % 11 == 0 && i % 13 == 0 && i % 17 == 0) {
                    result += 1;
                }
            }
            long timeConsumedMillis = System.currentTimeMillis() - startTime;


            if(result > 0){
                System.out.println("Клиент " + name + " на интервале " + start + "-" + end +  ", нашел " + result + " чисел " +  " за " + timeConsumedMillis + " мс.");
            }
            else {
                System.out.println("Клиент " + name + " на интервале" + start + "-" + end +  " не нашел подходящие числа.");
            }

            out.write(name);
            out.write(result);
            out.writeLong(timeConsumedMillis);

        } finally {
            System.out.println("Клиент отключился.");
            System.out.println("============================================");
        }
    }
}