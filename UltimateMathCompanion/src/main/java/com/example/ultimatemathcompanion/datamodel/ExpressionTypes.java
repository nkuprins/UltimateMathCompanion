package com.example.ultimatemathcompanion.datamodel;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class ExpressionTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int id;

    @Column(name = "type")
    private String type;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "complexity")
    private ExpressionComplexity expressionComplexity;

    public ExpressionTypes() {}

    public ExpressionTypes(String type, ExpressionComplexity expressionComplexity) {
        this.type = type;
        this.expressionComplexity = expressionComplexity;
    }

    public String getType() {
        return type;
    }

    public void setType(String typeString) {
        this.type = typeString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExpressionComplexity getExpressionComplexity() {
        return expressionComplexity;
    }

    public void setExpressionComplexity(ExpressionComplexity expressionComplexity) {
        this.expressionComplexity = expressionComplexity;
    }
}
