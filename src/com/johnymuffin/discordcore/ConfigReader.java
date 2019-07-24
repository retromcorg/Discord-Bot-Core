package com.johnymuffin.discordcore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class ConfigReader {
	// New Config
	private static String token = "";

	ConfigReader(DiscordCore instance) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(instance.config));
		} catch (FileNotFoundException var4) {
			instance.logger.warning("[DiscordCore] No properties found! Making new file...");
			var4.printStackTrace();
		} catch (IOException var5) {
			var5.printStackTrace();
		}
		// New Config
		token = prop.getProperty("token", token);
		prop.setProperty("token", token);

		try {
			prop.store(new FileOutputStream(instance.config), "Properties for DiscordCore");
		} catch (Exception var3) {
			instance.logger.severe("Failed to save properties for RetroBot!");
			var3.printStackTrace();
		}
	}


	String getToken() {
		return token;
	}
	

	void onDisable() {
		token = null;
	}
}
