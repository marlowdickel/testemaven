package br.teste.filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class FiltroAjax
 */
public class FiltroAjax extends UtilFiltro implements Filter{

    /**
     * Default constructor. 
     */
    public FiltroAjax() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		//converte request e response para http
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		/*
		//captura a URI origem da requisição
		String	uri	=  request.getRequestURI();
		System.out.println(uri);
		//se não for Ajax e tiver /rest no caminho, está acessando a rest sem poder
		if((!isAjax(request))&&(uri.contains("rest/"))) {
			System.out.println("Acessando rest pela URL!!!");
			response.sendRedirect("/MeuTesteMaven/login.html");
		}
		/* como vamos fazer: criar uma pasta para as páginas iniciais, outra para as internas, e filtrar as internas*/
		/*
		if(isAjax(request)) {
			System.out.println("É Ajax, inicia request real");
			chain.doFilter(request, response);
			System.out.println("Finalizou request");
		}else {
			System.out.println("Not Ajax, inicia request real");
			chain.doFilter(request, response);
			System.out.println(responseToString(response));
			System.out.println("Finalizou request");
		}
		*/
		chain.doFilter(request, response);
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
