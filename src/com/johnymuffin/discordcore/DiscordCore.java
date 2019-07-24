package com.johnymuffin.discordcore;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordCore extends JavaPlugin implements Listener {
	public ConsoleCommandSender console;
	Logger logger;
	Discordbot Discord;
	// Config
	private ConfigReader configReader;
	public static final String PLUGIN_FOLDER = "./plugins/DiscordCore";
	private File pluginFolder = new File("./plugins/DiscordCore");
	File config;

	public void onLoad() {
		this.config = new File(this.pluginFolder, "config.properties");
		this.logger = getServer().getLogger();
		if (!this.pluginFolder.exists() || !this.pluginFolder.exists()) {
			this.pluginFolder.mkdirs();
		}

		if (!this.config.exists()) {
			try {
				this.config.createNewFile();
			} catch (IOException var2) {
				var2.printStackTrace();
			}
		}
	}

	public void onEnable() {
		this.configReader = new ConfigReader(this);
		this.logger = getServer().getLogger();
		this.logger.info("[DiscordCore] Enabling DiscordCore");
		this.logger.info("[DiscordCore] THIS PLUGIN IS LICENSED UNDER GNU");
		//this.logger.info("[DiscordCore] TOKEN " + this.configReader.getToken());		
		if(!this.configReader.getToken().isEmpty()) {
			Discord = new Discordbot(this, this.configReader.getToken());
		} else {
			this.logger.warning(
					"----------------------------------------------------------\nPLEASE PROVIDE A TOKEN FOR DISCORD\n----------------------------------------------------------");
		}

	}

	public void onDisable() {
		this.logger.info("[DiscordCore] Disabling DiscordCore...");
		//Bukkit.getServer().getScheduler().cancelTask(taskId);
		Discord.DiscordbotStop();
	}

	public Discordbot Discord() {
		//Return Bot for other plugins
		return Discord;
	}

}
