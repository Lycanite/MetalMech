package lycanite.metalmech.tileentity;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.Arrays;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.PacketHandler;
import lycanite.metalmech.block.BlockMachineBasic;
import lycanite.metalmech.machine.MachineInfo;
import lycanite.metalmech.machine.MachineManager;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IConnector;
import universalelectricity.core.block.INetworkProvider;
import universalelectricity.core.electricity.ElectricityNetwork;
import universalelectricity.core.electricity.IElectricityNetwork;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.network.IPacketReceiver;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityWire extends TileEntity implements IConductor, IPacketReceiver {
	
	// Info:
	public MachineInfo machineInfo;
	public TileEntity[] tileEntityConnections = { null, null, null, null, null, null };
	protected long ticks = 0;
	
	// Stats:
	public double resistance = 0.05;
	public double capacity = 200;
	public int voltage = MetalMech.extremeVoltage;
	
	// Electricity:
	private IElectricityNetwork network;
	
	// Visuals:
	public boolean[] visualConnections = { false, false, false, false, false, false };
	
	
	// ==================================================
	//                     Setup
	// ==================================================
	// ========== Constructor ==========
	public TileEntityWire() {
		super();
	}
	
	
	// ========== Initialise =========
	public void initialize() {
		resistance = getInfo().resistance;
		capacity = getInfo().capacity;
		voltage = getInfo().voltage;
		updateAdjacentConnections();
	}
	
	
	// ==================================================
	//                 Machine Properties
	// ==================================================
	// Get Machine Type:
	public MachineInfo getInfo() {
		if(machineInfo == null)
			return machineInfo = MachineManager.getMachineInfo(getCategory(), getBlockMetadata());
		else
			return machineInfo;
	}
	
	
	// Get Machine Category:
	public String getCategory() {
		return "Wire";
	}
	
	
	// Get Machine Metadata:
	public String getType() {
		return getInfo().name;
	}
	
	
	// Get Machine Metadata:
	public int getMetadata() {
		return MachineManager.getMetadata(getType());
	}
	
	
	// ==================================================
	//                   Electricity
	// ==================================================
	// ========== Get Resistance ==========
	@Override
	public double getResistance() {
		return resistance;
	}

	
	// ========== Get Capacity ==========
	@Override
	public double getCurrentCapcity() {
		return capacity;
	}

	
	// ========== Get Voltage ==========
	public int getVoltage() {
		return voltage;
	}
	
	
	// ==================================================
	//                   Behaviour
	// ==================================================
	// ========== Update Tile Entity ==========
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (ticks == 0)
			initialize();
		if (ticks >= Long.MAX_VALUE)
			ticks = 1;
		ticks++;
		
		if (!worldObj.isRemote) {
			if (ticks % 300 == 0) {
				updateAdjacentConnections();
			}
			
			// Burn if Overloaded:
			if(getNetwork() != null && ticks % 20 == 0) {
				if(getNetwork().getProduced().voltage > getVoltage())
					worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Block.fire.blockID, 0, 2);
			}
		}
	}
	
	
	// ========== Invalidate ==========
	@Override
	public void invalidate() {
		if(!worldObj.isRemote)
			getNetwork().splitNetwork(this);
		super.invalidate();
	}
	
	
	// ========== Update Adjacent Connections ==========
	@Override
	public void updateAdjacentConnections() {
		if(!this.worldObj.isRemote && worldObj != null) {
			boolean[] prevVisualConnections = visualConnections.clone();
			for(byte i = 0; i < 6; i++) {
				updateConnection(VectorHelper.getConnectorFromSide(worldObj, new Vector3(this), ForgeDirection.getOrientation(i)), ForgeDirection.getOrientation(i));
			}
			if(!Arrays.areEqual(prevVisualConnections, visualConnections))
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	
	// ========== Update Connection ==========
	public void updateConnection(TileEntity tileEntity, ForgeDirection side) {
		if(!this.worldObj.isRemote) {
			if(tileEntity instanceof IConnector) {
				// Attempt to Connect:
				if(((IConnector)tileEntity).canConnect(side.getOpposite())) {
					if(!(tileEntity instanceof TileEntityWire) || (tileEntity instanceof TileEntityWire && ((TileEntityWire)tileEntity).getInfo().name == getInfo().name)) {
						tileEntityConnections[side.ordinal()] = tileEntity;
						visualConnections[side.ordinal()] = true;
						if(tileEntity.getClass() == getClass() && tileEntity instanceof INetworkProvider) { //TODO Investigate
							getNetwork().mergeConnection(((INetworkProvider) tileEntity).getNetwork());
						}
						return;
					}
				}
			}
		
			// If Can't Connect/Disconnect:
			if(tileEntityConnections[side.ordinal()] != null) {
				getNetwork().stopProducing(tileEntityConnections[side.ordinal()]);
				getNetwork().stopRequesting(tileEntityConnections[side.ordinal()]);
			}
			tileEntityConnections[side.ordinal()] = null;
			visualConnections[side.ordinal()] = false;
		}
	}
	
	
	// ========== Network ==========
	@Override
	public IElectricityNetwork getNetwork() {
		if(network == null)
			setNetwork(new ElectricityNetwork(this));
		return network;
	}
	@Override
	public void setNetwork(IElectricityNetwork setNetwork) {
		network = setNetwork;
	}
	
	
	// ===== Get Connections ==========
	@Override
	public TileEntity[] getAdjacentConnections() {
		return tileEntityConnections;
	}
	public boolean[] getVisualConnections() {
		return visualConnections;
	}
	
	
	// ========== Can Connection ==========
	@Override
	public boolean canConnect(ForgeDirection direction) {
		return true;
	}
	
	
	// ========== Render Bounding Box ==========
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getAABBPool().getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
	
	
	// ==================================================
	//              Network Packets and NBT
	// ==================================================
	// ========== Packet Setup ==========
	@Override
	public Packet getDescriptionPacket() {
		List<Object> packetData = new ArrayList<Object>();
		for(int i = 0; i < visualConnections.length; i++) {
			packetData.add(visualConnections[i]);
		}
		return PacketHandler.createPacket("MetalMech", this, packetData.toArray());
	}
	
	
	// ========== Handle Packet ==========
	public void handlePacketData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) {
		if(this.worldObj.isRemote) {
			try {
				for(int i = 0; i < visualConnections.length; i++) {
					visualConnections[i] = dataStream.readBoolean();
				}
			}
			catch (Exception e) {
				System.out.println("[MetalMech] Problem handling wire update packet for " + getType());
				e.printStackTrace();
			}
		}
	}
}
