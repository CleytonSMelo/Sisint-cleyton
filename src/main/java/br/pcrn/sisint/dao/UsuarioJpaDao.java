package br.pcrn.sisint.dao;

import br.pcrn.sisint.dominio.Servico;
import br.pcrn.sisint.dominio.Usuario;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by samue on 08/09/2017.
 */
public class UsuarioJpaDao extends EntidadeJpaDao<Usuario> implements UsuarioDao {

    /**
     * @deprecated CDI
     */
    @Deprecated
    public UsuarioJpaDao() {
        this(null);
    }

    @Inject
    public UsuarioJpaDao(EntityManager entityManager) {
        super(entityManager, Usuario.class);
    }
    // faz o controle o login, mas com consulta por setor, se estiver deletado o mesmo não efetuara o login
    @Override
    public Optional<Usuario> buscarPorLogin(String cpf) {
           // Query query = this.manager.createQuery("SELECT p from Usuario p where p.login = :login AND p.deletado = false");
            Query query = this.manager.createQuery("SELECT p from Usuario p "+"join Setor s "+"on p.setor.id = s.id"+" where p.cpf = :cpf AND p.deletado = false AND s.deletado = false");
            query.setParameter("cpf",cpf);
            return query.setMaxResults(1).getResultList().stream().findFirst();
    }
    
    public List<Usuario> listarTodososUsuariosPorSetor(Long id) {
        Query query = manager.createQuery("select u from Usuario u "+  
                                             "where u.deletado = false AND u.setor.id = :id "+" ORDER BY u.id DESC");
        query.setParameter("id", id);
        return query.getResultList();
    }

//    @Override
//    public List<Usuario> listarAptos() {
//        Query query = this.manager.createQuery("SELECT u FROM Usuario u WHERE u.id <> 1");
//        return query.getResultList();
//    }
    
//    @Override
//    public List<Usuario> listarAptos() {
//        Query query = this.manager.createQuery("SELECT u FROM Usuario u WHERE u.id <> 1 AND u.deletado = false"); 
//        return query.getResultList();
//    }
    
    @Override
    public List<Usuario> listarAptos() {
        Query query = this.manager.createQuery("SELECT u FROM Usuario u WHERE u.tipoUsuario='ADMINISTRADOR' or u.tipoUsuario='TECNICO' AND u.deletado = false ORDER BY u.id"); 
        return query.getResultList();
    }
    
    @Override
    public List<Usuario> listar() {
        return super.listar().stream().collect(Collectors.toList());
    }
    
    @Override
    public List<Usuario> listar2() {
        return super.listar2().stream().collect(Collectors.toList());
    }
    

}
