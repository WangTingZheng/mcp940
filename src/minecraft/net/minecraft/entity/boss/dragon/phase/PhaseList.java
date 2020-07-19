package net.minecraft.entity.boss.dragon.phase;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.minecraft.entity.boss.EntityDragon;

public class PhaseList<T extends IPhase>
{
    private static PhaseList<?>[] phases = new PhaseList[0];
    public static final PhaseList<PhaseHoldingPattern> HOLDING_PATTERN = create(PhaseHoldingPattern.class, "HoldingPattern");
    public static final PhaseList<PhaseStrafePlayer> STRAFE_PLAYER = create(PhaseStrafePlayer.class, "StrafePlayer");
    public static final PhaseList<PhaseLandingApproach> LANDING_APPROACH = create(PhaseLandingApproach.class, "LandingApproach");
    public static final PhaseList<PhaseLanding> LANDING = create(PhaseLanding.class, "Landing");
    public static final PhaseList<PhaseTakeoff> TAKEOFF = create(PhaseTakeoff.class, "Takeoff");
    public static final PhaseList<PhaseSittingFlaming> SITTING_FLAMING = create(PhaseSittingFlaming.class, "SittingFlaming");
    public static final PhaseList<PhaseSittingScanning> SITTING_SCANNING = create(PhaseSittingScanning.class, "SittingScanning");
    public static final PhaseList<PhaseSittingAttacking> SITTING_ATTACKING = create(PhaseSittingAttacking.class, "SittingAttacking");
    public static final PhaseList<PhaseChargingPlayer> CHARGING_PLAYER = create(PhaseChargingPlayer.class, "ChargingPlayer");
    public static final PhaseList<PhaseDying> DYING = create(PhaseDying.class, "Dying");
    public static final PhaseList<PhaseHover> HOVER = create(PhaseHover.class, "Hover");
    private final Class <? extends IPhase > clazz;
    private final int id;
    private final String name;

    private PhaseList(int idIn, Class <? extends IPhase > clazzIn, String nameIn)
    {
        this.id = idIn;
        this.clazz = clazzIn;
        this.name = nameIn;
    }

    public IPhase createPhase(EntityDragon dragon)
    {
        try
        {
            Constructor <? extends IPhase > constructor = this.getConstructor();
            return constructor.newInstance(dragon);
        }
        catch (Exception exception)
        {
            throw new Error(exception);
        }
    }

    protected Constructor <? extends IPhase > getConstructor() throws NoSuchMethodException
    {
        return this.clazz.getConstructor(EntityDragon.class);
    }

    public int getId()
    {
        return this.id;
    }

    public String toString()
    {
        return this.name + " (#" + this.id + ")";
    }

    public static PhaseList<?> getById(int idIn)
    {
        return idIn >= 0 && idIn < phases.length ? phases[idIn] : HOLDING_PATTERN;
    }

    public static int getTotalPhases()
    {
        return phases.length;
    }

    private static <T extends IPhase> PhaseList<T> create(Class<T> phaseIn, String nameIn)
    {
        PhaseList<T> phaselist = new PhaseList<T>(phases.length, phaseIn, nameIn);
        phases = (PhaseList[])Arrays.copyOf(phases, phases.length + 1);
        phases[phaselist.getId()] = phaselist;
        return phaselist;
    }
}
