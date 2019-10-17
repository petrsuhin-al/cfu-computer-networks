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

            String addr = in.readUTF(),
                   PORT = in.readUTF(),
                   localPORT = in.readUTF();

            out.write(clientName);
            out.writeLong(start);
            out.writeLong(end);

            System.out.println("Клиент " + clientName + " подключился c " + addr + ", порт: "
                    + PORT + ", локальный порт: " + localPORT + ", и получил интервал: " + start + "-" + end);

            int clientName = in.read();
            long time = in.readLong();
            String strResult = in.readUTF();

            int intResult = Integer.parseInt(strResult);

            if (intResult > 0) {
                System.out.println("Клиент " + clientName + " на интервале " + start + "-" + end + ", нашел "
                        + intResult + " чисел, за " + time + " мс.");
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
        System.out.println("Клиент " + clientName + " отключился.");
        System.out.println("============================================");
        System.out.println("Ожидание клиента...");

    }
}

public class Server {
    private static final int PORT = 9090;
    public static void main(String[] args) {
        int clientName = 0;
        long start = 0;
        long step = 1000000;
        long end = start + step;
        try (ServerSocket socket = new ServerSocket(PORT)) {
            InetAddress addr = InetAddress.getByName(null);
            String timeNow = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println("Сервер успешно запустился в " + timeNow + ", по адресу: " + addr + ", на порту: " + PORT);
            System.out.println("Ожидание клиента...");
            while(true) {
                try {
                    ServeOneThread serve = new ServeOneThread(socket.accept(), clientName, start, end);
                    serve.start();
                    start += step;
                    end += step;
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
