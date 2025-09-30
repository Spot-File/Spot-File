package model;
import java.util.List;

public class Ouvinte extends Usuario {
	
	private long idOuvinte; //conexão com o banco
	private Playlist playlistFavoritas; //playlist obrigatoria onde vai todas as "liked songs"
	private List<Playlist> playlists; 
	private List<Ouvinte> followers;
	
	public Ouvinte(String nome, String email, String senha, String fotoPerfil, long idOuvinte, Playlist playlistFavoritas,
			List<Playlist> playlists, List<Ouvinte> followers) {
		super(nome, email, senha, fotoPerfil);
		this.idOuvinte = idOuvinte;
		this.playlistFavoritas = playlistFavoritas;
		this.playlists = playlists;
		this.followers = followers;
	}
	
	public Ouvinte() {
		super();
	}

	//getters
	public long getIdOuvinte() {
		return idOuvinte;
	}
	
	public Playlist getPlaylistFavoritas() {
		return playlistFavoritas;
	}
	public List<Playlist> getPlaylists() {
		return playlists;
	}
	public List<Ouvinte> getFollowers() {
		return followers;
	}
	
	
	//setters
	public void setIdOuvinte(long idOuvinte) {
		this.idOuvinte = idOuvinte;
	}
	
	public void setPlaylistFavoritas(Playlist playlistFavoritas) {
		this.playlistFavoritas = playlistFavoritas;
	}
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
	public void setFollowers(List<Ouvinte> followers) {
		this.followers = followers;
	} 
	
	
	
	
	
	
	
	

}
