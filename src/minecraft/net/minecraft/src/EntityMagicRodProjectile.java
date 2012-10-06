package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityMagicRodProjectile extends EntityThrowable
{
	private int magicEffectId = -1;
	private double effectMultiplier = 1.0D;
	private EntityLiving shootingEntity;
	
    public EntityMagicRodProjectile(World par1World)
    {
        super(par1World);
    }

    public EntityMagicRodProjectile(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public EntityMagicRodProjectile(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
	
	protected void entityInit()
    {
        this.dataWatcher.addObject(15, Byte.valueOf((byte)0));
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
        {
			if(par1MovingObjectPosition.entityHit instanceof EntityLiving)
			{
				EntityLiving entityHit = (EntityLiving)par1MovingObjectPosition.entityHit;
				
				// Flame
				if (this.getMagicEffectId() == EnumMagicEffect.flame.getEffectId())
				{
					entityHit.setFire(5 * (int)this.effectMultiplier);
				}
				
				// Freeze
				else if (this.getMagicEffectId() == EnumMagicEffect.freeze.getEffectId() && par1MovingObjectPosition.entityHit instanceof EntityLiving)
				{
					entityHit.addPotionEffect(new PotionEffect(30, 71 * (int)this.effectMultiplier, 0));
				}
				
				// Life steal
				else if (this.getMagicEffectId() == EnumMagicEffect.lifesteal.getEffectId() && par1MovingObjectPosition.entityHit instanceof EntityLiving)
				{
					entityHit.attackEntityFrom(DamageSource.magic, 1);
					
					EntityLiving player = (EntityLiving)this.shootingEntity;
					int healAmount = (int)Math.floor(0.75D * this.effectMultiplier);
					player.heal(healAmount >= 1 ? healAmount : 1);
				}
				
				// Flee
				else if (this.getMagicEffectId() == EnumMagicEffect.flee.getEffectId() && par1MovingObjectPosition.entityHit instanceof EntityLiving)
				{
					entityHit.addPotionEffect(new PotionEffect(31, 41 * (int)this.effectMultiplier, 0));
				}
			}
			
			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), 2);
        }
		
        if (!this.worldObj.isRemote)
        {
			this.worldObj.playAuxSFX(9999, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), this.getMagicEffectId());
            this.setDead();
        }
    }
	
	public void onUpdate()
	{
		if (this.getMagicEffectId() == EnumMagicEffect.freeze.getEffectId())
		{
			for (int var21 = 0; var21 < 4; ++var21)
			{
				if(var21 == 3)
				{
					this.worldObj.spawnParticle("snowballpoof", this.posX + this.motionX * (double)var21 / 4.0D, this.posY + this.motionY * (double)var21 / 4.0D, this.posZ + this.motionZ * (double)var21 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
				else 
				{
					this.worldObj.spawnParticle("frozenenchant", this.posX + this.motionX * (double)var21 / 4.0D, this.posY + this.motionY * (double)var21 / 4.0D, this.posZ + this.motionZ * (double)var21 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}
		}
		else if (this.getMagicEffectId() == EnumMagicEffect.flame.getEffectId())
		{
			for (int var21 = 0; var21 < 4; ++var21)
			{
				if(var21 == 3)
				{
					this.worldObj.spawnParticle("slag", this.posX + this.motionX * (double)var21 / 4.0D, this.posY + this.motionY * (double)var21 / 4.0D, this.posZ + this.motionZ * (double)var21 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
				else 
				{
					this.worldObj.spawnParticle("flameenchant", this.posX + this.motionX * (double)var21 / 4.0D, this.posY + this.motionY * (double)var21 / 4.0D, this.posZ + this.motionZ * (double)var21 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}
		}
		else if (this.getMagicEffectId() == EnumMagicEffect.lifesteal.getEffectId())
		{
			for (int var21 = 0; var21 < 4; ++var21)
			{
				this.worldObj.spawnParticle("lifesteal", this.posX + this.motionX * (double)var21 / 4.0D, this.posY + this.motionY * (double)var21 / 4.0D, this.posZ + this.motionZ * (double)var21 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
			}
		}
		else if (this.getMagicEffectId() == EnumMagicEffect.flee.getEffectId())
		{
			for (int var21 = 0; var21 < 4; ++var21)
			{
				this.worldObj.spawnParticle("flee", this.posX + this.motionX * (double)var21 / 4.0D, this.posY + this.motionY * (double)var21 / 4.0D, this.posZ + this.motionZ * (double)var21 / 4.0D, 0.0F, 0.0F, 0.0F);
			}
		}
		
		super.onUpdate();
	}
	
	public int getMagicEffectId()
	{
		this.magicEffectId = (int)this.dataWatcher.getWatchableObjectByte(15);
		return this.magicEffectId;
	}
	
	public void setMagicEffect(EnumMagicEffect effect)
	{
		this.magicEffectId = effect.getEffectId();
		this.dataWatcher.updateObject(15, Byte.valueOf((byte)this.magicEffectId));
	}
	
	public void setEffectMultiplier(double par1)
    {
        this.effectMultiplier = par1;
    }

    public double getEffectMultiplier()
    {
        return this.effectMultiplier;
    }
	
	public void setShootingEntity(EntityLiving par2EntityLiving)
    {
        this.shootingEntity = par2EntityLiving;
    }

    public EntityLiving getShootingEntity()
    {
        return this.shootingEntity;
    }
}