package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ContainerEnchantment extends Container
{
    /** SlotEnchantmentTable object with ItemStack to be enchanted */
    public IInventory tableInventory = new SlotEnchantmentTable(this, "Enchant", 1);
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);
	public int effectId = 0;
	
    /** current world (for bookshelf counting) */
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private Random rand = new Random();

    /** used as seed for EnchantmentNameParts (see GuiEnchantment) */
    public long nameSeed;

    /** 3-member array storing the enchantment levels of each slot */
    public int[] enchantLevels = new int[3];

    public ContainerEnchantment(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        this.worldPointer = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.addSlotToContainer(new SlotEnchantment(this, this.tableInventory, 0, 12, 47));
		this.addSlotToContainer(new Slot(this.craftMatrix, 0, 37, 47));
        int var6;

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (int var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
        }
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.updateCraftingInventoryInfo(this, 0, this.enchantLevels[0]);
        par1ICrafting.updateCraftingInventoryInfo(this, 1, this.enchantLevels[1]);
        par1ICrafting.updateCraftingInventoryInfo(this, 2, this.enchantLevels[2]);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.updateCraftingResults();
        Iterator var1 = this.crafters.iterator();

        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting)var1.next();
            var2.updateCraftingInventoryInfo(this, 0, this.enchantLevels[0]);
            var2.updateCraftingInventoryInfo(this, 1, this.enchantLevels[1]);
            var2.updateCraftingInventoryInfo(this, 2, this.enchantLevels[2]);
        }
    }

    public void updateProgressBar(int par1, int par2)
    {
        if (par1 >= 0 && par1 <= 2)
        {
            this.enchantLevels[par1] = par2;
        }
        else
        {
            super.updateProgressBar(par1, par2);
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory par1IInventory)
    {
		ItemStack gemSlot = this.craftMatrix.getStackInSlot(0);
		ItemStack var2 = this.tableInventory.getStackInSlot(0);
		int var3;
		
		if(gemSlot != null && this.isGem(gemSlot) && var2 != null && this.isWeapon(var2))
		{
			setEffectByItem(gemSlot);
		}
		else
		{
			this.effectId = 0;
		}

		if (var2 != null && var2.isItemEnchantable())
		{
			this.nameSeed = this.rand.nextLong();

			if (!this.worldPointer.isRemote)
			{
				var3 = 0;
				int var4;

				for (var4 = -1; var4 <= 1; ++var4)
				{
					for (int var5 = -1; var5 <= 1; ++var5)
					{
						if ((var4 != 0 || var5 != 0) && this.worldPointer.isAirBlock(this.posX + var5, this.posY, this.posZ + var4) && this.worldPointer.isAirBlock(this.posX + var5, this.posY + 1, this.posZ + var4))
						{
							if (this.worldPointer.getBlockId(this.posX + var5 * 2, this.posY, this.posZ + var4 * 2) == Block.bookShelf.blockID)
							{
								++var3;
							}

							if (this.worldPointer.getBlockId(this.posX + var5 * 2, this.posY + 1, this.posZ + var4 * 2) == Block.bookShelf.blockID)
							{
								++var3;
							}

							if (var5 != 0 && var4 != 0)
							{
								if (this.worldPointer.getBlockId(this.posX + var5 * 2, this.posY, this.posZ + var4) == Block.bookShelf.blockID)
								{
									++var3;
								}

								if (this.worldPointer.getBlockId(this.posX + var5 * 2, this.posY + 1, this.posZ + var4) == Block.bookShelf.blockID)
								{
									++var3;
								}

								if (this.worldPointer.getBlockId(this.posX + var5, this.posY, this.posZ + var4 * 2) == Block.bookShelf.blockID)
								{
									++var3;
								}

								if (this.worldPointer.getBlockId(this.posX + var5, this.posY + 1, this.posZ + var4 * 2) == Block.bookShelf.blockID)
								{
									++var3;
								}
							}
						}
					}
				}

				if(!this.isWeapon(var2) && gemSlot == null)
				{
					;
				}
				else if(this.isWeapon(var2) && gemSlot == null)
				{
					;
				}
				else if(this.isWeapon(var2) && gemSlot != null && this.isGem(gemSlot))
				{
					;
				}
				else 
				{
					for (var3 = 0; var3 < 3; ++var3)
					{
						this.enchantLevels[var3] = 0;
					}
					
					return;
				}

				for (var4 = 0; var4 < 3; ++var4)
				{
					int level = EnchantmentHelper.calcItemStackEnchantability(this.rand, var4, var3, var2);
					if(gemSlot != null && this.isGem(gemSlot) && level < 10)
					{
						level = 10;
					}
					this.enchantLevels[var4] = level;
				}
				
				this.updateCraftingResults();
			}
		}
		else if(var2 != null && gemSlot != null && var2.getItem() instanceof ItemTrinket && gemSlot.getItem() instanceof ItemRunicDiamond)
		{
			this.enchantLevels[0] = 20;
			this.enchantLevels[1] = 0;
			this.enchantLevels[2] = 0;
			
			this.updateCraftingResults();
		}
		else
		{
			for (var3 = 0; var3 < 3; ++var3)
			{
				this.enchantLevels[var3] = 0;
			}
		}
    }

    /**
     * enchants the item on the table using the specified slot; also deducts XP from player
     */
    public boolean enchantItem(EntityPlayer par1EntityPlayer, int par2, int effectId)
    {
        ItemStack var3 = this.tableInventory.getStackInSlot(0);
        ItemStack gemSlot = this.craftMatrix.getStackInSlot(0);

        if (this.enchantLevels[par2] > 0 && var3 != null && (par1EntityPlayer.experienceLevel >= this.enchantLevels[par2] || par1EntityPlayer.capabilities.isCreativeMode))
        {
			if (!this.worldPointer.isRemote)
            {
                List var4 = EnchantmentHelper.buildEnchantmentList(this.rand, var3, this.effectId, this.enchantLevels[par2]);

                if (var4 != null)
                {
                    par1EntityPlayer.removeExperience(this.enchantLevels[par2]);
                    Iterator var5 = var4.iterator();

                    while (var5.hasNext())
                    {
                        EnchantmentData var6 = (EnchantmentData)var5.next();
                        var3.addEnchantment(var6.enchantmentobj, var6.enchantmentLevel);
                    }

					if(gemSlot != null)
					{
						this.craftMatrix.decrStackSize(0, 1);
                    }
					
					this.onCraftMatrixChanged(this.tableInventory);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);

        if (!this.worldPointer.isRemote)
        {
            ItemStack var2 = this.tableInventory.getStackInSlotOnClosing(0);
            ItemStack gem = this.craftMatrix.getStackInSlotOnClosing(0);

            if (var2 != null)
            {
                par1EntityPlayer.dropPlayerItem(var2);
            }
			if (gem != null)
            {
                par1EntityPlayer.dropPlayerItem(gem);
            }
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.worldPointer.getBlockId(this.posX, this.posY, this.posZ) != Block.enchantmentTable.blockID ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
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
			
			if (par1 == 0 || par1 == 1) // item or gem slot to inventory
            {
				if (!this.mergeItemStack(var4, 2, 29, true))
				{
					if (!this.mergeItemStack(var4, 2, 38, true))
					{
						return null;
					}
				}
            }
            else
            {
				if(this.isGem(var4) || var4.getItem() instanceof ItemRunicDiamond) // inventory to gem slot
				{
					if (((Slot)this.inventorySlots.get(1)).getHasStack() || !((Slot)this.inventorySlots.get(1)).isItemValid(var4))
					{
						return null;
					}
					
					if (!this.mergeItemStack(var4, 1, 2, true))
					{
						return null;
					}
				}
				else // inventory to item slot
				{
					if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(var4))
					{
						return null;
					}

					if (var4.hasTagCompound() && var4.stackSize == 1)
					{
						((Slot)this.inventorySlots.get(0)).putStack(var4.copy());
						var4.stackSize = 0;
					}
					else if (var4.stackSize >= 1)
					{
						((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(var4.itemID, 1, var4.getItemDamage()));
						--var4.stackSize;
					}
				}
				
                
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
	
	private boolean isWeapon(ItemStack item)
	{
		int[] itemId = new int[7];
		itemId[0] = Item.swordWood.shiftedIndex;
		itemId[1] = Item.swordStone.shiftedIndex;
		itemId[2] = Item.swordSteel.shiftedIndex;
		itemId[3] = Item.swordDiamond.shiftedIndex;
		itemId[4] = Item.swordGold.shiftedIndex;
		itemId[5] = Item.bow.shiftedIndex;
		itemId[6] = Item.crossbow.shiftedIndex;
		
		for(int i = 0; i < itemId.length; i++)
		{
			if(itemId[i] == item.getItem().shiftedIndex)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isGem(ItemStack item)
	{
		/*int[] itemId = new int[4];
		itemId[0] = Item.rubyCharged.shiftedIndex;
		itemId[1] = Item.sapphireCharged.shiftedIndex;
		itemId[2] = Item.amethystCharged.shiftedIndex;
		itemId[3] = Item.emeraldCharged.shiftedIndex;
		
		for(int i = 0; i < itemId.length; i++)
		{
			if(itemId[i] == item.getItem().shiftedIndex)
			{
				return true;
			}
		}*/
		
		return item.getItem() instanceof ItemGem;
	}
	
	private void setEffectByItem(ItemStack item)
	{
		int itemId = item.getItem().shiftedIndex;
		if(itemId == Item.rubyCharged.shiftedIndex)
		{
			this.effectId = EnumMagicEffect.flame.getEffectId();
		}
		if(itemId == Item.sapphireCharged.shiftedIndex)
		{
			this.effectId = EnumMagicEffect.freeze.getEffectId();
		}
		if(itemId == Item.amethystCharged.shiftedIndex)
		{
			this.effectId = EnumMagicEffect.lifesteal.getEffectId();
		}
		if(itemId == Item.emeraldCharged.shiftedIndex)
		{
			this.effectId = EnumMagicEffect.flee.getEffectId();
		}
	}
}
