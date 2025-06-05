package model;

import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity

@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    public Usuario() {}

    public Usuario(int id, String nome, String email, String senha, String telefone, String cpf, String dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public Usuario(String email, String senha, String nome, String telefone, String cpf, String dataNascimento) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }



    public int getId() { return id; }



    public String getEmail() { return email; }


    public String getSenha() { return senha; }


}
