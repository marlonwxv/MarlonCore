package io.github.marlonwxv.core.scheduler;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public final class Scheduler {

    private final Plugin plugin;
    private final Server server;

    public Scheduler(Plugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    public ScheduledTask runGlobal(Runnable runnable) {
        return server.getGlobalRegionScheduler().run(
                plugin,
                task -> runnable.run()
        );
    }

    public ScheduledTask runGlobalLater(long delayTicks, Runnable runnable) {
        return server.getGlobalRegionScheduler().runDelayed(
                plugin,
                task -> runnable.run(),
                delayTicks
        );
    }

    public ScheduledTask runGlobalRepeating(long delayTicks, long periodTicks, Runnable runnable) {
        return server.getGlobalRegionScheduler().runAtFixedRate(
                plugin,
                task -> runnable.run(),
                delayTicks,
                periodTicks
        );
    }

    public ScheduledTask runRegion(Location location, Runnable runnable) {
        return server.getRegionScheduler().run(
                plugin,
                location,
                task -> runnable.run()
        );
    }

    public ScheduledTask runRegionLater(Location location, long delayTicks, Runnable runnable) {
        return server.getRegionScheduler().runDelayed(
                plugin,
                location,
                task -> runnable.run(),
                delayTicks
        );
    }

    public ScheduledTask runRegionRepeating(Location location, long delayTicks, long periodTicks, Runnable runnable) {
        return server.getRegionScheduler().runAtFixedRate(
                plugin,
                location,
                task -> runnable.run(),
                delayTicks,
                periodTicks
        );
    }

    public ScheduledTask runEntity(Entity entity, Runnable runnable, Runnable retired) {
        return entity.getScheduler().run(
                plugin,
                task -> runnable.run(),
                retired
        );
    }

    public ScheduledTask runEntity(Entity entity, Runnable runnable) {
        return runEntity(entity, runnable, null);
    }

    public ScheduledTask runEntityLater(Entity entity, long delayTicks, Runnable runnable, Runnable retired) {
        return entity.getScheduler().runDelayed(
                plugin,
                task -> runnable.run(),
                retired,
                delayTicks
        );
    }

    public ScheduledTask runEntityRepeating(Entity entity, long delayTicks, long periodTicks,
                                            Runnable runnable, Runnable retired) {
        return entity.getScheduler().runAtFixedRate(
                plugin,
                task -> runnable.run(),
                retired,
                delayTicks,
                periodTicks
        );
    }

    public ScheduledTask runAsync(Runnable runnable) {
        return server.getAsyncScheduler().runNow(
                plugin,
                task -> runnable.run()
        );
    }

    public ScheduledTask runAsyncLater(long delay, TimeUnit unit, Runnable runnable) {
        return server.getAsyncScheduler().runDelayed(
                plugin,
                task -> runnable.run(),
                delay,
                unit
        );
    }

    public ScheduledTask runAsyncRepeating(long delay, long period, TimeUnit unit, Runnable runnable) {
        return server.getAsyncScheduler().runAtFixedRate(
                plugin,
                task -> runnable.run(),
                delay,
                period,
                unit
        );
    }
}