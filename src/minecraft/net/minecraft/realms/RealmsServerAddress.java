package net.minecraft.realms;

import net.minecraft.client.multiplayer.ServerAddress;

public class RealmsServerAddress
{
    private final String host;
    private final int port;

    protected RealmsServerAddress(String hostIn, int portIn)
    {
        this.host = hostIn;
        this.port = portIn;
    }

    public String getHost()
    {
        return this.host;
    }

    public int getPort()
    {
        return this.port;
    }

    public static RealmsServerAddress parseString(String p_parseString_0_)
    {
        ServerAddress serveraddress = ServerAddress.fromString(p_parseString_0_);
        return new RealmsServerAddress(serveraddress.getIP(), serveraddress.getPort());
    }
}
