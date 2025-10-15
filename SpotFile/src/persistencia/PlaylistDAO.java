

package persistencia;


import model.Musica;
import model.Playlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
			PreparedStatement st = conexao.getConexao().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			// formatação da string por index de:"?"
			st.setString(1, playlist.getNome());
			st.setString(2, playlist.getFotoDaCapaURL());
			st.setString(3, playlist.getBio());
			st.setInt(4, playlist.getTempoStreaming());
			st.setLong(5, playlist.getIdOuvinte());
			// execução no cmd do sql
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				playlist.setIdPlaylist(rs.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fecha conexão
			conexao.fecharConexao();
		}

	}
	/// VINCULANDO MUSICAS COM PLAYLIST CHAT AJUDOU 
	public void vincularMusicas(Playlist playlist) {
    conexao.abrirConexao();
    String sql = "INSERT INTO salvo (id_playlist, id_musica) VALUES (?, ?)";
    try {
        PreparedStatement st = conexao.getConexao().prepareStatement(sql);
        for (Musica musica : playlist.getMusicas()) {
            st.setLong(1, playlist.getIdPlaylist());
            st.setLong(2, musica.getIdMusica());
            st.addBatch();
        }
        st.executeBatch();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
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
		String sql = "DELETE FROM salvo WHERE id_playlist = ?;";
		String sql1 = "DELETE FROM playlist WHERE id_playlist = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, playlist.getIdPlaylist());
			st.executeUpdate();
		
			PreparedStatement st1 = conexao.getConexao().prepareStatement(sql1);
			st1.setLong(1, playlist.getIdPlaylist());
			st1.executeUpdate();
			
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

	public Playlist buscarPlaylistFavoritasIdOuvinte(long id_ouvinte) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM playlist WHERE nome = 'Músicas Favoritas' AND id_ouvinte = ?;";
		Playlist favorita = new Playlist();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_ouvinte);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idPlaylist = rs.getLong("id_playlist");
				String nome = rs.getString("nome");
				String fotoCapa = rs.getString("foto_da_capa_url");
				String bio = rs.getString("bio");
				int tempoStreaming = rs.getInt("tempo_de_streaming");
				long idOuvinte = rs.getLong("id_ouvinte");
			
				favorita.setIdPlaylist(idPlaylist);
				favorita.setNome(nome);
				favorita.setFotoDaCapaURL(fotoCapa);
				favorita.setBio(bio);
				favorita.setMusicas(buscarMusicasPorIdPlaylist(idPlaylist));
				favorita.setTempoStreaming(tempoStreaming);
				favorita.setIdOuvinte(idOuvinte);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return favorita;
	}
	
	public List<Playlist> buscarListaPlaylists(){
		conexao.abrirConexao();
		String sql = "SELECT * FROM playlist;";
		List<Playlist> playlists = new ArrayList<Playlist>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idPlaylist = rs.getLong("id_playlist");
				String nome = rs.getString("nome");
				String fotoDaCapa = rs.getString("foto_da_capa_url");
				String bio = rs.getString("bio");
				int tempoStreaming = rs.getInt("tempo_de_streaming");
				long idOuvinte = rs.getLong("id_ouvinte");
				Playlist playlist = new Playlist(buscarMusicasPorIdPlaylist(idPlaylist),fotoDaCapa,tempoStreaming,nome,idPlaylist,bio,idOuvinte);
				playlists.add(playlist);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return playlists;
	}

	// BUSCAR TODOS (MODELS PRONTOS)
	public List<Playlist> buscarListaPlaylistPorNome(String nomeProcurado){
		conexao.abrirConexao();
		String sql = "SELECT * FROM playlist WHERE nome LIKE ?;";
		List<Playlist> playlists = new ArrayList<Playlist>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1,"%" + nomeProcurado + "%");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idPlaylist = rs.getLong("id_playlist");
				String nome = rs.getString("nome");
				String fotoDaCapa = rs.getString("foto_da_capa_url");
				String bio = rs.getString("bio");
				int tempoStreaming = rs.getInt("tempo_de_streaming");
				long idOuvinte = rs.getLong("id_ouvinte");
				Playlist playlist = new Playlist(buscarMusicasPorIdPlaylist(idPlaylist),fotoDaCapa,tempoStreaming,nome,idPlaylist,bio,idOuvinte);
				playlists.add(playlist);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return playlists;
	}
		
	
	
	public List<Playlist> buscarListaPlaylistPorIdOuvinte(long id_ouvinte){
		conexao.abrirConexao();
		String sql = "SELECT * FROM playlist WHERE id_ouvinte = ?;";
		List<Playlist> playlists = new ArrayList<Playlist>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_ouvinte);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idPlaylist = rs.getLong("id_playlist");
				String nome = rs.getString("nome");
				String fotoDaCapa = rs.getString("foto_da_capa_url");
				String bio = rs.getString("bio");
				int tempoStreaming = rs.getInt("tempo_de_streaming");
				long idOuvinte = rs.getLong("id_ouvinte");
				Playlist playlist = new Playlist(buscarMusicasPorIdPlaylist(idPlaylist),fotoDaCapa,tempoStreaming,nome,idPlaylist,bio,idOuvinte);
				playlists.add(playlist);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return playlists;
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
				MusicaDAO mDAO = new MusicaDAO();
				Musica musica = mDAO.buscarPorId(idMusica);
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

	
	public void removerMusica(Musica musica,Playlist playlist) {
		conexao.abrirConexao();
		String sql = "DELETE FROM salvo WHERE id_musica = ? AND id_playlist = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, musica.getIdMusica());
			st.setLong(2, playlist.getIdPlaylist());
			st.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
