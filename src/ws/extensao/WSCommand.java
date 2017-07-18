package ws.extensao;

import javax.json.JsonException;
import javax.json.JsonObject;

import ws.util.WSUtil;
import cadastro.entidade.Usuario;
import cadastro.persistencia.AcessoBD;
import cadastro.util.Util;
import http.Request;
import http.Response;
import extensao.Command;

public abstract class WSCommand implements Command{
	private boolean validateToken;
	
	public WSCommand(){
		validateToken = true;
	}
	
	public WSCommand(boolean validateToken){
		this.validateToken = validateToken;
	}
	
	public abstract void wsexecute(JsonObject obj, Request req, Response resp) throws Exception;
	
	@Override
	public void execute(Request req, Response resp) throws Exception {
		if(!WSUtil.validarContentType(req)) {
			WSUtil.sendStatus(resp, "INVALID_CONTENT_TYPE");
			return;
		}
	
		try{
			JsonObject obj= WSUtil.toJson(req.getInputStream());
				
			if(validateToken){
				String token = null;
			
				if(obj.containsKey("TOKEN")) {
						token = obj.getString("TOKEN");
						Usuario usuario = AcessoBD.getInstancia().consultarUsuarioPorToken(token);
					if(usuario == null){
						WSUtil.sendStatus(resp, "INVALID_TOKEN");
						return;
					}else if(!usuario.validarAcesso()) {
						WSUtil.sendStatus(resp, "EXPIRED_TOKEN");
						return;
					}
					AcessoBD.getInstancia().atualizarAcesso(usuario);
				}else{
					WSUtil.sendStatus(resp, "TOKEN_NOT_FOUND");
					return;
				}
			}
			
			wsexecute(obj, req, resp);
			
		}catch(JsonException e) {
			WSUtil.sendStatus(resp, "INVALID_JSON_OBJECT");
		}catch (Exception e) {
			WSUtil.sendStatus(resp, "ERRO");
			e.printStackTrace();
		}
	}

	
}
