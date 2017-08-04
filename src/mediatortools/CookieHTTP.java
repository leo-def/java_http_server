package mediatortools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import managers.TextVariableManager;
import mediators.ProtocolObject;

public class CookieHTTP extends ProtocolObject{
	//Cookie: wlp=A|qske-t:h*oas9Cg.Color_DarkPurple:a*v6CCCg._; mbox=PC#1407426460169-421058.17_03#1408967179|check#true#1407757639|session#1407757578788-735425#1407759439; wlidperf=latency=215&throughput=14; MUID=3B7C30964B066BD13204361C4F066FF2; PPLState=1; MSPAuth=28!yN0woJE1ZSu1PxuhAmeXjofs7ekTzJP5Q5ej*ex5Kc4ft1*r0nnULp!0hqDWAjC86bXR55f5rpRs6Yn!*tKt4fdWxkZSFE6hkwNRTWGdI4ThzNB6wKPv0WvR9EvCVG7ntTCzao!M02EhqZzQXluzw$$; MSPProf=2oT0It*i!Ctey6!YumFkKB04DBDJA7RUOEmxRh72d68fydBLnvyWUa7NPEalhaPT177xTQBmVcWgUVCbgs3D3oD2MSAt68Y8inDt!VXew9eOPCH2szysUCbdwgPbaM1nS6qk9*kXiM0J0i9Jfx3ftW86GdaP4Ou5FPRbfiltnn60BwFTQcGery0zg2TX8PYX7HbMpxE2oxKFvMAbMNKUBsJK50q6xjl9w9OnMpZSo5m2EgWr0BQRyKoQ$$; MH=MSFT; NAP=V=1.9&E=f7d&C=RRJGFuQ0QGhz66nOe8C0-ybDD5vsvNGKrlY4pONtxTapxh2gkXrmOg&W=1; ANON=A=BFBCCC889E7C1D54285DEE71FFFFFFFF&E=fd7&W=1; WLSSC=EgCWAQMAAAAEgAAAC4AArkzpYMj9TWQ5MEbsbpTvm7q2Fnn/Pcb1EJ2eWS8+VOIhbkY76cIHHQoF7TsFwD0mmKjMrGVYRHnnJ58NlABSJLfR/BrvR0VhDZqByiBddH1641x3h60th7MJv/423rP1ymMLLuzniwfsIvnSBurIQpp5+t6vwwZpMrnHro7AHIkFAWIABQEAAAMA9tHDreeT6lMZrehTECcAAAoToAAQGQBsZW9fZGVmb2xpdmVpcmFAbGl2ZS5jb20AcgAAJWxlb19kZWZvbGl2ZWlyYSVsaXZlLmNvbUBwYXNzcG9ydC5jb20AAABdQlIACDgyNTYwMjUwAAAAAAQWAgAAhZdtQBAGQwAITGVvbmFyZG8AE2RlIEZyZWl0YXMgT2xpdmVpcmEEbQAAAAAAAAAAAAAAAB1HKQ4gxvLRAADnk+pTZ+XrUwAAAAAAAAAAAAAAABAAMjAwLjE4Ni4xODAuMTc3AAUCAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAAABHX1wcTus+qw==; xid=269e5934-ea22-41cb-ae46-2bc008745021&9aOrD&SNT148-W48&268; mkt=pt-BR; crms=1; wls=A|qske-t:h*m:a*n; LN=aTC9e1407882226164%26793f%2611; BP=l=WC.Hotmail&FR=&ST=&p=0; wlxS=wpc=1&WebIM=1; E=P:o6AFTJOE0Yg=:WbBoCoj9yuWM48df50Ait9n3ugidl74nNUGqljYf9ZQ=:F; wla42=; xidseq=10; LDH=12; pres=20c6f2d11d47290e=4; HIC=20c6f2d11d47290e|79|0|snt148|4251|snt148

	
	private static String DATE_FORMAT = "Wdy, DD Mon YYYY HH:MM:SS GMT";
	
	
	private static String COOKIE = "COOKIE";
	private static String SET_COOKIE = "SET_COOKIE";
	
	private static String PARAMS_INIT = "INIT";
	private static String ITEMS_SEPARATOR_INDEX = "ITEMS_SEPARATOR"; 
	private static String VALUE_SEPARATOR_INDEX = "VALUE_SEPARATOR"; 
	//private static String NAME_INDEX = "NAME"; 
	private static String EXPIRES_INDEX = "EXPIRES"; 
	private static String DOMAIN_INDEX = "DOMAIN"; 
	private static String PATH_INDEX = "NAME";
	private static String SECURE_INDEX = "SECURE"; 
	
	//Set-Cookie: value[; expires=date][; domain=domain][; path=path][; secure]
	private  String key;
	private String value;
	private String domain;
	private Date expires;
	private String path;
	private boolean secure;
	private String cookie_type;
	private TextVariableManager params;
	
	public String simple_cookie = "Cookie";
	public String set_cookie = "Set-Cookie";
	
	public CookieHTTP()
	{
		super();
		init();
	}
	public CookieHTTP(String protocol)
	{
		super();
		init();
		loadProtocol(protocol);
	}
	
	@Override
	public String toProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadProtocol(String protocol) {
		int param_separator = protocol.indexOf(config_fields.get(PARAMS_INIT));
		setCookieType(protocol.substring(0,param_separator));
		loadParams(protocol.substring((param_separator+config_fields.get(PARAMS_INIT).length()),protocol.length()));
	}

	public void loadParams(String protocol)
	{
		String itemSeparator = config_fields.get(ITEMS_SEPARATOR_INDEX );
		String valueSeparator = config_fields.get(VALUE_SEPARATOR_INDEX );
		String name;
		String value;
		for(String item : protocol.split(itemSeparator))
		{
			if(item.indexOf(valueSeparator)< 0)
			{
				params.put(null,item);
			}else if(item.equalsIgnoreCase(config_fields.get(SECURE_INDEX)))
			{
				secure = true;
			}else
			{
				String[] nameAndValue = item.split(valueSeparator);
				name = nameAndValue[0];
				value = nameAndValue[1];
				checkParams(name,value);
			}
		}
	}
	
	public void checkParams(String name, String value)
	{
		if(name.equalsIgnoreCase(config_fields.get(EXPIRES_INDEX)))
		{
			Date date = null;
			try {
				date = new SimpleDateFormat(DATE_FORMAT).parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			setExpires(date);
		}else if(name.equalsIgnoreCase(config_fields.get(DOMAIN_INDEX)))
		{
			setDomain(value);
		}else if(name.equalsIgnoreCase(config_fields.get(PATH_INDEX)))
		{
			setPath(value);
		}else 
		{
			params.put(name,value);
		}
	}
	
	public void init()
	{
		params = new TextVariableManager();
	}
	@Override
	protected void loadDefoultConfig() {
		super.loadDefoultConfig();
		config_fields.put(SET_COOKIE ,set_cookie);
		config_fields.put(COOKIE ,simple_cookie);
		
		config_fields.put(PARAMS_INIT,":");
		config_fields.put(ITEMS_SEPARATOR_INDEX," ");
		config_fields.put(VALUE_SEPARATOR_INDEX,"=");
		//config_fields.put(NAME_INDEX,"");
		
		config_fields.put(EXPIRES_INDEX,"expires"); 
		config_fields.put(DOMAIN_INDEX ,"omain");
		config_fields.put(PATH_INDEX,"path");
		config_fields.put(SECURE_INDEX,"secure"); 
		
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getExpires() {
		return expires;
	}
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public TextVariableManager getParams() {
		return params;
	}
	public void setParams(TextVariableManager params) {
		this.params = params;
	}
	public String getCookieType() {
		return cookie_type;
	}
	public void setCookieType(String cookie_type) {
		this.cookie_type = cookie_type;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

}
