package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiContainerCreative extends InventoryEffectRenderer
{
    private static InventoryBasic inventory = new InventoryBasic("tmp", 45);

    /** Currently selected creative inventory tab index. */
    private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();

    /** Amount scrolled in Creative mode inventory (0 = top, 1 = bottom) */
    private float currentScroll = 0.0F;

    /** True if the scrollbar is being dragged */
    private boolean isScrolling = false;

    /**
     * True if the left mouse button was held down last time drawScreen was called.
     */
    private boolean wasClicking;
    private GuiTextField searchField;
    private List creativeInventory;
    private Slot slotDestroy = null;
    private boolean resetSearchField = false;

    public GuiContainerCreative(EntityPlayer par1EntityPlayer)
    {
        super(new ContainerCreative(par1EntityPlayer));
        par1EntityPlayer.craftingInventory = this.inventorySlots;
        this.allowUserInput = true;
        par1EntityPlayer.addStat(AchievementList.openInventory, 1);
        this.ySize = 136;
        this.xSize = 195;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (!this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }

	protected void handleMouseClick(Slot slotClicked, int clientX, int clientY, boolean mouseButton)
	{
		this.resetSearchField = true;
		ItemStack playerItemStack;
		InventoryPlayer playerInventory;

		if (slotClicked != null)
		{
			int i;

			if (slotClicked == this.slotDestroy && mouseButton)
			{
				//for (i = 0; i < this.mc.thePlayer.inventorySlots.getInventory().size() - 4; ++i)
				for (i = 0; i < this.mc.thePlayer.inventorySlots.getInventory().size(); ++i)
				{
					this.mc.playerController.sendSlotPacket((ItemStack)null, i);
				}
			}
			else
			{
				ItemStack tempSlot;

				if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex())
				{
					if (slotClicked == this.slotDestroy)
					{
						this.mc.thePlayer.inventory.setItemStack((ItemStack)null);
					}
					else
					{
						i = SlotCreativeInventory.func_75240_a((SlotCreativeInventory)slotClicked).slotNumber;
						playerItemStack = this.mc.thePlayer.inventorySlots.getSlot(i).getStack();

						if (mouseButton && (playerItemStack == null || !(playerItemStack.getItem() instanceof ItemArmor) || !(playerItemStack.getItem() instanceof ItemTrinket)))
						{
							this.mc.playerController.sendSlotPacket((ItemStack)null, i);
						}
						else
						{
							this.mc.thePlayer.inventorySlots.slotClick(i, clientY, mouseButton, this.mc.thePlayer);
							tempSlot = this.mc.thePlayer.inventorySlots.getSlot(i).getStack();
							this.mc.playerController.sendSlotPacket(tempSlot, i);
						}
					}
				}
				else if (slotClicked.inventory == inventory)
				{
					playerInventory = this.mc.thePlayer.inventory;
					playerItemStack = playerInventory.getItemStack();
					tempSlot = slotClicked.getStack();

					if (playerItemStack != null && tempSlot != null && playerItemStack.isItemEqual(tempSlot))
					{
						if (clientY == 0)
						{
							if (mouseButton)
							{
								playerItemStack.stackSize = playerItemStack.getMaxStackSize();
							}
							else if (playerItemStack.stackSize < playerItemStack.getMaxStackSize())
							{
								++playerItemStack.stackSize;
							}
						}
						else if (playerItemStack.stackSize <= 1)
						{
							playerInventory.setItemStack((ItemStack)null);
						}
						else
						{
							--playerItemStack.stackSize;
						}
					}
					else if (tempSlot != null && playerItemStack == null)
					{
						boolean var8 = false;

						if (!var8)
						{
							playerInventory.setItemStack(ItemStack.copyItemStack(tempSlot));
							playerItemStack = playerInventory.getItemStack();

							if (mouseButton)
							{
								playerItemStack.stackSize = playerItemStack.getMaxStackSize();
							}
						}
					}
					else
					{
						playerInventory.setItemStack((ItemStack)null);
					}
				}
				else
				{
					this.inventorySlots.slotClick(slotClicked.slotNumber, clientY, mouseButton, this.mc.thePlayer);
					ItemStack var10 = this.inventorySlots.getSlot(slotClicked.slotNumber).getStack();
					//this.mc.playerController.sendSlotPacket(var10, slotClicked.slotNumber - (this.inventorySlots.inventorySlots.size() - 4) + 9 + 36);
					this.mc.playerController.sendSlotPacket(var10, slotClicked.slotNumber - this.inventorySlots.inventorySlots.size() + 4 + 9 + 36);
				}
			}
		}
		else
		{
			playerInventory = this.mc.thePlayer.inventory;

			if (playerInventory.getItemStack() != null)
			{
				if (clientY == 0)
				{
					this.mc.thePlayer.dropPlayerItem(playerInventory.getItemStack());
					this.mc.playerController.func_78752_a(playerInventory.getItemStack());
					playerInventory.setItemStack((ItemStack)null);
				}

				if (clientY == 1)
				{
					playerItemStack = playerInventory.getItemStack().splitStack(1);
					this.mc.thePlayer.dropPlayerItem(playerItemStack);
					this.mc.playerController.func_78752_a(playerItemStack);

					if (playerInventory.getItemStack().stackSize == 0)
					{
						playerInventory.setItemStack((ItemStack)null);
					}
				}
			}
		}
	}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        if (this.mc.playerController.isInCreativeMode())
        {
            super.initGui();
            this.controlList.clear();
            Keyboard.enableRepeatEvents(true);
            this.searchField = new GuiTextField(this.fontRenderer, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRenderer.FONT_HEIGHT);
            this.searchField.setMaxStringLength(15);
            this.searchField.setEnableBackgroundDrawing(false);
            this.searchField.setVisible(false);
            this.searchField.setTextColor(16777215);
            int var1 = selectedTabIndex;
            selectedTabIndex = -1;
            this.drawTab(CreativeTabs.creativeTabArray[var1]);
        }
        else
        {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex())
        {
            if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindChat.keyCode))
            {
                this.drawTab(CreativeTabs.tabAllSearch);
            }
            else
            {
                super.keyTyped(par1, par2);
            }
        }
        else
        {
            if (this.resetSearchField)
            {
                this.resetSearchField = false;
                this.searchField.setText("");
            }

            if (this.searchField.textboxKeyTyped(par1, par2))
            {
                this.func_74228_j();
            }
            else
            {
                super.keyTyped(par1, par2);
            }
        }
    }

    private void func_74228_j()
    {
        ContainerCreative var1 = (ContainerCreative)this.inventorySlots;
        var1.itemList.clear();
        Item[] var2 = Item.itemsList;
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            Item var5 = var2[var4];

            if (var5 != null && var5.getCreativeTab() != null)
            {
                var5.getSubItems(var5.shiftedIndex, (CreativeTabs)null, var1.itemList);
            }
        }

        Iterator var8 = var1.itemList.iterator();
        String var9 = this.searchField.getText().toLowerCase();

        while (var8.hasNext())
        {
            ItemStack var10 = (ItemStack)var8.next();
            boolean var11 = false;
            Iterator var6 = var10.getItemNameandInformation().iterator();

            while (true)
            {
                if (var6.hasNext())
                {
                    String var7 = (String)var6.next();

                    if (!var7.toLowerCase().contains(var9))
                    {
                        continue;
                    }

                    var11 = true;
                }

                if (!var11)
                {
                    var8.remove();
                }

                break;
            }
        }

        this.currentScroll = 0.0F;
        var1.scrollTo(0.0F);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        CreativeTabs var1 = CreativeTabs.creativeTabArray[selectedTabIndex];

        if (var1.drawInForegroundOfTab())
        {
            this.fontRenderer.drawString(var1.getTranslatedTabLabel(), 8, 6, 4210752);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            int var4 = par1 - this.guiLeft;
            int var5 = par2 - this.guiTop;
            CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
            int var7 = var6.length;

            for (int var8 = 0; var8 < var7; ++var8)
            {
                CreativeTabs var9 = var6[var8];

                if (this.func_74232_a(var9, var4, var5))
                {
                    this.drawTab(var9);
                    return;
                }
            }
        }

        super.mouseClicked(par1, par2, par3);
    }

    /**
     * returns (if you are not on the inventoryTab) and (the flag isn't set) and( you have more than 1 page of items)
     */
    private boolean needsScrollBars()
    {
        return selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative)this.inventorySlots).hasMoreThan1PageOfItemsInList();
    }

    private void drawTab(CreativeTabs par1CreativeTabs)
    {
		int tabIndex = selectedTabIndex;
        selectedTabIndex = par1CreativeTabs.getTabIndex();
        ContainerCreative containerCreative = (ContainerCreative)this.inventorySlots;
        containerCreative.itemList.clear();
        par1CreativeTabs.displayAllReleventItems(containerCreative.itemList);

        if (par1CreativeTabs == CreativeTabs.tabInventory)
        {
            Container playerInventory = this.mc.thePlayer.inventorySlots;

            if (this.creativeInventory == null)
            {
                this.creativeInventory = containerCreative.inventorySlots;
            }

            containerCreative.inventorySlots = new ArrayList();

            for (int i = 0; i < playerInventory.inventorySlots.size(); ++i)
            {
                SlotCreativeInventory creativeSlot = new SlotCreativeInventory(this, (Slot)playerInventory.inventorySlots.get(i), i);
                containerCreative.inventorySlots.add(creativeSlot);
                int posX;
                int offsetX;
                int offsetY;

				// armor slots
                if (i >= 5 && i < 9)
                {
                    posX = i - 5;
                    creativeSlot.xDisplayPosition = 45 + (posX * 18);
                    creativeSlot.yDisplayPosition = 9;
                }
				// trinket slots
				else if (i >= 9 && i < 13)
                {
                    posX = i - 9;
                    creativeSlot.xDisplayPosition = 45 + (posX * 18);
                    creativeSlot.yDisplayPosition = 30;
                }
				// crafting matrix
                else if (i >= 0 && i < 5)
                {
                    creativeSlot.yDisplayPosition = -2000;
                    creativeSlot.xDisplayPosition = -2000;
                }
				// player inventory
                else if (i < playerInventory.inventorySlots.size())
                {
                    posX = (i - 4) - 9;
                    offsetX = posX % 9;
                    offsetY = posX / 9;
                    creativeSlot.xDisplayPosition = 9 + offsetX * 18;

                    if ((i - 4) >= 36)
                    {
                        creativeSlot.yDisplayPosition = 112; // action bar
                    }
                    else
                    {
                        creativeSlot.yDisplayPosition = 54 + offsetY * 18; // inventory
                    }
                }
            }

            this.slotDestroy = new Slot(inventory, 0, 173, 112);
            containerCreative.inventorySlots.add(this.slotDestroy);
        }
        else if (tabIndex == CreativeTabs.tabInventory.getTabIndex())
        {
            containerCreative.inventorySlots = this.creativeInventory;
            this.creativeInventory = null;
        }

        if (this.searchField != null)
        {
            if (par1CreativeTabs == CreativeTabs.tabAllSearch)
            {
                this.searchField.setVisible(true);
                this.searchField.setCanLoseFocus(false);
                this.searchField.setFocused(true);
                this.searchField.setText("");
                this.func_74228_j();
            }
            else
            {
                this.searchField.setVisible(false);
                this.searchField.setCanLoseFocus(true);
                this.searchField.setFocused(false);
            }
        }

        this.currentScroll = 0.0F;
        containerCreative.scrollTo(0.0F);
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if (var1 != 0 && this.needsScrollBars())
        {
            int var2 = ((ContainerCreative)this.inventorySlots).itemList.size() / 9 - 5 + 1;

            if (var1 > 0)
            {
                var1 = 1;
            }

            if (var1 < 0)
            {
                var1 = -1;
            }

            this.currentScroll = (float)((double)this.currentScroll - (double)var1 / (double)var2);

            if (this.currentScroll < 0.0F)
            {
                this.currentScroll = 0.0F;
            }

            if (this.currentScroll > 1.0F)
            {
                this.currentScroll = 1.0F;
            }

            ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        boolean var4 = Mouse.isButtonDown(0);
        int var5 = this.guiLeft;
        int var6 = this.guiTop;
        int var7 = var5 + 175;
        int var8 = var6 + 18;
        int var9 = var7 + 14;
        int var10 = var8 + 112;

        if (!this.wasClicking && var4 && par1 >= var7 && par2 >= var8 && par1 < var9 && par2 < var10)
        {
            this.isScrolling = this.needsScrollBars();
        }

        if (!var4)
        {
            this.isScrolling = false;
        }

        this.wasClicking = var4;

        if (this.isScrolling)
        {
            this.currentScroll = ((float)(par2 - var8) - 7.5F) / ((float)(var10 - var8) - 15.0F);

            if (this.currentScroll < 0.0F)
            {
                this.currentScroll = 0.0F;
            }

            if (this.currentScroll > 1.0F)
            {
                this.currentScroll = 1.0F;
            }

            ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }

        super.drawScreen(par1, par2, par3);
        CreativeTabs[] var11 = CreativeTabs.creativeTabArray;
        int var12 = var11.length;

        for (int var13 = 0; var13 < var12; ++var13)
        {
            CreativeTabs var14 = var11[var13];

            if (this.renderCreativeInventoryHoveringText(var14, par1, par2))
            {
                break;
            }
        }

        if (this.slotDestroy != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && this.func_74188_c(this.slotDestroy.xDisplayPosition, this.slotDestroy.yDisplayPosition, 16, 16, par1, par2))
        {
            this.drawCreativeTabHoveringText(StringTranslate.getInstance().translateKey("inventory.binSlot"), par1, par2);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderHelper.enableGUIStandardItemLighting();
        int var4 = this.mc.renderEngine.getTexture("/gui/allitems.png");
        CreativeTabs var5 = CreativeTabs.creativeTabArray[selectedTabIndex];
        int var6 = this.mc.renderEngine.getTexture("/gui/creative_inv/" + var5.getBackgroundImageName());
        CreativeTabs[] var7 = CreativeTabs.creativeTabArray;
        int var8 = var7.length;
        int var9;

        for (var9 = 0; var9 < var8; ++var9)
        {
            CreativeTabs var10 = var7[var9];
            this.mc.renderEngine.bindTexture(var4);

            if (var10.getTabIndex() != selectedTabIndex)
            {
                this.renderCreativeTab(var10);
            }
        }

        this.mc.renderEngine.bindTexture(var6);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.searchField.drawTextBox();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var11 = this.guiLeft + 175;
        var8 = this.guiTop + 18;
        var9 = var8 + 112;
        this.mc.renderEngine.bindTexture(var4);

        if (var5.shouldHidePlayerInventory())
        {
            this.drawTexturedModalRect(var11, var8 + (int)((float)(var9 - var8 - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
        }

        this.renderCreativeTab(var5);

        if (var5 == CreativeTabs.tabInventory)
        {
            GuiInventory.func_74223_a(this.mc, this.guiLeft + 25, this.guiTop + 47, 20, (float)(this.guiLeft + 25 - par2), (float)(this.guiTop + 45 - 30 - par3));
        }
    }

    protected boolean func_74232_a(CreativeTabs par1CreativeTabs, int par2, int par3)
    {
        int var4 = par1CreativeTabs.getTabColumn();
        int var5 = 28 * var4;
        byte var6 = 0;

        if (var4 == 5)
        {
            var5 = this.xSize - 28 + 2;
        }
        else if (var4 > 0)
        {
            var5 += var4;
        }

        int var7;

        if (par1CreativeTabs.isTabInFirstRow())
        {
            var7 = var6 - 32;
        }
        else
        {
            var7 = var6 + this.ySize;
        }

        return par2 >= var5 && par2 <= var5 + 28 && par3 >= var7 && par3 <= var7 + 32;
    }

    /**
     * Renders the creative inventory hovering text if mouse is over it. Returns true if did render or false otherwise.
     * Params: current creative tab to be checked, current mouse x position, current mouse y position.
     */
    protected boolean renderCreativeInventoryHoveringText(CreativeTabs par1CreativeTabs, int par2, int par3)
    {
        int var4 = par1CreativeTabs.getTabColumn();
        int var5 = 28 * var4;
        byte var6 = 0;

        if (var4 == 5)
        {
            var5 = this.xSize - 28 + 2;
        }
        else if (var4 > 0)
        {
            var5 += var4;
        }

        int var7;

        if (par1CreativeTabs.isTabInFirstRow())
        {
            var7 = var6 - 32;
        }
        else
        {
            var7 = var6 + this.ySize;
        }

        if (this.func_74188_c(var5 + 3, var7 + 3, 23, 27, par2, par3))
        {
            this.drawCreativeTabHoveringText(par1CreativeTabs.getTranslatedTabLabel(), par2, par3);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Renders passed creative inventory tab into the screen.
     */
    protected void renderCreativeTab(CreativeTabs par1CreativeTabs)
    {
        boolean var2 = par1CreativeTabs.getTabIndex() == selectedTabIndex;
        boolean var3 = par1CreativeTabs.isTabInFirstRow();
        int var4 = par1CreativeTabs.getTabColumn();
        int var5 = var4 * 28;
        int var6 = 0;
        int var7 = this.guiLeft + 28 * var4;
        int var8 = this.guiTop;
        byte var9 = 32;

        if (var2)
        {
            var6 += 32;
        }

        if (var4 == 5)
        {
            var7 = this.guiLeft + this.xSize - 28;
        }
        else if (var4 > 0)
        {
            var7 += var4;
        }

        if (var3)
        {
            var8 -= 28;
        }
        else
        {
            var6 += 64;
            var8 += this.ySize - 4;
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
        this.zLevel = 100.0F;
        itemRenderer.zLevel = 100.0F;
        var7 += 6;
        var8 += 8 + (var3 ? 1 : -1);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        ItemStack var10 = new ItemStack(par1CreativeTabs.getTabIconItem());
        itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var7, var8);
        itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var7, var8);
        GL11.glDisable(GL11.GL_LIGHTING);
        itemRenderer.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
        }

        if (par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
        }
    }

    public int func_74230_h()
    {
        return selectedTabIndex;
    }

    /**
     * Returns the creative inventory
     */
    static InventoryBasic getInventory()
    {
        return inventory;
    }
}
