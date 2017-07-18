 package cadastro.extensao;
 
 import http.Request;
import http.Response;

import java.io.PrintStream;

import cadastro.entidade.Cliente;
import cadastro.persistencia.AcessoBD;
import cadastro.util.Util;
import extensao.Command;
 
 public class EditarClienteCommand implements Command {
	public void execute(Request req, Response resp) throws Exception {
		if (!Util.isLogado(req)) {
			 Util.redirect(resp, "/Login");
			 return;
		}
		
		String id = req.getParam("id");			

		Cliente cliente = null;

		if (id != null) {
			cliente = AcessoBD.getInstancia().consultarCliente(Integer.parseInt(id));
			
			if (cliente == null) {
				Util.redirect(resp, "/Listar");
				return;
			}
		}		
		
		apresentarTelaEditarCliente(resp, cliente);
	}
	
	private void apresentarTelaEditarCliente(Response resp, Cliente cliente) {
		PrintStream out = resp.getPrintStream();
		
		String acao = cliente == null ? "Novo" : "Editar";
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>" + acao + " :: Manuteção de Clientes</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Manutenção de Clientes<br/><button onclick=\"window.location='/Listar'\">Retornar</button></h1>");
		out.println("<hr/>");
		out.println("<h2>" + acao + " Cliente</h2>");
		out.println("<form method=\"post\" action=\"/Salvar\">");
		
		if (cliente != null) {
			out.println("<input type=\"hidden\" id=\"id\" name=\"id\" value=\"" + cliente.getId() + "\"/>");
		}
		
		out.println("<form method=\"post\" action=\"/Salvar\">");
		out.println("<table>");
		out.println("<tr><td align=\"right\">Nome:</td><td><input type=\"text\" id=\"nome\" name=\"nome\" maxlength=\"50\" required oninvalid=\"this.setCustomValidity('Este campo precisa ser preenchido.')\" oninput=\"this.setCustomValidity('')\" value=\"" + (cliente != null ? cliente.getNome() : "") + "\"/></td></tr>");
		out.println("<tr><td align=\"right\">Endereço:</td><td><input type=\"text\" id=\"endereco\" name=\"endereco\" maxlength=\"80\" value=\"" + (cliente != null ? cliente.getEndereco() : "") + "\"/></td></tr>");
		out.println("<tr><td align=\"right\">Complemento:</td><td><input type=\"text\" id=\"complemento\" name=\"complemento\" maxlength=\"20\" value=\"" + (cliente != null ? cliente.getComplemento() : "") + "\"/></td></tr>");
		out.println("<tr><td align=\"right\">Bairro:</td><td><input type=\"text\" id=\"bairro\" name=\"bairro\" maxlength=\"50\" value=\"" + (cliente != null ? cliente.getBairro() : "") + "\"/></td></tr>");
		out.println("<tr><td align=\"right\">Cidade:</td><td><input type=\"text\" id=\"cidade\" name=\"cidade\" maxlength=\"50\" value=\"" + (cliente != null ? cliente.getCidade() : "") + "\"/></td></tr>");
		out.println("<tr><td align=\"right\">Estado:</td><td><input type=\"text\" id=\"estado\" name=\"estado\" maxlength=\"2\" value=\"" + (cliente != null ? cliente.getEstado() : "") + "\"/></td></tr>");
		out.println("<tr><td align=\"right\" colspan=\"2\"><input type=\"submit\" value=\"Salvar\"/></td></tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

 }