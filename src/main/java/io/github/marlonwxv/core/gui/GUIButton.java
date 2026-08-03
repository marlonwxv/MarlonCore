package io.github.marlonwxv.core.gui;

import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GUIButton {

    private final ItemStack item;
    private final Consumer<GUIContext> action;

    public GUIButton(ItemStack item, Consumer<GUIContext> action) {
        this.item = item;
        this.action = action;
    }

    public GUIButton(ItemStack item){
        this(item, null);
    }

    public ItemStack getItem(){
        return item.clone();
    }

    public void click(GUIContext context){
        if (action != null) {
            action.accept(context);
        }
    }
}