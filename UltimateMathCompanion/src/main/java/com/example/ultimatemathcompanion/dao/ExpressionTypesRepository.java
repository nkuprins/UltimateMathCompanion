package com.example.ultimatemathcompanion.dao;

import com.example.ultimatemathcompanion.datamodel.ExpressionTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpressionTypesRepository extends JpaRepository<ExpressionTypes, Integer> {

}