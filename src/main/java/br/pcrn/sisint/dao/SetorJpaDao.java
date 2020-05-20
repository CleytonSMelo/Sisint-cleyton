package br.pcrn.sisint.dao;

import br.pcrn.sisint.dominio.Servico;
import br.pcrn.sisint.dominio.Setor;
import br.pcrn.sisint.dominio.Usuario;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;
import java.util.stream.Collectors;

public class SetorJpaDao extends EntidadeJpaDao<Setor> implements SetorDao{
    /**
     * @deprecated CDI
     */
    @Deprecated
    public SetorJpaDao() {
        this(null);
    }

    @Inject
    public SetorJpaDao(EntityManager entityManager) {
        super(entityManager, Setor.class);
    }

    @Override
    public List<Setor> listar() {
        return super.listar().stream().collect(Collectors.toList());
    }

    @Override
    public List<Setor> todos() {
        return super.todos().stream().collect(Collectors.toList());
    }
    
    //lista todos os setores para o cliente se não estiverem desabilitados
    @Override
    public List<Setor> todos2() {
        return super.todos2().stream().collect(Collectors.toList());
    }
    
    //adicionado para listar todos os setores
    @Override
    public List<Setor> listar2() {
        return super.listar2().stream().collect(Collectors.toList());
    }
    
    @Override
    public List<Servico> SetorServicos(Long id) {
        Query query = manager.createQuery("select s from Setor s " +
                                             "where s.id = :id");
        query.setParameter("id", id);
        return query.getResultList();
    }
}
