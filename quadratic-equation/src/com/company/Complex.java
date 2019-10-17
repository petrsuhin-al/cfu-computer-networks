package com.company;
import static java.lang.Math.*;

public class Complex {
    private static final double EPS = -12; // Точность вычислений
    private double re, im;                 // Действительная и мнимая часть

    Complex(double re, double im) {        // Четыре конструктора
        this.re = re; this.im = im;
    }
    Complex(double re){
        this(re, 0.0);
    }
    Complex(){
        this(0.0, 0.0);
    }
    Complex(Complex z){
        this(z.re, z.im);
    }

    public double getRe(){        // Методы доступа
        return re;
    }
    public double getlmf(){
        return im;
    }
    public Complex getZ(){
        return new Complex(re, im);
    }
    public void setRe(double re){
        this.re = re;
    }
    public void setlm(double im){
        this.im = im;
    }
    public void setZ(Complex z){
        re = z.re; im = z.im;
    }

    public double mod(){                              // Модуль и аргумент комплексного числа
        return sqrt(re * re + im * im);
    }
    public double arg(){
        return atan2(re, im);
    }

    public boolean isReal(){             // Проверка: действительное число?
        return Math.abs(im) < EPS;
    }

    public void pr(){    // Вывод на экран
        System.out.println(re + (im < 0.0 ? "" : "+") + im + "i");
    }

    public boolean equals(Complex z){                 // Переопределение методов класса Object
        return abs(re -z.re) < EPS && abs(im - z.im) < EPS;
    }

    public String toString(){
        return "Complex: " + re + " " + im;
    }

    public void add(Complex z){           // Методы, реализующие операции +=, -=, *=, /=
        re += z.re; im += z.im;
    }
    public void sub(Complex z){
        re -= z.re; im -= z.im;
    }
    public void mul(Complex z){
        double t = re * z.re - im * z. im;
        im = re * z.im + im * z.re;
        re = t;
    }

    public void div(Complex z){
        double m = z.mod();
        double t = re * z.re - im * z.im;
        im = (im * z.re - re * z.im) / m;
        re = t / m;
    }

    public Complex plus(Complex z){                 // Методы, реализующие операции +, -, *, /
        return new Complex(re + z.re, im + z.im);
    }

    public Complex minus(Complex z){
        return new Complex(re - z.re, im - z.im);
    }

    public Complex asterisk(Complex z){
        return new Complex(re * z.re - im * z.im, re * z.im + im * z.re);
    }

    public Complex slash(Complex z){
        double m = z.mod();
        return new Complex((re * z.re - im * z.im) / m, (im * z.re - re * z.im) / m);
    }
}
