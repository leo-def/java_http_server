package ws.extensao;

import http.Request;
import http.Response;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;

import ws.util.WSUtil;
import cadastro.entidade.Cliente;
import cadastro.persistencia.AcessoBD;
import cadastro.util.Util;
import extensao.Command;

public class WSAlterarCommand extends WSCommand {

	@Override
	public void wsexecute(JsonObject obj, Request req, Response resp)throws Exception {
			 
			int id = obj.getJsonObject("DADOS").getInt("ID");

		
			Cliente cliente = AcessoBD.getInstancia().consultarCliente(id);
			JsonObject rslt = null;
			
			if(cliente != null) {
				JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
					JsonObject jsonObj = Json.createObjectBuilder()
							.add("ID", cliente.getId())
							.add("NOME", cliente.getNome())
							.add("ENDERECO", cliente.getEndereco())
							.add("COMPLEMENTO", cliente.getComplemento())
							.add("BAIRRO", cliente.getBairro())
							.add("CIDADE", cliente.getCidade())
							.add("ESTADO", cliente.getEstado())
							.add("LAT", cliente.getLat())
							.add("LNG", cliente.getLng())
							.build();
					arrayBuilder.add(jsonObj);
				
					rslt = Json.createObjectBuilder()
						.add("STATUS", "OK")
						.add("DADOS", jsonObj)
						.build();
				}else {
					rslt = Json.createObjectBuilder()
						 .add("STATUS", "CLIENTE_NOT_FOUND")
						 .build();
				}
			
				WSUtil.sendObject(resp, rslt);
		}
	}
