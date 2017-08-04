package mediators;

import abstractions.ConfigObject;



public abstract class ProtocolObject extends ConfigObject{
	public abstract String toProtocol();
	public abstract void loadProtocol(String protocol);
	

	
}
