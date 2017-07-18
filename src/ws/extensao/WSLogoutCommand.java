package ws.extensao;

import http.Request;
import http.Response;

import javax.json.Json;
import javax.json.JsonObject;

import ws.util.WSUtil;
import cadastro.entidade.Usuario;
import cadastro.persistencia.AcessoBD;

public class WSLogoutCommand extends WSCommand {

	@Override
	public void wsexecute(JsonObject obj, Request req, Response resp)throws Exception {
		 
			String token = obj.getString("TOKEN");
	
			Usuario usuario = AcessoBD.getInstancia().consultarUsuarioPorToken(token);
			
			usuario.setToken(null);
			usuario.setUltimoAcesso(0);
			
			AcessoBD.getInstancia().atualizarAcesso(usuario);	
			
			JsonObject	rslt = Json.createObjectBuilder()
					.add("STATUS", "OK")
					.build();
				
			WSUtil.sendObject(resp, rslt);
		}
}