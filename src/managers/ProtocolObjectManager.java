package managers;

import java.util.HashMap;




import java.util.Set;

import server.Server;
import mediators.ProtocolObjectCollection;
import mediators.ProtocolObject;
public class ProtocolObjectManager extends ProtocolObjectCollection{
	
	private HashMap<String,ProtocolObject> variables;
	private Class<ProtocolObject> type;

	public ProtocolObjectManager(Class type)
	{
		setType(type);
		init();
	}
	public ProtocolObjectManager(Class type, String protocol, String itemSeparator,
			String valueSeparator)
	{
		setType(type);
		init();
		loadProtocol(protocol, itemSeparator, valueSeparator);
	}
	
	@Override
	public String toProtocol(String itemSeparator, String valueSeparator) {
		String protocol = "";
		for(String key : variables.keySet())
		{
			ProtocolObject object = variables.get(key);
			if(object != null)
			{
				protocol = protocol + key + valueSeparator  + object.toProtocol() + itemSeparator;
			}
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
			ProtocolObject object;
			try{
				object = type.newInstance();
				object.loadProtocol(itemArray[1]);
			}catch(Exception e)
				{
					relateLoadProtocolItemError(itemArray[0],itemArray[1]);
					object = null;
				}
			variables.put(itemArray[0],object);
		}
	}

	public void setType(Class type)
	{
		this.type = type;
	}

	public void init() {
		variables = new HashMap<String,ProtocolObject>();
	}
	
	public void put(String name, String protocol)
	{
		try {
			ProtocolObject po = type.newInstance();
			po.loadProtocol(protocol);
			variables.put(name,po);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void put(String name, ProtocolObject po)
	{
			variables.put(name,po);
	}
	public void remove(String name)
	{
		variables.remove(name);
	}
	public void get(String name)
	{
		variables.get(name);
	}
	@Override
	public Set getKeySet() {
		return variables.keySet();
	}
	
}
