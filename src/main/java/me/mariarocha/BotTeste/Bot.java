package me.mariarocha.BotTeste;

import javax.security.auth.login.LoginException;

import commands.*;
import events.*;
import events.ReadyEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class Bot {

	public static void main(String[] args) throws LoginException, Exception {
	
	JDABuilder jdaBuilder= JDABuilder.createDefault(Token.getToken()); //the bot exists 
	
	JDA jda= jdaBuilder //enabling the bot and putting the classes in it
		.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
		.addEventListeners(new BotCommands(), new ReadyEventListener(), //new MessageEventListener(),
		 new MusicCommands(), new InhouseEvent()) //put the event class here
		.build();
		
	//trying sum slash commands
	//jda.upsertCommand("sl", "descrição").setGuildOnly(true).queue(); -> Outra forma de usar 
	jda.updateCommands().addCommands(
			Commands.slash("invite", "Cria um link de convite pro servidor"),
			Commands.slash("pergunta","DudinhaBot vai tirar suas duvidas, ela sabe muito!")
			.addOption(OptionType.STRING, "pergunta", "faça sua pergunta"),
			Commands.slash("join", "O bot entra na call"),
			Commands.slash("play", "O bot toca musica").addOption(OptionType.STRING, "song", "link da musica"),
			Commands.slash("leave", "o bot sai do canal de voz"),
			Commands.slash("tocandoagora", "o bot mostra o que ta tocando"),
			Commands.slash("skip", "skipa a musica ")
			).queue();

	
}
}
