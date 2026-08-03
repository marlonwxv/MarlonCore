package io.github.marlonwxv.core;

import io.github.marlonwxv.core.gui.GUIManager;
import org.bukkit.Bukkit;

public final class Core {

    private Core() {}

    public static void init(MarlonPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new GUIManager(), plugin);
    }
}