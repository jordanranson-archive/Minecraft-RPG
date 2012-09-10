package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderProspectingTable extends TileEntitySpecialRenderer
{
    private ModelProspectingTable prospectingProp = new ModelProspectingTable();

    public void renderTileEntityProspectingTableAt(TileEntityProspectingTable par1TileEntityProspectingTable, double par2, double par4, double par6, float par8)
    {
        GL11.glPushMatrix();
		
        GL11.glTranslatef((float)par2 + 0.5F, (float)par4 - 0.6F, (float)par6 + 0.5F);
        GL11.glRotatef(-180.0F, 0.0F, 1.0F, 0.0F);
		
        this.bindTextureByName("/item/prospectingtable.png");

        this.prospectingProp.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityProspectingTableAt((TileEntityProspectingTable)par1TileEntity, par2, par4, par6, par8);
    }
}
