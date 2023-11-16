package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBD;
import model.ModelTelefone;

public class DAOTelefoneRepository {
	
	private Connection conn;
	
	public DAOTelefoneRepository() {
		conn =  SingleConnectionBD.getConnection();		
	}
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	
	public void gravarTelefone(ModelTelefone modelTelefone) throws Exception {	
		
		String sql = "INSERT INTO model_telefone (numero, id_usuario, id_cadastro) VALUES (?, ?, ?)";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setString(1, modelTelefone.getNumero());
		preparedStatement.setLong(2, modelTelefone.getIdUsuario().getId());
		preparedStatement.setLong(3, modelTelefone.getIdCadastro().getId());
		
		preparedStatement.execute();
		
		conn.commit();		
	}


	public List<ModelTelefone> listarTelefone(Long idUsuario) throws Exception{
		
		List<ModelTelefone> listaTelefones = new ArrayList<ModelTelefone>();
		
		String sql = "SELECT * FROM model_telefone WHERE id_usuario = ?";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUsuario);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(resultSet.getLong("id"));
			modelTelefone.setNumero(resultSet.getString("numero"));		

			modelTelefone.setIdUsuario(daoUsuarioRepository.consultarUsuarioId(resultSet.getLong("id_usuario")));
			modelTelefone.setIdCadastro(daoUsuarioRepository.consultarUsuarioId(resultSet.getLong("id_cadastro")));
			
			listaTelefones.add(modelTelefone);
		}
		
		return listaTelefones;
	}
	
	
	public void deletarTelefone(Long id) throws SQLException {
		String sql = "DELETE FROM model_telefone WHERE id = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setLong(1, id);
		preparedStatement.executeUpdate();
		conn.commit();
	}
	
	public boolean existeTelefone(String numero, Long idUsuario) throws SQLException {
		
		String sql = "SELECT COUNT(1) > 0 AS existeNumero FROM model_telefone WHERE numero = ? AND id_usuario = ?";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setString(1, numero);
		preparedStatement.setLong(2, idUsuario);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next();	
		
		return resultSet.getBoolean("existeNumero");
	}

}
