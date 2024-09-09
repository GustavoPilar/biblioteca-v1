package model.entities.dao.empl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DB;
import database.DbException;
import model.entities.Livro;
import model.entities.Pessoa;
import model.entities.dao.PessoaDao;

public final class PessoaDaoJDBC implements PessoaDao {
	
	private Connection conn = null;

	public PessoaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert() {
		
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pessoa findUser(String senha) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM usuario WHERE senha = ?");
			
			st.setInt(1, Integer.parseInt(senha));
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				Pessoa pessoa = instanciarPessoa(rs);
				return pessoa;
			}
			
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	@Override
	public List<Livro> findAll(Pessoa pessoa) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT l.* "
					+ "FROM livro l "
					+ "JOIN livroEmprestado le ON l.Id = le.Id_Livro "
					+ "JOIN usuario u ON le.Id_Usuario = u.Id "
					+ "WHERE u.Id = ?");
			
			st.setInt(1, pessoa.getId());
			rs = st.executeQuery();
			
			List<Livro> livros = new ArrayList<>();
			
			while (rs.next()) {
				livros.add(instanciarLivro(rs));
			}
			
			return livros;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Pessoa instanciarPessoa(ResultSet rs) throws SQLException {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(rs.getInt("Id"));
		pessoa.setNome(rs.getString("Nome"));
		pessoa.setEmail(rs.getString("Email"));
		pessoa.setSenha(rs.getString("Senha"));
		pessoa.setNivel(rs.getInt("Id_Nivel"));
		
		return pessoa;
	}
	
	private Livro instanciarLivro(ResultSet rs) throws SQLException {
		
		Livro livro = new Livro();
		livro.setId(rs.getInt("Id"));
		livro.setTitulo(rs.getString("Titulo"));
		livro.setDescricao(rs.getString("Descricao"));
		livro.setAnoPublicacao(rs.getInt("AnoPublicacao"));
		livro.setDisponivel(rs.getBoolean("Disponivel"));
		
		return livro;
	}

}
