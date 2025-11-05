package persistencia;

import model.Musica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MusicaDAO {
	private ConexaoMySql conexao;

	public MusicaDAO() {
		conexao = new ConexaoMySql();
	}


	public void salvar(Musica musica) {

		conexao.abrirConexao();

		String sql = "INSERT INTO musica (nome,duracao,id_album) VALUES(?,?,?);";
		try {
	
			PreparedStatement st = conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, musica.getNome());
			st.setInt(2, musica.getDuracaoMusica());
			
			st.setLong(3, musica.getIdAlbum());
		
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				musica.setIdMusica(rs.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}

	}


	public void editar(Musica musica) {
	
		conexao.abrirConexao();
	
		String sql = "UPDATE musica SET nome = ?, duracao = ? WHERE id_musica = ?;";
		try {

			PreparedStatement st = conexao.getConexao().prepareStatement(sql);

			st.setString(1, musica.getNome());
			st.setInt(2, musica.getDuracaoMusica());
			st.setLong(3, musica.getIdMusica());


			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			conexao.fecharConexao();
		}

	}


	public void excluirMusicaPorId(Musica musica) {
		conexao.abrirConexao();
		String sql = "DELETE FROM musica WHERE id_musica = ?;";
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, musica.getIdMusica());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
	}


	public Musica buscarPorId(long id_musica) {
		conexao.abrirConexao();
		String sql = "SELECT * FROM musica 	WHERE id_musica = ?";
		Musica musica = new Musica();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setLong(1, id_musica);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				long idMusica = rs.getLong("id_musica");
				String nome = rs.getString("nome");
				int duracao = rs.getInt("duracao");
				long idAlbum = rs.getLong("id_album");
				musica.setIdMusica(idMusica);
				musica.setNome(nome);
				musica.setDuracaoMusica(duracao);
				musica.setIdAlbum(idAlbum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexao.fecharConexao();
		}
		return musica;
	}

	public List<Musica> buscarListaMusicaPorNome(String nome){
		conexao.abrirConexao();
		String sql = "SELECT * FROM musica WHERE nome LIKE ?;";
		List<Musica> musicas = new ArrayList<Musica>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1,"%"+ nome + "%");
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idMusica = rs.getLong("id_musica");
				String nome0 = rs.getString("nome");
				int duracao = rs.getInt("duracao");
				long idAlbum = rs.getLong("id_album");
				Musica musica = new Musica(idMusica, nome0, duracao, idAlbum);
				musicas.add(musica);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return musicas;
	}
	
	public List<Musica> buscarListaMusicaPorNomeEIdAlbum(String nomeProcurado,long id_album){
		conexao.abrirConexao();
		String sql = "SELECT * FROM musica WHERE nome LIKE ? AND id_album = ?";
		List<Musica> musicas = new ArrayList<Musica>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1,"%"+ nomeProcurado + "%");
			st.setLong(2, id_album);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idMusica = rs.getLong("id_musica");
				String nome = rs.getString("nome");
				int duracao = rs.getInt("duracao");
				long idAlbum = rs.getLong("id_album");
				Musica musica = new Musica(idMusica, nome, duracao, idAlbum);
				musicas.add(musica);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return musicas;
	}
	
	public List<Musica> buscarListaMusicaPorNomeEIdPlaylist(String nomeProcurado,long id_playlist){
		conexao.abrirConexao();
		String sql = "SELECT * FROM salvo WHERE nome LIKE ? AND id_playlist = ?;";
		List<Musica> musicas = new ArrayList<Musica>();
		try {
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, "%" + nomeProcurado + "%");
			st.setLong(2,id_playlist);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				long idMusica = rs.getLong("id_musica");
				String nome = rs.getString("nome");
				int duracao = rs.getInt("duracao");
				long idAlbum = rs.getLong("id_album");
				Musica musica = new Musica(idMusica, nome, duracao, idAlbum);
				musicas.add(musica);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			conexao.fecharConexao();
		}
		return musicas;
	}
	
}