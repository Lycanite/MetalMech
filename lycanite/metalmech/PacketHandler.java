package lycanite.metalmech;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.network.IPacketReceiver;
import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachine.MachinePacketType;
import lycanite.metalmech.block.TileEntityMachineElectric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler, IPacketReceiver {
	
	// Packet Types:
    public static enum PacketType {
		NULL (-1),
		CONTAINER (0),
		MACHINE (1);
		
		public int id;
		
		private PacketType(int i) {
			id = i;
		}
	}
    
    
    // Write NBT:
    public static void writeNBTTagCompound(NBTTagCompound tag, DataOutputStream stream) throws IOException {
		if (tag == null) {
			stream.writeShort(-1);
		}
		else {
			byte[] var2 = CompressedStreamTools.compress(tag);
			stream.writeShort((short)var2.length);
			stream.write(var2);
		}
    }
    
    
    // Create Packet:
    public static Packet createPacket(String channelName, TileEntity sender, Object... sendData) {
    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    	DataOutputStream data = new DataOutputStream(bytes);
    	
    	int packetType = PacketType.MACHINE.id;
    	
    	try {
    		data.writeInt(packetType);
    		data.writeInt(sender.xCoord);
    		data.writeInt(sender.yCoord);
    		data.writeInt(sender.zCoord);
    		data = encodeDataStream(data, sendData);
    		
    		Packet250CustomPayload packet = new Packet250CustomPayload();
    		packet.channel = channelName;
    		packet.data = bytes.toByteArray();
    		packet.length = packet.data.length;
    		
    		return packet;
    	}
    	catch(IOException e) {
    		System.out.println("[MetalMech] Failed to create Tile Entity packet.");
    		e.printStackTrace();
    	}
    	return null;
    }
    
    
    // Encode Data Stream:
    public static DataOutputStream encodeDataStream(DataOutputStream data, Object[] sendData) {
    	try {
    		for (Object dataValue : sendData) {
    			if ((dataValue instanceof Integer))
    				data.writeInt(((Integer)dataValue).intValue());
    			else if ((dataValue instanceof Float))
    				data.writeFloat(((Float)dataValue).floatValue());
    			else if ((dataValue instanceof Double))
    				data.writeDouble(((Double)dataValue).doubleValue());
    			else if ((dataValue instanceof Byte))
    				data.writeByte(((Byte)dataValue).byteValue());
    			else if ((dataValue instanceof Boolean))
    				data.writeBoolean(((Boolean)dataValue).booleanValue());
    			else if ((dataValue instanceof String))
    				data.writeUTF((String)dataValue);
    			else if ((dataValue instanceof Short))
    				data.writeShort(((Short)dataValue).shortValue());
    			else if ((dataValue instanceof Long))
    				data.writeLong(((Long)dataValue).longValue());
    			else if ((dataValue instanceof NBTTagCompound))
    				writeNBTTagCompound((NBTTagCompound)dataValue, data);
    		}
    		return data;
    	}
    	catch(IOException e) {
    		System.out.println("[MetalMech] Failed to encode Tile Entity packet data.");
    		e.printStackTrace();
    	}
    	return data;
    }
    
    
    // Send Packet Data:
    public static void sendPacketToClients(Packet packet, World worldObj, Vector3 position, double range) {
    	try {
    		PacketDispatcher.sendPacketToAllAround(position.x, position.y, position.z, range, worldObj.provider.dimensionId, packet);
    	}
    	catch(Exception e) {
    		System.out.println("[MetalMech] Sending packet to client failed.");
    		e.printStackTrace();
    	}
    }
	
	
	// Respond to Packet Data:
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player) {
		try {
			ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
			EntityPlayer playerEntity = (EntityPlayer)player;
			World world = playerEntity.worldObj;
			
			if(packet.channel.equals("MetalMech")) {
				int packetType = data.readInt();
				
				if(packetType == PacketType.MACHINE.id) {
					if(world.isRemote) {
						int tileEntityX = data.readInt();
						int tileEntityY = data.readInt();
						int tileEntityZ = data.readInt();
						if(world != null) {
							TileEntity tileEntity = world.getBlockTileEntity(tileEntityX, tileEntityY, tileEntityZ);
							if ((tileEntity instanceof IPacketReceiver)) {
								((IPacketReceiver)tileEntity).handlePacketData(network, packetType, packet, (EntityPlayer)player, data);
							}
						}
					}
				}
				else {
					handlePacketData(network, packetType, packet, (EntityPlayer)player, data);
				}
			}
		}
		catch(Exception e) {
			System.err.println("[MetalMech] Invalid Packet Type was passed.");
			e.printStackTrace();
		}
	}
	
	
	// Handle Packet Data:
	public void handlePacketData(INetworkManager network, int packetType, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) {
		return;
	}
}
