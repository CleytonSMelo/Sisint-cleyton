<%--
  Created by IntelliJ IDEA.
  User: SINF
  Date: 10/10/2018
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="tagSisInt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<tags:layout>

    <jsp:attribute name="cabecalho">
        <meta http-equiv="refresh" content="300" url="${linkTo[InicioClienteController].index2}">
    </jsp:attribute>

    <jsp:attribute name="rodape">
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>
        <script src="https://code.highcharts.com/modules/export-data.js"></script>
<%--         <script src="${ctx}/resources/js/dashboard/dashboard.js"></script> --%>
    </jsp:attribute>
    <jsp:body>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Informações dos Serviços</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <div class="panel-body" style="padding-top: 0px;">
<%--                 <a class="btn btn-info" style="margin-bottom: 16px;" href="${linkTo[ServicosClienteController].form}">Cadastrar</a> --%>
                <div class="tabela-servicos">
                    <table class="table table-bordered tabela">
                        <thead>
                        <tr>
                            <th>Titulo</th>
                            <th>Prioridade</th>
                            <th>Data de Abertura</th>
                            <th>Prazo Final</th>
                            <th>Setor</th>
                            <th>Técnico</th>
                            <th>Status</th>
                            <th>Ações</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${servicos}" var="servico">
                            <tr>
                                <td>${servico.titulo}</td>
                                <td><span class="label label-prioridade">${servico.prioridade.chave}</span></td>
                                <td class="date-column">${servico.dataAbertura}</td>
                                <td class="date-column">${servico.dataFechamento}</td>
                                <td>${servico.setor.nome}</td>
                                <td>${servico.tecnico.nome}</td>
                                <td><span class="label label-status">${servico.statusServico.chave}</span></td>
                                <td>
                                    <a title="Detalhes" href="${linkTo[ServicosClienteController].detalhes}?id=${servico.id}">
                                        <i class="fa fa-eye fa-lg" aria-hidden="false"></i></a>
<%--                                     <c:if test="${usuarioLogado.isAdmin()}"> --%>
<%--                                         <a title="Log do serviço" href="${linkTo[ServicosController].logServico}?id=${servico.id}"> --%>
<!--                                             <i class="fa fa-list-ul fa-lg" aria-hidden="true"></i></a> -->
<%--                                     </c:if> --%>
                                    <a title="Editar" href="${linkTo[ServicosClienteController].editar}?id=${servico.id}">
                                        <i class="fa fa-pencil-square-o fa-lg" aria-hidden="false"></i></a>
                                    <a title="Assumir Serviço" class="assumir-servico" id-servico="${servico.id}" data-toggle="modal" href="#myModal">
                                        <i class="fa fa-check-circle-o fa-lg" aria-hidden="true"></i></a>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
            
            
            <!-- /.row -->
            <%-- <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-list-alt fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">${servicosAbertos}</div>
                                    <div>Serviços em Aberto</div>
                                </div>
                            </div>
                        </div>
                        <a href="${linkTo[ServicosController].servicosAbertos}">
                            <div class="panel-footer">
                                <span class="pull-left">Ver Serviços</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-file-text fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">${meusServicos}</div>
                                    <div>Meus Serviços</div>
                                </div>
                            </div>
                        </div>
                        <a href="${linkTo[ServicosController].meusServicos}">
                            <div class="panel-footer">
                                <span class="pull-left">Ver Meus Serviços</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-tasks fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">${tarefasPendentes}</div>
                                    <div>Minhas Tarefas</div>
                                </div>
                            </div>
                        </div>
                        <a href="${linkTo[TarefasController].minhasTarefas}">
                            <div class="panel-footer">
                                <span class="pull-left">Ver Pendentes</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-laptop fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">${manutencoesAbertas}</div>
                                    <div>Manutenções em Aberto</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                        <a href="${linkTo[ManutencaoController].lista}">
                            <div class="panel-footer">
                                <span class="pull-left">Ver Manutenções</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
            </div> --%>

            <!-- /.row -->
            <!-- <div class="row">

                <div class="col-lg-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Gráfico de Chamados
                        </div>
                        <!-- /.panel-heading -->
                       <!-- <div class="panel-body">
                            <div id="container-linha" style="height:200px;">
                            </div>
                        </div>
                        <!-- /.panel-body -->
                   <!-- </div>
                    <%--<div class="panel panel-default">--%>
                        <%--<div class="panel-heading">--%>
                            <%--<i class="fa fa-bar-chart-o fa-fw"></i> Gráfico de Chamados--%>
                        <%--</div>--%>
                        <%--<!-- /.panel-heading -->--%>
                        <%--<div class="panel-body">--%>
                            <%--<div id="container-pizza" style="height:200px;">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<!-- /.panel-body -->--%>
                    <%--</div>--%>
                    <%--<div class="panel panel-default">--%>
                        <%--<div class="panel-heading">--%>
                            <%--<i class="fa fa-bar-chart-o fa-fw"></i> Gráfico de Chamados--%>
                        <%--</div>--%>
                        <%--<!-- /.panel-heading -->--%>
                        <%--<div class="panel-body">--%>
                            <%--<div id="container-barra" style="height:200px;">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<!-- /.panel-body -->--%>
                    <%--</div>--%>
                </div>
                <!-- /.col-lg-8 -->
              <!--  <div class="col-lg-4">
                  <!--  <div class="panel panel-default">
                    <!--    <div class="panel-heading">
                         <!--   <i class="fa fa-bell fa-fw"></i> Últimas Tarefas
                        </div>
                        <!-- /.panel-heading -->
                      <!--  <div class="panel-body">
                         <!--   <div class="list-group">
                           <!--     <c:forEach items="${dezUltimasTarefas}" var="tarefa">
                                    <!--    <a href="${linkTo[TarefasController].detalhes}?id=${tarefa.id}" class="list-group-item">
                                       <!--     ${tarefa.titulo}
                                         <!--   <span class="pull-right text-muted small date-column"><em>${tarefa.dataFechamento}</em>
                                         <!--   </span>
                                         <!--   <span class="pull-right text-muted small" style="padding-right: 15px;"><em>${tarefa.tecnico.nome}</em>
                                        <!--    </span>
                                        </a>
                             <!--   </c:forEach>
                            <!-- </div>
                            <!-- /.list-group -->
                          <!--  <a href="${linkTo[TarefasController].lista}" class="btn btn-default btn-block">Ver Todas as Tarefas</a>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
           <!-- </div>
            <!-- /.row -->
    </jsp:body>
</tags:layout>