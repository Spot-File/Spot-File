package persistencia;
import model.Artista;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ArtistaDAO {
private ConexaoMySql conexao;
	
	public ArtistaDAO() {
		conexao = new ConexaoMySql();
	}
	
	//SALVAR 
	public void salvar(Artista artista) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "INSERT INTO artista (email,senha,nome,about,foto_de_perfil_url) VALUES(?,?,?,?,?);";
		try {
			// preparar o insert para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, artista.getEmail());
			st.setString(2, artista.getSenha());
			st.setString(3, artista.getNome());
			st.setString(4, artista.getAbout());			
			st.setString(5, artista.getFotoPerfil());
			// executar essa string de insert
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fechar a conexao
			conexao.fecharConexao();
		}

	}
	
	//EDITAR 
	public void editar(Artista artista) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "UPDATE artista SET nome = ?, email = ?, senha = ?, about = ?, foto_de_perfil_url = ? WHERE id_artista =  ?;";
		try {
			// preparar o update para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);

			st.setString(1, artista.getNome());
			st.setString(2, artista.getEmail() );
			st.setString(3, artista.getSenha()  );
			st.setString(4, artista.getAbout() );
			st.setString(5, artista.getFotoPerfil());
			st.setLong(6, artista.getIdArtista());
			// executar essa string de update
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fechar a conexao
			conexao.fecharConexao();
		}

	}
	//EXCLUIR
			public void excluirArtistaPorId(Artista artista) {
				conexao.abrirConexao();
				String sql = "DELETE FROM artista WHERE id_artista = ?;";
				try {
					PreparedStatement st = conexao.getConexao().prepareStatement(sql);
					st.setLong(1,artista.getIdArtista());
					st.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace();
				}finally {
					conexao.fecharConexao();
				}
		
	    }
	}
	//BUSCAR

