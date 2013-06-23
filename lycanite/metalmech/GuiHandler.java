package lycanite.metalmech;

import lycanite.metalmech.client.GuiMachine;
import lycanite.metalmech.client.GuiMachineElectric;
import lycanite.metalmech.client.GuiMachineElectric;
import lycanite.metalmech.container.ContainerMachine;
import lycanite.metalmech.container.ContainerMachineElectric;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	// Packet Types:
    public static enum GUIType {
		NULL(-1),
		CONTAINER(0),
		MACHINE(10),
		ELECTRIC_MACHINE(20),
		MODULAR_MACHINE(30);
		
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
		else if(guiID >= GUIType.ELECTRIC_MACHINE.id && guiID < GUIType.MODULAR_MACHINE.id)
			return new ContainerMachineElectric((TileEntityElectricBase)tileEntity, player.inventory);
		else
			return null;
	}
	
	
	// Get Client GUI:
	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if(guiID >= GUIType.MACHINE.id && guiID < GUIType.ELECTRIC_MACHINE.id)
			return new GuiMachine(player.inventory, (TileEntityMachine)tileEntity);
		else if(guiID >= GUIType.ELECTRIC_MACHINE.id && guiID < GUIType.MODULAR_MACHINE.id)
			if(((TileEntityElectricBase)tileEntity).getType() != "Furnace")
				return new GuiMachineElectric(player.inventory, (TileEntityElectricBase)tileEntity);
			else
				return new GuiMachineElectric(player.inventory, (TileEntityElectricBase)tileEntity);
		else
			return null;
	}
}