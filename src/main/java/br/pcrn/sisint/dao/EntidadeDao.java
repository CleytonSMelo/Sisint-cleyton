package br.pcrn.sisint.dao;

import br.pcrn.sisint.dominio.Entidade;

import java.util.Collection;

public interface EntidadeDao<T extends Entidade> {

    T buscarPorId(Long id);
    T salvar(T entidade);
    void remover(T entidade);
  //  void ativar(T entidade);
    Collection<T> listar();
    Collection<T> listar2();
    Collection<T> todos();
    
    //Mostrar setor para clientes
    Collection<T> todos2();
}