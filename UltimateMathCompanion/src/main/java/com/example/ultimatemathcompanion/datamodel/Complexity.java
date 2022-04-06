package com.example.ultimatemathcompanion.datamodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "complexities")
@Getter
@NoArgsConstructor
@ToString
public class Complexity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complexity_id")
    private int id;

    @Column(name = "complexity_name")
    private String name;

    @Column(name = "description")
    private String description;

}
