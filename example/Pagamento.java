package org.example;

import java.util.Scanner;//uso do teclado
import java.io.FileWriter;//para escrita  do arquivo txt
import java.io.IOException;// processamento de erros da escrita de txt
import java.time.LocalDateTime;//captura da data e hora do sistema txt
import java.time.format.DateTimeFormatter;//para formato correto da hora


public class Pagamento {

    // Atributos
    private String metodoPagamento;
    private double valor;
    private boolean pago;//privado para somente ocorrer ações por meio dessa classe
    private boolean cancelado;

    // Construtor inicia cada vez que um objeto é criado
    public Pagamento(double valor) { // parâmetro recebido da Main
        // inicialização dos atributos
        this.valor = valor; // guarda o valor recebido
        this.pago = false;    // inicia como não pago
        this.cancelado = false;  // inicia como não cancelado
        this.metodoPagamento = "não definido"; // inicia sem definição
    }

    // Metodo do Pix
    public void pix(Scanner sc) {
        if (cancelado) {
            System.out.println("Pagamento cancelado! Não é possível pagar.");
            return;
        }
        this.metodoPagamento = "Pix"; //atributo do pagamento recebe o modo que irá ser pago
        this.pago = true;//Atributo recebe que foi pago
        System.out.println("===== PAGAMENTO VIA PIX =====");
        System.out.println("Valor: R$ " + String.format("%.2f", valor));
        //Transformação de valores com. para ,  e com duas casas decimais
        System.out.println("Chave Pix: copa2026.com");
        System.out.println("Status: APROVADO ✔");
        System.out.println("=============================");
        // pergunta após o pagamento
        System.out.print("Deseja emitir o comprovante? (1-SIM / 2-NÃO): ");
        int opcao = sc.nextInt();
        if (opcao == 1)
        {
            emitirComprovante();
        }
        else {
            System.out.println("Comprovante não emitido.");
        }
    }

    public void credito(Scanner sc) {
        if (cancelado) {
            System.out.println("Pagamento cancelado! Não é possível pagar.");
            return;
        }
        this.metodoPagamento = "Cartão de Crédito";// seleciona
        this.pago = true;

        System.out.println("===== PAGAMENTO CRÉDITO =====");
        System.out.printf("Valor: R$ %.2f%n", valor);

        // pergunta quantas parcelas
        System.out.print("Parcelas (1, 2 ou 3x sem juros): ");
        int parcelas = sc.nextInt();// ENTRADA DO VALOR DE PARCELAS

        if (parcelas == 1) {
            System.out.printf("1x de R$ %.2f%n", valor);
        } else if (parcelas == 2) {
            System.out.printf("2x de R$ %.2f%n", valor / 2);
        } else if (parcelas == 3) {
            System.out.printf("3x de R$ %.2f%n", valor / 3);
        } else {
            System.out.println("Opção inválida! Processando em 1x.");
            System.out.printf("1x de R$ %.2f%n", valor);
        }

        System.out.println("Status: APROVADO ✔");
        System.out.println("=============================");
        // pergunta após o pagamento
        System.out.print("Deseja emitir o comprovante? (1-SIM / 2-NÃO): ");
        int opcao = sc.nextInt();
        if (opcao == 1)
        {
            emitirComprovante();
        }
        else {
            System.out.println("Comprovante não emitido.");
        }
    }

    // Pagamento via débito
    public void debito(Scanner sc) {
        if (cancelado) {
            System.out.println("Pagamento cancelado! Não é possível pagar.");
            return;
        }
        this.metodoPagamento = "Cartão de Débito";
        this.pago = true;
        System.out.println("===== PAGAMENTO DÉBITO ======");
        System.out.println("Valor: R$ " + String.format("%.2f", valor));
        System.out.println("Status: APROVADO ✔");
        System.out.println("=============================");

        // pergunta após o pagamento
        System.out.print("Deseja emitir o comprovante? (1-SIM / 2-NÃO): ");
        int opcao = sc.nextInt();
        if (opcao == 1)
        {
            emitirComprovante();
        }
        else {
            System.out.println("Comprovante não emitido.");
        }
    }

    // Cancela o pagamento
    public void cancelar() {
        if (pago) {
            System.out.println("Pagamento já realizado via " + metodoPagamento);
        } else {
            this.cancelado = true;
            this.metodoPagamento = "cancelado";
            //Atualiza o atributo de acordo com a condição
            System.out.println("Pagamento CANCELADO.");
        }
    }

    // Emite comprovante no console
    public void emitirComprovante() {
        System.out.println("======= COMPROVANTE =========");
        if (pago) {
            System.out.println("Loja: Camisas Copa Brasil");
            System.out.println("Método: " + metodoPagamento);
            System.out.println("Valor pago: R$ " + String.format("%.2f", valor));
            System.out.println("Status: PAGO ✔");
        } else if (cancelado) {
            System.out.println("Status: CANCELADO ✖");
        } else {
            System.out.println("Status: PAGAMENTO PENDENTE");
        }
        System.out.println("=============================");

        // Salva o comprovante em arquivo .txt
        salvarComprovanteEmArquivo();
        // reseta o objeto para estado inicial
        this.pago = false;
        this.cancelado = false;
        this.metodoPagamento = "não definido";
        this.valor = 0;
        System.out.println("Sistema reiniciado. Voltando ao menu principal...");
    }

    // ─── NOVO: grava o comprovante em comprovante.txt ───────────────────────────
    private void salvarComprovanteEmArquivo() {
        String nomeArquivo = "comprovante.txt";
        //nome e criação do arquivo txt
        String dataHora = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        //Salvamento da hora e data da criiação desse arquivo

        try (FileWriter fw = new FileWriter(nomeArquivo, false)) { // false = sobrescreve
            //Função para criação da estrutura do comprovante
            fw.write("======= COMPROVANTE DE PAGAMENTO =======\n");
            fw.write("Loja  : Camisas Copa Brasil\n");
            fw.write("Data  : " + dataHora + "\n");
            fw.write("----------------------------------------\n");

            if (pago) {
                fw.write("Método: " + metodoPagamento + "\n");
                //criação e escrita de uma nova linha e quebra ela
                fw.write("Valor : R$ " + String.format("%.2f", valor) + "\n");
                fw.write("Status: PAGO ✔\n");
            } else if (cancelado) {
                fw.write("Status: CANCELADO ✖\n");
            } else {
                fw.write("Status: PAGAMENTO PENDENTE\n");
            }

            fw.write("========================================\n");

            System.out.println("Comprovante salvo em: " + nomeArquivo);

        } catch (IOException e) {
            System.out.println("Erro ao salvar comprovante: " + e.getMessage());
        }
    }
    // ────────────────────────────────────────────────────────────────────────────

    // Getters e Setters,para pegar e entregar coisas privadas
    public String getMetodoPagamento() {
        return metodoPagamento;
        //retorna o metodo de pagamento
    }
    public double getValor() {
        return valor;
        //

    }
    public void   setValor(double v)
    {
        this.valor = v; }
    public boolean isPago() {
        return pago;
    }
    public boolean isCancelado()       {
        return cancelado;
    }
}