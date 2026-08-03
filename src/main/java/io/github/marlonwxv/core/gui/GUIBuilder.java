package io.github.marlonwxv.core.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import java.util.HashMap;
import java.util.Map;

public class GUIBuilder {

    private Component title = Component.text("GUI");

    private int rows = 3;

    private final Map<Integer, GUIButton> buttons = new HashMap<>();

    public GUIBuilder title(Component title) {
        this.title = title;
        return this;
    }

    public GUIBuilder rows(int rows) {
        this.rows = rows;
        return this;
    }

    public GUIBuilder button(int slot, GUIButton button) {
        buttons.put(slot, button);
        return this;
    }

    public AbstractGUI build() {
        return new AbstractGUI(title, rows) {
            @Override
            public void render(Player player) {
                for (Map.Entry<Integer, GUIButton> entry : buttons.entrySet()) {
                    inventory.setItem(entry.getKey(), entry.getValue().getItem());
                }
            }

            @Override
            public void onClick(GUIContext context){
                GUIButton button = buttons.get(context.slot());
                if (button != null) {
                    button.click(context);
                }
            }
        };
    }
}