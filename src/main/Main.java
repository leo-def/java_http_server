package main;

import mediators.RequestHTTP;

public class Main {

	public static void main(String[] args) {
		String new_request = 
"POST /gat/gat.dll?Action=poll&Lifespan=60&SessionID=85.1640688608 HTTP/1.1"+"\n"+
"Host: bn1msgr1011504.gateway.messenger.live.com:443"+"\n"+
"Accept-Encoding: gzip,deflate,sdch"+"\n"+
"Accept-Language: pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4"+"\n"+
"Content-Type: text/html; charset=UTF-8"+"\n"+
"Cookie: wlp=A|qske-t:h*oas.Color_DarkPurple:a*v6CCCg._;"+
"mbox=PC#460169-058.17_03#1408967179|check#true#1407757639"+
"\"|session#1407757578788-735425#1407759439;"+
"wlidperf=latency=215&throughput=14;"+
"MUID=3B7C30964B066BD13204361C4F066FF2;"+
"PPLState=1;"+
"MSPAuth=28!yN0woJE1ZSu1PxuhAmeXjofs7ekTzJP5Q5ej*ex5Kc4ft1*"+
"r0nnULp!0hqDWAjC86bXR55f5rpRs6Yn!*tKt4fdWxkZSFE6hkwNRTWGdI4T"+
"hzNB6wKPv0WvR9EvCVG7ntTCzao!M02EhqZzQXluzw$$;"+ 
"MSPProf=2oT0It*i!Ctey6!YumFkKB04DBDJA7RUOEmxRh72d68fydBLnvyWUa"+
"7NPEalhaPT177xTQBmVcWgUVCbgs3D3oD2MSAt68Y8inDt!"+
"VXew9eOPCH2szysUCbdwgPbaM1nS6qk9*"+
"kXiM0J0i9Jfx3ftW86GdaP4Ou5FPRbfiltnn60BwFTQc"+
"Gery0zg2TX8PYX7HbMpxE2oxKFvMAbMNKUBsJK50q6xjl9w9OnMpZSo5m2EgWr0BQRyKoQ$$;"+
"MH=MSFT; NAP=V=1.9&E=f7d&C=RRJGFuQ0QGhz66nOe8C0-ybDD5vsvNGKrlY4pONtx"+
"Tapxh2gkXrmOg&W=1; ANON=A=BFBCCC889E7C1D54285DEE71FFFFFFFF&E=fd7&W=1;"+"\n"+
"Origin: https://bn1msgr1011504.gateway.messenger.live.com"+"\n"+
"Pragma: No-Cache"+"\n"+
"Referer: https://bn1msgr1011504.gateway.messenger.live."+
"com/xmlProxy.htm?vn=9.090515.0&domain=live.com"+"\n"+
"User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64)"+
" AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36"+"\n"+
"X-MSN-Auth: Use-Cookie"+"\n"+
"X-Requested-Session-Content-Type: text/html"+"\n"+
"\n"+
"Name=Jonathan+Doe&Age=23&Formula=a+%2B+b+%3D%3D+13%25%21"+"\n"+
"\n";
		RequestHTTP request = new RequestHTTP();
		request.loadProtocol(new_request);
		System.out.println(""+request.getMethod()+"\n");
		System.out.println(""+request.getProtocolVersion()+"\n");
		System.out.println(""+request.getUrl().toProtocol()+"\n");
		for(Object header : request.getHeaders().getSimpleHeaders().getKeySet())
		{
			System.out.println("header "+request.getHeaders().getSimpleHeaders().get(header.toString()));
		}
		
		System.out.println(""+request.getUrl().toProtocol()+"\n");
		System.out.println(""+request.toProtocol()+"\n");
	}

}
