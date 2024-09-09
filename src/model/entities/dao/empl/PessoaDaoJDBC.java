package model.entities.dao.empl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public void insert(Pessoa pessoa) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO usuario (Nome, Email, Senha, Id_Nivel) "
					+ "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, pessoa.getNome());
			st.setString(2, pessoa.getEmail());
			st.setString(3, pessoa.getSenha());
			st.setInt(4, 2);
			
			int rowAfected = st.executeUpdate();
			
			if (rowAfected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					pessoa.setId(id);
				}
				DB.closeResultSet(rs);
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Pessoa pessoa, String objAtualizar, String msgAtualizar) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE usuario SET ? = ? WHERE Id = ?");
			st.setString(1, objAtualizar);
			st.setString(2, msgAtualizar);
			st.setInt(3, pessoa.getId());
			
			st.executeUpdate();
			System.out.println("Atualização feita.");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pessoa findUser(String password, String email) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM usuario WHERE Senha = ? AND Email = ?");
			
			st.setString(1, password);
			st.setString(2, email);
			
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
	public boolean findByEmail(String email) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM usuario WHERE Email = ?");
			
			st.setString(1, email);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				Pessoa pessoa = instanciarPessoa(rs);
				if (pessoa != null) {
					return true;
				}
			}
			
			return false;
			
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
