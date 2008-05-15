package br.ufrgs.f180.server;

import javax.xml.ws.Endpoint;

import br.ufrgs.f180.api.PlayerImpl;

public class Server {
	private static final String ADDRESS = "http://localhost:9000/player";
	private Endpoint endpoint;
	private PlayerImpl implementor;
	
	public void startServer() throws Exception {
		// START SNIPPET: publish
		System.out.println("Starting Server");
		implementor = new PlayerImpl();
		endpoint = Endpoint.create(implementor);
		endpoint.publish(ADDRESS);
		// END SNIPPET: publish
	}

	public void stopServer() throws Exception {
		// START SNIPPET: publish
		System.out.println("Stopping Server");
		endpoint.stop();
	}
	
	public static void main(String args[]) throws Exception {
		Server s = new Server();
		s.startServer();
		System.out.println("Server ready...");

		Thread.sleep(60 * 1000);
		System.out.println("Server exiting");
		s.stopServer();
		System.exit(0);
	}

	public boolean isStarted() {
		return endpoint != null && endpoint.isPublished();
	}

}
