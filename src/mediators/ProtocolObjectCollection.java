package mediators;

import java.util.Set;

import server.Server;


public abstract class ProtocolObjectCollection {

	
	
	public abstract String toProtocol(String itemSeparator,String valueSeparator);
	public abstract void loadProtocol(String protocol,String itemSeparator,String valueSeparator);
	protected void relateLoadProtocolItemError(String key,String value)
	{
		 Server.log += "Error: Key \" "+key+" \" , Value \" "+value+" \" not  be load;\n";
	}
	public abstract Set getKeySet();
}
