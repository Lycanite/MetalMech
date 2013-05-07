package lycanite.metalmech.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelExtractor extends ModelBase implements IModelRender {
	  ModelRenderer Base;
	  ModelRenderer Top;
	  ModelRenderer StandFR;
	  ModelRenderer StandBR;
	  ModelRenderer StandFL;
	  ModelRenderer StandBL;
	  ModelRenderer Pipe;

	  public ModelExtractor() {
	    this.textureWidth = 64;
	    this.textureHeight = 64;

	    this.Base = new ModelRenderer(this, 0, 16);
	    this.Base.addBox(0.0F, 10.0F, 0.0F, 16, 6, 16);
	    this.Base.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.Base.setTextureSize(64, 64);
	    this.Base.mirror = true;
	    this.Top = new ModelRenderer(this, 0, 0);
	    this.Top.addBox(0.0F, 0.0F, 0.0F, 14, 2, 14);
	    this.Top.setRotationPoint(1.0F, 0.0F, 1.0F);
	    this.Top.setTextureSize(64, 64);
	    this.Top.mirror = true;
	    this.Pipe = new ModelRenderer(this, 0, 28);
	    this.Pipe.addBox(0.0F, 0.0F, 0.0F, 10, 8, 10);
	    this.Pipe.setRotationPoint(3.0F, 2.0F, 3.0F);
	    this.Pipe.setTextureSize(64, 64);
	    this.Pipe.mirror = true;
	    
	    this.StandFR = new ModelRenderer(this, 0, 45);
	    this.StandFR.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
	    this.StandFR.setRotationPoint(1.0F, 2.0F, 1.0F);
	    this.StandFR.setTextureSize(64, 64);
	    this.StandFR.mirror = true;
	    this.StandBR = new ModelRenderer(this, 0, 45);
	    this.StandBR.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
	    this.StandBR.setRotationPoint(1.0F, 2.0F, 14.0F);
	    this.StandBR.setTextureSize(64, 64);
	    this.StandBR.mirror = true;
	    this.StandFL = new ModelRenderer(this, 0, 45);
	    this.StandFL.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
	    this.StandFL.setRotationPoint(14.0F, 2.0F, 1.0F);
	    this.StandFL.setTextureSize(64, 64);
	    this.StandFL.mirror = true;
	    this.StandBL = new ModelRenderer(this, 0, 45);
	    this.StandBL.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
	    this.StandBL.setRotationPoint(14.0F, 2.0F, 14.0F);
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