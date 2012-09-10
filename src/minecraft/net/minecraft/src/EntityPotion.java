package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityThrowable
{
    /**
     * The damage value of the thrown potion that this EntityPotion represents.
     */
    private int potionDamage;

    public EntityPotion(World par1World)
    {
        super(par1World);
    }

    public EntityPotion(World par1World, EntityLiving par2EntityLiving, int par3)
    {
        super(par1World, par2EntityLiving);
        this.potionDamage = par3;
    }

    public EntityPotion(World par1World, double par2, double par4, double par6, int par8)
    {
        super(par1World, par2, par4, par6);
        this.potionDamage = par8;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.05F;
    }

    protected float func_70182_d()
    {
        return 0.5F;
    }

    protected float func_70183_g()
    {
        return -20.0F;
    }

    /**
     * Returns the damage value of the thrown potion that this EntityPotion represents.
     */
    public int getPotionDamage()
    {
        return this.potionDamage;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition movingObjPos)
    {	
        if (!this.worldObj.isRemote)
        {
            List potionEffects = Item.potion.getEffects(this.potionDamage);

            if (potionEffects != null && !potionEffects.isEmpty())
            {
                AxisAlignedBB boundingBox = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
                List collisions = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, boundingBox);

                if (collisions != null && !collisions.isEmpty())
                {
                    Iterator icollisions = collisions.iterator();

                    while (icollisions.hasNext())
                    {
                        EntityLiving entityLiving = (EntityLiving)icollisions.next();
                        double distanceToEntity = this.getDistanceSqToEntity(entityLiving);

                        if (distanceToEntity < 16.0D)
                        {
                            double adjustedDistance = 1.0D - Math.sqrt(distanceToEntity) / 4.0D;

                            if (entityLiving == movingObjPos.entityHit)
                            {
                                adjustedDistance = 1.0D;
                            }

                            Iterator iPotionEffects = potionEffects.iterator();

                            while (iPotionEffects.hasNext())
                            {
                                PotionEffect potionEffect = (PotionEffect)iPotionEffects.next();
                                int potionId = potionEffect.getPotionID();

                                if (Potion.potionTypes[potionId].isInstant())
                                {
                                    Potion.potionTypes[potionId].affectEntity(this.thrower, entityLiving, potionEffect.getAmplifier(), adjustedDistance);
                                }
                                else
                                {
                                    int effectDuration = (int)(adjustedDistance * (double)potionEffect.getDuration() + 0.5D);

                                    if (effectDuration > 20)
                                    {
                                        entityLiving.addPotionEffect(new PotionEffect(potionId, effectDuration, potionEffect.getAmplifier()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), this.potionDamage);
            this.setDead();
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.potionDamage = par1NBTTagCompound.getInteger("potionValue");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("potionValue", this.potionDamage);
    }
}
