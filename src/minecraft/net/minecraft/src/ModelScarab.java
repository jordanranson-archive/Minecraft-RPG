// Date: 9/25/2012 11:09:00 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package net.minecraft.src;

public class ModelScarab extends ModelBase
{
  //fields
    ModelRenderer body;
    ModelRenderer head;
    ModelRenderer legs1;
    ModelRenderer legs2;
  
  public ModelScarab()
  {
    textureWidth = 32;
    textureHeight = 32;
    
      body = new ModelRenderer(this, 0, 0);
      body.addBox(-2F, 0F, -1F, 3, 2, 4);
      body.setRotationPoint(0F, 22F, 0F);
      body.setTextureSize(32, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      head = new ModelRenderer(this, 14, 0);
      head.addBox(-1.5F, 0.5F, -2F, 2, 1, 1);
      head.setRotationPoint(0F, 22F, 0F);
      head.setTextureSize(32, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      legs1 = new ModelRenderer(this, -6, 6);
      legs1.addBox(1F, 0.5F, -2F, 2, 0, 6);
      legs1.setRotationPoint(0F, 23F, 0F);
      legs1.setTextureSize(32, 32);
      legs1.mirror = true;
      setRotation(legs1, 0F, 0F, 0F);
      legs2 = new ModelRenderer(this, -2, 6);
      legs2.addBox(-4F, -0.5F, -2F, 2, 0, 6);
      legs2.setRotationPoint(0F, 24F, 0F);
      legs2.setTextureSize(32, 32);
      legs2.mirror = true;
      setRotation(legs2, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    body.render(f5);
    head.render(f5);
    legs1.render(f5);
    legs2.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5);
	
	float var7 = 1.0F;
	this.legs1.rotateAngleY = MathHelper.cos(f2 * 0.9F + (float)var7 * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (float)(1 + Math.abs(var7 - 2));
	this.legs1.rotationPointX = MathHelper.sin(f2 * 0.9F + (float)var7 * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * (float)Math.abs(var7 - 2); 
	this.legs2.rotateAngleY = MathHelper.cos(f2 * 0.9F + (float)var7 * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (float)(1 + Math.abs(var7 - 2));
	this.legs2.rotationPointX = MathHelper.sin(f2 * 0.9F + (float)var7 * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * (float)Math.abs(var7 - 2); 
  }

}
