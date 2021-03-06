<%--
  Created by IntelliJ IDEA.
  User: SINF
  Date: 07/02/2018
  Time: 14:56
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
        <script src="${ctx}/resources/plugins/moment/date-time-moment.js"></script>
        <script src="${ctx}/resources/js/init.js"></script>
        <script src="${ctx}/resources/js/manutencao/lista.js"></script>
        <script src="${ctx}/resources/js/manutencao/form.js"></script>
        <script>
            $(document).ready(function () {
                $.fn.dataTable.moment('DD/MM/YYYY');

                $('.table').DataTable( {
                    pageLength:25,
                    "language":
                        {
                            "sEmptyTable": "Nenhum registro encontrado",
                            "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                            "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
                            "sInfoFiltered": "(Filtrados de _MAX_ registros)",
                            "sInfoPostFix": "",
                            "sInfoThousands": ".",
                            "sLengthMenu": "_MENU_ resultados por página",
                            "sLoadingRecords": "Carregando...",
                            "sProcessing": "Processando...",
                            "sZeroRecords": "Nenhum registro encontrado",
                            "sSearch": "Pesquisar",
                            "oPaginate": {
                                "sNext": "Próximo",
                                "sPrevious": "Anterior",
                                "sFirst": "Primeiro",
                                "sLast": "Último"
                            },
                            "oAria": {
                                "sSortAscending": ": Ordenar colunas de forma ascendente",
                                "sSortDescending": ": Ordenar colunas de forma descendente"
                            }
                        }
                } );
            });


        </script>
    </jsp:attribute>

    <jsp:body>
        <div class="panel painel-sisint">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Manutenções</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <input id="urlAssumir" type="hidden" value="${linkTo[ManutencaoController].assumirManutencao}">
            <input id="urlConcluir" type="hidden" value="${linkTo[ManutencaoController].concluir}">
            <div class="panel-body" style="padding-top: 0px;">
                <a class="btn btn-info" style="margin-bottom: 16px;" href="${linkTo[ManutencaoController].form}">Cadastrar</a>
                <div class="tabela-servicos">
                    <table id="tabela-servico" class="table table-bordered tabela">
                        <thead>
                        <tr>
                            <%--<th>Título</th>--%>
                            <th>Setor Solicitante</th>
                            <th>Equipamento</th>
                            <th>Tombo</th>
                            <th>N/S do Equipamento</th>
                            <th>Técnico</th>
                            <th>Data de Abertura</th>
                            <th>Status</th>
                            <th>Ações</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${manutencoes}" var="manutencao">
                            <tr>
                                <%--<td>${manutencao.titulo}</td>--%>
                                <td>${manutencao.setor.nome}</td>
                                <td>${manutencao.equipamento.nome}</td>
                                <td>${manutencao.equipamento.tombo}</td>
                                <td>${manutencao.equipamento.numeroSerie}</td>
                                <td>${manutencao.tecnico.nome}</td>
                                <td>${manutencao.dataAbertura}</td>
                                <td><span class="label labelStatus">${manutencao.status.chave}</span></td>
                                <td><a title="Detalhar" href="${linkTo[ManutencaoController].detalhar}?id=${manutencao.id}"><i class="fa fa-eye fa-lg"></i></a>
                                    <c:if test="${(usuarioLogado.usuario.nome == manutencao.tecnico.nome))}">
                                            <a href="${linkTo[ManutencaoController].editar}?id=${manutencao.id}"
                                               title="Editar"><i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i></a>
                                            <a class="removerManutencao" href="#modalRemover" data-toggle="modal"
                                               url-remover="${linkTo[ManutencaoController].remover}?id=${manutencao.id}"
                                               title="Remover"><i class="fa fa-trash fa-lg"></i></a>
                                    </c:if>
                                    <c:if test="${(usuarioLogado.usuario.nome == manutencao.tecnico.nome)}">
                                        <c:if test="${manutencao.status == 'EM_MANUTENCAO'}">
                                            <a title="Concluir" class="concluir-manutencao" id-manutencao="${manutencao.id}" data-toggle="modal"
                                               href="#modalConcluir"><i class="fa fa-check fa-lg" aria-hidden="true"></i></a>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${manutencao.status == 'AGUARDANDO_MANUTENCAO' ||
                                                (usuarioLogado.usuario.nome != manutencao.tecnico.nome)}">
                                        <a title="Assumir" class="assumir-manutencao" id-manutencao="${manutencao.id}" data-toggle="modal"
                                           href="#modalAssumir"><i class="fa fa-hand-grab-o fa-lg" aria-hidden="true"></i></a>
                                    </c:if></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- MODAL PARA CONFIRMAR ASSUMIR SERVIÇO -->
        <div class="modal fade" id="modalAssumir" role="dialog">
            <div class="modal-dialog">
                <input type="hidden" name="manutencao.id" value="${manutencao.id}"/>
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title primary">Assumir Manutenção...</h4>
                    </div>
                    <div class="modal-body">
                        <h5> Deseja assumir a manutenção?</h5>
                    </div>
                    <div id="btns-modalAssumir" class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        <a id="btnAssumirManutencao" href="${linkTo[ManutencaoController].assumirManutencao}?id=" class="btn btn-primary">
                            Assumir
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- MODAL PARA CONCLUIR SERVIÇO -->
        <div class="modal fade" id="modalConcluir" role="dialog">
            <div class="modal-dialog">
                <input type="hidden" name="manutencao.id" value="${manutencao.id}"/>
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title primary">Deseja concluir a manutenção?</h4>
                    </div>
                    <div class="modal-body">
                        <div class="custom-checkbox custom-control">
                            <label>
                                <input type="checkbox" id="checkConserto" name="statusEquipamento" value="OK" checked/> Equipamento Consertado?
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="descricaoFinal-manutencao" style="margin-top: 15px;">Descrição:</label>
                            <textarea class="form-control" name="descricaoFinal" rows="4"
                                      id="descricaoFinal-manutencao">${manutencao.descricaoFinal}</textarea>
                        </div>
                    </div>
                    <div id="btns-modal" class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        <a id="btnConcluirManutencao" href="${linkTo[ManutencaoController].concluir}?id=" class="btn btn-primary">
                            Concluir
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal REMOVER -->
        <div class="modal fade" id="modalRemover" tabindex="-1" role="dialog" aria-labelledby="modalLabel">
            <div class="modal-dialog" role="document">
                <input type="hidden" name="manutencao.id" value="${manutencao.id}"/>
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="modalLabel">Remover Manutenção</h4>
                    </div>
                    <div class="modal-body">
                        Deseja realmente remover esta manutenção?
                    </div>
                    <div class="modal-footer">
                        <div id="btn-remover-modal" class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <a id="btnRemoverManutencao" href="${linkTo[ManutencaoController].remover}?id=" class="btn btn-danger">Remover</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</tags:layout>

