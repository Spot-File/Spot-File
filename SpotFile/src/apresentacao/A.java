package apresentacao;

import java.util.List;
import java.util.Scanner;

import model.*;
import persistencia.*;

public class A {
	
	
								/* TESTE PRINCIPAL */
	
	//testo se um objeto que crio, edito, leio e excluo está de fato entrando no bd. Daí retorno uma entrada.
	
	public static void main(String[]args) {
		int option = 0;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Menu");
			System.out.println("1-Ouvinte");
			System.out.println("2-Artista");
			System.out.println("3-Fechar");
			option = sc.nextInt();
			switch(option) {
			case 1:
				System.out.println("1-Criar");
				System.out.println("2-Deletar");
				System.out.println("3-Editar");
				System.out.println("4-Buscar");
				int optionOuvinte = sc.nextInt();
				switch(optionOuvinte) {
				case 1: 
					Ouvinte ouvinte = new Ouvinte();
					/*
					 * String nome, String email, String senha, String fotoPerfil, long idOuvinte, Playlist playlistFavoritas,
					List<Playlist> playlists, List<Ouvinte> followers
					 */
					System.out.println("NOME");
					ouvinte.setNome(sc.next());
					sc.nextLine();
					int email = 0;
					do {
						System.out.println("EMAIL");
						String email1 = sc.next();
						sc.nextLine();
						System.out.println("CONFIRMAR EMAIL");
						String email2 = sc.next();
						sc.nextLine();
						if(email1.equals(email2)) {
							email=1;
							ouvinte.setEmail(email1);
						}else {
							System.out.println("EMAILS DIFEREM TENTE NOVAMENTE");
							}
					}while(email==0);
					
					int senha = 0;
					do {
						System.out.println("SENHA");
						String senha1 = sc.next();
						sc.nextLine();
						System.out.println("CONFIRMAR SENHA");
						String senha2 = sc.next();
						sc.nextLine();
						if(senha2.equals(senha1)) {
							senha = 1;
							ouvinte.setSenha(senha1);
						}else{
								System.out.println("SENHAS DIFEREM, TENTE NOVAMENTE");		
						}
					}while(senha==0);
					
					System.out.println("URL FOTO DE PERFIL");
					ouvinte.setFotoPerfil(sc.next());
					sc.nextLine();
					
					OuvinteDAO oDAO = new OuvinteDAO();
					oDAO.salvar(ouvinte);
					
					;break;
				case 2:
					System.out.println("DIGITE O EMAIL");
					String emailDelete = sc.next();
					sc.nextLine();
					
					OuvinteDAO oDAODelete = new OuvinteDAO();
					oDAODelete.excluirPorEmail(emailDelete);
					;break;
					
				case 3:
					System.out.println("EMAIL");
					String emailBuscar = sc.next();
					sc.nextLine();
					int optionEditarOuvinte;
					do {
						OuvinteDAO oDAOEditar = new OuvinteDAO();
						Ouvinte ouvinteBusca = oDAOEditar.buscarOuvintePorEmail(emailBuscar);
						System.out.println("1- NOME:" +  ouvinteBusca.getNome());
						System.out.println("2- EMAIL:" +  ouvinteBusca.getEmail());
						System.out.println("3- SENHA:" +  ouvinteBusca.getSenha());
						System.out.println("4- URLFOTOPERFIL:" +  ouvinteBusca.getFotoPerfil());
						System.out.println("5- SAIR PARA O MENU");
						optionEditarOuvinte = sc.nextInt();
						
						switch(optionEditarOuvinte) {
						case 1:System.out.println("NOVO NOME");ouvinteBusca.setNome(sc.next());sc.nextLine();;break;
						case 2:System.out.println("NOVO EMAIL");ouvinteBusca.setEmail(sc.next());sc.nextLine();emailBuscar=ouvinteBusca.getEmail(); ;break;
						case 3:System.out.println("NOVA SENHA");ouvinteBusca.setSenha(sc.next());sc.nextLine(); ;break;
						case 4:System.out.println("NOVA FOTO");ouvinteBusca.setFotoPerfil(sc.next());sc.nextLine(); ;break;
						case 5:System.out.println("SAINDO");;break;
						default:System.out.println("Erro");;
						}
						
						oDAOEditar.editar(ouvinteBusca);

					}while(optionEditarOuvinte!=5);
					
					;break;
					
				case 4:
					System.out.println("DIGITE NOME");
					String nome = sc.next();
					sc.nextLine();
					
					OuvinteDAO oDAOBuscar = new OuvinteDAO();
					List<Ouvinte> ouvintesBusca = oDAOBuscar.buscarListaOuvintePorNome(nome);
					for(Ouvinte ouvinteP: ouvintesBusca) {
						System.out.println(ouvinteP.getNome());
						System.out.println();
					}
				}
				
				
				;break;
			case 2:
				
				
				;break;
			default:
				System.out.println("Erro");
				;
			}
		
		}while(option!=3);
		
	sc.close();
	}
	
	
	
	
	
	
	
	
	
	
	/*
	 * 		create
	 * artista 
	 * album
	 * musica
	 * ouvinte
	 * playlist
	 * 
	 */
	public static void criarArtista(String nome, String email, String senha, String fotoPerfil,String about) {
		ArtistaDAO aDAO = new ArtistaDAO();
		Artista artista = new Artista();
		artista.setNome(nome);
		artista.setEmail(email);
		artista.setSenha(senha);
		artista.setFotoPerfil(fotoPerfil);
		artista.setAbout(about);
		aDAO.salvar(artista);
	}
	
	public static void criarAlbum() {
		
	}
	
	public static void criarMusica() {
		
	}
	
	public static void criarOuvinte() {
		
	}
	
	public static void criarPlaylist() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	
	
	/*
	 * 	delete
	 * artista
	 * album
	 * musica
	 * ouvinte
	 * playlist
	 * 
	 */
	
	/*
	public static void deletarArtistaPorId() {
		ArtistaDAO aDAO = new ArtistaDAO();
		excluirArtistaPorId(this.artista);
	}
	*/
	
	
	public static void deletarArtistaPorEmail(String email) {
		ArtistaDAO aDAO = new ArtistaDAO();
		aDAO.excluirArtistaPorEmail(email);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	
	/*
	 * 	edit
	 * artista
	 * album
	 * musica
	 * ouvinte
	 * playlist
	 * 
	 */
	
	
	
	
	
	
	
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
	
	
	
	/*
	 * 	read
	 * artista
	 * album
	 * musica
	 * ouvinte
	 * playlist
	 * 
	 */
	
	
}
