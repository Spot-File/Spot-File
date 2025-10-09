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
	 private static  PlaylistDAO playlistDAO = new PlaylistDAO(); 
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
            scanner.nextLine(); // consome o enter
            
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
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//artista 
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void escolhaArtista() { 
		System.out.println("--ARTISTA--"); 
		System.out.println("1)Cadastrar (criar conta)"); 
		System.out.println("2)Entrar (já tem conta)"); 
		System.out.println("0)Voltar"); 
		int escolhaArtista = scanner.nextInt(); 
		scanner.nextLine(); // consome o enter
		
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
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void cadastrarArtista() { 
		//pede NOME
		System.out.println("Nome: "); 
		String nomeArtista = scanner.nextLine(); 
		//PEDE EMAIL
		System.out.println("Email: "); 
		String emailArtista = scanner.nextLine();
		//CRIA AS SENHAS PARA FAZER A VERIFICAÇÃO
		String senhaArtista ="1"; 
		String senhaArtista2 = "2"; 
		do { 
			System.out.println("Senha: "); 
			 senhaArtista = scanner.nextLine(); 
			System.out.println("Repita a senha: "); 
			 senhaArtista2 = scanner.nextLine(); 
			
			if (senhaArtista.equals(senhaArtista2)) {
				System.out.println("Senha verificada com sucesso!"); 
			} else { 
				System.out.println("As duas senhas são diferentes. Tente novamente.");
			}
		} while (!senhaArtista.equals(senhaArtista2)); 
		// PEDE A URL DA FOTO DE PERFIL
		System.out.println("Insira URL da foto de perfil:"); 
		String URLFotoArtista = scanner.nextLine(); 
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
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void verificarArtista() {
		System.out.print("E-mail: ");
        String emailArtista = scanner.nextLine();
        System.out.print("Senha: ");
        String senhaLogin = scanner.nextLine();
        
        Artista artistaProcurado = artistaDAO.buscarArtistaPorEmail(emailArtista); 
        String senhaBD;
        System.out.println(artistaProcurado.getIdArtista());
        do { 		
			if (artistaProcurado.getEmail().equals(emailArtista)&&artistaProcurado.getSenha()!=null) {
				System.out.println("Artista encontrado! "); 
				 senhaBD = artistaProcurado.getSenha();
				  do { 	
					  	
						if (senhaLogin.equals(senhaBD)) {
							System.out.println("Senha verificada com sucesso!"); 
						} else { 
							System.out.println("As duas senhas são diferentes. Tente novamente.");
						}
					} while (!senhaLogin.equals(senhaBD)); 
			        	
			} else { 
				System.out.println("Não existe artista cadastrado com esse email. Verifique email e tente novamente.");
			}
		} while (artistaProcurado.getEmail().equals(null)); 
   
        artistaLogado = artistaProcurado; 
        
     
        
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
		scanner.nextLine(); // consome o enter
		
		switch(escolhaMenuArtista){ 
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
				scanner.nextLine(); // consome o enter
				//id artista 
				long idArtistaCriador = artistaLogado.getIdArtista(); 
				
				Album album = new Album(); 
				album.setNome(nomeAlbum); 
				album.setFotoDaCapaURL(URLcapaAlbum); 
				album.setMusicas(musicasAlbum); 
				album.setidArtista(idArtistaCriador);
				album.setAnoLancamento(anoDeLancamento); 
				
				//salva no bd
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
				int tempoStreaming = 0; //em segundos
				do {
					System.out.println("Nome: "); 
					nomeMusica = scanner.nextLine(); 
					System.out.println("Duração da música (em segundos):");
					duracaoMusica = scanner.nextInt(); 
					scanner.nextLine(); // consome o enter
					
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
					//transforma o tempo de streaming do album na soma das durações das musicas
					tempoStreaming +=  duracaoMusica; 
					
					
		
					System.out.println("Upar nova música? Sim(1) / Não(0)"); 
						escolhaMusica =scanner.nextInt(); 
						scanner.nextLine(); // consome o enter
				}while (escolhaMusica != 0); 
				//adciona o tempo de streaming feito aqui nas musicas upadas
				album.setTempoStreaming(tempoStreaming); 
				

				
				//adiciona álbum criado com as músicas upadas ja no artista. 
				artistaLogado.getAlbuns().add(album); 
				
				//volta para o menu
				menuArtista();
				
				
				break; 
				
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			case 2: //LISTAR ÁLBUNS - apenas
				//for que percorre todos os álbuns do artista
				for(int i =0; i< artistaLogado.getAlbuns().size(); i++) {
					System.out.println("=====SEUS ÁLBUNS=====");
					System.out.println(artistaLogado.getAlbuns().get(i).getNome());
					System.out.println(artistaLogado.getAlbuns().get(i).printarInfos());
					System.out.println(artistaLogado.getAlbuns().get(i).getAnoLancamento());
					//BUSCAS O NOME DO ARTISTA PELO ID NO BANCO
					System.out.println(artistaDAO.buscarPorIdArtista(artistaLogado.getAlbuns().get(i).getIdArtista()).getNome());		
				}
				
				//volta para o menu
				menuArtista();
				break; 
			case 3: //LISTAR ÁLBUNS & MÚSICAS
				//for que percorre todos os álbuns do artista
				for(int i =0; i< artistaLogado.getAlbuns().size(); i++) {
					System.out.println("=====SEUS ÁLBUNS=====");
					System.out.println(artistaLogado.getAlbuns().get(i).getNome());
					System.out.println(artistaLogado.getAlbuns().get(i).printarInfos());
					System.out.println(artistaLogado.getAlbuns().get(i).getAnoLancamento());
					//BUSCAS O NOME DO ARTISTA PELO ID NO BANCO
					System.out.println(artistaDAO.buscarPorIdArtista(artistaLogado.getAlbuns().get(i).getIdArtista()).getNome());
					//for que percorre todas as musicas dentro do album
					for(int j =0; j< artistaLogado.getAlbuns().get(i).getMusicas().size(); j++) {
						System.out.println(artistaLogado.getAlbuns().get(i).getMusicas().get(j).getNome());
						System.out.println(artistaLogado.getAlbuns().get(i).getMusicas().get(j).getDuracaoMusica());
						//PRINT DE NOVO O NOME DO ARTISTA PQ EH ASSIM NO SPOTIFY
						System.out.println(artistaDAO.buscarPorIdArtista(artistaLogado.getAlbuns().get(i).getIdArtista()).getNome());
						
					}
				}
				//volta para o menu
				menuArtista();
				break; 
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
			case 4: //ATUALIZAR ÁLBUM
				
					System.out.println("Qual o nome do seu álbum que deseja editar?"); 
					String nomeAlbumEditar = scanner.nextLine(); 
					//essa lista vai ter so um retorno ENTAO PODERIA SER SO ALBUM (MUDAR DAO) 
					List<Album> albunsPuxados = albumDAO.buscarListaAlbumPorNome(nomeAlbumEditar);
					if (albunsPuxados.get(0) == null) {
					    System.out.println("Nenhum álbum encontrado com esse nome.");
					    break; // VE SE EXISTE MESMO ALBUNS COM ESSE NOME
					}
					Album albumProcurado = albunsPuxados.get(0);
					int escolhaDeAtributo = 5; 
					//verificando se o artista é o artista que criou o álbum procurado
					
					if(albumProcurado.getIdArtista() == artistaLogado.getIdArtista()) {
						System.out.println("Você é o artista criador do álbum!"); 
						do { 
							System.out.println("O que desejas editar?"); 
							System.out.println("1)Nome");
							System.out.println("2)Capa do Álbum (inserir URL)");
							System.out.println("3)Ano de lançamento");
							System.out.println("0)Voltar"); 
							escolhaDeAtributo = scanner.nextInt(); 
							scanner.nextLine(); // consome o enter
							
							switch(escolhaDeAtributo) { 
							////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							case 1: //MUDANDO NOME 
								System.out.println("Antigo nome: " + albumProcurado.getNome());
								System.out.println("Insira o novo nome do álbum:"); 
								String novoNome = scanner.nextLine(); 
								albumProcurado.setNome(novoNome); 
								
								try {
									albumDAO.editar(albumProcurado); 
									
								} catch(Exception e) {
									System.out.println("Erro ao mudar nome do álbum: " + e.getMessage());
						        }

								break; 
								////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							case 2: //MUDANDO CAPA
								System.out.println("Insira o novo URL da capa do álbum:"); 
								
								String novaCapaURL = scanner.nextLine(); 
								albumProcurado.setFotoDaCapaURL(novaCapaURL); 
								
								try {
									albumDAO.editar(albumProcurado); 
									
								} catch(Exception e) {
									System.out.println("Erro ao mudar capa do álbum: " + e.getMessage());
						        }

								break; 
								////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							case 3: //MUDANDO ANO DE LANÇAMENTO 
								System.out.println("Antigo ano de lançamento: " + albumProcurado.getAnoLancamento());
								System.out.println("Insira o novo ano de lançamento:"); 
								int novoAnoDeLancamento = scanner.nextInt(); 
								scanner.nextLine(); 
								albumProcurado.setAnoLancamento(novoAnoDeLancamento); 
								
								try {
									albumDAO.editar(albumProcurado); 
									
								} catch(Exception e) {
									System.out.println("Erro ao mudar ano de lançamento do álbum: " + e.getMessage());
						        }
								break;
								////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							case 0: //VOLTAR PARA MENU
								System.out.println("Voltando...");
								
								break; 
								////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							default: 
								System.out.println("Escolha não existe. Tente de novo."); 
								
		
								break; 
							}
						}while (escolhaDeAtributo !=0);
							
					
					}else {
						System.out.println("Ops... parece que você não é o artista criador desse álbum, tente novamente");
					}
				
					
				
				//volta para o menu
				menuArtista();
				break; 
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			case 5: //ATUALIZAR MÚSICA
				System.out.println("Qual o nome da música que deseja editar?"); 
				String nomeMusicaEditar = scanner.nextLine();
				List<Musica> musicasPuxadas = musicaDAO.buscarListaMusicaPorNome(nomeMusicaEditar);
				if(musicasPuxadas.get(0) == null) {
				    System.out.println("Nenhuma música encontrada com esse nome.");
				    break; // VE SE EXISTE MESMO ALBUNS COM ESSE NOME
				}
				Musica musicaProcurada = musicasPuxadas.get(0);
				
				int escolhaDeAtributoMusica = 5; 
				Album albumDaMusica = albumDAO.buscarPorId(musicaProcurada.getIdAlbum());
				//verificando se o artista é o artista que criou a música procurada
				if (albumDaMusica.getIdArtista() == artistaLogado.getIdArtista()) {
					System.out.println("Você é o artista criador da música!"); 
					do { 
						System.out.println("O que desejas editar?"); 
						System.out.println("1)Nome");
						System.out.println("2)Álbum (ver)");
						System.out.println("3)Tempo de duração:");
						System.out.println("0)Voltar"); 
						escolhaDeAtributo = scanner.nextInt(); 
						scanner.nextLine(); // consome o enter
						
						switch(escolhaDeAtributo) { 
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						case 1: // MUDANDO NOME DA MÚSICA
							
							System.out.println("Antigo nome: " + musicaProcurada.getNome());
							System.out.println("Insira o novo nome da música: "); 
							String novoNome = scanner.nextLine(); 
							musicaProcurada.setNome(novoNome); 
							
							try {
								musicaDAO.editar(musicaProcurada); 
								
							} catch(Exception e) {
								System.out.println("Erro ao mudar nome da música: " + e.getMessage());
					        }
							
							break;
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						case 2: //MUDANDO ÁLBUM
							
							System.out.println("Antigo álbum: " + albumDaMusica.getNome());
							System.out.println("Insira o nome do novo álbum da música: (testar) "); 
							String novoNomeAlbum = scanner.nextLine(); 
							
							List<Album> albunsPuxadosParaMusica = albumDAO.buscarListaAlbumPorNome(novoNomeAlbum);
							if (albunsPuxadosParaMusica.get(0) == null) {
							    System.out.println("Nenhum álbum encontrado com esse nome.");
							    break; // VE SE EXISTE MESMO ALBUNS COM ESSE NOME
							}
							//pega o album
							Album albumProcuradoParaMusica = albunsPuxadosParaMusica.get(0);
							
							//verifica se esse album é do artista logado
							if(albumProcuradoParaMusica.getIdArtista() == artistaLogado.getIdArtista()) {
								System.out.println("Você é o artista criador do álbum!"); 
								//muda o id da musica para o id do album procurado
								musicaProcurada.setIdAlbum(albumProcuradoParaMusica.getIdAlbum());
							}else {
								System.out.println("Ops... parece que você não é o artista criador desse álbum, tente novamente");
							}
							
							
							
							try {
								musicaDAO.editar(musicaProcurada); 
								
							} catch(Exception e) {
								System.out.println("Erro ao mudar o tempo de duração: " + e.getMessage());
					        }
							break;
							
							
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						case 3: //MUDANDO TEMPO DE DURAÇÃO
							System.out.println("Antigo tempo de duracao: " + musicaProcurada.getDuracaoMusica());
							System.out.println("Insira o novo tempo de duração da música"); 
							int tempoDuracao = scanner.nextInt(); 
							scanner.nextLine(); 
							musicaProcurada.setDuracaoMusica(tempoDuracao);
							
							
							try {
								musicaDAO.editar(musicaProcurada); 
								
							} catch(Exception e) {
								System.out.println("Erro ao mudar o tempo de duração: " + e.getMessage());
					        }
							break;
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						case 0: //VOLTAR PARA MENU
							System.out.println("Voltando...");
							menuArtista(); 
							
							break; 
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						default: 
							System.out.println("Escolha não existe. Tente de novo."); 
							
	
							break; 
						}
						
						
					}while(escolhaDeAtributoMusica !=0);
				}else {
					System.out.println("Ops... parece que você não é o artista criador dessa música, tente novamente");
				}


				//volta para o menu
				menuArtista();
				break; 
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			case 6: //ATUALIZAR PERFIL
				int escolhaMudarPerfil = 5; 
				do {
					System.out.println("O que desejas mudar?");
					System.out.println("1)Nome:");
					System.out.println("2)Foto de Perfil:");
					System.out.println("3)About:");
					System.out.println("4)Email:");
					System.out.println("5)Senha:");
					System.out.println("0)Voltar:");
					escolhaMudarPerfil = scanner.nextInt();
					scanner.nextLine(); 
					
					switch (escolhaMudarPerfil) {
                         //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 1: //mudnando nome
                  
						System.out.println("Antigo nome: " + artistaLogado.getNome());
						System.out.println("Insira o novo nome: "); 
						String novoNome = scanner.nextLine();  
						
						artistaLogado.setNome(novoNome);
						
						
						try {
							artistaDAO.editar(artistaLogado); 
							
						} catch(Exception e) {
							System.out.println("Erro ao mudar o nome: " + e.getMessage());
				        }
						break;
	                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 2: //mudando foto de perfil
						System.out.println("Antigo foto de perfil: " + artistaLogado.getFotoPerfil());
						System.out.println("Insira nova URL de perfil: "); 
						String novaFoto = scanner.nextLine(); 
						
						artistaLogado.setFotoPerfil(novaFoto);
						
						
						try {
							artistaDAO.editar(artistaLogado); 
							
						} catch(Exception e) {
							System.out.println("Erro ao mudar a URL de perfil:  " + e.getMessage());
				        }
						
						break;
	                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 3: //mudano about
						System.out.println("Antigo about " + artistaLogado.getAbout());
						System.out.println("Insira o novo about: " ); 
						String novoAbout = scanner.nextLine(); 
						
						artistaLogado.setAbout(novoAbout);
						
						
						try {
							artistaDAO.editar(artistaLogado); 
							
						} catch(Exception e) {
							System.out.println("Erro ao mudar o about: " + e.getMessage());
				        }
						
						break;
	                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 4: //mudando email
						System.out.println("Antigo email " + artistaLogado.getEmail());
						
						String emailNovo ="1"; 
						String verificando = "2"; 
						do { 
							System.out.println("Email: "); 
							emailNovo = scanner.nextLine(); 
							System.out.println("Repita a email: "); 
							verificando = scanner.nextLine(); 
							
							if (emailNovo.equals(verificando)) {
								System.out.println("Email verificado com sucesso!"); 
							} else { 
								System.out.println("Os dois emails são diferentes. Tente novamente.");
							}
						} while (!emailNovo.equals(verificando)); 
						
						
						artistaLogado.setEmail(emailNovo); 
						
						
						try {
							artistaDAO.editar(artistaLogado); 
							
						} catch(Exception e) {
							System.out.println("Erro ao mudar o email: " + e.getMessage());
				        }
						break; 
	                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 5: //mudando senha
						System.out.println("Antiga senha " + artistaLogado.getSenha());
					
						String novaSenha ="1"; 
						String verificandoSenha = "2"; 
						do { 
							System.out.println("Insira no senha: "); 
							novaSenha = scanner.nextLine(); 
							System.out.println("Repita a senha: "); 
							verificandoSenha = scanner.nextLine(); 
							
							if (novaSenha.equals(verificandoSenha)) {
								System.out.println("Senha verificada com sucesso!"); 
							} else { 
								System.out.println("As duas senhas são diferentes. Tente novamente.");
							}
						} while (!novaSenha.equals(verificandoSenha));
						
						
						artistaLogado.setSenha(novaSenha); 
						
						
						try {
							artistaDAO.editar(artistaLogado); 
							
						} catch(Exception e) {
							System.out.println("Erro ao mudar o tempo de duração: " + e.getMessage());
				        }
						break; 
	                   
						//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 0: //VOLTAR PARA MENU
						System.out.println("Voltando...");
						menuArtista(); 
						
						break; 
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					default: 
						System.out.println("Escolha não existe. Tente de novo."); 
						

						break; 
					}
						
					}while(escolhaMudarPerfil !=0); 
				//volta para o menu
				menuArtista();
				
				break; 
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			case 7: //DELETAR ÁLBUM
				//volta para o menu
				menuArtista();
				break; 
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			case 8: //DELETAR MÚSICA
				//volta para o menu
				menuArtista();
				break; 
			case 9: //DELETAR CONTA
				//volta para o menu
				menuArtista();
				break; 
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			default:
				//volta para o menu
				menuArtista();
				
				break; 
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}
		
		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//ouvinte
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void escolhaOuvinte() { 
		System.out.println("--OUVINTE--"); 
		System.out.println("1)Cadastrar (criar conta)"); 
		System.out.println("2)Entrar (já tem conta)"); 
		System.out.println("0)Voltar"); 
		int escolhaOuvinte = scanner.nextInt(); 
		scanner.nextLine(); // consome o enter
		
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
		String emailOuvinte = scanner.nextLine();
		//CRIA AS SENHAS PARA FAZER A VERIFICAÇÃO
		String senhaOuvinte ="1"; 
		String senhaOuvinte2 = "2"; 
		do { 
			System.out.println("Senha: "); 
			senhaOuvinte = scanner.nextLine(); 
			System.out.println("Repita a senha: "); 
			senhaOuvinte2 = scanner.nextLine(); 
			
			if (senhaOuvinte.equals(senhaOuvinte2)) {
				System.out.println("Senha verificada com sucesso!"); 
			} else { 
				System.out.println("As duas senhas são diferentes. Tente novamente.");
			}
		} while (!senhaOuvinte.equals(senhaOuvinte2)); 
		// PEDE A URL DA FOTO DE PERFIL
		System.out.println("Insira URL da foto de perfil:"); 
		String URLFotoOuvinte = scanner.nextLine(); 
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
