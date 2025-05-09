    public class Transacao {
    private String tipo;
    private String categoria;
    private String descricao;
    private double valor;
    private String data;


    public Transacao(String tipo, String categoria, String descricao, double valor, String data) {
        this.tipo = tipo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getDescricao() {
        return descricao;
    }
    public double getValor() {
        return valor;
    }
    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return tipo + "," + categoria + "," + descricao + "," + valor + "," + data;
    }
}
