package br.ufrgs.wumpus.demo;

import java.net.URL;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.log4j.Logger;

import br.ufrgs.wumpus.api.Player;

public class DemoPlayer {
	private static Logger logger = Logger.getLogger(DemoPlayer.class);
	
	static Player client;
	public static void main(String[] args) throws Exception {
		logger.debug("Iniciando simulação");
		logger.debug("Conectando com servidor");
		URL wsdlURL = new URL("http://localhost:9000/player?wsdl");
		QName SERVICE_NAME = new QName("http://api.wumpus.ufrgs.br/", "Player");
		Service service = Service.create(wsdlURL, SERVICE_NAME);
		client = service.getPort(Player.class);

		for (int i = 0; i < 5; i++) {
			logger.info("Moving forward");
			client.moveForward();
			client.rotateLeft();
			Thread.sleep(1000);
		}
		for (int i = 0; i < 5; i++) {
			logger.info("Moving forward");
			client.moveForward();
			client.rotateRight();
			Thread.sleep(1000);
		}
		logger.info("Done");
	}
}
