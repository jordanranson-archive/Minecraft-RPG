package net.minecraft.src;

public enum EnumMagicEffect
{
    flame(1),
    freeze(2),
    lifesteal(3),
    flee(4),
    lightning(5);
	
	private final int effectId;
	
	private EnumMagicEffect(int par1)
	{
		this.effectId = par1;
	}
	
	public int getEffectId()
	{
		return this.effectId;
	}
}