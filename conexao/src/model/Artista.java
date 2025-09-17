package model;
import java.util.ArrayList;

public class Artista extends Usuario {
	private long idArtista;
	private String about;  //about
	private ArrayList<Album> albuns; 
	private ArrayList<Ouvinte> fans;
	//followers and following
	
	Artista(String nome, String email, String senha, String fotoPerfil, long idArtista, String about, 
			ArrayList<Album> albuns, ArrayList<Ouvinte> fans) {
		super(nome, email, senha, fotoPerfil);
		this.idArtista = idArtista;
		this.about = about;
		this.albuns = albuns;
		this.fans = fans;
	}

	
	
	//getters
	public long getIdArtista() {
		return idArtista;
	}
	
	public String getAbout() {
		return about;
	}
	
	
	public ArrayList<Album> getAlbuns() {
		return albuns;
	}
	
	public ArrayList<Ouvinte> getFans() {
		return fans;
	}
	
	
	//setters
	public void setIdArtista(long idArtista) {
		this.idArtista = idArtista;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	
	public void setAlbuns(ArrayList<Album> albuns) {
		this.albuns = albuns;
	}
	
	public void setFans(ArrayList<Ouvinte> fans) {
		this.fans = fans;
	}

	
		
	
	
	





}
