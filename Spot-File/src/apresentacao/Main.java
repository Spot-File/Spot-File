

package apresentacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;
import persistencia.*;

public class Main {

	private static final Scanner scanner = new Scanner(System.in);

	private static AlbumDAO albumDAO = new AlbumDAO();
	private static ArtistaDAO artistaDAO = new ArtistaDAO();
	private static OuvinteDAO ouvinteDAO = new OuvinteDAO();
	private static PlaylistDAO playlistDAO = new PlaylistDAO();
	private static MusicaDAO musicaDAO = new MusicaDAO();

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
			scanner.nextLine(); 

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

	public static void escolhaArtista() {
		System.out.println("\n=== SESSÃO ARTISTA ===");
		System.out.println("1)Cadastrar (criar conta)");
		System.out.println("2)Entrar (já tem conta)");
		System.out.println("0)Voltar");
		int escolhaArtista = scanner.nextInt();
		scanner.nextLine(); 
		
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

	public static void cadastrarArtista() {
		System.out.println("\n==== CADASTRO DE ARTISTA ====");
		System.out.println("Nome: ");
		String nomeArtista = scanner.nextLine();
		System.out.println("Email: ");
		String emailArtista = scanner.nextLine();
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
		System.out.println("Insira URL da foto de perfil:");
		String URLFotoArtista = scanner.nextLine();
		System.out.println("Inserir ABOUT:");
		String about = scanner.nextLine();
		List<Ouvinte> fans = new ArrayList<Ouvinte>();
		List<Album> albuns = new ArrayList<Album>();

		Artista a = new Artista();
		a.setNome(nomeArtista);
		a.setEmail(emailArtista);
		a.setSenha(senhaArtista);
		a.setFotoPerfil(URLFotoArtista);
		a.setAbout(about);
		a.setFans(fans);
		a.setAlbuns(albuns);

		try {
			artistaDAO.salvar(a);
			artistaLogado = a;
			System.out.println("Cadastro Concluído! Seja bem vindo(a) " + artistaLogado.getNome());
		} catch (Exception e) {
			System.out.println("Erro ao cadastrar: " + e.getMessage());
		}

	}

	public static void verificarArtista() {
		Artista artistaProcurado;
		do {
			System.out.println("\n==== LOGIN ARTISTA ====");
			System.out.print("E-mail: ");
			String emailArtista = scanner.nextLine();
			System.out.print("Senha: ");
			String senhaLogin = scanner.nextLine();

			artistaProcurado = artistaDAO.buscarArtistaPorEmail(emailArtista);
			String senhaBD;

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
		} while (artistaProcurado.getEmail() == null);

		artistaLogado = artistaProcurado;

	}

	public static void menuArtista() {
		System.out.println("\n======== SPOT-FILE ARTISTAS =========");
		System.out.println("\n O que você quer fazer?");
		System.out.println("1) Criar Álbum");
		System.out.println("2) Listar Álbuns");
		System.out.println("3) Listar Álbuns & Músicas");
		System.out.println("4) Atualizar Álbun");
		System.out.println("5) Atualizar Música");
		System.out.println("6) Atualizar Perfil");
		System.out.println("7) Deletar Álbum");
		System.out.println("8) Deletar Música");
		System.out.println("9) Deletar Conta");
		System.out.println("0) Sair");
		int escolhaMenuArtista = scanner.nextInt();
		scanner.nextLine(); 
		
		switch (escolhaMenuArtista) {
		case 1: 
			List<Musica> musicasAlbum = new ArrayList<Musica>();
			System.out.println("Criando o álbum!!Yay");
			System.out.println("Nome:");
			String nomeAlbum = scanner.nextLine();
			System.out.println("Foto da Capa (URL):");
			String URLcapaAlbum = scanner.nextLine();
			System.out.println("Ano de lançamento?");
			int anoDeLancamento = scanner.nextInt();
			scanner.nextLine();
			long idArtistaCriador = artistaLogado.getIdArtista();

			Album album = new Album();
			album.setNome(nomeAlbum);
			album.setFotoDaCapaURL(URLcapaAlbum);
			album.setMusicas(musicasAlbum);
			album.setidArtista(idArtistaCriador);
			album.setAnoLancamento(anoDeLancamento);

			try {
				albumDAO.salvar(album);
				System.out.println("Álbum cadastrado com suceso! Diga olá ao seu novo álbum,  " + album.getNome());
			} catch (Exception e) {
				System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
			}
			System.out.println("Criando as músicas!!Yay");
			int escolhaMusica = 2;
			String nomeMusica;
			int duracaoMusica;
			long idAlbumDaMusica = album.getIdAlbum();
			do {

				System.out.println("Nome: ");
				nomeMusica = scanner.nextLine();
				System.out.println("Duração da música (em segundos):");
				duracaoMusica = scanner.nextInt();
				scanner.nextLine(); 
				
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

				System.out.println("Upar nova música? Sim(1) / Não(0)");
				escolhaMusica = scanner.nextInt();
				scanner.nextLine(); 
			} while (escolhaMusica != 0);

			atualizarAlbum(album);

			artistaLogado = atualizarArtista(artistaLogado);

			menuArtista();

			break;

		case 2: 
			System.out.println("===== SEUS ÁLBUNS =====");
			for (int i = 0; i < artistaLogado.getAlbuns().size(); i++) {
				System.out.println(artistaLogado.getAlbuns().get(i).getNome());
				System.out.println(artistaLogado.getAlbuns().get(i).printarInfos());
				System.out.println("    "
						+ artistaDAO.buscarPorIdArtista(artistaLogado.getAlbuns().get(i).getIdArtista()).getNome());
				System.out.println("=====================");
			}

			if (artistaLogado.getAlbuns().size() == 0) {
				System.out.println("Você não tem álbuns! Aproveite e crie! ;) ");
			}

			menuArtista();
			break;

		case 3: 
			System.out.println("===== SEUS ÁLBUNS =====");
			for (int i = 0; i < artistaLogado.getAlbuns().size(); i++) {
				System.out.println(artistaLogado.getAlbuns().get(i).getNome());
				System.out.println(artistaLogado.getAlbuns().get(i).printarInfos());
				System.out.println("=====================");

				for (int j = 0; j < artistaLogado.getAlbuns().get(i).getMusicas().size(); j++) {
					System.out.print("•");
					System.out.println(artistaLogado.getAlbuns().get(i).getMusicas().get(j).getNome());
					System.out.print("    - ");
					System.out.println(artistaLogado.getNome());
					System.out.print("    - ");
					System.out.println(artistaLogado.getAlbuns().get(i).getMusicas().get(j).getDuracaoMusica());
				}
			}
			if (artistaLogado.getAlbuns().size() == 0) {
				System.out.println("Você não tem álbuns nem músicas! Aproveite e crie! ;) ");
			}

			menuArtista();
			break;

		case 4:

			List<Album> albunsAtualizar = artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista());
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
			} 
			

			System.out.println("Qual o nome do seu álbum que deseja editar?");
			String nomeAlbumEditar = scanner.nextLine();
			List<Album> albunsPuxados = albumDAO.buscarListaAlbumPorNome(nomeAlbumEditar);
			if (albunsPuxados.get(0) == null || albunsPuxados.size() == 0) {
				System.out.println("Nenhum álbum encontrado com esse nome.");
				break;
			} else if (albunsPuxados.size() != 0) {
				Album albumProcurado = albunsPuxados.get(0);
				int escolhaDeAtributo = 5;

				if (albumProcurado.getIdArtista() == artistaLogado.getIdArtista()) {
					System.out.println("Você é o artista criador do álbum!");
					do {
						System.out.println("O que desejas editar?");
						System.out.println("1)Nome");
						System.out.println("2)Capa do Álbum (inserir URL)");
						System.out.println("3)Ano de lançamento");
						System.out.println("0)Voltar");
						escolhaDeAtributo = scanner.nextInt();
						scanner.nextLine(); 
						switch (escolhaDeAtributo) {
						case 1: 
							System.out.println("Antigo nome: " + albumProcurado.getNome());
							System.out.println("Insira o novo nome do álbum:");
							String novoNome = scanner.nextLine();
							albumProcurado.setNome(novoNome);

							try {
								albumDAO.editar(albumProcurado);

							} catch (Exception e) {
								System.out.println("Erro ao mudar nome do álbum: " + e.getMessage());
							}
							artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

							break;
						case 2: 
							System.out.println("Insira o novo URL da capa do álbum:");
							String novaCapaURL = scanner.nextLine();
							albumProcurado.setFotoDaCapaURL(novaCapaURL);

							try {
								albumDAO.editar(albumProcurado);

							} catch (Exception e) {
								System.out.println("Erro ao mudar capa do álbum: " + e.getMessage());
							}
							artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

							break;

						case 3: 
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
							artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

							break;

						case 0: 
							System.out.println("Voltando...");

							break;
						default:
							System.out.println("Escolha não existe. Tente de novo.");

							break;
						}
					} while (escolhaDeAtributo != 0);

				} else {
					System.out.println("Ops... parece que você não é o artista criador desse álbum, tente novamente");
				}
			}

			menuArtista();
			break;

		case 5: 
			System.out.println("Qual o nome da música que deseja editar?");
			String nomeMusicaEditar = scanner.nextLine();
			List<Musica> musicasPuxadas = musicaDAO.buscarListaMusicaPorNome(nomeMusicaEditar);
			if (musicasPuxadas.isEmpty()) {
				System.out.println("Nenhuma música encontrada com esse nome.");
				break; 
			} else if (musicasPuxadas.size() != 0) {
				Musica musicaProcurada = musicasPuxadas.get(0);

				int escolhaDeAtributoMusica = 5;
				Album albumDaMusica = albumDAO.buscarPorId(musicaProcurada.getIdAlbum());
				if (albumDaMusica.getIdArtista() == artistaLogado.getIdArtista()) {
					System.out.println("Você é o artista criador da música!");
					do {
						System.out.println("O que desejas editar?");
						System.out.println("1)Nome");
						System.out.println("2)Álbum (ver)");
						System.out.println("3)Tempo de duração:");
						System.out.println("0)Voltar");
						escolhaDeAtributoMusica = scanner.nextInt();
						scanner.nextLine(); 

						switch (escolhaDeAtributoMusica) {
						case 1: 

							System.out.println("Antigo nome: " + musicaProcurada.getNome());
							System.out.println("Insira o novo nome da música: ");
							String novoNome = scanner.nextLine();
							musicaProcurada.setNome(novoNome);

							try {
								musicaDAO.editar(musicaProcurada);

							} catch (Exception e) {
								System.out.println("Erro ao mudar nome da música: " + e.getMessage());
							}
							artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

							break;
						case 2: 
							System.out.println("Antigo álbum: " + albumDaMusica.getNome());
							System.out.println("Insira o nome do novo álbum da música: ");
							String novoNomeAlbum = scanner.nextLine();

							List<Album> albunsPuxadosParaMusica = albumDAO.buscarListaAlbumPorNome(novoNomeAlbum);
							if (albunsPuxadosParaMusica.isEmpty()) {
								System.out.println("Nenhum álbum encontrado com esse nome.");
								break; 
							}
							Album albumProcuradoParaMusica = albunsPuxadosParaMusica.get(0);
							musicaProcurada.setIdAlbum(albumProcuradoParaMusica.getIdAlbum());

							try {
								musicaDAO.editar(musicaProcurada);

							} catch (Exception e) {
								System.out.println("Erro ao mudar o tempo de duração: " + e.getMessage());
							}
							artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

							break;

						case 3: 
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
							artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

							break;
						case 0:
							System.out.println("Voltando...");

							break;
						default:
							System.out.println("Escolha não existe. Tente de novo.");

							break;
						}

					} while (escolhaDeAtributoMusica != 0);
				} else {
					System.out.println("Ops... parece que você não é o artista criador dessa música, tente novamente");
				}
			}

			menuArtista();
			break;

		case 6: 
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
				case 1: 
					System.out.println("Antigo nome: " + artistaLogado.getNome());
					System.out.println("Insira o novo nome: ");
					String novoNome = scanner.nextLine();

					artistaLogado.setNome(novoNome);

					try {
						artistaDAO.editar(artistaLogado);

					} catch (Exception e) {
						System.out.println("Erro ao mudar o nome: " + e.getMessage());
					}
					artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

					break;
				case 2: 
					System.out.println("Antigo foto de perfil: " + artistaLogado.getFotoPerfil());
					System.out.println("Insira nova URL de perfil: ");
					String novaFoto = scanner.nextLine();

					artistaLogado.setFotoPerfil(novaFoto);

					try {
						artistaDAO.editar(artistaLogado);

					} catch (Exception e) {
						System.out.println("Erro ao mudar a URL de perfil:  " + e.getMessage());
					}
					artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

					break;
				case 3: 
					System.out.println("Antigo about " + artistaLogado.getAbout());
					System.out.println("Insira o novo about: ");
					String novoAbout = scanner.nextLine();

					artistaLogado.setAbout(novoAbout);

					try {
						artistaDAO.editar(artistaLogado);

					} catch (Exception e) {
						System.out.println("Erro ao mudar o about: " + e.getMessage());
					}
					artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

					break;
				
				case 4: 
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
					artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());
					break;
					
				case 5: 
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
					artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());

					break;

				case 0: 
					System.out.println("Voltando...");
					menuArtista();
					break;
					
				default:
					System.out.println("Escolha não existe. Tente de novo.");

					break;
				}

			} while (escolhaMudarPerfil != 0);
			menuArtista();
			break;

		case 7: 
			System.out.println("=== EXCLUSÃO DE ÁLBUM ===");
			List<Album> deleteAlbuns = artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista());
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
			} 
			
			System.out.println("Digite o número do álbum que deseja deletar:");
			int deleteAlbum = scanner.nextInt();
			scanner.nextLine();
			Album albumDeletar = deleteAlbuns.get(deleteAlbum - 1);
			if (albumDeletar == null) {
				System.out.println("Não foi encontrado o álbum número " + deleteAlbum + ".\nVoltando ao Menu");
			} else {
				System.out.println("Tem certeza que deseja deleter o àlbum: " + albumDeletar.getNome() + "? (S/N)");
				String confirmacao = scanner.next();
				scanner.nextLine();
				confirmacao.toUpperCase();

				switch (confirmacao) {
				case "S":
					try {
						albumDAO.excluirAlbumPorId(albumDeletar.getIdAlbum());
						System.out.println("Álbum deletado com sucesso!\n");

					} catch (Exception e) {
						System.out.println("Erro ao deletar álbum: " + e.getMessage());
					}
					artistaLogado = atualizarArtista(artistaLogado);
					break;
				case "N":
					System.out.println("Exclusão Cancelada.\n");
					;
					break;
				default:
					System.out.println("Opção Inválida!");

				}
			}
			menuArtista();
			break;
		case 8: 
			System.out.println("=== EXCLUSÃO DE MÚSICAS ===");
			System.out.println("Aqui você escolhe um de seus álbuns\nLogo depois tira uma música por vez deles");

			List<Album> deleteMusicas = artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista());
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
			} 
			System.out.println("Digite o nome de qual álbum deseja deletar as músicas('C' para cancelar):");
			String nomeEscolhidoDelete = scanner.nextLine();
			nomeEscolhidoDelete.toUpperCase();

			if (nomeEscolhidoDelete.matches("C")) {
				System.out.println("Exclusão Cancelada.\n");
				menuArtista();
				break;
			} 
			List<Album> albunsEscolhidoDelete = albumDAO.buscarListaAlbumPorNome(nomeEscolhidoDelete); 
			if (!albunsEscolhidoDelete.isEmpty()) {
				Album albumEscolhaDeletar = albunsEscolhidoDelete.get(0);
				if (!(albumEscolhaDeletar.getIdArtista() == artistaLogado.getIdArtista())) { 
					System.out.println("Não há nenhum álbum com esse nome! Cheque novamente.");
				} else {
					System.out.println("\n" + albumEscolhaDeletar.getNome());

					for (int i = 0; i < albumEscolhaDeletar.getMusicas().size(); i++) {
						System.out.println(" " + (i + 1) + ". " + albumEscolhaDeletar.getMusicas().get(i).getNome());
					}
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
							try {
								musicaDAO.excluirMusicaPorId(musicaDeletar);
								System.out.println("Música deletada com sucesso!\n");

							} catch (Exception e) {
								System.out.println("Erro ao deletar música: " + e.getMessage());
							}
							;

							albumEscolhaDeletar
									.setMusicas(albumDAO.buscarListaMusicaPorIdAlbum(albumEscolhaDeletar.getIdAlbum()));
							artistaLogado
									.setAlbuns(artistaDAO.buscarListaAlbumPorIdArtista(artistaLogado.getIdArtista()));
							artistaLogado = artistaDAO.buscarPorIdArtista(artistaLogado.getIdArtista());
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

		case 9: 
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
						System.out.println("Estamos apagando suas músicas... Foi bom enquanto durou.");

						try {
							artistaDAO.excluirArtistaPorId(artistaLogado);
							System.out.println("Adeus " + artistaLogado.getNome());
							System.out.println("Voltando ao menu...");
							artistaLogado = null;
						} catch (Exception e) {
							System.out.println("Erro ao deletar conta: " + e.getMessage());
						}
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
		default:
			System.out.println("Opção Inválida. Tente Novamente.");
			menuArtista();
		}

	}

	public static void escolhaOuvinte() {
		System.out.println("--OUVINTE--");
		System.out.println("1)Cadastrar (criar conta)");
		System.out.println("2)Entrar (já tem conta)");
		System.out.println("0)Voltar");
		int escolhaOuvinte = scanner.nextInt();
		scanner.nextLine(); 

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

	public static void cadastrarOuvinte() {
		System.out.println("Nome: ");
		String nomeOuvinte = scanner.nextLine();
		System.out.println("Email: ");
		String emailOuvinte = scanner.nextLine();
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
		System.out.println("Insira URL da foto de perfil:");
		String URLFotoOuvinte = scanner.nextLine();
		List<Playlist> playlists = new ArrayList<Playlist>();
		List<Ouvinte> seguidores = new ArrayList<Ouvinte>();
		Playlist playlistFavoritas = new Playlist();
		Ouvinte o = new Ouvinte();
		o.setNome(nomeOuvinte);
		o.setEmail(emailOuvinte);
		o.setSenha(senhaOuvinte);
		o.setFotoPerfil(URLFotoOuvinte);
		o.setPlaylists(playlists);
		o.setFollowers(seguidores);
		o.setPlaylistFavoritas(playlistFavoritas);

		try {
			ouvinteDAO.salvar(o);
			ouvinteLogado = o;
			System.out.println("Cadastro Concluído! Seja bem vindo(a) " + ouvinteLogado.getNome());
		} catch (Exception e) {
			System.out.println("Erro ao cadastrar: " + e.getMessage());
		}

	}

	public static void verificarOuvinte() {
		Ouvinte ouvinteProcurado = null;
		do {
			System.out.println("=== LOGIN OUVINTE ===");
			System.out.print("E-mail: ");
			String emailOuvinte = scanner.nextLine();
			System.out.print("Senha: ");
			String senhaLogin = scanner.nextLine();
			ouvinteProcurado = ouvinteDAO.buscarOuvintePorEmail(emailOuvinte);
			String senhaBD;
			if (ouvinteProcurado == null || ouvinteProcurado.getNome() == null) {
				System.out.println("Não existe ouvinte cadastrado com esse email. Verifique email e tente novamente.");
			} else {
				System.out.println("Ouvinte encontrado! Olá,  " + ouvinteProcurado.getNome());
				senhaBD = ouvinteProcurado.getSenha();
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
		} while (ouvinteProcurado.getEmail() == null);

		ouvinteLogado = ouvinteProcurado;
	}

	public static void menuOuvinte() {
		System.out.println("======== SPOT-FILE OUVINTES =========");
		System.out.println(" O que deseja fazer?");
		System.out.println("1)Buscar artista"); 
		System.out.println("2)Buscar ouvinte");
		System.out.println("3)Buscar música");
		System.out.println("4)Buscar álbum"); 
		System.out.println("5)Criar playlist");
		System.out.println("6)Listar minhas playlists (Biblioteca)");
		System.out.println("7)Listar seguidores");
		System.out.println("8)Listar seguindos"); 
		System.out.println("9)Atualizar perfil");
		System.out.println("10)Atualizar playlist");
		System.out.println("11)Deletar playlist");
		System.out.println("12)Deletar perfil");
		System.out.println("0)Sair");
		int escolhaOuvinte = scanner.nextInt();
		scanner.nextLine();

		switch (escolhaOuvinte) {
		case 1: 
			System.out.println("Insira o nome do artista que procura:");
			String nomeArtistaProcurado = scanner.nextLine();
			List<Artista> artistasPuxados = artistaDAO.buscarListaArtistaPorNome(nomeArtistaProcurado);
			if (artistasPuxados.get(0) == null) {
				System.out.println("Nennhum artista encontrado com esse nome.");
				break;
			}
			for (int i = 0; i < artistasPuxados.size(); i++) {
				System.out.println((i + 1) + ". " + artistasPuxados.get(i).getNome());
			}
			System.out.println("Digite o número do artista que deseja vizualizar:");
			int opcaoSelecaoArtista = scanner.nextInt();
			scanner.nextLine();
			if (opcaoSelecaoArtista < 0 || opcaoSelecaoArtista > artistasPuxados.size()) {
				System.out.println("Opção Inválida...");
				System.out.println("Tente novamente.");
			} else {
				Artista artistaProcurado = artistasPuxados.get(opcaoSelecaoArtista - 1);
				acessoArtista(artistaProcurado);
			}
			menuOuvinte();
			break;
		case 2: 
			System.out.println("Insira o nome do ouvinte que procura: ");
			String nomeOuvinteProcurado = scanner.nextLine();
			List<Ouvinte> ouvintesPuxados = ouvinteDAO.buscarListaOuvintePorNome(nomeOuvinteProcurado);
			if (ouvintesPuxados.get(0) == null) {
				System.out.println("Nenhum ouvinte encontrado com esse nome.");
				break;
			}
			for (int i = 0; i < ouvintesPuxados.size(); i++) {
				System.out.println((i + 1) + ". " + ouvintesPuxados.get(i).getNome());
			}
			System.out.println("Digite o número do ouvinte que deseja vizualizar:");
			int opcaoSelecaoOuvinte = scanner.nextInt();
			scanner.nextLine();
			if (opcaoSelecaoOuvinte < 0 || opcaoSelecaoOuvinte > ouvintesPuxados.size()) {
				System.out.println("Opção Inválida...");
				System.out.println("Tente novamente.");
			} else {
				Ouvinte ouvinteProcurado = ouvintesPuxados.get(opcaoSelecaoOuvinte - 1);
				acessoOuvinte(ouvinteProcurado);
			}
			menuOuvinte();
			break;
		case 3: 
			System.out.println("Insira o nome da música que procura: ");
			String nomeMusica = scanner.nextLine();
			List<Musica> musicasPuxadas = musicaDAO.buscarListaMusicaPorNome(nomeMusica);
			if (musicasPuxadas.get(0) == null) {
				System.out.println("Nenhum album encontrado com esse nome.");
				break;
			}
			for (int i = 0; i < musicasPuxadas.size(); i++) {
				System.out.println((i + 1) + ". " + musicasPuxadas.get(i).getNome());
			}
			System.out.println("Digite o número da música que deseja vizualizar:");
			int opcaoSelecaoMusica = scanner.nextInt();
			scanner.nextLine();
			if (opcaoSelecaoMusica < 0 || opcaoSelecaoMusica > musicasPuxadas.size()) {
				System.out.println("Opção Inválida...");
				System.out.println("Tente novamente.");
			} else {
				Musica musicaProcurada = musicasPuxadas.get(opcaoSelecaoMusica - 1);
				acessoMusica(musicaProcurada);
			}
			menuOuvinte();
			break;
		case 4:
			System.out.println("Insira o nome do álbum que procura: ");
			String nomeAlbum = scanner.nextLine();
			List<Album> albunsPuxados = albumDAO.buscarListaAlbumPorNome(nomeAlbum);
			if (albunsPuxados.get(0) == null) {
				System.out.println("Nenhum album encontrado com esse nome.");
				break;
			}
			for (int i = 0; i < albunsPuxados.size(); i++) {
				System.out.println((i + 1) + ". " + albunsPuxados.get(i).getNome());
			}
			System.out.println("Digite o número do álbum que deseja vizualizar:");
			int opcaoSelecaoAlbum = scanner.nextInt();
			scanner.nextLine();
			if (opcaoSelecaoAlbum < 0 || opcaoSelecaoAlbum > albunsPuxados.size()) {
				System.out.println("Opção Inválida...");
				System.out.println("Tente novamente.");
			} else {
				Album albumProcurado = albunsPuxados.get(opcaoSelecaoAlbum - 1);
				acessoAlbum(albumProcurado);
			}
			menuOuvinte();
			break;
		case 5: 
			List<Musica> musicasPlaylist = new ArrayList<Musica>();
			System.out.println("Criando a playlist! Yayy.");
			System.out.println("Nome:");
			String nomePlaylist = scanner.nextLine();
			System.out.println("Bio:");
			String bioPlaylist = scanner.nextLine();
			System.out.println("Foto da capa:");
			String fotoCapaURlPlaylist = scanner.nextLine();

			Playlist playlist = new Playlist();
			playlist.setBio(bioPlaylist);
			playlist.setFotoDaCapaURL(fotoCapaURlPlaylist);
			playlist.setNome(nomePlaylist);
			playlist.setMusicas(musicasPlaylist);
			playlist.setIdOuvinte(ouvinteLogado.getIdOuvinte());

			try {
				playlistDAO.salvar(playlist);
				System.out.println(
						"Playlist cadastrada com suceso! Diga olá a sua nova playlist,  " + playlist.getNome());
			} catch (Exception e) {
				System.out.println("Erro ao cadastrar playlist: " + e.getMessage());
			}
			ouvinteLogado = ouvinteDAO.buscarPorIdOuvinte(ouvinteLogado.getIdOuvinte());

			System.out.println("Adicionar músicas a playlist? (S/N)");
			String escolhaAdicionarMusicas = scanner.nextLine();
			if (escolhaAdicionarMusicas.equals("S")) {
				Playlist favoritas = ouvinteLogado.getPlaylistFavoritas();
				int totalMusicas = favoritas.getMusicas().size();
				int nroMusicaEscolhida = 0;

				if (favoritas.getMusicas().size() == 0) {
					System.out.println(
							"Você não tem músicas favoritas... Para adicionar músicas em playlists criadas dê uma pesquisada em artistas, álbuns, ouvintes... Adicione sempre as músicas que gostar na favoritas e então adcione a na sua playlist!");
				}
				if (totalMusicas > 5) {
					System.out.println("=====Suas MÚSICAS FAVORITAS:=====");
					for (int i = 0; i < 5; i++) {
						System.out.println(i + 1 + ")");
						System.out.println(favoritas.getMusicas().get(totalMusicas - i).getNome());
						System.out.println(artistaDAO.buscarPorIdArtista(albumDAO
								.buscarPorId(favoritas.getMusicas().get(totalMusicas - i).getIdAlbum()).getIdArtista())
								.getNome()); 
					}
					System.out.println("==============================");

					System.out.println("Mostrar todas? (S/N)");
					String showAllFavoritas = scanner.nextLine();
					if (showAllFavoritas.equals("S")) {
						System.out.println("=====Suas MÚSICAS FAVORITAS:=====");
						for (int i = 0; i < favoritas.getMusicas().size(); i++) {
							System.out.println(i + 1 + ")");
							System.out.println(favoritas.getMusicas().get(i).getNome());
							System.out.println(artistaDAO.buscarPorIdArtista(
									albumDAO.buscarPorId(favoritas.getMusicas().get(i).getIdAlbum()).getIdArtista())
									.getNome()); 
							}
					}
					System.out.println("==============================");

				} else if (totalMusicas == 5) {
					System.out.println("=====Suas MÚSICAS FAVORITAS:=====");
					for (int i = 0; i < 5; i++) {
						System.out.println(i + 1 + ")");
						System.out.println(favoritas.getMusicas().get(i).getNome());
						System.out.println(artistaDAO
								.buscarPorIdArtista(
										albumDAO.buscarPorId(favoritas.getMusicas().get(i).getIdAlbum()).getIdArtista())
								.getNome()); 
					}
					System.out.println("==============================");

				} else if (totalMusicas < 5) {
					System.out.println("=====Suas MÚSICAS FAVORITAS:=====");
					for (int i = 0; i < favoritas.getMusicas().size(); i++) {
						System.out.println(i + 1 + ")");
						System.out.println(favoritas.getMusicas().get(i).getNome());
						System.out.println(artistaDAO
								.buscarPorIdArtista(
										albumDAO.buscarPorId(favoritas.getMusicas().get(i).getIdAlbum()).getIdArtista())
								.getNome()); 
					}
					System.out.println("==============================");

				}


				if (favoritas.getMusicas().size() == 0) {
					System.out.println(
							"Você não tem músicas favoritas... Para adicionar músicas em playlists criadas dê uma pesquisada em artistas, álbuns, ouvintes... Adicione sempre as músicas que gostar na favoritas e então adcione a na sua playlist!");
				} else {
					int continuar = 5;
					do {
						System.out.println("Escolha uma música pelo número indicado: (para sair digite 0)");
						nroMusicaEscolhida = scanner.nextInt();
						if (nroMusicaEscolhida == 0) {
							menuOuvinte();
						}

						scanner.nextLine();
						Musica musicaEscolhida = favoritas.getMusicas().get(nroMusicaEscolhida - 1);
						playlist.getMusicas().add(musicaEscolhida);

						try {
							playlistDAO.adicionarMusica(musicaEscolhida, playlist);
						} catch (Exception e) {
							System.out.println("Erro ao salvar música: " + e.getMessage());
						}

						ouvinteLogado = ouvinteDAO.buscarPorIdOuvinte(ouvinteLogado.getIdOuvinte());
						System.out.println(
								"Continuar adicionando? (Digite qualquer coisa diferente de 0, para sair digite 0");
						continuar = scanner.nextInt();
						scanner.nextLine();

					} while (continuar != 0);

				}
			} else if (escolhaAdicionarMusicas.equals("N")) {
				System.out.println("Ok...Voltando!");
			} else {
				System.out.println("Escolha inválida. Voltando...");
			}
			menuOuvinte();

			break;
		case 6: 
			verPlaylists(ouvinteLogado);
			menuOuvinte();
			break;
		case 7: 
			int opcaoVoltarSeguidores = 1;
			do {
				System.out.println("=== SEUS SEGUIDORES ===");
				List<Ouvinte> seguidoresLista = ouvinteLogado.getFollowers();
				System.out.println(seguidoresLista.size() + " seguidores");
				if (seguidoresLista.size() <= 5 && !seguidoresLista.isEmpty()) {
					for (int i = 0; i < seguidoresLista.size(); i++) {
						System.out.println("1. " + seguidoresLista.get(i).getNome());
					}
					System.out.println("-----------------------");
					System.out.println("1) Ver Perfil de Seguidor");
					System.out.println("0) Voltar");
					int opcaoVizualizarSeguidores = scanner.nextInt();
					scanner.nextLine();
					switch (opcaoVizualizarSeguidores) {
					case 1:
						System.out.println("Digite o número do seguidor que quer ver:");
						int seguidor = scanner.nextInt();
						scanner.nextLine();
						Ouvinte seguidorProcurado = seguidoresLista.get(seguidor - 1);
						if (seguidorProcurado == null) {
							System.out.println("Seguidor não encontrado!\nTente novamente");
						} else {
							acoesSeguidores(seguidorProcurado);
						}
						break;
					case 0:
						opcaoVoltarSeguidores = 0;
						break;
					default:
						System.out.println("Opção Inválida. Tente novamente!!");

					}
				} else if (!seguidoresLista.isEmpty()) {
					for (int i = 0; i < 5; i++) {
						System.out.println("1. " + seguidoresLista.get(i).getNome());
					}
					System.out.println("-----------------------");
					System.out.println("\n1) Ver Todos os Seguidores");
					System.out.println("2) Ver Perfil de Seguidor");
					System.out.println("0) Voltar");
					int opcaoVizualizarSeguidores = scanner.nextInt();
					scanner.nextLine();
					switch (opcaoVizualizarSeguidores) {
					case 1:
						System.out.println("=== SEUS SEGUIDORES ===");
						for (int i = 0; i < seguidoresLista.size(); i++) {
							System.out.println("1. " + seguidoresLista.get(i).getNome());
						}
						System.out.println("------------------------");
						System.out.println("Digite qualquer coisa para voltar");
						String s = scanner.next();
						scanner.nextLine();
						if (s.equals(s)) {
							System.out.println("Voltando...");
						}
						break;
					case 2:
						System.out.println("Digite o número do seguidor que quer ver:");
						int seguidor = scanner.nextInt();
						scanner.nextLine();
						Ouvinte seguidorProcurado = seguidoresLista.get(seguidor - 1);
						if (seguidorProcurado == null) {
							System.out.println("Seguidor não encontrado!\nTente novamente");
						} else {
							acoesSeguidores(seguidorProcurado);
						}
						break;
					case 0:
						opcaoVoltarSeguidores = 0;
						break;
					default:
						System.out.println("Opção Inválida!");
					}
				} else {
					System.out.println("Parece que você não tem nenhum seguidor :<. Que pena!");
					System.out.println("Saia de casa e tente fazer alguns amigos!! Acreditamos em você!");
					opcaoVoltarSeguidores = 0;
				}
			} while (opcaoVoltarSeguidores != 0);
			menuOuvinte();
			break;
		case 8: 
			int opcaoVoltarSeguindo = 1;
			do {
				System.out.println("=== SEUS SEGUIDOS ===");
				List<Usuario> seguidos = ouvinteLogado.getFollowing();
				System.out.println(seguidos.size() + " seguidos");
				if (seguidos.size() > 5) {
					for (int i = 0; i < 5; i++) {
						System.out.println((i + 1) + ". " + seguidos.get(i).getNome());
					}
					System.out.println("---------------------");
					System.out.println("1) Vizualizar Perfil de Seguido");
					System.out.println("2) Ver todos");
					System.out.println("0) Voltar");
					int opcaoVizualizarSeguido = scanner.nextInt();
					scanner.nextLine();
					switch (opcaoVizualizarSeguido) {
					case 1:
						acoesSeguido(seguidos);
						break;
					case 2:
						System.out.println("=== LISTA COMPLETA DE SEGUIDOS ===");
						for (int i = 0; i < seguidos.size(); i++) {
							System.out.println((i + 1) + ". " + seguidos.get(i).getNome());
						}
						System.out.println("---------------------");
						System.out.println("1) Vizualizar Perfil de Seguido");
						System.out.println("0) Voltar");
						int opcaoVizualizarSeguidoCompleto = scanner.nextInt();
						scanner.nextLine();
						switch (opcaoVizualizarSeguidoCompleto) {
						case 1:
							acoesSeguido(seguidos);
							break;
						case 0:
							System.out.println("Voltando...");
							break;
						default:
							System.out.println("Opcao Inválida!");
							System.out.println("Voltando...");
						}
						break;
					case 0:
						opcaoVoltarSeguindo = 0;
						System.out.println("Voltando ao Menu...");
						break;
					default:
						System.out.println("Opção Inválida! Tente Novamente...");
					}

				} else if (!seguidos.isEmpty()) {
					for (int i = 0; i < seguidos.size(); i++) {
						System.out.println((i + 1) + ". " + seguidos.get(i).getNome());
					}
					System.out.println("---------------------");
					System.out.println("1) Vizualizar Perfil de Seguido");
					System.out.println("0) Voltar");
					int opcaoVizualizarSeguido = scanner.nextInt();
					scanner.nextLine();
					switch (opcaoVizualizarSeguido) {
					case 1:
						acoesSeguido(seguidos);
						break;
					case 0:
						opcaoVoltarSeguindo = 0;
						System.out.println("Voltando ao Menu...");
						break;
					default:
						System.out.println("Opção Inválida! Tente Novamente...");
					}
				} else {
					System.out.println("Você atualmente não está seguindo nínguem!");
					System.out.println("Procure alguém pra seguir :)");
					opcaoVoltarSeguindo = 0;
				}
			} while (opcaoVoltarSeguindo != 0);
			menuOuvinte();
			break;
		case 9:
			int opcaoVoltarPerfil = 1;
			do {
				System.out.println("=== ATUALIZANDO PERFIL ===");
				System.out.println("O que deseja atualizar?");
				System.out.println("1) Nome");
				System.out.println("2) Email");
				System.out.println("3) Senha");
				System.out.println("4) Foto de Perfil");
				System.out.println("0) Voltar");
				int opcaoAtualizarPerfil = scanner.nextInt();
				scanner.nextLine();
				switch (opcaoAtualizarPerfil) {
				case 1:
					System.out.println("Nome Antigo: " + ouvinteLogado.getNome());
					System.out.println("Novo nome: ");
					String novoNome = scanner.nextLine();
					ouvinteLogado.setNome(novoNome);
					ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					break;
				case 2:
					System.out.println("Email Antigo: " + ouvinteLogado.getEmail());
					System.out.println("Novo Email: ");
					String novoEmail = scanner.nextLine();
					ouvinteLogado.setEmail(novoEmail);
					ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					break;
				case 3:
					System.out.println("Senha Antiga: " + ouvinteLogado.getSenha());
					System.out.println("Nova Senha: ");
					String novaSenha = scanner.nextLine();
					ouvinteLogado.setSenha(novaSenha);
					ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					break;
				case 4:
					System.out.println("Foto Antiga: " + ouvinteLogado.getFotoPerfil());
					System.out.println("Nova Foto: ");
					String novaFoto = scanner.nextLine();
					ouvinteLogado.setFotoPerfil(novaFoto);
					ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					break;
				case 0:
					System.out.println("Voltando ao Menu...");
					opcaoVoltarPerfil = 0;
					break;
				default:
					System.out.println("Opção Inválida! Tente Novamente...");
				}
			} while (opcaoVoltarPerfil != 0);
			menuOuvinte();
			break;
		case 10: 
			System.out.println("=== SUA BIBLIOTECA ===");
			List<Playlist> bibliotecaOuvinte = ouvinteLogado.getPlaylists(); 
			if (bibliotecaOuvinte.isEmpty() || bibliotecaOuvinte == null) { 
				System.out.println("No momento não tens nenhuma playlist");
				System.out.println("Aproveite a oportunidade para criar uma!");
				System.out.println("Voltando ao Menu...");
				break;
			} else {

				if (bibliotecaOuvinte.size() > 5) {
					for (int i = 0; i < 5; i++) { 
						System.out.println(i + 1 + ")");
						System.out.println(bibliotecaOuvinte.get(i).getNome());
					}
					System.out.println("Mostrar todas? (S/N)");
					String escolhaContinuar = scanner.nextLine();

					if (escolhaContinuar.equals("S")) {
						for (int i = 0; i < bibliotecaOuvinte.size(); i++) {
							System.out.println(i + 1 + ")");
							System.out.println(bibliotecaOuvinte.get(i).getNome());
						}
					}
					System.out.println("Escolha a playlist que deseja editar pelo número indicado:");
					int escolhaPlaylistEditar = scanner.nextInt();
					scanner.nextLine();
					Playlist playlistEditar = bibliotecaOuvinte.get(escolhaPlaylistEditar);
					editarPlaylist(playlistEditar);
				} else if (bibliotecaOuvinte.size() == 5) {
					for (int i = 0; i < 5; i++) {
						System.out.println(i + 1 + ")");
						System.out.println(bibliotecaOuvinte.get(i).getNome());
					}
					System.out.println("Escolha a playlist que deseja editar pelo número indicado:");
					int escolhaPlaylistEditar = scanner.nextInt();
					scanner.nextLine();
					Playlist playlistEditar = bibliotecaOuvinte.get(escolhaPlaylistEditar);
					editarPlaylist(playlistEditar);

				} else if (bibliotecaOuvinte.size() < 5) {
					for (int i = 0; i < bibliotecaOuvinte.size(); i++) {
						System.out.println(i + 1 + ")");
						System.out.println(bibliotecaOuvinte.get(i).getNome());
					}
					System.out.println("Escolha a playlist que deseja editar pelo número indicado:");
					int escolhaPlaylistEditar = scanner.nextInt();
					scanner.nextLine();
					Playlist playlistEditar = bibliotecaOuvinte.get(escolhaPlaylistEditar - 1);
					editarPlaylist(playlistEditar);
				}
			}

			menuOuvinte();
			break;
		case 11: 
			System.out.println("=== DELETAR PLAYLIST ===");
			List<Playlist> playlists = ouvinteLogado.getPlaylists();
			for (int i = 0; i < playlists.size(); i++) {
				System.out.println((i + 1) + ". " + playlists.get(i).getNome());
			}
			System.out.println("Ao apagar a sua playlist, irá perder a sua coletânea única");
			System.out.println("e personalizada. Será apagada para sempre!(é realmente muito tempo)");
			System.out.println("Digite o número da playlist que deseja deletar: ");
			int deletarPlaylist = scanner.nextInt();
			scanner.nextLine();
			Playlist playlistDeletar = playlists.get(deletarPlaylist - 1);
			if (playlistDeletar == null || playlistDeletar.getNome() == null) {
				System.out.println("Não foi encontrada a playlist de número " + deletarPlaylist);
			} else {
				System.out.println("Queres deletar a playlist: " + playlistDeletar.getNome() + "? (S/N)");
				String confirmacaoDeletarPlaylist = scanner.nextLine();
				confirmacaoDeletarPlaylist.toUpperCase();
				if (confirmacaoDeletarPlaylist.matches("S")) {
					System.out.println("Deletando...");
					try {
						playlistDAO.excluirPorId(playlistDeletar);
					} catch (Exception e) {
						System.out.println("Não foi possível excluir pois: " + e.getMessage());
					} finally {
						ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					}
				} else {
					System.out.println("Exclusão Cancelada!");
					System.out.println("Voltando ao Menu...");
				}
			}
			menuOuvinte();
			break;
		case 12: 
			System.out.println("=== DELETAR PERFIL ===");
			System.out.println("Apagando o seu perfil, irá deixar de fazer parte");
			System.out.println("da nossa amada comunidade SpotFile.");
			System.out.println("Você tem certeza que deseja deletar sua conta?(S/N)");
			String confirmacaoDeletarConta = scanner.nextLine();
			confirmacaoDeletarConta.toUpperCase();
			if (confirmacaoDeletarConta.matches("S")) {
				System.out.println("Certeza Absoluta??(S/N)");
				confirmacaoDeletarConta = scanner.nextLine();
				confirmacaoDeletarConta.toUpperCase();
				if (confirmacaoDeletarConta.matches("S")) {
					System.out.println("Nunca é um Adeus. Mas sim um até logo.");
					System.out.println("Até Logo, " + ouvinteLogado.getNome());
					System.out.println("Deletando a conta...");
					try {
						ouvinteDAO.excluirPorId(ouvinteLogado);
						System.out.println("Conta excluída...");
						System.out.println("Voltando ao Login");
						ouvinteLogado = null;
					} catch (Exception e) {
						System.out.println("Não foi possível excluir a conta pois: " + e.getMessage());
					}
				} else {

				}
			} else {

			}
			menuOuvinte();
			break;
		case 0:
			System.out.println("Saindo...");
			artistaLogado = null;
			break;
		default:
			System.out.println("Opção Inválida. Tente Novamente.");
			menuOuvinte();
			break;
		}
	}


	public static Artista atualizarArtista(Artista artista) {
		artistaDAO.editar(artista);
		artista = artistaDAO.buscarPorIdArtista(artista.getIdArtista());
		return artista;
	}

	public static Ouvinte atualizarOuvinte(Ouvinte ouvinte) {
		ouvinteDAO.editar(ouvinte);
		ouvinte = ouvinteDAO.buscarPorIdOuvinte(ouvinte.getIdOuvinte());
		return ouvinte;
	}

	public static Album atualizarAlbum(Album album) {
		albumDAO.editar(album);
		album = albumDAO.buscarPorId(album.getIdAlbum());
		return album;
	}

	public static Musica atualizarMusica(Musica musica) {
		musicaDAO.editar(musica);
		musica = musicaDAO.buscarPorId(musica.getIdMusica());
		return musica;
	}

	public static Playlist atualizarPlaylist(Playlist playlist) {
		playlistDAO.editar(playlist);
		playlist = playlistDAO.buscarPlaylistPorId(playlist.getIdPlaylist());
		return playlist;
	}

	public static void acessoArtista(Artista artista) {
		artista = atualizarArtista(artista);
		int opcaoVoltar = 1;
		do {
			if (checarSeSeguidorArtista(artista) == true) {
				System.out.println("=== " + artista.getNome() + " ===");
				System.out.println("  " + artista.getFans().size() + " fãs");
				System.out.println("Você segue esse artista!");
				System.out.println("1) Ver Álbuns");
				System.out.println("2) Ver About");
				System.out.println("3) Deixar de Seguir");
				System.out.println("0) Voltar");
				int opcaoArtista = scanner.nextInt();
				scanner.nextLine();
				switch (opcaoArtista) {
				case 1:
					verAlbuns(artista);
					break;
				case 2:
					System.out.println(artista.getAbout());
					break;
				case 3:
					System.out.println("Deixando de Seguir...");
					try {
						ouvinteDAO.unfollowArtista(ouvinteLogado, artista);
						System.out.println("Sucesso! Você não segue mais esse artista");
					} catch (Exception e) {
						System.out.println("Não foi possível deixar de seguir pois: " + e.getMessage());
					} finally {
						artista = atualizarArtista(artista);
						ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					}
					break;
				case 0:
					opcaoVoltar = 0;
					break;
				default:
					System.out.println("Opção Inválida");
					System.out.println("Tente novamente...");
				}
			} else {
				System.out.println("=== " + artista.getNome() + " ===");

				System.out.println("  " + artista.getFans().size() + " fãs");
				System.out.println("1) Ver Álbuns");
				System.out.println("2) Ver About");
				System.out.println("3) Seguir Artista");
				System.out.println("0) Voltar");
				int opcaoArtista = scanner.nextInt();
				scanner.nextLine();
				switch (opcaoArtista) {
				case 1:
					verAlbuns(artista);
					break;
				case 2:
					System.out.println(artista.getAbout());
					break;
				case 3:
					System.out.println("Seguindo " + artista.getNome() + "...");
					try {
						ouvinteDAO.seguirArtista(ouvinteLogado, artista);
						System.out.println("Seguido com sucesso!");
					} catch (Exception e) {
						System.out.println("Não foi possível seguir pois: " + e.getMessage());
					} finally {
						artista = atualizarArtista(artista);
						ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					}
					break;
				case 0:
					opcaoVoltar = 0;
					break;
				default:
					System.out.println("Opção Inválida");
					System.out.println("Tente novamente...");
				}
			}
		} while (opcaoVoltar != 0);

	}

	public static void acessoOuvinte(Ouvinte ouvinte) {
		ouvinte = atualizarOuvinte(ouvinte);
		int opcaoVoltar = 1;
		do {
			if (checarSeSeguidorOuvinte(ouvinte) == true) {
				System.out.println("=== " + ouvinte.getNome() + " ===");
				System.out
						.println(ouvinte.getFollowing().size() + " seguidos," + ouvinte.getFollowers().size() + " seguidores");
				System.out.println("Você segue esse ouvinte!");
				System.out.println("1) Ver Playlists");
				System.out.println("2) Ver Seguidores");
				System.out.println("3) Ver Seguidos");
				System.out.println("4) Deixar de Seguir");
				System.out.println("0) Voltar");
				int opcaoOuvinte = scanner.nextInt();
				scanner.nextLine();
				switch (opcaoOuvinte) {
				case 1:
					verPlaylists(ouvinte);
					break;
				case 2:
					verSeguidores(ouvinte);
					break;
				case 3:
					verSeguidos(ouvinte);
				case 4:
					System.out.println("Deixando de seguir...");
					try {
						ouvinteDAO.unfollowOuvinte(ouvinteLogado, ouvinte);
						System.out.println("Sucesso! Você não segue mais esse ouvinte");
					} catch (Exception e) {
						System.out.println("Não foi possível deixar de seguir pois: " + e.getMessage());
					} finally {
						ouvinte = atualizarOuvinte(ouvinte);
						ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					}
					break;
				case 0:
					opcaoVoltar = 0;
					break;
				default:
					System.out.println("Opção Inválida");
					System.out.println("Tente novamente...");
				}
			} else {
				System.out.println("=== " + ouvinte.getNome() + " ===");
				System.out
				.println(ouvinte.getFollowing().size() + " seguidos," + ouvinte.getFollowers().size() + " seguidores");
				System.out.println("1) Ver Playlists");
				System.out.println("2) Ver Seguidores");
				System.out.println("3) Ver Seguidos");
				System.out.println("4) Seguir Ouvinte");
				System.out.println("0) Voltar");
				int opcaoOuvinte = scanner.nextInt();
				scanner.nextLine();
				switch (opcaoOuvinte) {
				case 1:
					verPlaylists(ouvinte);
					break;
				case 2:
					verSeguidores(ouvinte);
					break;
				case 3:
					verSeguidos(ouvinte);
				case 4:
					System.out.println("Seguindo...");
					try {
						ouvinteDAO.seguirOuvinte(ouvinteLogado, ouvinte);
						System.out.println("Agora você segue " + ouvinte.getNome());
					} catch (Exception e) {
						System.out.println("Não foi possível deixar de seguir pois: " + e.getMessage());
					} finally {
						ouvinte = atualizarOuvinte(ouvinte);
						ouvinteLogado = atualizarOuvinte(ouvinteLogado);
					}
					break;
				case 0:
					opcaoVoltar = 0;
					break;
				default:
					System.out.println("Opção Inválida");
					System.out.println("Tente novamente...");
				}
			}
		} while (opcaoVoltar != 0);
	}

	public static void acoesSeguidores(Ouvinte seguidor) {
		seguidor = atualizarOuvinte(seguidor);
		System.out.println("=== Perfil de " + seguidor.getNome() + " ===");
		System.out.println(seguidor.getPlaylists().size() + " Playlists");
		System.out.println(seguidor.getFollowers().size() + " Seguidores");
		System.out.println(seguidor.getFollowing().size() + " Seguidos");
		System.out.println("---------------------------------");
		if (checarSeSeguidorOuvinte(seguidor) == false) {
			System.out.println("Vocês são amigos! Seguem-se mutuamente!!");
			System.out.println("1) Deixar de Seguir");
			System.out.println("2) Ver Perfil");
			System.out.println("0) Voltar");
			int voltar = scanner.nextInt();
			scanner.nextLine();
			switch (voltar) {
			case 1:
				System.out.println("Deixando de Seguir...");
				try {
					ouvinteDAO.unfollowOuvinte(seguidor, ouvinteLogado);
					System.out.println("Deixou de Seguir!");
					System.out.println("Voltando...");
				} catch (Exception e) {
					System.out.println("Não foi possível deixar de seguir pois: " + e.getMessage());
				} finally {
					ouvinteLogado = atualizarOuvinte(ouvinteLogado);
				}
				break;
			case 2:
				acessoOuvinte(seguidor);
				break;
			case 0:
				System.out.println("Voltando...");
				break;
			default:
				System.out.println("Opção Inválida");
				acoesSeguidores(seguidor);
			}
		} else {
			System.out.println("1) Seguir de Volta");
			System.out.println("2) Ver Perfil");
			System.out.println("0) Voltar");
			int voltar = scanner.nextInt();
			scanner.nextLine();
			switch (voltar) {
			case 1:
				System.out.println("Seguindo...");
				try {
					ouvinteDAO.seguirOuvinte(ouvinteLogado, seguidor);
					System.out.println("Seguido com sucesso!");
					System.out.println("Já pode aproveitar a sua amizade com " + seguidor.getNome());
				} catch (Exception e) {
					System.out.println("Não foi possível seguir pois: " + e.getMessage());
				} finally {
					ouvinteLogado = atualizarOuvinte(ouvinteLogado);
				}
				break;
			case 2:
				acessoOuvinte(seguidor);
				break;
			case 0:
				System.out.println("Voltando...");
				break;
			default:
				System.out.println("Opção Inválida");
				acoesSeguidores(seguidor);
			}
		}
	}

	public static void verSeguidores(Ouvinte ouvinte) {
		ouvinte = atualizarOuvinte(ouvinte);
		System.out.println("--- SEGUIDORES ---");
		List<Ouvinte> seguidores = ouvinte.getFollowers();
		if (seguidores.isEmpty()) {
			System.out.println(ouvinte.getNome() + " não tem nenhum seguidor!");
			System.out.println("Que tal segui-lo?");
			return;
		}
		for (int i = 0; i < seguidores.size(); i++) {
			System.out.println((i + 1) + ". " + seguidores.get(i).getNome());
		}
		System.out.println("-------------------");
		System.out.println("1) Ver Perfil de Seguidor");
		System.out.println("0) Voltar");
		int voltar = scanner.nextInt();
		scanner.nextLine();
		switch (voltar) {
		case 1:
			System.out.println("Digite o número do seguidor para ver seu perfil:");
			int indexSeguidor = scanner.nextInt();
			scanner.nextLine();
			Ouvinte seguidor = seguidores.get(indexSeguidor);
			acoesSeguidores(seguidor);
			break;
		case 0:
			System.out.println("Voltando...");
			break;
		default:
			System.out.println("Opção Inválida...");
			System.out.println("Tente Novamente");
			verSeguidores(ouvinte);
		}
	}

	public static void acoesSeguido(List<Usuario> seguidos) {
		System.out.println("Digite o número do Seguido para ver o perfil:");
		int indexSeguido = scanner.nextInt();
		scanner.nextLine();
		Usuario seguido = seguidos.get(indexSeguido - 1);
		if (ouvinteOuArtista(seguido) == true) {
			Artista artistaSeguido = artistaDAO.buscarArtistaPorEmail(seguido.getEmail());
			System.out.println("=== Artista " + artistaSeguido.getNome() + " ===");
			System.out.println("  " + artistaSeguido.getFans().size() + " Fãs");
			System.out.println("  " + artistaSeguido.getAlbuns().size() + " Álbuns");
			System.out.println("  " + artistaSeguido.getAbout());
			System.out.println("----------------------------------");
			System.out.println("1) Deixar de Seguir");
			System.out.println("2) Ver Perfil");
			System.out.println("0) Voltar");
			int opcaoArtistaSeguido = scanner.nextInt();
			scanner.nextLine();
			switch (opcaoArtistaSeguido) {
			case 1:
				System.out.println("Deixando de Seguir...");
				try {
					ouvinteDAO.unfollowArtista(ouvinteLogado, artistaSeguido);
					System.out.println("Deixou de Seguir!");
					System.out.println("Voltando...");
				} catch (Exception e) {
					System.out.println("Não foi possível concluir: " + e.getMessage());
				} finally {
					ouvinteLogado = atualizarOuvinte(ouvinteLogado);
				}
				break;
			case 2:
				acessoArtista(artistaSeguido);
				break;
			case 0:
				System.out.println("Voltando...");
				break;
			default:
				System.out.println("Opção Inválida. Tente novamente");
				acoesSeguido(seguidos);
			}
		} else {
			Ouvinte ouvinteSeguido = ouvinteDAO.buscarOuvintePorEmail(seguido.getEmail());
			System.out.println("=== Ouvinte " + ouvinteSeguido.getNome() + " ===");
			System.out.println("  " + ouvinteSeguido.getFollowers().size() + " Seguidores");
			System.out.println("  " + ouvinteSeguido.getFollowing().size() + " Seguindo");
			System.out.println("  " + ouvinteSeguido.getPlaylists().size() + " Playlists");
			System.out.println("----------------------------------");
			System.out.println("1) Deixar de Seguir");
			System.out.println("2) Ver Perfil");
			System.out.println("0) Voltar");
			int opcaoOuvinteSeguido = scanner.nextInt();
			scanner.nextLine();
			switch (opcaoOuvinteSeguido) {
			case 1:
				System.out.println("Deixando de Seguir...");
				try {
					ouvinteDAO.unfollowOuvinte(ouvinteLogado, ouvinteSeguido);
					System.out.println("Deixou de Seguir!");
					System.out.println("Voltando...");
				} catch (Exception e) {
					System.out.println("Não foi possível concluir: " + e.getMessage());
				} finally {
					ouvinteLogado = atualizarOuvinte(ouvinteLogado);
				}
				break;
			case 0:
				System.out.println("Voltando...");
				break;
			default:
				System.out.println("Opção Inválida. Tente novamente");
				acoesSeguido(seguidos);
			}
		}
	}

	public static void verSeguidos(Ouvinte ouvinte) {
		ouvinte = atualizarOuvinte(ouvinte);
		System.out.println("--- SEGUIDOS ---");
		List<Usuario> seguidos = ouvinte.getFollowing();
		for (int i = 0; i < seguidos.size(); i++) {
			System.out.println((i + 1) + ". " + seguidos.get(i).getNome());
		}
		System.out.println("-----------------");
		System.out.println("1) Ver Perfil de Seguido");
		System.out.println("0) Voltar");
		int opcaoSeguido = scanner.nextInt();
		scanner.nextLine();
		switch (opcaoSeguido) {
		case 1:
			if (ouvinte.getIdOuvinte() == ouvinteLogado.getIdOuvinte()) {
				acoesSeguido(seguidos);
			} else {
				System.out.println("Digite o número do seguido para ver seu perfil:");
				int verSeguido = scanner.nextInt();
				scanner.nextLine();
				if(verSeguido<0||verSeguido>seguidos.size()) {
					
				}else {
					Usuario seguido = seguidos.get(verSeguido-1);
					if(ouvinteOuArtista(seguido)) {
						Artista artistaSeguido = artistaDAO.buscarArtistaPorEmail(seguido.getEmail());
						acessoArtista(artistaSeguido);
					}else {

						Ouvinte ouvinteSeguido = ouvinteDAO.buscarOuvintePorEmail(seguido.getEmail());
						acessoOuvinte(ouvinteSeguido);
					}
				}
			}
			break;
		case 0:
			System.out.println("Voltando...");
			break;
		default:
			System.out.println("Opção Inválida...");
			System.out.println("Tente Novamente.");
			verSeguidos(ouvinte);
		}
	}

	public static void acessoAlbum(Album album) {
		album = atualizarAlbum(album);
		System.out.println("--- " + album.getNome() + " ---");
		System.out.println("Artista: " + artistaDAO.buscarPorIdArtista(album.getIdArtista()).getNome());
		System.out.println(album.printarInfos());
		System.out.println("Músicas:");
		verMusicas(album);
		System.out.println("Digite o número indicado para:");
		System.out.println("1)Salvar álbum");
		System.out.println("2)Salvar música em playlist");
		System.out.println("3)Ver Artista");
		System.out.println("0)Voltar");
		int escolhaDentroDeAlbum = scanner.nextInt();
		scanner.nextLine();


		switch (escolhaDentroDeAlbum) {

		case 1: 
			System.out.println("Salvando álbum, " + album.getNome());
			Playlist albumSalvo = new Playlist();
			List<Musica> musicasAlbumSalvo = album.getMusicas();
			albumSalvo.setNome(album.getNome());
			albumSalvo.setFotoDaCapaURL(album.getFotoDaCapaURL());
			albumSalvo.setTempoStreaming(album.getTempoStreaming());
			albumSalvo.setIdOuvinte(ouvinteLogado.getIdOuvinte());
			albumSalvo.setMusicas(musicasAlbumSalvo);
			try {
				playlistDAO.salvar(albumSalvo); 
				playlistDAO.vincularMusicas(albumSalvo);
				System.out.println("Álbum salvo com sucesso!");
			} catch (Exception e) {
				System.out.println("Erro ao cadastrar álbum: " + e.getMessage());
			}
			atualizarPlaylist(albumSalvo);
			ouvinteLogado = atualizarOuvinte(ouvinteLogado);
			acessoAlbum(album);
			break;

		case 2: 
			Playlist playlistProcurada = null;
			int escolhaMusica;
			System.out.println("Escolha uma música! Digite o número indicado(Para voltar digite 0): ");
			escolhaMusica = scanner.nextInt();
			scanner.nextLine();
			Musica musicaEscolhida = album.getMusicas().get(escolhaMusica - 1);
			if (escolhaMusica == 0) {
				System.out.println("Voltando...");
				menuOuvinte();
			}
			int escolhaPlaylist;
			System.out.println("Indique a playlist em que quer salvar a música:");
			System.out.println("Digite o número indicado para:");
			System.out.println("1)Listar nomes playlist:");
			System.out.println("2)Criar nova playlist: ");
			int indicacaoDeBusca = scanner.nextInt();
			scanner.nextLine();
			switch (indicacaoDeBusca) {

			case 1: 
				List<Playlist> playlistsOuvinte = ouvinteLogado.getPlaylists();
				if (playlistsOuvinte.isEmpty()) {
					System.out.println("No momento você não tem nenhuma playlist!");
					System.out.println("Aproveite para criar uma!");
					break;
				} else if (playlistsOuvinte.size() <= 5) {
					for (int i = 0; i < ouvinteLogado.getPlaylists().size(); i++) {
						System.out.println(i + 1 + ". " + playlistsOuvinte.get(i).getNome());
					}
				} else {
					for (int i = 0; i < playlistsOuvinte.size(); i++) {
						System.out.println(i + 1 + ". " + playlistsOuvinte.get(i).getNome());
					}
					System.out.println("Mostrar todas as playlists? S/N"); 
					String showAllPlaylist = scanner.nextLine();
					if (showAllPlaylist.matches("S")) {
						for (int i = 0; i < playlistsOuvinte.size(); i++) {
							System.out.println(i + 1 + ". " + playlistsOuvinte.get(i).getNome());
						}
					}
				}
				System.out.println(
						"Escolha a playlist!" + " Digite o número indicado: (Para playlist de favoritas digite 0)");
				escolhaPlaylist = scanner.nextInt();
				scanner.nextLine();
				if (escolhaPlaylist == 0) {
					playlistProcurada = ouvinteLogado.getPlaylistFavoritas();
				} else {
					playlistProcurada = ouvinteLogado.getPlaylists().get(escolhaPlaylist - 1);
				}
				System.out.println("Salvando música, " + musicaEscolhida.getNome() + ", na playlist "
						+ playlistProcurada.getNome());

				playlistProcurada.getMusicas().add(musicaEscolhida);
				try {
					playlistDAO.adicionarMusica(musicaEscolhida, playlistProcurada);
					System.out.println("Música adicionada com sucesso!");
				} catch (Exception e) {
					System.out.println("Erro ao salvar música: " + e.getMessage());
				}
				atualizarPlaylist(playlistProcurada);
				ouvinteLogado = atualizarOuvinte(ouvinteLogado);
				acessoAlbum(album);
				break;

			case 2: 
				Playlist playlist = new Playlist();
				playlist.setNome(musicaEscolhida.getNome());
				List<Musica> musicaPlaylist = new ArrayList<Musica>();
				musicaPlaylist.add(musicaEscolhida);
				playlist.setMusicas(musicaPlaylist);
				playlist.setIdOuvinte(ouvinteLogado.getIdOuvinte());
				playlist.setTempoStreaming(musicaEscolhida.getDuracaoMusica());
				playlist.setFotoDaCapaURL(albumDAO.buscarPorId(musicaEscolhida.getIdAlbum()).getFotoDaCapaURL());

				try {
					playlistDAO.salvar(playlist); 
					playlistDAO.vincularMusicas(playlist); 

					System.out.println("Playlist criada com sucesso!");
				} catch (Exception e) {
					System.out.println("Erro ao criar playlist: " + e.getMessage());
				}
				// ATUALIZA MEMÓRIA
				atualizarPlaylist(playlist);
				ouvinteLogado = atualizarOuvinte(ouvinteLogado);
				break;
			default:
				System.out.println("Escolha inválida. Voltando...");
			}

			break;

		case 3: 
			acessoArtista(artistaDAO.buscarPorIdArtista(album.getIdArtista()));
			break;
		case 0: 
			System.out.println("Voltando ao menu... ");
			break;
		default:
			System.out.println("Escolha inválida!");
			System.out.println("Voltando...");
			acessoAlbum(album);
			break;

		}
	}

	public static void verAlbuns(Artista artista) {
		artista = atualizarArtista(artista);
		System.out.println("--- ÁLBUNS DE " + artista.getNome() + " ---");
		List<Album> albuns = artista.getAlbuns();
		if (albuns.size() <= 5) {
			for (int i = 0; i < albuns.size(); i++) {
				System.out.println((i + 1) + ". " + albuns.get(i).getNome());
				System.out.println(" " + albuns.get(i).printarInfos());
				System.out.println("");
			}
			System.out.println("--------------------------------------");
			System.out.println("Digite o número do álbum para vizualizá-lo (0 para voltar):");
			int opcaoAlbum = scanner.nextInt();
			scanner.nextLine();
			if (opcaoAlbum == 0) {
				return;
			} else {
				Album albumEscolhido = albuns.get(opcaoAlbum - 1);
				acessoAlbum(albumEscolhido);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				System.out.println((i + 1) + ". " + albuns.get(i).getNome());
				System.out.println(" " + albuns.get(i).printarInfos());
				System.out.println("");
			}
			System.out.println("--------------------------------------");
			System.out.println("Deseja ver a lista completa de álbuns?(S/N)");
			String listaCompleta = scanner.nextLine();
			if (listaCompleta.matches("S")) {
				System.out.println("--- LISTA COMPLETA DE ÁLBUNS ---");
				for (int i = 0; i < albuns.size(); i++) {
					System.out.println((i + 1) + ". " + albuns.get(i).getNome());
					System.out.println(" " + albuns.get(i).printarInfos());
					System.out.println("");
				}
				System.out.println("--------------------------------------");
				System.out.println("Digite o número do álbum para vizualizá-lo (0 para voltar):");
				int opcaoAlbum = scanner.nextInt();
				scanner.nextLine();
				if (opcaoAlbum == 0) {
					return;
				} else {
					Album albumEscolhido = albuns.get(opcaoAlbum - 1);
					acessoAlbum(albumEscolhido);
				}
			}
		}
	}

	public static void acessoMusica(Musica musica) {
		musica = atualizarMusica(musica);
		System.out.println("=== " + musica.getNome() + " ===");
		System.out.println("Duração: " + musica.printarInfos());
		System.out.println("1) Ver Albúm");
		System.out.println("2) Ver Artista");
		System.out.println("3) Salvar Música");
		System.out.println("0) Voltar");
		int opcaoMusica = scanner.nextInt();
		scanner.nextLine();
		switch (opcaoMusica) {
		case 1:
			Album albumDaMusica = albumDAO.buscarPorId(musica.getIdAlbum());
			acessoAlbum(albumDaMusica);
			break;
		case 2:
			Album albumMusica = albumDAO.buscarPorId(musica.getIdAlbum());
			Artista artistaDaMusica = artistaDAO.buscarPorIdArtista(albumMusica.getIdArtista());
			acessoArtista(artistaDaMusica);
			break;
		case 3:
			adicionarMusica(musica);
			break;
		case 0:
			System.out.println("Voltando...");
			break;
		default:
			System.out.println("Opção Inválida...");
			System.out.println("Tente Novamente.");
		}
	}

	public static void verMusicas(Album album) {
		album = atualizarAlbum(album);
		for (int i = 0; i < album.getMusicas().size(); i++) {
			System.out.println((i + 1) + ". " + album.getMusicas().get(i).getNome());
		}
	}

	public static void adicionarMusica(Musica musica) {
		ouvinteLogado = atualizarOuvinte(ouvinteLogado);
		System.out.println("=== SUAS PLAYLISTS ===");
		List<Playlist> playlists = ouvinteLogado.getPlaylists();
		for (int i = 0; i < ouvinteLogado.getPlaylists().size(); i++) {
			System.out.println((i + 1) + ". " + playlists.get(i).getNome());
		}
		System.out.println("----------------------");
		System.out.println("Digite o número da playlist em que deseja salvar a música:");
		int indexPlaylist = scanner.nextInt();
		scanner.nextLine();
		if (indexPlaylist < 0 || indexPlaylist > playlists.size()) {
			System.out.println("Opção Inválida!");
			System.out.println("Tente Novamente");
			adicionarMusica(musica);
		} else {
			Playlist playlistEscolhida = playlists.get(indexPlaylist - 1);
			System.out.println("Salvando em " + playlistEscolhida.getNome() + "...");
			try {
				playlistDAO.adicionarMusica(musica, playlistEscolhida);
				System.out.println("Música Salva com Sucesso!");
			} catch (Exception e) {
				System.out.println("Não foi possível salvar pois: " + e.getMessage());
			}
			atualizarPlaylist(playlistEscolhida);
			ouvinteLogado = atualizarOuvinte(ouvinteLogado);
		}
	}

	public static void acessoPlaylist(Playlist playlist) {
		playlist = atualizarPlaylist(playlist);
		System.out.println("--- " + playlist.getNome() + " ---"); 
		System.out.println(playlist.printarInfos());
		System.out.println("Criador: " + ouvinteDAO.buscarPorIdOuvinte(playlist.getIdOuvinte()).getNome());
		System.out.println("Bio: " + playlist.getBio());
		System.out.println("Músicas: ");
		for (int i = 0; i < playlist.getMusicas().size(); i++) {
			Musica musica = playlist.getMusicas().get(i);
			System.out.println("  " + musica.getNome());
			System.out.println("     " + albumDAO.buscarPorId(musica.getIdAlbum()).getNome());
		}

		String tamanhoNomePlaylist = "";
		for (int i = 0; i < playlist.getNome().length(); i++) {
			tamanhoNomePlaylist += "-";
		}
		System.out.println("\n----" + tamanhoNomePlaylist + "----");
		System.out.println("1) Ver Ouvinte");
		System.out.println("2) Selecionar Música");
		System.out.println("0) Voltar");
		int opcaoPlaylist = scanner.nextInt();
		scanner.nextLine();
		switch (opcaoPlaylist) {
		case 1:
			Ouvinte ouvinte = ouvinteDAO.buscarPorIdOuvinte(playlist.getIdOuvinte());
			acessoOuvinte(ouvinte);
			break;
		case 2:
			System.out.println("Digite o número da música para selecionar: ");
			int selecaoMusica = scanner.nextInt();
			scanner.nextLine();
			if (selecaoMusica < 0 || selecaoMusica > playlist.getMusicas().size()) {
				System.out.println("Opção Inválida...");
				System.out.println("Tente novamente.");
				acessoPlaylist(playlist);
			} else {
				Musica musica = playlist.getMusicas().get(selecaoMusica - 1);
				acessoMusica(musica);
			}
			break;
		case 0:
			System.out.println("Voltando...");
			break;
		default:
			System.out.println("Opção Inválida...");
			System.out.println("Tente Novamente..");
		}

	}

	public static void acessoPlaylistOuvinteLogado(Playlist playlist) {
		playlist = atualizarPlaylist(playlist);
		int opcaoVoltarVizualizacao = 1;
		do {
			System.out.println("--- " + playlist.getNome() + " ---"); 
			System.out.println(playlist.printarInfos());
			System.out.println("Criador: " + ouvinteDAO.buscarPorIdOuvinte(playlist.getIdOuvinte()).getNome());
			System.out.println("Bio: " + playlist.getBio());
			System.out.println("Músicas: ");
			for (Musica musica : playlist.getMusicas()) {
				System.out.println("  " + musica.getNome());
				System.out.println("     " + albumDAO.buscarPorId(musica.getIdAlbum()).getNome());
			}

			String tamanhoNomePlaylist = "";
			for (int i = 0; i < playlist.getNome().length(); i++) {
				tamanhoNomePlaylist += "-";
			}
			System.out.println("\n----" + tamanhoNomePlaylist + "----");
			System.out.println("Digite qualquer coisa para voltar");
			scanner.nextLine();
			opcaoVoltarVizualizacao = 0;
		} while (opcaoVoltarVizualizacao != 0);
	}

	public static void verPlaylists(Ouvinte ouvinte) {
		ouvinte = atualizarOuvinte(ouvinte);
		int opcaoVoltar = 1;
		do {
			System.out.println("=== BIBLIOTECA  DE " + ouvinte.getNome() + " ===");
			List<Playlist> bibliotecaOuvinte = ouvinte.getPlaylists(); 
			if ((bibliotecaOuvinte.isEmpty() || bibliotecaOuvinte == null)
					&& ouvinteLogado.getIdOuvinte() == ouvinte.getIdOuvinte()) { 
				System.out.println("No momento não tens nenhuma playlist");
				System.out.println("Aproveite a oportunidade para criar uma!");
				System.out.println("Voltando ao Menu...");
				opcaoVoltar = 0;
			} else {
				if (bibliotecaOuvinte.size() <= 5) {
					for (int i = 0; i < bibliotecaOuvinte.size(); i++) {
						Playlist playlistBiblioteca = bibliotecaOuvinte.get(i);
						System.out.println((i + 1) + ". " + playlistBiblioteca.getNome());
						System.out.println("   " + playlistBiblioteca.printarInfos());
					}
					System.out.println("\n---------------------------");
					System.out.println("\n1) Ver Playlist");
					System.out.println("0) Voltar");
					int optionBiblioteca = scanner.nextInt();
					scanner.nextLine();
					switch (optionBiblioteca) {
					case 1:
						System.out.println("Digite o número da playlist que deseja vizualizar: ");
						int selecaoPlaylist = scanner.nextInt();
						scanner.nextLine();
						Playlist playlist = bibliotecaOuvinte.get(selecaoPlaylist - 1);

						if (ouvinteLogado.getIdOuvinte() == playlist.getIdOuvinte()) {
							acessoPlaylistOuvinteLogado(playlist);
						} else {
							acessoPlaylist(playlist);
						}
						break;
					case 0:
						opcaoVoltar = 0;
						break;
					default:
						System.out.println("Opção Inválida");
						System.out.println("Tente novamente...");
					}
				} else {
					for (int i = 0; i < 5; i++) {
						Playlist playlistBiblioteca = bibliotecaOuvinte.get(i);
						System.out.println((i + 1) + ". " + playlistBiblioteca.getNome());
						System.out.println("   " + playlistBiblioteca.printarInfos());
					}
					System.out.println("---------------------------");
					System.out.println("\1) Mostrar todas as playlists");
					System.out.println("2) Ver Playlist");
					System.out.println("0) Voltar");
					int optionBiblioteca = scanner.nextInt();
					scanner.nextLine();
					switch (optionBiblioteca) {
					case 1:
						System.out.println("=== BIBLIOTECA COMPLETA ==="); 
						for (int i = 0; i < bibliotecaOuvinte.size(); i++) {
							Playlist playlistBiblioteca = bibliotecaOuvinte.get(i);
							System.out.println((i + 1) + ". " + playlistBiblioteca.getNome());
							System.out.println("   " + playlistBiblioteca.printarInfos());
						}
						System.out.println("---------------------------");
						System.out.println("\n1) Ver Playlist");
						System.out.println("0) Voltar");
						int opcaoBibliotecaCompleta = scanner.nextInt();
						scanner.nextLine();
						switch (opcaoBibliotecaCompleta) {
						case 1:
							System.out.println("Digite o número da playlist que deseja vizualizar: ");
							int selecaoPlaylist = scanner.nextInt();
							scanner.nextLine();
							Playlist playlist = bibliotecaOuvinte.get(selecaoPlaylist - 1);
							if (ouvinteLogado.getIdOuvinte() == playlist.getIdOuvinte()) {
								acessoPlaylistOuvinteLogado(playlist);
							} else {
								acessoPlaylist(playlist);
							}
							break;
						case 0:
							System.out.println("Voltando...");
							break;
						default:
							System.out.println("Opção Inválida");
							System.out.println("Tente novamente...");
						}
						break;
					case 2:
						System.out.println("Digite o número da playlist que deseja vizualizar: ");
						int selecaoPlaylist = scanner.nextInt();
						scanner.nextLine();
						Playlist playlist = bibliotecaOuvinte.get(selecaoPlaylist - 1);
						if (ouvinteLogado.getIdOuvinte() == playlist.getIdOuvinte()) {
							acessoPlaylistOuvinteLogado(playlist);
						} else {
							acessoPlaylist(playlist);
						}
						break;
					case 0:
						opcaoVoltar = 0;
						break;
					default:
						System.out.println("Opção Inválida");
					}
				}
			}
		} while (opcaoVoltar != 0);
	}

	public static void editarPlaylist(Playlist playlistEditar) {
		int escolhaEdicaoPlaylist = 10;
		do {
			System.out.println("Insira o que desejas editar em em sua playlist, " + playlistEditar.getNome());
			System.out.println("1)Nome");
			System.out.println("2)Bio");
			System.out.println("3)Foto da capa");
			System.out.println("4)Adicionar músicas");
			System.out.println("0)Voltar");
			escolhaEdicaoPlaylist = scanner.nextInt();
			scanner.nextLine();
			switch (escolhaEdicaoPlaylist) {
			case 1:
				System.out.println("Nome atual: " + playlistEditar.getNome());
				System.out.println("Nome novo: ");
				String nomeNovo = scanner.next();
				scanner.nextLine();
				playlistEditar.setNome(nomeNovo);
				playlistDAO.editar(playlistEditar);
				break;
			case 2:
				System.out.println("Bio atual: " + playlistEditar.getBio());
				System.out.println("Bio nova: ");
				String bioNova = scanner.next();
				scanner.nextLine();
				playlistEditar.setBio(bioNova);
				playlistDAO.editar(playlistEditar);
				break;
			case 3:
				System.out.println("Foto atual: " + playlistEditar.getFotoDaCapaURL());
				System.out.println("Foto nova: ");
				String fotoNova = scanner.next();
				scanner.nextLine();
				playlistEditar.setFotoDaCapaURL(fotoNova);
				playlistDAO.editar(playlistEditar);
				break;
			case 4:
				System.out.println("=== Músicas Favoritas ===");
				Playlist playlistFavs = ouvinteLogado.getPlaylistFavoritas();
				for (int i = 0; i < playlistFavs.getMusicas().size(); i++) {
					System.out.println((i + 1) + ". " + playlistFavs.getMusicas().get(i).getNome());
					System.out.println(artistaDAO
							.buscarPorIdArtista(
									albumDAO.buscarPorId(playlistFavs.getMusicas().get(i).getIdAlbum()).getIdArtista())
							.getNome());
					System.out.println(albumDAO.buscarPorId(playlistFavs.getMusicas().get(i).getIdAlbum()).getNome());
				}
				System.out.println("----------------------");
				System.out.println("Digite o número da música em que deseja salvar na playlist:");
				int escolhaMusica = scanner.nextInt();
				scanner.nextLine();
				Musica musicaEscolhida = playlistFavs.getMusicas().get(escolhaMusica - 1);

				playlistEditar.getMusicas().add(musicaEscolhida);

				try {
					playlistDAO.adicionarMusica(musicaEscolhida, playlistEditar);
					System.out.println("Música adicionada com sucesso!");

				} catch (Exception e) {
					System.out.println("Erro ao salvar música: " + e.getMessage());
				}
				atualizarPlaylist(playlistEditar);
				ouvinteLogado = atualizarOuvinte(ouvinteLogado);

				break;

			case 0:
				System.out.println("Voltando...");
				break;
			default:
				System.out.println("Opção Inválida!");
				System.out.println("Voltando à Playlist...");

				break;
			}
		} while (escolhaEdicaoPlaylist != 0);
	}

	public static boolean ouvinteOuArtista(Usuario usuario) {
		List<Artista> artistas = artistaDAO.buscarListaArtistas();
		boolean oOuA = false;
		for (Artista artista : artistas) {
			if (artista.getEmail().equals(usuario.getEmail())) {
				oOuA = true;
				break;
			}
		}
		return oOuA;

	}

	public static boolean checarSeSeguidorOuvinte(Ouvinte seguidor) {
		List<Usuario> following = ouvinteLogado.getFollowing();
		for (int i = 0; i < following.size(); i++) {
			if (seguidor.getEmail().equals(following.get(i).getEmail())) {
				return true;
			}
		}
		return false;
	}

	public static boolean checarSeSeguidorArtista(Artista artista) {
		List<Ouvinte> fans = artista.getFans();
		for (int i = 0; i < fans.size(); i++) {
			if (ouvinteLogado.getIdOuvinte() == fans.get(i).getIdOuvinte()) {
				return true;
			}
		}
		return false;
	}

}