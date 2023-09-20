package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import model.ModelLogin;

@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			String acao = request.getParameter("acao");
			
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				String id = request.getParameter("id");
				daoUsuarioRepository.deletarUsuario(id);
				
				List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);				
				
				request.setAttribute("msg", "Usuário execluído com sucesso.");
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
				String id = request.getParameter("id");
				daoUsuarioRepository.deletarUsuario(id);
				response.getWriter().write("Usuário execluído com sucesso.");
								
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("PesquisarUsuarioAjax")) {
				String nomePesquisa = request.getParameter("nomePesquisa");
				List<ModelLogin> lista = daoUsuarioRepository.pesquisarUsuario(nomePesquisa);
				
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonNomePesquisa = objectMapper.writeValueAsString(lista);
				response.getWriter().write(jsonNomePesquisa);
			
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pesquisarParaEditar")) {						
				String id = request.getParameter("id");				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id);	
				
				List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);
				
				request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);				
				
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUsuarios")) {
				
				List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios();
				
				request.setAttribute("msg", "Lista de Usuários");
				request.setAttribute("listaUsuarios", listaUsuarios);
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			} else {
				List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios();
				request.setAttribute("listaUsuarios", listaUsuarios);
				
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

			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);

			if (daoUsuarioRepository.verificaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Login já existe";
			} else {

				if (modelLogin.isNovo()) {
					msg = "Novo usuário cadastrado.";
				} else {
					msg = "Usuário alterado.";
				}

				modelLogin = daoUsuarioRepository.gravarUsuário(modelLogin);
			}
			
			List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios();
			request.setAttribute("listaUsuarios", listaUsuarios);

			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());
			dispatcher.forward(request, response);
		}

	}

}
