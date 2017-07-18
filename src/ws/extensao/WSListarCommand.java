package ws.extensao;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;

import cadastro.entidade.Cliente;
import cadastro.entidade.Usuario;
import cadastro.persistencia.AcessoBD;
import ws.util.WSUtil;
import http.Request;
import http.Response;
import extensao.Command;

public class WSListarCommand extends WSCommand {

	@Override
	public void wsexecute(JsonObject obj, Request req, Response resp)throws Exception {
	
			List<Cliente> lista = AcessoBD.getInstancia().pesquisarTodosClientes();
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			for (Cliente c : lista) {
				JsonObject jsonObj = Json.createObjectBuilder()
						.add("ID", c.getId())
						.add("NOME", c.getNome())
						.add("LAT", c.getLat())
						.add("LNG", c.getLng())
						.build();
				
				arrayBuilder.add(jsonObj);
			}
			
			JsonObject rslt = Json.createObjectBuilder()
					.add("STATUS", "OK")
					.add("DADOS", arrayBuilder)
					.build();
			WSUtil.sendObject(resp, rslt);
		}
}
