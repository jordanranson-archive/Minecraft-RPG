package net.minecraft.src;

public class EntityAIPanic extends EntityAIBase
{
    private EntityCreature theEntityCreature;
    private float speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;
	private boolean isMagicEffect = false;

    public EntityAIPanic(EntityCreature par1EntityCreature, float par2)
    {
        this.theEntityCreature = par1EntityCreature;
        this.speed = par2;
        this.setMutexBits(1);
    }
	
	public EntityAIPanic(EntityCreature par1EntityCreature, float par2, boolean magicEffect)
    {
        this.theEntityCreature = par1EntityCreature;
        this.speed = par2;
        this.setMutexBits(1);
		this.isMagicEffect = magicEffect;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.theEntityCreature.getAITarget() == null)
        {
            return false;
        }
        else
        {
            Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);

            if (var1 == null)
            {
                return false;
            }
            else
            {
				if(!this.isMagicEffect)
				{
					this.randPosX = var1.xCoord;
					this.randPosY = var1.yCoord;
					this.randPosZ = var1.zCoord;
					return true;
				}
				else
				{
					if(this.theEntityCreature.isPotionActive(Potion.flee))
					{
						this.randPosX = var1.xCoord;
						this.randPosY = var1.yCoord;
						this.randPosZ = var1.zCoord;
						return true;
					}
					else
					{
						return false;
					}
				}
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.theEntityCreature.getNavigator().noPath();
    }
}
