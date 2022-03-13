package com.example.ultimatemathcompanion.service;

import com.example.ultimatemathcompanion.dao.TypesRepository;
import com.example.ultimatemathcompanion.datamodel.Types;
import org.springframework.stereotype.Service;

@Service
public class TypesServiceImpl implements TypesService {

    private final TypesRepository typesRepository;

    public TypesServiceImpl(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }

    @Override
    public Types findById(int id) {
        return typesRepository.getById(id);
    }
}
