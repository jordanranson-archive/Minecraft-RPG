package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableType3 implements Callable
{
    /** Gets Intergated Server type. */
    final IntegratedServer minecraftServerType3;

    CallableType3(IntegratedServer par1IntegratedServer)
    {
        this.minecraftServerType3 = par1IntegratedServer;
    }

    public String getType()
    {
        return "Integrated Server";
    }

    public Object call()
    {
        return this.getType();
    }
}
