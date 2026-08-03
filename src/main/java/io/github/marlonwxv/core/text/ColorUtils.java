package io.github.marlonwxv.core.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class ColorUtils {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private ColorUtils() {
    }

    public static Component parse(String input) {
        if (input == null) return Component.empty();
        return MINI_MESSAGE.deserialize(input);
    }
}