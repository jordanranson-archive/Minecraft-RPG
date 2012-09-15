package net.minecraft.src;

import java.util.Random;

public class EntitySack extends EntityThrowable
{
    public EntitySack(World par1World)
    {
        super(par1World);
    }

    public EntitySack(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntitySack(World par1World, double par2, double par4, double par6)
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
            byte var2 = 0;
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), var2);
        }
		
        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
	
	public ItemStack getSackContents(String sackType)
	{
		ItemStack sackContents;
		Item[] itemPool;
		
		if(sackType == "meat")
		{
			Item[] meat = new Item[7];
			meat[0] = Item.beefRaw;
			meat[1] = Item.beefCooked;
			meat[2] = Item.chickenRaw;
			meat[3] = Item.chickenCooked;
			meat[4] = Item.porkRaw;
			meat[5] = Item.porkCooked;
			meat[6] = Item.rottenFlesh;
			
			itemPool = new Item[meat.length];
			itemPool = meat;
		}
		else if(sackType == "treasure")
		{
			Item[] treasure = new Item[8];
			treasure[0] = Item.diamond;
			treasure[1] = Item.ingotGold;
			treasure[2] = Item.appleGold;
			treasure[3] = Item.expBottle;
			treasure[4] = Item.emerald;
			treasure[5] = Item.ruby;
			treasure[6] = Item.sapphire;
			treasure[7] = Item.amethyst;
			
			itemPool = new Item[treasure.length];
			itemPool = treasure;
		}
		else
		{
			Item[] junk = new Item[14];
			junk[0] = Item.flint;
			junk[1] = Item.coal;
			junk[2] = Item.silk;
			junk[3] = Item.feather;
			junk[4] = Item.stick;
			junk[5] = Item.gunpowder;
			junk[6] = Item.leather;
			junk[7] = Item.slimeBall;
			junk[8] = Item.bone;
			junk[9] = Item.glassBottle;
			junk[10] = Item.book;
			junk[11] = Item.roughStone;
			junk[12] = Item.hugeGeode;
			junk[13] = Item.powderMagic;
			
			itemPool = new Item[junk.length];
			itemPool = junk;
		}
		
		Random random = new Random();
		int nextItem = random.nextInt(itemPool.length);
		
		return new ItemStack(itemPool[nextItem]);
	}
}
