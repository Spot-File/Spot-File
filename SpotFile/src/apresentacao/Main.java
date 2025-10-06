package apresentacao;

import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner; 


import model.*; 
import persistencia.*; 

public class Main {
	
	 private static final Scanner scanner = new Scanner (System.in); 
	 
	 //DAO'S 
	 private static  AlbumDAO albumDAO = new AlbumDAO(); 
	 private static  ArtistaDAO artistaDAO = new ArtistaDAO(); 
	 private static  OuvinteDAO ouvinteDAO = new OuvinteDAO(); 
	 private static  PlaylistDAO playlistAO = new PlaylistDAO(); 
	 private static  MusicaDAO musicaDAO = new MusicaDAO(); 
	 
	 // logins 
	 private static Artista artistaLogado = null; 
	 private static Ouvinte ouvinteLogado = null; 
	 
	 
	
	public static void main (String[] args ) { 
		
		int escolha = 5; 
		while(escolha !=0) {
            System.out.println("==== MINI SPOTIFY (console) ====");
            System.out.println("1) Sou ARTISTA");
            System.out.println("2) Sou OUVINTE");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");
             escolha = scanner.nextInt(); 
            
            switch(escolha) {
            case 1: 
            	escolhaArtista();
            	break; 
            case 2: 
            	escolhaOuvinte(); 
            	break; 
            case 0:
            	System.out.println("Tchau!"); 
            	break;
            default: 
            	System.out.println("Escolha invalida...Tente de novo."); 
            	break; 
            }
		}
		
		
	}
	
	public static void escolhaArtista() { 
		System.out.println("--ARTISTA--"); 
		System.out.println("1)Cadastrar (criar conta)"); 
		System.out.println("2)Entrar (já tem conta)"); 
		System.out.println("0)Voltar"); 
		int escolhaArtista = scanner.nextInt(); 
		
		switch(escolhaArtista) { 
		case 1: 
			cadastrarArtista();
			break; 
		case 2: 
			verificarArtista(); 
			break; 
		case 0: 
			 return; 
		}
		
		if(artistaLogado != null) { 
			menuArtista(); 
		}
		
	}
	
	public static void cadastrarArtista() { 
		//pede NOME
		System.out.println("Nome: "); 
		String nomeArtista = scanner.nextLine(); 
		//PEDE EMAIL
		System.out.println("Email: "); 
		String emailArtista = scanner.next();
		//CRIA AS SENHAS PARA FAZER A VERIFICAÇÃO
		String senhaArtista ="1"; 
		String senhaArtista2 = "2"; 
		do { 
			System.out.println("Senha: "); 
			 senhaArtista = scanner.next(); 
			System.out.println("Repita a senha: "); 
			 senhaArtista2 = scanner.next(); 
			
			if (senhaArtista.equals(senhaArtista2)) {
				System.out.println("Senha verificada com sucesso!"); 
			} else { 
				System.out.println("As duas senhas são diferentes. Tente novamente.");
			}
		} while (!senhaArtista.equals(senhaArtista2)); 
		// PEDE A URL DA FOTO DE PERFIL
		System.out.println("Insira URL da foto de perfil:"); 
		String URLFotoArtista = scanner.next(); 
		// PEDE ABOUT
		System.out.println("Inserir ABOUT:"); 
		String about = scanner.nextLine(); 
		// CRIA VAZIOS A LISTA DE FANS E DE ALBUNS PRA SEREM UPADOS DEPOIS
		List<Ouvinte> fans = new ArrayList();  
		List<Album> albuns = new ArrayList(); 
		
		//criando o NOVO ARTISTA CADASTRADO
		Artista a = new Artista (); 
		a.setNome(nomeArtista);
		a.setEmail(emailArtista);
		a.setSenha(senhaArtista);
		a.setFotoPerfil(URLFotoArtista);
		a.setAbout(about);
		a.setFans(fans);
		a.setAlbuns(albuns);
		
		//SALVANDO NO BANCO COM DAO *** ver id
		try {
			artistaDAO.salvar(a); 
			artistaLogado = a; 
			System.out.println("Cadastro Concluído! Seja bem vindo(a) " + artistaLogado.getNome()); 
		} catch (Exception e) {
			System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
		
	}
	
	public static void verificarArtista() {
		System.out.print("E-mail: ");
        String emailArtista = scanner.nextLine();
        System.out.print("Senha: ");
        String senhaArtista = scanner.nextLine();
        
       // Artista a = artistaDAO.buscarPorEMAIL!!! 
        
		
	}
	
	public static void menuArtista () { 
		System.out.println("========MENU ARTISTA========="); 
		System.out.println("1)CRIAR ÁLBUM");
		System.out.println("2)LISTAR ÁLBUNS - apenas");
		System.out.println("3)LISTAR ÁLBUNS & MÚSICAS");
		System.out.println("4)ATUALIZAR ÁLBUM");
		System.out.println("5)ATUALIZAR MÚSICA");
		System.out.println("6)ATUALIZAR PERFIL");
		System.out.println("7)DELETAR ÁLBUM");
		System.out.println("8)DELETAR MÚSICA");
		System.out.println("9)DELETAR CONTA");
		int escolhaMenuArtista = scanner.nextInt(); 
		
		switch(escolhaMenuArtista){ 
			case 1: //CRIAR ÁLBUM e UPAR MUSICAS
				
				//criando album
				//LISTA DE MUSICAS VAZIAS
				List<Musica> musicasAlbum = new ArrayList(); 
				//NOME DO ALBUM
				System.out.println("Criando o álbum!!Yay"); 
				System.out.println("Nome:");
				String nomeAlbum = scanner.nextLine(); 
				// CAPA 
				System.out.println("Foto da Capa (URL):");
				String URLcapaAlbum = scanner.nextLine();
				//ver...ANO DE LANÇAMENTO TIMESTAMP? 
				System.out.println("Ano de lançamento?");
				int anoDeLancamento = scanner.nextInt(); 
				//id artista 
				long idArtistaCriador = artistaLogado.getIdArtista(); 
				
				Album album = new Album(); 
				album.setNome(nomeAlbum); 
				album.setFotoDaCapaURL(URLcapaAlbum); 
				album.setMusicas(musicasAlbum); 
				album.setidArtista(idArtistaCriador);
				album.setAnoLancamento(anoDeLancamento); 
				
				try {
					albumDAO.salvar(album); 
					System.out.println("Álbum cadastrado com suceeso! Diga olá ao seu novo álbum,  " + album.getNome()); 
				} catch (Exception e) {
					System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
		        }
				
				
						
				//criando e upando musicas
				System.out.println("Criando as músicas!!Yay"); 
				int escolhaMusica = 5; 
				String nomeMusica; 
				int duracaoMusica; 
				long idAlbumDaMusica = album.getIdAlbum(); 
				do {
					System.out.println("Nome: "); 
					nomeMusica = scanner.nextLine(); 
					System.out.println("Duração da música (em segundos):");
					duracaoMusica = scanner.nextInt(); 
					
					Musica musica = new Musica(); 
					musica.setDuracaoMusica(duracaoMusica); 
					musica.setNome(nomeMusica); 
					musica.setIdAlbum(idAlbumDaMusica); 
					
					try {
						musicaDAO.salvar(musica); 
						System.out.println("Música cadastrada com suceeso! Diga oiê a sua nova música,  " + musica.getNome()); 
					} catch (Exception e) {
						System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
			        }
					
					musicasAlbum.add(musica);
					
					System.out.println("Upar nova música? Sim(1) / Não(0)"); 
						escolhaMusica =scanner.nextInt(); 
				}while (escolhaMusica != 0); 
				
				
				
				
				break; 
			case 2: //LISTAR ÁLBUNS - apenas
				break; 
			case 3: //LISTAR ÁLBUNS & MÚSICAS
				break; 
			case 4: //ATUALIZAR ÁLBUM
				break; 
			case 5: //ATUALIZAR MÚSICA
				break; 
			case 6: //ATUALIZAR PERFIL
				break; 
			case 7: //DELETAR ÁLBUM
				break; 
			case 8: //DELETAR MÚSICA
				break; 
			case 9: //DELETAR CONTA
				break; 
			default:
				
				break; 
		}
		
		
	}
	
	public static void escolhaOuvinte() { 
		System.out.println("--OUVINTE--"); 
		System.out.println("1)Cadastrar (criar conta)"); 
		System.out.println("2)Entrar (já tem conta)"); 
		System.out.println("0)Voltar"); 
		int escolhaOuvinte = scanner.nextInt(); 
		
		switch(escolhaOuvinte) { 
		case 1: 
			cadastrarOuvinte();
			break; 
		case 2: 
			verificarOuvinte(); 
			break; 
		case 0: 
			 return; 
		}
		
		if(ouvinteLogado != null) { 
			menuOuvinte(); 
		}
		
		
	}
	
	public static void cadastrarOuvinte(){ 
		System.out.println("Nome: "); 
		String nomeOuvinte = scanner.nextLine(); 
		//PEDE EMAIL
		System.out.println("Email: "); 
		String emailOuvinte = scanner.next();
		//CRIA AS SENHAS PARA FAZER A VERIFICAÇÃO
		String senhaOuvinte ="1"; 
		String senhaOuvinte2 = "2"; 
		do { 
			System.out.println("Senha: "); 
			senhaOuvinte = scanner.next(); 
			System.out.println("Repita a senha: "); 
			senhaOuvinte2 = scanner.next(); 
			
			if (senhaOuvinte.equals(senhaOuvinte2)) {
				System.out.println("Senha verificada com sucesso!"); 
			} else { 
				System.out.println("As duas senhas são diferentes. Tente novamente.");
			}
		} while (!senhaOuvinte.equals(senhaOuvinte2)); 
		// PEDE A URL DA FOTO DE PERFIL
		System.out.println("Insira URL da foto de perfil:"); 
		String URLFotoOuvinte = scanner.next(); 
		//CRIA A LISTA DE PLAYLISTS VAZIA
		List<Playlist> playlists = new ArrayList(); 
		//CRIA A LISTA DE SEGUIDORES VAZIA
		List<Ouvinte> seguidores = new ArrayList(); 
		//CRIA A PLAYLIST OBRIGATÓRIA
		Playlist playlistFavoritas = new Playlist(); 
		
		//CRIANDO O NOVO OUVINTE CADASTRADO
		Ouvinte o = new Ouvinte(); 
		o.setNome(nomeOuvinte);
		o.setEmail(emailOuvinte);
		o.setSenha(senhaOuvinte);
		o.setFotoPerfil(URLFotoOuvinte);
		o.setPlaylists(playlists);
		o.setFollowers(seguidores);
		o.setPlaylistFavoritas(playlistFavoritas);
		
		
		//SALVANDO NO DAO 
		try {
			ouvinteDAO.salvar(o); 
			ouvinteLogado = o; 
			System.out.println("Cadastro Concluído! Seja bem vindo(a) " + ouvinteLogado.getNome()); 
		} catch (Exception e) {
			System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
		
	}
		
		
	
	public static void verificarOuvinte () { 
		System.out.print("E-mail: ");
        String emailOuvinte = scanner.nextLine();
        System.out.print("Senha: ");
        String senhaOuvinte = scanner.nextLine();
        
        // Ouvinte o = ouvinteDAO.buscarPorEMAIL!!! 
        //verficar a senha 
		
	}
	

	public static void menuOuvinte() {
		
		
		
		
	}

}
