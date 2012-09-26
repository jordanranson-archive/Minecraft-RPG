package net.minecraft.src;

import java.util.Random;

public class BiomeGenDesert extends BiomeGenBase
{
    public BiomeGenDesert(int par1)
    {
        super(par1);
        this.spawnableCreatureList.clear();
        this.topBlock = (byte)Block.sand.blockID;
        this.fillerBlock = (byte)Block.sand.blockID;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        this.theBiomeDecorator.cactiPerChunk = 10;
		
		// Minecraft RPG
		this.spawnableMonsterList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityCaveSpider.class, 6, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 4, 1, 2));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 6, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 6, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 1, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieFast.class, 10, 2, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieSpitter.class, 8, 2, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityMummy.class, 10, 2, 3));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityScarab.class, 4, 1, 2));
    }

    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        super.decorate(par1World, par2Random, par3, par4);

        if (par2Random.nextInt(1000) == 0)
        {
            int var5 = par3 + par2Random.nextInt(16) + 8;
            int var6 = par4 + par2Random.nextInt(16) + 8;
            WorldGenDesertWells var7 = new WorldGenDesertWells();
            var7.generate(par1World, par2Random, var5, par1World.getHeightValue(var5, var6) + 1, var6);
        }
		else 
		{
			if(par2Random.nextInt(17) == 0)
			{
				int x = 0;
				int z = 0;
				int y = 0;
				int offset = 0;
				int blockReplaced = 0;
				
				// quicksand gen
				for(int i = 0; i < par2Random.nextInt(16) + 8; i++)
				{
					x = par3 + par2Random.nextInt(11) - 5;
					z = par4 + par2Random.nextInt(11) - 5;
					y = par1World.getHeightValue(x, z) - 1;
					
					if(y < 71)
					{
						for(int k = 0; k < par2Random.nextInt(1) + 2; k++)
						{
							for(int j = 0; j < par2Random.nextInt(1) + 2; j++) 
							{
								blockReplaced = par1World.getBlockId(x, y, z);
								if
								(
									blockReplaced != Block.waterMoving.blockID &&
									blockReplaced != Block.waterStill.blockID &&
									blockReplaced != Block.lavaMoving.blockID &&
									blockReplaced != Block.lavaStill.blockID &&
									blockReplaced != 0
								)
								{
									for(int m = 0; m < par2Random.nextInt(3) + 2; m++) 
									{
										par1World.setBlock(x + k, y - m, z + j, Block.quicksand.blockID);
									}
								}
							}
						}
					}
				}
			}
			
			if(par2Random.nextInt(13) == 0)
			{
				int x = 0;
				int z = 0;
				int y = 0;
				int offset = 0;
				int blockReplaced = 0;
				
				// scarab block gen
				for(int i = 0; i < par2Random.nextInt(6) + 3; i++)
				{
					x = par3 + par2Random.nextInt(11) - 5;
					z = par4 + par2Random.nextInt(11) - 5;
					y = par1World.getHeightValue(x, z) - 1;
					
					if(y < 71)
					{
						for(int k = 0; k < par2Random.nextInt(1) + 1; k++)
						{
							for(int j = 0; j < par2Random.nextInt(1) + 1; j++) 
							{
								blockReplaced = par1World.getBlockId(x, y, z);
								if
								(
									blockReplaced != Block.waterMoving.blockID &&
									blockReplaced != Block.waterStill.blockID &&
									blockReplaced != Block.lavaMoving.blockID &&
									blockReplaced != Block.lavaStill.blockID &&
									blockReplaced != 0
								)
								{
									par1World.setBlock(x + k, y, z + j, Block.scarab.blockID);
									par1World.setBlockMetadata(x + k, y, z + j, 1);
								}
							}
						}
					}
				}
			}
		}
    }
}
