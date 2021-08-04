package br.teste.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.teste.dao.GenericDAO;
import br.teste.modelo.Marca;

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
		
	}

}