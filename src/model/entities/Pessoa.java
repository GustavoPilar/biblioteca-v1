package model.entities;

import java.util.ArrayList;
import java.util.List;

public final class Pessoa {

	private Integer id;
	private String nome;
	private String senha;
	private String email;
	private Integer nivel;
	
	private List<Livro> livrosEmprestados = new ArrayList<>();
	
	public Pessoa() {
		
	}
	
	public Pessoa(String nome, String senha, String email) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.nivel = 2;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}


	public List<Livro> getLivrosEmprestados() {
		return livrosEmprestados;
	}

	public void setLivrosEmprestados(List<Livro> livrosEmprestados) {
		this.livrosEmprestados = livrosEmprestados;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Nome: ");
		sb.append(getNome());
		sb.append("\n");
		sb.append("E-mail: ");
		sb.append(getEmail());
		sb.append("\n");		
		
		return sb.toString();
	}
	
}
