package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnectionBD;
import model.ModelLogin;

public class DAOLoginRepository {
	
	private Connection conn;
	
	public DAOLoginRepository() {
		conn = SingleConnectionBD.getConnection();
	}
	
	public boolean  validarAutenticacao(ModelLogin modelLogin) throws SQLException {
		
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?)";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setString(1, modelLogin.getLogin());
		preparedStatement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			return true;
		}
	
		return false;
		
	}
	
	

}
