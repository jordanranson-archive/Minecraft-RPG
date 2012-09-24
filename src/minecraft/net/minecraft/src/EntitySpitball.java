package net.minecraft.src;

public class EntitySpitball extends EntityThrowable
{
    public EntitySpitball(World par1World)
    {
        super(par1World);
    }

    public EntitySpitball(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntitySpitball(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
        {
			if(par1MovingObjectPosition.entityHit instanceof EntityLiving)
			{
				EntityLiving entity = (EntityLiving)par1MovingObjectPosition.entityHit;
				entity.addPotionEffect(new PotionEffect(19, 71, 0));
			}
			
			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), 1);
        }

        for (int var3 = 0; var3 < 8; ++var3)
        {
            this.worldObj.spawnParticle("meatsplash", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}
