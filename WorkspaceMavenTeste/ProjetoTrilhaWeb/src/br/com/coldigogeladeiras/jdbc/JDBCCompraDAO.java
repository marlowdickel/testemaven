package br.com.coldigogeladeiras.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

import br.com.coldigogeladeiras.jdbcinterface.CompraDAO;
import br.com.coldigogeladeiras.modelo.Compra;
import br.com.coldigogeladeiras.modelo.ProdutoCompra;

public class JDBCCompraDAO implements CompraDAO {

	private Connection conexao;

	public JDBCCompraDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean inserir(Compra compra) {
		String comando = "INSERT INTO compras (id, data, fornecedor) VALUES (?,?,?)";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS);
			p.setInt(1, compra.getId());
			p.setString(2, compra.getData());
			p.setString(3, compra.getFornecedor());
			p.execute();
			ResultSet idGerado = p.getGeneratedKeys();
			while (idGerado.next()) {
				for(ProdutoCompra produto: compra.getProdutos()) {
					produto.setIdCompra(idGerado.getInt(1));
					JDBCProdutoCompraDAO jdbcProdutoCompra = new JDBCProdutoCompraDAO(this.conexao);
					jdbcProdutoCompra.inserir(produto);
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public List<JsonObject> gerarRelatorio() {
		String comando = "SELECT * FROM compras "
				+ "ORDER BY data DESC";  
		
		List<JsonObject> listaCompras = new ArrayList<JsonObject>();
		JsonObject compra = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while (rs.next()) {
				
				int idCompra = rs.getInt("id");
				String data = rs.getString("data");
				String fornecedor = rs.getString("fornecedor");
				
				JDBCProdutoCompraDAO jdbcProdutoCompra = new JDBCProdutoCompraDAO(this.conexao);
				List<JsonObject> listaProdutos = jdbcProdutoCompra.buscaPorCompra(idCompra);

				compra = new JsonObject();
				compra.addProperty("id", idCompra);
				compra.addProperty("data", data);
				compra.addProperty("fornecedor", fornecedor);
				compra.addProperty("produtos", new Gson().toJson(listaProdutos));

				listaCompras.add(compra);
		        
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaCompras;
	}

}
