package net.minecraft.src;

import java.util.List;

public class ItemMagicRod extends Item
{
    public EnumMagicRodMaterial magicRodMaterial;

    public ItemMagicRod(int par1, EnumMagicRodMaterial par2)
    {
        super(par1);
        this.maxStackSize = 1;
		this.magicRodMaterial = par2;
        this.setMaxDamage(384);
        this.setTabToDisplayOn(CreativeTabs.tabCombat);
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
		int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
		float var7 = (float)var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
		
		if ((double)var7 < 0.05D) { return; }
		if (var7 > 0.8F){ var7 = 0.8F; }
		
		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
		par1ItemStack.damageItem(1, par3EntityPlayer);
		
		EntityMagicRodProjectile magicBolt = new EntityMagicRodProjectile(par2World, par3EntityPlayer);
		magicBolt.setShootingEntity(par3EntityPlayer);
		
		double multiplier = (double)(var7 * 2.4D);
		if(multiplier < 1.0D) { multiplier = 1.0D; }
		magicBolt.setEffectMultiplier(multiplier);

		switch(this.magicRodMaterial.getMaterialId())
		{
			case 1:
				magicBolt.setMagicEffect(EnumMagicEffect.flame);
				break;
			case 2:
				magicBolt.setMagicEffect(EnumMagicEffect.freeze);
				break;
			case 3:
				magicBolt.setMagicEffect(EnumMagicEffect.lifesteal);
				break;
			case 4:
				magicBolt.setMagicEffect(EnumMagicEffect.flee);
				break;
		}
		
		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(magicBolt);
		}
    }

    public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }
	
	public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
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
        return EnumAction.block;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
	
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.rare;
	}
	
	/**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, List par2List)
    {
		switch(this.magicRodMaterial.getMaterialId())
		{
			case 1:
				par2List.add("\u00a77" + Enchantment.flame.getTranslatedName(1));
				break;
			case 2:
				par2List.add("\u00a77" + Enchantment.frozen.getTranslatedName(1));
				break;
			case 3:
				par2List.add("\u00a77" + Enchantment.lifesteal.getTranslatedName(1));
				break;
			case 4:
				par2List.add("\u00a77" + Enchantment.flee.getTranslatedName(1));
				break;
		}
	}

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 0;
    }
}
