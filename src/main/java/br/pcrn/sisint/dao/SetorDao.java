package br.pcrn.sisint.dao;

import br.pcrn.sisint.dominio.Servico;
import br.pcrn.sisint.dominio.Setor;

import java.util.List;

public interface SetorDao extends EntidadeDao<Setor> {

    @Override
    List<Setor> listar();
    
    
    //referencia o listar 2 no setorjpaDao
    @Override
    List<Setor> listar2();

    @Override
    List<Setor> todos();
    
    @Override
    List<Setor> todos2();


	List<Servico> SetorServicos(Long id);
}
