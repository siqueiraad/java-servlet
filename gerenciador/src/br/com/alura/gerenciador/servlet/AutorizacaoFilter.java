package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/entrada")
public class AutorizacaoFilter implements Filter {

	public void doFilter(ServletRequest requestServlet, ServletResponse responseServlet, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) requestServlet;
		HttpServletResponse response = (HttpServletResponse) responseServlet;
		
		String paramAcao = request.getParameter("acao");
				
		HttpSession sessao = request.getSession();
		boolean usuarioNaoEstaLogado = (sessao.getAttribute("usuarioLogado") == null);
		boolean ehUmaAcaoProtegida = !(paramAcao.equals("Login") || paramAcao.equals("LoginForm"));
		
		if(ehUmaAcaoProtegida && usuarioNaoEstaLogado) {
		    response.sendRedirect("entrada?acao=LoginForm");
		    return;
		}
		
		
		chain.doFilter(request, response);
	}
}
