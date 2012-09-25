package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockScarab extends Block
{
    /** Block names that can be a silverfish stone. */
    public static final String[] scarabBlockTypes = new String[] {"sandStone", "sand"};

    public BlockScarab(int par1)
    {
        super(par1, 1, Material.clay);
        this.setHardness(0.0F);
        this.setCreativeTab(CreativeTabs.tabDeco);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par2 == 1 ? Block.sand.blockIndexInTexture : (
			   par1 == 1 ? Block.sandStone.blockIndexInTexture - 16 : (par1 == 0 ? Block.sandStone.blockIndexInTexture + 16 : Block.sandStone.blockIndexInTexture));
    }

    private void spawnScarabs(World world, int x, int y, int z, int numScarabs) 
	{
		if (!world.isRemote)
        {
			Random random = new Random();
			
			for(int i = 0; i < numScarabs; i++)
			{
				EntityScarab scarab;
				if(random.nextInt(999) == 0)
					scarab = (EntityScarab)(new EntityScarabGolden(world));
				else
					scarab = new EntityScarab(world);
					
				scarab.setLocationAndAngles((double)x + 0.5D, (double)y + 1.0D, (double)z + 0.5D, 0.0F, 0.0F);
				world.spawnEntityInWorld(scarab);
				scarab.spawnExplosionParticle();
			}
        }
	}
	
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
		Random random = new Random();
        spawnScarabs(par1World, par2, par3, par4, random.nextInt(3) + 2);
		
        super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
    }
	
	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
		Random random = new Random();
        spawnScarabs(par1World, par2, par3, par4, random.nextInt(2) + 1);
		
        super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
	
	public int tickRate()
    {
        return 30;
    }

    /**
     * Gets the blockID of the block this block is pretending to be according to this block's metadata.
     */
    public static boolean getPosingIdByMetadata(int par0)
    {
        return par0 == Block.sandStone.blockID || par0 == Block.sand.blockID;
    }

    /**
     * Returns the metadata to use when a Silverfish hides in the block. Sets the block to BlockScarab with this
     * metadata. It changes the displayed texture client side to look like a normal block.
     */
    public static int getMetadataForBlockType(int par0)
    {
        return par0 == Block.sand.blockID ? 1 : 0;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int par1)
    {
        Block var2 = Block.sandStone;

        if (par1 == 1)
        {
            var2 = Block.sand;
        }

        return new ItemStack(var2);
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockMetadata(par2, par3, par4);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 3; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
