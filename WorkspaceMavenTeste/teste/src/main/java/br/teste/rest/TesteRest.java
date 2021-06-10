package br.teste.rest;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.teste.bd.Conexao;
import br.teste.dao.MarcaDAO;
import br.teste.modelo.Marca;


@Path("teste")
public class TesteRest{

	@GET
	@Path("/marcas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMarcas(){
		ArrayList<Marca> marcas = new ArrayList<Marca>(); 
		try{
			MarcaDAO marcaDAO = new MarcaDAO();
			marcas = marcaDAO.buscar();
			
			String valorResposta = new Gson().toJson(marcas);
								
			return Response.ok(valorResposta).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.ok(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/novamarca")
	@Consumes("application/*")
	public Response addMarca(){
		try{
			Marca marca = new Marca();
			marca.setNome("Bike 21");
			EntityManager em = new Conexao().getEntityManager();
			  
			em.getTransaction().begin();
			em.persist(marca);
			em.getTransaction().commit();
			
			String valorResposta = "bike inserida";
								
			return Response.ok(valorResposta).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.ok(e.getMessage()).build();
		}
	}
	
}


