package org.example;

public class Desconto {
    private String tipoCliente; // "comum" ou "premium"
    private double percentual; // atributo que armazena o percentual de desconto

    public Desconto(String tipoCliente) { // construtor que recebe o tipo do cliente e já define o percentual
        this.tipoCliente = tipoCliente; // atribui o tipo de cliente
        this.percentual = definirPercentual(tipoCliente); //ométodo que vai definir o percentual
    }

    private double definirPercentual(String tipo) { // metodo que vai definir qual percentual aplicar com base no tipo de cliente
        if (tipo.equalsIgnoreCase("premium")) { // verifica se o tipo é preminum, se sim...
            return 20.0; // retorna 20% de desconto
        } else {
            return 5.0; // senao retorna 5%
        }
    }

    public double calculaDesconto(double valorOriginal) { // calcula e retorna o valor final apos aplicar o desconto
        return valorOriginal - (valorOriginal * percentual / 100); //subtrai o percentual
    }

    public String getTipoCliente() { //retorna o tipo do cliente
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) { //atualiza o tipo do cliente e recalcula o percentual automatico
        this.tipoCliente = tipoCliente;
        this.percentual = definirPercentual(tipoCliente);
    }

    public double getPercentual() { //retorna o percentual de desconto atual
        return percentual;
    }

    @Override
    public String toString() {
        return "Tipo de cliente: " + tipoCliente //exibe o tipo de cliente
                + " | Percentual de desconto: " + percentual + "%"; //mostra o valor
    }
}
