package mediators;

public class RequestHTTP extends ComunicationObjectHTTP{
	/*
POST /gateway/gateway.dll?Action=poll&Lifespan=60&SessionID=855770938.1640688608 HTTP/1.1
Host: bn1msgr1011504.gateway.messenger.live.com:443
Accept-Encoding: gzip,deflate,sdch
Accept-Language: pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4
Content-Type: text/html; charset=UTF-8
Cookie: wlp=A|qske-t:h*oas9Cg.Color_DarkPurple:a*v6CCCg._; mbox=PC#1407426460169-421058.17_03#1408967179|check#true#1407757639|session#1407757578788-735425#1407759439; wlidperf=latency=215&throughput=14; MUID=3B7C30964B066BD13204361C4F066FF2; PPLState=1; MSPAuth=28!yN0woJE1ZSu1PxuhAmeXjofs7ekTzJP5Q5ej*ex5Kc4ft1*r0nnULp!0hqDWAjC86bXR55f5rpRs6Yn!*tKt4fdWxkZSFE6hkwNRTWGdI4ThzNB6wKPv0WvR9EvCVG7ntTCzao!M02EhqZzQXluzw$$; MSPProf=2oT0It*i!Ctey6!YumFkKB04DBDJA7RUOEmxRh72d68fydBLnvyWUa7NPEalhaPT177xTQBmVcWgUVCbgs3D3oD2MSAt68Y8inDt!VXew9eOPCH2szysUCbdwgPbaM1nS6qk9*kXiM0J0i9Jfx3ftW86GdaP4Ou5FPRbfiltnn60BwFTQcGery0zg2TX8PYX7HbMpxE2oxKFvMAbMNKUBsJK50q6xjl9w9OnMpZSo5m2EgWr0BQRyKoQ$$; MH=MSFT; NAP=V=1.9&E=f7d&C=RRJGFuQ0QGhz66nOe8C0-ybDD5vsvNGKrlY4pONtxTapxh2gkXrmOg&W=1; ANON=A=BFBCCC889E7C1D54285DEE71FFFFFFFF&E=fd7&W=1; WLSSC=EgCWAQMAAAAEgAAAC4AArkzpYMj9TWQ5MEbsbpTvm7q2Fnn/Pcb1EJ2eWS8+VOIhbkY76cIHHQoF7TsFwD0mmKjMrGVYRHnnJ58NlABSJLfR/BrvR0VhDZqByiBddH1641x3h60th7MJv/423rP1ymMLLuzniwfsIvnSBurIQpp5+t6vwwZpMrnHro7AHIkFAWIABQEAAAMA9tHDreeT6lMZrehTECcAAAoToAAQGQBsZW9fZGVmb2xpdmVpcmFAbGl2ZS5jb20AcgAAJWxlb19kZWZvbGl2ZWlyYSVsaXZlLmNvbUBwYXNzcG9ydC5jb20AAABdQlIACDgyNTYwMjUwAAAAAAQWAgAAhZdtQBAGQwAITGVvbmFyZG8AE2RlIEZyZWl0YXMgT2xpdmVpcmEEbQAAAAAAAAAAAAAAAB1HKQ4gxvLRAADnk+pTZ+XrUwAAAAAAAAAAAAAAABAAMjAwLjE4Ni4xODAuMTc3AAUCAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAAABHX1wcTus+qw==; xid=269e5934-ea22-41cb-ae46-2bc008745021&9aOrD&SNT148-W48&268; mkt=pt-BR; crms=1; wls=A|qske-t:h*m:a*n; LN=aTC9e1407882226164%26793f%2611; BP=l=WC.Hotmail&FR=&ST=&p=0; wlxS=wpc=1&WebIM=1; E=P:o6AFTJOE0Yg=:WbBoCoj9yuWM48df50Ait9n3ugidl74nNUGqljYf9ZQ=:F; wla42=; xidseq=10; LDH=12; pres=20c6f2d11d47290e=4; HIC=20c6f2d11d47290e|79|0|snt148|4251|snt148
Origin: https://bn1msgr1011504.gateway.messenger.live.com
Pragma: No-Cache
Referer: https://bn1msgr1011504.gateway.messenger.live.com/xmlProxy.htm?vn=9.090515.0&domain=live.com
User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36
X-MSN-Auth: Use-Cookie
X-Requested-Session-Content-Type: text/html

Name=Jonathan+Doe&Age=23&Formula=a+%2B+b+%3D%3D+13%25%21

	 */
	
	
	//FIRST LINE
	private static String URL_INDEX = "URL_INDEX";// 
	private static String HTTP_VERSION_INDEX = "HTTP_VERSION_INDEX";// 
	//BODY
	private static String PARAMS_ITEM_SEPARATOR = "PARAMS_ITEM_SEPARATOR ";// &
	private static String PARAMS_VALUE_SEPARATOR = "PARAMS_VALUE_SEPARATOR";// =
	
	private String method;
	
	@Override
	protected String firstLineToProtocol() {
		
		return null;
	}

	@Override
	protected String bodyToProtocol() {
		return params.toProtocol(config_fields.get(PARAMS_ITEM_SEPARATOR), config_fields.get(PARAMS_VALUE_SEPARATOR));
	}

	@Override
	protected void loadFirstLine(String protocol) {
			int url_index = protocol.indexOf(config_fields.get(URL_INDEX));
			int http_version_index = protocol.substring((url_index+config_fields.get(URL_INDEX).length()),protocol.length()).indexOf(HTTP_VERSION_INDEX);
			setMethod(protocol.substring(0,url_index));
			setUrl(protocol.substring(url_index,http_version_index));
			setProtocolVersion(protocol.substring(http_version_index,protocol.length()));
			
	}

	@Override
	protected void loadBody(String body) {
		params.loadProtocol(body,config_fields.get(PARAMS_ITEM_SEPARATOR), config_fields.get(PARAMS_VALUE_SEPARATOR));
	}
	
	protected void loadDefoultConfig() {
		super.loadDefoultConfig();
		config_fields.put(URL_INDEX," ");
		config_fields.put(HTTP_VERSION_INDEX," ");
	}
	
	//get and set
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
