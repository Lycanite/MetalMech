package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.container.ContainerMachine;
import lycanite.metalmech.tileentity.TileEntityMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;


public class GuiMachine extends GuiContainer {
	
	// Info:
	private TileEntityMachine guiTileEntity;
	
	// Constructor:
	public GuiMachine(InventoryPlayer playerInventory, TileEntityMachine tileEntity) {
		super(new ContainerMachine(tileEntity, playerInventory));
		this.guiTileEntity = tileEntity;
	}
	
	
	// Draw Foreground:
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(this.guiTileEntity.getInvName(), 60, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    
    // Draw Background:
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        String texture = "/gui/furnace.png";
        if(this.guiTileEntity.getCategory() == "Crusher") {
			texture = "/mods/Metallurgy/textures/guis/crusher.png";
		}
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        
        int canvasWidth = (this.width - this.xSize) / 2;
        int canvasHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(canvasWidth, canvasHeight, 0, 0, this.xSize, this.ySize);
        int progressScale;
        
        if(this.guiTileEntity.isActive()) {
        	progressScale = this.guiTileEntity.getScaledRemainingFuelTime(12);
            this.drawTexturedModalRect(canvasWidth + 56, canvasHeight + 36 + 12 - progressScale, 176, 12 - progressScale, 14, progressScale + 2);
        }
        
        progressScale = this.guiTileEntity.getScaledProcessProgress(24);
        this.drawTexturedModalRect(canvasWidth + 79, canvasHeight + 34, 176, 14, progressScale + 1, 16);
    }
}