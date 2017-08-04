package mediatortools;

import managers.TextVariableManager;
import mediators.ProtocolObject;




public class Url extends ProtocolObject{
	//protocolo://máquina/caminho/recurso
	private static String FILE_SEPARATOR_INDEX = "FILE_SEPARATOR"; 
	private static String HOST_SEPARATOR_INDEX = "HOST_SEPARATOR"; 
	private static String PARAM_SEPARATOR_INDEX = "PARAM_SEPARATOR"; 
	private static String PARAM_VALUE_SEPARATOR_INDEX = "PARAM_VALUE_SEPARATOR";  
	
	private String protocol;
	private String host;
	private ContextFile file;
	private TextVariableManager params;
		
	
	public Url()
	{
		initConfig();
	}
	public Url(String protocol)
	{
		initConfig();
	}
	
	@Override
	public String toProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadProtocol(String protocol) {
		int hostIndex = protocol.indexOf(config_fields.get(HOST_SEPARATOR_INDEX));
		int fileIndex = protocol.indexOf(config_fields.get(FILE_SEPARATOR_INDEX));
		int paramIndex = protocol.indexOf(config_fields.get(PARAM_SEPARATOR_INDEX));
		setProtocol(protocol.substring(0,hostIndex));
		setFile(protocol.substring((hostIndex+config_fields.get(HOST_SEPARATOR_INDEX).length()),fileIndex));
		setParams(protocol.substring(paramIndex+PARAM_SEPARATOR_INDEX.length()),PARAM_SEPARATOR_INDEX, PARAM_VALUE_SEPARATOR_INDEX);
	}

	@Override
	protected void loadDefoultConfig() {
		super.loadDefoultConfig();
		config_fields.put(FILE_SEPARATOR_INDEX,"/");
		config_fields.put(HOST_SEPARATOR_INDEX,"://");
		config_fields.put(PARAM_SEPARATOR_INDEX, "?");
		config_fields.put(PARAM_VALUE_SEPARATOR_INDEX,"=");
	}

	public void init()
	{
		file = new ContextFile();
		params = new TextVariableManager();
	}
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
		this.params = new TextVariableManager();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public ContextFile getFile() {
		return file;
	}

	public void setFile(ContextFile file) {
		this.file = file;
	}

	public void setFile(String file) {
		this.file =new ContextFile(file);
	}
	public TextVariableManager getParams() {
		return params;
	}

	public void setParams(TextVariableManager params) {
		this.params = params;
	}
	public void setParams(String params,String itemSeparator, String valueSeparator) {
		this.params = new TextVariableManager(params,itemSeparator,valueSeparator);
	}



}
