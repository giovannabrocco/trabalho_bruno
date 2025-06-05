package controller;
import dao.CategoriaFinanceiraDAO;
import dao.CategoriaFinanceiraDAOImpl;
import model.CategoriaFinanceira;
import util.Sessao;
import view.CategoriaFinanceiraView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoriaFinanceiraController {
    private final CategoriaFinanceiraView view;
    private final CategoriaFinanceiraDAO dao;


    public CategoriaFinanceiraController(CategoriaFinanceiraView view) {
        this.view = view;
        this.dao = new CategoriaFinanceiraDAOImpl();


        this.view.addSalvarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCategoria();
            }
        });

        this.view.addExcluirListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCategoria();
            }
        });


        carregarCategorias();
    }




    private void salvarCategoria() {
        String nome = view.getCampoNome().trim();
        if (!nome.isEmpty()) {
            CategoriaFinanceira categoria = new CategoriaFinanceira();
            categoria.setNome(nome);

            categoria.setIdUsuario(Sessao.getIdUsuario());

            try {
                dao.salvar(categoria);
                carregarCategorias();
                view.limparCampos();



                view.dispose();

            } catch (Exception ex) {
              view.mostrarMensagem("Erro ao salvar categoria: " + ex.getMessage());
             ex.printStackTrace();
            }
        } else {
            view.mostrarMensagem("Digite um nome para a categoria.");
        }
    }




    private void excluirCategoria() {
        int id = view.getIdCategoriaSelecionada();
        if (id != -1) {
            try {

                dao.excluir(id);
                carregarCategorias();
                view.mostrarMensagem("Categoria excluída com sucesso!");
            } catch (Exception ex) {
                view.mostrarMensagem("Erro ao excluir categoria: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            view.mostrarMensagem("Selecione uma categoria na tabela para excluir.");
        }
    }



    private void carregarCategorias() {
        try {
            List<CategoriaFinanceira> categorias = dao.listar();
            view.preencherTabela(categorias);
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao carregar categorias: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

