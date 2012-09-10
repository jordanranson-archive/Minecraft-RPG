package net.minecraft.src;

import java.util.List;

public class ItemRunicDiamond extends Item
{
    public ItemRunicDiamond(int par1)
    {
        super(par1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setTabToDisplayOn(CreativeTabs.tabMaterials);
    }
	
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.epic;
	}
	
	public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }
}
