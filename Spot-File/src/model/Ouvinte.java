package model;
import java.util.List;

public class Ouvinte extends Usuario {
	
	private long idOuvinte; 
	private Playlist playlistFavoritas; 
	private List<Playlist> playlists; 
	private List<Ouvinte> followers;
	private List<Usuario> following;
	
	public Ouvinte(String nome, String email, String senha, String fotoPerfil, long idOuvinte, Playlist playlistFavoritas,
			List<Playlist> playlists, List<Ouvinte> followers, List<Usuario> following) {
		super(nome, email, senha, fotoPerfil);
		this.idOuvinte = idOuvinte;
		this.playlistFavoritas = playlistFavoritas;
		this.playlists = playlists;
		this.followers = followers;
		this.following = following;
	}
	
	public Ouvinte() {
		super();
	}


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
	
	public List<Usuario> getFollowing(){
		return following;
	}
	

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
	
	public void setFollowing(List<Usuario> following) {
		this.following = following;
	}


	
	
	

}