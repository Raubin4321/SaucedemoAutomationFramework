package com.saucedemo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties prop;
	private static FileInputStream fis;

	static {
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
			prop = new Properties();
			prop.load(fis);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config.properties file");
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
}
