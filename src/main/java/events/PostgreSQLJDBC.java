package events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.dv8tion.jda.api.entities.User;

public class PostgreSQLJDBC {
	String JDBCurl = "jdbc:postgresql://localhost:5432/inhouse";
	String username = "postgres";
	String password = "1411";
	Connection connection;
	
	public void AddUser(User user) throws SQLException {
		connection = DriverManager.getConnection(JDBCurl, username, password);
		String sql = "insert into tabela(usuario,njogos,vitorias,winrate) values (?,0,0,0)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, user.getId());
		
	}
	
	public void Win(Time vitoria, Time derrota) throws SQLException {
		connection = DriverManager.getConnection(JDBCurl, username, password);
		
		for(int i=0; i<5; i++) {
			User player = vitoria.getPlayers().get(i);
			String sql = "update tabela set vitorias = vitorias +1 , njogos = njogos +1,winrate = (vitorias+1)/(njogos+1)*100 where usuario=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, player.getId());
			
			
		}
		for(int i=0; i<5; i++) {
			User player = derrota.getPlayers().get(i);
			String sql = "update tabela set njogos = njogos +1, winrate = vitorias/(njogos+1)*100 where usuario=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, player.getId());
			
			
		}
	}
	

	
}
