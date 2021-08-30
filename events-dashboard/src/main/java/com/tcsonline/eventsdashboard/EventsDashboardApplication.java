package com.tcsonline.eventsdashboard;

//import java.sql.Statement;
//import java.util.ArrayList;
//import java.sql.Connection;
//import java.sql.ResultSet;

//import javax.sql.DataSource;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class EventsDashboardApplication //implements CommandLineRunner
{

	//@Autowired
	//DataSource dataSource;
	public static void main(String[] args) {
		SpringApplication.run(EventsDashboardApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		try (Connection connection = dataSource.getConnection()) {
//			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS event (link varchar(100), name varchar(20), start timestamp, end timestamp)");
//			stmt.executeUpdate("INSERT INTO event(link, name, start, end) VALUES ('http://localhost:2332', 'test1', now(), now())");
//			stmt.executeUpdate("INSERT INTO event(link, name, start, end) VALUES ('http://localhost:6554', 'test2', now(), now())");
//			ResultSet rs = stmt.executeQuery("SELECT link FROM event");
//
//			ArrayList<String> output = new ArrayList<String>();
//			while (rs.next()) {
//				output.add("Read from DB: " + rs.getString("link"));
//			}
//			
//		} catch (Exception e) {
//			System.out.println("error occured in adding events to db!, "+ e);
//		}
//	}

}
