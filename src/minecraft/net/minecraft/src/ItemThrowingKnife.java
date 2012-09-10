package net.minecraft.src;

public class ItemThrowingKnife extends Item
{
	public EnumToolMaterial material;
	
    public ItemThrowingKnife(int par1, EnumToolMaterial material)
    {
        super(par1);
        this.maxStackSize = 64;
        this.setTabToDisplayOn(CreativeTabs.tabCombat);
		this.material = material;
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
            par2World.spawnEntityInWorld(new EntityThrowingKnife(par2World, par3EntityPlayer, material));
        }

        return par1ItemStack;
    }
}
