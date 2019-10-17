package com.company;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class ServeOneThread extends Thread {
    private Socket socket;
    private int clientName;
    private long start;
    private long end;

    ServeOneThread(Socket socket, int clientName, long start, long end) {
        this.socket = socket;
        this.clientName = clientName;
        this.start = start;
        this.end = end;
    }

    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.write(clientName);
            out.writeLong(start);
            out.writeLong(end);

            int clientName = in.read();
            int result = in.read();
            long time = in.readLong();

            if (result > 0) {
                System.out.println("Клиент " + clientName + " нашел " + result + " чисел, за " + time + " мс.");
            } else{
                System.out.println("Клиент не нашел чисел...");
            }

        } catch (IOException e) {
            System.err.println("Не получилось передать данные клиенту...");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Сокет не закрыт...");
            }
        }
        System.out.println("Клиент отключился.");
        System.out.println("============================================");
        System.out.println("Ожидаем клиента...");

    }
}

public class Server {
    private static final int PORT = 8890;
    public static void main(String[] args) {
        int clientName = 0;
        long start = 0;
        long step = 1000000;
        long end = start + step;
        try (ServerSocket socket = new ServerSocket(PORT)) {
            InetAddress addr = InetAddress.getLocalHost();
            String timeNow = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println("Сервер успешно запустился в " + timeNow + ", по адресу: " + addr + ", на порту: " + PORT);
            System.out.println("Ожидание клиента...");
            while(true) {
                try {
                    ServeOneThread serve = new ServeOneThread(socket.accept(), clientName, start, end);
                    serve.start();
                    start += step;
                    end += step;
                    System.out.println("Клиент с именем " + clientName + " подключился" + " и получил интервал " + start + "-" + end);
                    clientName++;
                } catch (IOException ignored) {
                    System.out.println("Не удалось передать клиенту данные...");
                }
            }
        } catch (Exception ignored) {
            System.out.println("Сервер не запустился...");
        }
    }
}
