package net.minecraft.src;

import java.util.Random;

public class WorldGenFlowersNoise extends WorldGenerator
{
    /** The ID of the plant block used in this plant generator. */
    private int plantBlockId;
    private int plantSecondBlockId;

    public WorldGenFlowersNoise(int par1, int par2)
    {
        this.plantBlockId = par1;
		this.plantSecondBlockId = par2;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < 64; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(var7, var8, var9) && Block.blocksList[this.plantBlockId].canBlockStay(par1World, var7, var8, var9))
            {
				if(par2Random.nextInt(6) == 0)
				{
					if(this.plantSecondBlockId == Block.plantPurple.blockID)
					{
						par1World.setBlockAndMetadata(var7, var8, var9, this.plantSecondBlockId, 3);
					}
					else 
					{
						par1World.setBlock(var7, var8, var9, this.plantSecondBlockId);
					}
				}
				else
				{
					if(this.plantBlockId == Block.plantPurple.blockID)
					{
						par1World.setBlockAndMetadata(var7, var8, var9, this.plantBlockId, 3);
					}
					else 
					{
						par1World.setBlock(var7, var8, var9, this.plantBlockId);
					}
				}
            }
        }

        return true;
    }
}
