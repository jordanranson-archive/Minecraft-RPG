package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMinecraftVersion implements Callable
{
    /** Gets the Crash Rrport current Minecraft version. */
    final CrashReport crashReportMinecraftVersion;

    CallableMinecraftVersion(CrashReport par1CrashReport)
    {
        this.crashReportMinecraftVersion = par1CrashReport;
    }

    /**
     * The current version of Minecraft
     */
    public String minecraftVersion()
    {
        return "1.3.2";
    }

    public Object call()
    {
        return this.minecraftVersion();
    }
}
