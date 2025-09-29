package model;

import java.util.ArrayList;

public class Playlist extends Colecao {
	private long idPlaylist;
	private String bio; 
	private long idOuvinte;
	
	Playlist(ArrayList<Musica> musicas, String fotoDaCapaUrl, int tempoStreaming, String nome, long idPlaylist,
			String bio, long idOuvinte) {
		super(musicas, fotoDaCapaUrl, tempoStreaming, nome);
		this.idPlaylist = idPlaylist;
		this.bio = bio;
		this.idOuvinte = idOuvinte;
	}
	
	public Playlist() {

	}

	public long getIdPlaylist() {
		return idPlaylist;
	}
	public String getBio() {
		return bio;
	}
	public long getIdOuvinte() {
		return idOuvinte;
	}
	
	
	public void setIdPlaylist(long idPlaylist) {
		this.idPlaylist = idPlaylist;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public void setIdOuvinte(long idOuvinte) {
		this.idOuvinte = idOuvinte;
	}
	
	
	
	
	
	
	
}
