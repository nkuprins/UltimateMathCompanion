package com.example.ultimatemathcompanion.service;

import com.example.ultimatemathcompanion.dao.ExpressionTypesRepository;
import com.example.ultimatemathcompanion.datamodel.ExpressionTypes;
import org.springframework.stereotype.Service;

@Service
public class ExpressionTypesServiceImpl implements ExpressionTypesService {

    ExpressionTypesRepository expressionTypesRepository;

    public ExpressionTypesServiceImpl(ExpressionTypesRepository expressionTypesRepository) {
        this.expressionTypesRepository = expressionTypesRepository;
    }

    @Override
    public ExpressionTypes findById(int id) {
        return expressionTypesRepository.getById(id);
    }
}
