package persistencia;
import model.Artista;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ArtistaDAO {
private ConexaoMySql conexao;
	
	public ArtistaDAO() {
		conexao = new ConexaoMySql();
	}
	
	//SALVAR 
	public void salvar(Artista artista) {
		// abrir a conexao
		conexao.abrirConexao();
		// montar uma String de insert
		String sql = "INSERT INTO artista (email,senha,nome,about,foto_de_perfil_url) VALUES(?,?,?,?,?);";
		try {
			// preparar o insert para ser executado
			PreparedStatement st = conexao.getConexao().prepareStatement(sql);
			st.setString(1, artista.getEmail());
			st.setString(2, artista.getSenha());
			st.setString(3, artista.getNome());
			st.setString(4, artista.getAbout());			
			st.setString(5, artista.getFotoPerfil());
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

