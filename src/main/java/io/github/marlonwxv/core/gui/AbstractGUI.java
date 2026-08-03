package io.github.marlonwxv.core.gui;

import io.github.marlonwxv.core.gui.decoration.GUIDecorator;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public abstract class AbstractGUI implements GUI {

    private final Component title;
    private final int rows;

    protected Inventory inventory;

    private final List<GUIDecorator> decorators = new ArrayList<>();

    private final Map<Integer, GUIButton> buttons = new HashMap<>();

    public AbstractGUI(Component title, int rows) {
        this.title = title;
        this.rows = Math.max(1, Math.min(rows, 6));
    }

    protected void createInventory() {
        GUIHolder holder = new GUIHolder(this);
        inventory = Bukkit.createInventory(
                holder,
                rows * 9,
                title
        );
        holder.bind(inventory);
    }

    @Override
    public void open(Player player) {

        createInventory();
        redraw(player);

        player.openInventory(inventory);

        onOpen(player);
    }

    public AbstractGUI setButton(int slot, GUIButton button) {
        Objects.requireNonNull(button, "button");
        if (slot < 0 || slot >= rows * 9) {
            throw new IllegalArgumentException("Button slot " + slot + " is outside this GUI.");
        }
        buttons.put(slot, button);

        if (inventory != null) {
            inventory.setItem(
                    slot,
                    button.getItem()
            );
        }

        return this;
    }

    protected final void clearButtons() {
        buttons.clear();
    }

    protected final void redraw(Player player) {
        render(player);
        buttons.forEach((slot, button) -> inventory.setItem(slot, button.getItem()));
        for (GUIDecorator decorator : decorators) {
            decorator.apply(inventory);
        }
    }

    public GUIButton getButton(int slot) {
        return buttons.get(slot);
    }

    public AbstractGUI decorate(GUIDecorator decorator) {
        decorators.add(decorator);
        return this;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getRows() {
        return rows;
    }
}