package com.example.ultimatemathcompanion.service;

import com.example.ultimatemathcompanion.datamodel.Expression;
import java.util.List;

public interface ExpressionService {

    List<Expression> findAll();
    Expression findById(int id);
    void save(Expression expression);
    void deleteById(int id);
}
