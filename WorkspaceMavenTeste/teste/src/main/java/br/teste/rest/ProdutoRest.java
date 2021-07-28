package br.teste.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.teste.dao.GenericDAO;
import br.teste.dao.ProdutoDAO;
import br.teste.modelo.Produto;

@Path("produto")
public class ProdutoRest extends UtilRest{
	
	private ProdutoDAO daoProduto = new ProdutoDAO(); 
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String produtoParam){
		
		try{
			
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);
			daoProduto.inserir(produto);
			String msg = "Produto cadastrado com sucesso!";
			
			return this.buildResponse(msg);

		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarTodos(){
		try{
			List<Produto> listaProdutos = daoProduto.buscarTodos(Produto.class);

			return this.buildResponse(listaProdutos);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	@GET
	@Path("/buscarAvancado")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarAvancado(@QueryParam("modelo") String modelo){
		
		try{
			List<Produto> listaProdutos = daoProduto.buscarPorModelo(modelo);
			
			return this.buildResponse(listaProdutos);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id){
		
		try{
			
			Produto produto = daoProduto.buscarPorId(id);
			daoProduto.excluir(produto);
			String msg = "Produto excluído com sucesso!";
			
			return this.buildResponse(msg);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@GET
	@Path("/buscar/{id}")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@PathParam("id") int id){
		
		try{
			
			Produto produto = daoProduto.buscarPorId(id);
			
			return this.buildResponse(produto);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String produtoParam){
		try{
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);

			daoProduto.alterar(produto);

			String msg = "Produto alterado com sucesso!";
			
			return this.buildResponse(msg);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}//fecha o método alterar

	@GET
	@Path("/buscarParaVenda")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarParaVenda(@QueryParam("idMarca") int idMarca, @QueryParam("categoria") int categoria){
		
		try{
			List<Produto> listaProdutos = daoProduto.buscarParaVenda(idMarca, categoria);
			
			return this.buildResponse(listaProdutos);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
}//fecha a classe ProdutoRest

