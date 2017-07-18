package ws.extensao;

import http.Request;
import http.Response;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;

import ws.util.WSUtil;
import cadastro.entidade.Cliente;
import cadastro.persistencia.AcessoBD;
import extensao.Command;

public class WSExcluirCommand extends WSCommand {

	@Override
	public void wsexecute(JsonObject obj, Request req, Response resp)throws Exception {
		 
			int id = obj.getJsonObject("DADOS").getInt("ID");

		
			AcessoBD.getInstancia().removerCliente(id);
			
				
			JsonObject	rslt = Json.createObjectBuilder()
					.add("STATUS", "OK")
					.build();
				
			WSUtil.sendObject(resp, rslt);
		}
	}

