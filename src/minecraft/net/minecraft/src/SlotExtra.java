package net.minecraft.src;

class SlotExtra extends Slot
{
    /**
     * The parent class of this clot, ContainerPlayer, SlotExtra is a Anon inner class.
     */
    final ContainerPlayer parent;

    SlotExtra(ContainerPlayer par1ContainerPlayer, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.parent = par1ContainerPlayer;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return 1;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
		if(par1ItemStack.getItem() == Item.healthGem)
			return true;
		if(par1ItemStack.getItem() == Item.frozenGem)
			return true;
		if(par1ItemStack.getItem() == Item.cagedMagma)
			return true;
		if(par1ItemStack.getItem() == Item.amethystWing)
			return true;
		if(par1ItemStack.getItem() == Item.enchantedVial)
			return true;
		if(par1ItemStack.getItem() == Item.spiritStone)
			return true;
		if(par1ItemStack.getItem() == Item.glowingFeather)
			return true;
			
		return false;
    }
}
