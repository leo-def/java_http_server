 package cadastro.extensao;
 
 import http.Request;
import http.Response;

import java.io.PrintStream;

import cadastro.entidade.Usuario;
import cadastro.persistencia.AcessoBD;
import cadastro.util.Util;
import extensao.Command;
 
 public class ProcessarLoginCommand implements Command {	
	public void execute(Request req, Response resp) throws Exception {
		if (Util.isLogado(req)) {
			Util.redirect(resp, "/Listar");
			return;
		}
		
		String email = req.getParam("email");
		String senha = req.getParam("senha");

		if (email == null && senha == null) {
			apresentarTelaLogin(resp);
		}		
		else {
			Usuario usuario = AcessoBD.getInstancia().consultarUsuario(email);
			
			if (usuario == null || (usuario != null && !usuario.validar(senha))) {
				apresentarTelaLogin(resp, "E-mail e/ou senha inválido.");
			} else {				
				Util.login(req, resp, usuario);
			}
		}
	}

	private void apresentarTelaLogin(Response resp) {
		apresentarTelaLogin(resp, null);
	}

	private void apresentarTelaLogin(Response resp, String msg) {
		PrintStream out = resp.getPrintStream();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Login :: Manutenção de Clientes</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Manutenção de Clientes</h1>");
		out.println("<hr/>");
		out.println("<h2>Login</h2>");
		
		if (msg != null) {
			out.print("<h4 style=\"color:red\">");
			out.print(msg);
			out.println("</h4>");
		}
		
		out.println("<form method=\"post\" action=\"/Login\">");
		out.println("<table>");
		out.println("<tr><td align=\"right\">E-mail:</td><td><input type=\"email\" id=\"email\" name=\"email\" required oninvalid=\"this.setCustomValidity('Este campo precisa ser preenchido com um endereço de e-mail válido.')\" oninput=\"this.setCustomValidity('')\"/></td></tr>");
		out.println("<tr><td align=\"right\">Senha:</td><td><input type=\"password\" id=\"senha\" name=\"senha\" required oninvalid=\"this.setCustomValidity('Este campo precisa ser preenchido.')\" oninput=\"this.setCustomValidity('')\"/></td></tr>");
		out.println("<tr><td align=\"right\" colspan=\"2\"><input type=\"submit\" value=\"Efetuar Login\"/></td></tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
 }