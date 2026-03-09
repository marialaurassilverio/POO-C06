package org.example;

import java.util.Scanner;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner (System.in); // biblioteca para que o usuario possa usar o teclado
        Random rand = new Random(); // biblioteca

        int x = rand.nextInt( 10) + 1;// gera um numero entre 1 a 10 e salva em x
        System.out.println(x);

        System.out.println("Qual número foi gerado?");
        int numero = entrada.nextInt(); // entrada de valor

        if(numero == x) { // verificação se o valor gerado é igual ao que foi digitado pelo usuário
            System.out.println("O numero digitado está certo");
        }
        else {
            System.out.println("O numero digitado está errado");
        }

    }
}
