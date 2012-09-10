package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet133ProspectItem extends Packet
{
    public int windowId;
	public int itemId;
	public int quantity;

    public Packet133ProspectItem() {}

    public Packet133ProspectItem(int par1, int itemId, int quantity)
    {
        this.windowId = par1;
		this.itemId = itemId;
		this.quantity = quantity;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleProspectItem(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        this.windowId = par1DataInputStream.readByte();
        this.itemId = par1DataInputStream.readByte();
        this.quantity = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(this.windowId);
        par1DataOutputStream.writeByte(this.itemId);
        par1DataOutputStream.writeByte(this.quantity);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 3;
    }
}
