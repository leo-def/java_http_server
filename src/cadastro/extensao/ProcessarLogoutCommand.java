 package cadastro.extensao;
 
 import http.Request;
import http.Response;
import cadastro.util.Util;
import extensao.Command;
 
 public class ProcessarLogoutCommand implements Command {
	public void execute(Request req, Response resp) throws Exception {
		Util.logout(req, resp);
	}
 }