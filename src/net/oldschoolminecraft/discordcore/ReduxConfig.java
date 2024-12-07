package net.oldschoolminecraft.discordcore;

import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.Arrays;

public class ReduxConfig extends Configuration
{
    public ReduxConfig(File file) {
        super(file);
        reload();
    }

    public void reload()
    {
        load();
        write();
        save();
    }

    private void write()
    {
        generateConfigOption("token", "INSERT_TOKEN_HERE");
        generateConfigOption("intents", Arrays.asList("GUILD_MEMBERS", "DIRECT_MESSAGES", "MESSAGE_CONTENT"));
    }

    private void generateConfigOption(String key, Object defaultValue)
    {
        if (this.getProperty(key) == null) this.setProperty(key, defaultValue);
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    public Object getConfigOption(String key)
    {
        return this.getProperty(key);
    }

    public Object getConfigOption(String key, Object defaultValue)
    {
        Object value = getConfigOption(key);
        if (value == null) value = defaultValue;
        return value;
    }
}
