package com.example.ultimatemathcompanion.datamodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "types")
@NoArgsConstructor
@Getter
@Setter
public class Types {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int id;

    @Column(name = "type")
    private String type;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "complexity")
    private Complexity complexity;

    @AllArgsConstructor
    public enum Kinds {
        SumSubtract(1),             // +-
        SumSubtrDivMultipl(2),      // */+-
        DivisionMultipl(3),         // */
        LongExpression(4);          // */+-*/+-

        @Getter private final int id;
    }

}
