package controller;

import dao.CategoriaFinanceiraDAO;
import model.CategoriaFinanceira;
import util.Sessao;
import java.util.List;

public class CategoriaFinanceiraController {
    private CategoriaFinanceiraDAO dao;

    public CategoriaFinanceiraController() {
        dao = new CategoriaFinanceiraDAO();
    }

    public List<CategoriaFinanceira> listarCategorias() {
        return dao.listar();
    }

    // certifique-se de importar isso

    public void adicionarCategoria(String nome) {
        int idUsuario = Sessao.getIdUsuario();
        dao.adicionar(new CategoriaFinanceira(nome, idUsuario));
    }


    public void editarCategoria(int id, String novoNome) {
        dao.editar(id, novoNome);
    }

    public void excluirCategoria(int id) {
        dao.excluir(id);
    }
}
