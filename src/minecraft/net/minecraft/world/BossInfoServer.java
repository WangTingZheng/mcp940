package net.minecraft.world;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class BossInfoServer extends BossInfo
{
    private final Set<EntityPlayerMP> players = Sets.<EntityPlayerMP>newHashSet();
    private final Set<EntityPlayerMP> readOnlyPlayers;
    private boolean visible;

    public BossInfoServer(ITextComponent nameIn, BossInfo.Color colorIn, BossInfo.Overlay overlayIn)
    {
        super(MathHelper.getRandomUUID(), nameIn, colorIn, overlayIn);
        this.readOnlyPlayers = Collections.<EntityPlayerMP>unmodifiableSet(this.players);
        this.visible = true;
    }

    public void setPercent(float percentIn)
    {
        if (percentIn != this.percent)
        {
            super.setPercent(percentIn);
            this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PCT);
        }
    }

    public void setColor(BossInfo.Color colorIn)
    {
        if (colorIn != this.color)
        {
            super.setColor(colorIn);
            this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
        }
    }

    public void setOverlay(BossInfo.Overlay overlayIn)
    {
        if (overlayIn != this.overlay)
        {
            super.setOverlay(overlayIn);
            this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
        }
    }

    public BossInfo setDarkenSky(boolean darkenSkyIn)
    {
        if (darkenSkyIn != this.darkenSky)
        {
            super.setDarkenSky(darkenSkyIn);
            this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
        }

        return this;
    }

    public BossInfo setPlayEndBossMusic(boolean playEndBossMusicIn)
    {
        if (playEndBossMusicIn != this.playEndBossMusic)
        {
            super.setPlayEndBossMusic(playEndBossMusicIn);
            this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
        }

        return this;
    }

    public BossInfo setCreateFog(boolean createFogIn)
    {
        if (createFogIn != this.createFog)
        {
            super.setCreateFog(createFogIn);
            this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
        }

        return this;
    }

    public void setName(ITextComponent nameIn)
    {
        if (!Objects.equal(nameIn, this.name))
        {
            super.setName(nameIn);
            this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_NAME);
        }
    }

    private void sendUpdate(SPacketUpdateBossInfo.Operation operationIn)
    {
        if (this.visible)
        {
            SPacketUpdateBossInfo spacketupdatebossinfo = new SPacketUpdateBossInfo(operationIn, this);

            for (EntityPlayerMP entityplayermp : this.players)
            {
                entityplayermp.connection.sendPacket(spacketupdatebossinfo);
            }
        }
    }

    /**
     * Makes the boss visible to the given player.
     */
    public void addPlayer(EntityPlayerMP player)
    {
        if (this.players.add(player) && this.visible)
        {
            player.connection.sendPacket(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.ADD, this));
        }
    }

    /**
     * Makes the boss non-visible to the given player.
     */
    public void removePlayer(EntityPlayerMP player)
    {
        if (this.players.remove(player) && this.visible)
        {
            player.connection.sendPacket(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.REMOVE, this));
        }
    }

    public void setVisible(boolean visibleIn)
    {
        if (visibleIn != this.visible)
        {
            this.visible = visibleIn;

            for (EntityPlayerMP entityplayermp : this.players)
            {
                entityplayermp.connection.sendPacket(new SPacketUpdateBossInfo(visibleIn ? SPacketUpdateBossInfo.Operation.ADD : SPacketUpdateBossInfo.Operation.REMOVE, this));
            }
        }
    }

    public Collection<EntityPlayerMP> getPlayers()
    {
        return this.readOnlyPlayers;
    }
}
