package tools;

import mediators.RequestHTTP;
import comunicators.SocketComunicator;

public class Context {
	private long id;
	private RequestHTTP requestHTTP;
	private SocketComunicator comunicator;
	private Thread thread;
	
	public Context(long id,SocketComunicator comunicator)
	{
		setComunicator(comunicator);
		setRequestHTTP(comunicator.reciveRequest());
		setId(id);
	}
	
	private void setComunicator(SocketComunicator comunicator)
	{
		this.comunicator = comunicator;
	}
	public SocketComunicator getComunicator()
	{
		return comunicator;
	}
	private void setRequestHTTP(RequestHTTP requestHTTP)
	{
		this.requestHTTP  = requestHTTP;
	}
	
	public RequestHTTP getRequestHTTP()
	{
		return requestHTTP;
	}
	
	public void setThread(Thread thread)
	{
		this.thread  = thread;
	}
	
	public Thread getThread()
	{
		return thread;
	}

	public long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

}

