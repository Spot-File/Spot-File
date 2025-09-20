package model;

public class Musica {
	private long idMusica;
	private String nome;
	private int duracaoMusica;
	private Album album;
		
	Musica(long idMusica, String nome, int duracaoMusica, Album album) {
		this.idMusica = idMusica;
		this.nome = nome;
		this.duracaoMusica = duracaoMusica;
		this.album = album;
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
	public Album getAlbum() {
		return album;
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
	public void setAlbum(Album album) {
		this.album = album;
	}

	
	
	
	
	
}
