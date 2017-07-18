package ws.extensao;

import java.io.InputStreamReader;
import java.io.StringReader;











import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;

import cadastro.entidade.Usuario;
import cadastro.persistencia.AcessoBD;
import ws.util.WSUtil;
import http.Request;
import http.Response;
import extensao.Command;

public class WSLoginCommand extends WSCommand {

	public WSLoginCommand()
	{
		super(false);
	}
	
	@Override
	public void wsexecute(JsonObject obj, Request req, Response resp)throws Exception {

			String email = obj.getJsonObject("DADOS").getString("email");
			String senha = obj.getJsonObject("DADOS").getString("senha");
		
			Usuario usuario = AcessoBD.getInstancia().consultarUsuario(email);
			
			if(usuario == null || (usuario != null && !usuario.validar(senha))) {
				WSUtil.sendStatus(resp, "EMAIL_E_OU_SENHA_INVÁLIDOS");
			}else {
				long ultimoAcesso = System.currentTimeMillis();
				String token      = String.valueOf(ultimoAcesso);
				
				usuario.setUltimoAcesso(ultimoAcesso);
				usuario.setToken(token);
				
				AcessoBD.getInstancia().atualizarAcesso(usuario);
				
				JsonObject rslt = Json.createObjectBuilder()
						.add("STATUS","OK")
						.add("TOKEN", token)
						.build();
				WSUtil.sendObject(resp, rslt);		
		}
	}

}
