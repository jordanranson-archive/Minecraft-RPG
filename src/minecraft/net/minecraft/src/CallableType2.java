package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

public class CallableType2 implements Callable
{
    /** Gets Client Server type. */
    final Minecraft minecraftServerType2;

    public CallableType2(Minecraft par1Minecraft)
    {
        this.minecraftServerType2 = par1Minecraft;
    }

    public String getType()
    {
        return "Client";
    }

    public Object call()
    {
        return this.getType();
    }
}
