package model;
import java.util.ArrayList;

public class Ouvinte extends Usuario {
	
	private int idOuvinte; //conexão com o banco
	private Playlist playlistFavoritas; //playlist obrigatoria onde vai todas as "liked songs"
	private ArrayList<Playlist> playlists; 
	private ArrayList<Ouvinte> followers;
	
	Ouvinte(String nome, String email, String senha, String fotoPerfil, int idOuvinte, Playlist playlistFavoritas,
			ArrayList<Playlist> playlists, ArrayList<Ouvinte> followers) {
		super(nome, email, senha, fotoPerfil);
		this.idOuvinte = idOuvinte;
		this.playlistFavoritas = playlistFavoritas;
		this.playlists = playlists;
		this.followers = followers;
	}
	
	//getters
	public int getIdOuvinte() {
		return idOuvinte;
	}
	
	public Playlist getPlaylistFavoritas() {
		return playlistFavoritas;
	}
	public ArrayList<Playlist> getPlaylists() {
		return playlists;
	}
	public ArrayList<Ouvinte> getFollowers() {
		return followers;
	}
	
	
	//setters
	public void setIdOuvinte(int idOuvinte) {
		this.idOuvinte = idOuvinte;
	}
	
	public void setPlaylistFavoritas(Playlist playlistFavoritas) {
		this.playlistFavoritas = playlistFavoritas;
	}
	public void setPlaylists(ArrayList<Playlist> playlists) {
		this.playlists = playlists;
	}
	public void setFollowers(ArrayList<Ouvinte> followers) {
		this.followers = followers;
	} 
	
	
	
	
	
	
	
	

}
