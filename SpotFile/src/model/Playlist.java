package model;

import java.util.ArrayList;

public class Playlist extends Colecao {
	private long idPlaylist;
	private String bio; 
	private Ouvinte criador;
	
	Playlist(ArrayList<Musica> musicas, String fotoDaCapaUrl, int tempoStreaming, String nome, long idPlaylist,
			String bio, Ouvinte criador) {
		super(musicas, fotoDaCapaUrl, tempoStreaming, nome);
		this.idPlaylist = idPlaylist;
		this.bio = bio;
		this.criador = criador;
	}
	
	public long getIdPlaylist() {
		return idPlaylist;
	}
	public String getBio() {
		return bio;
	}
	public Ouvinte getCriador() {
		return criador;
	}
	
	
	public void setIdPlaylist(long idPlaylist) {
		this.idPlaylist = idPlaylist;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public void setCriador(Ouvinte criador) {
		this.criador = criador;
	}
	
	
	
	
	
	
	
}
