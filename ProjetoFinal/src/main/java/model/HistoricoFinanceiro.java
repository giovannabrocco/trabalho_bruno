package model;

import javax.persistence.*;


@Entity
@Table(name = "historico_financas")
public class HistoricoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acao;
    private String dataHora;

    @ManyToOne
    private Usuario usuario;

    public HistoricoFinanceiro() {}

    public HistoricoFinanceiro(String acao, String dataHora) {
        this.acao = acao;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
