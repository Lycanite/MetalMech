package lycanite.metalmech.tileentity;

import lycanite.metalmech.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IDisableable;

public class TileEntityBase extends TileEntity implements IDisableable {
	
	// Information:
	protected String tileEntityName;
	
	// Rotation:
	protected short facing = 2;
	
	// Behaviour:
	protected long ticks = 0;
	protected int ticksSinceSync;
	protected int playersUsing;
	
	// Disableable:
	protected int disabledTicks = 0;
	
	
	// ==================================================
	//                    Information
	// ==================================================
	// Is Inventory Name Localised:
	public boolean isInvNameLocalized() {
        return this.tileEntityName != null && this.tileEntityName.length() > 0;
    }
	
	
	// ==================================================
	//                     Rotation
	// ==================================================
	// Get Facing:
	public short getFacing() {
		return facing;
	}
	
	// Set Facing With Update:
	public void setFacingWithUpdate(int targetFacing) {
		facing = (short)targetFacing;
		PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 15.0D);
	}
	
	
	// Set Facing:
	public void setFacing(int targetFacing) {
		facing = (short)targetFacing;
	}
	
	
	// ==================================================
	//                     Behaviour
	// ==================================================
	// Entity Update:
	@Override
	public void updateEntity() {
		if(this.ticks == 0) this.initiate();
		if(this.ticks >= Long.MAX_VALUE) this.ticks = 1;
		this.ticks++;

		if(this.disabledTicks > 0) {
			this.disabledTicks--;
			this.whileDisable();
			return;
		}
	}

	// Initiate:
	public void initiate() {}
	
	// Check If Player Can Use:
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	
	// ==================================================
	//                     Disableable
	// ==================================================
	// While Disabled:
	protected void whileDisable() {

	}
	
	// On Disable:
	@Override
	public void onDisable(int duration) {
		this.disabledTicks = duration;
	}
	
	// Is Disabled:
	@Override
	public boolean isDisabled() {
		return this.disabledTicks > 0;
	}
}
