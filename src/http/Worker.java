package http;

import java.net.Socket;

public class Worker implements Runnable {
	private Thread thread;
	private boolean sair;
	private Socket socket;
	
	public Worker(Socket socket) {
		this.thread = null;
		this.sair = false;
		this.socket = socket;
	}
	
	public void start() {
		if (thread != null) {
			return;
		}
	
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		System.out.println("Working - #" + Thread.currentThread().getId() + " *******************");

		try {
			Response resp = new Response(socket.getOutputStream());

			Request req = new Request(socket.getInputStream());
			req.setResponse(resp);

			System.out.println(req);

			Dispatcher disp = new Dispatcher();
			disp.resolve(req, resp);
		} catch(Exception e) {
			System.out.println(socket);
			e.printStackTrace();			
		} finally {
			try {
				System.out.println(socket);
				System.out.println("Close Connection - #" + Thread.currentThread().getId() + " *******************");				
				socket.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}	
}













