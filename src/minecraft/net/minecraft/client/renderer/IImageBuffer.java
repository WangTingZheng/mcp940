package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;

public interface IImageBuffer
{
    BufferedImage parseUserSkin(BufferedImage image);

    void skinAvailable();
}
