package org.example;

//TipoCliente herda todos os atributos e métodos de Cliente
public class TipoCliente extends Cliente{
    //Constantes estáticas que pertencem à classe, e não ao objeto, e nunca se alteram
    public static final int COMUM = 0;
    public static final int PREMIUM = 1;

    //Atributos da classe filha
    private int nivel;
    private int totalCompras;

    //Construtor para receber os dados e repassar para o construtor da classe pai
    public TipoCliente(String nome, String cpf, String telefone, String email, String endereco) {
        super(nome, cpf, telefone, email, endereco);
        this.totalCompras = 0; // todo cliente começa com nenhuma compra
        this.nivel = COMUM; // todo cliente começa no nível COMUM (0)
    }

    //Getter permite que outras classes acessem esses dados privados
    public int getNivel() {
        return nivel;
    }

    public int getTotalCompras() {
        return totalCompras;
    }

    //Método privado, acessado somente dentro da classe
    private void atualizarNivel() { // verificar se precisa atualizar o nível do cliente
        if(totalCompras > 2)
            nivel = PREMIUM;
        else
            nivel = COMUM;
    }

    //Método público para registrar uma nova compra
    public void registrarCompra() {
        totalCompras++; // soma 1 na variavel totalCompras
        atualizarNivel(); // verifica se precisa atualizar o nível do cliente
    }

    //Método para validar o nível do cliente
    public String verificaNivel() {
        if(nivel == PREMIUM)
            return "Cliente Premium";
        else
            return "Cliente Comum";
    }

    //Método para mostrar as informações do cliente
    @Override
    public void mostraInfo() {
        super.mostraInfo();
        System.out.println("Nível do cliente: " + verificaNivel());
        System.out.println("============================================");
    }
}
