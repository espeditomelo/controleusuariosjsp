<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="pt-br">


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
														<h4 class="sub-title">Cadastro do Usuário</h4>
														<form class="form-material" enctype="multipart/form-data" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
															
															<input type="hidden" name="acao" id="acao" value="">
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly" value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">Id</label>
															</div>


															<div class="form-group form-default form-static-label">
																<div class="mb-3">
																	<label for="formFileSm" class="form-label">Foto</label>
																	<div>
																		<img alt="Foto usuário" id="fotoBase64" src="" width="70px">
																	</div>
																	<br>
																	<input id="arquivoFoto" name="arquivoFoto" accept="image/*" onchange="visualizarImagem('fotoBase64', 'arquivoFoto');" class="form-control form-control-sm" type="file">
																</div>
															</div>


															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required" value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.email}"> <span class="form-bar"></span>
																<label class="float-label">Email (exa@gmail.com)</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<select class="form-control"
																	aria-label="Default select example" name="perfil">																	
																	<option disabled="disabled">[Selecione uma opção]</option>
																	
																	<option value="ADMIN" <%																	
																		ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");																	
																		if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																		} %> >Admin</option>																	
																	
																	<option value="SECRETARIA" <%
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		if (modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																		} %> >Secretária</option>																	
																	
																	<option value="ALFAIATE" <%
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		if (modelLogin != null && modelLogin.getPerfil().equals("ALFAIATE")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																		} %> >Alfaiate</option>			
																	 																	
																</select>
																<span class="form-bar"></span>
																<label class="float-label">Perfil</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required" value="${modelLogin.login}"> <span
																	class="form-bar"></span> <label class="float-label">Login</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.senha}"> <span class="form-bar"></span>
																<label class="float-label">Senha</label>
															</div>
															
															
															<div class="form-group form-default form-static-label">
																<select class="form-control"
																	aria-label="Default select example" name="genero">																	
																	<option disabled="disabled">[Selecione uma opção]</option>
																	
																	<option value="M" <%																	
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");																	
																		if (modelLogin != null && modelLogin.getSexo().equals("M")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																		} %> >Masculino</option>																	
																	
																	<option value="F" <%
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		if (modelLogin != null && modelLogin.getSexo().equals("F")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																		} %> >Feminino</option> 																	
																</select>
																<span class="form-bar"></span>
																<label class="float-label">Sexo</label>
															</div>								
															

															<div class="card-block">
																<button type="button"
																	class="btn btn-primary btn-round waves-effect waves-light" onclick="limparForm();">Novo</button>
																<button type="submit"
																	class="btn btn-primary btn-round waves-effect waves-light">Salvar</button>
																<button type="button"
																	class="btn btn-primary btn-round waves-effect waves-light" onclick="prepararDeleteAjax();">Excluir</button>
																<button type="button"
																	class="btn btn-primary btn-round waves-effect waves-light" data-toggle="modal" data-target="#pesquisarModal">Pesquisar</button>
															</div>

														</form>
													</div>
												</div>
											</div>
										</div>
										
										<span id="msg">${msg}</span>

										<div style="height: 300px; overflow: scroll">
											<!-- table para listaUsuarios -->
											<table id="tabelaListaUsuarios"
												class="table table-striped">
												<thead>
													<tr>
														<th scope="col">Id</th>
														<th scope="col">Nome</th>
														<th scope="col">Perfil</th>
														<th scope="col">Sexo</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${listaUsuarios}" var="lU">
														<tr>
															<td><c:out value="${lU.id}"></c:out></td>
															<td><c:out value="${lU.nome}"></c:out></td>
															<td><c:out value="${lU.perfil}"></c:out></td>
															<td><c:out value="${lU.sexo}"></c:out></td>
															<!-- <td> <button type="button" class="btn btn-primary btn-round waves-effect waves-light">Ver</button></td> --> 
															<td><a class="btn btn-primary btn-round waves-effect waves-light" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=pesquisarParaEditar&id=${lU.id}">Ver</a></td>
															
														</tr>														
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="pesquisarModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Pesquisar Usuário</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <div class="input-group mb-3">
			  <input type="text" id="nomePesquisa" class="form-control" placeholder="Nome do usuário" aria-label="Nome do Usuário" aria-describedby="basic-addon2">
			  <div class="input-group-append">
			    <button class="btn btn-success" type="button" onclick="pesquisarUsuario();">Pesquisar</button>
			  </div>
			</div>			
			
			<div style="height: 300px; overflow: scroll">
			<!-- table para resultado da pesquisa -->
			<table id="tabelaResultadoPesquisa" class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">Id</th>
			      <th scope="col">Nome</th>
			      <th scope="col">Perfil</th>
			      <th scope="col">Sexo</th>
			      <th scope="col">Ver</th>			      
			    </tr>
			  </thead>
			  <tbody>
			
			  </tbody>
			</table>				
			</div>		
			<br>
			<span id="totalResultadoPesquisa"></span>
	      </div>	      
	      
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary btn-round waves-effect waves-light" data-dismiss="modal">Fechar</button>        
	      </div>
	    </div>
	  </div>
	</div>	


	<jsp:include page="javascript.jsp"></jsp:include>
	
	<script type="text/javascript">
	
	
	function visualizarImagem(fotoBase64, arquivoFoto) {
		
		alert('dentro da funcao visualizarImagem');
	
		var preview = document.getElementById(fotoBase64);
		var arquivoUsuario = document.getElementById(arquivoFoto).files[0];
		var reader = new FileReader();
	
		
		reader.onloadend = function () {
			preview.src = reader.result; // carregar foto na tela
		};
		
		if (arquivoUsuario) {
			reader.readAsDataURL(arquivoUsuario); // preview da imagem
		} else {
			preview.src = '';
		}
	}
	
	function verUsuarioSelecionado(id) {
		
		var urlAction = document.getElementById('formUser').action;
					
		window.location.href = urlAction + '?acao=pesquisarParaEditar&id=' +id;
		
	}
	
	function pesquisarUsuario() {
		var nomePesquisa = document.getElementById('nomePesquisa').value;
		
		if (nomePesquisa != null && nomePesquisa != '' && nomePesquisa.trim() != '' ) {

			var urlAction = document.getElementById('formUser').action;
			
			$.ajax({
				
				method: "get",
				url: urlAction,
				data: "nomePesquisa=" + nomePesquisa + "&acao=PesquisarUsuarioAjax",
				success: function(response) {
					
					var json = JSON.parse(response);
					
					$('#tabelaResultadoPesquisa > tbody > tr').remove();
					
					for(var i = 0; i < json.length; i++){
						/* $('#tabelaResultadoPesquisa > tbody').append('<tr> <td>'+json[i].id+'</td>  
						<td>'+json[i].nome+'</td>  <td> <button type="button" onclick="verUsuarioSelecionado('+json[i].id+');" 
						class="btn btn-primary btn-round waves-effect waves-light">Ver</button> </td>  </tr>');
						document.getElementById('totalResultadoPesquisa').textContent = 'Total de Usuários pesquisados: ' + json.length; */
						
						$('#tabelaResultadoPesquisa > tbody').append('<tr> <td>'+json[i].id+'</td>  <td>'+json[i].nome+'</td>  <td>'+json[i].perfil+'</td> <td>'+json[i].sexo+'</td>  <td> <button type="button" onclick="verUsuarioSelecionado('+json[i].id+');" class="btn btn-primary btn-round waves-effect waves-light">Ver</button> </td>  </tr>');
								document.getElementById('totalResultadoPesquisa').textContent = 'Total de Usuários pesquisados: ' + json.length;
						
					}
					
				}
				
									
			}).fail(function(xhr, status, errorThrown){
				alert('Erro ao tentar pesquisar usuário por nome: ' + xhr.responseText);
			}); 
			
		}
	}
	
	
	function prepararDeleteAjax() {		
		if(confirm('Deseja excluir esse usuário?')){			
			
			console.log('dentro da funcao ajax');		
			
			var urlAction = document.getElementById('formUser').action;
			var idUser = document.getElementById('id').value;
			
			console.log('urlAction = ' + urlAction);
			console.log('idUser = ' + idUser);
			
			$.ajax({
				
				method: "get",
				url: urlAction,
				data: "id=" + idUser + "&acao=deletarAjax",
				success: function(response) {
					limparForm();
					document.getElementById('msg').textContent = response;
				}
				
									
			}).fail(function(xhr, status, errorThrown){
				alert('Erro ao tentar deletar usuário por id: ' + xhr.responseText);
			}); 
		}
	}
		

		function prepararDelete() {			
			if(confirm('Deseja excluir esse usuário sem ajax?')){
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();	
			}		
		}
	
		function limparForm() {					
			var elementos = document.getElementById("formUser").elements;			
			for (e = 0; e < elementos.length; e++){
				elementos[e].value = '';
			}			
		}
	</script>
</body>

</html>
