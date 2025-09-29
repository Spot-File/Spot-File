package model;

import java.util.List;

public class Album extends Colecao {
	private long idAlbum;
	private int anoLancamento;
	private long idArtista;
	

	
	public Album(List<Musica> musicas, String fotoDaCapaUrl, int tempoStreaming, String nome, long idAlbum,
			int anoLancamento, long idArtista) {
		super(musicas, fotoDaCapaUrl, tempoStreaming, nome);
		this.idAlbum = idAlbum;
		this.anoLancamento = anoLancamento;
		this.idArtista = idArtista;
	}
	
	public Album() {
		super();
	}
	
	//getters
	public long getIdAlbum() {
		
		return idAlbum;
	}
	public int getAnoLancamento() {
		return anoLancamento;
	}
	public long getIdArtista() {
		return idArtista;
	}
	
	
	//setters
	public void setIdAlbum(long idAlbum) {
		this.idAlbum = idAlbum;
	}
	public void setAnoLancamento(int anoLancamento) {
		this.anoLancamento = anoLancamento;
	}
	public void setidArtista(long idArtista) {
		this.idArtista = idArtista;
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
