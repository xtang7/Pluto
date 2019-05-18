package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.List;
import java.util.Set;

import db.DBConnection;
import db.DBConnectionFactory;

import entity.Machine;
import entity.Machine.MachineBuilder;

public class MySQLConnection  {
	private Connection conn;

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// get userName 
	
	//registerUser 
	
	//getTaskStatus 
	public Set<Machine> getTaskStatus(String userId) { 
		if(conn == null) {
			return null; 
		}
		Set<Machine> machinesInUse = new HashSet<>();  
		try {
			String sql = "SELECT * FROM machines WHERE user_id = ?"  ;
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,userId); 
			
			ResultSet rs = stmt.executeQuery(); 
			
			MachineBuilder builder = new MachineBuilder();
			while(rs.next()) {
				
				builder.setMachineId(rs.getString("machine_id")); 
				builder.setUserId(rs.getString("user_id")); 
				builder.setAvailability(rs.getBoolean("availability")); 
				builder.setStartTime(rs.getString("start_time")); 
				builder.setEndTime(rs.getString("end_time")); 
				
				machinesInUse.add(builder.build()); 
			}
			 
		}catch (SQLException e) {
			e.printStackTrace(); 
		}
		return machinesInUse; 
	}
	//get all avail Machine  
	public Set<Machine> getAvailMachine() { 
		if(conn == null) {
			return null; 
		}
		Set<Machine> availMachines = new HashSet<>();
		
		try {
			String sql = "SELECT * FROM machines WHERE availability = TRUE"  ;
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery(); 
			
			while(rs.next()) {
				MachineBuilder builder = new MachineBuilder(); 
				builder.setMachineId(rs.getString("machine_id")); 
				builder.setUserId(rs.getString("user_id")); 
				builder.setAvailability(rs.getBoolean("availability")); 
				builder.setStartTime(rs.getString("start_time")); 
				builder.setEndTime(rs.getString("end_time")); 
				
				availMachines.add(builder.build()); 
			}
			  
		}catch (SQLException e) {
			e.printStackTrace(); 
		}
		return availMachines; 
	}
	
	//get all machines status 
	
	public Set<Machine> getAllMachines() { 
		if(conn == null) {
			return null; 
		}
		Set<Machine> allMachines = new HashSet<>();
		
		try {
			String sql = "SELECT * FROM machines" ;
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery(); 
			
			while(rs.next()) {
				MachineBuilder builder = new MachineBuilder(); 
				builder.setMachineId(rs.getString("machine_id")); 
				builder.setUserId(rs.getString("user_id")); 
				builder.setAvailability(rs.getBoolean("availability")); 
				builder.setStartTime(rs.getString("start_time")); 
				builder.setEndTime(rs.getString("end_time")); 
				
				allMachines.add(builder.build()); 
			}
			  
		}catch (SQLException e) {
			e.printStackTrace(); 
		}
		return allMachines; 
	}
	// get one machine status 
	public Machine getMachineStatus(String machineId) { 
		if(conn == null) {
			return null; 
		}	
		
		try {
			String sql = "SELECT * FROM machines WHERE machine_id = ?" ;
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, machineId); 
			ResultSet rs = stmt.executeQuery(); 
			
			if(rs.next()) {
				MachineBuilder builder = new MachineBuilder(); 
				builder.setMachineId(rs.getString("machine_id")); 
				builder.setUserId(rs.getString("user_id")); 
				builder.setAvailability(rs.getBoolean("availability")); 
				builder.setStartTime(rs.getString("start_time")); 
				builder.setEndTime(rs.getString("end_time")); 
				return builder.build();
			}	  
		}catch (SQLException e) {
			e.printStackTrace(); 
		}
		return null; 
	}
	
	// start washing 
	public Machine startWashingSQL( String machineId,String userId, String startTime, String endTime) { 
		if(conn == null) {
			return null; 
		}
		try {
			String sql = "UPDATE machines SET availability = ?,"
					+ "user_id = ?,start_time = ?, end_time = ? WHERE machine_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, "0");
			ps.setString(2, userId);
			ps.setString(3, startTime);
			ps.setString(4,	endTime);
			ps.setString(5, machineId);
			
			System.out.println("This is the statement for startWashing");
			System.out.println(ps); 
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getMachineStatus(machineId); 		
	}
	
	
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
