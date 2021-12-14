package spd.trello.domain.service;

import spd.trello.domain.Resource;

public abstract class AbstractService <T extends Resource> {
    public abstract T create();
    public void print(T t){
        System.out.println(t);
    }
    public abstract void update(int index, T t);
}
