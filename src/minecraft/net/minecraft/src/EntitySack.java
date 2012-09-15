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
			Item[] junk = new Item[42];
			junk[0] = Item.flintAndSteel;
			junk[1] = Item.appleRed;
			junk[2] = Item.arrow;
			junk[3] = Item.coal;
			junk[4] = Item.ingotIron;
			junk[5] = Item.stick;
			junk[6] = Item.silk;
			junk[7] = Item.feather;
			junk[8] = Item.gunpowder;
			junk[9] = Item.seeds;
			junk[10] = Item.wheat;
			junk[11] = Item.bread;
			junk[12] = Item.flint;
			junk[13] = Item.bucketEmpty;
			junk[14] = Item.leather;
			junk[15] = Item.brick;
			junk[16] = Item.paper;
			junk[17] = Item.book;
			junk[18] = Item.slimeBall;
			junk[19] = Item.compass;
			junk[20] = Item.fishingRod;
			junk[21] = Item.pocketSundial;
			junk[22] = Item.bone;
			junk[23] = Item.sugar;
			junk[24] = Item.cookie;
			junk[25] = Item.shears;
			junk[26] = Item.enderPearl;
			junk[27] = Item.goldNugget;
			junk[28] = Item.glassBottle;
			junk[29] = Item.spiderEye;
			junk[30] = Item.writableBook;
			junk[31] = Item.record13;
			junk[32] = Item.powderFragrant;
			junk[33] = Item.powderSparkling;
			junk[34] = Item.powderMagic;
			junk[35] = Item.roughStone;
			junk[36] = Item.hugeGeode;
			junk[37] = Item.dyePowder;
			junk[38] = Item.dyePowder;
			junk[39] = Item.dyePowder;
			junk[40] = Item.dyePowder;
			junk[41] = Item.dyePowder;
			
			itemPool = new Item[junk.length];
			itemPool = junk;
		}
		
		Random random = new Random();
		int nextItem = random.nextInt(itemPool.length);
		
		if(itemPool[nextItem] == Item.dyePowder)
		{
			return new ItemStack(itemPool[nextItem], 1, random.nextInt(16));
		}
		else if(itemPool[nextItem] == Item.record13)
		{
			Item[] record = new Item[11];
			record[0] = Item.record13;
			record[1] = Item.recordCat;
			record[2] = Item.recordBlocks;
			record[3] = Item.recordChirp;
			record[4] = Item.recordFar;
			record[5] = Item.recordMall;
			record[6] = Item.recordMellohi;
			record[7] = Item.recordStal;
			record[8] = Item.recordStrad;
			record[9] = Item.recordWard;
			record[10] = Item.record11;
		
			return new ItemStack(record[random.nextInt(record.length)]);
		}
		
		return new ItemStack(itemPool[nextItem]);
	}
}
