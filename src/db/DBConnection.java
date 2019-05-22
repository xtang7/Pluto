package db;

import java.util.Set;
import entity.Machine;

public interface DBConnection {
	
	public void close();
	
	public Set<Machine> getTaskStatus(String userId);
	
	public Set<Machine> getAvailMachine();
	
	public Set<Machine> getAllMachines();
	
	public Machine getMachineStatus(String machineId);
	
	public boolean createMachineSQL(String machineId);
	
	public boolean startWashingSQL(String machineId,String userId, String startTime, String endTime);
	
	public boolean endWashingSQL(String machineId);
	
	public String getFullName(String userId);
	
	public boolean verifyLogin(String userId, String password);

	public boolean registerUser(String userId, String password, String firstname, String lastname);
	

}
