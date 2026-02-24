package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var valor1 = 30;
        var valor2 = 15;

        var Qtt_inteiros = 8;
        var Qtt_Meia = 5;

        var Total_inteiros = Qtt_inteiros*valor1;
        var Total_meia = Qtt_Meia*valor2;

        System.out.println("Valor total arrecadado com ingressos inteiros: " + Total_inteiros);
        System.out.println("Valor total arrecadado com ingressos meia: " + Total_meia);

        var soma =  Total_inteiros + Total_meia;
        System.out.println("Valor total arrecadado: " + soma);

        var media = soma/(Qtt_Meia+Qtt_inteiros);
        System.out.println("Valor medio pago por ingresso: " + media);

        }
    }
