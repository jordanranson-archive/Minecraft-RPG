package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderMagicRodProjectile extends Render
{
    /**
     * Have the icon index (in items.png) that will be used to render the image. Currently, eggs and snowballs uses this
     * classes.
     */
	
    public RenderMagicRodProjectile()
    {
	
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor3f(0.5F, 0.5F, 0.5F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.loadTexture("/gui/items.png");
        Tessellator var10 = Tessellator.instance;
		int icon = Item.snowball.iconIndex;
		
		int effectId = ((EntityMagicRodProjectile)par1Entity).getMagicEffectId();
		if(effectId == EnumMagicEffect.flame.getEffectId())
		{
			GL11.glColor3f(0.8F, 0.2F, 0.0F);
		}
		else if(effectId == EnumMagicEffect.freeze.getEffectId())
		{
			GL11.glColor3f(0.3F, 0.3F, 0.8F);
		}
		else if(effectId == EnumMagicEffect.lifesteal.getEffectId())
		{
			GL11.glColor3f(0.5F, 0.0F, 0.6F);
		}
		else if(effectId == EnumMagicEffect.flee.getEffectId())
		{
			GL11.glColor3f(0.1F, 0.6F, 0.0F);
		}
		
		this.func_77026_a(var10, icon);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
    }

    private void func_77026_a(Tessellator par1Tessellator, int par2)
    {
        float var3 = (float)(par2 % 16 * 16 + 0) / 256.0F;
        float var4 = (float)(par2 % 16 * 16 + 16) / 256.0F;
        float var5 = (float)(par2 / 16 * 16 + 0) / 256.0F;
        float var6 = (float)(par2 / 16 * 16 + 16) / 256.0F;
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
        par1Tessellator.addVertexWithUV((double)(0.0F - var8), (double)(0.0F - var9), 0.0D, (double)var3, (double)var6);
        par1Tessellator.addVertexWithUV((double)(var7 - var8), (double)(0.0F - var9), 0.0D, (double)var4, (double)var6);
        par1Tessellator.addVertexWithUV((double)(var7 - var8), (double)(var7 - var9), 0.0D, (double)var4, (double)var5);
        par1Tessellator.addVertexWithUV((double)(0.0F - var8), (double)(var7 - var9), 0.0D, (double)var3, (double)var5);
        par1Tessellator.draw();
    }
}
