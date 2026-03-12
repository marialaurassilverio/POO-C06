package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Pessoa Maria = new Pessoa();  //novo objeto

        Maria.nome = "Maria Souza";
        Maria.idade = 20;;

        Maria.falar();//tudo que esta no void falar é executado aqui

        Professor ProfessoraPoo = new Professor();

        ProfessoraPoo.nome = "Maria Eduarda";
        ProfessoraPoo.idade = 20;
        ProfessoraPoo.disciplina = "Fisiologia";

        ProfessoraPoo.falar();
        ProfessoraPoo.ministraAula();

        Engenheiro Eng = new Engenheiro();

        Eng.categoria = "biomédica";
        Eng.idade = 20;
        Eng.nome = "Maria";
        Eng.disciplina = "Eletronica digital";
        Eng.falar();
        Eng.ministraAula();
        Eng.constroi();

    }
}