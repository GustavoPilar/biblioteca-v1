package model.entities.dao;

import java.util.List;

import model.entities.Livro;
import model.entities.Pessoa;

public interface LivroDao {
	
	void insert(Livro livro); // INSERIR LIVROS
	void updateDisponivel(Livro livro, Pessoa pessoa); // ATUALIZAR PARA DÍSPONÍVEL OU NÃO DÍSPONIVEL
	void updateEmprestimo(Livro livro, Pessoa pessoa);
	void delete(Livro livro); // DELETAR POR ID
	Livro findById(Integer integer); // ENCONTRAR LIVRO PELO ID
	List<Livro> findAll(); // ENCONTRAR TODOS OS LIVROS

}
