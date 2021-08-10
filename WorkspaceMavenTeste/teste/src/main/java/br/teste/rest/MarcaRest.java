package br.teste.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.teste.dao.MarcaDAO;
import br.teste.modelo.Marca;

@Path("marca")
public class MarcaRest extends UtilRest{
	
	private MarcaDAO daoMarca = new MarcaDAO(); 
	
	@Override
	protected void fechaConexao() {
		daoMarca.fechaConexao();
	}
	
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(){
		Response retorno = null;
		try{
			List<Marca> listaMarcas = daoMarca.buscarTodos();
			retorno =  this.buildResponse(listaMarcas);
		}catch(Exception e){
			e.printStackTrace();
			//Constrói a Response e armazena na variável
			retorno = this.buildErrorResponse(e.getMessage());
		}finally {
			//fecha a(s) conexão(ões) gerenciada(s)
			this.fechaConexao();
		}
		//Retorna a Response
		return retorno;
		
		
	}

}