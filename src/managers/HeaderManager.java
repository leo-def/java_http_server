package managers;

import mediators.ProtocolObject;
import mediatortools.CookieHTTP;

public class HeaderManager extends ProtocolObject{
	private static String HEADER_SEPARATOR_INDEX = "HEADER_SEPARATOR"; 
	private static String VALUE_SEPARATOR_INDEX = "VALUE_SEPARATOR"; 
	private static String COOKIE_ID = "Cookie";
	
	private ProtocolObjectManager cookies;
	private TextVariableManager headers;
	
	public  HeaderManager()
	{
		super();
		init();
	}
	
	public  HeaderManager(String protocol)
	{
		super();
		init();
		loadProtocol(protocol);
	}
	
	@Override
	public String toProtocol() {
		return cookies.toProtocol(config_fields.get(HEADER_SEPARATOR_INDEX),config_fields.get(VALUE_SEPARATOR_INDEX))+
				HEADER_SEPARATOR_INDEX+
				headers.toProtocol(config_fields.get(HEADER_SEPARATOR_INDEX),config_fields.get(VALUE_SEPARATOR_INDEX));
	}
	
	@Override
	public void loadProtocol(String protocol) {
		for(String line : protocol.split(config_fields.get(HEADER_SEPARATOR_INDEX)))
		{
			String[] values = line.split(config_fields.get(VALUE_SEPARATOR_INDEX));
			if(isCookie(values[0]))
			{
				cookies.put(values[0],new CookieHTTP(line));
			}else if(isSimpleHeader(values[0]))
			{
				headers.put(values[0],values[1]);
			}
		}
		
	}
	
	public boolean isSimpleHeader(String name)
	{
		return true;
	}
	
	public boolean isCookie(String name)
	{
		
		if(name.indexOf(config_fields.get(COOKIE_ID)) >= 0) return true;
		else return false;
			
	}
	
	@Override
	public void loadDefoultConfig() {
		super.loadDefoultConfig();
		config_fields.put(HEADER_SEPARATOR_INDEX ,"\n"); 
		config_fields.put(VALUE_SEPARATOR_INDEX ,":"); 
	}
	
	public void init()
	{
		cookies = new ProtocolObjectManager(CookieHTTP.class);
		headers = new TextVariableManager();
	}
	
	public ProtocolObjectManager getCookies()
	{
		return cookies;
	}
	public TextVariableManager getSimpleHeaders()
	{
		return headers;
	}
}
