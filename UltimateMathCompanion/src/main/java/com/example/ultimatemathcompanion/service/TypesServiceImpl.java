package com.example.ultimatemathcompanion.service;

import com.example.ultimatemathcompanion.dao.ExpressionTypesRepository;
import com.example.ultimatemathcompanion.datamodel.Types;
import org.springframework.stereotype.Service;

@Service
public class TypesServiceImpl implements TypesService {

    ExpressionTypesRepository expressionTypesRepository;

    public TypesServiceImpl(ExpressionTypesRepository expressionTypesRepository) {
        this.expressionTypesRepository = expressionTypesRepository;
    }

    @Override
    public Types findById(int id) {
        return expressionTypesRepository.getById(id);
    }
}
