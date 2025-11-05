
package model;

public abstract class Usuario {
	private String nome; 
	private String email;
	private String senha;
	private String fotoPerfil;
	
	
	Usuario(String nome, String email, String senha, String fotoPerfil) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.fotoPerfil = fotoPerfil;
	}


	public Usuario() {
		
	}



	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}
	
	public String getFotoPerfil() {
		return fotoPerfil;
	}


	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	
	
	

}
