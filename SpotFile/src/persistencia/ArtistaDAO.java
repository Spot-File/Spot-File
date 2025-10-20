
package persistencia;

import model.Album;
import model.Artista;
import model.Ouvinte;
import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistaDAO {
	private ConexaoMySql conexao;

	public ArtistaDAO() {
		conexao = new ConexaoMySql();
	}

	// SALVAR
	public void salvar(Artista artista) {
		conexao.abrirConexao();
		String sql = "INSERT INTO artista (email,senha,nome,about,foto_de_perfil) VALUES(?,?,?,?,?);";
		try {

			PreparedStatement st = conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, artista.getEmail());
			st.setString(2, artista.getSenha());
			st.setString(3, artista.getNome());
			st.setString(4, artista.getAbout());
			st.setString(5, artista.getFotoPerfil());
			st.executeUpdate();

			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				artista.setIdArtista(rs.getLong(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fechar a conexao
			conexao.fecharConexao();
		}

	}

	// EDITAR
	public void editar(Artista artista) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "UPDATE artista SET nome = ?, email = ?, senha = ?, about = ?, foto_de_perfil = ? WHERE id_artista =  ?;";
		try {
			// preparar o update para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);

			st.setString(1, artista.getNome());
			st.setString(2, artista.getEmail());
			st.setString(3, artista.getSenha());
			st.setString(4, artista.getAbout());
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

	// EXCLUIR
	public void excluirArtistaPorId(Artista artista) {
		conexao.abrirConexao();
		String sql = "DELETE FROM musica WHERE id_album = ?;";
		String sql1 = "DELETE FROM album WHERE id_artista = ?;";
		String sql2 = "DELETE FROM fans WHERE id_artista = ?;";
		String sql3 = "DELETE FROM artista WHERE id_artista = ?;";
		try {
			
			for (Album album : buscarListaAlbumPorIdArtista(artista.getIdArtista())) {
				conexao.abrirConexao();
				PreparedStatement st = conexao.getConexao().prepareStatement(sql);
				st.setLong(1, album.getIdAlbum());
				st.executeUpdate();
				st.close();
			}

			PreparedStatement st1 = conexao.getConexao().prepareStatement(sql1);
			st1.setLong(1, artista.getIdArtista());
			st1.executeUpdate();

			PreparedStatement st2 = conexao.getConexao().prepareStatement(sql2);
			st2.setLong(1, artista.getIdArtista());
			st2.executeUpdate();

			PreparedStatement st3 = conexao.getConexao().prepareStatement(sql3);
			st3.setLong(1, artista.getIdArtista());
			st3.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		
	}

	// DELETAR POR EMAIL
	public void excluirArtistaPorEmail(String email) {
		conexao.abrirConexao();
		String sql = "SELECT id_artista FROM artista WHERE email = ?;";
		String sql1 = "DELETE FROM musica WHERE id_album = ?;";
		String sql2 = "DELETE FROM album WHERE id_artista = ?;";
		String sql3 = "DELETE FROM fans WHERE id_artista = ?;";
		String sql4 = "DELETE FROM artista WHERE id_artista = ?;";

		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			long idArtista = 0;
			if(rs.next()) {
				idArtista = rs.getLong(1);
			}
			
			for (Album album : buscarListaAlbumPorIdArtista(idArtista)) {
				PreparedStatement st1 = conexao.getConexao().prepareStatement(sql1);
				st1.setLong(1, album.getIdAlbum());
				st1.executeUpdate();
				st1.close();
			}

			PreparedStatement st2 = conexao.getConexao().prepareStatement(sql2);
			st2.setLong(1, idArtista);
			st2.executeUpdate();

			PreparedStatement st3 = conexao.getConexao().prepareStatement(sql3);
			st3.setLong(1, idArtista);
			st3.executeUpdate();

			PreparedStatement st4 = conexao.getConexao().prepareStatement(sql4);
			st4.setLong(1, idArtista);
			st4.executeUpdate();

			st.close();
			st2.close();
			st3.close();
			st4.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
	}

	// BUSCAR
	public Artista buscarPorIdArtista(long id_artista) {
		conexao.abrirConexao();
		String sql = "SELECT*FROM artista WHERE id_artista = ?;";
		Artista artista = new Artista();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_artista);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				long idArtista = rs.getLong("id_artista");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String about = rs.getString("about");
				String fotoPerfil = rs.getString("foto_de_perfil");

				artista.setIdArtista(idArtista);
				artista.setAbout(about);
				artista.setEmail(email);
				artista.setSenha(senha);
				artista.setNome(nome);
				artista.setFotoPerfil(fotoPerfil);
				artista.setAlbuns(buscarListaAlbumPorIdArtista(idArtista));
				artista.setFans(buscarFans(idArtista));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return artista;
	}

	public Artista buscarArtistaPorEmail(String email) {
		conexao.abrirConexao();
		String sql = "SELECT*FROM artista WHERE email = ?;";
		Artista artista = new Artista();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idArtista = rs.getLong("id_artista");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String about = rs.getString("about");
				String fotoPerfil = rs.getString("foto_de_perfil");

				artista.setIdArtista(idArtista);
				artista.setAbout(about);
				artista.setEmail(email);
				artista.setSenha(senha);
				artista.setNome(nome);
				artista.setFotoPerfil(fotoPerfil);
				artista.setAlbuns(buscarListaAlbumPorIdArtista(idArtista));
				artista.setFans(buscarFans(idArtista));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return artista;
	}

	public Usuario buscarUsuarioArtista(long id_artista) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM ouvinte WHERE id_ouvinte = ?;";
		Usuario artista = new Artista();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1,id_artista);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				String senha = rs.getString("senha");
				String email = rs.getString("email");
				String nome = rs.getString("nome");
				artista.setNome(nome);
				artista.setEmail(email);
				artista.setSenha(senha);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return artista;
	}
	
	public List<Artista> buscarListaArtistaPorNome(String nome) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM artista WHERE nome LIKE ?;";
		List<Artista> artistas = new ArrayList<Artista>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, "%" + nome + "%");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idArtista = rs.getLong("id_artista");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String nome0 = rs.getString("nome");
				String about = rs.getString("about");
				String fotoPerfil = rs.getString("foto_de_perfil");

				Artista artista = new Artista(nome0, email, senha, fotoPerfil, idArtista, about,
						buscarListaAlbumPorIdArtista(idArtista), buscarFans(idArtista));
				artistas.add(artista);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return artistas;
	}

	public List<Artista> buscarListaArtistas() {
		conexao.abrirConexao();
		String sql = "SELECT*FROM artista;";
		List<Artista> artistas = new ArrayList<Artista>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idArtista = rs.getLong("id_artista");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String about = rs.getString("about");
				String fotoPerfil = rs.getString("foto_de_perfil");

				Artista artista = new Artista(nome, email, senha, fotoPerfil, idArtista, about,
						buscarListaAlbumPorIdArtista(idArtista), buscarFans(idArtista));
				artistas.add(artista);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return artistas;
	}

	public List<Album> buscarListaAlbumPorIdArtista(long id_artista) {
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
				AlbumDAO aDAO = new AlbumDAO();
				Album album = new Album(aDAO.buscarListaMusicaPorIdAlbum(idAlbum), fotoDaCapa, tempoStreaming, nome,
						idAlbum, anoLancamento, idArtista);
				albuns.add(album);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return albuns;
	}

	public List<Ouvinte> buscarFans(long id_artista) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM fans WHERE id_artista = ?;";
		List<Ouvinte> fans = new ArrayList<Ouvinte>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_artista);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idOuvinte = rs.getLong("id_ouvinte");
				OuvinteDAO oDAO = new OuvinteDAO();
				fans.add(oDAO.buscarPorIdOuvinte(idOuvinte));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return fans;
	}

}
