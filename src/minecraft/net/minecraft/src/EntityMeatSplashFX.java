package net.minecraft.src;

import java.util.Random;

public class EntityMeatSplashFX extends EntityFX
{
	private int[] meatIcon;

    public EntityMeatSplashFX(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleGravity = Block.blockSnow.blockParticleGravity;
        this.particleScale /= 2.0F;
		
		this.meatIcon = buildMeatIconList();
		
		Random random = new Random();
		int nextIcon = random.nextInt(this.meatIcon.length);
        this.setParticleTextureIndex(meatIcon[nextIcon]);
    }
	
	private int[] buildMeatIconList()
	{
		int[] meatIcon = new int[9];
		meatIcon[0] = Item.beefRaw.getIconFromDamage(0);
		meatIcon[1] = Item.beefCooked.getIconFromDamage(0);
		meatIcon[2] = Item.chickenRaw.getIconFromDamage(0);
		meatIcon[3] = Item.chickenCooked.getIconFromDamage(0);
		meatIcon[4] = Item.porkRaw.getIconFromDamage(0);
		meatIcon[5] = Item.porkCooked.getIconFromDamage(0);
		meatIcon[6] = Item.rottenFlesh.getIconFromDamage(0);
		meatIcon[7] = Item.redstone.getIconFromDamage(0);
		meatIcon[8] = Item.fermentedSpiderEye.getIconFromDamage(0);
		
		return meatIcon;
	}

    public int getFXLayer()
    {
        return 2;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var8 = ((float)(this.getParticleTextureIndex() % 16) + this.particleTextureJitterX / 4.0F) / 16.0F;
        float var9 = var8 + 0.015609375F;
        float var10 = ((float)(this.getParticleTextureIndex() / 16) + this.particleTextureJitterY / 4.0F) / 16.0F;
        float var11 = var10 + 0.015609375F;
        float var12 = 0.1F * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float var16 = 1.0F;
        par1Tessellator.setColorOpaque_F(var16 * this.particleRed, var16 * this.particleGreen, var16 * this.particleBlue);
        par1Tessellator.addVertexWithUV((double)(var13 - par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 - par5 * var12 - par7 * var12), (double)var8, (double)var11);
        par1Tessellator.addVertexWithUV((double)(var13 - par3 * var12 + par6 * var12), (double)(var14 + par4 * var12), (double)(var15 - par5 * var12 + par7 * var12), (double)var8, (double)var10);
        par1Tessellator.addVertexWithUV((double)(var13 + par3 * var12 + par6 * var12), (double)(var14 + par4 * var12), (double)(var15 + par5 * var12 + par7 * var12), (double)var9, (double)var10);
        par1Tessellator.addVertexWithUV((double)(var13 + par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 + par5 * var12 - par7 * var12), (double)var9, (double)var11);
    }
}
