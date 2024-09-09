package application;

import java.util.List;

import model.entities.Livro;
import model.entities.Pessoa;
import model.entities.dao.DaoFactory;
import model.entities.dao.LivroDao;
import model.entities.dao.PessoaDao;

public final class UI {
	
	private static PessoaDao pessoaDao = DaoFactory.criarPessoaDao();
	private static LivroDao livroDao = DaoFactory.criarLivroDao();

	public static void init() {
		int opcao;
		
		do {
			System.out.println("|-----------|");
			System.out.println("[1] - Login");
			System.out.println("[2] - Cadastro");
			System.out.println("[3] - Sair");
			System.out.println("|-----------|");
			opcao = conferirEscolha(1, 3);

			switch (opcao) {
				case 1 -> login();
				case 2 -> cadastrar();
				default -> System.out.println("Saindo...");
			}
			
		} while (opcao != 3);
	}
	
	public static void login() {
		System.out.print("E-mail: ");
		String email = Main.sc.nextLine();
		System.out.print("Senha: ");
		String senha = Main.sc.nextLine();
		
		Pessoa pessoa = conferirUsuario(email, senha);
		
		if (pessoa != null) {
			menu(pessoa);
		}
		else {
			int opcao;
			
			System.out.println("[1] - Cadastrar ");
			System.out.println("[2] - Voltar");
			opcao = conferirEscolha(1, 2);
			
			switch (opcao) {
				case 1 -> cadastrar();
				default -> System.out.println("Voltando...");
			}
		}
	}
	
	public static void cadastrar() {
		Pessoa pessoa = new Pessoa();
		System.out.print("Usuario: ");
		pessoa.setNome(Main.sc.nextLine());
		System.out.print("Senha: ");
		pessoa.setSenha(conferirSenha());
		System.out.print("E-mail: ");
		pessoa.setEmail(conferirEmail());
		
		pessoaDao.insert(pessoa);
		menu(pessoa);
	}
	
	public static String conferirSenha() {
		String senha = Main.sc.nextLine();
		
		while (senha.length() < 8 || senha.length() > 15) {
			System.out.print("A senha deverá conter entre 8 a 15 caracteres.\nDigite novamente: ");
			senha = Main.sc.nextLine();
		}
		
		return senha;
	}
	
	public static String conferirEmail() {
		String email = Main.sc.next();
		Main.sc.nextLine();
		
		while (pessoaDao.findByEmail(email)) {
			System.out.print("Email já cadastrado. Digite novamente: ");
			email = Main.sc.next();
			Main.sc.nextLine();
		}
		
		return email;
	}
	
	public static Pessoa conferirUsuario(String email, String senha) {
		
		if (!pessoaDao.findByEmail(email)) {
			System.out.println("E-mail não encontrado.");
			return null;
		}
		
		Pessoa pessoa = pessoaDao.findUser(senha, email);
		
		while (pessoa == null) {
			System.out.print("Senha incorreta. Digite novamente: ");
			senha = Main.sc.nextLine();
			pessoa = pessoaDao.findUser(senha, email);
		}
		
		return pessoa;
	}
	
	public static void menu(Pessoa pessoa) {
		int opcao;
		
		do {
			System.out.println("|-----------|");
			System.out.println("[1] - Biblioteca");
			System.out.println("[2] - Ver meu livros");
			System.out.println("[3] - Sair");
			System.out.println("|-----------|");
			opcao = conferirEscolha(1, 3);
			
			switch (opcao) {
				case 1:
					biblioteca(pessoa);
					break;
				case 2: 
					verMeusLivros(pessoa);
					break;
				default:
					opcao = voltar(1, 3);
			}
			
		} while (opcao != 3);
	}
	
	public static void biblioteca(Pessoa pessoa) {
		int opcao;
		
		do {
			livroDao.findAll();
			verLista(livroDao.findAll());
			System.out.println("|-------------|");
			System.out.println("[1] - Ver livro");
			System.out.println("[2] - Voltar");
			opcao = conferirEscolha(1, 2);
			
			switch (opcao) {
				case 1:
					verLivro(livroDao.findAll(), "Emprestar", pessoa);
					break;					
				default: 
					System.out.println("Voltando...");
			}
			
		} while (opcao != 2);
	}
	
	public static void verLista(List<Livro> lista) {
		
		if (lista.isEmpty()) {
			System.out.println("Nenhum livro disponível no momento.");
		}
		else {
			StringBuilder sb = new StringBuilder();
			for (Livro l : lista) {
				
				sb.append("[");
				sb.append(lista.indexOf(l) + 1);
				sb.append("] - ");
				sb.append(l.getTitulo());
				sb.append(" (");
				sb.append(l.getAnoPublicacao());
				sb.append(").");
				sb.append("\n");
			}
			
			System.out.println(sb.toString());
		}
	}
	
	public static void verMeusLivros(Pessoa pessoa) {
		int opcao;
		
		do {
			pessoa.setLivrosEmprestados(pessoaDao.findAll(pessoa));
			verLista(pessoa.getLivrosEmprestados());
			System.out.println("|-------------|");
			System.out.println("[1] - Ver livro");
			System.out.println("[2] - Voltar");
			opcao = conferirEscolha(1, 2);
			
			switch (opcao) {
				case 1:
					verLivro(pessoa.getLivrosEmprestados(), "Devolver", pessoa);
					break;
				default: 
					System.out.println("Voltando.");
			}
			
		} while (opcao != 2);
	}
	
	public static Livro escolherPosicaoLivro(List<Livro> lista) {
		System.out.print("Posição do livro. ");
		int pos = conferirEscolha(1, lista.size());
		return lista.get(pos - 1);
	}
	
	public static void verLivro(List<Livro> livros, String emprestarDevolver, Pessoa pessoa) {
		
		if (livros.isEmpty()) {
			System.out.println("Nenhum livro dísponivel");
			return;
		}
		
		int opcao;
		Livro livro = escolherPosicaoLivro(livros);
		System.out.println(livro);
		System.out.println("|-------------|");
		System.out.println("[1] - " + emprestarDevolver);
		System.out.println("[2] - Voltar");
		opcao = conferirEscolha(1, 2);
		
		switch (opcao) {
			case 1: 
				emprestarDevolver(livro, emprestarDevolver, pessoa);
				break;
			default: 
				System.out.println("Voltando");
		}
	}
	
	public static void emprestarDevolver(Livro livro, String emprestarDevolver, Pessoa pessoa) {
		int opcao;
		
		System.out.println(livro);
		System.out.println("Deseja " + emprestarDevolver + "?");
		System.out.println("|-------------|");
		System.out.println("[1] - Sim");
		System.out.println("[2] - Não");
		opcao = conferirEscolha(1, 2);
		
		switch (opcao) {
		case 1:
			livroDao.updateDisponivel(livro, pessoa);
			break;
		default:
			System.out.println("Voltando...");		
		}
	}
	
	public static void teste() {
		System.out.println("Está funcionando. :)");
	}
	
	public static int conferirEscolha(int min, int max) {
		System.out.print("Escolha: ");
		int escolha = Main.sc.nextInt();
		Main.sc.nextLine();
		
		while (escolha < min || escolha > max) {
			System.out.print("Valor inválido. Escolha uma opção escolhida: ");
			escolha = Main.sc.nextInt();
			Main.sc.nextLine();
		}
		
		return escolha;
	}
	
	public static int voltar(int opcao, int max) {
		System.out.println("|-------------|");
		System.out.println("Tem certeza que deseja mesmo voltar?");
		System.out.println("[1] - Sim");
		System.out.println("[2] - Não");
		opcao = conferirEscolha(1, 2);
		
		if (opcao == 1) {
			System.out.println("Voltando...");
			return max;
		}
		else {
			return (max + 1);
		}
	}
}
