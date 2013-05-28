package ch.zhaw.jroute.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigHandler {
	Properties properties;

	public ConfigHandler() {
		properties = new Properties();

		try {
			properties.load(new FileInputStream(
					"src/main/resources/jroute.properties"));
			System.out.println(properties.getProperty("openStreetMapBoxURL"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getConfig(String key) {

		String value = properties.getProperty(key);
		return value;

	}

	public void setConfig(String key, String value) {
		properties.setProperty(key, value);
	}

}