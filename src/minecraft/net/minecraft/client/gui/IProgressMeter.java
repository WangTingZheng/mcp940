package net.minecraft.client.gui;

public interface IProgressMeter
{
    String[] LOADING_STRINGS = new String[] {"oooooo", "Oooooo", "oOoooo", "ooOooo", "oooOoo", "ooooOo", "oooooO"};

    void onStatsUpdated();
}
