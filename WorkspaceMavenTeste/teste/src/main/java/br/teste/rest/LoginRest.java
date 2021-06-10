package br.teste.rest;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.teste.dao.LoginDAO;
import br.teste.modelo.Login;


@Path("login")
public class LoginRest extends UtilRest{
/*
	@POST
	@Path("/entrar")
	@Consumes("application/*")
	public Response entrar(String paramLogin, HttpSession session){
		try{
			Login login = new Gson().fromJson(paramLogin, Login.class);
			LoginDAO loginDAO = new LoginDAO();
			boolean isFound = loginDAO.entrar(login);
			
			if (isFound) {
				session.setAttribute("usuarioLogado", login.getUsuario());
				session.setAttribute("idSessao", session.getId());
				return this.buildResponse("logado!");
			} else {
				return this.buildErrorResponse("Dados incorretos!");		
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());		
		}
	}
	
	@GET
	@Path("/sair")
	public Response sair(HttpSession session){
		try{
		
			session.invalidate();
			return this.buildResponse("Sessão encerrada!");

		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());		
		}
	}
	
	*/
}


