package comunicators;


import java.net.ServerSocket;
import java.net.Socket;

import abstractions.ServerException;
import mediators.RequestHTTP;

public class SocketComunicator extends Comunicator{
	public static ServerSocket serverSockets;
	
	public boolean working;
	public Socket contextSocket;
	public boolean isWorking()
	{
		return working;
	}
	
	
	
	public static SocketComunicator runServer() throws ServerException{
		return null;
	}
	
	public RequestHTTP reciveRequest()
	{
		return null;
	}
	
}
