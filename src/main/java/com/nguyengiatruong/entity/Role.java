package com.nguyengiatruong.entity;

import com.nguyengiatruong.orm.annotation.Column;
import com.nguyengiatruong.orm.annotation.Entity;
import com.nguyengiatruong.orm.annotation.Id;

@Entity
public class Role {
    @Id
    private long id;
    @Column
    private String name;
    @Column
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
