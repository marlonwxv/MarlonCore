package io.github.marlonwxv.core.gui.pagination;

import java.util.List;
import java.util.Objects;

public class Pagination<T> {

    private final List<T> items;
    private final int perPage;

    private int page;

    public Pagination(List<T> items, int perPage) {
        if (perPage < 1) {
            throw new IllegalArgumentException("perPage must be at least 1.");
        }
        this.items = List.copyOf(Objects.requireNonNull(items, "items"));
        this.perPage = perPage;
        this.page = 0;
    }

    public List<T> getItems() {
        int start = page * perPage;
        int end = Math.min(start + perPage, items.size());

        if (start >= items.size()) {
            return List.of();
        }

        return items.subList(start, end);
    }

    public void next() {
        if (hasNext()) {
            page++;
        }
    }

    public void previous() {
        if (hasPrevious()) {
            page--;
        }
    }

    public boolean hasNext() {
        return page < getMaxPage();
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public int getPage() {
        return page;
    }


    public int getMaxPage() {
        if (items.isEmpty()) {
            return 0;
        }

        return (items.size() - 1) / perPage;
    }
}