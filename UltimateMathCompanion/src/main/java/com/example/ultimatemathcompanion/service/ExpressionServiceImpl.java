package com.example.ultimatemathcompanion.service;

import com.example.ultimatemathcompanion.dao.ExpressionRepository;
import com.example.ultimatemathcompanion.datamodel.Expression;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressionServiceImpl implements ExpressionService {

    private ExpressionRepository expressionRepository;

    public ExpressionServiceImpl(ExpressionRepository expressionRepository) {
        this.expressionRepository = expressionRepository;
    }

    @Override
    public List<Expression> findAll() {
        return expressionRepository.findAll();
    }
}
