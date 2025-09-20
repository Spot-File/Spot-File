package model;

import java.util.ArrayList;

public class Album extends Colecao {
	private long idAlbum;
	private int anoLancamento;
	private String tipo;
	private Artista criador;
	

	
	Album(ArrayList<Musica> musicas, String fotoDaCapaUrl, int tempoStreaming, String nome, long idAlbum,
			int anoLancamento, String tipo, Artista criador) {
		super(musicas, fotoDaCapaUrl, tempoStreaming, nome);
		this.idAlbum = idAlbum;
		this.anoLancamento = anoLancamento;
		this.tipo = tipo;
		this.criador = criador;
	}
	//getters
	public long getIdAlbum() {
		
		return idAlbum;
	}
	public int getAnoLancamento() {
		return anoLancamento;
	}
	public Artista getCriador() {
		return criador;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	//setters
	public void setIdAlbum(long idAlbum) {
		this.idAlbum = idAlbum;
	}
	public void setAnoLancamento(int anoLancamento) {
		this.anoLancamento = anoLancamento;
	}
	public void setCriador(Artista criador) {
		this.criador = criador;
	} 
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	//métodos 
	
	//overwrite 
	public String printarInfos () {
		String printarInfos;
		int segs = getTempoStreaming();
		int mins = 0; 
		int horas = 0; 
		int nroMusicas = getMusicas().size(); 
		while (segs > 60) { 
			mins ++; 
			segs -= 60; 
		}
		while (mins > 60) {
			horas ++; 
			mins-=60;
		}
		if(horas>0) { 
			printarInfos = " • " + anoLancamento + " • " + nroMusicas + " músicas, " + horas + " horas, " + mins + " minutos"; 
		} else if (mins>0) {
			printarInfos = " • " + anoLancamento + " • " + nroMusicas + " músicas, " + mins + " minutos, " + segs + " segundos"; 
		} else if (mins==0 && horas ==0) {
			printarInfos = " • " + anoLancamento + " • " + nroMusicas + " músicas, " + segs + " segundos";
		} else {
			printarInfos = " • " + anoLancamento + " • " + nroMusicas + " músicas, " + segs + " segundos!"; //nao dar ponto de exclamação
		}
		
		return (printarInfos); 
		
		
	}
	
	
	
	
	
	

}
