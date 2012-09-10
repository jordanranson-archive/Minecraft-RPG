package net.minecraft.src;

public class EntityThrowingKnife extends EntityThrowable
{
	public EnumToolMaterial material;

    public EntityThrowingKnife(World par1World)
    {
        super(par1World);
    }

    public EntityThrowingKnife(World par1World, EntityLiving par2EntityLiving, EnumToolMaterial material)
    {
        super(par1World, par2EntityLiving);
		this.material = material;
    }

    public EntityThrowingKnife(World par1World, double par2, double par4, double par6)
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
			int var2 = 1;
            if(this.material == EnumToolMaterial.STONE)
			{
				var2 = 3;
			}
			else
			{
				var2 = 4;
			}

            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), var2);
        }

        for (int var3 = 0; var3 < 9; ++var3)
        {
            this.worldObj.spawnParticle("meatsplash", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}
