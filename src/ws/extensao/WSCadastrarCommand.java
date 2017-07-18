package ws.extensao;

import java.math.BigDecimal;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObject;

import cadastro.entidade.Cliente;
import cadastro.entidade.Usuario;
import cadastro.persistencia.AcessoBD;
import cadastro.util.Util;
import ws.util.WSUtil;
import http.Request;
import http.Response;
import extensao.Command;

public class WSCadastrarCommand extends WSCommand {

	@Override
	public void wsexecute(JsonObject obj, Request req, Response resp)throws Exception {

			Cliente cliente = new Cliente();
			
			cliente.setNome(obj.getJsonObject("DADOS").getString("NOME"));
			cliente.setEndereco(obj.getJsonObject("DADOS").getString("ENDERECO"));
			cliente.setComplemento(obj.getJsonObject("DADOS").getString("COMPLEMENTO"));
			cliente.setBairro(obj.getJsonObject("DADOS").getString("BAIRRO"));
			cliente.setCidade(obj.getJsonObject("DADOS").getString("CIDADE"));
			cliente.setEstado(obj.getJsonObject("DADOS").getString("ESTADO"));

			String[] location = Util.getLocation(cliente);
			
			if (location != null) {
				cliente.setLat(new BigDecimal(location[0]));
				cliente.setLng(new BigDecimal(location[1]));
			}
			System.out.println("passou por aqui");
			AcessoBD.getInstancia().salvarOuAtualizarCliente(cliente);
			JsonObject rslt = null;
			
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
				JsonObject jsonObj = Json.createObjectBuilder()
						.add("ID", cliente.getId())
						.add("NOME", cliente.getNome())
						.add("ENDERECO", cliente.getEndereco())
						.add("COMPLEMENTO", cliente.getComplemento())
						.add("BAIRRO", cliente.getBairro())
						.add("CIDADE", cliente.getCidade())
						.add("ESTADO", cliente.getEstado())
						.build();
				arrayBuilder.add(jsonObj);
			
				rslt = Json.createObjectBuilder()
					.add("STATUS", "OK")
					.add("DADOS", jsonObj)
					.build();
			WSUtil.sendObject(resp, rslt);
	  	}
	}