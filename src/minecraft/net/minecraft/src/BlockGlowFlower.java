package net.minecraft.src;

import java.util.Random;

public class BlockGlowFlower extends BlockFlower
{
    protected BlockGlowFlower(int par1, int par2)
    {
        super(par1, par2);
        float var3 = 0.3F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
        this.setTickRandomly(true);
		this.setLightValue(0.45F);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(16) == 0)
        {
			int var8 = par2 + (par5Random.nextInt(15) + (par5Random.nextInt(6) - 3)) - 8;
			int var9 = par3 + par5Random.nextInt(6) - 4;
			int var10 = par4 + (par5Random.nextInt(15) + (par5Random.nextInt(6) - 3)) - 8;

			if (par1World.getBlockId(var8, var9, var10) != this.blockID && par1World.isAirBlock(var8, var9, var10) && this.canBlockStay(par1World, var8, var9, var10))
			{
				par1World.setBlockWithNotify(var8, var9, var10, this.blockID - 1);
			}
		}
    }
	
	protected void growSpore(World par1World, int par2, int par3, int par4, Random par5Random) 
	{

	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        double var6 = (double)((float)par2 + par5Random.nextFloat());
        double var8 = (double)((float)par3 + 0.8F);
        double var10 = (double)((float)par4 + par5Random.nextFloat());
        double var12 = 0.0D;
        double var14 = 0.0D;
        double var16 = 0.0D;
        par1World.spawnParticle("glowflower", var6, var8, var10, var12, var14, var16);
    }
	
    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && this.canBlockStay(par1World, par2, par3, par4);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean canThisPlantGrowOnThisBlockID(int par1)
    {
        return par1 == Block.grass.blockID || par1 == Block.dirt.blockID || par1 == Block.tilledField.blockID || par1 == Block.mycelium.blockID;
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        if (par3 >= 0 && par3 < 256)
        {
            int var5 = par1World.getBlockId(par2, par3 - 1, par4);
            return var5 == Block.mycelium.blockID || this.canThisPlantGrowOnThisBlockID(var5);
        }
        else
        {
            return false;
        }
    }

    /**
     * Fertilize the mushroom.
     */
    public void fertilize(World par1World, int par2, int par3, int par4, Random par5Random)
    {
		int numSpores = par5Random.nextInt(4) + 1;
		for(int i = 0; i < numSpores; i++)
		{
			int var8 = par2 + (par5Random.nextInt(15) + (par5Random.nextInt(6) - 3)) - 8;
			int var9 = par3 + par5Random.nextInt(6) - 4;
			int var10 = par4 + (par5Random.nextInt(15) + (par5Random.nextInt(6) - 3)) - 8;

			if (par1World.getBlockId(var8, var9, var10) != this.blockID && par1World.isAirBlock(var8, var9, var10) && this.canBlockStay(par1World, var8, var9, var10))
			{
				par1World.setBlockWithNotify(var8, var9, var10, this.blockID - 1);
			}
		}
    }
}
