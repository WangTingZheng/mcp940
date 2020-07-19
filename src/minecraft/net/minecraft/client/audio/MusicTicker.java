package net.minecraft.client.audio;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class MusicTicker implements ITickable
{
    private final Random rand = new Random();
    private final Minecraft mc;
    private ISound currentMusic;
    private int timeUntilNextMusic = 100;

    public MusicTicker(Minecraft mcIn)
    {
        this.mc = mcIn;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        MusicTicker.MusicType musicticker$musictype = this.mc.getAmbientMusicType();

        if (this.currentMusic != null)
        {
            if (!musicticker$musictype.getMusicLocation().getSoundName().equals(this.currentMusic.getSoundLocation()))
            {
                this.mc.getSoundHandler().stopSound(this.currentMusic);
                this.timeUntilNextMusic = MathHelper.getInt(this.rand, 0, musicticker$musictype.getMinDelay() / 2);
            }

            if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic))
            {
                this.currentMusic = null;
                this.timeUntilNextMusic = Math.min(MathHelper.getInt(this.rand, musicticker$musictype.getMinDelay(), musicticker$musictype.getMaxDelay()), this.timeUntilNextMusic);
            }
        }

        this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, musicticker$musictype.getMaxDelay());

        if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0)
        {
            this.playMusic(musicticker$musictype);
        }
    }

    /**
     * Plays a music track for the maximum allowable period of time
     */
    public void playMusic(MusicTicker.MusicType requestedMusicType)
    {
        this.currentMusic = PositionedSoundRecord.getMusicRecord(requestedMusicType.getMusicLocation());
        this.mc.getSoundHandler().playSound(this.currentMusic);
        this.timeUntilNextMusic = Integer.MAX_VALUE;
    }

    public static enum MusicType
    {
        MENU(SoundEvents.MUSIC_MENU, 20, 600),
        GAME(SoundEvents.MUSIC_GAME, 12000, 24000),
        CREATIVE(SoundEvents.MUSIC_CREATIVE, 1200, 3600),
        CREDITS(SoundEvents.MUSIC_CREDITS, 0, 0),
        NETHER(SoundEvents.MUSIC_NETHER, 1200, 3600),
        END_BOSS(SoundEvents.MUSIC_DRAGON, 0, 0),
        END(SoundEvents.MUSIC_END, 6000, 24000);

        private final SoundEvent musicLocation;
        private final int minDelay;
        private final int maxDelay;

        private MusicType(SoundEvent musicLocationIn, int minDelayIn, int maxDelayIn)
        {
            this.musicLocation = musicLocationIn;
            this.minDelay = minDelayIn;
            this.maxDelay = maxDelayIn;
        }

        public SoundEvent getMusicLocation()
        {
            return this.musicLocation;
        }

        public int getMinDelay()
        {
            return this.minDelay;
        }

        public int getMaxDelay()
        {
            return this.maxDelay;
        }
    }
}
