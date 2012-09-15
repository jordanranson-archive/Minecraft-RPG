package net.minecraft.src;

public class ItemSack extends Item
{
	public String sackType = "meat";

    public ItemSack(int par1, String sackType)
    {
        super(par1);
        this.maxStackSize = 16;
        this.setTabToDisplayOn(CreativeTabs.tabMisc);
		this.sackType = sackType;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote)
        {
			if(this.sackType == "meat")
			{
				par2World.spawnEntityInWorld(new EntitySackMeat(par2World, par3EntityPlayer));
			}
			if(this.sackType == "treasure")
			{
				par2World.spawnEntityInWorld(new EntitySackTreasure(par2World, par3EntityPlayer));
			}
			if(this.sackType == "junk")
			{
				par2World.spawnEntityInWorld(new EntitySackJunk(par2World, par3EntityPlayer));
			}
		}

        return par1ItemStack;
    }
}
