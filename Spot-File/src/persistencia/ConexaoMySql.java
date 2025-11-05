package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySql {
	private final String IP = "localhost";
	private final String PORTA = "3306";
	private final String LOGIN = "root";
	private final String SENHA = "pepesani8";
	private final String NOME_BD = "bd_spot_file";
	private Connection conexao;
	
	public ConexaoMySql() {

	}
	
	public Connection getConexao () {
		return conexao;
	}
	

		public void abrirConexao() {
			
			String url = "jdbc:mysql://" + IP + ":" + PORTA + "/" + NOME_BD;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				this.conexao = DriverManager.getConnection(url, LOGIN, SENHA);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	
		public void fecharConexao() {
	
			try {
				if (conexao != null & !conexao.isClosed()) {
					conexao.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	
}
