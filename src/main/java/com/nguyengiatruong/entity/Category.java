package com.nguyengiatruong.entity;

import com.nguyengiatruong.orm.annotation.Column;
import com.nguyengiatruong.orm.annotation.Entity;

@Entity
public class Category {
    @Column
    private long id;
    @Column
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
