package br.pcrn.sisint.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.pcrn.sisint.anotacoes.Seguranca;
import br.pcrn.sisint.anotacoes.Transacional;
import br.pcrn.sisint.dao.SetorDao;
import br.pcrn.sisint.dao.UsuarioDao;
import br.pcrn.sisint.dominio.Servico;
import br.pcrn.sisint.dominio.TipoUsuario;
import br.pcrn.sisint.dominio.Usuario;
import br.pcrn.sisint.dominio.UsuarioLogado;
import br.pcrn.sisint.negocio.ServicosNegocio;
import br.pcrn.sisint.negocio.SetorNegocio;
import br.pcrn.sisint.util.Criptografia;
import br.pcrn.sisint.util.OpcaoSelect;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by samue on 09/09/2017.
 */

@Controller
public class UsuariosController extends ControladorSisInt<Usuario> {

    UsuarioDao usuarioDao;
    private UsuarioLogado usuarioLogado;
    private SetorDao setorDao;
    private SetorNegocio setorNegocio;
    
   
    /**
     * @deprecated CDI eyes only
     */
    protected UsuariosController() {
        this(null, null, null, null,null);
    }

    @Inject
    public UsuariosController(UsuarioDao usuarioDao, Result resultado, UsuarioLogado usuarioLogado, SetorNegocio setorNegocio,SetorDao setorDao) {
        super(resultado);
        this.usuarioDao = usuarioDao;
        this.usuarioLogado = usuarioLogado;
        this.setorNegocio = setorNegocio;
        this.setorDao = setorDao;
    }

    @Seguranca(tipoUsuario = TipoUsuario.ADMINISTRADOR)
    public void form(){
        this.resultado.include("tipoUsuario", OpcaoSelect.toListaOpcoes(TipoUsuario.values()));
        this.resultado.include("setores", setorNegocio.gerarListaOpcoesSetor());
    }

    @Post("/usuarios")
    @Transacional
    public void salvar(Usuario usuario){
        usuario.setDataCadastro(LocalDate.now());
        usuario.setDeletado(false);
        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        usuario.setSetor(usuario.getSetor());
        this.usuarioDao.salvar(usuario);
        resultado.include("mensagem", new SimpleMessage("success","mensagem.salvar.sucesso"));
        resultado.redirectTo(UsuariosController.class).lista();
    }

    @Transacional
    public void atualizar(Usuario usuario){
        usuario.setDataCadastro(LocalDate.now());
        this.usuarioDao.salvar(usuario);
        resultado.include("mensagem", new SimpleMessage("success","mensagem.salvar.sucesso"));
        resultado.redirectTo(InicioController.class).index();
    }

    @Seguranca(tipoUsuario = TipoUsuario.ADMINISTRADOR)
    public void lista() {
    	
    	List<Usuario> usuario = this.usuarioDao.listar();
        resultado.include("usuarios", usuario);
    	
        //resultado.include("usuarios", usuarioDao.listar());
        
    }

    @Seguranca(tipoUsuario = TipoUsuario.ADMINISTRADOR)
    public void editar(Long id) {
        Usuario usuario = usuarioDao.buscarPorId(id);
        this.resultado.include("tipoUsuario", OpcaoSelect.toListaOpcoes(TipoUsuario.values()));
        this.resultado.include("setores", setorNegocio.gerarListaOpcoesSetor());
        this.resultado.include("usuario", usuario);
        resultado.of(this).form();
    }

    @Transacional
    public void trocarSenha(Usuario usuario) {
        Usuario usuarioBanco = usuarioDao.buscarPorId(usuario.getId());
        if(Criptografia.criptografar(usuario.getSenha()).equals(usuarioBanco.getSenha()) && usuario.getNovaSenha().equals(usuario.getConfirmaSenha())) {
            usuarioBanco.setSenha(Criptografia.criptografar(usuario.getNovaSenha()));
            resultado.include("mensagem", new SimpleMessage("success","mensagem.salvar.sucesso"));
            
            if(usuarioLogado.isAdmin()|| usuarioLogado.isTecnico()) {
            	 resultado.redirectTo(InicioController.class).index();
            }else {
            	resultado.redirectTo(InicioClienteController.class).index2();
            }
                                 
        } else {
            resultado.include("mensagem", new SimpleMessage("error","mensagem.dadoIncorreto"));
            resultado.redirectTo(PerfilController.class).form(usuario.getId());
        }

    }

    @Seguranca(tipoUsuario = TipoUsuario.ADMINISTRADOR)
    @Transacional
    public void remover(Long id) {
        try {
            Usuario usuario = usuarioDao.buscarPorId(id);
            usuario.setDeletado(true);
            usuarioDao.salvar(usuario);
            resultado.include("mensagem", new SimpleMessage("success","mensagem.usuario.remover.sucesso"));
            resultado.redirectTo(this).lista();
        } catch (Exception e) {
            resultado.include("mensagem", new SimpleMessage("error", "mensagem.ususario.remover.error"));
            resultado.redirectTo(this).lista();
        }
    }


    private String criptografarSenha(String senha) {
        return Criptografia.criptografar(senha);
    }



}
