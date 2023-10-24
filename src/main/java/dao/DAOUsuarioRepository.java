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

	public ModelLogin gravarUsuario(ModelLogin modelLogin, Long usuarioLogado) throws SQLException {

		if (modelLogin.isNovo()) {
		
			//String sql = "INSERT INTO public.model_login (login, senha, nome, email, id_cadastro, perfil, sexo, cep, logradouro, bairro, cidade, uf, numero) VALUES(UPPER(?), UPPER(?), UPPER(?), UPPER(?), ?, ?, ?)";
			String sql = "INSERT INTO public.model_login (login, senha, nome, email, id_cadastro, perfil, sexo, cep, logradouro, bairro, cidade, uf, numero) VALUES(UPPER(?), UPPER(?), UPPER(?), UPPER(?), ?, UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?))";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			preparedStatement.setString(3, modelLogin.getNome());
			preparedStatement.setString(4, modelLogin.getEmail());
			preparedStatement.setLong(5, usuarioLogado);
			preparedStatement.setString(6, modelLogin.getPerfil());
			preparedStatement.setString(7, modelLogin.getSexo());
			
			preparedStatement.setString(8, modelLogin.getCep());
			preparedStatement.setString(9, modelLogin.getLogradouro());
			preparedStatement.setString(10, modelLogin.getBairro());
			preparedStatement.setString(11, modelLogin.getCidade());
			preparedStatement.setString(12, modelLogin.getUf());
			preparedStatement.setString(13, modelLogin.getNumero());

			preparedStatement.execute();

			conn.commit();
			
			if(modelLogin.getFotoUsuario() != null && !modelLogin.getFotoUsuario().isEmpty()) {
				
				sql = "UPDATE public.model_login SET fotousuario = ?, extensaofotousuario = ? WHERE login = UPPER(?)";
				
				preparedStatement = conn.prepareStatement(sql);
				
				preparedStatement.setString(1, modelLogin.getFotoUsuario());
				preparedStatement.setString(2, modelLogin.getExtensaoFotoUsuario());
				preparedStatement.setString(3, modelLogin.getLogin());				
						
				preparedStatement.execute();
				
				conn.commit();
			}
			

		} else {
			
			//String sql = "UPDATE model_login SET login=UPPER(?), senha=UPPER(?), nome=UPPER(?), email=UPPER(?), perfil=? , sexo=? WHERE id= '" +  modelLogin.getId() + "';";
			String sql = "UPDATE model_login SET login=UPPER(?), senha=UPPER(?), nome=UPPER(?), email=UPPER(?), perfil=UPPER(?) , sexo=UPPER(?), cep=UPPER(?), logradouro=UPPER(?), bairro=UPPER(?), cidade=UPPER(?), uf=UPPER(?), numero=UPPER(?) WHERE id= '" +  modelLogin.getId() + "';";

			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, modelLogin.getLogin());
			preparedStatement.setString(2, modelLogin.getSenha());
			preparedStatement.setString(3, modelLogin.getNome());
			preparedStatement.setString(4, modelLogin.getEmail());
			preparedStatement.setString(5, modelLogin.getPerfil());
			preparedStatement.setString(6, modelLogin.getSexo());
			
			preparedStatement.setString(7, modelLogin.getCep());
			preparedStatement.setString(8, modelLogin.getLogradouro());
			preparedStatement.setString(9, modelLogin.getBairro());
			preparedStatement.setString(10, modelLogin.getCidade());
			preparedStatement.setString(11, modelLogin.getUf());
			preparedStatement.setString(12, modelLogin.getNumero());

			preparedStatement.executeUpdate();

			conn.commit();
			
			if (modelLogin.getFotoUsuario() != null && !modelLogin.getFotoUsuario().isEmpty()) {

				sql = "update public.model_login set fotousuario = ?, extensaofotousuario = ? where id = ?";

				preparedStatement = conn.prepareStatement(sql);

				preparedStatement.setString(1, modelLogin.getFotoUsuario());
				preparedStatement.setString(2, modelLogin.getExtensaoFotoUsuario());
				preparedStatement.setLong(3, modelLogin.getId());

				preparedStatement.execute();

				conn.commit();
			}
			
		}

		return this.consultarUsuario(modelLogin.getLogin(), usuarioLogado);

	}

	public ModelLogin consultarUsuario(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM public.model_login WHERE UPPER(login) = UPPER(?) and admin is false";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		preparedStatement.setString(1, login);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setAdmin(resultSet.getBoolean("admin"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("genero"));
			
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setCidade(resultSet.getString("cidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));			
		}
		
		return modelLogin;

	}
	
	
	public ModelLogin consultarUsuarioLogado(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM public.model_login WHERE UPPER(login) = UPPER(?)";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		preparedStatement.setString(1, login);

		ResultSet resultSet = preparedStatement.executeQuery();

		//if (resultSet.next()) {
		while (resultSet.next()) {
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setAdmin(resultSet.getBoolean("admin"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			modelLogin.setFotoUsuario(resultSet.getString("fotousuario"));
			
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setCidade(resultSet.getString("cidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
			
		}
		
		return modelLogin;

	}
	public ModelLogin consultarUsuario(String login, Long usuarioLogado) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM public.model_login WHERE UPPER(login) = UPPER(?) and admin is false and id_cadastro = " + usuarioLogado;

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		preparedStatement.setString(1, login);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));			
			modelLogin.setFotoUsuario(resultSet.getString("fotousuario"));
			
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setCidade(resultSet.getString("cidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
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
		String sql = "DELETE FROM model_login WHERE id=? and admin is false;";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setLong(1, Long.parseLong(id));
		preparedStatement.executeUpdate();
		conn.commit();
	}

	public List<ModelLogin> pesquisarUsuario(String nomePesquisa, Long usuarioLogado) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE Nome like UPPER(?) and admin is false and id_cadastro = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "%" + nomePesquisa + "%");
		preparedStatement.setLong(2, usuarioLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			
			lista.add(modelLogin);
		}

		return lista;
	}
	
	public ModelLogin consultarUsuarioId(String id, Long usuarioLogado) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE id = ? and admin is false and id_cadastro = ?";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		preparedStatement.setLong(1, Long.parseLong(id));
		preparedStatement.setLong(2, usuarioLogado);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));			
			modelLogin.setFotoUsuario(resultSet.getString("fotousuario"));
			modelLogin.setExtensaoFotoUsuario(resultSet.getString("extensaofotousuario"));
			
			modelLogin.setCep(resultSet.getString("cep"));
			modelLogin.setLogradouro(resultSet.getString("logradouro"));
			modelLogin.setBairro(resultSet.getString("bairro"));
			modelLogin.setCidade(resultSet.getString("cidade"));
			modelLogin.setUf(resultSet.getString("uf"));
			modelLogin.setNumero(resultSet.getString("numero"));
		}
		return modelLogin;		
	}
	
	public List<ModelLogin> listarUsuarios(Long usuarioLogado) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login where admin is false and id_cadastro = " + usuarioLogado + " order by nome";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);		
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			
			lista.add(modelLogin);
		}

		return lista;
		
	}


}
