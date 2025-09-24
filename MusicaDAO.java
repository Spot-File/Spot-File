package persistencia;
import model.Musica;


import java.sql.PreparedStatement;
import java.sql.SQLException;


public class MusicaDAO {
	private ConexaoMySql conexao;
	
	public MusicaDAO() {
		conexao = new ConexaoMySql();
	}
	
	//SALVAR 
	public void salvar(Musica musica) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "INSERT INTO musica (nome,duracao,id_album) VALUES(?,?,?);";
		try {
			// preparar o insert para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, musica.getNome());
			st.setInt(2, musica. getDuracaoMusica());
			//para criar a musica precisa-se criar o album first 
			st.setLong(3, musica.getAlbum().getIdAlbum());
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
	public void editar(Musica musica) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "UPDATE musica SET nome = ?, duracao = ? WHERE id_musica = ?;";
		try {
			// preparar o update para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);

			st.setString(1, musica.getNome());
			st.setInt(2, musica.getDuracaoMusica());
			st.setLong(3, musica.getIdMusica());
			
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
		public void excluirMusicaPorId(Musica musica) {
			conexao.abrirConexao();
			String sql = "DELETE FROM musica WHERE id_musica = ?;";
			try {
				PreparedStatement st = conexao.getConexao().prepareStatement(sql);
				st.setLong(1, musica.getIdMusica());
				st.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally{
				conexao.fecharConexao();
			}
		}
		//BUSCAR
	
	
	
	

	

}
