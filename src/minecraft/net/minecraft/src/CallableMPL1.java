package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMPL1 implements Callable
{
    /** Initialises the WorldClient for CallableMPL1. */
    final WorldClient worldClientMPL1;

    CallableMPL1(WorldClient par1WorldClient)
    {
        this.worldClientMPL1 = par1WorldClient;
    }

    /**
     * Returns the size and contents of the entity list.
     */
    public String getEntityCountAndList()
    {
        return WorldClient.getEntityList(this.worldClientMPL1).size() + " total; " + WorldClient.getEntityList(this.worldClientMPL1).toString();
    }

    public Object call()
    {
        return this.getEntityCountAndList();
    }
}
