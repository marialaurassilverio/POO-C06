package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        int horas = 3;
        int primeirahora = entrada.nextInt();
        int segundahora = entrada.nextInt();
        int terceirahora = entrada.nextInt();

        int soma = primeirahora + segundahora + terceirahora;
        double media = soma/horas;

        System.out.println("Total: " + soma);
        System.out.println("Média: " + media);
    }
}