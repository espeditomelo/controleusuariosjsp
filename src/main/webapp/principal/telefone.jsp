<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>   
 
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>



  <body>
  
  	<jsp:include page="theme-loader.jsp"></jsp:include>
  
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
              
                  <jsp:include page="navbar-mainmenu.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      <jsp:include page="page-header.jsp"></jsp:include>
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">

										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">

													<div class="card-block">
														<h4 class="sub-title">Cadastro do Telefone de Clientes</h4>
														
														<form class="form-material" action="<%= request.getContextPath() %>/ServletTelefone" method="post" id="formTelefone">
														
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly" value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">Id Usuário</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" readonly="readonly" name="nome" id="nome"
																	class="form-control" required="required" value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome</label>
															</div>														
														
															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero"
																	class="form-control" required="required"> <span
																	class="form-bar"></span> <label class="float-label">Numero</label>
															</div>
															
															<button type="submit"
																	class="btn btn-primary btn-round waves-effect waves-light">Salvar</button>
														
														</form>												
														
													</div>
													
												</div>
											</div>
										</div>
										<!-- Page-body end -->
										
										<span id="msg">${msg}</span>
										
										<div style="height: 300px; overflow: scroll">
											<!-- table para listaUsuarios -->
											<table id="tabelaListaUsuarios"
												class="table table-striped">
												<thead>
													<tr>
														<th scope="col">Id</th>
														<th scope="col">Numero</th>
														<th scope="col">Excluir</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${listaTelefones}" var="lT">
														<tr>
															<td><c:out value="${lT.id}"></c:out></td>
															<td><c:out value="${lT.numero}"></c:out></td>
															<td><a class="btn btn-primary btn-round waves-effect waves-light" href="<%= request.getContextPath() %>/ServletTelefone?acao=deletar&id=${lT.id}&idUser=${modelLogin.id}">Excluir</a></td>															
														</tr>														
													</c:forEach>
												</tbody>
											</table>
										</div>
										
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   </div>
   <jsp:include page="javascript.jsp"></jsp:include>
</body>

</html>
