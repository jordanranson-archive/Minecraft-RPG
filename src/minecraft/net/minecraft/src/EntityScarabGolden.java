package net.minecraft.src;

import java.util.Random;

public class EntityScarabGolden extends EntityScarab
{
    private int allySummonCooldown;

    public EntityScarabGolden(World par1World)
    {
        super(par1World);
        this.texture = "/mob/scarab_golden.png";
        this.setSize(0.25F, 0.35F);
        this.moveSpeed = 1.0F;
        this.attackStrength = 2;
    }

    public int getMaxHealth()
    {
        return 6;
    }

    protected boolean canTriggerWalking()
    {
        return true;
    }

    protected int getDropItemId()
    {
		Random random = new Random();
		int next = random.nextInt(3);
		int itemId = 0;
		
		if(next == 0)
			itemId = Item.treasureSack.shiftedIndex;
		if(next == 1)
			itemId = Item.ingotGold.shiftedIndex;
		if(next == 2)
			itemId = Item.goldNugget.shiftedIndex;
			
		return itemId;
    }
	
	protected void updateEntityActionState()
    {
        super.updateEntityActionState();

        if (!this.worldObj.isRemote)
        {
            int var1;
            int var2;
            int var3;
            int var5;

            if (this.allySummonCooldown > 0)
            {
                --this.allySummonCooldown;

                if (this.allySummonCooldown == 0)
                {
                    var1 = MathHelper.floor_double(this.posX);
                    var2 = MathHelper.floor_double(this.posY);
                    var3 = MathHelper.floor_double(this.posZ);
                    boolean var4 = false;

                    for (var5 = 0; !var4 && var5 <= 5 && var5 >= -5; var5 = var5 <= 0 ? 1 - var5 : 0 - var5)
                    {
                        for (int var6 = 0; !var4 && var6 <= 10 && var6 >= -10; var6 = var6 <= 0 ? 1 - var6 : 0 - var6)
                        {
                            for (int var7 = 0; !var4 && var7 <= 10 && var7 >= -10; var7 = var7 <= 0 ? 1 - var7 : 0 - var7)
                            {
                                int var8 = this.worldObj.getBlockId(var1 + var6, var2 + var5, var3 + var7);

                                if (var8 == Block.scarab.blockID)
                                {
                                    this.worldObj.playAuxSFX(2001, var1 + var6, var2 + var5, var3 + var7, Block.scarab.blockID + (this.worldObj.getBlockMetadata(var1 + var6, var2 + var5, var3 + var7) << 12));
                                    //this.worldObj.setBlockWithNotify(var1 + var6, var2 + var5, var3 + var7, 0);
                                    Block.scarab.onBlockDestroyedByPlayer(this.worldObj, var1 + var6, var2 + var5, var3 + var7, 0);

                                    if (this.rand.nextBoolean())
                                    {
                                        var4 = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (this.entityToAttack == null && !this.hasPath())
            {
                var1 = MathHelper.floor_double(this.posX);
                var2 = MathHelper.floor_double(this.posY + 0.5D);
                var3 = MathHelper.floor_double(this.posZ);
                int var9 = this.rand.nextInt(6);
                var5 = this.worldObj.getBlockId(var1 + Facing.offsetsXForSide[var9], var2 + Facing.offsetsYForSide[var9], var3 + Facing.offsetsZForSide[var9]);

                if (BlockScarab.getPosingIdByMetadata(var5) && this.rand.nextInt(4) == 0)
                {
                    this.worldObj.setBlockAndMetadataWithNotify(var1 + Facing.offsetsXForSide[var9], var2 + Facing.offsetsYForSide[var9], var3 + Facing.offsetsZForSide[var9], Block.scarab.blockID, BlockScarab.getMetadataForBlockType(var5));
                    this.spawnExplosionParticle();
                    this.setDead();
                }
                else
                {
                    this.updateWanderPath();
                }
            }
            else if (this.entityToAttack != null && !this.hasPath())
            {
                this.entityToAttack = null;
            }
        }
    }
}
