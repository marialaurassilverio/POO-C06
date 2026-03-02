package org.example;

import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

            System.out.println("Entre com a NPA:" );
            int NPA = teclado.nextInt();

            if(NPA >= 60){
                System.out.println("O aluno está aprovado" );
            }
            else if(NPA < 30){
                System.out.println("O aluno está reprovado" );
            }
            else if(NPA >= 30  && NPA < 60){

                System.out.println("Entre com a NP3:");
                int NP3 = teclado.nextInt();
                int NFA = (NPA + NP3)/2;

                if(NFA >= 50){
                    System.out.println("O aluno está aprovado" );
                }
                else{
                    System.out.println("O aluno está reprovado" );
                }
            }
        }
    }