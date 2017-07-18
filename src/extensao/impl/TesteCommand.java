package extensao.impl;

import extensao.*;
import http.*;

import java.io.*;

public class TesteCommand implements Command {
	public void execute(Request req, Response resp) throws Exception {
		String nome = req.getParam("nome");

		Session s = req.getSession();

		if (nome != null) {
			s.setAttribute("nome", nome);
		} else {
			nome = (String) s.getAttribute("nome");

		}

		if (nome == null) {
			nome = "";
		}

		Cookie c = req.getCookie("id_carrinho");

		int val = c != null 
				? Integer.parseInt(c.getValue()) 
				: 0;


		PrintStream out = resp.getPrintStream();

		out.println("<html><body>");
		out.println("<h1>Esta é a nossa extensão em execução</h1>");
		out.println("<h2>id_carrinho=" + val + "</h2>");
		out.println("<h2>nome=" + nome + "</h2>");
		out.println("</body></html>");

		if (val == 250) {
			resp.setCookie("id_carrinho" , null);
		} else {
			resp.setCookie("id_carrinho" , String.valueOf(val+1));
		}
	}
}










