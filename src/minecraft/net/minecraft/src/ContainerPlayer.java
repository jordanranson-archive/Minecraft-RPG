package net.minecraft.src;

public class ContainerPlayer extends Container
{
    /** The crafting matrix inventory. */
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;

    /** Determines if inventory manipulation should be handled. */
    public boolean isLocalWorld;

    public ContainerPlayer(InventoryPlayer par1InventoryPlayer)
    {
        this(par1InventoryPlayer, true);
    }

    public ContainerPlayer(InventoryPlayer par1InventoryPlayer, boolean par2)
    {
        this.craftMatrix = new InventoryCrafting(this, 2, 2);
        this.craftResult = new InventoryCraftResult();
        this.isLocalWorld = false;
        this.isLocalWorld = par2;
        this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 126, 62));
        int var3;
        int var4;

        for (var3 = 0; var3 < 2; ++var3)
        {
            for (var4 = 0; var4 < 2; ++var4)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, var4 + var3 * 2, 118 + var4 * 18, 8 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 4; ++var3)
        {
            this.addSlotToContainer(new SlotArmor(this, par1InventoryPlayer, par1InventoryPlayer.getSizeInventory() - 1 - var3, 8, 8 + var3 * 18, var3));
        }

		for (var3 = 0; var3 < 4; ++var3)
        {
            this.addSlotToContainer(new SlotExtra(this, par1InventoryPlayer, (par1InventoryPlayer.getSizeInventory() + 4) - 1 - var3, 80, 8 + var3 * 18));
        }

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + (var3 + 1) * 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix));
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);

        for (int var2 = 0; var2 < 4; ++var2)
        {
            ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);

            if (var3 != null)
            {
                par1EntityPlayer.dropPlayerItem(var3);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 == 0) // crafting output
            {
                if (!this.mergeItemStack(var4, 13, 49, true))
                {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            }
            else if (par1 >= 1 && par1 < 5) // crafting matrix
            {
                if (!this.mergeItemStack(var4, 13, 49, false))
                {
                    return null;
                }
            }
            else if (par1 >= 5 && par1 < 9) // item out of armor
            {
                if (!this.mergeItemStack(var4, 13, 49, false))
                {
                    return null;
                }
            }
            else if (par1 >= 9 && par1 < 13) // item out of armor
            {
                if (!this.mergeItemStack(var4, 13, 49, false))
                {
                    return null;
                }
            }
			// item into armor
            else if (var2.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)var2.getItem()).armorType)).getHasStack())
            {
                int var5 = 5 + ((ItemArmor)var2.getItem()).armorType;

                if (!this.mergeItemStack(var4, var5, var5 + 1, false))
                {
                    return null;
                }
            }
			// item into extra
            else if (var2.getItem() instanceof ItemTrinket)
            {
				int var5;
				
				for(int i = 0; i < 4; i++)
				{
					var5 = 9 + i;

					if (!this.mergeItemStack(var4, var5, var5 + 1, false))
					{
						return null;
					}
				}
            }
            else if (par1 >= 13 && par1 < 40) // inventory to hotbar
            {
                if (!this.mergeItemStack(var4, 40, 49, false))
                {
                    return null;
                }
            }
            else if (par1 >= 40 && par1 < 49) // hot bar into inventory
            {
                if (!this.mergeItemStack(var4, 13, 40, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 13, 49, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(var4);
        }

        return var2;
    }
}
