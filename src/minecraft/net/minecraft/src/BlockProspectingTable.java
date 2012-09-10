package net.minecraft.src;

import java.util.Random;

public class BlockProspectingTable extends BlockContainer
{
    protected BlockProspectingTable(int par1)
    {
        super(par1, 215, Material.rock);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
		double var6 = (double)((float)par2 + par5Random.nextFloat() / 2 + 0.1F);
        double var8 = (double)((float)par3 + 0.9F);
        double var10 = (double)((float)par4 + par5Random.nextFloat() / 2 + 0.1F);
        double var12 = 0.0D;
        double var14 = 0.0D;
        double var16 = 0.0D;
        par1World.spawnParticle("prospecttable", var6, var8, var10, var12, var14, var16);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return this.getBlockTextureFromSide(par1);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int par1)
    {
		// 0 - bottom, 1 - top, 3 - front
        return par1 == 1 ? this.blockIndexInTexture: (par1 == 0 ? 62 : ((par1 == 3 || par1 == 5) ? this.blockIndexInTexture + 16 : this.blockIndexInTexture + 1));
    }

    /**
     * each class overrdies this to return a new <className>
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityProspectingTable();
		//return null;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (par1World.isRemote)
        {
            return true;
        }
        else
        {
            par5EntityPlayer.displayGUIProspecting(par2, par3, par4);
            return true;
        }
    }
}
