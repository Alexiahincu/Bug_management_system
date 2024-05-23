package org.example.Domain;

import java.io.Serializable;

public abstract class Entity<T> implements Serializable {
    protected T id;
    private static final long serialVersionUID = 1000L;
    public Entity(T id){
        this.id=id;
    }
    public T getId() {
        return id;
    }


}
