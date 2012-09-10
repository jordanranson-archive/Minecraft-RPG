package net.minecraft.src;

public enum EnumMagicRodMaterial
{
    ruby(1),
    sapphire(2),
    amethyst(3),
    emerald(4),
    topaz(5);
	
	private final int materialId;
	
	private EnumMagicRodMaterial(int par1)
	{
		this.materialId = par1;
	}
	
	public int getMaterialId()
	{
		return this.materialId;
	}
}