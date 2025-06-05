package dao;
import model.CategoriaFinanceira;
import java.util.List;

public interface CategoriaFinanceiraDAO {
    List<CategoriaFinanceira> listar();
    void adicionar(CategoriaFinanceira categoria);
    void editar(int id, String novoNome);
    void salvar(CategoriaFinanceira categoria);
    void excluir(int id);
}