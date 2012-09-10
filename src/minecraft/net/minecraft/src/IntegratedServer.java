package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class IntegratedServer extends MinecraftServer
{
    /** The Minecraft instance. */
    private final Minecraft mc;
    private final WorldSettings theWorldSettings;

    /** Instance of IntegratedServerListenThread. */
    private IntegratedServerListenThread theServerListeningThread;
    private boolean field_71348_o = false;
    private boolean isPublic;
    private ThreadLanServerPing field_71345_q;

    public IntegratedServer(Minecraft par1Minecraft, String par2Str, String par3Str, WorldSettings par4WorldSettings)
    {
        super(new File(Minecraft.getMinecraftDir(), "saves"));
        this.setServerOwner(par1Minecraft.session.username);
        this.setFolderName(par2Str);
        this.setWorldName(par3Str);
        this.setDemo(par1Minecraft.isDemo());
        this.canCreateBonusChest(par4WorldSettings.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigurationManager(new IntegratedPlayerList(this));
        this.mc = par1Minecraft;
        this.theWorldSettings = par4WorldSettings;

        try
        {
            this.theServerListeningThread = new IntegratedServerListenThread(this);
        }
        catch (IOException var6)
        {
            throw new Error();
        }
    }

    protected void loadAllWorlds(String par1Str, String par2Str, long par3, WorldType par5WorldType)
    {
        this.convertMapIfNeeded(par1Str);
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        ISaveHandler var6 = this.getActiveAnvilConverter().getSaveLoader(par1Str, true);

        for (int var7 = 0; var7 < this.worldServers.length; ++var7)
        {
            byte var8 = 0;

            if (var7 == 1)
            {
                var8 = -1;
            }

            if (var7 == 2)
            {
                var8 = 1;
            }

            if (var7 == 0)
            {
                if (this.isDemo())
                {
                    this.worldServers[var7] = new DemoWorldServer(this, var6, par2Str, var8, this.theProfiler);
                }
                else
                {
                    this.worldServers[var7] = new WorldServer(this, var6, par2Str, var8, this.theWorldSettings, this.theProfiler);
                }
            }
            else
            {
                this.worldServers[var7] = new WorldServerMulti(this, var6, par2Str, var8, this.theWorldSettings, this.worldServers[0], this.theProfiler);
            }

            this.worldServers[var7].addWorldAccess(new WorldManager(this, this.worldServers[var7]));
            this.getConfigurationManager().setPlayerManager(this.worldServers);
        }

        this.setDifficultyForAllWorlds(this.getDifficulty());
        this.initialWorldChunkLoad();
    }

    /**
     * Initialises the server and starts it.
     */
    protected boolean startServer() throws IOException
    {
        logger.info("Starting integrated minecraft server version 1.3.1");
        this.setOnlineMode(false);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        logger.info("Generating keypair");
        this.setKeyPair(CryptManager.createNewKeyPair());
        this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType());
        this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        return true;
    }

    /**
     * Main function called by run() every loop.
     */
    public void tick()
    {
        boolean var1 = this.field_71348_o;
        this.field_71348_o = this.theServerListeningThread.func_71752_f();

        if (!var1 && this.field_71348_o)
        {
            logger.info("Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }

        if (!this.field_71348_o)
        {
            super.tick();
        }
    }

    public boolean canStructuresSpawn()
    {
        return false;
    }

    public EnumGameType getGameType()
    {
        return this.theWorldSettings.getGameType();
    }

    /**
     * Defaults to "1" (Easy) for the dedicated server, defaults to "2" (Normal) on the client.
     */
    public int getDifficulty()
    {
        return this.mc.gameSettings.difficulty;
    }

    /**
     * Defaults to false.
     */
    public boolean isHardcore()
    {
        return this.theWorldSettings.getHardcoreEnabled();
    }

    protected File getDataDirectory()
    {
        return this.mc.mcDataDir;
    }

    public boolean isDedicatedServer()
    {
        return false;
    }

    /**
     * Gets the IntergratedServerListenThread.
     */
    public IntegratedServerListenThread getServerListeningThread()
    {
        return this.theServerListeningThread;
    }

    /**
     * Called on exit from the main run() loop.
     */
    protected void finalTick(CrashReport par1CrashReport)
    {
        this.mc.crashed(par1CrashReport);
    }

    /**
     * Adds the server info, including from theWorldServer, to the crash report.
     */
    public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport)
    {
        par1CrashReport.addCrashSectionCallable("Type", new CallableType3(this));
        par1CrashReport.addCrashSectionCallable("Is Modded", new CallableIsModded(this));
        return super.addServerInfoToCrashReport(par1CrashReport);
    }

    /**
     * Returns whether snooping is enabled or not.
     */
    public boolean isSnooperEnabled()
    {
        return Minecraft.getMinecraft().isSnooperEnabled();
    }

    /**
     * On dedicated does nothing. On integrated, sets commandsAllowedForAll, gameType and allows external connections.
     */
    public String shareToLAN(EnumGameType par1EnumGameType, boolean par2)
    {
        try
        {
            String var3 = this.theServerListeningThread.func_71755_c();
            System.out.println("Started on " + var3);
            this.isPublic = true;
            this.field_71345_q = new ThreadLanServerPing(this.getMOTD(), var3);
            this.field_71345_q.start();
            this.getConfigurationManager().setGameType(par1EnumGameType);
            this.getConfigurationManager().setCommandsAllowedForAll(par2);
            return var3;
        }
        catch (IOException var4)
        {
            return null;
        }
    }

    /**
     * Saves all necessary data as preparation for stopping the server.
     */
    public void stopServer()
    {
        super.stopServer();

        if (this.field_71345_q != null)
        {
            this.field_71345_q.interrupt();
            this.field_71345_q = null;
        }
    }

    /**
     * Sets the serverRunning variable to false, in order to get the server to shut down.
     */
    public void initiateShutdown()
    {
        super.initiateShutdown();

        if (this.field_71345_q != null)
        {
            this.field_71345_q.interrupt();
            this.field_71345_q = null;
        }
    }

    /**
     * Gets if Intergated Server is Public.
     */
    public boolean getIsIntergatedServerPublic()
    {
        return this.isPublic;
    }

    /**
     * Sets the game type for all worlds.
     */
    public void setGameType(EnumGameType par1EnumGameType)
    {
        this.getConfigurationManager().setGameType(par1EnumGameType);
    }

    public NetworkListenThread getNetworkThread()
    {
        return this.getServerListeningThread();
    }
}
