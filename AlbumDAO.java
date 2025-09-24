package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Album;

public class AlbumDAO {
private ConexaoMySql conexao;
	
	public AlbumDAO() {
		conexao = new ConexaoMySql();
	}
	
	//SALVAR 
	public void salvar(Album album) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "INSERT INTO album (ano_lancamento,nome,foto_da_capa_url,id_artista) VALUES(?,?,?,?);";
		try {
			// preparar o insert para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setInt(1, album.getAnoLancamento());
			st.setString(2, album.getNome());
			st.setString(3, album.getFotoDaCapaURL());
			st.setLong(4,album.getCriador().getIdArtista());
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
	public void editar(Album album) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "UPDATE album SET nome = ?, ano_lancamento = ?, foto_da_capa_url = ?, tempo_de_streaming = ? WHERE id_album = ?;";
		try {
			// preparar o update para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);

			st.setString(1, album.getNome() );
			st.setInt(2, album.getAnoLancamento() );
			st.setString(3, album.getFotoDaCapaURL() );
			st.setInt(4, album.getTempoStreaming());
			st.setLong(5, album.getIdAlbum());
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
		public void excluirAlbumPorId(Album album) {
			conexao.abrirConexao();
			String sql = "DELETE FROM album WHERE id_album = ?;";
			try {
				PreparedStatement st = conexao.getConexao().prepareStatement(sql);
				st.setLong(1,album.getIdAlbum());
				st.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				conexao.fecharConexao();
			}
					
		}
		
	//BUSCAR (por id) 
		public void buscarPorId(long id) {
			// abrir a conexao
			conexao.abrirConexao();
			// montar uma String de insert
			String sql = "SELECT * FROM endereco WHERE id_usuario=?;";
			try {
				// preparar o update para ser executado
				PreparedStatement st = conexao.getConexao().prepareStatement(sql);
				st.setLong(1, id);
				// executar essa string de update
				ResultSet rs = st.executeQuery();
				while(rs.next())
				{
					System.out.println("ID do endereço: " + rs.getLong(1));
					System.out.println("ID do usuário associado: " + rs.getLong(2));
					System.out.println("Rua: " + rs.getString(3));
					System.out.println("Número: " + rs.getString(4));
					System.out.println("Cidade: " + rs.getString(5));
					System.out.println("Estado: " + rs.getString(6));
				}
			} catch (SQLException e) {
				System.out.println("O ID digitado não existe dentro do banco de dados.");
			} finally {
				// fechar a conexao
				conexao.fecharConexao();
			}
		}
			
		
		
		
		
	//BUSCAR (todes)
	
}
