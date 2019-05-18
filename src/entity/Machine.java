package entity;

import org.json.JSONObject;


public class Machine {
	private String machineId;
	private String userId;
	private boolean availability;
	private String startTime;
	private String endTime;
	
	private Machine(MachineBuilder builder) {
		this.machineId = builder.machineId;
		this.userId = builder.userId;
		this.availability = builder.availability;
		this.startTime = builder.startTime;
		this.endTime = builder.endTime;	
	}

	public String getMachineId() {
		return machineId;
	}

	public String getUserId() {
		return userId;
	}

	public boolean getAvailability() {
		return availability;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	
	public void printAllInfo() {
		System.out.println("Machine ID: " + machineId); 
		System.out.println("Avalibity: " + availability); 
		System.out.println("User ID: " + userId); 
		System.out.println("Start Time: " + startTime); 
		System.out.println("End Time: " + endTime); 
		
	}

	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		try {
			object.put("machine_id", machineId);
			object.put("user_id", userId);
			object.put("availability", availability);
			object.put("start_time", startTime);
			object.put("end_time", endTime);
			} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	public static class MachineBuilder {
		private String machineId;
		private String userId;
		private boolean availability;
		private String startTime;
		private String endTime;

		public MachineBuilder setMachineId(String machineId) {
			this.machineId = machineId;
			return this;
		}

		public MachineBuilder setUserId(String userId) {
			this.userId = userId;
			return this;
		}

		public MachineBuilder setAvailability(boolean availability) {
			this.availability = availability;
			return this;
		}

		public MachineBuilder setStartTime(String startTime) {
			this.startTime = startTime;
			return this;
		}

		public MachineBuilder setEndTime(String endTime) {
			this.endTime = endTime;
			return this;
		}

		
		public Machine build() {
			return new Machine(this);
		}

	}

}
