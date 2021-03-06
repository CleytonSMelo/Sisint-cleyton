package br.pcrn.sisint.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.pcrn.sisint.anotacoes.Seguranca;
import br.pcrn.sisint.dao.EquipamentoDao;
import br.pcrn.sisint.dao.ManutencaoDao;
import br.pcrn.sisint.dao.ServicoDao;
import br.pcrn.sisint.dao.TarefaDao;
import br.pcrn.sisint.dao.UsuarioDao;
import br.pcrn.sisint.dominio.StatusManutencao;
import br.pcrn.sisint.dominio.StatusServico;
import br.pcrn.sisint.dominio.TipoUsuario;
import br.pcrn.sisint.dominio.UsuarioLogado;
import br.pcrn.sisint.negocio.DashboardNegocio;
import com.google.gson.JsonElement;

import javax.inject.Inject;
import java.time.LocalDate;

@Path("/externo")

@Seguranca(tipoUsuario = TipoUsuario.CLIENTE)
@Controller
public class InicioClienteController extends Controlador {
    
    private ServicoDao servicoDao;
    private TarefaDao tarefaDao;
    private ManutencaoDao manutencaoDao;
    private EquipamentoDao equipamentoDao;
    private DashboardNegocio dashboardNegocio;
    private UsuarioDao usuarioDao;
    
    @Inject
    private Validator validator;
    
    @Inject
    private UsuarioLogado usuarioLogado;

    protected InicioClienteController() {
        this(null, null, null, null, null, null, null);
    }
    
    
    @Inject
    public InicioClienteController(Result resultado, ServicoDao servicoDao, TarefaDao tarefaDao, ManutencaoDao manutencaoDao,
                            EquipamentoDao equipamentoDao, DashboardNegocio dashboardNegocio, UsuarioDao usuarioDao) {
        super(resultado);
        this.servicoDao = servicoDao;
        this.tarefaDao = tarefaDao;
        this.manutencaoDao = manutencaoDao;
        this.equipamentoDao = equipamentoDao;
        this.dashboardNegocio = dashboardNegocio;
        this.usuarioDao = usuarioDao;
    }

//    public void informacoesDashboard() {
//        JsonElement informacoes = this.dashboardNegocio.dashServicos();
//        resultado.use(Results.json()).withoutRoot().from(informacoes).serialize();
//    }
    
    @Path("/home")
    public void index2(){
		/*
		 * resultado.include("totalServicos",servicoDao.contarTotalServicos());
		 * resultado.include("servicosAbertos",servicoDao.contarServicosStatus(
		 * StatusServico.EM_ESPERA));
		 * resultado.include("servicosExecucao",servicoDao.contarServicosStatus(
		 * StatusServico.EM_EXECUCAO));
		 * resultado.include("totalTarefas",tarefaDao.contarTotalTarefas());
		 * 
		 * resultado.include("totalManutencoes",manutencaoDao.contarTotalManutencoes());
		 * resultado.include("manutencoesAbertas",manutencaoDao.contarManutencoesStatus(
		 * StatusManutencao.AGUARDANDO_MANUTENCAO));
		 * resultado.include("totalEquipamentos",equipamentoDao.contarTotalEquipamentos(
		 * )); resultado.include("tarefasPendentes", tarefaDao.minhasTarefas().size());
		 * resultado.include("meusServicos", servicoDao.meusServicos());
		 * 
		 * resultado.include("dezUltimasTarefas", tarefaDao.buscarDezUltimas());
		 */
    	//resultado.include("meusServicos", servicoDao.meusServicos());
    	//carrega a lista de serviços do cliente
    	resultado.include("usuariosSetor", usuarioDao.listarTodososUsuariosPorSetor(usuarioLogado.getUsuario().getSetor().getId()));
    	resultado.include("todosServicos", servicoDao.listarPorSetorUltimosAberto(usuarioLogado.getUsuario().getSetor().getId()));
    }
   
//    @Path("/info")
//    @Get
//    public void info() {
//        JsonElement informacoes = dashboardNegocio.servicosPorSetor();
//        resultado.use(Results.json()).withoutRoot().from(informacoes).serialize();
//    }

    @Path("/informacoesexterno")
    @Get
    public void informacoes() {

    }


}
