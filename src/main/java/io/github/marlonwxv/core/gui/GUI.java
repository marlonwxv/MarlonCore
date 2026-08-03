package io.github.marlonwxv.core.gui;

import org.bukkit.entity.Player;

public interface GUI {

    void open(Player player);
    void render(Player player);

    default void onClick(GUIContext context) {}
    default void onClose(Player player) {}
    default void onOpen(Player player) {}
}