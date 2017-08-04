package mediators;


import managers.HeaderManager;
import managers.TextVariableManager;
import mediatortools.Url;

public abstract class ComunicationObjectHTTP extends ProtocolObject{

	private static String HEADERS_SEPARATOR_INDEX = "FILE_SEPARATOR"; 
	private static String BODY_SEPARATOR_INDEX = "HOST_SEPARATOR"; 
	private static String DOCUMENT_END_INDEX = "DOCUMENT_END";
	
	protected HeaderManager headers;
	protected TextVariableManager params;
	protected String content;
	protected Url url;
	protected String protocolVersion;
	
	
	public ComunicationObjectHTTP()
	{
		super();
		init();
		
	}
	
	public ComunicationObjectHTTP(String protocol)
	{
		super();
		init();
		loadProtocol(protocol);
	}	
	@Override
	public String toProtocol() {
		return firstLineToProtocol()+
				config_fields.get(HEADERS_SEPARATOR_INDEX)+
				headersToProtocol()+
				config_fields.get(BODY_SEPARATOR_INDEX)+
				bodyToProtocol()+
				config_fields.get(DOCUMENT_END_INDEX);
	}	
	protected abstract String firstLineToProtocol();
	protected String headersToProtocol()
	{
		return  headers.toProtocol();
	}
	protected abstract String bodyToProtocol();
	
	@Override
	public void loadProtocol(String protocol) {
		int heradersIndex = protocol.indexOf(config_fields.get(HEADERS_SEPARATOR_INDEX));
		int bodyIndex = protocol.indexOf(config_fields.get(BODY_SEPARATOR_INDEX));
		loadFirstLine(protocol.substring(0,heradersIndex));
		loadHeaders(protocol.substring((heradersIndex+config_fields.get(HEADERS_SEPARATOR_INDEX).length()),bodyIndex));
		loadBody(protocol.substring((bodyIndex+config_fields.get(BODY_SEPARATOR_INDEX).length()),protocol.length()));
	}
	protected abstract void loadFirstLine(String protocol);
	protected void loadHeaders(String protocol)
	{
		headers.loadProtocol(protocol);
	}
	protected abstract void loadBody(String body);
	
	@Override
	protected void loadDefoultConfig() {
		super.loadDefoultConfig();
		config_fields.put(HEADERS_SEPARATOR_INDEX,"/n");
		config_fields.put(BODY_SEPARATOR_INDEX,"/n/n");
		config_fields.put(DOCUMENT_END_INDEX,"/n/n");
	}
	public void init()
	{
		headers = new HeaderManager();
		params = new TextVariableManager();
	}
	
	
	//get and set
	public HeaderManager getHeaders() {
		return headers;
	}

	public void setHeaders(HeaderManager headers) {
		this.headers = headers;
	}

	public TextVariableManager getParams() {
		return params;
	}

	public void setParams(TextVariableManager params) {
		this.params = params;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}
	public void setUrl(String url_protocol)
	{
		this.url = new Url(url_protocol);
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

}
