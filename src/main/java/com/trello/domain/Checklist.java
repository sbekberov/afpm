package com.trello.domain;

import java.util.List;

public class Checklist {
    public List<CheckableItem> getItems() {
        return items;
    }

    public void setItems(List<CheckableItem> items) {
        this.items = items;
    }

    private String name;
    private List<CheckableItem> items;
}
