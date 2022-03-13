package com.example.ultimatemathcompanion.service;

import com.example.ultimatemathcompanion.dao.ExpressionRepository;
import com.example.ultimatemathcompanion.datamodel.Expression;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressionServiceImpl implements ExpressionService {

    private final ExpressionRepository expressionRepository;

    public ExpressionServiceImpl(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }

    @Override
    public List<Expression> findAll() {
        return expressionRepository.findAll();
    }

    @Override
    public Expression findById(int id) {
        return expressionRepository.getById(id);
    }

    @Override
    public void deleteById(int id) {
        expressionRepository.deleteById(id);
    }

    @Override
    public void save(Expression expression) {
        expressionRepository.save(expression);
    }
}
