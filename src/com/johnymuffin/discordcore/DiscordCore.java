package com.johnymuffin.discordcore;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.oldschoolminecraft.discordcore.ReduxConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscordCore extends JavaPlugin implements Listener {
    //Basic Plugin Info
    private static DiscordCore plugin;
    private Logger log;
    private String pluginName;
    private PluginDescriptionFile pdf;
    //Plugin Fields
    private DiscordBot discord;
    private ReduxConfig reduxConfig;


    public void onEnable() {
        plugin = this;
        log = Bukkit.getServer().getLogger();
        pdf = this.getDescription();
        pluginName = pdf.getName();
        logInfo(Level.INFO, "Is Loading, Version: " + pdf.getVersion() + ".");
        logInfo(Level.INFO, "THIS PLUGIN IS LICENSED UNDER GNU.");

        reduxConfig = new ReduxConfig(new File(getDataFolder(), "config.yml"));

        //Config Information Start
        String softToken = reduxConfig.getString("token", "INSERT_TOKEN_HERE");

        if(softToken == null || softToken.equalsIgnoreCase("INSERT_TOKEN_HERE") || softToken.isEmpty()) {
            plugin.logInfo(Level.WARNING, "Failed to find a Discord token in the config file, shutting down.");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        ArrayList<String> rawIntentList = (ArrayList<String>) reduxConfig.getConfigOption("intents");
        ArrayList<GatewayIntent> intents = new ArrayList<>();
        for (String str : rawIntentList)
        {
            GatewayIntent intent = null;

            try
            {
                intent = GatewayIntent.valueOf(str);
            } catch (IllegalArgumentException ignored) {}

            if (intent != null) intents.add(intent);
        }

        //Config Information End
        logInfo(Level.INFO, "Starting internal Discord Bot.");
        try {
            discord = new DiscordBot(this);
            discord.startBot(softToken, intents);
        } catch (Exception e) {
            logInfo(Level.WARNING, e + ": " + e.getMessage());
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }
    }

    public void onDisable() {
        logInfo(Level.INFO, "Disabling plugin.");
        if (discord != null) {
            DiscordShutdownEvent shutdownEvent = new DiscordShutdownEvent();
            Bukkit.getServer().getPluginManager().callEvent(shutdownEvent);

            discord.discordBotStop();
        }
        logInfo(Level.INFO, "Disabled.");
    }

    @Deprecated
    public DiscordBot Discord() {
        //Return Bot for other plugins
        return discord;
    }

    public DiscordBot getDiscordBot() {
        return discord;
    }

    public void logInfo(Level level, String s) {
        log.log(level, "[" + pluginName + "] " + s);
    }


}
