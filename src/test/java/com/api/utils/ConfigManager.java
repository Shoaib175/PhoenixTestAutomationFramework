package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties prop = new Properties();
	private static String path = "config/config.qa.properties";
	private static String env;

	private ConfigManager() {
		// we are keeping constructor here as PRIVATE so that whenever someone is trying
		// to create an object for this class
		// they get the compile time error and that way they get the signal that this
		// class is a UTILITY CLASS
	}

	static {

		// performs loading of property file in memory
		// static block - it will be executed once during CLASS LOADING TIME
//		File configFile = new File(System.getProperty("user.dir") + File.separator+ "src"+File.separator+"test"
//		+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties");

		// Instead of using the full path like the above, you can use
		// getContextClassLoader function - which knows
		// all the paths of your project structure
//		File configFile = new File(input);
//		FileReader reader;

		env = System.getProperty("env", "qa").toLowerCase().trim(); // second parameter qa is a default value
		switch (env) {
		case "qa" -> path = "config/config.qa.properties";
		case "uat" -> path = "config/config.uat.properties";
		case "dev" -> path = "config/config.dev.properties";
		default -> path = "config/config.qa.properties";
		}

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		if (input == null) {
			throw new RuntimeException("Cannot find file at path " + path);
		}

		try {
//			reader = new FileReader();
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) throws IOException {

		return prop.getProperty(key);

	}

}
