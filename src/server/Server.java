package server;

import java.util.HashMap;
import abstractions.ServerException;
import comunicators.SocketComunicator;
import tools.Context;
import tools.ThreadFactory;



public class Server implements Runnable{
	
	private static HashMap<Long,Server> runnings;
	private static SocketComunicator serverComunicator;
	private static long currentId = 0;
	public static String log;
	
	private Context context;
	
	
	public void init()
	{
		runnings = new HashMap<Long,Server>();
		serverComunicator = new SocketComunicator();
	}
	
	private Server(long id, SocketComunicator comunicator)
	{
		context = new Context(id, comunicator);
	}
	
	public static void start()
	{
		while(serverComunicator.isWorking())
		{
			try{
				 createContext(SocketComunicator.runServer());
			}catch(ServerException ex){listenException(ex);}
		}
	}
	
	private static long geId()
	{
		currentId++;
		return(currentId-1);
	}
	
	private static void createContext(SocketComunicator comunicator)
	{
		startContext( new Server(geId(),comunicator));
	}
	
	private static void startContext(Server server)
	{
		server.context.setThread(ThreadFactory.startThread(server));
		runnings.put(server.context.getId(),server);
	}
	
	private static void listenException(ServerException ex)
	{
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
}
