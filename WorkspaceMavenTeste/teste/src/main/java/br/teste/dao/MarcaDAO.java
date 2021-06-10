package br.teste.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.teste.bd.Conexao;
import br.teste.modelo.Marca;

public class MarcaDAO {
	
	private Connection conexao;
	
	public MarcaDAO() throws SQLException, ClassNotFoundException {
		//conexao = new Conexao().conecta();
	}
	
	public ArrayList<Marca> buscar() throws SQLException{
		ArrayList<Marca> marcas = new ArrayList<Marca>();
		Marca marca = null;
		
		PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM marcas");
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			marca = new Marca();
			
			marca.setId(rs.getInt("id"));
			marca.setNome(rs.getString("nome"));
			
			marcas.add(marca);
		}
		
		this.conexao.close();
		
		return marcas;
	}

}
