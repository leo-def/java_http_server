package ws.util;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import http.Request;
import http.Response;

public class WSUtil {
	
/*	public static boolean validarContentType(Request req, Response resp) {
		return "application/json".equals(req.getContentType());
		resp.setHeader("Content-Type", "application/json");
		if("application/json".equals(req.getContentType())) {
			return true;		
		}else {
			resp.getPrintStream().println("{ \"STATUS\" : \"INVALID_CONTENT_TYPE\" }");
			return false;
		}
	}
	*/
	
	public static boolean validarContentType(Request req) {
		return "application/json".equals(req.getContentType());
	}
	
	
	public static void sendStatus(Response resp, String status) {
		resp.setHeader("Content-Type", "application/json");
		resp.getPrintStream().println("{ \"STATUS\" : \""+status+"\" }");
	}

	public static JsonObject toJson(InputStream inputStream) {
		JsonReader reader = Json.createReader(new InputStreamReader(inputStream));
		JsonObject obj = reader.readObject();
		return obj;
	}

	public static void sendObject(Response resp, JsonObject rslt) {
		resp.setHeader("Content-Type", "application/json");
		resp.getPrintStream().println(rslt.toString());
	}
}
