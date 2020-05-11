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
    
     <style type="text/css">
        .remover {  
 /*         pointer-events: none; */ 
         cursor: default;  
        color: red; 
        
        } 
        
         .adicionar {  
 /*         pointer-events: none; */ 
         cursor: default;  
         color: green; 
         } 
        
        .ocutar{ 
          display: none ; 
         } 

    </style>
    
        <script src="${ctx}/resources/plugins/dataTables/datatables.js"><c:out value=""/></script>
        <script src="${ctx}/resources/plugins/dataTables/Buttons-1.4.2/js/buttons.html5.js"><c:out value=""/></script>
        <script src="${ctx}/resources/js/setor/setor.js"></script>
        <script src="${ctx}/resources/js/setor/setor2.js"></script>
        <script src="${ctx}/resources/js/setor/setorverificar.js"></script>
        <script src="${ctx}/resources/plugins/moment/date-time-moment.js"></script>
    </jsp:attribute>

    <jsp:body>
        <div class="panel painel-sisint">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Gerenciamento dos Setores</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="panel-body" style="padding-top: 0px;">
                <a class="btn btn-info" style="margin-bottom: 16px;" href="${linkTo[SetorController].form}">Cadastrar</a>

                <div class="tabela-servicos">
                    <table id="tabela-servico" class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Senha</th>
                            <th>Telefone</th>
                            <th>Endereço</th>
                            <th>Deletado</th>
                            <th>Ações</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${setores}" var="setor">
                            <tr>
                                <td>${setor.nome}</td>
                                <td>${setor.senha}</td>
                                <td>${setor.telefone}</td>
                                <td>${setor.endereco}</td>
                                <td>${setor.deletado}</td>
                                <td>
                                    <a title="Editar" href="${linkTo[SetorController].editar}?id=${setor.id}">
                                        <i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i></a>
                                    <c:if test="${usuarioLogado.isAdmin()}">
                                        <a title="Remover" class="link-remover remover" href="#delete-modal"
                                           url-remover="${linkTo[SetorController].remover}?id=${setor.id}" data-toggle="modal">
                                            <i class="fa fa-times fa-lg"></i></a>
                                            
                                        <a title="Adicionar" class="link-adicionar adicionar" href="#adiciona-modal"
                                           url-adicionar="${linkTo[SetorController].ativar}?id=${setor.id}" data-toggle="modal">
                                            <i class="fa fa-check fa-lg"></i></a>    
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>

                        <!-- Modal REMOVER -->
                        <div class="modal fade" id="delete-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="modalLabel">Excluir Item</h4>
                                    </div>
                                    <div class="modal-body">
                                        Deseja realmente excluir este item?
                                    </div>
                                    <div class="modal-footer">
                                        <a id="btn-remover" href="" class="btn btn-primary">Sim</a>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">N&atilde;o</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                         <!-- Modal ATIVAR -->
                        <div class="modal fade" id="adiciona-modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="modalLabel">Ativar Setor</h4>
                                    </div>
                                    <div class="modal-body">
                                        Deseja realmente habilitar este setor?
                                    </div>
                                    <div class="modal-footer">
                                        <a id="btn-ativar" href="" class="btn btn-success">Sim</a>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">N&atilde;o</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</tags:layout>