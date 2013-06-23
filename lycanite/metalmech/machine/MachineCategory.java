package lycanite.metalmech.machine;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.client.model.IModelCustom;

public class MachineCategory {
	
	// ========== Details ==========
	public String name = "CategoryName";
	public int blockID = 0;
	
	
	// ========== Machines ==========
	public Map<String, MachineInfo> machines = new HashMap<String, MachineInfo>();
	public Map<Integer, String> machinesMetadata = new HashMap<Integer, String>();
	public int machineCount = 0;
	
	
	// ========== Get Machine ==========
	public MachineInfo getMachine(String name) {
		return machines.get(name);
	}
	public MachineInfo getMachine(int metadata) {
		return getMachine(machinesMetadata.get(metadata));
	}
	
	
	// ========== Add Machine ==========
	public void addMachine(String machineName, MachineInfo machineInfo) {
		machineInfo.metadata = machineCount;
		machineInfo.category = this;
		machineInfo.updateStats();
		machines.put(machineName, machineInfo);
		machinesMetadata.put(machineCount, machineName);
		machineCount++;
	}
}
