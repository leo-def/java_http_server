package managers;

import java.util.HashMap;
import java.util.Set;

import server.Server;
import mediators.ProtocolObject;
import mediators.ProtocolObjectCollection;

public class TextVariableManager extends ProtocolObjectCollection{
	public HashMap<String,String> variables; 

	public TextVariableManager()
	{
		 init();
	}
	public TextVariableManager(String protocol, String itemSeparator,
			String valueSeparator)
	{
		 init();
		 loadProtocol(protocol,itemSeparator,valueSeparator);
	}
	
	@Override
	public String toProtocol(String itemSeparator, String valueSeparator) {
		String protocol = "";
		for(String key : variables.keySet())
		{
			protocol = protocol + key + valueSeparator  + variables.get(key) + itemSeparator;
		}
		protocol = protocol.substring(0, protocol.length() - itemSeparator.length());
		return null;
	}

	@Override
	public void loadProtocol(String protocol, String itemSeparator,
			String valueSeparator) {
		for(String item : protocol.split(itemSeparator))
		{
			String[] itemArray = item.split(valueSeparator);
			variables.put(itemArray[0], itemArray[1]);
		}
	}

	public void init() {
		variables = new HashMap<String,String>();
		
	}

	public void put(String name, String protocol)
	{
			variables.put(name,protocol);
	}
	public void remove(String name)
	{
		variables.remove(name);
	}
	public String get(String name)
	{
		return variables.get(name);
	}
	@Override
	public Set getKeySet() {
		return variables.keySet();
	}
	
}
