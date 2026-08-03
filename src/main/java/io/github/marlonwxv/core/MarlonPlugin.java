package io.github.marlonwxv.core;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MarlonPlugin extends JavaPlugin {

    @Override
    public final void onEnable() {
        Core.init(this);

        new Metrics(this, getMetricsId());

        enable();
    }

    @Override
    public final void onDisable() {
        disable();
    }

    public abstract int getMetricsId();

    public void enable() {}

    public void disable() {}
}