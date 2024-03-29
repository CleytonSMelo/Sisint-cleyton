package br.pcrn.sisint.controller;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.pcrn.sisint.anotacoes.Seguranca;
import br.pcrn.sisint.anotacoes.Transacional;
import br.pcrn.sisint.dao.*;
import br.pcrn.sisint.dominio.*;
import br.pcrn.sisint.negocio.ServicosNegocio;
import br.pcrn.sisint.negocio.SetorNegocio;
import br.pcrn.sisint.util.OpcaoSelect;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Seguranca(tipoUsuario = TipoUsuario.TECNICO)
@Controller
public class ServicosClienteController extends ControladorSisInt<Servico> {

    private ServicoDao servicoDao;
    private Validator validador;
    private UsuarioDao usuarioDao;
    private ServicosNegocio servicosNegocio;
    private EntidadeDao<Servico> dao;
    private SetorDao setorDao;

    @Inject
    private ServletContext context;

    @Inject
    private UsuarioLogado usuarioLogado;

    @Inject
    private TarefaJpaDao tarefaJpaDao;

    @Inject
    private SetorNegocio setorNegocio;

    private Setor setor;
    /**
     * @deprecated CDI eyes only
     */
    protected ServicosClienteController() {
        this(null, null, null, null, null, null, null, null);
    }

    @Inject
    public ServicosClienteController(Result resultado, EntidadeDao<Servico> dao, ServicoDao servicoDao, Validator validador, UsuarioDao usuarioDao,
                              ServicosNegocio servicosNegocio, SetorDao setorDao, Setor setor) {
        super(resultado);
        this.dao = dao;
        this.servicoDao = servicoDao;
        this.validador = validador;
        this.usuarioDao = usuarioDao;
        this.servicosNegocio = servicosNegocio;
        this.setorDao = setorDao;
        this.setor = setor;
    }
   // @Path("/servicosCliente")
    public void form() {
        resultado.include("usuarios", servicosNegocio.geraListaOpcoesUsuarios());
        //resultado.include("setores", servicosNegocio.geraListaOpcoesSetorCli());
        resultado.include("setores", setorDao.SetorServicos(usuarioLogado.getUsuario().getSetor().getId()));
        resultado.include("status", OpcaoSelect.toListaOpcoes(StatusServico.values()));
        resultado.include("statusTarefa", OpcaoSelect.toListaOpcoes(StatusTarefa.values()));
        resultado.include("prioridades", OpcaoSelect.toListaOpcoes(Prioridade.values()));
        
        resultado.include("verificar", servicoDao.listarPorSetorEmAberto(usuarioLogado.getUsuario().getSetor().getId()));
    }


//    @Post("/servicosCliente")
//    @Transacional
//    public void salvar(Servico servico) {
//        try {
//            //Atribui data de abertura de chamado e caso não haja um técnico reponsável, torna nula a variável de usuário
//            if (servico.getId() == null) {
//                servico.setDataAbertura(LocalDate.now());
//                if (servico.getTecnico().getId() == null) {
//                    servico.setTecnico(null);
//                }
//            }
//            // Atribui Status do serviço
//            if (servico.getTecnico() == null) {
//                servico.setStatusServico(StatusServico.EM_ESPERA);
//            } else {
//                servico.setStatusServico(StatusServico.EM_EXECUCAO);
//            }
//            //Gera o código de serviço
//            if (servico.getCodigoServico() == null) {
//                servico.setCodigoServico(servicosNegocio.gerarCodigoServico());
//                if (servico.getTarefas() != null) {
//                    if (!servico.getTarefas().isEmpty()) {
//                        servicosNegocio.gerarCodigoTarefas(servico.getCodigoServico(), servico.getTarefas());
//                    }
//                }
//            } else {
//                if (servico.getTarefas() != null) {
//                    servicosNegocio.gerarCodigoTarefas(servico.getCodigoServico(), servico.getTarefas());
//                }
//            }
//            //Gera Log do serviço
//            servicosNegocio.gerarLog(servico);
//            if (servico.getTarefas() != null) {
//                if (servicosNegocio.verificarConclusaoServico(servico.getTarefas())) {
//                    servico.setStatusServico(StatusServico.CONCLUIDO);
//                    LogServico logServico = new LogServico();
//                    logServico.setLog("Servico " + servico.getCodigoServico() + " foi concluído.");
//                    logServico.setServico(servico);
//                    logServico.setDataAlteracao(LocalDateTime.now());
//                    logServico.setUsuario(usuarioLogado.getUsuario());
//                    servico.getLogServicos().add(logServico);
//                }
//            }
//
//            this.servicoDao.salvar(servico);
//            resultado.include("mensagem", new SimpleMessage("success", "mensagem.salvar.sucesso"));
//            resultado.redirectTo(this).editar(servico.getId());
//        } catch (Exception e) {
//            resultado.include("mensagem", new SimpleMessage("error", "mensagem.salvar.error"));
//            resultado.redirectTo(this).editar(servico.getId());
//        }
//    }

  @Post("/servicosCliente")
  @Transacional
  public void salvar(Servico servico) {
	  
  try {
	  if (servico.getId() == null) {
          servico.setDataAbertura(LocalDate.now());
          //servico.setTecnico(null);       
          servico.setDataFechamento(LocalDate.now());
          servico.setStatusServico(StatusServico.EM_ESPERA);  
          servico.setCodigoServico(servicosNegocio.gerarCodigoServico());
          servico.setPrioridade(Prioridade.AGUARDANDO_ANALISE);
          servico.setSetor(servico.getSetor());
          servico.setTelefoneRetorno(servico.getTelefoneRetorno());
          servicosNegocio.gerarLog(servico);
          this.servicoDao.salvar(servico);
          
      }
	  
	  if (servico.getCodigoServico() != null) {		  
		  servico.setDataFechamento(servico.getDataFechamento());
    	  servico.setStatusServico(servico.getStatusServico());
    	  servico.setDataFechamento(servico.getDataFechamento());
    	  servico.setPrioridade(servico.getPrioridade());
    	  servico.setSetor(servico.getSetor());
          servico.setTelefoneRetorno(servico.getTelefoneRetorno());
          this.servicoDao.salvar(servico);
      }
	  
	 
	  
	  
      
//          //Atribui data de abertura de chamado e caso não haja um técnico reponsável, torna nula a variável de usuário
//          if (servico.getId() == null) {
//              servico.setDataAbertura(LocalDate.now());
//              if (servico.getTecnico().getId() == null) {
//                  servico.setTecnico(null);
//              }
//          }
//          // Atribui Status do serviço
//          if (servico.getTecnico() == null) {
//              servico.setStatusServico(StatusServico.EM_ESPERA);
//          } else {
//              servico.setStatusServico(StatusServico.EM_EXECUCAO);
//          }
//          //Gera o código de serviço
//          if (servico.getCodigoServico() == null) {
//              servico.setCodigoServico(servicosNegocio.gerarCodigoServico());
//              if (servico.getTarefas() != null) {
//                  if (!servico.getTarefas().isEmpty()) {
//                      servicosNegocio.gerarCodigoTarefas(servico.getCodigoServico(), servico.getTarefas());
//                  }
//              }
//          } else {
//              if (servico.getTarefas() != null) {
//                  servicosNegocio.gerarCodigoTarefas(servico.getCodigoServico(), servico.getTarefas());
//              }
//          }
//          //Gera Log do serviço
//          servicosNegocio.gerarLog(servico);
//          if (servico.getTarefas() != null) {
//              if (servicosNegocio.verificarConclusaoServico(servico.getTarefas())) {
//                  servico.setStatusServico(StatusServico.CONCLUIDO);
//                  LogServico logServico = new LogServico();
//                  logServico.setLog("Servico " + servico.getCodigoServico() + " foi concluído.");
//                  logServico.setServico(servico);
//                  logServico.setDataAlteracao(LocalDateTime.now());
//                  logServico.setUsuario(usuarioLogado.getUsuario());
//                  servico.getLogServicos().add(logServico);
//              }
//          }
//
//          this.servicoDao.salvar(servico);
          resultado.include("mensagem", new SimpleMessage("success", "mensagem.salvar.sucesso"));
          //resultado.redirectTo(this).editar(servico.getId());
          resultado.redirectTo(this).servicosAbertos();
      } catch (Exception e) {
          resultado.include("mensagem", new SimpleMessage("error", "mensagem.salvar.error"));
          resultado.redirectTo(this).editar(servico.getId());
      }
  }
    

//    public void logServico(Long id) {
//        Servico servico = servicoDao.BuscarPorId(id);
//        resultado.include("listaLogs", servico.getLogServicos());
//    }
//
//    @Seguranca(tipoUsuario = TipoUsuario.ADMINISTRADOR)
//    public void lista() {
//        List<Servico> servicos = this.servicoDao.listar();
//        resultado.include("servicos", servicos);
//    }
// //lista seriços por usuario
    public void meusServicos() {
        List<Servico> servicos = this.servicoDao.listarMeusServicos(usuarioLogado.getUsuario().getId());
        resultado.include("servicos", servicos);
    }
    
    //listar serviços por setor
    public void setorServicos() {
      List<Servico> servicos = this.servicoDao.listarPorSetorTodos(usuarioLogado.getUsuario().getSetor().getId());
      resultado.include("servicos", servicos);
    }
    
//    @Path("/servicosAberto")
//    public void servicosAbertos() {
//        List<Servico> servicos = this.servicoDao.listarServicosEmAberto();
//        resultado.include("servicos", servicos);
//    }
    
   // lista serviços por setor para o cliente
    @Path("/servicosAberto")
  public void servicosAbertos() {
    	List<Servico> servicos = this.servicoDao.listarPorSetorEmAberto(usuarioLogado.getUsuario().getSetor().getId());
        resultado.include("servicos", servicos);
  }
    
    //Verificar serviço aberto no setor
//     public void verificarServicosAbertos() {
//    	List<Servico> servicos = this.servicoDao.listarPorSetorEmAberto(usuarioLogado.getUsuario().getSetor().getId());
//        resultado.include("verificar", servicos);
//    }

    public void detalhes(Long id) {
        Servico servico = servicoDao.BuscarPorId(id);
        resultado.include("servico", servico);
    }

//    @Get
//    public void listaTarefas(Long id) {
//        Servico servico = servicoDao.BuscarPorId(id);
//        JsonArray listaServicos = new JsonArray();
//
//        if (servico != null) {
//            if(servico.getTarefas() != null) {
//                for (Tarefa tarefa : servico.getTarefas()) {
//                    if (!tarefa.isDeletado()) {
//                        JsonObject jsonObject = new JsonObject();
//                        String pendente;
//                        if (tarefa.isPendente()) {
//                            pendente = "true";
//                        } else {
//                            pendente = "false";
//                        }
//                        jsonObject.addProperty("id", tarefa.getId());
//                        jsonObject.addProperty("titulo", tarefa.getTitulo());
//                        jsonObject.addProperty("statusValor", tarefa.getStatusTarefa().getValor());
//                        jsonObject.addProperty("statusChave", tarefa.getStatusTarefa().getChave());
//                        jsonObject.addProperty("dataFechamento", tarefa.getDataFechamento().toString());
//                        jsonObject.addProperty("descricao", tarefa.getDescricao());
//                        jsonObject.addProperty("servicoId", tarefa.getServico().getId());
//                        jsonObject.addProperty("tecnicoId", tarefa.getTecnico().getId());
//                        jsonObject.addProperty("tecnicoNome", tarefa.getTecnico().getNome());
//                        jsonObject.addProperty("codigoTarefa", tarefa.getCodigoTarefa());
//                        jsonObject.addProperty("dataAbertura", tarefa.getDataAbertura().toString());
//                        jsonObject.addProperty("pendente", pendente);
//                        listaServicos.add(jsonObject);
//                    }
//                }
//            }
//            resultado.use(Results.json()).withoutRoot().from(listaServicos).recursive().serialize();
//        } else {
//            resultado.use(Results.json()).withoutRoot().from(listaServicos).recursive().serialize();
//        }
//    }

//    @Get
//    public void listaLogs(Long id) {
//        Servico servico = servicoDao.BuscarPorId(id);
//        JsonArray listaServicos = new JsonArray();
//
//        if (servico != null) {
//            for (LogServico logServico : servico.getLogServicos()) {
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("id", logServico.getId());
//                jsonObject.addProperty("titulo", logServico.getLog());
//                jsonObject.addProperty("dataFechamento", logServico.getDataAlteracao().toString());
//                jsonObject.addProperty("servico", logServico.getServico().getId());
//                jsonObject.addProperty("usuario", logServico.getUsuario().getId());
//                listaServicos.add(jsonObject);
//            }
//            resultado.use(Results.json()).withoutRoot().from(listaServicos).recursive().serialize();
//        } else {
//
//        }
//    }
//
    public void editar(Long id) {
        if (id == null) {
            resultado.redirectTo(InicioClienteController.class).index2();
        } else {
            Servico servico = this.servicoDao.BuscarPorId(id);
           // resultado.include("setores", servicosNegocio.geraListaOpcoesSetor());
            resultado.include("setores", setorDao.SetorServicos(usuarioLogado.getUsuario().getSetor().getId()));
            resultado.include("usuarios", servicosNegocio.geraListaOpcoesUsuarios());
            resultado.include("status", OpcaoSelect.toListaOpcoes(StatusServico.values()));
            resultado.include("statusTarefa", OpcaoSelect.toListaOpcoes(StatusTarefa.values()));
            resultado.include("prioridades", OpcaoSelect.toListaOpcoes(Prioridade.values()));
            resultado.include("listaLogs", servico.getLogServicos());
           // resultado.include("sts", servico.getStatusServico());
            resultado.include(servico);
            resultado.of(this).form();
        }
    }

//    @Path("/remover")
//    @Transacional
//    public void remover(Long id) {
//        Servico servico = this.dao.buscarPorId(id);
////        dao.remover(servico);
//        servico.getTarefas().stream().forEach((tarefa -> tarefa.setDeletado(true)));
//        servico.setDeletado(true);
//        resultado.redirectTo(InicioController.class).index();
//    }

//    @Path("/assumirServico")
//    @Transacional
//    public void assumirServico(Long id) {
//        Servico servico = servicoDao.BuscarPorId(id);
//        servico.setTecnico(usuarioLogado.getUsuario());
//        servico.setStatusServico(StatusServico.EM_EXECUCAO);
//        servicosNegocio.gerarLogAssumirServico(servico);
//        servicoDao.salvar(servico);
//        resultado.redirectTo(ServicosClienteController.class).meusServicos();
//    }

//    @Path("/assumirServicoComTarefa")
//    @Transacional
//    public void assumirServicoComTarefa(Long id) {
//        Servico servico = servicoDao.BuscarPorId(id);
//        servico.setTecnico(usuarioLogado.getUsuario());
//        servico.setStatusServico(StatusServico.EM_EXECUCAO);
//        List<Tarefa> tarefas = new ArrayList<>();
//        Tarefa tarefa = new Tarefa();
//        String codigoTarefa = servico.getCodigoServico() + "T" + tarefaJpaDao.contarTotalTarefas();
//        tarefa.setCodigoTarefa(codigoTarefa);
//        tarefa.setDataAbertura(LocalDate.now());
//        tarefa.setDescricao(servico.getDescricao());
//        tarefa.setTitulo(servico.getTitulo());
//        tarefa.setDataFechamento(servico.getDataFechamento());
//        tarefa.setTecnico(servico.getTecnico());
//        tarefa.setDeletado(false);
//        tarefa.setServico(servico);
//        tarefa.setStatusTarefa(StatusTarefa.EM_ESPERA);
//        tarefas.add(tarefa);
//        servico.setTarefas(tarefas);
//        servicoDao.salvar(servico);
//        resultado.redirectTo(ServicosClienteController.class).meusServicos();
//    }
    
    public void buscarSetorJson(Long id) {
        Setor setor = setorDao.buscarPorId(id);
        JsonObject telJson = new JsonObject();
        telJson.addProperty("telJson", setor.getTelefone());

        resultado.use(Results.json()).withoutRoot().from(telJson).recursive().serialize();
    }
//
//    public void formRelatorio() {
//        resultado.include("setores", setorNegocio.gerarListaOpcoesSetor());
//    }

//    @Post
//    public void relatorio(String dtDe, String dtAte) {
//        List<Object[]> info = servicosNegocio.filtrarRelatorio(dtDe, dtAte);
//        resultado.include("dtDe" , dtDe);
//        resultado.include("dtAte" , dtAte);
//        resultado.include("servicos", info);
//        resultado.redirectTo(this).formRelatorio();
//   }

//    @Get
//    @Path("/imprimirProdutos")
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    public Download imprimirProdutos() {
//        List<Servico> lista = servicoDao.listar();
//        Report report = new ReportJasperServico<Servico>(lista, "termoResponsabilidade.jasper", context);
//        ReportDownload download = new ReportDownload(report, ExportFormats.pdf(), false);
//        return download;
//
//    }
}
