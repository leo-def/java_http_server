package mediators;

public class ResponseHTTP extends ComunicationObjectHTTP{

/*
HTTP/1.1 200 OK
Cache-Control: private
Content-Encoding: gzip
Content-Length: 17641
Content-Type: text/html; charset=utf-8
Date: Tue, 12 Aug 2014 23:07:39 GMT
Server: Microsoft-IIS/7.5
Vary: Accept-Encoding
X-AspNet-Version: 4.0.30319
X-Powered-By: ASP.NET
X-UA-Compatible: IE=Edge
 * 
 * 
 * 	
 */
	
	//first line
	private static String STATUS_CODE_INDEX = "STATUS_CODE";// 
	private static String STATUS_MESSAGE_INDEX = "STATUS_MESSAGE";// 
	
	private int statusCod;
	private String statusMessage;
	
	
	@Override
	protected String firstLineToProtocol() {
		return protocolVersion+
				config_fields.get(STATUS_CODE_INDEX)+
				statusCod+
				config_fields.get(STATUS_MESSAGE_INDEX)+
				statusMessage;
	}

	@Override
	protected String bodyToProtocol() {
		return content;
	}

	@Override
	protected void loadFirstLine(String protocol) {
		int prot_len = protocol.length();
		
		int status_code_index = protocol.indexOf(config_fields.get(STATUS_CODE_INDEX));
		int status_message_index = protocol.substring(status_code_index+config_fields.get(STATUS_CODE_INDEX).length(),prot_len).indexOf(config_fields.get(STATUS_MESSAGE_INDEX));
		
		setProtocolVersion(protocol.substring(0,status_code_index));
		setStatusCod(Integer.parseInt(protocol.substring(status_code_index,status_message_index)));
		setStatusMessage(protocol.substring(status_message_index,prot_len));
		
	}

	@Override
	protected void loadBody(String body) {
		this.content = body;
		
	}

	protected void loadDefoultConfig() {
		super.loadDefoultConfig();
		config_fields.put(STATUS_CODE_INDEX," ");
		config_fields.put(STATUS_MESSAGE_INDEX," ");
	}
	
	
	//get and set
	public int getStatusCod() {
		return statusCod;
	}

	public void setStatusCod(int statusCod) {
		this.statusCod = statusCod;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}


}
