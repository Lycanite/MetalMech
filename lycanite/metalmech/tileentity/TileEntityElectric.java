package lycanite.metalmech.tileentity;

import java.util.EnumSet;

import lycanite.metalmech.MetalMech;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IConnector;
import universalelectricity.core.block.IVoltage;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.electricity.IElectricityNetwork;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.implement.IDisableable;

public class TileEntityElectric extends TileEntityBase implements IConnector, IVoltage {
	
	// Electricity:
	public double voltage = 0d;
	public double voltsReceived = 0d;
	public double prevWattsReceived, wattsReceived, prevWattsGenerated, wattsGenerated, prevAmpsStored, ampsStored = 0d;
	public double wattCost = 0d;
	public double wattGeneration = 0d;
	public double wattGenerationThreshhold = 0d;
	public double wattGenerationMin = 0d;
	public double jouleStorage = 0d;
	public double requestedWatts = 0d;
	
	// Generating:
	public IElectricityNetwork outputNetwork = null;
	
	
	// ==================================================
	//                     Electricity
	// ==================================================
	// Get Voltage:
	@Override
	public double getVoltage() {
		return voltage;
	}
	
	// Get Volts Received:
	public double getVoltsReceived() {
		return voltsReceived;
	}
	
	// Set Volts Received:
	public void setVoltsReceived(double volts) {
		voltsReceived = volts;
	}
	
	// Get Watts Generated:
	public double getWattsGenerated() {
		return wattsGenerated;
	}
	
	// Set Watts Generated:
	public void setWattsGenerated(double watts) {
		wattsGenerated = Math.max(Math.min(watts, wattGeneration), 0);
	}
	
	// Get Amps Stored:
	public double getAmpsStored() {
		return ampsStored;
	}
	
	// Set Amps Stored:
	public void setAmpsStored(double watts) {
		ampsStored = Math.max(Math.min(watts, jouleStorage), 0);
	}
	
	// Needs to Charge:
	public boolean needsCharge() {
		return getAmpsStored() < jouleStorage;
	}
	
	// Has Output Connection:
	public boolean hasOutputConnection() {
		return outputNetwork != null && requestedWatts > 0;
	}
	
	// Invalidate:
	@Override
	public void invalidate() {
		ElectricityNetworkHelper.invalidate(this);
		super.invalidate();
	}
	
	// Sufficient Watts:
	public boolean sufficientWatts() {
		return wattsReceived >= wattCost;
	}
	
	// Update Watt Generation Minium:
	public void updateWattGenerationMin() {
		wattGenerationMin = wattGeneration * wattGenerationThreshhold;
	}
	
	
	// ==================================================
	//                     Behaviour
	// ==================================================
	// ========= Entity Update ==========
	@Override
	public void updateEntity() {
		super.updateEntity();
			
		prevWattsReceived = wattsReceived;
		prevWattsGenerated = wattsGenerated;
		prevAmpsStored = ampsStored;
		
		// Get Networks:
		if (!worldObj.isRemote) {
			//wattsReceived = 0;
			
			// Output Network:
			if(wattGeneration > 0) {
				TileEntity outputTile;
				outputNetwork = null;
				outputTile = getOutputTile();
				outputNetwork = ElectricityNetworkHelper.getNetworkFromTileEntity(outputTile, getOutputSide());
				
				// If there is a hungry output network:
				if(outputNetwork != null) {
					requestedWatts = outputNetwork.getRequest().getWatts();
				}
				else
					requestedWatts = 0;
			}
			
			// Input Network:
			if(!isDisabled()) {
				if(wattCost > 0) {
					// Begin Draining:
					ElectricityPack electricityPack = ElectricityNetworkHelper.consumeFromMultipleSides(this, getInputSides(), getRequest());
					onReceive(electricityPack);
				}
			}
			else {
				if(wattCost > 0) {
					// Stop Draining:
					ElectricityNetworkHelper.consumeFromMultipleSides(this, new ElectricityPack());
				}
			}
		}
	}
	
	
	// Can Connect:
	@Override
	public boolean canConnect(ForgeDirection direction) {
		return direction == ForgeDirection.getOrientation(this.facing).getOpposite();
	}
	
	
	// Input
	protected EnumSet<ForgeDirection> getInputSides() {
		return ElectricityNetworkHelper.getDirections(this);
	}
	
	
	// Output:
	public ForgeDirection getOutputSide() {
		return ForgeDirection.getOrientation(facing).getOpposite();
	}
	public TileEntity getOutputTile() {
		return VectorHelper.getConnectorFromSide(worldObj, new Vector3(xCoord, yCoord, zCoord), getOutputSide());
	}
	
	
	// Get Request:
	public ElectricityPack getRequest() {
		return new ElectricityPack();
	}
	
	
	// Drain Battery:
	public void drainBattery(ItemStack itemStack) {
		double batteryCharge = 0;
		if(jouleStorage <= 0 || needsCharge())
			batteryCharge = ElectricItemHelper.dechargeItem(itemStack, wattCost, getVoltage());
		if(jouleStorage <= 0)
			wattsReceived += batteryCharge;
		if(jouleStorage > 0)
			setAmpsStored(getAmpsStored() + batteryCharge);
	}
	
	
	// Charge Item:
	public void chargeItem(ItemStack itemStack) {
		if(jouleStorage > 0)
			setAmpsStored(getAmpsStored() - ElectricItemHelper.chargeItem(itemStack, Math.min(wattGeneration, getAmpsStored()), getVoltage()));
		else if(wattsGenerated > 0)
			ElectricItemHelper.chargeItem(itemStack, wattsGenerated, getVoltage());
	}
	
	
	// On Receive: Power Incoming
	public void onReceive(ElectricityPack electricityPack) {
		setVoltsReceived(electricityPack.voltage);
		// Overload:
		if (UniversalElectricity.isVoltageSensitive) {
			if(electricityPack.voltage > this.getVoltage()) {
				worldObj.createExplosion(null, xCoord, yCoord, zCoord, 1.5f, true);
				return;
			}
		}
		
		setAmpsStored(getAmpsStored() + electricityPack.getWatts());
        wattsReceived = Math.min(wattsReceived + electricityPack.getWatts(), getWattBuffer());
	}
	
	
	// Get Watt Buffer (Temporary Storage):
	public double getWattBuffer() {
		return getRequest().getWatts() * 2;
	}
	
	
	// Update Producing: Power Outgoing
	public void updateProducing() {
		updateWattGenerationMin();
		if(hasOutputConnection()) {
			if(wattsGenerated > wattGenerationMin) {
				outputNetwork.startProducing(this, wattsGenerated / getVoltage(), getVoltage());
				setAmpsStored(getAmpsStored() - wattsGenerated);
			}
			else
				outputNetwork.stopProducing(this);
		}
	}
	
	
	// Power Changed:
	public boolean powerChanged() {
		return prevWattsGenerated <= 0 && wattsGenerated > 0 || prevWattsGenerated > 0 && wattsGenerated <= 0;
	}
	
	
	// ==================================================
	//                        GUI
	// ==================================================
	// Get Scaled Volts:
	public int getScaledVolts(int scale, boolean useOutput) {
		double volts = voltsReceived;
		if(useOutput)
			volts = getVoltage();
		if(volts > MetalMech.highVoltage)
			return (int)((scale * 0.75) + ((volts - MetalMech.highVoltage) * (scale * 0.25) / (MetalMech.extremeVoltage - MetalMech.highVoltage)));
		else if(volts > MetalMech.mediumVoltage)
			return (int)((scale * 0.5) + ((volts - MetalMech.mediumVoltage) * (scale * 0.25) / (MetalMech.highVoltage - MetalMech.mediumVoltage)));
		else if(volts > MetalMech.lowVoltage)
			return (int)((scale * 0.25) + ((volts - MetalMech.lowVoltage) * (scale * 0.25) / (MetalMech.mediumVoltage - MetalMech.lowVoltage)));
		else
			return (int)(volts * (scale * 0.25) / MetalMech.lowVoltage);
	}
	

	// Get Scaled Heat:
	public int getScaledHeat(int scale) {
		updateWattGenerationMin();
		if(getWattsGenerated() < wattGenerationMin)
			return (int)(getWattsGenerated() * (scale * 0.5) / wattGenerationMin);
		else if(getWattsGenerated() < wattGeneration - wattGenerationMin)
			return (int) ((scale * 0.5) + (getWattsGenerated() * (scale * 0.5) / (wattGeneration - wattGenerationMin)));
		else
			return scale;
	}
	

	// Get Scaled Watts Generated:
	public int getScaledWattsGenerated(int scale) {
		return (int)(wattsGenerated * scale / wattGeneration);
	}
	

	// Get Scaled Amps Stored:
	public int getScaledAmpsStored(int scale) {
		return (int)(ampsStored * scale / jouleStorage);
	}
}
