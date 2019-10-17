package com.company;
import java.util.Scanner;
import static java.lang.Math.*;

public class Main {

    public static void main(String[] args) {

        double a=0, b=0, c=0;

        if (args.length>0){       // проверяем использованы ли аргументы командной строки
            try {
                a = Double.parseDouble(args[0]);
            }
            catch (NumberFormatException ex){
                System.out.println("a не число. a приравниваем к 0.");
                a = 0;
            }

            if(args.length>1) {
                try {
                    b = Double.parseDouble(args[1]);
                }
                catch (NumberFormatException ex) {
                    System.out.println("b не число. b приравниваем к 0.");
                    b = 0;
                }
            }
            else if(args.length==1){
                b=0;
                c=0;
            }

            if (args.length>2) {
                try{
                    c = Double.parseDouble(args[2]);
                }
                catch(NumberFormatException ex){
                    System.out.println("c не число. c приравниваем к 0.");
                    c = 0;
                }
            }
            else if (args.length == 2) {
                c = 0;
            }
        }

        else {  // если нет, то юзаем сканер
                System.out.print("Введите a: ");
                try {
                    Scanner num = new Scanner(System.in);
                    a = num.nextDouble();
                }
                catch (Exception ex){
                    System.out.println("a не число. a приравниваем к 0. ");
                }

                System.out.print("Введите b: ");
                try {
                    Scanner num = new Scanner(System.in);
                    b = num.nextDouble();
                }
                catch (Exception ex){
                    System.out.println("b не число. b приравниваем к 0. ");
                }

                System.out.print("Введите c: ");
                try {
                    Scanner num = new Scanner(System.in);
                    c = num.nextDouble();
                }
                catch (Exception ex){
                    System.out.println("c не число. c приравниваем к 0. ");
                }
        }

        System.out.println("Решаем уравнение вида: " + a + "x^2 + " + b + "x + " + c + " = 0");
            if (a != 0) {
                double x1, x2;
                double d = b * b - 4 * a * c;
                if (d > 0) {
                    x1 = (-b + sqrt(d)) / (2 * a);
                    x2 = (-b - sqrt(d)) / (2 * a);
                    System.out.println("Так как дискриминант больше 0, то у уравнения два корня:");
                    System.out.println("x1= " + x1);
                    System.out.println("x2= " + x2);
                } else if (d == 0) {
                    x1 = (-b) / (2 * a);
                    System.out.print("Так как дискриминант равен 0, то корень всего один: " + x1 + "\n");
                } else {
                    System.out.println("Так как дискриминант меньше 0, то имеются компексные корни. ");
                    Complex z1 = new Complex(-b / (2 * a), sqrt(abs(d)) / (2 * a));
                    Complex z2 = new Complex(-b / (2 * a), -(sqrt(abs(d))) / (2 * a));
                    System.out.print("z1= ");
                    z1.pr();
                    System.out.print("z2= ");
                    z2.pr();
                }

            } else {
                if (b != 0) {
                    System.out.println("Так как a равно 0, то уравнение вырождается в линейное:\n" + "x = " + -1. * c / b);
                } else if (c == 0) {
                    System.out.println("x - любое число.");
                } else {
                    System.out.println("Нет решений.");
                }
            }
    }
}