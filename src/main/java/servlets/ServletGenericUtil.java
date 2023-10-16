package servlets;

import java.io.Serializable;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.DAOUsuarioRepository;

public class ServletGenericUtil extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 1L;
       
	DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	public Long getUsuarioLogado(HttpServletRequest request) throws SQLException {
						
		HttpServletRequest req = request;		
		
		HttpSession session = req.getSession();

		String usuarioLogado = (String) session.getAttribute("usuario");
		
		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado).getId();
		
	}

}
