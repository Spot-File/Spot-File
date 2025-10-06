package persistencia;

import model.Artista;
import model.Ouvinte;

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
		String sql = "INSERT INTO artista (email,senha,nome,about,foto_de_perfil_url) VALUES(?,?,?,?,?);";
		try {
			
			PreparedStatement st = conexao.getConexao().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			st.setString(1, artista.getEmail());
			st.setString(2, artista.getSenha());
			st.setString(3, artista.getNome());
			st.setString(4, artista.getAbout());
			st.setString(5, artista.getFotoPerfil());
			st.executeUpdate();
			
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				artista.setIdArtista(rs.getLong("id_artista"));
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
		String sql = "UPDATE artista SET nome = ?, email = ?, senha = ?, about = ?, foto_de_perfil_url = ? WHERE id_artista =  ?;";
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
		String sql = "DELETE FROM artista WHERE id_artista = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, artista.getIdArtista());
			st.executeUpdate();
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
			st.setLong(1,id_artista);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				
				long idArtista = rs.getLong("id_artista");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String about = rs.getString("about");
				String fotoPerfil = rs.getString("foto_de_perfil_url");
				
				AlbumDAO aDAO = new AlbumDAO();
				artista.setIdArtista(idArtista);
				artista.setAbout(about);
				artista.setEmail(email);
				artista.setSenha(senha);
				artista.setNome(nome);
				artista.setFotoPerfil(fotoPerfil);
				artista.setAlbuns(aDAO.buscarListaAlbumPorIdArtista(idArtista));
				artista.setFans(buscarFans(idArtista));
				
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
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
			st.setString(1,email);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idArtista = rs.getLong("id_artista");
				String senha = rs.getString("senha");
				String nome = rs.getString("nome");
				String about = rs.getString("about");
				String fotoPerfil = rs.getString("foto_de_perfil_url");
				
				AlbumDAO aDAO = new AlbumDAO();
				artista.setIdArtista(idArtista);
				artista.setAbout(about);
				artista.setEmail(email);
				artista.setSenha(senha);
				artista.setNome(nome);
				artista.setFotoPerfil(fotoPerfil);
				artista.setAlbuns(aDAO.buscarListaAlbumPorIdArtista(idArtista));
				artista.setFans(buscarFans(idArtista));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return artista;
	}
	
	public List<Artista> buscarListaArtistaPorNome(String nome){
		conexao.abrirConexao();
		String sql = "SELECT * FROM artista WHERE nome LIKE ?;";
		List<Artista> artistas = new ArrayList<Artista>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1,"'%"+ nome + "%'");
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idArtista = rs.getLong("id_artista");
				String email = rs.getString("email");
				String senha = rs.getString("senha");
				String nome0 = rs.getString("nome");
				String about = rs.getString("about");
				String fotoPerfil = rs.getString("foto_de_perfil_url");
				
				AlbumDAO aDAO = new AlbumDAO();
				Artista artista = new Artista(nome0, email, senha, fotoPerfil, idArtista,about,
						aDAO.buscarListaAlbumPorIdArtista(idArtista), buscarFans(idArtista));
				artistas.add(artista);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return artistas;
	}
	
	public List<Artista> buscarListaArtistas(){
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
				String fotoPerfil = rs.getString("foto_de_perfil_url");
				
				AlbumDAO aDAO = new AlbumDAO();
				Artista artista = new Artista(nome, email, senha, fotoPerfil, idArtista,about,
						aDAO.buscarListaAlbumPorIdArtista(idArtista), buscarFans(idArtista));
				artistas.add(artista);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return artistas;
	}
	
	public List<Ouvinte> buscarFans(long id_artista){
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