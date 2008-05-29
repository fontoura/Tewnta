package br.ufrgs.f180.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameProperties extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static GameProperties instance;

	private GameProperties(){
		try {
			InputStream f = ClassLoader.getSystemResourceAsStream("game.properties");
			load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static GameProperties getInstance(){
		if(instance == null){
			instance = new GameProperties();
		}
		return instance;
	}
	
	public static double getDoubleValue(String key){
		return Double.valueOf(getInstance().getProperty(key));
	}

	public static int getIntValue(String key){
		return Integer.valueOf(getInstance().getProperty(key));
	}
}
