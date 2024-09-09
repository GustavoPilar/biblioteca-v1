package model.entities;

public final class Livro {

	private Integer id;
	private String titulo;
	private String descricao;
	private Integer anoPublicacao;
	private Boolean disponivel;
	
	public Livro() {
		
	}
	
	public Livro(String titulo, String descricao, Integer anoPublicacao, Boolean disponivel) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.anoPublicacao = anoPublicacao;
		this.disponivel = disponivel;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(Integer anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public Boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Id: ");
		sb.append(getId());
		sb.append("\nTitulo: ");
		sb.append(getTitulo());
		sb.append("\nAno de publicação: ");
		sb.append(getAnoPublicacao());
		sb.append("\nDisponível: ");
		
		if (getDisponivel()) {
			sb.append("Sim");
		}
		else {
			sb.append("Não");
		}
		
		sb.append("\nDescrição:\n");
		sb.append(getDescricao());
		sb.append("\n");
		
		return sb.toString();
	}
}
