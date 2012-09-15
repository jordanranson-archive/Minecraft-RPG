package net.minecraft.src;

import java.util.Random;

public class EntitySackJunk extends EntitySack
{
	public EntitySackJunk(World par1World)
    {
        super(par1World);
    }

    public EntitySackJunk(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }
	
	public EntitySackJunk(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
	
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
		for (int var3 = 0; var3 < 7; ++var3)
		{
			this.worldObj.spawnParticle("junksackimpact", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}
		if (!this.worldObj.isRemote)
		{
			Random random = new Random();
			int nextRandom = random.nextInt(4) + 3;
			for (int var3 = 0; var3 < nextRandom; ++var3)
			{
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, super.getSackContents("junk")));
			}
		}
		
		super.onImpact(par1MovingObjectPosition);
	}
}