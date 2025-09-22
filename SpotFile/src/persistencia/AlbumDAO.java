package persistencia;

import java.sql.PreparedStatement;
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
	
	
}
