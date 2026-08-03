package io.github.marlonwxv.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public record GUIContext(Player player, InventoryClickEvent event, GUI gui) {

    public int slot() {
        return event.getRawSlot();
    }

    public ClickType click() {
        return event.getClick();
    }
}