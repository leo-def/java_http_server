package mediatortools;

import mediators.ProtocolObject;



public class ContextFile extends ProtocolObject{
	private static String DIR_SEPARATOR_INDEX = "DIR_SEPARATOR"; 
	private static String EXTENSION_SEPARATOR_INDEX = "DIR_SEPARATOR"; 
	
	private String dir;
	private String fileName;
	private String extension;
	
	public ContextFile()
	{
		initConfig();
	}
	public ContextFile(String protocol)
	{
		initConfig();
		loadProtocol(protocol);
	}
	
	
	@Override
	public String toProtocol() {
		
		return dir+config_fields.get(DIR_SEPARATOR_INDEX)+fileName+config_fields.get(EXTENSION_SEPARATOR_INDEX)+extension;
	}

	@Override
	public void loadProtocol(String protocol) {
		boolean dirFound = false;
		String dir = protocol;
		for(int index = 0; index >= 0 && !dirFound ; index--)
		{
			String teste = dir.substring((index - config_fields.get(DIR_SEPARATOR_INDEX).length()),index);
			if(teste.equalsIgnoreCase(config_fields.get(DIR_SEPARATOR_INDEX)))
			{
				setFileName(dir.substring(index,dir.indexOf(config_fields.get(EXTENSION_SEPARATOR_INDEX))));
				setDir(dir.substring(0,(index - config_fields.get(DIR_SEPARATOR_INDEX).length())));
				setExtension(dir.substring(dir.indexOf(config_fields.get(EXTENSION_SEPARATOR_INDEX)),dir.length()));
			}
		}
	}
	
	@Override
	protected void loadDefoultConfig() {
		super.loadDefoultConfig();
		config_fields.put(DIR_SEPARATOR_INDEX,"/"); 
		config_fields.put(EXTENSION_SEPARATOR_INDEX,"."); 
	}
	
	//get set is
	public boolean isDir()
	{
		return true;
	}
	public boolean isFile()
	{
		return true;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}



}
