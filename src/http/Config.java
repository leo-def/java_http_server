package http;

import java.io.FileInputStream;
import java.util.Properties;

public class Config extends Properties {
	public static final String CONFIG_FILE = "config.properties";	
	public static final String PROP_HTTP_PORTA = "http.porta";
	public static final String PROP_HTTP_DEFAULT_RESOURCE = "http.default.resource";
	public static final String PROP_HTTP_DEFAULT_REPOS = "http.default.repos";
	public static final int DEFAULT_HTTP_PORTA = 80;


	private static class Holder {
		private static final Config INSTANCE = new Config();
	}
	
	public static Config getInstance() {
		return Holder.INSTANCE;
	}
	
	private Config() {
		try {
			System.out.println("Carregando arquivo de configuracao.");
			load(new FileInputStream(CONFIG_FILE));				
		} catch(Exception e) {
			System.out.println("Falha ao tentar carregar arquivo de configuracao.");
		}
	}
	
	public String getString(String name) {
		return getProperty(name);
	}
	
	public int getInt(String name) {
		return Integer.parseInt(getProperty(name));
	}	
}








