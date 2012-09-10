package net.minecraft.src;

public class EntityLifeStealFX extends EntityFX
{
    float lifeStealParticleScale;
	boolean isStar = false;
	
    public EntityLifeStealFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10)
    {
        this(par1World, par2, par4, par6, 1.0F, par8, par9, par10);
    }

    public EntityLifeStealFX(World par1World, double par2, double par4, double par6, float par8, float par9, float par10, float par11)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;

		if(Math.random() >= 0.5)
		{
			this.setParticleTextureIndex(66);
			this.isStar = true;
		}
		else
		{
			this.setParticleTextureIndex(65);
		}
		
		if(Math.random() >= 0.8)
		{
			this.particleRed = 0.0F;
			this.particleBlue = this.particleRed;
			this.particleGreen = this.particleRed;
		}
		else
		{
			float darken = (float)Math.random() * 0.3F;
			this.particleRed = 0.4F - darken;
			this.particleBlue = 0.6F - darken;
			this.particleGreen = 0.0F;
		}
		
        this.particleScale *= 0.8F - ((float)Math.random() * 0.3F);
        this.particleScale *= par8;
        this.lifeStealParticleScale = this.particleScale;
        this.particleMaxAge = (int)(12.0D / (Math.random() * 1.0D + 0.2D));
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

        this.particleScale = this.lifeStealParticleScale * var8;
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

		float percentDone = ((float)this.particleAge / (float)this.particleMaxAge) * 1.0F;
		if(percentDone > 0.5F)
		{
			if(this.isStar)
			{
				this.setParticleTextureIndex(65);
			}
			else
			{
				this.setParticleTextureIndex(66);
			}
		}
		
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

		if(this.particleBlue > 0.0F)
		{
			float flicker = this.particleRed + ((float)Math.random() * 0.2F) - 0.1F;
			if(flicker >= 0.5F) 
			{
				this.particleRed = 0.5F;
			}
			else if(flicker <= 0.3F)
			{
				this.particleRed = 0.3F;
			}
			else
			{
				this.particleRed = flicker;
			}
		}
		
		this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;

		if (this.onGround)
		{
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
    }
}
