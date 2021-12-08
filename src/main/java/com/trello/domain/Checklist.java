package com.trello.domain;

import java.util.List;

public class Checklist {
    private String name;
    private List<CheckableItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CheckableItem> getItems() {
        return items;
    }

    public void setItems(List<CheckableItem> items) {
        this.items = items;
    }
}
