package net.minecraft.src;

import java.util.Random;

public class BlockStone extends Block
{
    public BlockStone(int par1, int par2)
    {
        super(par1, par2, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);

        if (this.idDropped(par5, par1World.rand, par7) != this.blockID)
        {
			if(MathHelper.getRandomIntegerInRange(par1World.rand, 0, 66) == 0)
			{
				float var10 = 0.7F;
				float var11 = par1World.rand.nextFloat() * var10 + (1.0F - var10) * 0.5F;
				float var12 = par1World.rand.nextFloat() * var10 + (1.0F - var10) * 0.5F;
				float var13 = par1World.rand.nextFloat() * var10 + (1.0F - var10) * 0.5F;
			
				int numGeodes = MathHelper.getRandomIntegerInRange(par1World.rand, 1, 2);
				for(int i = 0; i < numGeodes; i++)
				{
					boolean isGeode = false;
					isGeode = MathHelper.getRandomIntegerInRange(par1World.rand, 0, 4) == 0;
					EntityItem var14 = new EntityItem(
						par1World, 
						(double)((float)par2 + var11),
						(double)((float)par3 + var12), 
						(double)((float)par4 + var13), 
						new ItemStack(isGeode ? Item.hugeGeode : Item.roughStone)
					);	
					var14.delayBeforeCanPickup = 10;
					par1World.spawnEntityInWorld(var14);
				}
			}
        }
    }
	
    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.cobblestone.blockID;
    }
}
