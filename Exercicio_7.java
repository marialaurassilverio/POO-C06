package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Entre com a quantidade de alunos em C06:");
        int quantidade = entrada.nextInt();

        switch(quantidade)
        {
            case 10:
                System.out.println("Sala I - 6");
                break;

            case 20:
                System.out.println("Sala I - 6");
                break;

            case 30:
                System.out.println("Sala I - 5");
                break;

            default:
                System.out.println("Quantidade de alunos inválida");
        }
    }
}