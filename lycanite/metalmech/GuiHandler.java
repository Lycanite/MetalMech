package lycanite.metalmech;

import lycanite.metalmech.block.ContainerMachine;
import lycanite.metalmech.block.ContainerMachineElectric;
import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachineElectric;
import lycanite.metalmech.client.GuiMachine;
import lycanite.metalmech.client.GuiMachineElectricCompressor;
import lycanite.metalmech.client.GuiMachineElectricCrusher;
import lycanite.metalmech.client.GuiMachineElectricExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	// Packet Types:
    public static enum GUIType {
		NULL (-1),
		CONTAINER (0),
		MACHINE (10),
		ELECTRIC_MACHINE (20);
		
		public int id;
		
		private GUIType(int i) {
			id = i;
		}
	}
	
	// Get Server GUI:
	@Override
	public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(guiID >= GUIType.MACHINE.id && guiID < GUIType.ELECTRIC_MACHINE.id)
			return new ContainerMachine((TileEntityMachine)tileEntity, player.inventory);
		else if(guiID >= GUIType.ELECTRIC_MACHINE.id)
			return new ContainerMachineElectric((TileEntityMachineElectric)tileEntity, player.inventory);
		
		return null;
	}
	
	
	// Get Client GUI:
	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(guiID >= GUIType.MACHINE.id && guiID < GUIType.ELECTRIC_MACHINE.id)
			return new GuiMachine(player.inventory, (TileEntityMachine)tileEntity);
		else if(guiID >= GUIType.ELECTRIC_MACHINE.id) {
			if(guiID - GUIType.ELECTRIC_MACHINE.id == 0)
				return new GuiMachineElectricCrusher(player.inventory, (TileEntityMachineElectric)tileEntity);
			if(guiID - GUIType.ELECTRIC_MACHINE.id == 1)
				return new GuiMachineElectricExtractor(player.inventory, (TileEntityMachineElectric)tileEntity);
			if(guiID - GUIType.ELECTRIC_MACHINE.id == 2)
				return new GuiMachineElectricCompressor(player.inventory, (TileEntityMachineElectric)tileEntity);
		}
		
		return null;
	}
}