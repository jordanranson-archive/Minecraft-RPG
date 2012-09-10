package net.minecraft.src;

public class RecipesMinecraftRPG
{
    /**
     * Adds the crafting recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        par1CraftingManager.addRecipe(new ItemStack(Item.crossbow), new Object[] {"B", "I", "S", 'B', Item.bow, 'I', Item.ingotIron, 'S', Item.stick});
		
        par1CraftingManager.addRecipe(new ItemStack(Block.blockRuby), new Object[] {"###", "###", "###", '#', Item.ruby});
        par1CraftingManager.addRecipe(new ItemStack(Block.blockSapphire), new Object[] {"###", "###", "###", '#', Item.sapphire});
        par1CraftingManager.addRecipe(new ItemStack(Block.blockAmethyst), new Object[] {"###", "###", "###", '#', Item.amethyst});
				
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.ruby, 9), new Object[] {Block.blockRuby});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.sapphire, 9), new Object[] {Block.blockSapphire});
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.amethyst, 9), new Object[] {Block.blockAmethyst});
		
		par1CraftingManager.addRecipe(new ItemStack(Item.rubyCharged), new Object[] {" P ", "P#P", " P ", '#', Block.blockRuby, 'P', Item.powderMagic});
		par1CraftingManager.addRecipe(new ItemStack(Item.sapphireCharged), new Object[] {" P ", "P#P", " P ", '#', Block.blockSapphire, 'P', Item.powderMagic});
		par1CraftingManager.addRecipe(new ItemStack(Item.amethystCharged), new Object[] {" P ", "P#P", " P ", '#', Block.blockAmethyst, 'P', Item.powderMagic});
		par1CraftingManager.addRecipe(new ItemStack(Item.emeraldCharged), new Object[] {" P ", "P#P", " P ", '#', Block.blockEmerald, 'P', Item.powderMagic});
		
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.powderMagic, 2), new Object[] {Item.powderFragrant, Item.powderSparkling});
		
        par1CraftingManager.addRecipe(new ItemStack(Block.prospectingTable), new Object[] {"CGC", "###", 'C', Block.cloth, 'G', Block.glass, '#', Block.cobblestone});
		
		par1CraftingManager.addRecipe(new ItemStack(Item.rodRuby), new Object[] {"#", "G", "S", '#', Item.rubyCharged, 'G', Item.ingotGold, 'S', Item.stick});
		par1CraftingManager.addRecipe(new ItemStack(Item.rodSapphire), new Object[] {"#", "G", "S", '#', Item.sapphireCharged, 'G', Item.ingotGold, 'S', Item.stick});
		par1CraftingManager.addRecipe(new ItemStack(Item.rodAmethyst), new Object[] {"#", "G", "S", '#', Item.amethystCharged, 'G', Item.ingotGold, 'S', Item.stick});
		par1CraftingManager.addRecipe(new ItemStack(Item.rodEmerald), new Object[] {"#", "G", "S", '#', Item.emeraldCharged, 'G', Item.ingotGold, 'S', Item.stick});
		
		par1CraftingManager.addShapelessRecipe(new ItemStack(Item.runicDiamond, 1), new Object[] {Item.diamond, Item.rubyCharged, Item.sapphireCharged, Item.amethystCharged, Item.emeraldCharged});
    }
}
