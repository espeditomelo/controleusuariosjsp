package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnectionBD;

public class DAOVersionadorBd implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	
	public DAOVersionadorBd() {
		conn = SingleConnectionBD.getConnection();
	}
	
	public void insereArquivoBd(String nomeArquivoBd) throws SQLException {
		
		String sql = "INSERT INTO versionador_bd(arquivo_sql) VALUES (?)";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setString(1, nomeArquivoBd);
		
		preparedStatement.execute();
		
		conn.commit();
		
	}
	
	public boolean arquivoBdExecutado(String nomeArquivo) throws SQLException {
		
		String sql = "SELECT COUNT(1) > 0 as arquivo_sql_executado FROM versionador_bd WHERE arquivo_sql = ?";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setString(1, nomeArquivo);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next();		
		
		return resultSet.getBoolean("arquivo_sql_executado");
		
	}
	
}
