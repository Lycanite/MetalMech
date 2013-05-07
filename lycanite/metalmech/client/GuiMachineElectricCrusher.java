package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.block.ContainerMachine;
import lycanite.metalmech.block.ContainerMachineElectric;
import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachineElectric;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.core.electricity.ElectricityDisplay.ElectricUnit;


public class GuiMachineElectricCrusher extends GuiContainer {
	
	// Info:
	private TileEntityMachineElectric tileEntity;
	private int containerWidth;
	private int containerHeight;
	
	// Constructor:
	public GuiMachineElectricCrusher(InventoryPlayer playerInventory, TileEntityMachineElectric tileEntityMachine) {
		super(new ContainerMachineElectric(tileEntityMachine, playerInventory));
		this.tileEntity = tileEntityMachine;
	}
	
	
	// Draw Foreground:
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(this.tileEntity.getInvName(), 60, 6, 4210752);
        this.fontRenderer.drawString("Crush:", 10, 28, 4210752);
        this.fontRenderer.drawString("Battery:", 10, 53, 4210752);
        String displayText = "";
        
        if (this.tileEntity.isDisabled()) {
        	displayText = "Disabled!";
        }
        else if (this.tileEntity.isActive()) {
        	displayText = "Crushing";
        }
        else {
        	displayText = "Idle";
        }
        
        this.fontRenderer.drawString("Status: " + displayText, 82, 45, 4210752);
        this.fontRenderer.drawString(ElectricityDisplay.getDisplay(this.tileEntity.wattsPerTick * 20, ElectricUnit.WATT), 82, 56, 4210752);
        this.fontRenderer.drawString(ElectricityDisplay.getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE), 82, 68, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    
    // Draw Background:
	@Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        String texture = "/mods/" + MetalMech.modid + "/textures/guis/Crusher.png";
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); // Allows for colours.
        this.mc.renderEngine.bindTexture(texture); // Loads texture for GUI.
        
        this.containerWidth = (this.width - this.xSize) / 2;
        this.containerHeight = (this.height - this.ySize) / 2;
        drawTexturedModalRect(this.containerWidth, this.containerHeight, 0, 0, this.xSize, this.ySize);
        
        if (this.tileEntity.processTime > 0) {
        	int scale = tileEntity.getScaledProcessProgress((int)23.0D);
        	drawTexturedModalRect(this.containerWidth + 77, this.containerHeight + 24, 176, 0, 23 - scale, 20);
        }
    }
}