package br.teste.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.teste.bd.Conexao;
import br.teste.dao.GenericDAO;
import br.teste.modelo.Marca;
import br.teste.modelo.Produto;

@Path("marca")
public class MarcaRest extends UtilRest{
	
	private GenericDAO<Marca> daoGenerico = new GenericDAO<Marca>(); 
	
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(){
		try{
			List<Marca> listaMarcas = daoGenerico.buscarTodos(Marca.class);
			return this.buildResponse(listaMarcas);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		/*
		try{	
			EntityManager em = new Conexao().getEntityManager();
			em.getTransaction().begin();
			List listaMarcas = em.createQuery("FROM Marca").getResultList();
			/*
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaMarcas = jdbcMarca.buscar();
			conec.fecharConexao();	
			return this.buildResponse(listaMarcas);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}*/
		
	}

}

/*
	public Response buscar(){
		try{
			List<Marca> listaMarcas = new ArrayList<Marca>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaMarcas = jdbcMarca.buscar();
			conec.fecharConexao();	
			String json = new Gson().toJson(listaMarcas);
			return this.buildResponse(json);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		//return this.buildResponse("oi!");
	}
*/