package net.minecraft.src;

public class EntityMummy extends EntityMob
{
    public EntityMummy(World par1World)
    {
        super(par1World);
        this.texture = "/mob/mummy.png";
        this.moveSpeed = 0.23F;
        this.attackStrength = 8;
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRestrictSun(this));
        this.tasks.addTask(2, new EntityAIFleeSun(this, this.moveSpeed));
        this.tasks.addTask(3, new EntityAIBreakDoor(this));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(6, new EntityAIMoveTwardsRestriction(this, this.moveSpeed / 2.0F));
        this.tasks.addTask(7, new EntityAIMoveThroughVillage(this, this.moveSpeed / 2.0F, false));
        this.tasks.addTask(8, new EntityAIWander(this, this.moveSpeed / 2.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 8.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 8.0F, 0, false));
    }

    public int getMaxHealth()
    {
        return 30;
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        return 2;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
        {
            float var1 = this.getBrightness(1.0F);

            if (var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F)
            {
                this.setFire(8);
            }
        }

        super.onLivingUpdate();
    }
	
	public boolean attackEntityAsMob(Entity par1Entity)
    {
        int var2 = this.attackStrength;

        if (this.isPotionActive(Potion.damageBoost))
        {
            var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (this.isPotionActive(Potion.weakness))
        {
            var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }
		
		float var25 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		float mult = 2.0F;
		
		par1Entity.addVelocity(this.motionX * mult * 0.6000000238418579D / (double)var25, 0.1D, this.motionZ * mult * 0.6000000238418579D / (double)var25);
				
		if(par1Entity instanceof EntityLiving)
		{
			EntityLiving entity = (EntityLiving)par1Entity;
			entity.addPotionEffect(new PotionEffect(2, 41, 0));
		}
		
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.zombie";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.zombiehurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.zombiedeath";
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.rottenFlesh.shiftedIndex;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected void dropRareDrop(int par1)
    {
        switch (this.rand.nextInt(4))
        {
            case 0:
                this.dropItem(Item.swordSteel.shiftedIndex, 1);
                break;

            case 1:
                this.dropItem(Item.helmetSteel.shiftedIndex, 1);
                break;

            case 2:
                this.dropItem(Item.ingotIron.shiftedIndex, 1);
                break;

            case 3:
                this.dropItem(Item.shovelSteel.shiftedIndex, 1);
        }
    }
}
