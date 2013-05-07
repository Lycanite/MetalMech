package lycanite.metalmech.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

// Copied from Metallurgy Core mod, I do not take credit for this.
public class ModelCrusher extends ModelBase implements IModelRender {
  ModelRenderer Shape1;
  ModelRenderer Shape2;
  ModelRenderer Shape3;
  ModelRenderer Shape4;
  ModelRenderer Shape5;
  ModelRenderer Shape6;
  ModelRenderer Shape7;
  ModelRenderer Shape8;
  ModelRenderer Shape9;
  ModelRenderer Shape10;

  public ModelCrusher()   {
    this.textureWidth = 64;
    this.textureHeight = 64;

    this.Shape1 = new ModelRenderer(this, 0, 20);
    this.Shape1.addBox(0.0F, 6.0F, 0.0F, 16, 10, 16);
    this.Shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Shape1.setTextureSize(64, 64);
    this.Shape1.mirror = true;
    this.Shape2 = new ModelRenderer(this, 0, 0);
    this.Shape2.addBox(0.0F, 0.0F, 0.0F, 16, 4, 16);
    this.Shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.Shape2.setTextureSize(64, 64);
    this.Shape2.mirror = true;
    this.Shape3 = new ModelRenderer(this, 24, 45);
    this.Shape3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.Shape3.setRotationPoint(0.0F, 4.0F, 0.0F);
    this.Shape3.setTextureSize(64, 64);
    this.Shape3.mirror = true;
    this.Shape4 = new ModelRenderer(this, 24, 45);
    this.Shape4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.Shape4.setRotationPoint(0.0F, 4.0F, 15.0F);
    this.Shape4.setTextureSize(64, 64);
    this.Shape4.mirror = true;
    this.Shape5 = new ModelRenderer(this, 24, 45);
    this.Shape5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.Shape5.setRotationPoint(15.0F, 4.0F, 0.0F);
    this.Shape5.setTextureSize(64, 64);
    this.Shape5.mirror = true;
    this.Shape6 = new ModelRenderer(this, 24, 45);
    this.Shape6.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
    this.Shape6.setRotationPoint(15.0F, 4.0F, 15.0F);
    this.Shape6.setTextureSize(64, 64);
    this.Shape6.mirror = true;
    this.Shape7 = new ModelRenderer(this, 0, 40);
    this.Shape7.addBox(0.0F, 0.0F, 0.0F, 6, 2, 6);
    this.Shape7.setRotationPoint(1.0F, 4.0F, 1.0F);
    this.Shape7.setTextureSize(64, 64);
    this.Shape7.mirror = true;
    this.Shape8 = new ModelRenderer(this, 0, 40);
    this.Shape8.addBox(0.0F, 0.0F, 0.0F, 6, 2, 6);
    this.Shape8.setRotationPoint(9.0F, 4.0F, 1.0F);
    this.Shape8.setTextureSize(64, 64);
    this.Shape8.mirror = true;
    this.Shape9 = new ModelRenderer(this, 0, 40);
    this.Shape9.addBox(0.0F, 0.0F, 0.0F, 6, 2, 6);
    this.Shape9.setRotationPoint(1.0F, 4.0F, 9.0F);
    this.Shape9.setTextureSize(64, 64);
    this.Shape9.mirror = true;
    this.Shape10 = new ModelRenderer(this, 0, 40);
    this.Shape10.addBox(0.0F, 0.0F, 0.0F, 6, 2, 6);
    this.Shape10.setRotationPoint(9.0F, 4.0F, 9.0F);
    this.Shape10.setTextureSize(64, 64);
    this.Shape10.mirror = true;
  }

  public void renderAll() {
    this.Shape1.render(0.0625F);
    this.Shape2.render(0.0625F);
    this.Shape3.render(0.0625F);
    this.Shape4.render(0.0625F);
    this.Shape5.render(0.0625F);
    this.Shape6.render(0.0625F);
    this.Shape7.render(0.0625F);
    this.Shape8.render(0.0625F);
    this.Shape9.render(0.0625F);
    this.Shape10.render(0.0625F);
  }
}