package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String categoria;
    private String descricao;
    private String dataTexto; // salvando a data como texto
    private double valor;

    @ManyToOne
    private Usuario usuario;

    public Transacao() {}

    public Transacao(String tipo, String categoria, String descricao, String dataTexto, double valor) {
        this.tipo = tipo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.dataTexto = dataTexto;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataTexto() {
        return dataTexto;
    }

    public void setDataTexto(String dataTexto) {
        this.dataTexto = dataTexto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
