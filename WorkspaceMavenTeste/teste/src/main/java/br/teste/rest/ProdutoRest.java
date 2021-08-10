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

import br.teste.dao.ProdutoDAO;
import br.teste.modelo.Produto;

@Path("produto")
public class ProdutoRest extends UtilRest{
	
	private ProdutoDAO daoProduto = new ProdutoDAO(); 
	
	@Override
	protected void fechaConexao() {
		daoProduto.fechaConexao();		
	}
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String produtoParam){
		//Cria uma referência para armazenar o retorno que esse método deve dar
		Response retorno = null;
		try{
			//recebe o produto cadastrado em um objeto
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);
			//insere o produto no BD
			daoProduto.inserir(produto);
			String msg = "Produto cadastrado com sucesso!";
			
			retorno =  this.buildResponse(msg);

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
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarTodos(){
		Response retorno = null;
		try{
			List<Produto> listaProdutos = daoProduto.buscarTodos();

			retorno =  this.buildResponse(listaProdutos);
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
	
	@GET
	@Path("/buscarAvancado")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarAvancado(@QueryParam("modelo") String modelo){
		Response retorno = null;
		try{
			//Busca os produtos por modelo e armazena a lista retornada
			List<Produto> listaProdutos = daoProduto.buscarPorModelo(modelo);
			
			retorno =  this.buildResponse(listaProdutos);
			
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
	
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id){
		Response retorno = null;
		try{
			
			daoProduto.excluir(id);
			String msg = "Produto excluído com sucesso!";
			
			retorno =  this.buildResponse(msg);
			
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
	
	@GET
	@Path("/buscar/{id}")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@PathParam("id") int id){
		Response retorno = null;
		try{
			
			Produto produto = daoProduto.buscarPorId(id);
			
			retorno =  this.buildResponse(produto);
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
	
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String produtoParam){
		Response retorno = null;
		try{
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);

			daoProduto.alterar(produto);

			String msg = "Produto alterado com sucesso!";
			
			retorno =  this.buildResponse(msg);
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

	@GET
	@Path("/buscarParaVenda")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarParaVenda(@QueryParam("idMarca") int idMarca, @QueryParam("categoria") int categoria){
		Response retorno = null;
		try{
			//Busca todos os produtos para venda (ou seja, os produtos da marca e categoria selecionados em uma das linhas do mestre-detalhe)
			List<Produto> listaProdutos = daoProduto.buscarParaVenda(idMarca, categoria);
			
			retorno =  this.buildResponse(listaProdutos);
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

}//fecha a classe ProdutoRest

