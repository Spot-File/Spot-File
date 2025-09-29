package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.Musica;
import model.Playlist;

public class PlaylistDAO {
	private ConexaoMySql conexao;

	public PlaylistDAO() {
		conexao = new ConexaoMySql();
	}

	// Salvando Playlist
	public void salvar(Playlist playlist) {
		// abre conexão
		conexao.abrirConexao();
		// prompt do bd
		String sql = "INSERT INTO playlist (nome,foto_da_capa_url,bio,tempo_de_streaming,id_ouvinte) VALUES(?,?,?,?,?)";
		try {
			// preparedStatement pega a conexao e "trabalha" no bd com os dados
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			// formatação da string por index de:"?"
			st.setString(1, playlist.getNome());
			st.setString(2, playlist.getFotoDaCapaURL());
			st.setString(3, playlist.getBio());
			st.setInt(4, playlist.getTempoStreaming());
			st.setLong(5, playlist.getIdOuvinte());
			// execução no cmd do sql
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fecha conexão
			conexao.fecharConexao();
		}

	}

	// EDITAR
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
			st.setInt(4, playlist.getTempoStreaming());
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

	// EXCLUIR
	public void excluirPorId(Playlist playlist) {
		conexao.abrirConexao();
		String sql = "DELETE FROM playlist WHERE id_playlist = ?;DELETE FROM salvo WHERE id_playlist = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, playlist.getIdPlaylist());
			st.setLong(2, playlist.getIdPlaylist());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
	}

	// BUSCAR
	public Playlist buscarPlaylistPorId(long id_playlist) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM playlist WHERE id_playlist = ?";
		Playlist playlist = new Playlist();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_playlist);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idPlaylist = rs.getLong("id_playlist");
				String nome = rs.getString("nome");
				String fotoDaCapa = rs.getString("foto_da_capa_url");
				String bio = rs.getString("bio");
				int tempoStreaming = rs.getInt("tempo_de_streaming");
				long idOuvinte = rs.getLong("id_ouvinte");

				playlist.setIdPlaylist(idPlaylist);
				playlist.setNome(nome);
				playlist.setBio(bio);
				playlist.setTempoStreaming(tempoStreaming);
				playlist.setFotoDaCapaURL(fotoDaCapa);
				playlist.setMusicas(buscarMusicasPorIdPlaylist(idPlaylist));
				playlist.setIdOuvinte(idOuvinte);

			}
		} catch (SQLException e) {
			System.out.println("O ID digitado não existe dentro do banco de dados.");
		} finally {
			// fechar a conexao
			conexao.fecharConexao();
		}
		return playlist;
	}

	public List<Playlist> buscarListaPlaylists(){
		
	}

	public List<Playlist> buscarListaPlaylistPorIdOuvinte(){
			
			
	}
	
	public List<Musica> buscarMusicasPorIdPlaylist(long id_playlist) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM salvo WHERE id_playlist = ?;";
		List<Musica> musicas = new ArrayList<Musica>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_playlist);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idMusica = rs.getLong("id_musica");
				String nome = rs.getString("nome");
				int duracao = rs.getInt("duracao");
				long idAlbum = rs.getLong("id_album");
				Musica musica = new Musica(idMusica, nome, duracao, idAlbum);
				musicas.add(musica);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return musicas;
	}

	
	
	
	// Adicionando Música na Playlist
	public void adicionarMusica(Musica musica, Playlist playlist) {
		conexao.abrirConexao();
		String sql = "INSERT INTO salvo (id_playlist,id_musica) VALUES(?,?);";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, playlist.getIdPlaylist());
			st.setLong(2, musica.getIdMusica());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}

	}

}
