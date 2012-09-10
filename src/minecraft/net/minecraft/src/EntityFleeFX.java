package net.minecraft.src;

public class EntityFleeFX extends EntityFX
{
    private int field_70590_a = 128;
	private float waveOffset = 0.0F;
	
    public EntityFleeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);

        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
		
		this.particleRed = 0.5F;
		this.particleBlue = 0.2F;
		this.particleGreen = 0.9F;
		
		this.waveOffset = (float)Math.random() * 100.0F;
		
		if(Math.random() > 0.75)
		{
			field_70590_a += 16;
		}
		
        this.particleScale *= 0.65F;
        this.particleMaxAge = (int)(12.0D / (Math.random() * 0.8D + 0.2D));
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

		this.setParticleTextureIndex(this.field_70590_a + (7 - this.particleAge * 8 / this.particleMaxAge));
		
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }
		
        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;
		
		this.particleRed = (float)Math.sin(this.particleAge / 10 + this.waveOffset) * 0.9F;
		this.particleBlue = (float)Math.sin(this.particleAge / 5 + this.waveOffset) * 0.4F;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    public void func_70589_b(int par1)
    {
        this.field_70590_a = par1;
    }
}
