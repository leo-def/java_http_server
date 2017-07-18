 package cadastro.extensao;
 
 import java.math.BigDecimal;

import http.Request;
import http.Response;
import cadastro.entidade.Cliente;
import cadastro.persistencia.AcessoBD;
import cadastro.util.Util;
import extensao.Command;
 
 public class SalvarClienteCommand implements Command {
	public void execute(Request req, Response resp) throws Exception {
		if (!Util.isLogado(req)) {
			 Util.redirect(resp, "/Login");
			 return;
		}
		
		String id = req.getParam("id");
		
		Cliente cliente = new Cliente();

		if (id != null) {
			cliente.setId(Integer.parseInt(id));
		}
		
		cliente.setNome(req.getParam("nome"));
		cliente.setEndereco(req.getParam("endereco"));
		cliente.setComplemento(req.getParam("complemento"));
		cliente.setBairro(req.getParam("bairro"));
		cliente.setCidade(req.getParam("cidade"));
		cliente.setEstado(req.getParam("estado"));
		
		String[] location = Util.getLocation(cliente);
		
		if (location != null) {
			cliente.setLat(new BigDecimal(location[0]));
			cliente.setLng(new BigDecimal(location[1]));
		}
		
		AcessoBD.getInstancia().salvarOuAtualizarCliente(cliente);
		Util.redirect(resp, "/Listar");
	}
 }