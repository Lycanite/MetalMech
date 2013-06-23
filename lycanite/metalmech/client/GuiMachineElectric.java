package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.container.ContainerMachine;
import lycanite.metalmech.container.ContainerMachineElectric;
import lycanite.metalmech.machine.MachineInventory;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.core.electricity.ElectricityDisplay.ElectricUnit;


public class GuiMachineElectric extends GuiContainer {
	
	// Info:
	private TileEntityElectricBase tileEntity;
	protected int textureWidth = 256;
	protected int textureHeight = 256;
	protected int containerX;
	protected int containerY;
	protected int voltBarWidth = 16;
	protected int voltBarHeight = 32;
	protected int energyArrowWidth = 6;
	protected int fuelBurnWidth = 14;
	protected int heatGaugeWidth = 24;
	
	// Constructor:
	public GuiMachineElectric(InventoryPlayer playerInventory, TileEntityElectricBase tileEntityMachine) {
		super(new ContainerMachineElectric(tileEntityMachine, playerInventory));
		tileEntity = tileEntityMachine;
	}
	
	
	// Draw Foreground:
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        // Text:
		fontRenderer.drawString(this.tileEntity.getInvName(), 50, 6, 4210752);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        
        int statsX = 76;
        if(tileEntity.getCategory() == "Battery")
        	statsX = 60;
        
        // Stats:
        fontRenderer.drawString("Volts: " + (int)tileEntity.getVoltage() + "V", statsX, 68, 4210752);
        
        if(tileEntity.getCategory() == "Machine")
        	fontRenderer.drawString("Costs: " + (int)tileEntity.wattCost + "W", statsX, 56, 4210752);
        
        if(tileEntity.getCategory() == "Generator") {
        	fontRenderer.drawString("Heat: " + (int)tileEntity.getScaledHeat(100) + "%", statsX, 56, 4210752);
        	if((int)tileEntity.getScaledHeat(100) >= 50)
        		fontRenderer.drawString("Output: " + (int)tileEntity.getWattsGenerated() + "W", statsX, 44, 4210752);
        	else
        		fontRenderer.drawString("Output: 0W", statsX, 44, 4210752);
        }
        
        if(tileEntity.getCategory() == "Battery") {
        	fontRenderer.drawString("Storage: " + (int)(tileEntity.getAmpsStored() / 1000) + "kJ", statsX, 56, 4210752);
        	fontRenderer.drawString("Output: " + (int)tileEntity.getWattsGenerated() + "W", statsX, 44, 4210752);
        }
    }
    
    
    // Draw Background:
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		String texture = "/mods/" + MetalMech.modid + "/textures/guis/Electric.png";
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(texture);
		
		// Main Window:
		containerX = (width - xSize) / 2;
		containerY = (height - ySize) / 2;
		drawTexturedModalRect(containerX, containerY, 0, 0, xSize, ySize);
		
		// Inventory Slots:
		int slotAmount = tileEntity.getSizeInventory();
		for(MachineInventory inventoryValue : tileEntity.inventory.values()) {
			drawSlot(inventoryValue);
		}
		
		// Machine:
		if(tileEntity.getCategory() == "Machine")
			drawProgressBar(75, 22);
		
		// Generator:
		else if(tileEntity.getCategory() == "Generator") {
			drawFuelBurn(56, 30);
			drawHeatGauge(75, 25);
			drawWattBar(108, 25);
		}
		
		// Battery:
		else if(tileEntity.getCategory() == "Battery") {
			drawStorageBar(48, 25);
			drawWattBar(136, 25);
		}
		
		// Energy Bar:
		drawVoltBar(171, 80);
	}
	
	
	// Draw Slot:
	protected void drawSlot(MachineInventory inventory) {
		
		// Size:
		int x = (int)inventory.slotPos.x;
		int y = (int)inventory.slotPos.y;
		int slotWidth = 16;
		int slotHeight = 16;
		
		// Offsets:
		int slotXOffset = 0;
		int slotYOffset = 0;
		int slotWidthOffset = 2;
		int slotHeightOffset = 2;
		int slotVOffset = 0;
		
		// Types:
		if(inventory.name == "Battery") {
			slotVOffset = slotHeight + slotHeightOffset;
		}
		else if(inventory.name == "Charge") {
			slotVOffset = (slotHeight + slotHeightOffset) * 2;
		}
		else if(inventory.name == "Fuel") {
			slotVOffset = (slotHeight + slotHeightOffset) * 3;
		}
		else if(inventory.name == "Upgrade") {
			slotVOffset = (slotHeight + slotHeightOffset) * 4;
		}
		else if(inventory.name == "Output") {
			slotVOffset = (slotHeight + slotHeightOffset) * 5;
			slotWidthOffset += 26 - (slotWidth + slotWidthOffset);
			slotHeightOffset += 26 - (slotHeight + slotHeightOffset);
		}
		
		// Apply Offsets:
		slotXOffset -= slotWidthOffset / 2;
		slotYOffset -= slotHeightOffset / 2;
		x += slotXOffset;
		y += slotYOffset;
		slotWidth += slotWidthOffset;
		slotHeight += slotHeightOffset;
		
		// Draw Slot:
		drawTexturedModalRect(containerX + x, containerY + y, textureWidth - slotWidth, 0 + slotVOffset, slotWidth, slotHeight);
	}
	
	
	// Draw Progress Bar:
	protected void drawProgressBar(int x, int y) {
		// Size:
		int barWidth = 24;
		int barHeight = 20;
		int barU = 0;
		int barV = ySize;
		
		// Assess TileEntity:
		boolean active = tileEntity.processTime > 0;
		int scale = tileEntity.getScaledProcessProgress(barWidth);
		barU = barWidth * tileEntity.getMetadata();
		
		// Draw Back:
		drawTexturedModalRect(containerX + x, containerY + y, barU, barV, barWidth, barHeight);
		
		// Draw Front:
		if(active) {
			barV += barHeight;
			drawTexturedModalRect(containerX + x, containerY + y, barU, barV, barWidth - scale, barHeight);
		}
	}
	
	
	// Draw Energy Bar:
	protected void drawVoltBar(int x, int y) {
		// Size:
		int barWidth = voltBarWidth;
		int barHeight = voltBarHeight;
		int barU = 0;
		int barV = textureHeight - barHeight;
		
		int arrowWidth = energyArrowWidth;
		int arrowHeight = 7;
		int arrowU = 0;
		int arrowV = barV - arrowHeight;
		int arrowX = x - barWidth;
		int arrowY = y - barHeight - (arrowHeight / 2);
		int arrowYOffset = barHeight;
		
		// Assess TileEntity:
		int scale = barHeight;
		if(tileEntity.processSpeed > 0 || tileEntity.isActive())
			scale = barHeight - tileEntity.getScaledVolts(barHeight, tileEntity.processSpeed <= 0);
		if(scale > barHeight) scale = barHeight;
		int voltsReceived = (int)tileEntity.getVoltsReceived();
		if(tileEntity.getCategory() == "Generator")
			voltsReceived = (int)tileEntity.getVoltage();
		int voltage = (int)tileEntity.getVoltage();
		
		// Draw Back:
		drawTexturedModalRect(containerX + x - barWidth, containerY + y - barHeight, barU, barV, barWidth, barHeight);
		
		// Set Bar Color from Volts:
		if(voltsReceived > MetalMech.highVoltage)
			barU = barWidth * 4;
		else if(voltsReceived > MetalMech.mediumVoltage)
			barU = barWidth * 3;
		else if(voltsReceived > MetalMech.lowVoltage)
			barU = barWidth * 2;
		else if(voltsReceived > 0)
			barU = barWidth * 1;
		
		
		// Draw Front:
		barHeight -= scale;
		barV += scale;
		drawTexturedModalRect(containerX + x - barWidth, containerY + y - barHeight, barU, barV, barWidth, barHeight);
		
		// Assess Voltage:
		if(voltage > MetalMech.highVoltage)
			arrowYOffset = 0;
		else if(voltage > MetalMech.mediumVoltage)
			arrowYOffset *= 0.25;
		else if(voltage > MetalMech.lowVoltage)
			arrowYOffset *= 0.5;
		else
			arrowYOffset *= 0.75;
		
		// Draw Arrow:
		drawTexturedModalRect(containerX + x - barWidth - arrowWidth, containerY + arrowY + arrowYOffset, arrowU, arrowV, arrowWidth, arrowHeight);
	}
	
	
	// Draw Fuel Burn:
	protected void drawFuelBurn(int x, int y) {
		// Size:
		int gaugeWidth = fuelBurnWidth;
		int gaugeHeight = 14;
		int gaugeU = energyArrowWidth;
		int gaugeV = textureHeight - voltBarHeight - gaugeHeight;
		
		// Assess TileEntity:
		int scale = tileEntity.getScaledFuelTime(gaugeHeight);
		
		// Draw Back:
		drawTexturedModalRect(containerX + x, containerY + y, gaugeU, gaugeV, gaugeWidth, gaugeHeight);
		
		// Draw Front:
		gaugeU += gaugeWidth;
		drawTexturedModalRect(containerX + x, containerY + y + (gaugeHeight - scale), gaugeU, gaugeV + (gaugeHeight - scale), gaugeWidth, scale);
	}
	
	
	// Draw Heat Gauge:
	protected void drawHeatGauge(int x, int y) {
		// Size:
		int gaugeWidth = heatGaugeWidth;
		int gaugeHeight = 16;
		int gaugeU = energyArrowWidth + (fuelBurnWidth * 2);
		int gaugeV = textureHeight - voltBarHeight - gaugeHeight;
		
		// Assess TileEntity:
		int scale = tileEntity.getScaledHeat(gaugeWidth);
		
		// Draw Back:
		drawTexturedModalRect(containerX + x, containerY + y, gaugeU, gaugeV, gaugeWidth, gaugeHeight);
		
		// Draw Front:
		gaugeU += gaugeWidth;
		drawTexturedModalRect(containerX + x, containerY + y, gaugeU, gaugeV, scale, gaugeHeight);
	}
	
	
	// Draw Watt Bar:
	protected void drawWattBar(int x, int y) {
		// Size:
		int barWidth = 8;
		int barHeight = 16;
		int barU = energyArrowWidth + (fuelBurnWidth * 2) + (heatGaugeWidth * 2);
		int barV = textureHeight - voltBarHeight - barHeight;
		
		// Assess TileEntity:
		int scale = tileEntity.getScaledWattsGenerated(barHeight);
		
		// Draw Back:
		drawTexturedModalRect(containerX + x, containerY + y, barU, barV, barWidth, barHeight);
		
		// Draw Front:
		barU += barWidth;
		drawTexturedModalRect(containerX + x, containerY + y + (barHeight - scale), barU, barV + (barHeight - scale), barWidth, scale);
	}
	
	
	// Draw Storage Bar:
	protected void drawStorageBar(int x, int y) {
		// Size:
		int barWidth = 80;
		int barHeight = 16;
		int barU = voltBarWidth * 5;
		int barV = textureHeight - barHeight;
		
		// Assess TileEntity:
		int scale = tileEntity.getScaledAmpsStored(barWidth);
		
		// Draw Back:
		drawTexturedModalRect(containerX + x, containerY + y, barU, barV, barWidth, barHeight);
		
		// Draw Front:
		barV -= barHeight;
		drawTexturedModalRect(containerX + x, containerY + y, barU, barV, scale, barHeight);
	}
}