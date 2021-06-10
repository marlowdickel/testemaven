package br.teste.filtro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UtilFiltro {
	
	public static boolean isAjax(ServletRequest request) {
		HttpServletRequest requestHttp = (HttpServletRequest) request;
		return "XMLHttpRequest".equals(requestHttp.getHeader("X-Requested-With"));
	}
	
	public String responseToString(ServletResponse response) {
		HttpServletResponse responseHttp = (HttpServletResponse) response;
		return "incompleto";
	}

}
