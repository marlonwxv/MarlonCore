package io.github.marlonwxv.core.gui.util;

import java.util.ArrayList;
import java.util.List;

public final class SlotUtil {

    private SlotUtil() {}

    public static List<Integer> border(int rows) {
        List<Integer> slots = new ArrayList<>();

        int size = rows * 9;

        for (int i = 0; i < size; i++) {
            int row = i / 9;
            int column = i % 9;

            if (row == 0 || row == rows - 1 || column == 0 || column == 8) {
                slots.add(i);
            }
        }

        return slots;
    }
}