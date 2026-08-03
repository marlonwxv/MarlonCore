package io.github.marlonwxv.core.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GUIHolder implements InventoryHolder {

    private final GUI gui;
    private Inventory inventory;

    public GUIHolder(GUI gui) {
        this.gui = gui;
    }

    public GUI getGUI() {
        return gui;
    }

    void bind(Inventory inventory) {
        if (this.inventory != null) {
            throw new IllegalStateException("GUI holder is already bound to an inventory.");
        }
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        if (inventory == null) {
            throw new IllegalStateException("GUI holder has not been bound to an inventory yet.");
        }
        return inventory;
    }
}