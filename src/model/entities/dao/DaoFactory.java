package model.entities.dao;

import database.DB;
import model.entities.dao.empl.LivroDaoJDBC;
import model.entities.dao.empl.PessoaDaoJDBC;

public final class DaoFactory {
	
	public static LivroDao criarLivroDao() {
		return new LivroDaoJDBC(DB.getConnection());
	}
	
	public static PessoaDao criarPessoaDao() {
		return new PessoaDaoJDBC(DB.getConnection());
	}

}
