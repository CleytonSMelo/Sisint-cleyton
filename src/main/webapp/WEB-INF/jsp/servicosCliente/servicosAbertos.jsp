<%--
  Created by IntelliJ IDEA.
  User: samue
  Date: 08/09/2017
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" uri="tagSisInt" %>
<%@ taglib prefix="td" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<tags:layout>
    <jsp:attribute name="cabecalho">

    </jsp:attribute>
    <jsp:attribute name="rodape">
        <script src="${ctx}/resources/plugins/dataTables/datatables.js"><c:out value=""/></script>
        <script src="${ctx}/resources/plugins/dataTables/Buttons-1.4.2/js/buttons.html5.js"><c:out value=""/></script>
        <%--<script src="${ctx}/resources/js/init.js"></script>--%>
        <script>
            var idServico = "";
            var url = $('#btnSalvarTarefa').attr('href');
            var urlServicoPadrao = $('#urlServicoPadrao').val();
//            var $btnAssumir = $('#assumir-servico');
//            atribuirListennerBtnEdicao();
//            function atribuirListennerBtnEdicao($btnEditar) {
                $('.assumir-servico').off('click');
                $('.assumir-servico').each(function () {
                    $(this).click(function () {
                        var novaUrl;
                        idServico = $(this).attr('id-servico');
                        novaUrl = url + idServico;
                        var novaUrlServPadrao = urlServicoPadrao +"?id="+idServico;
                        // $('#btnSalvarTarefa').attr('href', novaUrl);
                        $('#salvarServPadrao').attr('href', novaUrlServPadrao);
                        console.log($('#salvarServPadrao').attr('href'));
                    });
                });


//            }
//            var a = $("#assumir-servico").attr('id-servico');
//            console.log(a);

        </script>
        <script src="${ctx}/resources/plugins/moment/date-time-moment.js"></script>
        <script src="${ctx}/resources/js/table.js"></script>
    </jsp:attribute>

    <jsp:body>
        <div class="panel">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Serviços Abertos</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
<%--             <input id="urlServicoPadrao" type="hidden" value="${linkTo[ServicosController].assumirServicoComTarefa}"> --%>
            <div class="panel-body" style="padding-top: 0px;">
                <a class="btn btn-info" style="margin-bottom: 16px;" href="${linkTo[ServicosClienteController].form}">Cadastrar</a>
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
<%--                                     <a title="Assumir Serviço" class="assumir-servico" id-servico="${servico.id}" data-toggle="modal" href="#myModal"> --%>
<!--                                         <i class="fa fa-check-circle-o fa-lg" aria-hidden="true"></i></a> -->
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>

<!--         <div class="modal fade" id="myModal" role="dialog"> -->
<!--             <div class="modal-dialog"> -->
<%--                 <input type="hidden" name="tarefa.id" value="${tarefa.id}"/> --%>
<!--                 Modal content -->
<!--                 <div class="modal-content"> -->
<!--                     <div class="modal-header"> -->
<!--                         <button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!--                         <h4 class="modal-title primary">Serviço aberto</h4> -->
<!--                     </div> -->
<!--                     <div class="modal-body"> -->
<!--                         <h5> Deseja assumir o serviço?</h5> -->
<!--                     </div> -->
<!--                     <div id="btns-modal" class="modal-footer"> -->
<!--                         <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button> -->
<%--                         <a id="salvarServPadrao" href="${linkTo[ServicosController].assumirServicoComTarefa}?id=" class="btn btn-success">Assumir</a> --%>
<%--                         <a id="btnSalvarTarefa" href="${linkTo[ServicosController].assumirServico}?id=" class="btn btn-primary"> --%>
<%--                         Salvar --%>
<%--                         </a> --%>
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->
<!--         </div> -->
    </jsp:body>
</tags:layout>
