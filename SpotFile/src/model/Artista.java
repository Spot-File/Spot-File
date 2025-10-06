package model;

import java.util.List;

public class Artista extends Usuario {
	private long idArtista;
	private String about; // about
	private List<Album> albuns;
	private List<Ouvinte> fans;
	// followers and following

	public Artista(String nome, String email, String senha, String fotoPerfil, long idArtista, String about,
			List<Album> albuns, List<Ouvinte> fans) {
		super(nome, email, senha, fotoPerfil);
		this.idArtista = idArtista;
		this.about = about;
		this.albuns = albuns;
		this.fans = fans;
	}

	public Artista() {
		// TODO Auto-generated constructor stub
	}

	// getters
	public long getIdArtista() {
		return idArtista;
	}

	public String getAbout() {
		return about;
	}

	public List<Album> getAlbuns() {
		return albuns;
	}

	public List<Ouvinte> getFans() {
		return fans;
	}

	// setters
	public void setIdArtista(long idArtista) {
		this.idArtista = idArtista;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public void setAlbuns(List<Album> albuns) {
		this.albuns = albuns;
	}

	public void setFans(List<Ouvinte> fans) {
		this.fans = fans;
	}

	
	
	
	
}
