package com.johnymuffin.discordcore;

import javax.security.auth.login.LoginException;
import org.bukkit.event.Listener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Discordbot extends ListenerAdapter implements Listener {
	public DiscordCore plugin;
	public JDA jda;
	String channel = "";
	String serverName = "";
	public Discordbot(DiscordCore main, String token) {
		this.plugin = main;	
		startBot(token);
		jda.addEventListener(this);
	}

	private void startBot(String token) {
		try {
			jda = new JDABuilder(AccountType.BOT)
					.setToken(token).build();
		} catch (LoginException e) {
			e.printStackTrace();
		}

	}




	public void DiscordbotStop() {
		System.out.println("Discord Bot Will Begin Shutdown: " + jda.getStatus());
		jda.shutdownNow();

	}
	public void DiscordSendToChannel(String channel, String message) {
		if(jda.getStatus() == JDA.Status.CONNECTED) {
			TextChannel textChannel = jda.getTextChannelById(channel);
			textChannel.sendMessage(message).queue();
		} else {
			System.out.println("Message is unable to send, Discord still starting: " + jda.getStatus());
		}
		
	}
//	public void discordChatEvent(String player, String chat) {
//		String message = chat;
//		TextChannel textChannel = jda.getTextChannelById(channel);
//		textChannel.sendMessage("**" + player + "**: " + message).queue();
//	}

}