package org.example.DAO;

import java.util.List;

public interface GenericsDAO<E,K>{

    void inserir(E entidade);

    void remover(K id);

    void atualizar(E entidade);

    E buscarPorId(K id);

    List<E> buscarTodos();

}
