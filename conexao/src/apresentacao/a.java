package apresentacao;

import persistencia.ConexaoMySql;

public class a {
	
	public static void main (String[] args ) { 
		ConexaoMySql conexao = new ConexaoMySql(); 
		conexao.abrirConexao();
		
	}

}
