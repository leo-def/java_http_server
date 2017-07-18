package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import extensao.Command;

public class Dispatcher {
	public Dispatcher() {
	}

	public String getClassName(Request req) {
		String res = req.getResource();
		if ("".equals(res)) {
			return null;
		}

		String className = null;
		String str = "";
		Config config = Config.getInstance();

		// reconhecimento do 1o. padrão
		int pos = res.lastIndexOf("/");
		if (pos != -1) {
			str = res.substring(pos + 1);
		}
		className = config.getString("cmd_" + str);

		// reconheciomento do 2o. padrão
		if (className == null) {
			pos = str.lastIndexOf(".");		
			if (pos != -1) {
				str = str.substring(pos + 1);
			}
			className = config.getString("cmd_." + str);
		}

		return className;
	}

	public void resolve(Request req, Response resp) throws Exception {
		// verificar recurso dinâmico
		String className = getClassName(req);

		if (className != null) {
			try {
				Class clazz = Class.forName(className);
				Command cmd = (Command) clazz.newInstance();
				cmd.execute(req, resp);
				resp.flush();
			} catch(ClassNotFoundException e) {
				send("404", "File Not Found", req, resp);
			} catch(Exception e) {
				e.printStackTrace();
				send("501", "Internal Error", req, resp);
			}
		} else {
			String repos = getRepos(req.getHost());

			File file = new File(repos, req.getResource());

			if (file.exists()) {
				send(resp, file);
			} else {	
				send("404", "File Not Found", req, resp);
			}
		}
	}

	private void send(Response resp, File file) throws Exception {
		String mimeType = getMimeType(file);

		if (mimeType != null) {
			resp.setHeader("Content-Type", mimeType);
		}

		/*
		PrintStream pout = resp.getPrintStream();
		pout.println("HTTP/1.1 200 OK");
		pout.println("Content-Type: " + mimeType);
		pout.println("Content-Length: " + file.length());
		pout.println("Connection: close");
		pout.println();
		pout.flush();
		*/

		OutputStream bout = resp.getOutputStream();			
		FileInputStream fin = new FileInputStream(file);		
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = fin.read(buffer)) != -1) {
			bout.write(buffer, 0, length);
		}
		bout.flush();
		fin.close();
		
		resp.flush();
	}

	private void send(String code, String desc, Request req, Response resp) throws Exception {
		String page = 
			"<html><head><title>" + 
			code + 
			" " + 
			desc + 
			"</title></head><body><h1>" + 
			code + 
			" " + 
			desc + 
			"</h1><h2>RES: '" + 
			req.getResource() + 
			"'</h2></body></html>";

		resp.setResponseLine(code, desc);
		resp.getPrintStream().print(page);
		resp.flush();
	}

	private String getRepos(String host) {
		String reposKey = "repos_" + host.replace(".", "_");

		Config cfg = Config.getInstance();

		String repos = cfg.getProperty(reposKey);

		if (repos == null) {
			repos = cfg.getProperty(Config.PROP_HTTP_DEFAULT_REPOS);
		}

		return repos;
	}

	private String getMimeType(File file) {
		String name = file.getName();
		String ext  = "undefined";

		int pos = name.lastIndexOf(".");

		if (pos != 0) {
			ext = name.substring(pos + 1);
		}

		Config cfg = Config.getInstance();
		String mimeType = cfg.getProperty(ext);

		return mimeType;
	}
}


















