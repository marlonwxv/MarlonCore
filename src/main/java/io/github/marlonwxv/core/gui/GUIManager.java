package io.github.marlonwxv.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class GUIManager implements Listener {

    @EventHandler
    public void click(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getView().getTopInventory().getHolder() instanceof GUIHolder holder)) {
            return;
        }

        GUI gui = holder.getGUI();

        event.setCancelled(true);

        int rawSlot = event.getRawSlot();
        if (rawSlot < 0 || rawSlot >= event.getView().getTopInventory().getSize()) {
            return;
        }

        if (gui instanceof AbstractGUI abstractGUI) {
            GUIButton button = abstractGUI.getButton(event.getRawSlot());

            if (button != null) {
                button.click(new GUIContext(player, event, gui));
                return;
            }
        }

        gui.onClick(new GUIContext(player, event, gui));
    }

    @EventHandler
    public void drag(InventoryDragEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof GUIHolder)) {
            return;
        }

        int topSize = event.getView().getTopInventory().getSize();
        if (event.getRawSlots().stream().anyMatch(slot -> slot < topSize)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (event.getInventory().getHolder() instanceof GUIHolder holder) {
            holder.getGUI().onClose(player);
        }
    }
}