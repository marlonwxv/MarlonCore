package io.github.marlonwxv.core.gui.decoration;

import io.github.marlonwxv.core.gui.util.SlotUtil;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BorderDecorator implements GUIDecorator {

    private final ItemStack item;

    public BorderDecorator(ItemStack item){
        this.item = item;
    }

    @Override
    public void apply(Inventory inventory) {
        int rows = inventory.getSize() / 9;

        for (Integer slot : SlotUtil.border(rows)) {
            if (inventory.getItem(slot) == null) {
                inventory.setItem(slot, item.clone());
            }
        }
    }
}