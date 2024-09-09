package model.entities.dao.empl;

import java.util.ArrayList;
import java.util.List;

import database.DB;
import database.DbException;
import model.entities.Livro;
import model.entities.Pessoa;
import model.entities.dao.LivroDao;
import java.sql.*;

public final class LivroDaoJDBC implements LivroDao {
	
	private Connection conn;
	
	public LivroDaoJDBC(Connection conn) {
		this.conn = conn; 
	}

	@Override
	public void insert(Livro livro) {
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"INSERT INTO livro "
					+ "(Titulo, Descricao, AnoPublicacao, Disponivel) "
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, livro.getTitulo());
			st.setString(2, livro.getDescricao());
			st.setInt(3, livro.getAnoPublicacao());
			st.setBoolean(4, livro.getDisponivel());
			
			int linhasAfetadas = st.executeUpdate();
			
			if (linhasAfetadas < 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					livro.setId(id);
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
	public void update(Livro livro, Pessoa pessoa) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE livro SET Disponivel = ? WHERE Id = ?");
			
			st.setBoolean(1, !livro.getDisponivel()); // 1 -> 0
			st.setInt(2, livro.getId());
			
			int linhasAtualizadas = st.executeUpdate();
			
			if (linhasAtualizadas > 0) {
				if (livro.getDisponivel()) {					
					conn.prepareStatement("INSERT INTO livroEmprestado (Id_Usuario, Id_Livro) VALUES (?, ?)");
					st.setInt(1, livro.getId());
					st.setInt(1, pessoa.getId());
					
					int rowsInserted = st.executeUpdate();
					
					if (rowsInserted > 0) {
						System.out.println("Registro de empréstimo inserido com sucesso!");
					}
					
					
				}
				else {
					System.out.println("Registro de devolução inserido com sucesso!");
					delete(livro);
				}
			}
			// PROBLEMA NO UPDATE
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void delete(Livro livro) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE FROM livroEmprestado WHERE Id_Livro = ?");
			
			st.setInt(1, livro.getId());
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}
	
	@Override
	public Livro findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM livro WHERE Id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				return instanciarLivro(rs);
			}
			
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Livro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT * FROM livro WHERE Disponivel = 1");
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
