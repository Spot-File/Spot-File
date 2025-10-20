
package persistencia;

import model.Artista;
import model.Ouvinte;
import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OuvinteDAO {
	private ConexaoMySql conexao;

	public OuvinteDAO() {
		conexao = new ConexaoMySql();
	}

	// SALVAR
	public void salvar(Ouvinte ouvinte) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "INSERT INTO ouvinte (email,senha,nome,foto_de_perfil) VALUES(?,?,?,?);";
		String sql1 = "INSERT INTO playlist (nome,id_ouvinte) VALUES('Músicas Favoritas',?);";
		try {
			// preparar o insert para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, ouvinte.getEmail());
			st.setString(2, ouvinte.getSenha());
			st.setString(3, ouvinte.getNome());
			st.setString(4, ouvinte.getFotoPerfil());
			// executar essa string de insert
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				ouvinte.setIdOuvinte(rs.getLong(1));
			}

			PreparedStatement st1 = conexao.getConexao().prepareStatement(sql1);
			st1.setLong(1, ouvinte.getIdOuvinte());
			st1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// fechar a conexao
			conexao.fecharConexao();
		}

	}

	// EDITAR
	public void editar(Ouvinte ouvinte) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "UPDATE ouvinte SET nome = ?,email = ?, senha = ?, foto_de_perfil = ? WHERE id_ouvinte = ?;";
		try {
			// preparar o update para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);

			st.setString(1, ouvinte.getNome());
			st.setString(2, ouvinte.getEmail());
			st.setString(3, ouvinte.getSenha());
			st.setString(4, ouvinte.getFotoPerfil());
			st.setLong(5, ouvinte.getIdOuvinte());

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
	public void excluirPorId(Ouvinte ouvinte) {
		conexao.abrirConexao();
		String sql = "DELETE FROM playlist WHERE id_ouvinte = ?;";
		String sql1 = "DELETE FROM fans WHERE id_ouvinte = ?;";
		String sql2 = "DELETE FROM seguidores WHERE id_seguido = ?;";
		String sql3 = "DELETE FROM seguidores WHERE id_seguidor = ?;";
		String sql4 = "DELETE FROM ouvinte WHERE id_ouvinte = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, ouvinte.getIdOuvinte());
			st.executeUpdate();
			
			PreparedStatement st1 = conexao.getConexao().prepareStatement(sql1);
			st1.setLong(1, ouvinte.getIdOuvinte());
			st1.executeUpdate();
			
			PreparedStatement st2 = conexao.getConexao().prepareStatement(sql2);
			st2.setLong(1, ouvinte.getIdOuvinte());
			st2.executeUpdate();
			
			PreparedStatement st3 = conexao.getConexao().prepareStatement(sql3);
			st3.setLong(1, ouvinte.getIdOuvinte());
			st3.executeUpdate();
			
			PreparedStatement st4 = conexao.getConexao().prepareStatement(sql4);
			st4.setLong(1, ouvinte.getIdOuvinte());
			st4.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
	}

	public void excluirPorEmail(String email) {
		conexao.abrirConexao();
		String sql = "SELECT id_ouvinte FROM ouvinte WHERE email = ?;";
		String sql1 = "DELETE FROM playlist WHERE id_ouvinte = ?;";
		String sql2 = "DELETE FROM fans WHERE id_ouvinte = ?;";
		String sql3 = "DELETE FROM seguidores WHERE id_seguido = ?;";
		String sql4 = "DELETE FROM seguidores WHERE id_seguidor = ?;";
		String sql5 = "DELETE FROM ouvinte WHERE id_ouvinte = ?;";
		
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			long idOuvinte = 0;
			while(rs.next()) {
				idOuvinte = rs.getLong(1);
			}
			
			PreparedStatement st1 = conexao.getConexao().prepareStatement(sql1);
			st1.setLong(1, idOuvinte);
			st1.executeUpdate();
			
			PreparedStatement st2 = conexao.getConexao().prepareStatement(sql2);
			st2.setLong(1, idOuvinte);
			st2.executeUpdate();
			
			PreparedStatement st3 = conexao.getConexao().prepareStatement(sql3);
			st3.setLong(1, idOuvinte);
			st3.executeUpdate();
			
			PreparedStatement st4 = conexao.getConexao().prepareStatement(sql4);
			st4.setLong(1, idOuvinte);
			st4.executeUpdate();
			
			PreparedStatement st5 = conexao.getConexao().prepareStatement(sql5);
			st5.setLong(1, idOuvinte);
			st5.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
	
	}
	// BUSCAR
	public Ouvinte buscarPorIdOuvinte(long id_ouvinte) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM ouvinte WHERE id_ouvinte = ?";
		Ouvinte ouvinte = new Ouvinte();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_ouvinte);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idOuvinte = rs.getLong("id_ouvinte");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String fotoPerfil = rs.getString("foto_de_perfil");

				PlaylistDAO pDAO = new PlaylistDAO();
				ouvinte.setIdOuvinte(idOuvinte);
				ouvinte.setNome(nome);
				ouvinte.setEmail(email);
				ouvinte.setSenha(senha);
				ouvinte.setFotoPerfil(fotoPerfil);
				ouvinte.setPlaylistFavoritas(pDAO.buscarPlaylistFavoritasIdOuvinte(idOuvinte));
				ouvinte.setPlaylists(pDAO.buscarListaPlaylistPorIdOuvinte(idOuvinte));
				ouvinte.setFollowers(buscarSeguidores(idOuvinte));
				ouvinte.setFollowing(buscarListaUsuarioSeguido(idOuvinte));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return ouvinte;
	}

	public Ouvinte buscarOuvintePorEmail(String email) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM ouvinte WHERE email = ?;";
		Ouvinte ouvinte = new Ouvinte();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1,email);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idOuvinte = rs.getLong("id_ouvinte");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String fotoPerfil = rs.getString("foto_de_perfil");

				PlaylistDAO pDAO = new PlaylistDAO();
				ouvinte.setIdOuvinte(idOuvinte);
				ouvinte.setNome(nome);
				ouvinte.setEmail(email);
				ouvinte.setSenha(senha);
				ouvinte.setFotoPerfil(fotoPerfil);
				ouvinte.setPlaylistFavoritas(pDAO.buscarPlaylistFavoritasIdOuvinte(idOuvinte));
				ouvinte.setPlaylists(pDAO.buscarListaPlaylistPorIdOuvinte(idOuvinte));
				ouvinte.setFollowers(buscarSeguidores(idOuvinte));
				ouvinte.setFollowing(buscarListaUsuarioSeguido(idOuvinte));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return ouvinte;
	}
	
	public List<Ouvinte> buscarListaOuvintePorNome(String nomeProcurado){
		conexao.abrirConexao();
		String sql = "SELECT * FROM ouvinte WHERE nome LIKE ?;";
		List<Ouvinte> ouvintes = new ArrayList<Ouvinte>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1,"%"+ nomeProcurado + "%");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idOuvinte = rs.getLong("id_ouvinte");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String fotoPerfil = rs.getString("foto_de_perfil");
				PlaylistDAO pDAO = new PlaylistDAO();
				Ouvinte ouvinte = new Ouvinte(nome, email, senha, fotoPerfil, idOuvinte,
						pDAO.buscarPlaylistFavoritasIdOuvinte(idOuvinte),
						pDAO.buscarListaPlaylistPorIdOuvinte(idOuvinte), 
						buscarSeguidores(idOuvinte),buscarListaUsuarioSeguido(idOuvinte));
				ouvintes.add(ouvinte);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return ouvintes;
	}
	
	public List<Ouvinte> buscarListaOuvinte() {
		conexao.abrirConexao();
		String sql = "SELECT*FROM ouvinte;";
		List<Ouvinte> ouvintes = new ArrayList<Ouvinte>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idOuvinte = rs.getLong("id_ouvinte");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String fotoPerfil = rs.getString("foto_de_perfil");
				PlaylistDAO pDAO = new PlaylistDAO();
				Ouvinte ouvinte = new Ouvinte(nome, email, senha, fotoPerfil, idOuvinte,
						pDAO.buscarPlaylistFavoritasIdOuvinte(idOuvinte),
						pDAO.buscarListaPlaylistPorIdOuvinte(idOuvinte), 
						buscarSeguidores(idOuvinte),buscarListaUsuarioSeguido(idOuvinte));
				ouvintes.add(ouvinte);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return ouvintes;
	}

	public List<Ouvinte> buscarSeguidores(long id_ouvinte) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM seguidores WHERE id_seguido = ?;";
		List<Ouvinte> seguidores = new ArrayList<Ouvinte>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_ouvinte);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idSeguidor = rs.getLong("id_seguidor");
				seguidores.add(buscarPorIdOuvinte(idSeguidor));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return seguidores;
	}

	public Usuario buscarUsuarioOuvinte(long id_ouvinte) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM ouvinte WHERE id_ouvinte = ?;";
		Usuario ouvinte = new Ouvinte();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1,id_ouvinte);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				String senha = rs.getString("senha");
				String email = rs.getString("email");
				String nome = rs.getString("nome");
				ouvinte.setNome(nome);
				ouvinte.setEmail(email);
				ouvinte.setSenha(senha);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return ouvinte;
	}
	
	public List<Usuario> buscarListaUsuarioSeguido(long id_ouvinte) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM seguidores WHERE id_seguidor = ?;";
		String sql1 = "SELECT * FROM fans WHERE id_ouvinte = ?";
		List<Usuario> seguidos = new ArrayList<Usuario>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_ouvinte);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idSeguido = rs.getLong("id_seguido");
				seguidos.add(buscarUsuarioOuvinte(idSeguido));
			}

			PreparedStatement st1 = conexao.getConexao().prepareStatement(sql1);
			st1.setLong(1, id_ouvinte);
			ResultSet rs1 = st1.executeQuery();
			while (rs1.next()) {
				long idArtista = rs1.getLong("id_artista");
				ArtistaDAO atDAO = new ArtistaDAO();
				seguidos.add(atDAO.buscarUsuarioArtista(idArtista));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return seguidos;
	}

	// Seguir Ouvinte
	public void seguirOuvinte(Ouvinte seguidor, Ouvinte seguido) {
		conexao.abrirConexao();
		String sql = "INSERT INTO seguidores (id_seguido,id_seguidor) VALUES(?,?); ";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, seguido.getIdOuvinte());
			st.setLong(2, seguidor.getIdOuvinte());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
	}

	// Seguir Artista
	public void seguirArtista(Ouvinte ouvinte, Artista artista) {
		conexao.abrirConexao();
		String sql = "INSERT INTO fans (id_artista,id_ouvinte) VALUES(?,?); ";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, artista.getIdArtista());
			st.setLong(2, ouvinte.getIdOuvinte());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
	}
	public void unfollowOuvinte(Ouvinte seguidor, Ouvinte seguido) {
		conexao.abrirConexao();
		String sql = "DELETE FROM seguidores WHERE id_seguido = ? AND id_seguidor = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, seguido.getIdOuvinte());
			st.setLong(2, seguidor.getIdOuvinte());
			st.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
	}
	
	public void unfollowArtista(Ouvinte ouvinte, Artista artista) {
		conexao.abrirConexao();
		String sql = "DELETE FROM fans WHERE id_ouvinte = ? AND id_artista = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, ouvinte.getIdOuvinte());
			st.setLong(2, artista.getIdArtista());
			st.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
	}

}
