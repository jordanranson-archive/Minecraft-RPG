package net.minecraft.src;

import java.util.Random;

public class ContainerProspecting extends Container
{
    /** The crafting matrix inventory (3x3). */
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 1, 1);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;
	private boolean canProspect = false;
	public int[] lootList = new int[2];

    public ContainerProspecting(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
    {
        this.worldObj = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
		// result
        this.addSlotToContainer(new SlotProspecting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 141, 35));
        int var6;
        int var7;
		
		// input
		this.addSlotToContainer(new Slot(this.craftMatrix, 0, 19, 35));

        for (var6 = 0; var6 < 3; ++var6)
        {
            for (var7 = 0; var7 < 9; ++var7)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
            }
        }

        for (var6 = 0; var6 < 9; ++var6)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
	public void onCraftMatrixChanged(IInventory inventory)
    {
		ItemStack itemSlotInput = this.craftMatrix.getStackInSlot(0);
		ItemStack output = this.craftResult.getStackInSlot(0);
		
		if (itemSlotInput != null && output == null && this.isItemProspectable(itemSlotInput))
		{
			this.setProspect(true);
		}
		else
		{
			this.setProspect(false);
		}
		
		/*this.setLootResults();
		
		ItemStack input = this.craftMatrix.getStackInSlot(0);
		ItemStack output = this.craftResult.getStackInSlot(0);

		if (input != null && output == null && this.isItemProspectable(input))
		{
			if(Item.itemsList[this.lootList[0]] != null)
			{
				this.craftResult.setInventorySlotContents(0, new ItemStack(Item.itemsList[this.lootList[0]], this.lootList[1]));
			}
			
			this.onCraftMatrixChanged(this.craftMatrix);
		}*/
    }
	
	public boolean prospectItem(int itemId, int quantity)
	{
		ItemStack input = this.craftMatrix.getStackInSlot(0);
		ItemStack output = this.craftResult.getStackInSlot(0);

		if (input != null && output == null && this.isItemProspectable(input))
		{
			if(!this.worldObj.isRemote)
			{
				this.craftMatrix.decrStackSize(0, 1);
				this.onCraftMatrixChanged(this.craftMatrix);
			}

			if(Item.itemsList[itemId] != null)
			{
				this.craftResult.setInventorySlotContents(0, new ItemStack(Item.itemsList[itemId], quantity));
			}

			return true;
		}
		else
		{
			return false;
		}
	}
	
	private int nextRange(int min, int max)
	{
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}
	
	public void setLootResults()
	{
		ItemStack input = this.craftMatrix.getStackInSlot(0);
		this.lootList[0] = 0;
		this.lootList[1] = 0;
		
		if(input != null)
		{
			if(input.getItem().shiftedIndex == Item.roughStone.shiftedIndex)
			{
				switch(nextRange(0, 8))
				{
					case 0:
					case 8:
						this.lootList[0] = Item.goldNugget.shiftedIndex;
						this.lootList[1] = nextRange(1, 2);
						break;
					case 1:
						this.lootList[0] = Item.emerald.shiftedIndex;
						this.lootList[1] = 1;
						break;
					case 2:
						this.lootList[0] = Item.ruby.shiftedIndex;
						this.lootList[1] = nextRange(1, 2);
						break;
					case 3:
						this.lootList[0] = Item.sapphire.shiftedIndex;
						this.lootList[1] = nextRange(1, 2);
						break;
					case 4:
						this.lootList[0] = Item.amethyst.shiftedIndex;
						this.lootList[1] = nextRange(1, 2);
						break;
					case 5:
						this.lootList[0] = Item.diamond.shiftedIndex;
						this.lootList[1] = 1;
						break;
					case 6:
					case 7:
						this.lootList[0] = Block.gravel.blockID;
						this.lootList[1] = nextRange(1, 2);
						break;
				}
			}
			else if(input.getItem().shiftedIndex == Item.hugeGeode.shiftedIndex)
			{
				switch(nextRange(0, 4))
				{
					case 0:
						this.lootList[0] = Item.emerald.shiftedIndex;
						this.lootList[1] = nextRange(1, 3);
						break;
					case 1:
						this.lootList[0] = Item.ruby.shiftedIndex;
						this.lootList[1] = nextRange(1, 4);
						break;
					case 2:
						this.lootList[0] = Item.sapphire.shiftedIndex;
						this.lootList[1] = nextRange(1, 4);
						break;
					case 3:
						this.lootList[0] = Item.amethyst.shiftedIndex;
						this.lootList[1] = nextRange(1, 4);
						break;
					case 4:
						this.lootList[0] = Item.diamond.shiftedIndex;
						this.lootList[1] = nextRange(1, 2);
						break;
				}
				
			}
			else
			{
				if(nextRange(0, 4) == 0)
				{
					this.lootList[0] = Item.hugeGeode.shiftedIndex;
					this.lootList[1] = nextRange(1, 2);
				}
				else
				{
					this.lootList[0] = Item.roughStone.shiftedIndex;
					this.lootList[1] = nextRange(1, 2);
				}
			}
		}
	}

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote)
        {
			ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(0);
			ItemStack var4 = this.craftResult.getStackInSlotOnClosing(0);

			if (var3 != null)
			{
				par1EntityPlayer.dropPlayerItem(var3);
			}
			if (var4 != null)
			{
				par1EntityPlayer.dropPlayerItem(var4);
			}
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockId(this.posX, this.posY, this.posZ) != Block.prospectingTable.blockID ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
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

            if (par1 == 0)
            {
                if (!this.mergeItemStack(var4, 2, 38, true))
                {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            }
			else if (par1 == 1)
            {
                if (!this.mergeItemStack(var4, 2, 29, true))
                {
					if (!this.mergeItemStack(var4, 2, 38, true))
					{
						return null;
					}
				}

                var3.onSlotChange(var4, var2);
            }
            else if (par1 >= 2 && par1 < 38)
            {
                if (!this.mergeItemStack(var4, 1, 2, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 2, 38, false))
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

        return null;
    }
	
	private boolean isItemProspectable(ItemStack item)
	{
		int[] itemId = new int[4];
		itemId[0] = Block.oreIron.blockID;
		itemId[1] = Block.oreGold.blockID;
		itemId[2] = Item.roughStone.shiftedIndex;
		itemId[3] = Item.hugeGeode.shiftedIndex;
		
		for(int i = 0; i < itemId.length; i++)
		{
			if(itemId[i] == item.getItem().shiftedIndex)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void setProspect(boolean result)
	{
		this.canProspect = result;
	}
	
	public boolean getProspect()
	{
		return this.canProspect;
	}
}
