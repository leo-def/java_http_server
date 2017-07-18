package http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
	// GET, POST, HEAD, PUT, DELETE
	private String method;

	// /, /index.html, images/logo.jpg, css/default.css
	private String resource;

	// HTTP/1.0, HTTP/1.1
	private String httpVersion;

	// headers ...
	private Map<String, String> headers;

	// cookies ...
	private Map<String, Cookie> cookies;

	// parametros GET: /index.php?p1=v1&p2=v2&...
	private Map<String, String> getParams;

	// parametros POST: 
	// Content-Type: x-www-form-urlencoded 
	// Content-Length: <tamanho>
	// Corpo da mensagem: p1=v1&p2=v2&...
	private Map<String, String> postParams;

	// Raw Data
	private ByteArrayInputStream rawData;

	// vinculo com o objeto response para manupulação de cookies de retorno
	private Response response;
		
	public Request(InputStream inputStream) throws Exception {
		headers = new HashMap<>();
		cookies = new HashMap<>();
		getParams = new HashMap<>();
		postParams = new HashMap<>();

		processRequestLine(inputStream);
		processHeaderLines(inputStream);
		processRequestBody(inputStream);

		touchSession();
	}

	private void processRequestLine(InputStream in) throws Exception {
		String line = Util.readLine(in);

		String[] parts = line.split(" ");

		method = parts[0];
		resource = parts[1];
		httpVersion = parts[2];

		processResource();
	}

	private void processResource() throws Exception {
		int pos = resource.indexOf("?");

		if (pos != -1) {
			String params = resource.substring(pos + 1);			
			params = URLDecoder.decode(params, "UTF-8");
			processGetParams(params);
			resource = resource.substring(0, pos);			
		}

		resource = URLDecoder.decode(resource, "UTF-8");

		if ("/".equals(resource)) {
			Config cfg = Config.getInstance();
			resource = cfg.getProperty(Config.PROP_HTTP_DEFAULT_RESOURCE);
		}		
	}

	private void processGetParams(String params) {
		String[] parts = params.split("&");

		for (String p : parts) {
			String[] parts2 = p.split("=");
			
			String key = parts2[0];

			String value = "";

			if (parts2.length == 2) {
				value = parts2[1];
			}

			getParams.put(key, value);
		}
	}

	private void processHeaderLines(InputStream in) throws Exception {
		while (true) {
			String line = Util.readLine(in);

			if ("".equals(line)) {
				break;
			}

			int pos = line.indexOf(":");

			String key = line.substring(0, pos);

			String value = line.substring(pos + 1);

			value = value.trim();

			if (!key.equals("Cookie")) {
				headers.put(key, value);
			} else {
				String[] p1 = value.split(";");

				for (String s : p1) {
					String[] p2 = s.trim().split("=");
					Cookie c = new Cookie(p2[0], p2.length==2 ? p2[1] : "");
					cookies.put(p2[0], c);
				}
			}
		}
	}

	public void setResponse(Response resp) {
		response = resp;
	}

	public Cookie getCookie(String k) {
		return cookies.get(k);
	}

	public Map<String, Cookie> getCookies() {
		return cookies;
	}

	private void processRequestBody(InputStream in) throws Exception {
		int length = getContentLength();

		if (length == 0) {
			rawData = new ByteArrayInputStream(new byte[0]);
			return;
		}

		byte[] buffer = new byte[length];
		in.read(buffer);

		if ("application/x-www-form-urlencoded".equals(getContentType())) {
			String params = new String(buffer, "UTF-8");
			params = URLDecoder.decode(params, "UTF-8");
			processPostParams(params);
		}

		rawData = new ByteArrayInputStream(buffer);		
	}

	private void processPostParams(String params) {
		String[] parts = params.split("&");

		for (String p : parts) {
			String[] parts2 = p.split("=");
			
			String key = parts2[0];

			String value = "";

			if (parts2.length == 2) {
				value = parts2[1];
			}

			postParams.put(key, value);
		}
	}

	public String getMethod() {
		return method;
	}

	public String getResource() {
		return resource;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public InputStream getInputStream() {
		return rawData;
	}	

	public String getHost() {
		return headers.get("Host");
	}

	public String getContentType() {
		return headers.get("Content-Type");
	}

	public int getContentLength() {
		String value = headers.get("Content-Length");
		
		if (value != null) {
			return Integer.parseInt(value);
		} else {
			return 0;
		}		
	}

	public String getHeader(String key) {
		return headers.get(key);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getParam(String key) {
		String value = getParams.get(key);

		if (value == null) {
			value = postParams.get(key);
		}
	
		return value;
	}

	public Map<String, String> getGetParams() {
		return getParams;
	}

	public Map<String, String> getPostParams() {
		return postParams;
	}

	private String getMyServerSSID() {
		Cookie c = getCookie("MyServerSSID");
		if (c != null) {
			return c.getValue();
		} else {
			return "not-found";
		}
	}

	private void touchSession() {
		Session.touch(getMyServerSSID());
	}

	// retorna uma sessão se existir e cria uma nova caso não exista
	public Session getSession() {
		Session session = getSession(false);		

		if (session == null) {
			session = new Session(response);
		}

		return session;
	}	

	// force = true  - forca a criação de uma nova sessão
	// force = false - retorna a sessão se existir ou retorna null se não existir
	public Session getSession(boolean force) {
		Session session = Session.get(getMyServerSSID());

		if (session != null) {
			if (session.expired()) {
				session.invalidate();
				session = null;
			}
		}

		if (force) {
			session.invalidate();
			session = new Session(response);
		}

		return session;	
	}	

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Method=")      .append(method)     .append("\n")
		  .append("Resource=")    .append(resource)   .append("\n")
		  .append("HttpVersion=") .append(httpVersion).append("\n")
		  .append("Headers=")     .append(headers)    .append("\n")
		  .append("Cookies=")     .append(cookies)    .append("\n")
		  .append("GetParams=")   .append(getParams)  .append("\n")
		  .append("PostParams=")  .append(postParams);

		return sb.toString();
	}
}











