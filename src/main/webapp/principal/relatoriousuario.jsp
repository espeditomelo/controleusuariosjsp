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

														<h4 class="sub-title">Relatório de Usuário</h4>

														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="get" id="formUser">
															
															<input type="hidden" id="acaoRelatorio" name="acao" value="imprimirRelatorioUsuario">

															<div>
																<input type="text" name="dataInicial" id="dataInicial"
																	class="form-control" autocomplete="off" value=${dataInicial}>
																<span class="form-bar"></span> <label>Data
																	Inicial</label>
															</div>
															<div>
																<input type="text" name="dataFinal" id="dataFinal"
																	class="form-control" autocomplete="off" value=${dataFinal}>
																<span class="form-bar"></span> <label>Data Final</label>
															</div>

															<div>
															
																<button type="button" onclick="imprimirEmTela()"
																	class="btn btn-primary btn-round waves-effect waves-light">Imprimir em Tela</button>
																	
																<button type="button" onclick="imprimirEmPDF()"
																	class="btn btn-primary btn-round waves-effect waves-light">Imprimir em PDF</button>
															</div>

														</form>

														<div style="height: 300px; overflow: scroll">
														
																												
															<table id="tabelaListaUsuarios"
																class="table table-striped">
																<thead>
																	<tr>
																		<th scope="col">Id</th>
																		<th scope="col">Nome</th>
																		<th scope="col">Perfil</th>
																		<th scope="col">Sexo</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${listaUsuarios}" var="lU">
																		<tr>
																			<td><c:out value="${lU.id}"></c:out></td>
																			<td><c:out value="${lU.nome}"></c:out></td>
																			<td><c:out value="${lU.perfil}"></c:out></td>
																			<td><c:out value="${lU.sexo}"></c:out></td>																		
																		</tr>
																																		
																			<c:forEach items="${lU.telefones}" var="lT">
																				<tr>
																					<td><c:out value="${lT.numero}"></c:out></td>
																				</tr>																					
																			</c:forEach>
																	
																	</c:forEach>
																</tbody>
															</table>
														</div>

													</div>
												</div>
											</div>
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

	<jsp:include page="javascript.jsp"></jsp:include>

	<script type="text/javascript">
		$(function() {
			$("#dataInicial")
					.datepicker(
							{
								dateFormat : 'dd/mm/yy',
								dayNames : [ 'Domingo', 'Segunda', 'Terça',
										'Quarta', 'Quinta', 'Sexta', 'Sábado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'Sáb', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Março',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Próximo',
								prevText : 'Anterior'
							});
		});

		$(function() {
			$("#dataFinal")
					.datepicker(
							{
								dateFormat : 'dd/mm/yy',
								dayNames : [ 'Domingo', 'Segunda', 'Terça',
										'Quarta', 'Quinta', 'Sexta', 'Sábado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'Sáb', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Março',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Próximo',
								prevText : 'Anterior'
							});
		});
		
		
		function imprimirEmTela() {
			document.getElementById("acaoRelatorio").value = 'imprimirRelatorioUsuario';
			$("#formUser").submit();
		}
		
		function imprimirEmPDF() {
			document.getElementById("acaoRelatorio").value = 'imprimirRelatorioUsuarioPDF';
			$("#formUser").submit();
		}
		
		
	</script>

</body>

</html>
