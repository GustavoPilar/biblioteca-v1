package model.entities.dao;

import java.util.List;

import model.entities.Livro;
import model.entities.Pessoa;

public interface PessoaDao {
	
	void insert(Pessoa pessoa); // INSERIR LIVROS
	void update(Pessoa pessoa, String objAtualizar, String msgAtualizar); // ATUALIZAR PARA DÍSPONÍVEL OU NÃO DÍSPONIVEL
	void delete(Integer id); // DELETAR POR ID (APENAS PARA NÍVEL 1 - BIBLIOTECÁRIO)
	Pessoa findUser(String password, String email); // ENCONTAR UM USUARIO PELA SENHA E EMAIL
	boolean findByEmail(String email); // VERIFICAR SE EXISTE UM USUARIO COM O EMAIL
	List<Livro> findAll(Pessoa pessoa); // ENCONTRAR TODOS OS USUARIOS

}
