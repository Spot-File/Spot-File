package model;

public class Musica {
	private long idMusica;
	private String nome;
	private int duracaoMusica;
	private long idAlbum;

		
	public Musica(long idMusica, String nome, int duracaoMusica,long idAlbum) {
		this.idMusica = idMusica;
		this.nome = nome;
		this.duracaoMusica = duracaoMusica;
		this.idAlbum = idAlbum;
	}
	
	public Musica() {
		
	}
	
	//getters
	public long getIdMusica() {
		return idMusica;
	}
	public String getNome() {
		return nome;
	}
	public int getDuracaoMusica() {
		return duracaoMusica;
	}
	public long getIdAlbum() {
		return idAlbum;
	}
	
	//setters
	public void setIdMusica(long idMusica) {
		this.idMusica = idMusica;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setDuracaoMusica(int duracaoMusica) {
		this.duracaoMusica = duracaoMusica;
	}
	public void setIdAlbum(long idAlbum) {
		this.idAlbum = idAlbum;
	}

	
	
	
	
	
}
