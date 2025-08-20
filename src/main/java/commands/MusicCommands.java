package commands;

import java.net.URI;
import java.net.URISyntaxException;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import lavaPlayer.GuildMusicManager;
import lavaPlayer.PlayerManager;
import lavaPlayer.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class MusicCommands extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		super.onSlashCommandInteraction(event);
		
	
		String opc = event.getName();
	
			switch(opc) {
			case("play"):
				if(event.getMember().getVoiceState().getChannel().equals(event.getGuild().getSelfMember().getVoiceState().getChannel())) {
					
					
					
					 OptionMapping songOption = event.getOption("song");
					 String songUrl = songOption.getAsString();
					 if(songOption.getAsString()== null) {
						 event.reply("favor inserir um nome ou link").queue();
					 }
					 else
					 
					 try {
						 new URI(songUrl);
					 }
					 catch(URISyntaxException e){
						 songUrl = "ytsearch:" + songUrl;
								 
					 }
					   event.reply("tocando").queue();
					PlayerManager playerManager = PlayerManager.get();
					playerManager.play(event.getGuild(), songUrl);}
					  
					    	

				
				else {
					event.reply("Voce precisa estar no mesmo canal que o bot para fazer isso, tente entrar em um canal e usar o comando join!").queue();
				}
		
			
			break;
			
			case("join"):
				if(event.getMember().getVoiceState().getChannel()==null) {
					event.reply("voce nao esta em um canal de voz para eu fazer isso").queue();
				}
			else if(event.getGuild().getSelfMember().getVoiceState().getChannel()!=null) {
					event.reply("erro: ja estou em um canal de voz").queue();
				}
				else {
					event.reply("Entrando!").queue();
					event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
				}
			break;
			case("leave")	:
			if(event.getGuild().getSelfMember().getVoiceState().getChannel()==null) {
				event.reply("Eu nao estou em um canal de voz para sair").queue();
			}
				else if(event.getGuild().getSelfMember().getVoiceState().getChannel().equals(event.getMember().getVoiceState().getChannel())) {
					event.getGuild().getAudioManager().closeAudioConnection();
					event.reply("Saindo!").queue();}
					else {
						event.reply("Eu nao estou no mesmo canal de voz que voce").queue();
					}
			break;
			case("skip")	:
				if(event.getGuild().getSelfMember().getVoiceState().getChannel()==null) {
					event.reply("nao estou em um canal de voz para fazer isso!!").queue();
				}
				else if(!event.getGuild().getSelfMember().getVoiceState().getChannel().equals(event.getMember().getVoiceState().getChannel())) {
					event.reply("voce nao esta no mesmo canal de voz que eu para fazer isso!").queue();
				}
				else {
					GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
					guildMusicManager.getTrackScheduler().getPlayer().stopTrack();
					event.reply("Musica skipada").queue();
				}
			break;
			case("stop"):
				if(event.getGuild().getSelfMember().getVoiceState().getChannel()==null) {
					event.reply("nao estou em um canal de voz para fazer isso!").queue();
				}
				else if(!event.getGuild().getSelfMember().getVoiceState().getChannel().equals(event.getMember().getVoiceState().getChannel())) {
					event.reply("voce nao esta no mesmo canal de voz que eu para fazer isso!").queue();
				}
				else {
					GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
					TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
					trackScheduler.getQueue().clear();
					trackScheduler.getPlayer().stopTrack();
					event.reply("parou tudo").queue();
				}
			break;
			case("tocandoagora"):
				if(event.getGuild().getSelfMember().getVoiceState().getChannel()==null) {
					event.reply("nao estou em um canal de voz para fazer isso!").queue();
				}
				else if(!event.getGuild().getSelfMember().getVoiceState().getChannel().equals(event.getMember().getVoiceState().getChannel())) {
					event.reply("voce nao esta no mesmo canal de voz que eu para fazer isso!").queue();
				}
				else {
					GuildMusicManager guildMusicManager = PlayerManager.get().getGuildMusicManager(event.getGuild());
					AudioTrackInfo info = guildMusicManager.getTrackScheduler().getPlayer().getPlayingTrack().getInfo();
					EmbedBuilder eb = new EmbedBuilder();
					eb.setTitle("Tocando Agora: ");
					eb.setDescription("**Name: **" + info.title + "'");
					eb.appendDescription("\n Canal:  " + info.author);
					event.getChannel().sendMessageEmbeds(eb.build()).queue();
				}
			break;
			case("fila"):
				
		}
	}
	
		
	

}
	

