package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBD;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection conn;

	public DAOUsuarioRepository() {

		conn = SingleConnectionBD.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin modelLogin) throws SQLException {

		if (modelLogin.isNovo()) {
			String sql = "INSERT INTO public.model_login (login, senha, nome, email) VALUES(UPPER(?), UPPER(?), UPPER(?), UPPER(?));";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			preparedStatement.setString(3, modelLogin.getNome());
			preparedStatement.setString(4, modelLogin.getEmail());

			preparedStatement.execute();

			conn.commit();

		} else {
			String sql = "UPDATE model_login SET login=UPPER(?), senha=UPPER(?), nome=UPPER(?), email=UPPER(?) WHERE id= '"
					+ modelLogin.getId() + "';";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			preparedStatement.setString(3, modelLogin.getNome());
			preparedStatement.setString(4, modelLogin.getEmail());

			preparedStatement.executeUpdate();

			conn.commit();
		}

		return this.consultarUsuario(modelLogin.getLogin());

	}

	public ModelLogin consultarUsuario(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM public.model_login WHERE UPPER(login) = UPPER(?);";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		preparedStatement.setString(1, login);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
		}
		return modelLogin;

	}

	public boolean verificaLogin(String login) throws Exception {

		String sql = "select count(1) > 0 as existe from public.model_login where UPPER(login) = upper('" + login
				+ "');";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		resultSet.next();
		return resultSet.getBoolean("existe");
	}

	public void deletarUsuario(String id) throws SQLException {
		String sql = "DELETE FROM model_login WHERE id=?;";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setLong(1, Long.parseLong(id));
		preparedStatement.executeUpdate();
		conn.commit();
	}

	public List<ModelLogin> pesquisarUsuario(String nomePesquisa) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE Nome like UPPER(?);";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "%" + nomePesquisa + "%");
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			lista.add(modelLogin);
		}

		return lista;
	}
	
	public ModelLogin consultarUsuarioId(String id) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE id = ?;";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		preparedStatement.setLong(1, Long.parseLong(id));

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
		}
		return modelLogin;		
	}
	
	public List<ModelLogin> listarUsuarios() throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login;";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);		
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			lista.add(modelLogin);
		}

		return lista;
		
	}


}
