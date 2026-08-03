package io.github.marlonwxv.core.gui.pagination;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaginationTest {

    @Test
    void navigatesWithoutLeavingValidBounds() {
        Pagination<Integer> pagination = new Pagination<>(List.of(1, 2, 3, 4, 5), 2);

        assertEquals(List.of(1, 2), pagination.getItems());
        assertFalse(pagination.hasPrevious());
        assertTrue(pagination.hasNext());

        pagination.next();
        assertEquals(List.of(3, 4), pagination.getItems());

        pagination.next();
        assertEquals(List.of(5), pagination.getItems());
        assertFalse(pagination.hasNext());

        pagination.next();
        assertEquals(List.of(5), pagination.getItems());
    }

    @Test
    void rejectsAnEmptyPageSize() {
        assertThrows(IllegalArgumentException.class, () -> new Pagination<>(List.of(1), 0));
    }
}
