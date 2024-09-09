package model.entities.dao;

import java.util.List;

import model.entities.Livro;
import model.entities.Pessoa;

public interface PessoaDao {
	
	void insert(); // INSERIR LIVROS
	void update(); // ATUALIZAR PARA DÍSPONÍVEL OU NÃO DÍSPONIVEL
	void delete(); // DELETAR POR ID (APENAS PARA NÍVEL 1 - BIBLIOTECÁRIO)
	Pessoa findUser(String password); // ENCONTAR UM USUARIO PELO SEU ID
	List<Livro> findAll(Pessoa pessoa); // ENCONTRAR TODOS OS USUARIOS

}
