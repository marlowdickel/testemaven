package br.teste.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.teste.bd.Conexao;
import br.teste.modelo.Login;

public class LoginDAO {
	
	private Connection conexao;
	
	public LoginDAO() throws SQLException, ClassNotFoundException {
		//conexao = new Conexao().conecta();
	}
	
	public boolean entrar(Login login) throws SQLException{
		PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM usuarios WHERE usuario=? AND senha=?");
		stmt.setString(1, login.getUsuario());
		stmt.setString(2, login.getSenha());
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			return true;
		}
	
		return false;
	}

}
