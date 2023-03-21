package etu1989.model;

import etu1989.annotation.url;

public class Employe {

    int id;
    String name;
    
    public void setId(int id) throws IllegalArgumentException {
        if (id < 0) throw new IllegalArgumentException("id must be positive");
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("Name must not be null");
        if (name.length() == 0) throw new IllegalArgumentException("Name must not be empty");
        if (name.matches(".*\\d.*")) throw new IllegalArgumentException("name must not contain digits");
        if (name.matches(".*\\W.*")) throw new IllegalArgumentException("name must not contain special characters");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Employe(int id, String name) {
        setId(id);
        setName(name);
    }

    public Employe() {
    
    }

    @url("emp-find")
    public Employe findById(int id) {
        return new Employe(id, "John Doe");
    }

    @url("emp-save")
    public void save() {
        System.out.println("Saved");
    }

}