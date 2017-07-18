package cadastro.util;

import http.Request;
import http.Response;
import http.Session;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;

import javax.json.Json;
import javax.json.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cadastro.entidade.Cliente;
import cadastro.entidade.Usuario;

public class Util {	
	public static void redirect(Response resp, String location) {
		resp.getPrintStream().println("<!DOCTYPE html><html><head><script>window.location = '" + location + "';</script></head>/html>");
	}
	
	public static boolean isLogado(Request req) {
		Session session = req.getSession(false);
		
		if (session == null) {
			return false;
		}		
		
		if ("TRUE".equals(session.getAttribute("LOGADO"))) {
			return true;
		}		

		return false;
	}
	
	public static void login(Request req, Response resp, Usuario usuario) {
		Session session = req.getSession();		
		session.setAttribute("LOGADO" , "TRUE" );
		session.setAttribute("usuario", usuario);		
		redirect(resp, "/Listar");
	}
	
	public static void logout(Request req, Response resp) {
		Session session = req.getSession();		
		session.invalidate();		
		redirect(resp, "/Login");
	}
	
	public static String[] getLocation(Cliente cliente) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
	
		try {		
			HttpGet httpGet = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(cliente.getEnderecoCompleto(), "UTF-8"));
			
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};	
			
			String responseBody = httpClient.execute(httpGet, responseHandler);
		
			JsonObject obj = Json.createReader(new StringReader(responseBody)).readObject();
			
			System.out.println("// LOCATION ***************************");
			System.out.println(obj.toString());
			
			String status = obj.getString("status");
			
			if ("OVER_QUERY_LIMIT".equals(status)) {
				System.out.println("OVER_QUERY_LIMIT");
				return null;
			} else {
				JsonObject res = obj.getJsonArray("results").getJsonObject(0);
				
				String[] location = new String[2];
				
				location[0] = res.getJsonObject("geometry").getJsonObject("location").get("lat").toString();
				location[1] = res.getJsonObject("geometry").getJsonObject("location").get("lng").toString();
				
				return location;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
