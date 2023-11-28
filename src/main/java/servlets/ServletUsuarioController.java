package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDTOPerfilSalarioParaGrafico;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import util.ReportUtil;



@MultipartConfig
@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {
		super();
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String acao = request.getParameter("acao");		
			
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String id = request.getParameter("id");
				
				daoUsuarioRepository.deletarUsuario(id);
				
				List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios(super.getUsuarioLogado(request));
				request.setAttribute("listaUsuarios", listaUsuarios);				
				
				request.setAttribute("msg", "Usuario execluido com sucesso.");
				
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request), 5));
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
				String id = request.getParameter("id");
				daoUsuarioRepository.deletarUsuario(id);
				response.getWriter().write("Usuario execluido com sucesso.");
								
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("PesquisarUsuarioAjax")) {
				String nomePesquisa = request.getParameter("nomePesquisa");								
				
				List<ModelLogin> lista = daoUsuarioRepository.pesquisarUsuario(nomePesquisa, super.getUsuarioLogado(request));
				
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonNomePesquisa = objectMapper.writeValueAsString(lista);
				
				response.addHeader("totalPaginas", ""+daoUsuarioRepository.pesquisarUsuarioTotalPaginaEPaginacao(nomePesquisa, super.getUsuarioLogado(request)));
				response.getWriter().write(jsonNomePesquisa);				
			
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("PesquisarUsuarioAjaxPage")) {
				
				String nomePesquisa = request.getParameter("nomePesquisa");
				
				String pagina = request.getParameter("pagina");
				
				List<ModelLogin> lista = daoUsuarioRepository.pesquisarUsuarioOffSet(nomePesquisa, super.getUsuarioLogado(request), Integer.parseInt(pagina));
								
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonNomePesquisa = objectMapper.writeValueAsString(lista);
				
				response.addHeader("totalPaginas", ""+daoUsuarioRepository.pesquisarUsuarioTotalPaginaEPaginacao(nomePesquisa, super.getUsuarioLogado(request)));
				response.getWriter().write(jsonNomePesquisa);
			
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pesquisarParaEditar")) {						
				
				String id = request.getParameter("id");				
				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id, super.getUsuarioLogado(request));	
				
				List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios(super.getUsuarioLogado(request));
				
				request.setAttribute("listaUsuarios", listaUsuarios);
				
				request.setAttribute("msg", "Usuário em edição");
				
				request.setAttribute("modelLogin", modelLogin);
				
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request), 5));
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);				
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUsuarios")) {
				
				List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios(super.getUsuarioLogado(request));
				
				request.setAttribute("msg", "Lista de Usuarios");
				request.setAttribute("listaUsuarios", listaUsuarios);
				
					request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request), 5));
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				
				String id = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id, super.getUsuarioLogado(request));
				
				if (modelLogin.getFotoUsuario() != null && !modelLogin.getFotoUsuario().isEmpty()) {
					response.setHeader("Content-Disposition", "attachment;filename=imagem." + modelLogin.getExtensaoFotoUsuario());
					response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotoUsuario().split("\\,")[1]));
				}
			
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				
				Integer offSet = Integer.parseInt(request.getParameter("pagina"));				
				List<ModelLogin> listaModelLogin = daoUsuarioRepository.listarUsuariosPaginados(this.getUsuarioLogado(request), offSet);				
				request.setAttribute("listaUsuarios", listaModelLogin);			
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request), 5));			
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} 
						
			
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUsuario")) {
				
				String dataInicial = request.getParameter("dataInicial");												
				String dataFinal = request.getParameter("dataFinal");				
				

				if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
					request.setAttribute("listaUsuarios", daoUsuarioRepository.listarTodosUsuarios(this.getUsuarioLogado(request)));
				} else {
					request.setAttribute("listaUsuarios", daoUsuarioRepository.listarTodosUsuariosPorDataNascimento(this.getUsuarioLogado(request), Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))), Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal)))));
				}
				
				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal);
				
				request.getRequestDispatcher("principal/relatoriousuario.jsp").forward(request, response);
				
			} 
			
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUsuarioPDF") || acao.equalsIgnoreCase("imprimirRelatorioUsuarioXLS")) {
				
				String dataInicial = request.getParameter("dataInicial");												
				String dataFinal = request.getParameter("dataFinal");				
				
				List<ModelLogin> modelLogins = null;

				if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
					
					modelLogins = daoUsuarioRepository.listarTodosUsuarios(this.getUsuarioLogado(request));
					
				} else {
					
					modelLogins = daoUsuarioRepository.listarTodosUsuariosPorDataNascimento(this.getUsuarioLogado(request), Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))), Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
				}
				
				Map params = new HashMap();
				
				byte[] relatorio = null;
				
				String extensao = "";
				
				if (acao.equalsIgnoreCase("imprimirRelatorioUsuarioPDF")) {
					relatorio = new ReportUtil().geraRelatorioPDF(modelLogins, "relatorio-users-jsp", request.getServletContext());
					extensao = "pdf";
				} else if(acao.equalsIgnoreCase("imprimirRelatorioUsuarioXLS")) {
					relatorio = new ReportUtil().geraRelatorioXLS(modelLogins, "relatorio-users-jsp", params, request.getServletContext());
					extensao = "xls";
				}
								
				response.setHeader("Content-Disposition", "attachment;filename=relatoriousuario." + extensao);
				response.getOutputStream().write(relatorio);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("relatorioGraficoUsuario")) {
			
				//Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial)))
				
				String dataInicial = request.getParameter("dataInicial");				
				
				String dataFinal = request.getParameter("dataFinal");					
				
				BeanDTOPerfilSalarioParaGrafico beanDTOPerfilSalarioParaGrafico = new BeanDTOPerfilSalarioParaGrafico();
				
				if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
					
					beanDTOPerfilSalarioParaGrafico = daoUsuarioRepository.listarPerfilseSalariosParaGrafico(super.getUsuarioLogado(request));
					
				} else {
					
					beanDTOPerfilSalarioParaGrafico = daoUsuarioRepository.listarPerfilseSalariosParaGrafico(super.getUsuarioLogado(request), dataInicial, dataFinal);
					
				}
				
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(beanDTOPerfilSalarioParaGrafico);				
				response.getWriter().write(json);
				
				
			} else {			
			
				List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios(super.getUsuarioLogado(request));
				request.setAttribute("listaUsuarios", listaUsuarios);								
				request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request), 5));				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());
			dispatcher.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try { 
			
			String msg = "Operação realizada com sucesso.";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("genero");			
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			
			String dataNascimento = request.getParameter("dataNascimento");
			
			String rendaMensal = request.getParameter("rendaMensal");
			
			rendaMensal = rendaMensal.split("\\$ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");		

			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);			
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setCidade(cidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
						
			modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
			
			modelLogin.setRendaMensal(Double.valueOf(rendaMensal));			
			
					
			if(ServletFileUpload.isMultipartContent(request)) {
						
				Part part = request.getPart("arquivoFoto");	
						
				if (part.getSize() > 0) {
					byte[] foto = new byte[(int) part.getSize()];
					
					part.getInputStream().read(foto);

					String base64AsString = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new String(Base64.encodeBase64String(foto));
					
					modelLogin.setFotoUsuario(base64AsString);
					modelLogin.setExtensaoFotoUsuario(part.getContentType().split("\\/")[1]);				
				}					
				
 			}
			
			if (daoUsuarioRepository.verificaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Login já existe";
			} else {

				if (modelLogin.isNovo()) {
					msg = "Novo usuário cadastrado.";
				} else {
					msg = "usuário alterado.";
				}

				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUsuarioLogado(request));
			}
			
			List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios(super.getUsuarioLogado(request));			
			request.setAttribute("listaUsuarios", listaUsuarios);
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);			
			request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request), 5));			
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());
			dispatcher.forward(request, response);
		}

	}

}
