package org.example;

public class Cliente { // classe Cliente, acessível para qualquer outra classe
    //Atributos da classe (encapsulamento)
    private String nome;      // nome do cliente
    private String cpf;       // CPF do cliente, usado como identificador na busca
    private String telefone;  // telefone de contato do cliente
    private String email;     // email de contato do cliente
    private String endereco;  // endereço de entrega do cliente

    //Construtor recebe e armazena os dados após criar o objeto Cliente
    public Cliente(String nome, String cpf, String telefone, String email, String endereco) {
        this.nome = nome;             // guarda o nome recebido
        this.cpf = cpf;               // guarda o CPF recebido
        this.telefone = telefone;     // guarda o telefone recebido
        this.email = email;           // guarda o email recebido
        this.endereco = endereco;     // guarda o endereço recebido
    }

    //Getters permitem que outras classes acessem os dados privados
    public String getNome() {
        return nome; // retorna o nome do cliente
    }

    public String getCpf() {
        return cpf; // retorna o CPF do cliente
    }

    public String getTelefone() {
        return telefone; // retorna o telefone do cliente
    }

    public String getEmail() {
        return email; // retorna o email do cliente
    }

    public String getEndereco() {
        return endereco; // retorna o endereço do cliente
    }

    //Setters para permitir que outras classes alterem os valores dos atributos
    public void setNome(String nome) {
        this.nome = nome; // atualiza o nome do cliente
    }

    public void setCpf(String cpf) {
        this.cpf = cpf; // atualiza o CPF do cliente
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone; // atualiza o telefone do cliente
    }

    public void setEmail(String email) {
        this.email = email; // atualiza o email do cliente
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco; // atualiza o endereço do cliente
    }

    //Método para exibir os dados do cliente
    public void mostraInfo() {
        System.out.println("========== INFORMAÇÕES DO CLIENTE ==========");
        System.out.println("Nome: " + nome);             // exibe o nome
        System.out.println("CPF: " + cpf);                // exibe o CPF
        System.out.println("Telefone: " +telefone);        // exibe o telefone
        System.out.println("Endereço: " + endereco);       // exibe o endereço
    }
}