package persistencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Musica;
import model.Playlist;

public class PlaylistDAO {
	private ConexaoMySql conexao;

	public PlaylistDAO() {
		conexao = new ConexaoMySql();
	}
	
	//Salvando Playlist
	public void salvar(Playlist playlist) {
		//abre conexão
		conexao.abrirConexao();
		//prompt do bd
		String sql= "INSERT INTO playlist (nome,foto_da_capa_url,bio,tempo_de_streaming,id_ouvinte) VALUES(?,?,?,?,?)";
		try {
			//preparedStatement pega a conexao e "trabalha" no bd com os dados
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			//formatação da string por index de:"?"
			st.setString(1,playlist.getNome());
			st.setString(2, playlist.getFotoDaCapaURL());
			st.setString(3,playlist.getBio());
			st.setInt(4,playlist.getTempoStreaming());
			st.setLong(5,playlist.getCriador().getIdOuvinte());
			//execução no cmd do sql
			st.executeUpdate(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			//fecha conexão
			conexao.fecharConexao();
		}
		
	}
	
	//EDITAR 
		public void editar(Playlist playlist) {
			// abrir a conexao
			conexao.abrirConexao();
			// montar uma String de insert
			String sql = "UPDATE playlist SET nome = ?,foto_da_capa_url = ?, bio = ?, tempo_de_streaming = ? WHERE id_playlist = ?;";
			try {
				// preparar o update para ser executado
				PreparedStatement st = conexao.getConexao().prepareStatement(sql);

				st.setString(1, playlist.getNome());
				st.setString(2, playlist.getFotoDaCapaURL());
				st.setString(3, playlist.getBio());
				st.setInt(4,  playlist.getTempoStreaming());
				st.setLong(5, playlist.getIdPlaylist());
				
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
		public void excluirPorId(Playlist playlist) {
			conexao.abrirConexao();
			String sql = "DELETE FROM playlist WHERE id_playlist = ?;DELETE FROM salvo WHERE id_playlist = ?;";
			try {
				PreparedStatement st = conexao.getConexao().prepareStatement(sql);
				st.setLong(1, playlist.getIdPlaylist());
				st.setLong(2, playlist.getIdPlaylist());
				st.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				conexao.fecharConexao();
			}
		}
		
		//BUSCAR
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Adicionando Música na Playlist
	public void adicionarMusica(Musica musica, Playlist playlist) {
		conexao.abrirConexao();
		String sql = "INSERT INTO salvo (id_playlist,id_musica) VALUES(?,?);";
		try{
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, playlist.getIdPlaylist());
			st.setLong(2, musica.getIdMusica());
			st.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		
	}
	
	
}
