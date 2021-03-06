
package br.pcrn.sisint.dao;

import br.pcrn.sisint.dominio.Servico;
import br.pcrn.sisint.dominio.Usuario;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by samue on 08/09/2017.
 */
public interface UsuarioDao extends EntidadeDao<Usuario> {

    Optional<Usuario> buscarPorLogin(String cpf);
    List<Usuario> listarAptos();
    
    @Override
    List<Usuario> listar();
    
    @Override
    List<Usuario> listar2();
    
    //Listar todos os usuarios por setor
    List<Usuario> listarTodososUsuariosPorSetor(Long id);
    
}
