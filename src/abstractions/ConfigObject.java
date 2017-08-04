package abstractions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

public abstract class ConfigObject {
	protected HashMap<String,String> config_fields;
	protected String config_file = "";
	protected boolean isSaved;
	protected static String value_separator = "<$%>VALUE:<%$>";
	protected static String field_separator = "<$%>/FIELD<%$>";
	
	public ConfigObject(String new_config_file)
	{
		config_file = new_config_file;
		initConfig();
	}
	public ConfigObject()
	{
		initConfig();
	}
	
	protected void initConfig()
	{
		if(!saved())
		{
			loadDefoultConfig();
		}else
		{
			load();
		}
	}
	protected void loadDefoultConfig()
	{
		config_fields = new HashMap<String,String>();
	}
	public boolean saved()
	{
		if(config_file == "")
		{
			return new File(config_file).exists();
		}else {return false;}
		
	}
	
	public void save()
	{
		if(config_file != "")
		{
			File file;
			String content;
			PrintStream print;
			try {
				file = new File(config_file);
				content = "";
				print = new PrintStream(file);
				
				for(String key : config_fields.keySet())
				{
					content += key+value_separator+config_fields.get(key)+value_separator;
				}
				print.write(content.getBytes());
				print.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void load()
	{
		BufferedReader reader; 
		String content;
		try {
			content = "";
			reader = new BufferedReader(new FileReader(config_file));
			String line = null;
			while ((line = reader.readLine()) != null) {
			    content += line;
			}
			reader.close();
			for(String field : content.split(field_separator))
			{
				String[] values = field.split(value_separator);
				config_fields.put(values[0] , values[1] );
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
