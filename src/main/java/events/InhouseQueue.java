package events;
import java.util.ArrayList;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import java.util.Collections;

public class InhouseQueue {

	ArrayList<User> users = new ArrayList<>();
	String players = new String();
	EmbedBuilder embed;
	String blue = new String();
	String red = new String();

	public void metionUsersAdd() {
		for(User u : users) {                         
			players += u.getAsMention() + ",";    }
	}
	public void randomizar(){
		Collections.shuffle(this.users);
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public void add(User usuario) {
		users.add(usuario);
	}
	public void remove(User usuario) {
		users.remove(usuario);
	}
	public void clear(){
		users.clear();
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
	}

	public InhouseQueue() {
		embed = new EmbedBuilder();
		embed.setTitle("Fila atual do inhas");
		embed.addField("Players: ", this.getPlayers() , false);
		embed.setDescription("numero de players na fila: " + this.getUsers().size());
		embed.setImage("https://i.imgur.com/kYE7iNg.jpg");
	}

	public EmbedBuilder getEmbed() {
	    embed.clearFields(); // Limpar campos existentes antes de adicionar novos
	    embed.addField("Players: ", this.getPlayers(), false);
	    embed.setDescription("Número de players na fila: " + this.getUsers().size());
	    return embed;
	}


	public void setEmbed(EmbedBuilder embed) {
		this.embed = embed;
	}
	
	public String getBlue() {
		for(int i=0; i<5; i++) {
			blue+= this.getUsers().get(i).getAsMention() + ",";
		}
		return blue;
	}
	public String getRed() {
		for(int i=5; i<10; i++) {
			red+= this.getUsers().get(i).getAsMention() + ",";
		}
		return red;
	}



}
