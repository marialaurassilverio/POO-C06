package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Zumbi zumbi = new Zumbi();
        var Zumbi1 = new Zumbi();

        zumbi.resmungar();
        zumbi.atacar();

        zumbi.vida=9;
        zumbi.vida=12;

        double vidaZumbi = zumbi.mostraVida();
        System.out.println("Vida do Zumbi: "+vidaZumbi);

        double vidaZumbi1 = zumbi.mostraVida();
        System.out.println("Vida do Zumbi1: "+vidaZumbi1);

        System.out.println("-----");
        zumbi.transfereVida(Zumbi1,3);

        System.out.println("Vida do zumbi: " + zumbi.mostraVida());
        System.out.println("Vida do zumbi1: " + Zumbi1.mostraVida());

    }
}