package events;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InhouseEvent extends ListenerAdapter {
	 String prefix = "!";
	 InhouseQueue queue = new InhouseQueue();
	 Message queueMessage;
	 ArrayList<User> blue = new ArrayList<User>();
	 ArrayList<User> red = new ArrayList<User>();
	 PostgreSQLJDBC teste = new PostgreSQLJDBC();
	 Emoji check = Emoji.fromUnicode("✅");
	 private int qtd;
	 Time time1;
	 Time time2;
	 Time vitoria;
	 Time derrota;
	 

	 
	 
	 
	public void onMessageReceived(MessageReceivedEvent event) {		
		String[] args = event.getMessage().getContentRaw().split(" ");
	 if(args[0].equalsIgnoreCase(prefix + "join")) {
		 event.getMessage().delete().queueAfter(1, TimeUnit.SECONDS);
		User user = event.getAuthor();
		if(queue.getUsers().contains(user)) {
			event.getChannel().sendMessage("Você ja está na fila!").queue();
		}
		else {	queue.add(user);
		queue.setPlayers("");
		queue.metionUsersAdd();

			if(queueMessage==null) {
				queueMessage=event.getChannel().sendMessageEmbeds(queue.getEmbed().build()).complete();
			}
			else {
				queueMessage.editMessageEmbeds(queue.getEmbed().build()).queue();
			}
			if(queue.getUsers().size()==10) {
				EmbedBuilder eb = new EmbedBuilder();
				queue.randomizar();
				eb.setTitle("Partida encontrada!");
				eb.addField("Blue side: ", 	queue.getBlue() ,false );
				eb.addField("Red side", queue.getRed(), false );
				Color orange = new Color(255,69,0);
				eb.setColor(orange);
				event.getChannel().sendMessageEmbeds(eb.build()).queue();

			VoiceChannel audioblue = event.getGuild().createVoiceChannel("Blue Side").complete();
			VoiceChannel audiored =	event.getGuild().createVoiceChannel("Red Side").complete();
				for(int i=0; i<5; i++) {
					event.getGuild().moveVoiceMember(event.getGuild().getMember(queue.getUsers().get(i)), audioblue ).queue();
					blue.add(queue.getUsers().get(i));
				}
				for(int i=5; i<10; i++) {
					event.getGuild().moveVoiceMember(event.getGuild().getMember(queue.getUsers().get(i)), audiored ).queue();
					red.add(queue.getUsers().get(i));
				}
				time1= new Time(blue, "blue");
				time2= new Time(red, "red");
				queue.clear();
				queueMessage =null;
			}}
	 }
	 else if(args[0].equalsIgnoreCase(prefix + "leave")) {
		User user = event.getAuthor();
		queue.remove(user);	
		queue.setPlayers("");
		queue.metionUsersAdd();
		queueMessage.editMessageEmbeds(queue.getEmbed().build()).queue();
		 

	 }
	 
	 else if(args[0].equalsIgnoreCase(prefix + "win")) {
	event.getChannel().sendMessage(event.getAuthor().getAsMention() +  " esta afirmando vitoria para o side! Vote para confirmar" ).queue(
			m -> {m.addReaction(Emoji.fromUnicode("✅")).queue();});
	if(time1.getPlayers().contains(event.getAuthor())) {
		vitoria = time1;
		derrota = time2;
	}
	else {
		vitoria = time2;
		derrota = time1;
	}
	
	
	 }
	 else if(args[0].equalsIgnoreCase(prefix + "teste")) {
		User user = event.getAuthor();
		try {
			teste.AddUser(user);
			event.getChannel().sendMessage("teste deu certo!").queue();
		} catch (SQLException e) {
			event.getChannel().sendMessage("voce ja esta registrado, jogador").queue();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	 }
	 else if(args[0].equalsIgnoreCase(prefix + "caio")) {
	event.getChannel().sendMessage("https://imgur.com/a/xZWSK4B").queue();
	 }
	else if(args[0].equalsIgnoreCase(prefix + "irmaos")) {
	event.getChannel().sendMessage("https://imgur.com/nWcHkFN").queue();
		}
	 else if(args[0].equalsIgnoreCase(prefix + "matshow")) {
	event.getChannel().sendMessage("https://imgur.com/9KUDZJJ").queue(); 
	 }
	 else if(args[0].equalsIgnoreCase(prefix + "eow")) {
			event.getChannel().sendMessage("https://imgur.com/a/njnRK7d").queue(); 
			 }
	
	 
 }
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		if(event.getMessageAuthorId().equals("1186340855443886181")) {
			event.retrieveMessage().queue(msg-> {
				MessageReaction reaction = msg.getReaction(check);
				qtd = reaction.getCount();
				System.out.println(qtd);
			}
			);

		}
		if(qtd==2) {
			event.getChannel().sendMessage("ganharam").queue();
			System.out.println(vitoria.getPlayers().get(0).getAsMention());
			
			try {
				teste.Win(vitoria,derrota);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
}
