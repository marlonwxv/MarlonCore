package io.github.marlonwxv.core;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MarlonPlugin extends JavaPlugin {

    private Core core;

    @Override
    public final void onEnable() {
        this.core = Core.init(this);

        new Metrics(this, getMetricsId());
        enable();
    }

    @Override
    public final void onDisable() {
        try {
            disable();
        } finally {
            core.shutdown();
        }
    }

    public final Core core() {
        return core;
    }

    public abstract int getMetricsId();

    public void enable() {}

    public void disable() {}
}