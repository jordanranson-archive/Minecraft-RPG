package net.minecraft.src;

import java.util.Random;

public class ItemCrossbow extends Item
{
    public ItemCrossbow(int par1)
    {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setTabToDisplayOn(CreativeTabs.tabCombat);
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {

    }

    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        boolean canUseWithoutArrows = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;
		float velocity = 0.9F;
		
        if (canUseWithoutArrows || player.inventory.hasItem(Item.arrow.shiftedIndex))
        {
            EntityArrow arrow = new EntityArrow(world, player, velocity);
			arrow.setDamage(arrow.getDamage() / 1.3F);

            int enchantPower = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
			
            if (enchantPower > 0)
            {
                arrow.setDamage(arrow.getDamage() + (double)enchantPower * 0.5D + 0.5D);
            }

            int enchantPunch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);

            if (enchantPunch > 0)
            {
                arrow.setKnockbackStrength(enchantPunch);
            }

            if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0)
			{
				arrow.setMagicEffect(EnumMagicEffect.flame);
			}
			else if(EnchantmentHelper.getEnchantmentLevel(Enchantment.frozen.effectId, itemStack) > 0) 
			{
				arrow.setMagicEffect(EnumMagicEffect.freeze);
			}
			else if(EnchantmentHelper.getEnchantmentLevel(Enchantment.lifesteal.effectId, itemStack) > 0)
			{
				Random random = new Random();
				if(random.nextBoolean()) 
				{
					if(random.nextBoolean()) 
					{
						arrow.setEffectMultiplier(1.4D);
					}
					else
					{
						arrow.setEffectMultiplier(0.0D);
					}
				}
				else
				{
					arrow.setEffectMultiplier(0.0D);
				}
				arrow.setMagicEffect(EnumMagicEffect.lifesteal);
			}
			else if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flee.effectId, itemStack) > 0)
			{
				arrow.setMagicEffect(EnumMagicEffect.flee);
			}

            itemStack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + velocity * 0.5F);

            if (canUseWithoutArrows)
            {
                arrow.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Item.arrow.shiftedIndex);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(arrow);
            }
        }

        return itemStack;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 1;
    }
}
