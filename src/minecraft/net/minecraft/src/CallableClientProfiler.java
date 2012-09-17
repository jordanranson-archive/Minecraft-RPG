package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;

public class CallableClientProfiler implements Callable
{
    /** Gets skin of Minecraft player. */
    final Minecraft minecraftClientProfiler;

    public CallableClientProfiler(Minecraft par1Minecraft)
    {
        this.minecraftClientProfiler = par1Minecraft;
    }

    /**
     * Gets if Client Profiler (aka Snooper) is enabled.
     */
    public String getClientProfilerEnabled()
    {
        return this.minecraftClientProfiler.mcProfiler.profilingEnabled ? this.minecraftClientProfiler.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
    }

    public Object call()
    {
        return this.getClientProfilerEnabled();
    }
}
