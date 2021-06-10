package br.teste.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
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
import com.google.gson.JsonObject;

import br.teste.bd.Conexao;
import br.teste.modelo.Produto;

@Path("produto")
public class ProdutoRest extends UtilRest{
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String produtoParam){
		
		try{
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);
			
			EntityManager em = new Conexao().getEntityManager();
			em.getTransaction().begin();
			em.persist(produto);
			em.getTransaction().commit();
			
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
	public Response buscarPorNome(@QueryParam("valorBusca") String nome){
		//System.out.println("buscando produto");
		try{
			
			EntityManager em = new Conexao().getEntityManager();
			em.getTransaction().begin();
			List listaBusca = em.createQuery("FROM Produto p ORDER BY p.categoria ASC, p.marca.nome ASC, p.modelo ASC").getResultList();
			List<Produto> listaProdutos = new ArrayList<Produto>();
			//System.out.println(new Gson().toJson(lista));
			for (Object item : listaBusca) {
				Produto produto = (Produto) item;
				produto.setCategoria(produto.getCategoria().equals("1") ? "Geladeira" : "Freezer");
				listaProdutos.add(produto);			
			}
			return this.buildResponse(listaProdutos);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	/*
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id){
		
		try{
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			
			boolean retorno = jdbcProduto.deletar(id);
				
			String msg = "";
			if(retorno){
				msg = "Produto excluído com sucesso!";
			}else{
				msg = "Erro ao excluir produto.";
			}
		
			conec.fecharConexao();

			return this.buildResponse(msg);
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id){
		
		try{
			Produto produto = new Produto();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

			produto = jdbcProduto.buscarPorId(id);
			
			conec.fecharConexao();	
			
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
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			
			boolean retorno = jdbcProduto.alterar(produto);
			
			String msg = "";
			if (retorno){
				msg = "Produto alterado com sucesso!";
			}else{
				msg = "Erro ao alterar produto.";
			}

			conec.fecharConexao();	
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
			List<JsonObject> listaProdutos = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			listaProdutos = jdbcProduto.buscarParaVenda(idMarca, categoria);
			conec.fecharConexao();	
			String json = new Gson().toJson(listaProdutos);
			System.out.println(json);
			return this.buildResponse(json);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	*/
}//fecha a classe ProdutoRest

