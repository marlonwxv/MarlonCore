package io.github.marlonwxv.core.gui.pagination;

import io.github.marlonwxv.core.builder.ItemBuilder;
import io.github.marlonwxv.core.gui.AbstractGUI;
import io.github.marlonwxv.core.gui.GUIContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PaginatedGUI<T> extends AbstractGUI {

    private final Pagination<T> pagination;
    private final List<Integer> contentSlots;
    private final Function<T, ItemStack> renderer;
    private final BiConsumer<T, GUIContext> clickAction;

    public PaginatedGUI(Component title, int rows, List<T> items,
                        List<Integer> contentSlots, Function<T, ItemStack> renderer,
                        BiConsumer<T, GUIContext> clickAction) {
        super(title, rows);
        this.pagination = new Pagination<>(
                items,
                contentSlots.size()
        );
        this.contentSlots = contentSlots;
        this.renderer = renderer;
        this.clickAction = clickAction;
    }

    public PaginatedGUI(Component title, int rows, List<T> items,
                        List<Integer> contentSlots, Function<T, ItemStack> renderer,
                        Consumer<T> clickAction) {
        this(title, rows, items, contentSlots, renderer, (item, context) -> clickAction.accept(item));
    }

    @Override
    public void render(Player player) {
        inventory.clear();

        List<T> items = pagination.getItems();

        for(int i = 0; i < items.size(); i++){
            int slot = contentSlots.get(i);

            inventory.setItem(
                    slot,
                    renderer.apply(items.get(i))
            );
        }

        createNavigation();
    }

    private void createNavigation() {
        if (pagination.hasPrevious()) {
            inventory.setItem(inventory.getSize() - 9, new ItemBuilder(Material.ARROW)
                    .name(Component.text("Previous Page", TextColor.fromHexString("#1F95CD")))
                    .build());
        }

        if (pagination.hasNext()) {
            inventory.setItem(inventory.getSize() - 1, new ItemBuilder(Material.ARROW)
                    .name(Component.text("Next Page", TextColor.fromHexString("#1F95CD")))
                    .build());
        }
    }

    @Override
    public void onClick(GUIContext context) {

        int slot = context.slot();

        if (slot == inventory.getSize() - 9 && pagination.hasPrevious()) {
            pagination.previous();
            redraw(context.player());
            return;
        }

        if (slot == inventory.getSize() - 1 && pagination.hasNext()) {
            pagination.next();
            redraw(context.player());
            return;
        }

        int index = contentSlots.indexOf(slot);

        if (index == -1) {
            return;
        }

        if (index >= pagination.getItems().size()) {
            return;
        }

        T clicked = pagination.getItems().get(index);

        clickAction.accept(clicked, context);
    }
}