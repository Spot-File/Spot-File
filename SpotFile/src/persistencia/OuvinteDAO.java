package persistencia;

import model.Artista;
import model.Ouvinte;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OuvinteDAO {
	private ConexaoMySql conexao;
	
	public OuvinteDAO() {
		conexao = new ConexaoMySql();
	}
	
	//SALVAR 
	public void salvar(Ouvinte ouvinte) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "INSERT INTO ouvinte (email,senha,nome,foto_de_perfil_url) VALUES(?,?,?,?);";
		try {
			// preparar o insert para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, ouvinte.getEmail());
			st.setString(2, ouvinte.getSenha());
			st.setString(3, ouvinte.getNome());
			st.setString(4, ouvinte.getFotoPerfil());
			// executar essa string de insert
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fechar a conexao
			conexao.fecharConexao();
		}

	}
	
	//Seguir Ouvinte
	public void seguirOuvinte(Ouvinte seguidor, Ouvinte seguido) {
		conexao.abrirConexao();
		String sql = "INSERT INTO seguidores (id_seguido,id_seguidor) VALUES(?,?); ";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, seguido.getIdOuvinte());
			st.setLong(2, seguidor.getIdOuvinte());
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
	}

	//Seguir Artista
	public void seguirArtista(Ouvinte ouvinte, Artista artista) {
		conexao.abrirConexao();
		String sql = "INSERT INTO fans (id_artista,id_seguidor) VALUES(?,?); ";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, artista.getIdArtista());
			st.setLong(2, ouvinte.getIdOuvinte());
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
	}

}
