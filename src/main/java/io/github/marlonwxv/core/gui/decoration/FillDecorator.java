package io.github.marlonwxv.core.gui.decoration;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FillDecorator implements GUIDecorator {

    private final ItemStack item;

    public FillDecorator(ItemStack item) {
        this.item = item;
    }

    @Override
    public void apply(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, item.clone());
            }
        }
    }
}