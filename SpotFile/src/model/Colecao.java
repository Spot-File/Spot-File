package model;
import java.util.ArrayList;

public abstract class Colecao {
	
	private ArrayList<Musica> musicas; 
	private String fotoDaCapaUrl;
	private int tempoStreaming;
	private String nome;
	
		
	Colecao(ArrayList<Musica> musicas, String fotoDaCapaUrl, int tempoStreaming, String nome) {
		this.musicas = musicas;
		this.fotoDaCapaUrl = fotoDaCapaUrl;
		this.tempoStreaming = tempoStreaming;
		this.nome = nome;
	}

	//getters
	public ArrayList<Musica> getMusicas() {
		return musicas;
	}
	
	public String getFotoDaCapaURL() {
		return fotoDaCapaUrl;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getTempoStreaming() {
		return tempoStreaming;
	}

	//setters
	public void setMusicas(ArrayList<Musica> musicas) {
		this.musicas = musicas;
	}
	
	public void setFotoDaCapaURL(String fotoDaCapaURL) {
		fotoDaCapaUrl = fotoDaCapaURL;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTempoStreaming(int tempoStreaming) {
		this.tempoStreaming = tempoStreaming;
	}
	
	//métodos 
	
	public String printarInfos () {
		String printarInfos;
		int segs = tempoStreaming; 
		int mins = 0; 
		int horas = 0; 
		int nroMusicas = musicas.size(); 
		while (segs > 60) { 
			mins ++; 
			segs -= 60; 
		}
		while (mins > 60) {
			horas ++; 
			mins-=60;
		}
		if(horas>0) { 
			printarInfos = nroMusicas + " músicas, " + horas + " horas, " + mins + " minutos"; 
		} else if (mins>0) {
			printarInfos = nroMusicas + " músicas, " + mins + " minutos, " + segs + " segundos"; 
		} else if (mins==0 && horas ==0) {
			printarInfos = nroMusicas + " músicas, " + segs + " segundos";
		} else {
			printarInfos = nroMusicas + " músicas, " + segs + " segundos!"; //nao dar ponto de exclamação
		}
		
		return (printarInfos); 
		
		
	}
	
	
	
}
