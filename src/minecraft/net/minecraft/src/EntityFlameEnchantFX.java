package net.minecraft.src;

public class EntityFlameEnchantFX extends EntityFX
{
    float flameEnchantParticleScale;
	boolean isSmoke = false;
	
    public EntityFlameEnchantFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10)
    {
        this(par1World, par2, par4, par6, 1.0F, par8, par9, par10);
    }

    public EntityFlameEnchantFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float par11)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.2D;
        this.motionZ *= 0.10000000149011612D;

        if (par9 == 0.0F)
        {
            par9 = 1.0F;
        }

		if((int)(Math.random() * 2) == 0)
		{
			float colour = (float)Math.random() * 0.45F;
			this.particleRed = colour;
			this.particleGreen = colour;
			this.particleBlue = colour;
			this.isSmoke = true;
		}
		else
		{
			this.particleRed = 1.0F;
			this.particleGreen = 0.6F;
			this.particleBlue = 0.0F;
		}
        
        this.particleScale *= 0.75F;
        this.particleScale *= par8;
        this.flameEnchantParticleScale = this.particleScale;
        this.particleMaxAge = (int)(12.0D / (Math.random() * 0.8D + (this.isSmoke ? 0.6D : 0.2D)));
        this.particleMaxAge = (int)((float)this.particleMaxAge * par8);
        this.noClip = true;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var8 = ((float)this.particleAge + par2) / (float)this.particleMaxAge * 32.0F;

        if (var8 < 0.0F)
        {
            var8 = 0.0F;
        }

        if (var8 > 1.0F)
        {
            var8 = 1.0F;
        }

        this.particleScale = this.flameEnchantParticleScale * var8;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }
		
		if(!this.isSmoke)
		{
			float percentDone = ((float)this.particleAge / (float)this.particleMaxAge) * 1.0F;
			if(percentDone > (float)Math.random() * 0.2)
			{
				percentDone = ((float)this.particleAge / (float)this.particleMaxAge - 0.15F) * 1.0F;
				this.particleRed = (1.0F - percentDone <= 0.0F) ? 0.0F : 1.0F - percentDone;
				this.particleGreen = (0.6F - (percentDone + (percentDone / 2.0F)) <= 0.0F) ? 0.0F : 0.6F - (percentDone + (percentDone / 2.0F));
				this.particleBlue = 0.0F;
			}
		}
		else
		{
			this.particleRed = (this.particleRed <= 0.0F) ? 0.0F : this.particleRed - (float)0.0125D;
			this.particleGreen = this.particleRed;
			this.particleBlue = this.particleRed;
		}
		
		this.motionX *= 0.9599999785423279D;
		this.motionY *= 1.0D;
		this.motionZ *= 0.9599999785423279D;

		if (this.onGround)
		{
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
    }
	
	public int getBrightnessForRender(float par1)
    {
        float var2 = ((float)this.particleAge + par1) / (float)this.particleMaxAge;

        if (var2 < 0.0F)
        {
            var2 = 0.0F;
        }

        if (var2 > 1.0F)
        {
            var2 = 1.0F;
        }

        int var3 = super.getBrightnessForRender(par1);
        int var4 = var3 & 255;
        int var5 = var3 >> 16 & 255;
        var4 += (int)(var2 * 15.0F * 16.0F);

        if (var4 > 240)
        {
            var4 = 240;
        }

        return var4 | var5 << 16;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        float var2 = ((float)this.particleAge + par1) / (float)this.particleMaxAge;

        if (var2 < 0.0F)
        {
            var2 = 0.0F;
        }

        if (var2 > 1.0F)
        {
            var2 = 1.0F;
        }

        float var3 = super.getBrightness(par1);
        return var3 * var2 + (1.0F - var2);
    }
}
