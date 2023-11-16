package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBD;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {

	private Connection conn;
	
	private final int LIMITEPAGINACAOCADASTRO = 5;
	
	public DAOUsuarioRepository() {	
		conn = SingleConnectionBD.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin modelLogin, Long usuarioLogado) throws SQLException {

		if (modelLogin.isNovo()) {
		
			String sql = "INSERT INTO public.model_login (login, senha, nome, email, id_cadastro, perfil, sexo, cep, logradouro, bairro, cidade, uf, numero, datanascimento, rendamensal) VALUES(UPPER(?), UPPER(?), UPPER(?), UPPER(?), ?, UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), UPPER(?), ?, ?)";

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
			preparedStatement.setDate(14, modelLogin.getDataNascimento());
			preparedStatement.setDouble(15, modelLogin.getRendaMensal());
			
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
			
			String sql = "UPDATE model_login SET login=UPPER(?), senha=UPPER(?), nome=UPPER(?), email=UPPER(?), perfil=UPPER(?) , sexo=UPPER(?), cep=UPPER(?), logradouro=UPPER(?), bairro=UPPER(?), cidade=UPPER(?), uf=UPPER(?), numero=UPPER(?), datanascimento=?, rendamensal=? WHERE id= '" +  modelLogin.getId() + "';";

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
			preparedStatement.setDate(13, modelLogin.getDataNascimento());
			preparedStatement.setDouble(14, modelLogin.getRendaMensal());
			
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
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		
		return modelLogin;

	}
	
	
	public ModelLogin consultarUsuarioLogado(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM public.model_login WHERE UPPER(login) = UPPER(?)";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		preparedStatement.setString(1, login);

		ResultSet resultSet = preparedStatement.executeQuery();

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
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
			
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
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		
		return modelLogin;

	}

	public boolean verificaLogin(String login) throws Exception {

		String sql = "select count(1) > 0 as existe from public.model_login where UPPER(login) = upper('" + login + "');";

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
	
	public int  pesquisarUsuarioTotalPaginaEPaginacao(String nomePesquisa, Long usuarioLogado) throws Exception {

		String sql = "SELECT COUNT(1) as totalcadastros FROM model_login WHERE nome LIKE UPPER(?) AND admin IS FALSE AND id_cadastro = ? LIMIT " + LIMITEPAGINACAOCADASTRO;
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "%" + nomePesquisa + "%");
		preparedStatement.setLong(2, usuarioLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next();
		
		Double totalCadastros = resultSet.getDouble("totalcadastros");
				
		Double qtdPaginas = totalCadastros / LIMITEPAGINACAOCADASTRO;
		
		Double resto = qtdPaginas % 2;
		
		if (resto > 0) {
			qtdPaginas++;
		}
		
		return qtdPaginas.intValue();
	}

	public List<ModelLogin> pesquisarUsuario(String nomePesquisa, Long usuarioLogado) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE nome LIKE UPPER(?) and admin is false and id_cadastro = ? LIMIT " + LIMITEPAGINACAOCADASTRO;
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
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		return modelLogin;		
	}
	
	public List<ModelLogin> listarUsuarios(Long usuarioLogado) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login where admin is false and id_cadastro = " +usuarioLogado+ " ORDER BY nome LIMIT " + LIMITEPAGINACAOCADASTRO;
		
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
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));			
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
			
			lista.add(modelLogin);
		}

		return lista;
		
	}

	
	public List<ModelLogin> listarUsuariosPaginados(Long usuarioLogado, Integer paginacao) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login where admin is false and id_cadastro = " + usuarioLogado + " ORDER BY NOME OFFSET " +paginacao+ " LIMIT " + LIMITEPAGINACAOCADASTRO;
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
	
	public int totalPaginas(Long usuarioLogado, Integer paginacao) throws Exception {
		
		String sql = "SELECT COUNT(1) as totalcadastros FROM model_login WHERE id_cadastro = " + usuarioLogado;
		PreparedStatement preparedStatement = conn.prepareStatement(sql);	
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next();
		
		Double totalCadastros = resultSet.getDouble("totalcadastros");
				
		Double qtdPaginas = totalCadastros / paginacao;
		
		Double resto = qtdPaginas % 2;
		
		if (resto > 0) {
			qtdPaginas++;
		}
		
		return qtdPaginas.intValue();
	}
	
	public List<ModelLogin> pesquisarUsuarioOffSet(String nomePesquisa, Long usuarioLogado, int offSet) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE nome LIKE UPPER(?) and admin is false and id_cadastro = ? ORDER BY NOME OFFSET ? LIMIT " + LIMITEPAGINACAOCADASTRO;
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "%" + nomePesquisa + "%");
		preparedStatement.setLong(2, usuarioLogado);
		preparedStatement.setInt(3, offSet);
		
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
	
	public ModelLogin consultarUsuarioId(Long id) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "SELECT * FROM model_login WHERE id = ? and admin is false";

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		preparedStatement.setLong(1, id);

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
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
		}
		return modelLogin;		
	}
	

	public List<ModelLogin> listarTodosUsuarios(Long usuarioLogado) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login where admin is false and id_cadastro = " +usuarioLogado+ " ORDER BY nome";
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
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));			
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
			
			modelLogin.setTelefones(this.listarTelefonesPorID(modelLogin.getId()));
			
			lista.add(modelLogin);
		}

		return lista;		
	}
	
	
	public List<ModelTelefone> listarTelefonesPorID(Long idUsuario) throws Exception{
		
		List<ModelTelefone> listaTelefonesPorId = new ArrayList<ModelTelefone>();
		
		String sql = "SELECT * FROM model_telefone WHERE id_usuario = ?";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUsuario);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(resultSet.getLong("id"));
			modelTelefone.setNumero(resultSet.getString("numero"));		

			modelTelefone.setIdUsuario(this.consultarUsuarioId(resultSet.getLong("id_usuario")));
			modelTelefone.setIdCadastro(this.consultarUsuarioId(resultSet.getLong("id_cadastro")));
			
			listaTelefonesPorId.add(modelTelefone);
		}
		
		return listaTelefonesPorId;
	}
	
	public List<ModelLogin> listarTodosUsuariosPorDataNascimento(Long usuarioLogado, Date dataInicial, Date dataFinal) throws Exception {

		List<ModelLogin> lista = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login where admin is false and id_cadastro = " +usuarioLogado+ " AND datanascimento  BETWEEN ? and ? ORDER BY nome";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);	
		
		preparedStatement.setDate(1, dataInicial);
		preparedStatement.setDate(2, dataFinal);
		
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setNome(resultSet.getString("nome"));
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));			
			modelLogin.setDataNascimento(resultSet.getDate("datanascimento"));			
			modelLogin.setRendaMensal(resultSet.getDouble("rendamensal"));
			
			modelLogin.setTelefones(this.listarTelefonesPorID(modelLogin.getId()));
			
			lista.add(modelLogin);
		}

		return lista;		
	}
	

}
