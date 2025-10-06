package persistencia;

import model.Album;
import model.Musica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AlbumDAO {
	private ConexaoMySql conexao;

	public AlbumDAO() {
		conexao = new ConexaoMySql();
	}

	// SALVAR
	public void salvar(Album album) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "INSERT INTO album (ano_lancamento,nome,foto_da_capa_url,id_artista) VALUES(?,?,?,?);";
		try {
			// preparar o insert para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, album.getAnoLancamento());
			st.setString(2, album.getNome());
			st.setString(3, album.getFotoDaCapaURL());
			st.setLong(4, album.getIdArtista());
			// executar essa string de insert
			st.executeUpdate();
			
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				album.setIdAlbum(rs.getLong("id_album"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fechar a conexao
			conexao.fecharConexao();
		}

	}

	// EDITAR
	public void editar(Album album) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "UPDATE album SET nome = ?, ano_lancamento = ?, foto_da_capa_url = ?, tempo_de_streaming = ? WHERE id_album = ?;";
		try {
			// preparar o update para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);

			st.setString(1, album.getNome());
			st.setInt(2, album.getAnoLancamento());
			st.setString(3, album.getFotoDaCapaURL());
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

	// EXCLUIR
	public void excluirAlbumPorId(long id_album) {
		conexao.abrirConexao();
		String sql = "DELETE FROM album WHERE id_album = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1,id_album);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}

	}

	// BUSCAR (por id)
	public Album buscarPorId(long id_album) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "SELECT * FROM album WHERE id_album = ?";
		Album album = new Album();
		try {
			// preparar o update para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_album);
			// executar essa string de update
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idAlbum = rs.getLong("id_album");
				int anoLancamento = rs.getInt("ano_lancamento");
				String nome = rs.getString("nome");
				String fotoDaCapa = rs.getString("foto_da_capa_url");
				int tempoStreaming = rs.getInt("tempo_de_streaming");
				long idArtista = rs.getLong("id_artista");

				album.setIdAlbum(idAlbum);
				album.setNome(nome);
				album.setTempoStreaming(tempoStreaming);
				album.setAnoLancamento(anoLancamento);
				album.setFotoDaCapaURL(fotoDaCapa);
				album.setMusicas(buscarListaMusicaPorIdAlbum(idAlbum));
				album.setidArtista(idArtista);

			}
		} catch (SQLException e) {
			System.out.println("O ID digitado não existe dentro do banco de dados.");
		} finally {
			// fechar a conexao
			conexao.fecharConexao();
		}
		return album;
	}

	// BUSCAR TODOS (MODELS PRONTOS)
	public List<Album> buscarListaAlbumPorNome(String nome){
		conexao.getConexao();
		String sql = "SELECT * FROM album WHERE nome LIKE ?;";
		List<Album> albuns = new ArrayList<Album>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1,"'%" + nome + "%'");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idAlbum = rs.getLong(1);
				int anoLancamento = rs.getInt(2);
				String nome0 = rs.getString(3);
				String fotoDaCapa = rs.getString(4);
				int tempoStreaming = rs.getInt(5);
				long idArtista = rs.getLong(6);
				Album album = new Album(buscarListaMusicaPorIdAlbum(idAlbum), fotoDaCapa, tempoStreaming, nome0, idAlbum,
						anoLancamento, idArtista);
				albuns.add(album);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return albuns;
	}
	
	public List<Album> buscarListaAlbum() {
		conexao.abrirConexao();
		String sql = "SELECT * FROM album;";
		List<Album> albuns = new ArrayList<Album>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idAlbum = rs.getLong(1);
				int anoLancamento = rs.getInt(2);
				String nome = rs.getString(3);
				String fotoDaCapa = rs.getString(4);
				int tempoStreaming = rs.getInt(5);
				long idArtista = rs.getLong(6);
				Album album = new Album(buscarListaMusicaPorIdAlbum(idAlbum), fotoDaCapa, tempoStreaming, nome, idAlbum,
						anoLancamento, idArtista);
				albuns.add(album);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return albuns;
	}

	public List<Album> buscarListaAlbumPorIdArtista(long id_artista){
		conexao.abrirConexao();
		String sql = "SELECT * FROM album WHERE id_artista = ?;";
		List<Album> albuns = new ArrayList<Album>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_artista);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idAlbum = rs.getLong(1);
				int anoLancamento = rs.getInt(2);
				String nome = rs.getString(3);
				String fotoDaCapa = rs.getString(4);
				int tempoStreaming = rs.getInt(5);
				long idArtista = rs.getLong(6);
				Album album = new Album(buscarListaMusicaPorIdAlbum(idAlbum), fotoDaCapa, tempoStreaming, nome, idAlbum,
						anoLancamento, idArtista);
				albuns.add(album);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return albuns;
	}
	
	public List<Musica> buscarListaMusicaPorIdAlbum(long id_album) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM musica WHERE id_album = ?;";
		List<Musica> musicas = new ArrayList<Musica>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_album);
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
	

	
}
