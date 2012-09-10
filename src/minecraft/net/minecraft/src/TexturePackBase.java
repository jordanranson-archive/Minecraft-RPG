package net.minecraft.src;

import java.io.InputStream;

public interface TexturePackBase
{
    void func_77533_a(RenderEngine var1);

    void func_77535_b(RenderEngine var1);

    /**
     * Gives a texture resource as InputStream.
     */
    InputStream getResourceAsStream(String var1);

    /**
     * Get the texture pack ID
     */
    String getTexturePackID();

    /**
     * Get the file name of the texture pack, or Default if not from a custom texture pack
     */
    String getTexturePackFileName();

    /**
     * Get the first line of the texture pack description (read from the pack.txt file)
     */
    String getFirstDescriptionLine();

    /**
     * Get the second line of the texture pack description (read from the pack.txt file)
     */
    String getSecondDescriptionLine();

    int func_77534_f();
}
