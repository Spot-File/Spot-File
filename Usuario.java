package model;

public abstract class Usuario {
	private String nome; 
	private String urlFoto;
	private String bio;
	
	
	//getters
	public String getNome() {
		return nome;
	}

	public String getUrlFoto() {
		return urlFoto;
	}
	
	public String getBio() {
		return bio;
	}
	
	//setters
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	} 
	
	

}
