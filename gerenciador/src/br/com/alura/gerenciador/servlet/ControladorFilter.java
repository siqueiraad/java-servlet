package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.acao.Acao;

@WebFilter("/entrada")
public class ControladorFilter implements Filter {

	public void doFilter(ServletRequest requestServlet, ServletResponse responseServlet, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) requestServlet;
		HttpServletResponse response = (HttpServletResponse) responseServlet;
		
		String paramAcao = request.getParameter("acao");		
		String nome;
		
		try {
			String nomeDaClasse = "br.com.alura.gerenciador.acao." + paramAcao;
			Class classe = Class.forName(nomeDaClasse);
			Acao acao = (Acao) classe.newInstance();
			nome = acao.executa(request, response);
		} catch (ClassNotFoundException | 
	            InstantiationException | 
	            IllegalAccessException e) {
	        throw new ServletException(e);
	    }
		
		String[] tipoEEndereco = nome.split(":");
		if(tipoEEndereco[0].equals("forward")) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/" + tipoEEndereco[1]);
			rd.forward(request, response);	
		} else {
			response.sendRedirect(tipoEEndereco[1]);
		}
	}
}
