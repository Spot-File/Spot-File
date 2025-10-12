package apresentacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;
import persistencia.*;

public class Main {

	private static final Scanner scanner = new Scanner(System.in);

	// DAO'S
	private static AlbumDAO albumDAO = new AlbumDAO();
	private static ArtistaDAO artistaDAO = new ArtistaDAO();
	private static OuvinteDAO ouvinteDAO = new OuvinteDAO();
	private static PlaylistDAO playlistDAO = new PlaylistDAO();
	private static MusicaDAO musicaDAO = new MusicaDAO();

	// logins
	private static Artista artistaLogado = null;
	private static Ouvinte ouvinteLogado = null;

	public static void main(String[] args) {

		int escolha = 5;
		while (escolha != 0) {
			System.out.println("====== SPOT-FILE ======");
			System.out.println("1) Sou ARTISTA");
			System.out.println("2) Sou OUVINTE");
			System.out.println("0) Sair");
			System.out.print("Escolha: ");
			escolha = scanner.nextInt();
			scanner.nextLine(); // consome o enter

			switch (escolha) {
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
	// artista
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void escolhaArtista() {
		System.out.println("\n=== SESSÃO ARTISTA ===");
		System.out.println("1)Cadastrar (criar conta)");
		System.out.println("2)Entrar (já tem conta)");
		System.out.println("0)Voltar");
		int escolhaArtista = scanner.nextInt();
		scanner.nextLine(); // consome o enter

		switch (escolhaArtista) {
		case 1:
			cadastrarArtista();
			break;
		case 2:
			verificarArtista();
			break;
		case 0:
			return;
		}

		if (artistaLogado != null) {
			menuArtista();
		}

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void cadastrarArtista() {
		// pede NOME
		System.out.println("\n==== CADASTRO DE ARTISTA ====");
		System.out.println("Nome: ");
		String nomeArtista = scanner.nextLine();
		// PEDE EMAIL
		System.out.println("Email: ");
		String emailArtista = scanner.nextLine();
		// CRIA AS SENHAS PARA FAZER A VERIFICAÇÃO
		String senhaArtista = "1";
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

		// criando o NOVO ARTISTA CADASTRADO
		Artista a = new Artista();
		a.setNome(nomeArtista);
		a.setEmail(emailArtista);
		a.setSenha(senhaArtista);
		a.setFotoPerfil(URLFotoArtista);
		a.setAbout(about);
		a.setFans(fans);
		a.setAlbuns(albuns);

		// SALVANDO NO BANCO COM DAO *** ver id
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
		System.out.println("\n==== LOGIN ARTISTA ====");
		System.out.print("E-mail: ");
		String emailArtista = scanner.nextLine();
		System.out.print("Senha: ");
		String senhaLogin = scanner.nextLine();

		Artista artistaProcurado = artistaDAO.buscarArtistaPorEmail(emailArtista);
		String senhaBD;
		do {
			if (artistaProcurado == null || artistaProcurado.getEmail() == null) {
				System.out.println("Não existe artista cadastrado com esse email. Verifique email e tente novamente.");
			} else {
				System.out.println("Artista encontrado! ");
				senhaBD = artistaProcurado.getSenha();
				do {

					if (senhaLogin.equals(senhaBD)) {
						System.out.println("Senha verificada com sucesso!");
					} else {
						System.out.println("As duas senhas são diferentes. Tente novamente.");
						System.out.print("Senha: ");
						senhaLogin = scanner.nextLine();
					}
				} while (!senhaLogin.equals(senhaBD));
			}
		} while (artistaProcurado.getEmail().equals(null));

		artistaLogado = artistaProcurado;

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void menuArtista() {
		System.out.println("\n======== SPOT-FILE ARTISTAS =========");
		System.out.println("\n O que você quer fazer?");
		System.out.println("1) Criar Álbum");
		System.out.println("2) Listar ÁLbuns");
		System.out.println("3) Listar Álbuns & Músicas");
		System.out.println("4) Atualizar Álbun");
		System.out.println("5) Atualizar Música");
		System.out.println("6) Atualizar Perfil");
		System.out.println("7) Deletar Álbum");
		System.out.println("8) Deletar Música");
		System.out.println("9) Deletar Conta");
		System.out.println("0) Sair");
		int escolhaMenuArtista = scanner.nextInt();
		scanner.nextLine(); // consome o enter

		switch (escolhaMenuArtista) {
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 1: // CRIAR ÁLBUM e UPAR MUSICAS

			// criando album
			// LISTA DE MUSICAS VAZIAS
			List<Musica> musicasAlbum = new ArrayList();
			// NOME DO ALBUM
			System.out.println("Criando o álbum!!Yay");
			System.out.println("Nome:");
			String nomeAlbum = scanner.nextLine();
			// CAPA
			System.out.println("Foto da Capa (URL):");
			String URLcapaAlbum = scanner.nextLine();
			// ver...ANO DE LANÇAMENTO TIMESTAMP?
			System.out.println("Ano de lançamento?");
			int anoDeLancamento = scanner.nextInt();
			scanner.nextLine(); // consome o enter
			// id artista
			long idArtistaCriador = artistaLogado.getIdArtista();

			Album album = new Album();
			album.setNome(nomeAlbum);
			album.setFotoDaCapaURL(URLcapaAlbum);
			album.setMusicas(musicasAlbum);
			album.setidArtista(idArtistaCriador);
			album.setAnoLancamento(anoDeLancamento);

			// salva no bd
			try {
				albumDAO.salvar(album);
				System.out.println("Álbum cadastrado com suceso! Diga olá ao seu novo álbum,  " + album.getNome());
			} catch (Exception e) {
				System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
			}

			// criando e upando musicas
			System.out.println("Criando as músicas!!Yay");
			int escolhaMusica = 5;
			String nomeMusica;
			int duracaoMusica;
			long idAlbumDaMusica = album.getIdAlbum();
			int tempoStreaming = 0; // em segundos
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
					System.out
							.println("Música cadastrada com suceeso! Diga oiê a sua nova música,  " + musica.getNome());
				} catch (Exception e) {
					System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
				}

				musicasAlbum.add(musica);
				// transforma o tempo de streaming do album na soma das durações das musicas
				tempoStreaming += duracaoMusica;

				System.out.println("Upar nova música? Sim(1) / Não(0)");
				escolhaMusica = scanner.nextInt();
				scanner.nextLine(); // consome o enter
			} while (escolhaMusica != 0);
			// adciona o tempo de streaming feito aqui nas musicas upadas
			album.setTempoStreaming(tempoStreaming);

			// adiciona álbum criado com as músicas upadas ja no artista.
			artistaLogado.getAlbuns().add(album);

			// volta para o menu
			menuArtista();

			break;

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 2: // LISTAR ÁLBUNS - apenas
			// quando não temos album o console não diz nada, só volta pro menu artista;
			// for que percorre todos os álbuns do artista
			for (int i = 0; i < artistaLogado.getAlbuns().size(); i++) {
				System.out.println("=====SEUS ÁLBUNS=====");
				System.out.println(artistaLogado.getAlbuns().get(i).getNome());
				System.out.println(artistaLogado.getAlbuns().get(i).printarInfos());
				System.out.println(artistaLogado.getAlbuns().get(i).getAnoLancamento());
				// BUSCAS O NOME DO ARTISTA PELO ID NO BANCO
				System.out.println(
						artistaDAO.buscarPorIdArtista(artistaLogado.getAlbuns().get(i).getIdArtista()).getNome());
			}

			// volta para o menu
			menuArtista();
			break;
		case 3: // LISTAR ÁLBUNS & MÚSICAS
			// for que percorre todos os álbuns do artista
			for (int i = 0; i < artistaLogado.getAlbuns().size(); i++) {
				System.out.println("=====SEUS ÁLBUNS=====");
				System.out.println(artistaLogado.getAlbuns().get(i).getNome());
				System.out.println(artistaLogado.getAlbuns().get(i).printarInfos());
				System.out.println(artistaLogado.getAlbuns().get(i).getAnoLancamento());
				// BUSCAS O NOME DO ARTISTA PELO ID NO BANCO
				System.out.println(
						artistaDAO.buscarPorIdArtista(artistaLogado.getAlbuns().get(i).getIdArtista()).getNome());
				// for que percorre todas as musicas dentro do album
				for (int j = 0; j < artistaLogado.getAlbuns().get(i).getMusicas().size(); j++) {
					System.out.println(artistaLogado.getAlbuns().get(i).getMusicas().get(j).getNome());
					System.out.println(artistaLogado.getAlbuns().get(i).getMusicas().get(j).getDuracaoMusica());
					// PRINT DE NOVO O NOME DO ARTISTA PQ EH ASSIM NO SPOTIFY
					System.out.println(
							artistaDAO.buscarPorIdArtista(artistaLogado.getAlbuns().get(i).getIdArtista()).getNome());

				}
			}
			// volta para o menu
			menuArtista();
			break;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		case 4: // ATUALIZAR ÁLBUM

			List<Album> albunsAtualizar = artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista());// busca
																												// os
																												// albuns
																												// do
																												// artista
																												// logado
			if (albunsAtualizar.isEmpty()) {
				System.out.println(
						"Você não tem nenhum Álbum.\nJá tá na hora de criar música não?...\nVoltando ao menu...");
				menuArtista();
				break;
			}
			System.out.println("\nSeus Álbuns:");

			for (int i = 0; i < albunsAtualizar.size(); i++) {
				Album albumListagem = albunsAtualizar.get(i);
				System.out.println(" " + (i + 1) + ". " + albumListagem.getNome());
			} // lista os nomes dos álbuns do artista

			System.out.println("Qual o nome do seu álbum que deseja editar?");
			String nomeAlbumEditar = scanner.nextLine();
			// essa lista vai ter so um retorno ENTAO PODERIA SER SO ALBUM (MUDAR DAO)
			List<Album> albunsPuxados = albumDAO.buscarListaAlbumPorNome(nomeAlbumEditar);
			if (albunsPuxados.get(0) == null) {
				System.out.println("Nenhum álbum encontrado com esse nome.");
				break; // VE SE EXISTE MESMO ALBUNS COM ESSE NOME
			}
			Album albumProcurado = albunsPuxados.get(0);
			int escolhaDeAtributo = 5;
			// verificando se o artista é o artista que criou o álbum procurado

			if (albumProcurado.getIdArtista() == artistaLogado.getIdArtista()) {
				System.out.println("Você é o artista criador do álbum!");
				do {
					System.out.println("O que desejas editar?");
					System.out.println("1)Nome");
					System.out.println("2)Capa do Álbum (inserir URL)");
					System.out.println("3)Ano de lançamento");
					System.out.println("0)Voltar");
					escolhaDeAtributo = scanner.nextInt();
					scanner.nextLine(); // consome o enter

					switch (escolhaDeAtributo) {
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 1: // MUDANDO NOME
						System.out.println("Antigo nome: " + albumProcurado.getNome());
						System.out.println("Insira o novo nome do álbum:");
						String novoNome = scanner.nextLine();
						albumProcurado.setNome(novoNome);

						try {
							albumDAO.editar(albumProcurado);

						} catch (Exception e) {
							System.out.println("Erro ao mudar nome do álbum: " + e.getMessage());
						}

						break;
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 2: // MUDANDO CAPA
						System.out.println("Insira o novo URL da capa do álbum:");

						String novaCapaURL = scanner.nextLine();
						albumProcurado.setFotoDaCapaURL(novaCapaURL);

						try {
							albumDAO.editar(albumProcurado);

						} catch (Exception e) {
							System.out.println("Erro ao mudar capa do álbum: " + e.getMessage());
						}

						break;
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 3: // MUDANDO ANO DE LANÇAMENTO
						System.out.println("Antigo ano de lançamento: " + albumProcurado.getAnoLancamento());
						System.out.println("Insira o novo ano de lançamento:");
						int novoAnoDeLancamento = scanner.nextInt();
						scanner.nextLine();
						albumProcurado.setAnoLancamento(novoAnoDeLancamento);

						try {
							albumDAO.editar(albumProcurado);

						} catch (Exception e) {
							System.out.println("Erro ao mudar ano de lançamento do álbum: " + e.getMessage());
						}
						break;
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 0: // VOLTAR PARA MENU
						System.out.println("Voltando...");

						break;
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					default:
						System.out.println("Escolha não existe. Tente de novo.");

						break;
					}
				} while (escolhaDeAtributo != 0);

			} else {
				System.out.println("Ops... parece que você não é o artista criador desse álbum, tente novamente");
			}

			// volta para o menu
			menuArtista();
			break;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 5: // ATUALIZAR MÚSICA
			System.out.println("Qual o nome da música que deseja editar?");
			String nomeMusicaEditar = scanner.nextLine();
			List<Musica> musicasPuxadas = musicaDAO.buscarListaMusicaPorNome(nomeMusicaEditar);
			if (musicasPuxadas.get(0) == null) {
				System.out.println("Nenhuma música encontrada com esse nome.");
				break; // VE SE EXISTE MESMO ALBUNS COM ESSE NOME
			}
			Musica musicaProcurada = musicasPuxadas.get(0);

			int escolhaDeAtributoMusica = 5;
			Album albumDaMusica = albumDAO.buscarPorId(musicaProcurada.getIdAlbum());
			// verificando se o artista é o artista que criou a música procurada
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

					switch (escolhaDeAtributo) {
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 1: // MUDANDO NOME DA MÚSICA

						System.out.println("Antigo nome: " + musicaProcurada.getNome());
						System.out.println("Insira o novo nome da música: ");
						String novoNome = scanner.nextLine();
						musicaProcurada.setNome(novoNome);

						try {
							musicaDAO.editar(musicaProcurada);

						} catch (Exception e) {
							System.out.println("Erro ao mudar nome da música: " + e.getMessage());
						}

						break;
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 2: // MUDANDO ÁLBUM

						System.out.println("Antigo álbum: " + albumDaMusica.getNome());
						System.out.println("Insira o nome do novo álbum da música: ");
						String novoNomeAlbum = scanner.nextLine();

						List<Album> albunsPuxadosParaMusica = albumDAO.buscarListaAlbumPorNome(novoNomeAlbum);
						if (albunsPuxadosParaMusica.get(0) == null) {
							System.out.println("Nenhum álbum encontrado com esse nome.");
							break; // VE SE EXISTE MESMO ALBUNS COM ESSE NOME
						}
						// pega o album
						Album albumProcuradoParaMusica = albunsPuxadosParaMusica.get(0);
						// muda o id da musica para o id do album procurado
						musicaProcurada.setIdAlbum(albumProcuradoParaMusica.getIdAlbum());

						try {
							musicaDAO.editar(musicaProcurada);

						} catch (Exception e) {
							System.out.println("Erro ao mudar o tempo de duração: " + e.getMessage());
						}
						break;

					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 3: // MUDANDO TEMPO DE DURAÇÃO
						System.out.println("Antigo tempo de duracao: " + musicaProcurada.getDuracaoMusica());
						System.out.println("Insira o novo tempo de duração da música");
						int tempoDuracao = scanner.nextInt();
						scanner.nextLine();
						musicaProcurada.setDuracaoMusica(tempoDuracao);

						try {
							musicaDAO.editar(musicaProcurada);

						} catch (Exception e) {
							System.out.println("Erro ao mudar o tempo de duração: " + e.getMessage());
						}
						break;
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					case 0: // VOLTAR PARA MENU
						System.out.println("Voltando...");

						break;
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					default:
						System.out.println("Escolha não existe. Tente de novo.");

						break;
					}

				} while (escolhaDeAtributoMusica != 0);
			} else {
				System.out.println("Ops... parece que você não é o artista criador dessa música, tente novamente");
			}

			// volta para o menu
			menuArtista();
			break;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 6: // ATUALIZAR PERFIL
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
				case 1: // mudnando nome

					System.out.println("Antigo nome: " + artistaLogado.getNome());
					System.out.println("Insira o novo nome: ");
					String novoNome = scanner.nextLine();

					artistaLogado.setNome(novoNome);

					try {
						artistaDAO.editar(artistaLogado);

					} catch (Exception e) {
						System.out.println("Erro ao mudar o nome: " + e.getMessage());
					}
					break;
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				case 2: // mudando foto de perfil
					System.out.println("Antigo foto de perfil: " + artistaLogado.getFotoPerfil());
					System.out.println("Insira nova URL de perfil: ");
					String novaFoto = scanner.nextLine();

					artistaLogado.setFotoPerfil(novaFoto);

					try {
						artistaDAO.editar(artistaLogado);

					} catch (Exception e) {
						System.out.println("Erro ao mudar a URL de perfil:  " + e.getMessage());
					}

					break;
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				case 3: // mudano about
					System.out.println("Antigo about " + artistaLogado.getAbout());
					System.out.println("Insira o novo about: ");
					String novoAbout = scanner.nextLine();

					artistaLogado.setAbout(novoAbout);

					try {
						artistaDAO.editar(artistaLogado);

					} catch (Exception e) {
						System.out.println("Erro ao mudar o about: " + e.getMessage());
					}

					break;
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				case 4: // mudando email
					System.out.println("Antigo email " + artistaLogado.getEmail());

					String emailNovo = "1";
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

					} catch (Exception e) {
						System.out.println("Erro ao mudar o email: " + e.getMessage());
					}
					break;
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				case 5: // mudando senha
					System.out.println("Antiga senha " + artistaLogado.getSenha());

					String novaSenha = "1";
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

					} catch (Exception e) {
						System.out.println("Erro ao mudar o tempo de duração: " + e.getMessage());
					}
					break;

				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				case 0: // VOLTAR PARA MENU
					System.out.println("Voltando...");
					menuArtista();

					break;
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				default:
					System.out.println("Escolha não existe. Tente de novo.");

					break;
				}

			} while (escolhaMudarPerfil != 0);
			// volta para o menu
			menuArtista();

			break;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 7: // DELETAR ÁLBUM
			System.out.println("=== EXCLUSÃO DE ÁLBUM ===");
			List<Album> deleteAlbuns = artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista());// busca os
																												// albuns
																												// do
																												// artista
																												// logado
			if (deleteAlbuns.isEmpty()) {
				System.out.println(
						"Você não tem nenhum Álbum.\nDifícil exlcuir um álbum se ainda não tem nada...\nVoltando ao menu...");
				menuArtista();
				break;
			}
			System.out.println("\nSeus Álbuns:");

			for (int i = 0; i < deleteAlbuns.size(); i++) {
				Album albumListagem = deleteAlbuns.get(i);
				System.out.println(" " + (i + 1) + ". " + albumListagem.getNome());
			} // lista os nomes dos álbuns do artista

			System.out.println("Insira o nome do álbum que deseja deletar ('C' para cancelar):");
			String nomeDelete = scanner.nextLine();
			nomeDelete.toUpperCase();

			if (nomeDelete.matches("C")) {
				System.out.println("Exclusão Cancelada.\n");
				menuArtista();
				break;
			} // Verifica se o usuario quer sair da exclusão de álbum

			List<Album> albunsArtistaDelete = albumDAO.buscarListaAlbumPorNome(nomeDelete); // procura o album com o
																							// nome digitado
			if (!albunsArtistaDelete.isEmpty()) {
				Album albumDeletar = albunsArtistaDelete.get(0);
				if (!(albumDeletar.getIdArtista() == artistaLogado.getIdArtista())) { //
					System.out.println("Não há nenhum álbum com esse nome! Cheque novamente.");
				} else {
					System.out.println("Tem certeza que deseja deleter o àlbum: " + albumDeletar.getNome() + "? (S/N)");
					String confirmacao = scanner.next();
					scanner.nextLine();
					confirmacao.toUpperCase();

					// testa os valores de confirmacao
					switch (confirmacao) {
					case "S":
						albumDAO.excluirAlbumPorId(albumDeletar.getIdAlbum());
						System.out.println("Álbum deletado com sucesso!\n");
						artistaLogado.setAlbuns(artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista()));
						;
						break;
					case "N":
						System.out.println("Exclusão Cancelada.\n");
						;
						break;
					default:
						System.out.println("Opção Inválida!");

					}
				}
			}
			menuArtista();
			break;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 8: // DELETAR MÚSICA
			System.out.println("=== EXCLUSÃO DE MÚSICAS ===");
			System.out.println("Aqui você escolhe um de seus álbuns\nLogo depois tira uma música por vez deles");

			List<Album> deleteMusicas = artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista());// busca
																												// os
																												// albuns
																												// do
																												// artista
																												// logado
			if (deleteMusicas.isEmpty()) {
				System.out.println(
						"Você não tem nenhum Álbum.\nAs vezes o artista só tem a música na cabeça dele...\nVoltando ao menu...");
				menuArtista();
				break;
			}
			System.out.println("\nSeus Álbuns:");

			for (int i = 0; i < deleteMusicas.size(); i++) {
				Album albumListagem = deleteMusicas.get(i);
				System.out.println(" " + (i + 1) + ". " + albumListagem.getNome());
			} // lista os nomes dos álbuns do artista

			System.out.println("Digite o nome de qual álbum deseja deletar as músicas('C' para cancelar):");// Escolha
																											// de album
			String nomeEscolhidoDelete = scanner.nextLine();
			nomeEscolhidoDelete.toUpperCase();

			if (nomeEscolhidoDelete.matches("C")) {// matches é para saber se é exatamente tal texto.
				System.out.println("Exclusão Cancelada.\n");
				menuArtista();
				break;
			} // Verifica se o usuario quer sair da exclusão de álbum

			List<Album> albunsEscolhidoDelete = albumDAO.buscarListaAlbumPorNome(nomeEscolhidoDelete); // busca no bd
			if (!albunsEscolhidoDelete.isEmpty()) {
				Album albumEscolhaDeletar = albunsEscolhidoDelete.get(0);
				if (!(albumEscolhaDeletar.getIdArtista() == artistaLogado.getIdArtista())) { //
					System.out.println("Não há nenhum álbum com esse nome! Cheque novamente.");
				} else {
					System.out.println("\n" + albumEscolhaDeletar.getNome());// nome do album escolhido

					for (int i = 0; i < albumEscolhaDeletar.getMusicas().size(); i++) {
						System.out.println(" " + (i + 1) + ". " + albumEscolhaDeletar.getMusicas().get(i).getNome());
					} // printa todas as musicas do album escolhido

					System.out.println("Digite o nome da música que deseja excluir:");
					String nomeMusicaDelete = scanner.nextLine();

					List<Musica> musicasDelete = musicaDAO.buscarListaMusicaPorNomeEIdAlbum(nomeMusicaDelete,
							albumEscolhaDeletar.getIdAlbum());
					if (!musicasDelete.isEmpty()) {
						Musica musicaDeletar = musicasDelete.get(0);
						System.out.println("Você tem certeza que deseja deletar a música: " + musicaDeletar.getNome()
								+ "\nDo álbum: " + albumEscolhaDeletar.getNome() + "(S/N)? ");
						String confirmacao = scanner.next();
						scanner.nextLine();
						confirmacao.toUpperCase();

						switch (confirmacao) {
						case "S":
							musicaDAO.excluirMusicaPorId(musicaDeletar);
							System.out.println("Música deletada com sucesso!\n");
							albumEscolhaDeletar
									.setMusicas(albumDAO.buscarListaMusicaPorIdAlbum(albumEscolhaDeletar.getIdAlbum()));
							artistaLogado
									.setAlbuns(artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista()));
							break;
						case "N":
							System.out.println("Exclusão Cancelada.\n");
							;
							break;
						default:
							System.out.println("Opção Inválida!");

						}
					} else {
						System.out.println("Essa Música não existe nesse Álbum\nCheque Novamente...");
					}

				}
			}
			System.out.println("\n");
			menuArtista();
			break;
		case 9: // DELETAR CONTA
			System.out.println("=== EXCLUSÃO DE CONTA ===");
			System.out.println(
					"Você está prestes a apagar sua conta\nCom ela, todas as suas preciosas músicas se perderão");
			System.out.println("Você tem certeza? (S/N)");
			if (scanner.nextLine().toUpperCase().matches("S")) {
				System.out.println("Certeza Mesmo? (S/N)");
				{
					String confirmacao = scanner.nextLine();
					confirmacao.toUpperCase();
					switch (confirmacao) {
					case "S":
						System.out.println(
								"Estamos apagando suas músicas... Foi bom enquanto durou, " + artistaLogado.getNome());
						artistaDAO.excluirArtistaPorId(artistaLogado);
						System.out.println("Voltando ao menu...");
						break;
					case "N":
						System.out.println("Ainda bem que perguntamos de novo :D\nVoltando ao Menu...");
						menuArtista();
						break;
					default:
						System.out.println("Opção Inválida");
						menuArtista();
					}

				}

			}
			break;
		case 0:
			System.out.println("Saindo...");
			artistaLogado = null;
			break;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		default:
			System.out.println("Opção Inválida. Tente Novamente.");
			menuArtista();
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}

	}

	
	// ouvinte
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void escolhaOuvinte() {
		System.out.println("--OUVINTE--");
		System.out.println("1)Cadastrar (criar conta)");
		System.out.println("2)Entrar (já tem conta)");
		System.out.println("0)Voltar");
		int escolhaOuvinte = scanner.nextInt();
		scanner.nextLine(); // consome o enter

		switch (escolhaOuvinte) {
		case 1:
			cadastrarOuvinte();
			break;
		case 2:
			verificarOuvinte();
			break;
		case 0:
			return;
		}

		if (ouvinteLogado != null) {
			menuOuvinte();
		}

	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void cadastrarOuvinte() {
		System.out.println("Nome: ");
		String nomeOuvinte = scanner.nextLine();
		// PEDE EMAIL
		System.out.println("Email: ");
		String emailOuvinte = scanner.nextLine();
		// CRIA AS SENHAS PARA FAZER A VERIFICAÇÃO
		String senhaOuvinte = "1";
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
		// CRIA A LISTA DE PLAYLISTS VAZIA
		List<Playlist> playlists = new ArrayList<Playlist>();
		// CRIA A LISTA DE SEGUIDORES VAZIA
		List<Ouvinte> seguidores = new ArrayList<Ouvinte>();
		// CRIA A PLAYLIST OBRIGATÓRIA
		Playlist playlistFavoritas = new Playlist();

		// CRIANDO O NOVO OUVINTE CADASTRADO
		Ouvinte o = new Ouvinte();
		o.setNome(nomeOuvinte);
		o.setEmail(emailOuvinte);
		o.setSenha(senhaOuvinte);
		o.setFotoPerfil(URLFotoOuvinte);
		o.setPlaylists(playlists);
		o.setFollowers(seguidores);
		o.setPlaylistFavoritas(playlistFavoritas);

		// SALVANDO NO DAO
		try {
			ouvinteDAO.salvar(o);
			ouvinteLogado = o;
			System.out.println("Cadastro Concluído! Seja bem vindo(a) " + ouvinteLogado.getNome());
		} catch (Exception e) {
			System.out.println("Erro ao cadastrar: " + e.getMessage());
		}

	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void verificarOuvinte() {
		
		//separa os dados para teste
		System.out.print("E-mail: ");
		String emailOuvinte = scanner.nextLine();
		System.out.print("Senha: ");
		String senhaLogin = scanner.nextLine();
		
		//busca registro no bd com email
		Ouvinte ouvinteProcurado = ouvinteDAO.buscarOuvintePorEmail(emailOuvinte);
		String senhaBD;
		do {
			//testa se objeto não é null ou se email não é null
			if (ouvinteProcurado == null || ouvinteProcurado.getEmail() == null) {
				System.out.println("Não existe ouvinte cadastrado com esse email. Verifique email e tente novamente.");
			} else {
				System.out.println("Ouvinte encontrado! Olá,  " + ouvinteProcurado.getNome());
				senhaBD = ouvinteProcurado.getSenha();
				do {
					//testa a senha
					if (senhaLogin.equals(senhaBD)) {
						System.out.println("Senha verificada com sucesso!");
					} else {
						System.out.println("As duas senhas são diferentes. Tente novamente.");
						System.out.print("Senha: ");
						senhaLogin = scanner.nextLine();
					}
				} while (!senhaLogin.equals(senhaBD));
			}
		} while (ouvinteProcurado.getEmail().equals(null));

		//loga o ouvinte
		ouvinteLogado = ouvinteProcurado;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void menuOuvinte() {
		System.out.println("======== SPOT-FILE OUVINTES =========");
		System.out.println(" O que deseja fazer?");
		// READ & UPDATE & DELETE meio junto
		System.out.println("1)Buscar artista"); //seguir (update) | mostrar albuns e músicas | salvar albuns e musicas
		System.out.println("2)Buscar ouvinte"); //seguir (update) | mostrar playlists e musicas salvas | mostrar artistas que segue e ouvintes que seguem e segue
		System.out.println("3)Buscar música");  //salvar (update) | mostrar album e artista
		System.out.println("4)Buscar álbum");   // salvar (vai criar nova playlist, ent eh mais um CREATE) | mostrar musicas e artista
		System.out.println("5)Criar playlist");
		System.out.println("6)Listar minhas playlists (Biblioteca)"); // mostrar musicas dentro de uma playlist escolhida + PLAYLIST FAVORITAS | atualizar playlist
		System.out.println("7)Listar seguidores"); // des-SEGUIR (DELETE) | mostrar ouvinte escolhido
		System.out.println("8)Listar seguindos");  // des-SEGUIR (DELETE) | mostrar ARTISTAS e OUVINTES escolhidos 
		System.out.println("9)Atualizar perfil");
		System.out.println("10)Atualizar playlist");
		System.out.println("11)Deletar playlist"); 
		System.out.println("12)Deletar perfil");
		System.out.println("0)Sair"); 
		int escolhaOuvinte = scanner.nextInt(); 
		scanner.nextLine(); 
		
		switch(escolhaOuvinte) {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 1 : //BUSCAR ARTISTA
			System.out.println("Insira o nome do artista procurado:"); 
			String nomeArtistaProcurado = scanner.nextLine(); 
			List<Artista> artistasPuxados = artistaDAO.buscarListaArtistaPorNome(nomeArtistaProcurado); 
			if (artistasPuxados.get(0) == null) {
				System.out.println("Nennhum artista encontrado com esse nome."); 
				break;
			}
			Artista artistaProcurado = artistasPuxados.get(0); 
			
			//printando about 
			System.out.println(artistaProcurado.getAbout()); 
		
			//PRINTANDO ÁLBUNS DE ACORDO COM O TOTAL DE ÁLBUNS CRIADOS DO ARTISTA SELECIONADO
			int escolhaAlbum; 
			//maior que 5
			if(artistaProcurado.getAlbuns().size()>5) {
				for(int i =0; i<5; i++) {
					System.out.println(i+1 + ")"); 
					System.out.println(artistaProcurado.getAlbuns().get(i).getNome()); 
					System.out.println(artistaProcurado.getAlbuns().get(i).printarInfos()); 
					System.out.println(artistaProcurado.getAlbuns().get(i).getAnoLancamento()); 
				}
				System.out.println("Mostrar todos os álbuns? S/N"); // show all
				String showAllAlbuns = scanner.nextLine(); 
				if(showAllAlbuns.equals("S")) {
					for (int i = 0; i < artistaProcurado.getAlbuns().size(); i++) {
						System.out.println(i+1 +")"); 
						System.out.println(artistaProcurado.getAlbuns().get(i).getNome()); 
						System.out.println(artistaProcurado.getAlbuns().get(i).printarInfos()); 
						System.out.println(artistaProcurado.getAlbuns().get(i).getAnoLancamento()); 
						}
					//escolhendo um álbum
					System.out.println("Escolha um álbum! Digite o número indicado: (Para voltar digite 0)"); 
					escolhaAlbum = scanner.nextInt(); 
					scanner.nextLine(); 
					if(escolhaAlbum ==0) {
						menuOuvinte(); 
					}
					//pritando álbum e suas musicas 
					for(int i = 0; i<artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().size() ; i++) {
						System.out.println(i+1 +")"); 
						System.out.println(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getNome()); 
						System.out.println(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getDuracaoMusica()); 
					}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
					System.out.println("Digite o número indicado para:"); 
					System.out.println("1)Salvar álbum"); 
					System.out.println("2)Salvar música em playlist"); 
					System.out.println("3)Seguir artista"); 
					System.out.println("0)Voltar"); 
					int escolhaDentroDeAlbum = scanner.nextInt(); 
					scanner.nextLine(); 
					
					
					
					switch(escolhaDentroDeAlbum) {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 1: //salvando album
						System.out.println("Salvando álbum, " + artistaProcurado.getAlbuns().get(escolhaAlbum-1).getNome()); 
						
						Playlist albumSalvo = new Playlist(); 
						
						List<Musica> musicasAlbumSalvo = new ArrayList(); 
						//pega quantidade de músicas
						for (int i = 0; i<artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().size(); i++) {
							
							Musica musica = new Musica(); 
							musica.setNome(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getNome()); 
							musica.setDuracaoMusica(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getDuracaoMusica()); 
							musica.setIdAlbum(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getIdAlbum()); 
							
							// salva no bd
							try {
								musicaDAO.salvar(musica);
							} catch (Exception e) {
								System.out.println("Erro ao salvar musica na playlist de album salvo: " + e.getMessage());
							}
							
							//salvando na lista vazia 
							musicasAlbumSalvo.add(musica); 
							
						
						}
						albumSalvo.setNome(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getNome());
						albumSalvo.setFotoDaCapaURL(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getFotoDaCapaURL()); 
						albumSalvo.setTempoStreaming(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getTempoStreaming()); 
						albumSalvo.setIdOuvinte(ouvinteLogado.getIdOuvinte()); 
						albumSalvo.setMusicas(musicasAlbumSalvo);
						// salva no bd
						try {
							playlistDAO.salvar(albumSalvo);
							System.out.println("Álbum salvo com sucesso!");
						} catch (Exception e) {
							System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
						}
						
						ouvinteLogado.getPlaylists().add(albumSalvo); 
						
						
						menuOuvinte();
						break; 
					case 2: //salvando musica em playlist
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						Playlist playlistProcurada = null; 
						//escolhendo música
						
						int escolhaMusica; 
						System.out.println("Escolha uma música! Digite o número indicado: (Para voltar digite 0)"); 
						escolhaMusica = scanner.nextInt(); 
						Musica musicaEscolhida = artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(escolhaMusica-1); 
						scanner.nextLine(); 
						if(escolhaMusica ==0) {
							menuOuvinte(); 
						}
						//escolhendo playlist 
						
						int escolhaPlaylist; 
						System.out.println("Indique a playlist que quer salvar música:"); 
						System.out.println("Digite o número indicado para:"); 
						System.out.println("1)Buscar playlist pelo nome:"); 
						System.out.println("2)Listar nomes playlist:"); 
						System.out.println("3)Criar nova playlist: "); 
						int indicacaoDeBusca = scanner.nextInt(); 
						scanner.nextLine(); 
						switch(indicacaoDeBusca) {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						case 1: //buscar playlist
							System.out.println("Qual o nome da playlist que você quer salvar?"); 
							String nomePlaylist = scanner.nextLine(); 
							List<Playlist> playlistPuxadas = playlistDAO.buscarListaPlaylistPorNome(nomePlaylist); 
							if (playlistPuxadas.get(0) == null) {
								System.out.println("Nenhuma playlist encontrada com esse nome."); 
								break; 
							}
							 playlistProcurada = playlistPuxadas.get(0); 
							
							
							break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						case 2: //listar playlists
							
							if(ouvinteLogado.getPlaylists().size() !=0 && ouvinteLogado.getPlaylists().size() >5 ) {
								for(int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
									System.out.println(i+1 + ")"); 
									System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
								}
								System.out.println("Mostrar todos os álbuns? S/N"); // show all
								String showAllPlaylist = scanner.nextLine(); 
								if(showAllPlaylist.equals("S")) {
									for (int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
										System.out.println(i+1 + ")"); 
										System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
									}
								}			
					} else if (ouvinteLogado.getPlaylists().size() ==5) {
						for(int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
							System.out.println(i+1 + ")"); 
							System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
						}
					}else {
						for (int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
							System.out.println(i+1 + ")"); 
							System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
						}
						
					}
							//escolhendo a playlist 
							System.out.println("Escolha a playlist! Digite o número indicado: (Para playlist de favoritas digite 0)"); 
							escolhaPlaylist = scanner.nextInt(); 
							scanner.nextLine(); 
							if(escolhaPlaylist ==0) {
								playlistProcurada = ouvinteLogado.getPlaylistFavoritas(); 	 
							} else {
								playlistProcurada = ouvinteLogado.getPlaylists().get(escolhaPlaylist-1);
							}
							
							
							
							break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

							
						case 3: // criar nova playlist
							Playlist playlist = new Playlist(); 
							playlist.setNome(musicaEscolhida.getNome()); 
							List<Musica> musicaPlaylist = new ArrayList(); 
							musicaPlaylist.add(musicaEscolhida); 
							playlist.setMusicas(musicaPlaylist); 
							playlist.setIdOuvinte(ouvinteLogado.getIdOuvinte()); 
							playlist.setTempoStreaming(musicaEscolhida.getDuracaoMusica()); 
							playlist.setFotoDaCapaURL(albumDAO.buscarPorId(musicaEscolhida.getIdAlbum()).getFotoDaCapaURL()); 
							System.out.println("Música salva com sucesso!"); 
							break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						default:
							System.out.println("Escolha inválida. Voltando...");
							menuOuvinte(); 
						}
						
						
						//salvando musica na playlist escolhida 
						
						if(indicacaoDeBusca != 3) {
							System.out.println("Salvando música, " + musicaEscolhida.getNome() + ", na playlist " + playlistProcurada.getNome()); 
							playlistProcurada.getMusicas().add(musicaEscolhida); 
						}
						menuOuvinte();
						break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 3: //seguir artista
						System.out.println("Seguindo artista, " + artistaProcurado.getNome()); 
						artistaProcurado.getFans().add(ouvinteLogado); 
						
						// COLOCAR ARTISTA NA LISTA DE SEGUINDOS DO OUVINTE!!!!!!!!!!!!!!!!!!!
						
						System.out.println("Artista seguido com sucesso!"); 
						menuOuvinte();
						break; 
					case 0: // voltar ao menu
						System.out.println("Voltando ao menu... "); 
						menuOuvinte(); 
						break; 
					default: 
						System.out.println("Escolha inválida! Voltando ao menu... "); 
						menuOuvinte(); 
						break; 
						
					}
					
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
				} else {
					System.out.println("Não mostrar mais."); 
					//escolhendo um álbum
					System.out.println("Escolha um álbum! Digite o número indicado: (Para voltar digite 0)"); 
					escolhaAlbum = scanner.nextInt(); 
					scanner.nextLine(); 
					if(escolhaAlbum ==0) {
						menuOuvinte(); 
					}
					//pritando álbum e suas musicas 
					for(int i = 0; i<artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().size() ; i++) {
						System.out.println(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getNome()); 
						System.out.println(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getDuracaoMusica()); 
					}
				
				
				System.out.println("Digite o número indicado para:"); 
				System.out.println("1)Salvar álbum"); 
				System.out.println("2)Salvar música em playlist"); 
				System.out.println("3)Seguir artista"); 
				System.out.println("0)Voltar"); 
				int escolhaDentroDeAlbum = scanner.nextInt(); 
				scanner.nextLine(); 
				
				
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				switch(escolhaDentroDeAlbum) {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 1: //salvando album
					System.out.println("Salvando álbum, " + artistaProcurado.getAlbuns().get(escolhaAlbum-1).getNome()); 
					
					Playlist albumSalvo = new Playlist(); 
					
					List<Musica> musicasAlbumSalvo = new ArrayList(); 
					//pega quantidade de músicas
					for (int i = 0; i<artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().size(); i++) {
						
						Musica musica = new Musica(); 
						musica.setNome(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getNome()); 
						musica.setDuracaoMusica(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getDuracaoMusica()); 
						musica.setIdAlbum(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getIdAlbum()); 
						
						// salva no bd
						try {
							musicaDAO.salvar(musica);
						} catch (Exception e) {
							System.out.println("Erro ao salvar musica na playlist de album salvo: " + e.getMessage());
						}
						
						//salvando na lista vazia 
						musicasAlbumSalvo.add(musica); 
						
					
					}
					albumSalvo.setNome(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getNome());
					albumSalvo.setFotoDaCapaURL(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getFotoDaCapaURL()); 
					albumSalvo.setTempoStreaming(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getTempoStreaming()); 
					albumSalvo.setIdOuvinte(ouvinteLogado.getIdOuvinte()); 
					albumSalvo.setMusicas(musicasAlbumSalvo);
					// salva no bd
					try {
						playlistDAO.salvar(albumSalvo);
						System.out.println("Álbum salvo com sucesso!");
					} catch (Exception e) {
						System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
					}
					
					ouvinteLogado.getPlaylists().add(albumSalvo); 
					
					
					menuOuvinte();
					break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 2: //salvando musica em playlist
					Playlist playlistProcurada = null; 
					//escolhendo música
					
					int escolhaMusica; 
					System.out.println("Escolha uma música! Digite o número indicado: (Para voltar digite 0)"); 
					escolhaMusica = scanner.nextInt(); 
					Musica musicaEscolhida = artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(escolhaMusica-1); 
					scanner.nextLine(); 
					if(escolhaMusica ==0) {
						menuOuvinte(); 
					}
					//escolhendo playlist 
					
					int escolhaPlaylist; 
					System.out.println("Indique a playlist que quer salvar música:"); 
					System.out.println("Digite o número indicado para:"); 
					System.out.println("1)Buscar playlist pelo nome:"); 
					System.out.println("2)Listar nomes playlist:"); 
					System.out.println("3)Criar nova playlist: "); 
					int indicacaoDeBusca = scanner.nextInt(); 
					scanner.nextLine(); 
					switch(indicacaoDeBusca) {
					case 1: //buscar playlist
						System.out.println("Qual o nome da playlist que você quer salvar?"); 
						String nomePlaylist = scanner.nextLine(); 
						List<Playlist> playlistPuxadas = playlistDAO.buscarListaPlaylistPorNome(nomePlaylist); 
						if (playlistPuxadas.get(0) == null) {
							System.out.println("Nenhuma playlist encontrada com esse nome."); 
							break; 
						}
						 playlistProcurada = playlistPuxadas.get(0); 
						
						
						break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 2: //listar playlists
	
						if(ouvinteLogado.getPlaylists().size() !=0 && ouvinteLogado.getPlaylists().size() >5 ) {
							for(int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
								System.out.println(i+1 + ")"); 
								System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
							}
							System.out.println("Mostrar todos os álbuns? S/N"); // show all
							String showAllPlaylist = scanner.nextLine(); 
							if(showAllPlaylist.equals("S")) {
								for (int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
									System.out.println(i+1 + ")"); 
									System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
								}
							}			
				} else if (ouvinteLogado.getPlaylists().size() ==5) {
					for(int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
						System.out.println(i+1 + ")"); 
						System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
					}
				}else {
					for (int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
						System.out.println(i+1 + ")"); 
						System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
					}
					
				}
						//escolhendo a playlist 
						System.out.println("Escolha a playlist! Digite o número indicado: (Para playlist de favoritas digite 0)"); 
						escolhaPlaylist = scanner.nextInt(); 
						scanner.nextLine(); 
						if(escolhaPlaylist ==0) {
							playlistProcurada = ouvinteLogado.getPlaylistFavoritas(); 	 
						} else {
							playlistProcurada = ouvinteLogado.getPlaylists().get(escolhaPlaylist-1);
						}
						
						
						
						break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						
					case 3: // criar nova playlist
						Playlist playlist = new Playlist(); 
						playlist.setNome(musicaEscolhida.getNome()); 
						List<Musica> musicaPlaylist = new ArrayList(); 
						musicaPlaylist.add(musicaEscolhida); 
						playlist.setMusicas(musicaPlaylist); 
						playlist.setIdOuvinte(ouvinteLogado.getIdOuvinte()); 
						playlist.setTempoStreaming(musicaEscolhida.getDuracaoMusica()); 
						playlist.setFotoDaCapaURL(albumDAO.buscarPorId(musicaEscolhida.getIdAlbum()).getFotoDaCapaURL()); 
						System.out.println("Música salva com sucesso!"); 
						break; 
					default:
						System.out.println("Escolha inválida. Voltando...");
						menuOuvinte(); 
					}
					
					//salvando musica na playlist escolhida 
					
					if(indicacaoDeBusca != 3) {
						System.out.println("Salvando música, " + musicaEscolhida.getNome() + ", na playlist " + playlistProcurada.getNome()); 
						playlistProcurada.getMusicas().add(musicaEscolhida); 
					}
					menuOuvinte();
					break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 3: //seguir artista
					System.out.println("Seguindo artista, " + artistaProcurado.getNome()); 
					artistaProcurado.getFans().add(ouvinteLogado); 
					
					// COLOCAR ARTISTA NA LISTA DE SEGUINDOS DO OUVINTE!!!!!!!!!!!!!!!!!!!
					
					System.out.println("Artista seguido com sucesso!"); 
					menuOuvinte();
					break; 
				case 0: // voltar ao menu
					System.out.println("Voltando ao menu... "); 
					menuOuvinte(); 
					break; 
				default: 
					System.out.println("Escolha inválida! Voltando ao menu... "); 
					menuOuvinte(); 
					break; 
					
				}
				
			}
			
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//igual a 5
			} else if (artistaProcurado.getAlbuns().size()==5) {
				for(int i =0; i<5; i++) {
					System.out.println(i+1 + ")"); 
					System.out.println(artistaProcurado.getAlbuns().get(i).getNome()); 
					System.out.println(artistaProcurado.getAlbuns().get(i).printarInfos()); 
					System.out.println(artistaProcurado.getAlbuns().get(i).getAnoLancamento());
				}
				//escolhendo um álbum
				System.out.println("Escolha um álbum! Digite o número indicado: (Para voltar digite 0)"); 
				escolhaAlbum = scanner.nextInt(); 
				scanner.nextLine(); 
				if(escolhaAlbum ==0) {
					menuOuvinte(); 
				}
				//pritando álbum e suas musicas 
				for(int i = 0; i<artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().size() ; i++) {
					System.out.println(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getNome()); 
					System.out.println(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getDuracaoMusica()); 
				}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				System.out.println("Digite o número indicado para:"); 
				System.out.println("1)Salvar álbum"); 
				System.out.println("2)Salvar música em playlist"); 
				System.out.println("3)Seguir artista"); 
				System.out.println("0)Voltar"); 
				int escolhaDentroDeAlbum = scanner.nextInt(); 
				scanner.nextLine(); 
				
				
				
				switch(escolhaDentroDeAlbum) {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 1: //salvando album
					System.out.println("Salvando álbum, " + artistaProcurado.getAlbuns().get(escolhaAlbum-1).getNome()); 
					
					Playlist albumSalvo = new Playlist(); 
					
					List<Musica> musicasAlbumSalvo = new ArrayList(); 
					//pega quantidade de músicas
					for (int i = 0; i<artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().size(); i++) {
						
						Musica musica = new Musica(); 
						musica.setNome(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getNome()); 
						musica.setDuracaoMusica(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getDuracaoMusica()); 
						musica.setIdAlbum(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getIdAlbum()); 
						
						// salva no bd
						try {
							musicaDAO.salvar(musica);
						} catch (Exception e) {
							System.out.println("Erro ao salvar musica na playlist de album salvo: " + e.getMessage());
						}
						
						//salvando na lista vazia 
						musicasAlbumSalvo.add(musica); 
						
					
					}
					albumSalvo.setNome(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getNome());
					albumSalvo.setFotoDaCapaURL(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getFotoDaCapaURL()); 
					albumSalvo.setTempoStreaming(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getTempoStreaming()); 
					albumSalvo.setIdOuvinte(ouvinteLogado.getIdOuvinte()); 
					albumSalvo.setMusicas(musicasAlbumSalvo);
					// salva no bd
					try {
						playlistDAO.salvar(albumSalvo);
						System.out.println("Álbum salvo com sucesso!");
					} catch (Exception e) {
						System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
					}
					
					ouvinteLogado.getPlaylists().add(albumSalvo); 
					
					
					menuOuvinte();
					break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 2: //salvando musica em playlist
					Playlist playlistProcurada = null; 
					//escolhendo música
					
					int escolhaMusica; 
					System.out.println("Escolha uma música! Digite o número indicado: (Para voltar digite 0)"); 
					escolhaMusica = scanner.nextInt(); 
					Musica musicaEscolhida = artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(escolhaMusica-1); 
					scanner.nextLine(); 
					if(escolhaMusica ==0) {
						menuOuvinte(); 
					}
					//escolhendo playlist 
					
					int escolhaPlaylist; 
					System.out.println("Indique a playlist que quer salvar música:"); 
					System.out.println("Digite o número indicado para:"); 
					System.out.println("1)Buscar playlist pelo nome:"); 
					System.out.println("2)Listar nomes playlist:"); 
					System.out.println("3)Criar nova playlist: "); 
					int indicacaoDeBusca = scanner.nextInt(); 
					scanner.nextLine(); 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					switch(indicacaoDeBusca) {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 1: //buscar playlist
						System.out.println("Qual o nome da playlist que você quer salvar?"); 
						String nomePlaylist = scanner.nextLine(); 
						List<Playlist> playlistPuxadas = playlistDAO.buscarListaPlaylistPorNome(nomePlaylist); 
						if (playlistPuxadas.get(0) == null) {
							System.out.println("Nenhuma playlist encontrada com esse nome."); 
							break; 
						}
						 playlistProcurada = playlistPuxadas.get(0); 
						
						
						break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 2: //listar playlists
						
						
						if(ouvinteLogado.getPlaylists().size() !=0 && ouvinteLogado.getPlaylists().size() >5 ) {
							for(int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
								System.out.println(i+1 + ")"); 
								System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
							}
							System.out.println("Mostrar todos os álbuns? S/N"); // show all
							String showAllPlaylist = scanner.nextLine(); 
							if(showAllPlaylist.equals("S")) {
								for (int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
									System.out.println(i+1 + ")"); 
									System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
								}
							}			
				} else if (ouvinteLogado.getPlaylists().size() ==5) {
					for(int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
						System.out.println(i+1 + ")"); 
						System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
					}
				}else {
					for (int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
						System.out.println(i+1 + ")"); 
						System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
					}
					
				}
						//escolhendo a playlist 
						System.out.println("Escolha a playlist! Digite o número indicado: (Para playlist de favoritas digite 0)"); 
						escolhaPlaylist = scanner.nextInt(); 
						scanner.nextLine(); 
						if(escolhaPlaylist ==0) {
							playlistProcurada = ouvinteLogado.getPlaylistFavoritas(); 	 
						}else {
							playlistProcurada = ouvinteLogado.getPlaylists().get(escolhaPlaylist-1);
						}
						
						
						
						break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						
					case 3: // criar nova playlist
						Playlist playlist = new Playlist(); 
						playlist.setNome(musicaEscolhida.getNome()); 
						List<Musica> musicaPlaylist = new ArrayList(); 
						musicaPlaylist.add(musicaEscolhida); 
						playlist.setMusicas(musicaPlaylist); 
						playlist.setIdOuvinte(ouvinteLogado.getIdOuvinte()); 
						playlist.setTempoStreaming(musicaEscolhida.getDuracaoMusica()); 
						playlist.setFotoDaCapaURL(albumDAO.buscarPorId(musicaEscolhida.getIdAlbum()).getFotoDaCapaURL()); 
						System.out.println("Música salva com sucesso!"); 
						break; 
					default:
						System.out.println("Escolha inválida. Voltando...");
						menuOuvinte(); 
					}
					
					//salvando musica na playlist escolhida 
					
					if(indicacaoDeBusca != 3) {
						System.out.println("Salvando música, " + musicaEscolhida.getNome() + ", na playlist " + playlistProcurada.getNome()); 
						playlistProcurada.getMusicas().add(musicaEscolhida); 
					}
					menuOuvinte();
					break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 3: //seguir artista
					System.out.println("Seguindo artista, " + artistaProcurado.getNome()); 
					artistaProcurado.getFans().add(ouvinteLogado); 
					
					// COLOCAR ARTISTA NA LISTA DE SEGUINDOS DO OUVINTE!!!!!!!!!!!!!!!!!!!
					
					System.out.println("Artista seguido com sucesso!"); 
					menuOuvinte();
					break; 
				case 0: // voltar ao menu
					System.out.println("Voltando ao menu... "); 
					menuOuvinte(); 
					break; 
				default: 
					System.out.println("Escolha inválida! Voltando ao menu... "); 
					menuOuvinte(); 
					break; 
					
				}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				
			} else { //se o numero de álbuns for menor que 5 
				for (int i = 0; i < artistaProcurado.getAlbuns().size(); i++) {
					System.out.println(i+1 +")"); 
					System.out.println(artistaProcurado.getAlbuns().get(i).getNome()); 
					System.out.println(artistaProcurado.getAlbuns().get(i).printarInfos()); 
					System.out.println(artistaProcurado.getAlbuns().get(i).getAnoLancamento()); 
				}
				//escolhendo um álbum
				System.out.println("Escolha um álbum! Digite o número indicado: (Para voltar digite 0)"); 
				escolhaAlbum = scanner.nextInt(); 
				scanner.nextLine(); 
				if(escolhaAlbum ==0) {
					menuOuvinte(); 
				}
				//pritando álbum e suas musicas 
				for(int i = 0; i<artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().size() ; i++) {
					System.out.println(i+1 +")");
					System.out.println(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getNome()); 
					System.out.println(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getDuracaoMusica()); 
				}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				System.out.println("Digite o número indicado para:"); 
				System.out.println("1)Salvar álbum"); 
				System.out.println("2)Salvar música em playlist"); 
				System.out.println("3)Seguir artista"); 
				System.out.println("0)Voltar"); 
				int escolhaDentroDeAlbum = scanner.nextInt(); 
				scanner.nextLine(); 
				
				
				
				switch(escolhaDentroDeAlbum) {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 1: //salvando album
					System.out.println("Salvando álbum, " + artistaProcurado.getAlbuns().get(escolhaAlbum-1).getNome()); 
					
					Playlist albumSalvo = new Playlist(); 
					
					List<Musica> musicasAlbumSalvo = new ArrayList(); 
					//pega quantidade de músicas
					for (int i = 0; i<artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().size(); i++) {
						
						Musica musica = new Musica(); 
						musica.setNome(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getNome()); 
						musica.setDuracaoMusica(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(i).getDuracaoMusica()); 
						musica.setIdAlbum(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getIdAlbum()); 
						
						// salva no bd
						try {
							musicaDAO.salvar(musica);
						} catch (Exception e) {
							System.out.println("Erro ao salvar musica na playlist de album salvo: " + e.getMessage());
						}
						
						//salvando na lista vazia 
						musicasAlbumSalvo.add(musica); 
						
					
					}
					albumSalvo.setNome(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getNome());
					albumSalvo.setFotoDaCapaURL(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getFotoDaCapaURL()); 
					albumSalvo.setTempoStreaming(artistaProcurado.getAlbuns().get(escolhaAlbum-1).getTempoStreaming()); 
					albumSalvo.setIdOuvinte(ouvinteLogado.getIdOuvinte()); 
					albumSalvo.setMusicas(musicasAlbumSalvo);
					// salva no bd
					try {
						playlistDAO.salvar(albumSalvo);
						System.out.println("Álbum salvo com sucesso!");
					} catch (Exception e) {
						System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
					}
					
					ouvinteLogado.getPlaylists().add(albumSalvo); 
					
					
					menuOuvinte();
					break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 2: //salvando musica em playlist
					Playlist playlistProcurada = null; 
					//escolhendo música
					
					int escolhaMusica; 
					System.out.println("Escolha uma música! Digite o número indicado: (Para voltar digite 0)"); 
					escolhaMusica = scanner.nextInt(); 
					Musica musicaEscolhida = artistaProcurado.getAlbuns().get(escolhaAlbum-1).getMusicas().get(escolhaMusica-1); 
					scanner.nextLine(); 
					if(escolhaMusica ==0) {
						menuOuvinte(); 
					}
					//escolhendo playlist 
					
					int escolhaPlaylist; 
					System.out.println("Indique a playlist que quer salvar música:"); 
					System.out.println("Digite o número indicado para:"); 
					System.out.println("1)Buscar playlist pelo nome:"); 
					System.out.println("2)Listar nomes playlist:"); 
					System.out.println("3)Criar nova playlist: "); 
					int indicacaoDeBusca = scanner.nextInt(); 
					scanner.nextLine(); 
					switch(indicacaoDeBusca) {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 1: //buscar playlist
						
						System.out.println("Qual o nome da playlist que você quer salvar?"); 
						String nomePlaylist = scanner.nextLine(); 
						List<Playlist> playlistPuxadas = playlistDAO.buscarListaPlaylistPorNome(nomePlaylist); 
						if (playlistPuxadas.get(0) == null) {
							System.out.println("Nenhuma playlist encontrada com esse nome."); 
							break; 
						}
						 playlistProcurada = playlistPuxadas.get(0); 
						
						
						break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					case 2: //listar playlists
						
						
						if(ouvinteLogado.getPlaylists().size() !=0 && ouvinteLogado.getPlaylists().size() >5 ) {
							for(int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
								System.out.println(i+1 + ")"); 
								System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
							}
							System.out.println("Mostrar todos as playlists álbuns? S/N"); // show all
							String showAllPlaylist = scanner.nextLine(); 
							if(showAllPlaylist.equals("S")) {
								for (int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
									System.out.println(i+1 + ")"); 
									System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
								}
							}			
				} else if (ouvinteLogado.getPlaylists().size() ==5) {
					for(int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
						System.out.println(i+1 + ")"); 
						System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
					}
				}else {
					for (int i = 0; i<ouvinteLogado.getPlaylists().size(); i++) {
						System.out.println(i+1 + ")"); 
						System.out.println(ouvinteLogado.getPlaylists().get(i).getNome()); 
					}
					
				}
						//escolhendo a playlist 
						System.out.println("Escolha a playlist! Digite o número indicado: (Para playlist de favoritas digite 0)"); 
						escolhaPlaylist = scanner.nextInt(); 
						scanner.nextLine(); 
						if(escolhaPlaylist ==0) {
							playlistProcurada = ouvinteLogado.getPlaylistFavoritas(); 	 
						} else {
							playlistProcurada = ouvinteLogado.getPlaylists().get(escolhaPlaylist-1);
						}
						
						
						
						break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

						
					case 3: // criar nova playlist
						Playlist playlist = new Playlist(); 
						playlist.setNome(musicaEscolhida.getNome()); 
						List<Musica> musicaPlaylist = new ArrayList(); 
						musicaPlaylist.add(musicaEscolhida); 
						playlist.setMusicas(musicaPlaylist); 
						playlist.setIdOuvinte(ouvinteLogado.getIdOuvinte()); 
						playlist.setTempoStreaming(musicaEscolhida.getDuracaoMusica()); 
						playlist.setFotoDaCapaURL(albumDAO.buscarPorId(musicaEscolhida.getIdAlbum()).getFotoDaCapaURL()); 
						System.out.println("Música salva com sucesso!"); 
						break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					default:
						System.out.println("Escolha inválida. Voltando...");
						menuOuvinte(); 
					}
					
					//salvando musica na playlist escolhida 
					
					if(indicacaoDeBusca != 3) {
						System.out.println("Salvando música, " + musicaEscolhida.getNome() + ", na playlist " + playlistProcurada.getNome()); 
						playlistProcurada.getMusicas().add(musicaEscolhida); 
					}
					menuOuvinte();
					break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 3: //seguir artista
					System.out.println("Seguindo artista, " + artistaProcurado.getNome()); 
					artistaProcurado.getFans().add(ouvinteLogado); 
					
					// COLOCAR ARTISTA NA LISTA DE SEGUINDOS DO OUVINTE!!!!!!!!!!!!!!!!!!!
					
					System.out.println("Artista seguido com sucesso!"); 
					menuOuvinte();
					break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				case 0: // voltar ao menu
					System.out.println("Voltando ao menu... "); 
					menuOuvinte(); 
					break; 
				default: 
					System.out.println("Escolha inválida! Voltando ao menu... "); 
					menuOuvinte(); 
					break; 
					
				}
			}
			
	
			
			
			
			
			
			
		
		
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 2 :  // BUSCAR OUVINTE
			menuOuvinte();
			break;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 3:  // BUSCAR MÚSICA
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 4: //BUSCAR ÁLBUM
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 5: // CRIAR PLAYLIST
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 6: //LISTAR PLAYLISTS (BIBLIOTECA + FAVS)
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 7: //LISTAR SEGUIDORES
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 8: //LISTAR SEGUINDOS
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 9: //ATUALIZAR PERFIL
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 10: //ATUALIZAR PLAYLIST
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 11: //DELETAR PLAYLIST
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 12: //DELETAR PERFIL
			menuOuvinte();
			break; 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 0:
			System.out.println("Saindo...");
			artistaLogado = null;
			break;
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		default:
			System.out.println("Opção Inválida. Tente Novamente.");
			menuOuvinte();
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}
		
		

	}

}