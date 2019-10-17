package com.company;
import java.lang.String;


class searchNumbers extends Thread {

    private long start;
    private long end;
    public long result;

    searchNumbers(long start, long end, String name){
        this.start = start;
        this.end = end;
        this.setName(name);
    }

    public void run() {
        StringBuilder s = new StringBuilder();
        result = 0;

        for (long i = start; i <= end; i++) {
            if (i % 11 == 0 && i % 13 == 0 && i % 18 == 0) {
                result += 1;
                s.append(this.getName()).append(": ").append(i).append(" ");
                if (result % 14 == 0) {
                    System.out.println(s);
                    s = new StringBuilder();
                }
            }
        }
        if (!this.getName().equals("0")) {
            System.out.println(this.getName() + " поток закончил работу (трехпоточная версия)" + " и нашел " + result + " чисел.");
        } else {
            System.out.println(this.getName() + " поток закончил работу (однопоточная версия)" + " и нашел " + result + " чисел.");
        }

    }
}


public class Main {
    public static void main(String[] args){

        // В ОДИН ПОТОК
        long startTime = System.currentTimeMillis();

        searchNumbers fullSearchNumbers = new searchNumbers(100000000, 400000000, "0");
        fullSearchNumbers.run();

        long finishTime = System.currentTimeMillis();
        long timeConsumedMillis1 = finishTime - startTime;

        // В ТРИ ПОТОКА
        startTime = System.currentTimeMillis();

        searchNumbers firstSearchNumbers = new searchNumbers(100000000, 200000000, "1");
        firstSearchNumbers.start();

        searchNumbers secondSearchNumbers = new searchNumbers(200000000, 300000000, "2");
        secondSearchNumbers.start();

        searchNumbers thirdSearchNumbers = new searchNumbers(300000000, 400000000, "3");
        thirdSearchNumbers.start();


        try {
            firstSearchNumbers.join();
            secondSearchNumbers.join();
            thirdSearchNumbers.join();
        }
        catch (Exception e){
            System.out.println("Ошибка...");
        }

        finishTime = System.currentTimeMillis();
        long result3 = firstSearchNumbers.result + secondSearchNumbers.result + thirdSearchNumbers.result;
        long timeConsumedMillis2 = finishTime - startTime;

        System.out.println("______________________________________________");
        System.out.println("Однопоточная версия" + " нашла "  + fullSearchNumbers.result + " чисел " + "и выполнилась за " + timeConsumedMillis1 + " мс.");
        System.out.println("Трехпоточная версия" + " нашла " + result3  + " чисел " + "и выполнилась за " + timeConsumedMillis2 + " мс.");
    }

}
