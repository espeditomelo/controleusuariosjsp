package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import model.ModelTelefone;

@WebServlet("/ServletTelefone")
public class ServletTelefone extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();
       
    public ServletTelefone() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
			try {
				
				String acao = request.getParameter("acao");
				
				if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
					
					String id = request.getParameter("id");								
				
					daoTelefoneRepository.deletarTelefone(Long.parseLong(id));
					
					String idUsuario = request.getParameter("idUser");
					
					ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(idUsuario));
					
					List<ModelTelefone> listaTelefones = daoTelefoneRepository.listarTelefone(modelLogin.getId());
					
					request.setAttribute("listaTelefones", listaTelefones);
					
					request.setAttribute("modelLogin", modelLogin);
					
					request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
					
					return;
				}
				
				
				String idUsuario = request.getParameter("idUsuario");
				
				if(idUsuario != null && !idUsuario.isEmpty()) {
				
					ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(idUsuario));
					
					List<ModelTelefone> listaTelefones = daoTelefoneRepository.listarTelefone(modelLogin.getId());
					
					request.setAttribute("listaTelefones", listaTelefones);
					
					request.setAttribute("modelLogin", modelLogin);
					
					request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
					
				} else {
					List<ModelLogin> listaUsuarios = daoUsuarioRepository.listarUsuarios(super.getUsuarioLogado(request));
					request.setAttribute("listaUsuarios", listaUsuarios);								
					request.setAttribute("totalPaginas", daoUsuarioRepository.totalPaginas(this.getUsuarioLogado(request), 5));				
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				}
					
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			
		try {
			String idUsuario = request.getParameter("id");
			String numero = request.getParameter("numero");
			
			ModelTelefone modelTelefone = new ModelTelefone();			
			
			modelTelefone.setNumero(numero);
			modelTelefone.setIdUsuario(daoUsuarioRepository.consultarUsuarioId(Long.parseLong(idUsuario)));
			modelTelefone.setIdCadastro(super.getObjetoUsuarioLogado(request));
			
			daoTelefoneRepository.gravarTelefone(modelTelefone);
			
			request.setAttribute("msg", "Telefone gravado");
			
			ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(idUsuario));
			
			List<ModelTelefone> listaTelefones = daoTelefoneRepository.listarTelefone(Long.parseLong(idUsuario));
			
			request.setAttribute("listaTelefones", listaTelefones);
						
			request.setAttribute("modelLogin", modelLogin);
			
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
