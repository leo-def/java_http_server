 package cadastro.extensao;
 
 import http.Request;
import http.Response;

import java.io.PrintStream;
import java.util.List;

import cadastro.entidade.Cliente;
import cadastro.persistencia.AcessoBD;
import cadastro.util.Util;
import extensao.Command;
 
 public class ListarClientesCommand implements Command {
	public void execute(Request req, Response resp) throws Exception {
		if (!Util.isLogado(req)) {
			 Util.redirect(resp, "/Login");
			 return;
		}
		
		List<Cliente> clientes = AcessoBD.getInstancia().pesquisarTodosClientes();
		apresentarTelaListarClientes(resp, clientes);
	}
	
	private void apresentarTelaListarClientes(Response resp, List<Cliente> clientes) {
		PrintStream out = resp.getPrintStream();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Listar :: Manuteção de Clientes</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Manutenção de Clientes<br/><button onclick=\"window.location='/Logout'\">Logout</button></h1>");
		out.println("<hr/>");
		out.println("<h2>Listar Clientes<br/><button onclick=\"window.location='/Editar'\">Incluir Novo cliente</button></h2>");
		out.println("<table border=\"1\">");
		out.println("<tr><th>Nome</th><th>Endereço</th><th>Complemento</th><th>Bairro</th><th>Cidade</th><th>Estado</th><th>Editar</th><th>Remover</th></tr>");
		
		StringBuilder js = new StringBuilder("var pontos = [];");
		
		for (Cliente cliente : clientes) {
			out.print("<tr>");
			out.print("<td>");
			out.print(cliente.getNome());
			out.print("</td>");
			out.print("<td>");
			out.print(cliente.getEndereco());
			out.print("</td>");
			out.print("<td>");
			out.print(cliente.getComplemento());
			out.print("</td>");
			out.print("<td>");
			out.print(cliente.getBairro());
			out.print("</td>");
			out.print("<td>");
			out.print(cliente.getCidade());
			out.print("</td>");
			out.print("<td>");
			out.print(cliente.getEstado());
			out.print("</td>");
			out.println("<td><button onclick=\"window.location='/Editar?id=" + cliente.getId() + "'\">Editar</button></td>");
			out.println("<td><button onclick=\"if (window.confirm('Tem certeza?')) window.location='/Remover?id=" + cliente.getId() + "'\">Remover</button></td>");
			out.println("</tr>");
			
			js.append("pontos.push(['").append(cliente.getNome()).append("',").append(cliente.getLat()).append(",").append(cliente.getLng()).append("]);");
		}
		
		out.println("</table>");
		
		out.println("<br/><div id=\"map\" style=\"width: 80%; height: 80%; position: absolute;\"></div>");
		
		out.print("<script>");
		out.print(js);
		out.println("</script>");
		
		out.println("<script src=\"https://maps.googleapis.com/maps/api/js?v=3.exp\"></script>");		
		out.println("<script src=\"/js/show_map.js\"></script>");
		
		out.println("</body>");
		out.println("</html>");
	}

 }