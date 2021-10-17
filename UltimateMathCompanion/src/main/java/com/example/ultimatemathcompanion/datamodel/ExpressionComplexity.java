package com.example.ultimatemathcompanion.datamodel;

import javax.persistence.*;

@Entity
@Table(name = "complexities")
public class ExpressionComplexity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complexity_id")
    private int id;

    @Column(name = "complexity_name")
    private String name;

    @Column(name = "description")
    private String description;

    public ExpressionComplexity() {}

    public ExpressionComplexity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "ExpressionComplexity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
