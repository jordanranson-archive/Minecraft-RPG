package net.minecraft.src;

public class EnchantmentArrow extends Enchantment
{
    public EnchantmentArrow(int par1, int par2, String par3)
    {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName(par3);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return 20;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }
}
