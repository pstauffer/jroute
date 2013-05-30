package ch.zhaw.jroute.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHandler {
	
	private Properties properties;
	private static ConfigHandler _instance = null;

	private ConfigHandler() {
		properties = new Properties();
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("jroute.properties");

		if (inputStream == null) {
			throw new RuntimeException("property file jroute.properties"
					+ "' not found in the classpath");
		}

		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ConfigHandler getInstance(){
		if (_instance == null){
			_instance = new ConfigHandler();
		}
		
		return _instance;
	}

	public String getConfig(String key) {

		String value = properties.getProperty(key);
		return value;

	}

	public void setConfig(String key, String value) {
		properties.setProperty(key, value);
	}

}