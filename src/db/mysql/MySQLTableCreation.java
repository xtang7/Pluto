package db.mysql;

import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.sql.Connection;

import entity.Machine;

import db.mysql.MySQLConnection;

public class MySQLTableCreation {

	public static void main(String[] args) {
		try {
			// Step 1 Connect to MySQL.
			System.out.println("Connecting to " + MySQLDBUtil.URL);
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);

			if (conn == null) {
				return;
			}

			// Step 2 Drop tables in case they exist.
			Statement statement = conn.createStatement();
			String sql = "DROP TABLE IF EXISTS categories";
			statement.executeUpdate(sql);


			sql = "DROP TABLE IF EXISTS machines";
			statement.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS users";
			statement.executeUpdate(sql);
			
			System.out.println("Import done successfully");

			// Step 3 Create new tables
			// user table

			sql = "CREATE TABLE users (" + "user_id VARCHAR(255) NOT NULL," + "password VARCHAR(255) NOT NULL,"
					+ "first_name VARCHAR(255)," + "last_name VARCHAR(255)," + "PRIMARY KEY (user_id)" + ")";
			statement.executeUpdate(sql);
			
			

			// machine table
			sql = "CREATE TABLE machines (" + "machine_id VARCHAR(255) NOT NULL," + "availability BOOLEAN DEFAULT true," +"user_id VARCHAR(255),"
					+ "start_time TIMESTAMP NULL DEFAULT NULL," + "end_time TIMESTAMP NULL DEFAULT NULL,"
					+ "PRIMARY KEY (machine_id)" +
					//")";
					",FOREIGN KEY (user_id) REFERENCES users(user_id)" + ")";
			statement.executeUpdate(sql);

			

			// Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050
			sql = "INSERT INTO users VALUES('1111', '3229c1097c00d497a0fd282d586be050', 'John', 'Smith')";
			statement.executeUpdate(sql);
			
			// Step 5: insert  current washing machines 
			// In MySQL FALSE = 0 
			
			String[] machine_ids = {"0001", "0002", "0003","0004","0005"};
			for(String machine_id : machine_ids) {
				sql = "INSERT INTO machines VALUES('"+ machine_id + "',TRUE,NULL,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
				statement.executeUpdate(sql);
				
			}
			sql = "INSERT INTO machines VALUES('0006',FALSE,'1111',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
			
statement.executeUpdate(sql);
			
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// below are for testing  
		MySQLConnection conn = new MySQLConnection(); 
		Set<Machine> machinesInUse = conn.getTaskStatus("1111"); 
		for(Machine mach : machinesInUse) {
			mach.printAllInfo(); 
		}
		
		System.out.println("---------------");
		System.out.println("All avail Machines as follow: "); 
		
		Set<Machine> availMachines = conn.getAvailMachine(); 
		for(Machine mach : availMachines) {
			mach.printAllInfo(); 
		}
		// test start washing 
		
		System.out.println("---------------");
		System.out.println("Start washing : "); 
		conn.startWashingSQL("0002","1111","2019-05-11 01:15:58","2000-05-11T23:36:39.895");
		//machInUse.printAllInfo(); 
		
	}



	
}	