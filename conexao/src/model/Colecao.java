package model;
import java.util.ArrayList;
import java.util.List;

public abstract class Colecao {
	private ArrayList<Musica> musicas; 
	private String FotoDaCapaURL; 
	private String nome; 
	//
	
	//getter
	public ArrayList<Musica> getMusicas() {
		return musicas;
	}
	
	public String getFotoDaCapaURL() {
		return FotoDaCapaURL;
	}
	
	public String getNome() {
		return nome;
	}
	
	//setters
	public void setMusicas(ArrayList<Musica> musicas) {
		this.musicas = musicas;
	}
	
	public void setFotoDaCapaURL(String fotoDaCapaURL) {
		FotoDaCapaURL = fotoDaCapaURL;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
