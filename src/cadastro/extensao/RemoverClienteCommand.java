 package cadastro.extensao;
 
 import http.Request;
import http.Response;
import cadastro.persistencia.AcessoBD;
import cadastro.util.Util;
import extensao.Command;
 
 public class RemoverClienteCommand implements Command {
	public void execute(Request req, Response resp) throws Exception {
		if (!Util.isLogado(req)) {
			 Util.redirect(resp, "/Login");
			 return;
		}
		
		String id = req.getParam("id");

		if (id != null) {
			AcessoBD.getInstancia().removerCliente(Integer.parseInt(id));
			Util.redirect(resp, "/Listar");
		}
	}
 }