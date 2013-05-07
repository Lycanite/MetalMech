package lycanite.metalmech.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCompressor extends ModelBase implements IModelRender {
	  ModelRenderer Base;
	  ModelRenderer Top;
	  ModelRenderer StandFR;
	  ModelRenderer StandBR;
	  ModelRenderer StandFL;
	  ModelRenderer StandBL;
	  ModelRenderer Pipe;

	  public ModelCompressor() {
	    this.textureWidth = 64;
	    this.textureHeight = 64;
	    
	    this.Top = new ModelRenderer(this, 0, 0);
	    this.Top.addBox(0.0F, 0.0F, 0.0F, 16, 6, 16);
	    this.Top.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.Top.setTextureSize(64, 64);
	    this.Top.mirror = true;
	    this.Base = new ModelRenderer(this, 0, 22);
	    this.Base.addBox(0.0F, 10.0F, 0.0F, 16, 6, 16);
	    this.Base.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.Base.setTextureSize(64, 64);
	    this.Base.mirror = true;
	    this.Pipe = new ModelRenderer(this, 0, 31);
	    this.Pipe.addBox(0.0F, 0.0F, 0.0F, 12, 4, 13);
	    this.Pipe.setRotationPoint(2.0F, 6.0F, 3.0F);
	    this.Pipe.setTextureSize(64, 64);
	    this.Pipe.mirror = true;
	    
	    this.StandFR = new ModelRenderer(this, 0, 48);
	    this.StandFR.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
	    this.StandFR.setRotationPoint(0.0F, 6.0F, 0.0F);
	    this.StandFR.setTextureSize(64, 64);
	    this.StandFR.mirror = true;
	    this.StandBR = new ModelRenderer(this, 0, 48);
	    this.StandBR.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
	    this.StandBR.setRotationPoint(0.0F, 6.0F, 14.0F);
	    this.StandBR.setTextureSize(64, 64);
	    this.StandBR.mirror = true;
	    this.StandFL = new ModelRenderer(this, 0, 48);
	    this.StandFL.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
	    this.StandFL.setRotationPoint(14.0F, 6.0F, 0.0F);
	    this.StandFL.setTextureSize(64, 64);
	    this.StandFL.mirror = true;
	    this.StandBL = new ModelRenderer(this, 0, 48);
	    this.StandBL.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
	    this.StandBL.setRotationPoint(14.0F, 6.0F, 14.0F);
	    this.StandBL.setTextureSize(64, 64);
	    this.StandBL.mirror = true;
	  }

	  public void renderAll() {
	    this.Base.render(0.0625F);
	    this.Top.render(0.0625F);
	    this.Pipe.render(0.0625F);
	    this.StandFR.render(0.0625F);
	    this.StandBR.render(0.0625F);
	    this.StandFL.render(0.0625F);
	    this.StandBL.render(0.0625F);
	  }
}