package lycanite.metalmech.tileentity;

import java.util.EnumSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.block.IConnector;
import universalelectricity.core.block.IVoltage;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.prefab.implement.IDisableable;

public class TileEntityElectric extends TileEntityBase implements IConnector, IVoltage {
	
	// Electricity:
	public double prevWatts, wattsReceived = 0;
	public double wattsPerTick = 500.0D;
	
	
	// ==================================================
	//                     Electricity
	// ==================================================
	// Get Voltage:
	@Override
	public double getVoltage() {
		return 120;
	}

	// Can Connect:
	@Override
	public boolean canConnect(ForgeDirection direction) {
		return direction == ForgeDirection.getOrientation(this.facing).getOpposite();
	}
	
	// Invalidate:
	@Override
	public void invalidate() {
		ElectricityNetworkHelper.invalidate(this);
		super.invalidate();
	}
	
	
	// ==================================================
	//                     Behaviour
	// ==================================================
	// Entity Update:
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		this.prevWatts = this.wattsReceived;
		
		// Consume or Stop Power (Server Only):
		if (!this.worldObj.isRemote) {
			if(!this.isDisabled()) {
				ElectricityPack electricityPack = ElectricityNetworkHelper.consumeFromMultipleSides(this, this.getConsumingSides(), this.getRequest());
				this.onReceive(electricityPack);
			}
			else {
				ElectricityNetworkHelper.consumeFromMultipleSides(this, new ElectricityPack());
			}
		}
	}
	
	
	// Get Consuming Sides:
	protected EnumSet<ForgeDirection> getConsumingSides() {
		return ElectricityNetworkHelper.getDirections(this);
	}
	
	
	// Get Request:
	public ElectricityPack getRequest() {
		return new ElectricityPack();
	}
	
	
	// On Receive:
	public void onReceive(ElectricityPack electricityPack) {
		// Overload:
		if (UniversalElectricity.isVoltageSensitive) {
			if (electricityPack.voltage > this.getVoltage()) {
				this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 1.5f, true);
				return;
			}
		}

		this.wattsReceived = Math.min(this.wattsReceived + electricityPack.getWatts(), this.getWattBuffer());
	}
	
	
	// Get Watt Buffer (Temporary Storage):
	public double getWattBuffer() {
		return this.getRequest().getWatts() * 2;
	}
}
