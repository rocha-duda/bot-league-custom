package commands;


import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;




public class BotCommands extends ListenerAdapter{

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) { //the bot generates an invitation link to join another guild
		super.onSlashCommandInteraction(event);
		if(event.getName().equals("invite")) {
		event.reply("O link para me adicionar no servidor é: " + event.getJDA().getInviteUrl()).queue();
		}
				
	}


}
