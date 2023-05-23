package com.vannou.test.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Fireman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "fireman")
    private List<Fire> fires;

    public Fireman() {
    }
    public Fireman( String name, List<Fire> fires) {

        this.name = name;
        this.fires = fires;
    }

    public Fireman(Long id, String name, List<Fire> fires) {
        this.id = id;
        this.name = name;
        this.fires = fires;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Fire> getFires() {
        return this.fires;
    }

    public void setFires(List<Fire> fires) {
        this.fires = fires;
    }

}
