
package dao;

import java.util.List;

public interface GenericDAO<T> {
    void salvar(T entidade);
    void atualizar(T entidade);
    void deletar(T entidade);
    T buscarPorId(Long id);
    List<T> buscarTodos();
}
