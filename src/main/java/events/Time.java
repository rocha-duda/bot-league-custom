package events;

import java.util.ArrayList;
import net.dv8tion.jda.api.entities.User;

public class Time {
	private ArrayList<User> players;
	private String side;
	
	public Time(ArrayList<User> players, String side) {
		this.players = players;
		this.side = side;
	}

	public ArrayList<User> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<User> players) {
		this.players = players;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}
	
	
}
