package net.minecraft.src;

import java.util.List;

public class ItemTrinket extends Item
{
    public ItemTrinket(int par1)
    {
        super(par1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setTabToDisplayOn(CreativeTabs.tabMisc);
    }
	
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.rare;
	}
	
	public boolean hasEffect(ItemStack par1ItemStack)
    {
        return false;
    }
	
	public void addInformation(ItemStack par1ItemStack, List par2List)
    {
        par2List.add("Trinket");
    }
}
