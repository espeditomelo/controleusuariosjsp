package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOUsuarioRepository;
import model.ModelLogin;

@WebServlet("/ServletTelefone")
public class ServletTelefone extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
       
    public ServletTelefone() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String idUsuario = request.getParameter("idUsuario");
		
		if(idUsuario != null && !idUsuario.isEmpty()) {
			try {
				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(idUsuario));
				
				request.setAttribute("objetoUsuarioInteiro", modelLogin);
				
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
