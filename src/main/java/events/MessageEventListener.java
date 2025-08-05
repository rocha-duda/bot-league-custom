package events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageEventListener extends ListenerAdapter {


	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		super.onMessageReceived(event);
		if(event.getAuthor().isBot()){
			System.out.println("O bot falou: " + event.getMessage().getContentDisplay()); //shows if it as user or a bot's message
		}
		else
		System.out.println(event.getMessage().getMember().getUser().getEffectiveName() +" falou: " + event.getMessage().getContentDisplay());
		
	}
	

	

}
